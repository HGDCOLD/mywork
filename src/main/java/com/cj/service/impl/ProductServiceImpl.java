package com.cj.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.util.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cj.entity.Order;
import com.cj.entity.Product;
import com.cj.mapper.ProductMapper;
import com.cj.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.commons.lang3.RandomUtils;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient1;

    @Autowired
    private RedissonClient redissonClient2;

    @Override
    public String buy(Order order) {
//        ConcurrentHashMap
        int ans = productMapper.buyProduct(order);
        String result = null;
        if(ans > 0){
            result = "购买成功";
        }else{
            result = "购买失败";
        }

        return result;
    }

    @Override
    public List<Product> queryAll() {
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<Product>();
        List<Product> products = productMapper.selectList(productQueryWrapper);
        return products;
    }

    @Override
    @Transactional
    public String buyTestRedisLock(Order order, int timeStatus) {
        String key = "buyProductId:" + order.getProductId();
        String value = RandomUtils.nextInt() + ":" + Thread.currentThread().getName();
        while(!stringRedisTemplate.opsForValue().setIfAbsent(key,value,5,TimeUnit.SECONDS)){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info(Thread.currentThread().getName() + ":等待让出redis商品锁:" + DateUtil.now());
        }
        log.info(Thread.currentThread().getName() + ":抢到redis商品锁:" + DateUtil.now());
        String result;
        try {
            int ans = productMapper.buyProduct(order);
            log.info(Thread.currentThread().getName() + ":执行业务中.....");
            try {
                if(timeStatus == 0){
                    TimeUnit.SECONDS.sleep(8);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            result = null;
            if(ans > 0){
                result = "购买成功";
            }else{
                result = "购买失败";
            }
        } finally {
            String s = stringRedisTemplate.opsForValue().get(key);
            log.info(Thread.currentThread().getName() + "锁为：" + s);
//            stringRedisTemplate.delete(key);
            String luaScript = "if (redis.call('get',KEYS[1]) == ARGV[1]) then " +
                                "return redis.call('del',KEYS[1]) " + "else " +
                                "return 0 " + "end";
            stringRedisTemplate.execute(new DefaultRedisScript<>(luaScript,Boolean.class), Arrays.asList(key),value);
        }
        log.info(Thread.currentThread().getName() + ":" + value + ":业务结束:" + DateUtil.now());
        return result;
    }

    public String buyTestRedisson1Lock(Order order) {
        String key = "buyProductId:" + order.getProductId();
        RLock lock = redissonClient1.getLock(key);
        lock.lock();
        String result = "有异常";
        try {
            System.out.println("Redisson1" + Thread.currentThread().getName() + "获得锁" + key + "时间：" + DateUtil.now());;
            try {
                TimeUnit.SECONDS.sleep(15);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            int ans = productMapper.buyProduct(order);
            if(ans > 0){
                result = "购买成功";
            }else{
                result = "购买失败";
            }
        } finally {
            System.out.println("Redisson1" + Thread.currentThread().getName() + "释放锁" + key + "时间：" + DateUtil.now());;
            lock.unlock();
        }
        return result;
    }

    public String buyTestRedisson2Lock(Order order) {
        String key = "buyProductId:" + order.getProductId();
        RLock lock = redissonClient2.getLock(key);
        lock.lock();
        String result = "有异常";
        try {
            System.out.println("Redisson2" + Thread.currentThread().getName() + "获得锁" + key + "时间：" + DateUtil.now());;
            try {
                TimeUnit.SECONDS.sleep(60);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            int ans = productMapper.buyProduct(order);
            if(ans > 0){
                result = "购买成功";
            }else{
                result = "购买失败";
            }
        } finally {
            System.out.println("Redisson2" + Thread.currentThread().getName() + "释放锁" + key + "时间：" + DateUtil.now());;
            lock.unlock();
        }
        return result;
    }

    public String buyTestRedissonAllLock(Order order) {
        String key = "buyProductId:" + order.getProductId();
        RLock lock1 = redissonClient1.getLock(key);
        RLock lock2 = redissonClient2.getLock(key);
        RedissonMultiLock redissonMultiLock = new RedissonMultiLock(lock1, lock2);
        try {
            redissonMultiLock.tryLock(15, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String result = "有异常";
        try {
            System.out.println("多机" + Thread.currentThread().getName() + "获得锁" + key + "时间：" + DateUtil.now());;
            try {
                TimeUnit.SECONDS.sleep(60);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            int ans = productMapper.buyProduct(order);
            if(ans > 0){
                result = "购买成功";
            }else{
                result = "购买失败";
            }
        } finally {
            System.out.println("多机" + Thread.currentThread().getName() + "释放锁" + key + "时间：" + DateUtil.now());
//            redissonMultiLock.isLocked() && redissonMultiLock.isHeldByCurrentThread()
            redissonMultiLock.unlock();
        }
        return result;
    }
}

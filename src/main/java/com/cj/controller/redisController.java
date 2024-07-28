package com.cj.controller;

import com.alibaba.fastjson2.util.UUIDUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cj.entity.Order;
import com.cj.entity.Product;
import com.cj.service.ProductService;
import com.cj.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;


@RestController
@Api(tags = "redis测试接口")
public class redisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProductService productService;

    @PostMapping("/redistest/sale/buy")
    @ApiOperation("redis测试-购买商品")
    public Result buy(@RequestBody Order order){

        String result = productService.buy(order);
        return Result.success(result);

    }

    @PostMapping("/redistest/sale/queryall")
    @ApiOperation("redis测试-展示全部商品")
    public Result queryAll(){
        List<Product> products = productService.queryAll();
        return Result.success(products);

    }

    @PostMapping("/redistest/sale/test1")
    @ApiOperation("redis测试-分布式锁-购买商品")
    public Result testSale(@RequestBody Order order){

        String result = productService.buyTestRedisLock(order,order.getId().intValue());
        return Result.success(result);
    }

    @PostMapping("/redisson1test/sale/test1")
    @ApiOperation("redisson1测试-分布式锁-购买商品")
    public Result testSaleByRedisson1(@RequestBody Order order){

        String result = productService.buyTestRedisson1Lock(order);
        return Result.success(result);
    }

    @PostMapping("/redisson2test/sale/test1")
    @ApiOperation("redisson2测试-分布式锁-购买商品")
    public Result testSaleByRedisson2(@RequestBody Order order){

        String result = productService.buyTestRedisson2Lock(order);
        return Result.success(result);
    }

    @PostMapping("/redissonAlltest/sale/test1")
    @ApiOperation("redissonAll测试-分布式锁-购买商品")
    public Result testSaleByRedissonAll(@RequestBody Order order){

        String result = productService.buyTestRedissonAllLock(order);
        return Result.success(result);
    }
}

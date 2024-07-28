package com.cj.service;

import com.cj.entity.Order;
import com.cj.entity.Product;

import java.util.List;

public interface ProductService {

    String buy(Order order);

    List<Product> queryAll();

    String buyTestRedisLock(Order order,int status);


    String buyTestRedisson1Lock(Order order);
    String buyTestRedisson2Lock(Order order);
    String buyTestRedissonAllLock(Order order);


}

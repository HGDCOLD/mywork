package com.cj.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cj.entity.Order;
import com.cj.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper extends BaseMapper<Product> {

    int buyProduct(Order order);


}

package com.cj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@TableName("m_product")
@Data
public class Product {

    @TableId(value = "id",type = IdType.AUTO)
    public Long id;

//    @TableName(value = "productName")
    @TableField("productName")
    public String productName;

    public BigDecimal price;

    public Long store;

    public LocalDateTime createdTime;

    public LocalDateTime updatedTime;



}

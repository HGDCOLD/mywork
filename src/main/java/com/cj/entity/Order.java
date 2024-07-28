package com.cj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;

import java.math.BigDecimal;

@TableName("t_order")
@Data
public class Order {

    @TableId(value = "id", type = IdType.AUTO)
    public Long id;

    public Long productId;

    public Integer count;

    public BigDecimal money;

}

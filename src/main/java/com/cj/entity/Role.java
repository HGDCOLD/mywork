package com.cj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("cj_role")
public class Role {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String roleName;

    private String roleCode;


}

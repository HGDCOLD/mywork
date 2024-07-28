package com.cj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("cj_user")
public class User {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String realname;

    private String email;

    private String phone;

    private String address;

    private String sex;

    private String birthday;

    private String headImg;

    private Date createTime;

    private int status;



}

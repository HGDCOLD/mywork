package com.cj.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("cj_user_role")
public class UserRole {


    private Long userId;

    private Long roleId;


}

package com.cj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cj.entity.User;

public interface UserService extends IService<User> {

    User getUserByUserName(String username);


}


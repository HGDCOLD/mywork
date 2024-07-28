package com.cj.security;

import com.cj.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUser extends org.springframework.security.core.userdetails.User {

    /**
     * 我们自己的用户实体对象，要调取用户信息时直接获取这个实体对象。（这里我就不写get/set方法了）
     */
    private User user;

    public CustomUser(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

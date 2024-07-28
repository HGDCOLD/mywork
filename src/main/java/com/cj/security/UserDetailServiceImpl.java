package com.cj.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cj.entity.Role;
import com.cj.entity.User;
import com.cj.service.RoleService;
import com.cj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUserName(username);
        if(user == null){
            throw  new RuntimeException("用户名不存在");
        }
        if(user.getStatus() == 1){
            throw  new RuntimeException("该账号禁用");
        }
        List<Role> rolesList = roleService.findRolesByUserId(user.getId());

        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        if(rolesList != null){
            for(Role role : rolesList){
                authList.add(new SimpleGrantedAuthority(role.getRoleName()));
            }
        }

        return new CustomUser(user,authList);
    }
}

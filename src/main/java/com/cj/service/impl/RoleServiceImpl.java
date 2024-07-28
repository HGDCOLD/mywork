package com.cj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cj.entity.Role;
import com.cj.entity.UserRole;
import com.cj.entity.Vo.AssignRoleVo;
import com.cj.mapper.RoleMapper;
import com.cj.service.RoleService;
import com.cj.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;

    public List<Role> findRolesByUserId(Long userId){
        List<Role> roles = null;
        roles = baseMapper.findRoleByUserId(userId);
        return roles;
    }

    public void assignRole(AssignRoleVo assignRoleVo){
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,assignRoleVo.getUserId());
        userRoleService.remove(wrapper);

        List<Long> roleIds = assignRoleVo.getRoleIds();

        if(roleIds != null){
            for(Long roleId : roleIds){
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(assignRoleVo.getUserId());
                userRoleService.save(userRole);
            }
        }

    }

}

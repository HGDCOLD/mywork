package com.cj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cj.entity.Role;
import com.cj.entity.Vo.AssignRoleVo;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<Role> findRolesByUserId(Long userId);

    public void assignRole(AssignRoleVo assignRoleVo);

}

package com.cj.controller;

import com.cj.entity.Role;
import com.cj.entity.User;
import com.cj.entity.UserRole;
import com.cj.entity.Vo.AssignRoleVo;
import com.cj.mapper.RoleMapper;
import com.cj.service.RoleService;
import com.cj.util.Result;
import com.cj.util.md5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/role")
public class roleController {

    @Autowired
    public RoleService roleService;

    @ApiOperation("查询用户的角色信息")
    @GetMapping("/getRoles/{userId}")
    public Result<List> getRolesByUserId(@PathVariable Long userId) {
        List<Role> list = roleService.findRolesByUserId(userId);

        return Result.success(list);
    }

    @ApiOperation("分配角色")
    @PreAuthorize("hasAuthority('管理员')")
    @PostMapping("/assignRole")
    public Result<String> assignRole(@RequestBody AssignRoleVo assignRoleVo) {
        String result = null;
        roleService.assignRole(assignRoleVo);
        return Result.success(result);
    }

    @ApiOperation("查看所有角色")
    @PreAuthorize("hasAuthority('管理员')")
    @GetMapping("/getAllRoles")
    public Result<List> getAllRoles() {
        List<Role> list = roleService.list();
        return Result.success(list);
    }

    @ApiOperation("添加角色")
    @PreAuthorize("hasAuthority('管理员')")
    @PostMapping("/addRole")
    public Result<String> addRole(@RequestBody Role role) {
        String result;
        if(roleService.save(role)){
            result = "添加角色成功";
        }else{
            result = "添加角色失败";
        }
        return Result.success(result);
    }

    @ApiOperation("删除角色")
    @PreAuthorize("hasAuthority('管理员')")
    @PostMapping("/deleteRole")
    public Result<String> deleteRole(@RequestBody Role role) {
        String result;
        if(roleService.removeById(role.getId())){
            result = "删除角色成功";
        }else{
            result = "删除角色失败";
        }
        return Result.success(result);
    }

}

package com.cj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cj.entity.User;
import com.cj.entity.Vo.UserQueryVo;
import com.cj.service.UserService;
import com.cj.util.Result;
import com.cj.util.md5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class userController {
    @Autowired
    public UserService userService;

    @ApiOperation("分页查看所有用户")
    @PreAuthorize("hasAuthority('用户')")
    @PostMapping("/getAllUser/{page}/{limit}")
    public Result index(@PathVariable Long page,
                              @PathVariable Long limit,
                              @RequestBody UserQueryVo userQueryVo) {

        Page<User> objectPage = new Page<>(page, limit);

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();

        String username = userQueryVo.getUsername();
        String createTimeStart = userQueryVo.getCreateTimeBegin();
        String createTimeEnd = userQueryVo.getCreateTimeEnd();

        if(StringUtils.isNotEmpty(username)){
            userLambdaQueryWrapper.like(User::getUsername,username);
        }
        if(StringUtils.isNotEmpty(createTimeStart)){
            userLambdaQueryWrapper.ge(User::getCreateTime,createTimeStart);
        }
        if(StringUtils.isNotEmpty(createTimeEnd)){
            userLambdaQueryWrapper.le(User::getCreateTime,createTimeEnd);
        }

        IPage<User> page1 = userService.page(objectPage, userLambdaQueryWrapper);

        return Result.success(page1);
    }

    @ApiOperation("查看所有用户")
    @PreAuthorize("hasAuthority('管理员')")
    @GetMapping("/getAllUser")
    public Result<List> getAllUser() {
        List<User> list = userService.list();
        return Result.success(list);
    }

    @ApiOperation("添加用户")
    @PreAuthorize("hasAuthority('管理员')")
    @PostMapping("/addUser")
    public Result<String> addUser(@RequestBody User user) {
        String result;
        user.setPassword(md5.encode(user.getPassword()));
        if(userService.save(user)){
            result = "添加用户成功";
        }else{
            result = "添加用户失败";
        }
        return Result.success(result);
    }

    @ApiOperation("修改用户")
    @PreAuthorize("hasAuthority('管理员')")
    @PostMapping("/updateUser")
    public Result<String> updateUser(@RequestBody User user) {
        String result;
//        userService.saveOrUpdate(user);
        user.setPassword(null);
        if(userService.updateById(user)){
            result = "修改用户成功";
        }else{
            result = "修改用户失败";
        }
        return Result.success(result);
    }

    @ApiOperation("删除用户")
    @PreAuthorize("hasAuthority('管理员')")
    @PostMapping("/deleteUser")
    public Result<String> deleteUser(@RequestBody User user) {
        String result;
        if(userService.removeById(user.getId())){
            result = "删除用户成功";
        }else{
            result = "删除用户失败";
        }
        return Result.success(result);
    }

}

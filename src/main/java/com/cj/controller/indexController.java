package com.cj.controller;

import com.cj.entity.LoginVo;
import com.cj.entity.User;
import com.cj.service.UserService;
import com.cj.util.Result;
import com.cj.util.md5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Api(tags = "测试接口")
@RestController
@Slf4j
@RequestMapping("/CJSystem")
public class indexController {

    @Autowired
    private UserService userService;

    @GetMapping("test")
    public Result test(){
        int[] a = {1,2,3};
        List ints = Arrays.asList(a);
        System.out.println(ints.size());
        System.out.println(ints.get(0));
        System.out.println(ints.get(1));
        return Result.success("test");
    }

    @ApiOperation("登录接口")
    @PostMapping("login")
    public Result<String> login(@RequestBody LoginVo loginVo){
//        log.info("loginVo: ");
        return Result.success("登录成功");
    }
    @ApiOperation("个人身份信息")
    @GetMapping("info")
    public Result<User> info(){
//        log.info("loginVo: ");
        String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByUserName(username);
        return Result.success(user);
    }


    @ApiOperation("管理员操作")
    @PreAuthorize("hasAuthority('管理员')")
    @GetMapping("/test1")
    public Result<String> test1() {
        log.info("info管理员操作成功");
        log.debug("debug管理员操作成功");
        log.error("error管理员操作成功");
        log.warn("warn管理员操作成功");
        return Result.success("管理员操作成功");
    }

    @ApiOperation("老师操作")
    @PreAuthorize("hasAuthority('老师')")
    @GetMapping("/test2")
    public Result<String> test2() {
        return Result.success("老师操作成功");
    }

    @ApiOperation("学生操作")
    @PreAuthorize("hasAuthority('用户')")
    @GetMapping("/test3")
    public Result<String> test3() {
        return Result.success("学生操作成功");
    }

    /**
     * 只需要输入规定的用户名、密码、姓名即可批量插入50条学生信息
     * 50条数据的用户名和姓名分别是输入的末尾加上1-50数字 密码均为111111
     * @return
     */
    @ApiOperation("批量插入50条学生信息")
    @PreAuthorize("hasAuthority('管理员')")
    @PostMapping("/test4")
    public Result<String> test4(@RequestBody User user) {
        String result;
        List<User> userList = new ArrayList<>();
        for(int i = 0; i < 50; i++){
            User user1 = new User();
            user1.setUsername(user.getUsername() + i);
            user1.setPassword(md5.encode("111111"));
            user1.setRealname(user.getRealname() + i);
            userList.add(user1);
        }
        if(userService.saveBatch(userList)){
            result = "批量插入成功";
        }else{
            result = "批量插入失败";
        }
        return Result.success(result);
    }

    @ApiOperation("测试")
    @PreAuthorize("hasAuthority('用户')")
    @GetMapping("/test5")
    public Result<String> test5() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        System.out.println(principal);

//        return Result.success(principa2);
        return null;
    }

}

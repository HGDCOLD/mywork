package com.cj.controller;

import com.cj.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试servlet接口")
public class MyServletController {

    @PostMapping("/testServlet/test1")
    @ApiOperation("测试1")
    public Result<String> test1() {
        System.out.println("test1 controller");
        return Result.success("test1 controller success");
    }

}

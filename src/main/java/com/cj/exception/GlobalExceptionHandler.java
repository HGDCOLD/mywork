package com.cj.exception;

import com.cj.util.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * spring security异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e) throws AccessDeniedException {
        return Result.fail("没有操作权限");
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public Result error(Exception e) {
//        e.printStackTrace();
//        return Result.fail(e.getMessage());
//    }
}

package com.cj.util;

import lombok.Data;

@Data
public class Result<T> {

    private Integer code;

    private String message;

    private T data;

    public Result() {

    }

    public static <T> Result<T> build(T data,int code,String message) {
        Result<T> result = new Result<>();
        if(data != null){
            result.setData(data);
        }

        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> success() {
        return build(null, 200, "success");
    }
    public static <T> Result<T> success(T data) {
        return build(data, 200, "success");
    }

    public static <T> Result<T> fail() {
        return build(null, 500, "fail");
    }

    public static <T> Result<T> fail(T data) {
        return build(data, 500, "fail");
    }
}

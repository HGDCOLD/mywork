package com.cj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.cj.servlet")
public class SpringbootServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringbootServiceApplication.class, args);
    }
}

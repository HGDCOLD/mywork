package com.cj.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/head/");
        registry.addResourceHandler("/avatar/**").addResourceLocations("file:E:\\idea-project\\myImg/")
               .setCacheControl(CacheControl.maxAge(120, TimeUnit.SECONDS));
    }
}

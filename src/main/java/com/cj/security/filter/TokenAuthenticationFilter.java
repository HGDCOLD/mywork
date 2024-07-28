package com.cj.security.filter;

import com.alibaba.fastjson.JSON;
import com.cj.entity.Role;
import com.cj.entity.User;
import com.cj.service.RoleService;
import com.cj.service.UserService;
import com.cj.util.JwtHelper;
import com.cj.util.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

//    @Autowired
    private RedisTemplate redisTemplate;

//    @Autowired
    private RoleService roleService;

    public TokenAuthenticationFilter(RedisTemplate redisTemplate, RoleService roleService) {
        this.redisTemplate = redisTemplate;
        this.roleService = roleService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if("/CJSystem/login".equals(httpServletRequest.getRequestURI())){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = null;
        String token = httpServletRequest.getHeader("token");
        if(!StringUtils.isEmpty(token)){
            String username = JwtHelper.getUsername(token);
            if(!StringUtils.isEmpty(username)){
                String auths = (String)redisTemplate.opsForValue().get(username);
                List<Map> maps = JSON.parseArray(auths, Map.class);
                List<SimpleGrantedAuthority> authList = new ArrayList<>();
                if(maps == null){
                    Long userId = JwtHelper.getUserId(token);
                    List<Role> rolesByUserId = roleService.findRolesByUserId(userId);
                    for(Role role : rolesByUserId){
                        authList.add(new SimpleGrantedAuthority(role.getRoleName()));
                    }
                    redisTemplate.opsForValue().set(username, JSON.toJSONString(authList),30, TimeUnit.MINUTES);
                    System.out.println("过期了，重新设置");
                }else{
                    for(Map map : maps){
                        authList.add(new SimpleGrantedAuthority(map.get("authority").toString()));
                    }
                }
                authentication = new UsernamePasswordAuthenticationToken(username, null, authList);

            }
        }
        if(authentication != null){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }else{
            httpServletResponse.setStatus(200);
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(Result.fail("token失效")));
        }
    }
}

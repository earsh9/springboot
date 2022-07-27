package com.example.admin.interceptor;

/*
* 设置一个拦截器，监控访问首页、查询sql的次数
* 将其存储到 redis 中并将次数显示到网页中
*
* */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RedisUrlCountInterceptor implements HandlerInterceptor {

    @Autowired
    StringRedisTemplate redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();                   //将请求路径作为 key 保存到 redis 中
        redisTemplate.opsForValue().increment(uri);             //默认每次访问当前 uri 就会计数 +1
        return true;
    }
}

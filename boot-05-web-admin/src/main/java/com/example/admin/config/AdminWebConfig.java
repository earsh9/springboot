package com.example.admin.config;

import com.example.admin.interceptor.LoginInterceptor;
import com.example.admin.interceptor.RedisUrlCountInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*
 * 个性化设置拦截器需拦截的路径
 * 1. 编写一个拦截器实现 HandlerInterceptor 接口   (这里实现的是 LoginInterceptor)
 * 2. 拦截器注册到容器中（实现 WebMvcConfigurer 的 addInterceptors 方法）
 * 3. 指定拦截规则 [如果是拦截所有，静态资源也会被拦截]
 * */

@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    /*
    * 分析 Filter、Interceptor 区别：
    * 二者功能一致，都是拦截器
    * Filter 是 servlet 定义的原生组件。好处：脱离 Spring 应用也能使用
    * Interceptor 是 Spring 定义的接口。可以使用 Spring 的自动装配功能
    * */
    @Autowired
    RedisUrlCountInterceptor redisUrlCountInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")                         //拦截 /** 时，所有请求都会被拦截; 包括静态资源, 所以下述需要放行
                .excludePathPatterns("/","/login","/css/**","/fonts/**","/images/**","/js/**"); //放行的，不管用户登录与否，都可以访问的页面

        /*
        * 添加新的拦截器 redisUrlCountInterceptor，方便进行计数操作
        * 注意这里的 Interceptor 不是 new 出来的而是从容器中取出来的(AutoWired) 因为只有在容器中的组件 Spring 才会解析注解从而注入 redisTemplate
        * */

        registry.addInterceptor(redisUrlCountInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/","/login","/css/**","/fonts/**","/images/**","/js/**");
    }
}

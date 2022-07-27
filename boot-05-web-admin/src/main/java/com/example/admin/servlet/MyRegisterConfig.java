package com.example.admin.servlet;

/*
* 使用 Spring 的方式注入 Servlet、Filter 和 listener(注意此时要想验证其有效性，需将原生Servlet失效。即将@ServletComponentScan注释掉就好)
* 使用 RegistrationBean
* */

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.Registration;
import javax.servlet.Servlet;
import java.util.Arrays;

@Configuration(proxyBeanMethods = true)     //保证每一个对象都是单列的，防止重复堆叠
public class MyRegisterConfig{

    @Bean
    public ServletRegistrationBean<Servlet> myServlet(){
        MyServlet myServlet = new MyServlet();
        return new ServletRegistrationBean<>(myServlet, "/my","/my02");
    }

    @Bean
    public FilterRegistrationBean<Filter> myFiler(){
        MyFilter myFilter = new MyFilter();
//        return new FilterRegistrationBean(myFilter, myServlet());       //设置过滤的 ServletRegistrationBean 为 myServlet，则 myServlet 设置的 urlMapping 即为过滤的路径
        //写法二：直接设置过滤的路径
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>(myFilter);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/my", "/css/*"));
        return filterRegistrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean myListener(){
        MyServletContextListener myServletContextListener = new MyServletContextListener();
        return new ServletListenerRegistrationBean(myServletContextListener);
    }
}

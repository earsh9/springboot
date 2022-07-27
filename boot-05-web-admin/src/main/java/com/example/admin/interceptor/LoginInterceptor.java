package com.example.admin.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器：作登录检查。即所有页面都需登录了才能跳转
 * 1. 配置好拦截器要拦截哪些请求
 * 2. 将这些配置放在容器中
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    /*
    * 目标方法执行之前 实现登录检查逻辑
    * */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("preHandle 拦截的请求路径是{}", requestURI);

        //检查登录逻辑
        HttpSession session = request.getSession();
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            //放行
            return true;
        }
        //拦截住。由于未登录，所以跳转到登录页面
/*        session.setAttribute("msg", "请先登录");
        response.sendRedirect("/");*/
        request.setAttribute("msg", "请先登录");
        request.getRequestDispatcher("/").forward(request, response);
        return false;
    }

    /*
    * 目标方法执行之前
    * */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle 执行{}", modelAndView);
    }

    /*
    * 页面渲染后执行
    * */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion 执行异常", ex);
    }
}

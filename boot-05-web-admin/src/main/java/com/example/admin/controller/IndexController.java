package com.example.admin.controller;

import com.example.admin.bean.Job;
import com.example.admin.bean.User;
import com.example.admin.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class IndexController {

    /*
    * 随便写一个 sql，方便查看 druid 的监控页面
    * */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    JobService jobService;

    /*
    * 使用 mybatis 实现数据查询
    * */
    @ResponseBody
    @GetMapping("/jobs")
    public Job getJobById(@RequestParam("jobId") String jobId){
        return jobService.getJobById(jobId);
    }

    @ResponseBody
    @GetMapping("/sql")
    public String queryFromDb(){
        Long query = jdbcTemplate.queryForObject("select count(*) from jobs", Long.class);
        log.info("记录总数：{}", query);
//        log.info("数据源类型：{}", );
        return query.toString();
    }

    /**
     * 设置登录页面
     */
    @GetMapping(value = {"/", "/login"})
    public String loginPage(){
        return "login";
    }

    /*
    * 登录成功就来到首页
    * 解决页面刷新：即重复请求的情况
    * 让它重定向到 main 页面
    * */
    @PostMapping("/login")
    public String main(User user, HttpSession httpSession, Model model){
        //登录成功，重定向到 main.html，重定向防止表单重复提交
        //将登录成功的用户保存起来
        if(StringUtils.hasLength(user.getUserName()) && "123456".equals(user.getPassword())){
            httpSession.setAttribute("loginUser", user);
            return "redirect:/main.html";
        }else{
            //回到登录页
            model.addAttribute("msg", "账号密码错误");
            return "login";
        }
    }

    @Autowired
    StringRedisTemplate redisTemplate;

    /*
    * 登录成功后，转发到 main 页面，但请求还是登录
    * 一刷新表单会重复提交, 所以搞个 get 请求处理 "/main.html" 请求到 main，这样表单不会重复提交
    * */
    @GetMapping("/main.html")
    public String mainPage(HttpSession session, Model model){
        log.info("当前方法是：{}", "mainPage");
        //前置判断：是否已经登录
/*        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null){
            return "main";
        }else{
            model.addAttribute("msg", "请重新登录:)");
            return "login";
        }*/

        /*
        * 在首页显示 redis 记录的请求次数
        * */
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();

        String s = opsForValue.get("/main.html");
        String s1 = opsForValue.get("/sql");

        model.addAttribute("mainCount", s);
        model.addAttribute("sqlCount", s1);
        //上述无需再判断，拦截器已经做了
        return "main";
    }
}

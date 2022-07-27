package com.example.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.admin.bean.User;
import com.example.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
public class TableController {

    @GetMapping("/basic_table")
    public String basic_table(){
        return "table/basic_table";
    }

    /*
    * 从数据库中查到用户信息后 写回到表格里
    * */
    @Autowired
    UserService userService;

    @GetMapping("/dynamic_table")
    public String dynamic_table(Model model, @RequestParam(value = "pn",defaultValue = "1") Integer pn){

        //表格内容遍历
//        List<User> users = Arrays.asList(new User("lucy", "123456"),
//                new User("mary", "74326"),
//                new User("kki", "483209"),
//                new User("yaMi", "840321"));
//        model.addAttribute("users", users);
        List<User> list = userService.list();
//        model.addAttribute("users", list);

        //自动查出分页查询数据
        Page<User> userPage = new Page<>(pn, 2);         //传入当前页码、每页显示几条数据; 当前页码应该是页面点击以后获取到的

        //分页查询的结果
        Page<User> page = userService.page(userPage, null);//翻页对象、Wrapper
        model.addAttribute("page", page);
        return "table/dynamic_table";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id,
                             @RequestParam(value = "pn", defaultValue = "1") Integer pn,
                             RedirectAttributes ra){

        userService.removeById(id);
        ra.addAttribute("pn", pn);                  //帮助重定向自动携带参数
        return "redirect:/dynamic_table";
    }

    @GetMapping("/editable_table")
    public String editable_table(){
        return "table/editable_table";
    }

    @GetMapping("/pricing_table")
    public String pricing_table(){
        return "table/pricing_table";
    }

    @GetMapping("/responsive_table")
    public String responsive_table(){
        return "table/responsive_table";
    }
}

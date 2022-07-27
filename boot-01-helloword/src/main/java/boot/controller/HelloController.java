package boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/*@ResponseBody 和
@Controller 合并为 RestController*/
@RestController
public class HelloController {


    @RequestMapping("/hello")
    public String hello01(){
        return "hello spring boot 2!";
    }
}

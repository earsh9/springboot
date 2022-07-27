package com.example.admin.controller;

/*
* 文件上传测试
* */

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Controller
public class FormTestController {

    @GetMapping("/form_layouts")
    public String form_layouts(){
        return "form/form_layouts";
    }

    /*
    * 获取上传的信息
    * MultipartFile 会自动封装上传过来的文件
    * */
    @PostMapping("/upload")
    public String upload(@RequestParam("email") String email,
                         @RequestParam("username") String username,
                         @RequestPart("headerImg") MultipartFile headerImg,
                         @RequestPart("photos") MultipartFile[] photos) throws IOException {
        log.info("上传的信息：email={}, username={}, headerImg={}, photos={}",
                email, username, headerImg.getSize(), photos.length);

        if (!headerImg.isEmpty()){
            //保存到文件服务器，此处保存到本地磁盘
            String originalFilename = headerImg.getOriginalFilename();
            headerImg.transferTo(new File("F:\\tingFiles\\" + originalFilename));
        }

        if (photos.length > 0){
            for (MultipartFile photo : photos) {
                if (!photo.isEmpty()){
                    String originalFilename = photo.getOriginalFilename();
                    photo.transferTo(new File("F:\\tingFiles\\cache\\" + originalFilename));
                }
            }
        }

        return "main";              //表单文件上传后跳转回 main 页面
    }
}

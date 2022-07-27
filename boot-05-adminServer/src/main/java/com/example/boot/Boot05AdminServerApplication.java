package com.example.boot;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer                      //开启 admin 监控功能
@SpringBootApplication
public class Boot05AdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Boot05AdminServerApplication.class, args);
    }

}

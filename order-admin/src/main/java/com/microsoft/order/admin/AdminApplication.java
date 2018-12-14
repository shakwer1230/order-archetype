package com.microsoft.order.admin;


import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/13
 * @
 */
@Configuration
@SpringBootApplication
@EnableAdminServer
public class AdminApplication{
    public  static void main(String[]args){
        SpringApplication.run(AdminApplication.class,args);
    }
}

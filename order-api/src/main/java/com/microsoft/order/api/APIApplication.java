package com.microsoft.order.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Repository;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/11/28
 * @
 */
@SpringBootApplication
@ServletComponentScan
//@ComponentScan(value = "com.microsoft.order")
//@EnableAutoConfiguration
//@ImportResource("")// load xml configuration files
public class APIApplication {
    public static void main(String[]args){
        SpringApplication.run(APIApplication.class,args);
    }
}

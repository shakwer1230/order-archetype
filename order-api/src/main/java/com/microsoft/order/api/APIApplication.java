package com.microsoft.order.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/11/28
 * @
 */
@SpringBootApplication
@ServletComponentScan
//@ComponentScan(value = "com.microsoft.order")
public class APIApplication {
    public static void main(String[]args){
        SpringApplication.run(APIApplication.class,args);
    }
}

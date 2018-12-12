package com.microsoft.openapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/12
 * @把 项目打包成war包
 */

@SpringBootApplication
public class OpenApiApplication {
    public static  void  main(String[]args){
        SpringApplication.run(OpenApiApplication.class,args);
    }
}
//springboot 项目打war包
//@SpringBootApplication
//public class OpenApiApplication extends SpringBootServletInitializer {
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(OpenApiApplication.class);
//    }
//    public static  void  main(String[]args){
//        SpringApplication.run(OpenApiApplication.class,args);
//    }
//}

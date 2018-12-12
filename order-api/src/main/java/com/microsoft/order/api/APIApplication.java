package com.microsoft.order.api;

import com.microsoft.order.api.config.MyListener;
import javafx.application.Application;
import javafx.scene.Parent;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
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
//        System.setProperty("spring.devtools.restart.enabled","true");

//        SpringApplication application=new SpringApplicationBuilder().sources(Parent.class).child(APIApplication.class).bannerMode(Banner.Mode.CONSOLE).build();
//        SpringApplication.run(APIApplication.class,args);

        SpringApplication application=new SpringApplication(APIApplication.class);
        application.addListeners(new MyListener());
        application.run(args);
    }
}

package com.microsoft.order.api;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/13
 * @
 */
@SpringBootApplication
public class ExitCodeApplication {
    @Bean
    public ExitCodeGenerator exitCodeGenerator(){
        return ()->42;
    }

    public  static void main(String[]args){
        System.exit(SpringApplication.exit(SpringApplication.run(ExitCodeApplication.class,args)));
    }
}

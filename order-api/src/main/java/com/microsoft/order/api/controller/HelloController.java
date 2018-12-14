package com.microsoft.order.api.controller;

import com.microsoft.order.api.config.MyNumber;
import com.microsoft.order.api.service.UserJackJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/11/28
 * @
 */
@RestController
@RequestMapping(value = "/welcome")
public class HelloController {
    @Autowired
    private MyNumber number;
    @Autowired
    UserJackJson userJson;

    Logger logger= LoggerFactory.getLogger(HelloController.class);
    @GetMapping("/hello")
    @ResponseBody
    public String welcome(){
//        MyNumber number=new MyNumber();
//        System.out.println(number.getBignumber());
        logger.error("This is a testing logger");
        userJson.jackJsonUserSerialize();
        userJson.jackJsonUserDeserialize();
        userJson.jackJsonUserRead();
        return "Welcome to Spring boot "+number.getIntnumber();
    }

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping("/shut")
    public void shutDown(){
        SpringApplication.exit(applicationContext,()->42);
    }
}

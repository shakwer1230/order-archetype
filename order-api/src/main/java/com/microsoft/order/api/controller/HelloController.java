package com.microsoft.order.api.controller;

import com.microsoft.order.api.config.MyNumber;
import com.microsoft.order.api.service.PersonGson;
import com.microsoft.order.api.service.UserJackJson;
import com.shakwer.HelloService;
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

    @Autowired
    PersonGson personGson;

    //注入自定义starter内逻辑
    @Autowired
    HelloService helloService;

    Logger logger= LoggerFactory.getLogger(HelloController.class);
    @GetMapping("/hello")
    @ResponseBody
    public String welcome(){
//        MyNumber number=new MyNumber();
//        System.out.println(number.getBignumber());
        logger.error("This is a testing logger");
//        userJson.jackJsonUserSerialize();
//        userJson.jackJsonUserDeserialize();
//        userJson.jackJsonUserRead();

        personGson.gsonPersonSerialize();
        personGson.gsonPersonDeSerialize();
        return "Welcome to Spring boot "+number.getIntnumber();
    }

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping("/shut")
    public void shutDown(){
        SpringApplication.exit(applicationContext,()->42);
    }

    /**
     * 测试访问地址/hello
     * @return 格式化字符串
     */
    @RequestMapping(value = "/sayhello")
    public String sayHello()
    {
        return helloService.sayHello();
    }
}

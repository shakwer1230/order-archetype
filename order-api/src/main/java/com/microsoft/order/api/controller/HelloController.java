package com.microsoft.order.api.controller;

import org.springframework.stereotype.Controller;
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
    @GetMapping("/hello")
    @ResponseBody
    public String welcome(){
        return "Welcome to Spring boot";
    }
}

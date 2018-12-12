package com.microsoft.openapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/12
 * @
 */
@RestController
@RequestMapping("/")
public class HelloController {

    @RequestMapping(value = "greet", method = RequestMethod.GET)
    public String greeting(){
        return "Hello ,Sptingboot";
    }
}

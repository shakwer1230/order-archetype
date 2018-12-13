package com.microsoft.order.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/12
 * @
 * Accessing Application Arguments
 */
@Component
public class MyArgumentsBean {
    @Autowired
    public  MyArgumentsBean(ApplicationArguments arguments){
        boolean debugger=arguments.containsOption("debug");
        List<String> files=arguments.getNonOptionArgs();
        System.out.println(debugger);
        System.out.println(files);
    }

}

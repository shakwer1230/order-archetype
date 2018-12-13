package com.microsoft.order.api.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.*;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/12
 * @
 * 1、ApplicationStartingEvent 框架启动 注册初始化就执行，在其他的进程前运行
 * 2、ApplicationEnvironmentPreparedEvent 上下文环境创建前运行 context 没有创建完成 bean 也没有加载进去
 * 3、ApplicationPreparedEvent 在服务启动前 context 创建完成  bean暂未成功加载 运行
 * 4、ApplicationReadyEvent 服务启动后 运行
 * 5、ApplicationFailedEvent：springboot框架启动失败。
 */




//public class MyListener implements ApplicationListener<ApplicationStartingEvent> {
//
//    @Override
//    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
//        SpringApplication application=applicationStartingEvent.getSpringApplication();
//        System.out.println("##########MyListener ApplicationStartingEvent 启动 .....##########");
//    }
//}
//
//public class MyListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
//    @Override
//    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
//        SpringApplication application=applicationEnvironmentPreparedEvent.getSpringApplication();
//        System.out.println("##########MyListener ApplicationEnvironmentPreparedEvent 启动 .....##########");
//    }
//}



//public class MyListener implements ApplicationListener<ApplicationPreparedEvent> {
//
//    @Override
//    public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
//        SpringApplication application=applicationPreparedEvent.getSpringApplication();
//        System.out.println("##########MyListener ApplicationPreparedEvent 启动 .....##########");
//    }
//}

public class MyListener implements ApplicationListener<ApplicationReadyEvent> ,Ordered{

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationPreparedEvent) {
        SpringApplication application=applicationPreparedEvent.getSpringApplication();
        System.out.println("##########MyListener ApplicationReadyEvent 启动 .....##########");
    }

    @Override
    public int getOrder() {
        return 1;
    }
}

//public class MyListener implements ApplicationListener<ApplicationFailedEvent> {
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent applicationPreparedEvent) {
//        SpringApplication application=applicationPreparedEvent.getSpringApplication();
//        System.out.println("##########MyListener ApplicationReadyEvent 启动 .....##########");
//    }
//}
package com.microsoft.order.api.config;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/13
 * @
 */
@Component
public class MyAnnotationPreDestroy {
    @PreDestroy
    public void destory() {
        System.out.println("我被销毁了、、、、、我是用的@PreDestory的方式、、、、、、");
        System.out.println("我被销毁了、、、、、我是用的@PreDestory的方式、、、、、、");
    }
}

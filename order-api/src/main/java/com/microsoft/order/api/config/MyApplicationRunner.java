package com.microsoft.order.api.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/13
 * @
 * Applicaltion.run()方法执行完后 运行此方法  同CommandLineRunner
 */
@Component
public class MyApplicationRunner implements ApplicationRunner,Ordered {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("ApplicationRunner: Application running...");
    }

    @Override
    public int getOrder() {
        return 100;
    }
}

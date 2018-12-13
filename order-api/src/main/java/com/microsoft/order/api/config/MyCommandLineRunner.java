package com.microsoft.order.api.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/13
 * @Applicaltion.run()方法执行完后 运行此方法 同ApplicationRunner
 */
@Component
public class MyCommandLineRunner implements CommandLineRunner ,Ordered {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner: Application running...");
    }

    //实现Ordered 接口  决定执行顺序
    @Override
    public int getOrder() {
        return 10;
    }
}

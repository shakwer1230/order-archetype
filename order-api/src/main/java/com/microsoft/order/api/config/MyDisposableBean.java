package com.microsoft.order.api.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/13
 * @
 */
@Component
public class MyDisposableBean implements DisposableBean,ExitCodeGenerator{
    @Override
    public void destroy() throws Exception {
        System.out.println("<<<<<<<<<<<MyDisposableBean 我被销毁了......................>>>>>>>>>>>>>>>");
        System.out.println("<<<<<<<<<<<MyDisposableBean 我被销毁了......................>>>>>>>>>>>>>>>");
    }

    @Override
    public int getExitCode() {
       return 0;
    }
}

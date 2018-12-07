package com.microsoft.order.aspect;

import com.microsoft.order.annotation.ReadOnlyConnectionAnnotation;
import com.microsoft.order.config.DbContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @ 强制只读从库
 * @Author:lvxiaoke
 * @Date: 2018/12/6
 * @
 */
@Aspect
@Component
public class ReadOnlyConnectionAspect implements Ordered{
    Logger log= LoggerFactory.getLogger(ReadOnlyConnectionAspect.class);

    /**
     * 数据库配置文件的默认数据源名称
     */
    @Value("${dbconfig.slaveDataName}")
    private  String slaveDataName;

    @Before("@annotation(readOnlyConnection)")
    public void doBefore(JoinPoint joinPoint, ReadOnlyConnectionAnnotation readOnlyConnection){
        if (log.isInfoEnabled()) {
            log.info("set database connection to read only");
        }
        DbContextHolder.setDbType(slaveDataName);
    }

    @After("@annotation(readOnlyConnection)")
    public void after(JoinPoint joinPoint, ReadOnlyConnectionAnnotation readOnlyConnection){
        if (log.isInfoEnabled()) {
            log.info("remove database connection to read only");
        }
        DbContextHolder.clearDbType();
    }
    @Override
    public int getOrder() {
        return 0;
    }
}

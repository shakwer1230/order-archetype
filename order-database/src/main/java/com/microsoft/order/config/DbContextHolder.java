package com.microsoft.order.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/5
 * @
 */
public class DbContextHolder {
    private static   Logger log = LoggerFactory.getLogger(DbContextHolder.class);

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 默认数据源
     */
    @Value("${dbconfig.defaultDataName}")
    private static String defaultDbSource ="";

    public static void setDbType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }
    public static String getDbType() {
        if(contextHolder.get()==null){
            return defaultDbSource;
        }
        return contextHolder.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clearDbType() {
        contextHolder.remove();
        if(log.isInfoEnabled()){
            log.info("clean DbContextHolder database key");
        }
    }
}

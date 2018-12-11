package com.microsoft.order.common.config.email;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/7
 * @
 */
@ConfigurationProperties(prefix="spring.mail")
@Data
public class EmailConfig {
    private String username;
    private String password;
    private String host;
    private String defaultEncoding;
}

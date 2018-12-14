package com.microsoft.order.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/14
 * @
 */
@Component
@ConfigurationProperties(prefix = "my")
@Data
public class MyNumber {
    public String secret;

    public Long bignumber;

    public Integer intnumber;
}

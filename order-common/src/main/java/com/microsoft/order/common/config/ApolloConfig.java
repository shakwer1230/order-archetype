package com.microsoft.order.common.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableApolloConfig(value = {"aos.fang_order"}, order = 1)
public class ApolloConfig {
    @Configuration
    @EnableApolloConfig(order = 2)
    public class ApolloApplicationConfig {

    }
}
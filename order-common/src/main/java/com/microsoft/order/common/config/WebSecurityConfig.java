package com.microsoft.order.common.config;//package com.fang.order.common.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
///**
// * @author: 陈偲
// * @date: 2018/4/26 16:51
// * @version: 1.0
// */
//@EnableWebSecurity
//public  class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("SuperInspector").password("whosyourdaddy").roles("ADMIN").build());
//        return manager;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.antMatcher("/actuator/**")
//                .authorizeRequests()
//                .anyRequest().hasRole("ADMIN")
//                .and()
//                .httpBasic();
//    }
//}

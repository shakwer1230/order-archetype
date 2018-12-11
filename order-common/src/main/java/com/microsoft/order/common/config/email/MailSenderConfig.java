package com.microsoft.order.common.config.email;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/7
 * @
 */
@Configuration
@EnableConfigurationProperties(EmailConfig.class)
public class MailSenderConfig {
    @Autowired
    private EmailConfig emailConfig;
    @Bean
    public JavaMailSender javaMailSender() {
        val mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailConfig!=null?emailConfig.getHost():"");
        mailSender.setDefaultEncoding(emailConfig.getDefaultEncoding());
        mailSender.setUsername(emailConfig.getUsername());
        mailSender.setPassword(emailConfig.getPassword());
        //加认证机制
        val javaMailProperties =new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.smtp.timeout", 5000);
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }
}

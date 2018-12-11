package com.microsoft.order.common.tools.mail;

import com.microsoft.order.common.tools.data.text.ToolRegex;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import  org.springframework.mail.javamail.*;
import javax.activation.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/7
 * @
 */
public class EmailService {
    private Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.profiles.active}")
    private String env;



    /**
     * 发送邮件通用底层方法
     */
    @Async
    public void sendSimpleMail(ArrayList<String> to , String subject , String content) {
        val emailAddress = emailAddressFilter(to);
        val environment = envZH();
        val message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo("${emailAddress}");
        message.setSubject("$subject[$environment]") ;
        message.setText(content);
        try {
            mailSender.send(message);
            log.debug("发送简单邮件");
        } catch (Exception e ) {
            log.error("发邮件异常:" + e.getLocalizedMessage());
            //throw Exception("发邮件错误")
        }
    }


    /**
     * 根据环境返回对应的中文名称
     */
    private String envZH() {
        String envZH="";
        switch (env){
            case "dev":envZH="本地";break;
            case "test":envZH="测试";break;
            case "uat":envZH="仿真";break;
            case "pro":envZH="正式";break;
            default:envZH="";
        }
        return envZH;
    }
    /**
     * 发送带附件的邮件
     */
    @Async
    public void sendAttachmentsMail(List<String> to , String subject , String content, String fileName , DataSource file ) {
        val emailAddress = emailAddressFilter(to);
        val environment = envZH();
        try {
            val message = mailSender.createMimeMessage();
            val helper = new MimeMessageHelper(message, true);
            helper.setFrom(username);
            helper.setTo(emailAddress);
            helper.setSubject("$subject[$environment]");
            helper.setText(content, true);
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            log.debug("带附件的邮件已经发送。");
        } catch (Exception e) {
            log.error("发送带附件的邮件时发生异常！", e);
        }


    }

    /**
     * 过滤非邮件地址的字符串
     */
    private  String[] emailAddressFilter(List<String> emailArray) {
        val newEmailAddress =emailArray.stream().filter(o->ToolRegex.isEmail(o)&&o!=null&&o.length()!=0).collect(Collectors.toList ());
        val array = new String[(newEmailAddress.size())];
        return newEmailAddress.toArray(array);
    }

}

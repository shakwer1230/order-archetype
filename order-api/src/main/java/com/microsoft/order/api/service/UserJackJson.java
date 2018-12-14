package com.microsoft.order.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.order.api.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/14
 * @
 */
@Service
public class UserJackJson {
    Logger logger= LoggerFactory.getLogger(UserJackJson.class);

    /**
     * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
     ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
     writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
     writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
     writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
     writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
     */
    public void jackJsonUserSerialize() {

        User user = new User();
        user.setName("小民");
        user.setEmail("xiaomin@sina.com");
        user.setAge(20);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            user.setBirthday(dateFormat.parse("1996-10-01"));

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(user);

            System.out.println(json);

            List<User> users = new ArrayList<User>();
            users.add(user);
            String jsonlist = mapper.writeValueAsString(users);
            System.out.println(jsonlist);
        } catch (ParseException pe) {
            logger.error("日期转换信息失败："+pe.toString());
        } catch (IOException ie) {
            logger.error("JackJson 序列化用户信息失败："+ie.getMessage());
        }
    }

    /**
     * 反序列化
     */
    public void jackJsonUserDeserialize(){
        String json = "{\"userName\":\"小民\",\"age\":20,\"birthday\":844099200000,\"email\":\"xiaomin@sina.com\"}";
        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(json, User.class);
            System.out.println(user);
        }catch (IOException ie){
            logger.error("JackJson 反序列化用户信息失败："+ie.getMessage());
        }
    }

    /**
     * 查找json某个节点
     */
    public void jackJsonUserRead(){
        String json = "{\"userName\":\"小民\",\"age\":20,\"birthday\":844099200000,\"email\":\"xiaomin@sina.com\"}";
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode=mapper.readTree(json);
            JsonNode node=jsonNode.get("age");
            String result=mapper.writeValueAsString (node);
            System.out.println("age is "+result);
        }catch (IOException ie){
            logger.error("jackjson查找节点异常："+ie.getMessage());
        }

    }
}

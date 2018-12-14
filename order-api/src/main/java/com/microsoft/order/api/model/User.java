package com.microsoft.order.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/14
 * @
 */
@Data
public class User {
    @JsonProperty("userName")
    public String name;
    //不参与json序列化
    @JsonIgnore
    public Integer age;
    @JsonFormat(pattern = "yyyy年MM月dd日")
    public Date birthday;
    @JsonFormat(pattern = "email")
    public String email;
}

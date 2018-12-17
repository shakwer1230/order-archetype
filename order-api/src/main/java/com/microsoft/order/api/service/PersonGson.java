package com.microsoft.order.api.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.microsoft.order.api.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/17
 * @
 */
@Service
public class PersonGson {
    public void gsonPersonSerialize(){
        Person person=new Person();
        person.setName("小明");
        person.setAge(19);
        person.setHobby("钓鱼");

        List<Person> personList=new ArrayList<>();
        personList.add(person);
        //方式一
        Gson gson=new Gson();

        System.out.println(gson.toJson(person));
        System.out.println(gson.toJson(personList));

        //方式二
        Gson gson2=new GsonBuilder()
                        .setLenient() //json宽松
                        .enableComplexMapKeySerialization()//支持map的key为复杂对象的形式
                        .serializeNulls() //只能null
                        .setPrettyPrinting()//调教格式
                        .disableHtmlEscaping()//默认是Gson把html转义
                        .create();

        System.out.println(gson2.toJson(person));
        System.out.println(gson2.toJson(personList));

    }
    public void gsonPersonDeSerialize(){
        String jsonStr = "{\"name\":\"栗霖\",\"age\":\"18\"}";
        Gson gson=new GsonBuilder().create();
        Person person=gson.fromJson(jsonStr,Person.class);
        System.out.println(person);


        String listJsonStr = "[{\"name\":\"栗霖\",\"age\":\"18\"},{\"name\":\"栗霖之雨\",\"age\":\"18\"}]";
        List<Person>personList=gson.fromJson(listJsonStr,new TypeToken<ArrayList<Person>>(){}.getType());
        System.out.println(personList);
    }
}

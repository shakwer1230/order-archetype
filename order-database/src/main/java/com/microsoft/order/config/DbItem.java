package com.microsoft.order.config;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.microsoft.order.common.tools.data.text.ToolJson;
import javafx.util.Pair;
import lombok.Data;
import lombok.val;
import org.springframework.context.annotation.Bean;

import java.io.*;
import java.util.Map;

/**
 * @
 * @Author:lvxiaoke
 * @Date: 2018/12/5
 * @
 */
public class DbItem {
    /**
     *
     */
    @SerializedName("DBname")
    //        @JSONField(name = "DBname")
            String dbName;

    /**
     *
     */
    @SerializedName("DBtype")
    //        @JSONField(name = "DBtype")
            String dbType;

    /**
     *
     */
    @SerializedName("DBaddr")
    //        @JSONField(name = "DBaddr")
            String dbAddr;

    /**
     *
     */
    @SerializedName("DBport")
    //        @JSONField(name = "DBport")
            String dbPort;

    /**
     *
     */
    @SerializedName("DBusertype")
    //        @JSONField(name = "DBusertype")
            String dbUserType;

    /**
     *
     */
    @SerializedName("DBusername")
    //        @JSONField(name = "DBusername")
            String dbUserName;

    /**
     *
     */
    @SerializedName("DBpw")
    //        @JSONField(name = "DBpw")
            String dbPassword;


    public static Map<String, DbItem> getDbItemMap(String location) throws Exception {
        if (location == null || location == "") {
            throw new IllegalArgumentException("$location 为空");
        }
        val configFile = new File(location);
        /**文件不存在*/
        if (!configFile.exists()) {
            throw new FileNotFoundException("$location 不存在");
        }
        String text="";
        try {
            val reader= new FileReader(configFile);
            char []buffer=new char[1024];
            int num=0;
            while ((num=reader.read(buffer))!=-1){
                text+=new String(buffer,0,num);
            }
            //    val config = JSON.parseObject(text,object : TypeReference<Map<String,DbItem>>() {})
            return ToolJson.jsonToModel(text, new TypeToken<Map<String, DbItem >>(){}.getType());
        }catch (IOException e){
            throw  e;
        }
    }

    /**
     * 获取驱动名称和jdbc url
     *
     * @return pair：第一个为driver名称  第二个为jdbc url
     */
    public Pair<String, String> getDriverAndUrl() throws Exception {
        val lowerDbType = dbType.toLowerCase();
        Pair<String, String> driver = null;
        switch (lowerDbType) {
            case "mysql":
                driver = new Pair("com.mysql.jdbc.Driver",
                        "jdbc:mysql://$dbAddr:$dbPort/$dbName?useUnicode=true&characterEncoding=utf8&useSSL=false");
                break;
            case "oracle":
                driver = new Pair("oracle.jdbc.driver.OracleDriver",
                        "jdbc:oracle:thin:@$dbAddr:$dbPort:$dbName"); break;
            case "sqlserver":
                driver = new Pair("com.microsoft.sqlserver.jdbc.SQLServerDriver",
                        "jdbc:sqlserver://$dbAddr:$dbPort;DatabaseName=$dbName");break;
            default:
                throw new Exception("not support jdbc driver");
        }
        return driver;
    }
}

package com.microsoft.order.common.tools.http;




import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 签名工具
 * @author: 陈偲
 * @date: 2017/12/6 17:54
 * @version: 1.0
 */
public class ToolVerifycode {

    /**
     * 请求md5加盐
     *
     * @param params
     * @param saltkey
     * @param saltValue
     * @param sign
     * @return
     */
    public static Map<String, String> coded(Map<String, String> params, String saltkey, String saltValue, String sign) {
//        MapUtils
        if (params.isEmpty()) {
            throw new IllegalArgumentException("参数不能为空");
        }
        SortedMap<String, String> sortedMap = new TreeMap<>(params);
        String waitJoint = "";

        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            waitJoint += entry.getKey() + "=" + entry.getValue() + "&";
        }
        String waitSign = waitJoint + saltkey + "=" + saltValue;

        String md5Hex = DigestUtils.md5Hex(waitSign);
        sortedMap.put(sign, md5Hex);
        return sortedMap;
    }

    public static String coded(String params, String saltkey, String saltValue) {

        Map<String, String> map = new HashMap<String, String>(20);
        for (String param : params.split("&")) {
            String[] pkv = param.split("=");
            map.put(pkv[0], pkv[1]);
        }

        SortedMap<String, String> sortedMap = new TreeMap<>(map);
        String waitJoint = "";

        for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
            waitJoint += entry.getKey() + "=" + entry.getValue() + "&";
        }

        String waitSign = waitJoint + saltkey + "=" + saltValue;

        String md5Hex = DigestUtils.md5Hex(waitSign);
        return md5Hex;
    }
}


package com.microsoft.order.common.tools.http;

import org.apache.commons.codec.digest.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangxiangyang
 *
 */
public class ToolSign {

    /**
     * 生成签名
     *
     * @param params   待签名的参数
     * @param salkKey  密钥名
     * @param salValue 密钥值 （必须为16位）
     * @return 签名sign
     * @throws Exception
     */
    public static String getSign(String params, String salkKey, String salValue) throws Exception {

        if (params == null || salkKey == null || salValue == null) {
            return null;
        }

        String waitSign = params + "&" + salkKey + "=" + salValue;
        String md5Sign = DigestUtils.md5Hex(waitSign);

        SimpleDateFormat dformat = new SimpleDateFormat("yyyyMMddHHmmss");
        return ToolAESUtil.aesEncrypt(md5Sign + "_" + dformat.format(new Date()), salValue);

    }
}

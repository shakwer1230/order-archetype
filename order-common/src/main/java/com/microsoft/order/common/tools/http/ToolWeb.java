package com.microsoft.order.common.tools.http;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @Author 王志学
 * @Data 2017/12/11
 */
public class ToolWeb {
    private static final Logger logger = LoggerFactory.getLogger(ToolWeb.class);
    private static final String UNKNOWN = "unknown";
    /***
     * 获取URI的路径,如路径为http://www.babasport.com/action/post.htm?method=add, 得到的值为"/action/post.htm"
     * @param request
     * @return
     */
    public static final String getRequestURI(HttpServletRequest request){
        return request.getRequestURI();
    }
    /**
     * 获取完整请求路径(含内容路径及请求参数)
     * @param request
     * @return
     */
    public static final String getRequestURIWithParam(HttpServletRequest request){
        return getRequestURI(request) + (request.getQueryString() == null ? "" : "?"+ request.getQueryString());
    }

    /**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    public static final String getBodyString(final ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream=request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


    /**
     * 获取客户端的真实ip
     *
     * @param request
     * @return
     */
    private static final String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    /**
     * 获取当前登录的用户登录IP地址。
     *
     * @return 当前登录的用户登录IP地址
     */
    public static final String currentUserIp() {
        try {
            //多线程下 报错 java.lang.NullPointerException
            if(RequestContextHolder.getRequestAttributes()==null){
                return "unknow";
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if(request==null){
                return "unkonw";
            }
            return getIpAddress(request);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error(Throwables.getStackTraceAsString(e));
            return "unknown";
        }
    }

    /**
     * 获取当前登录的用户登录IP地址。
     *
     * @return 当前登录的用户登录IP地址
     */
    public static final String currentUserIp(HttpServletRequest request) {
        try {
            return getIpAddress(request);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(Throwables.getStackTraceAsString(e));
            return "unknown";
        }
    }
}

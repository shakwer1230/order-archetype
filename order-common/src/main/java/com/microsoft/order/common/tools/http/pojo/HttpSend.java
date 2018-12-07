package com.microsoft.order.common.tools.http.pojo;

import com.fang.order.common.tools.constant.StrConst;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-20 13:50
 */
public class HttpSend {
    /**
     * 地址
     */
    private String url;
    /**
     * 传入的参数
     */
    private Map<String, Object> params;
    /**
     * header列表
     */
    private Map<String, Object> headers;
    /**
     * 访问方法
     */
    private HttpMethod method;
    /**
     * 编码
     */
    private Charset charset;
    /**
     * 是否需要返回header列表
     */
    private Boolean needReceiveHeaders;

    /**
     * 传入的json参数串（content-type=application/json时使用）
     */
    private String jsonParams;

    public HttpSend(String url) {
        init();
        this.url = url;
    }

    public HttpSend(String url, Map<String, Object> params) {
        init();
        this.url = url;
        this.params = params;
    }

    public HttpSend(String url, Map<String, Object> params, HttpMethod method) {
        init();
        this.url = url;
        this.params = params;
        this.method = method;
    }

    public HttpSend(String url, String jsonParams) {
        init();
        this.url = url;
        this.jsonParams = jsonParams;
    }

    public static HttpSend of(String url) {
        return new HttpSend(url);
    }

    public static HttpSend of(String url, Map<String, Object> params) {
        return new HttpSend(url, params);
    }

    public static HttpSend of(String url, Map<String, Object> params, HttpMethod method) {
        return new HttpSend(url, params, method);
    }

    public static HttpSend of(String url, String jsonParams) {
        return new HttpSend(url, jsonParams);
    }

    /**
     * 初始化
     */
    private void init() {
        method = HttpMethod.GET;
        charset = StrConst.DEFAULT_CHARSET;
        needReceiveHeaders = false;
    }

    public String getUrl() {
        return url;
    }

    public HttpSend setUrl(String url) {
        this.url = url;
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public HttpSend setParams(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public HttpSend setHeaders(Map<String, Object> headers) {
        this.headers = headers;
        return this;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpSend setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public Charset getCharset() {
        return charset;
    }

    public HttpSend setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public Boolean getNeedReceiveHeaders() {
        return needReceiveHeaders;
    }

    public HttpSend setNeedReceiveHeaders(Boolean needReceiveHeaders) {
        this.needReceiveHeaders = needReceiveHeaders;
        return this;
    }

    public String getJsonParams() {
        return jsonParams;
    }

    public HttpSend setJsonParams(String jsonParams) {
        this.jsonParams = jsonParams;
        return this;
    }
}

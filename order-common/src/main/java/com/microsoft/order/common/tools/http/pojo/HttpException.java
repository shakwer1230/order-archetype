package com.microsoft.order.common.tools.http.pojo;

/**
 * @author SeanDragon
 * <p>
 * Create By 2017-07-20 15:57
 */
public class HttpException extends Exception {
    private com.fang.order.common.tools.http.pojo.HttpSend httpSend;
    private com.fang.order.common.tools.http.pojo.HttpReceive httpReceive;

    public HttpException(String msg) {
        super(msg);
    }

    public HttpException(Exception e) {
        super(e);
    }

    public HttpException(String msg, Exception e) {
        super(msg, e);
    }

    public HttpException(String msg, com.fang.order.common.tools.http.pojo.HttpSend httpSend, com.fang.order.common.tools.http.pojo.HttpReceive httpReceive) {
        super(msg);
        this.httpSend = httpSend;
        this.httpReceive = httpReceive;
    }

    @Override
    public String getLocalizedMessage() {
        return super.getMessage().concat("\r\nHTTP_SEND:\t" + httpSend.toString().concat("\r\nHTTP_RECEIVE:\t" + httpReceive.toString()));
    }
}

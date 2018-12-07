package com.microsoft.order.common.web.response;

/**
 * @author 周松柏  zhousongbai@fang.com
 * @date 2018-04-17 11:29
 **/
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS("1"),
    /**
     * 失败
     */
    FAIL("-99"),
    /**
     * 未认证（签名错误）
     */
    UNAUTHORIZED("401"),
    /**
     * 接口不存在
     */
    NOT_FOUND("404"),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR("500"),
    /**
     * 参数错误
     */
    ParameterError("300"),
    /**
     * 限流启动
     */
    LIMITER("600");

    public String code;

    ResultCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
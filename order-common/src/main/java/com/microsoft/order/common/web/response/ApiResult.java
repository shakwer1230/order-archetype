package com.microsoft.order.common.web.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@ApiModel(value = "ApiResult")
/**
 * FAILURE 0 SUCCESS 1
 */
public final class ApiResult<T> implements Serializable {

    @JsonIgnore
    private static final String DEFAULT_SYSTEM_ERROR_MESSAGE_CODE = "101";
    @JsonIgnore
    private static final String DEFAULT_SYSTEM_ERROR_MESSAGE = "系统异常,请稍后重试";

    @Value("${spring.profiles.active}")
    private String env;

    public enum CodeEnum {
        /**
         * 失败
         */
        FAILURE("0"),

        /**
         * 成功
         */
        SUCCESS("1");

        public final String code;

        CodeEnum(String code) {
            this.code = code;
        }

    }

    public enum ErrorCodeEnum {
        /**
         * 失败
         */
        FAILURE("0"),
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
         * 参数异常
         */
        PARAMETER_ERROR("300"),
        /**
         * 程序已处理过重复调用了
         */
        REPEATCALL_ERROR("600");

        public final String code;

        private ErrorCodeEnum(String code) {
            this.code = code;
        }

    }

    //@JsonInclude(JsonInclude.Include.ALWAYS)
    private String message = CodeEnum.SUCCESS.name();

    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorCode;

    /**
     * 状态码
     */
    //@JsonInclude(JsonInclude.Include.ALWAYS)
    @ApiModelProperty(value = "是否成功0-否，1-成功", required = true)
    private String code = CodeEnum.SUCCESS.code;

    /**
     * 正确结果
     */
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "接口返回的用于承装map的对象", required = false)
    private T data;

    @ApiModelProperty(value = "总数量", required = false)
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private Long total;

    @ApiModelProperty(value = "总页数")
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalPage;

    public String getCode() {
        return code;
    }

    public ApiResult<T> error() {
        this.code = CodeEnum.FAILURE.code;
        this.message = CodeEnum.FAILURE.name();
        return this;
    }

    public ApiResult<T> addErrorMessage(String errorMessage) {
        error();
        if (!StringUtils.isEmpty(errorMessage)) {

            this.errorCode = DEFAULT_SYSTEM_ERROR_MESSAGE_CODE;
            this.message = DEFAULT_SYSTEM_ERROR_MESSAGE;
        }
        this.data = null;
        return this;
    }

    public ApiResult<T> addErrorMessageWithData(String errorMessage) {
        error();
        this.message = errorMessage;
        return this;
    }

    public ApiResult<T> addErrorMessageWithData(String errorMessage, String errorCode) {
        error();
        this.message = errorMessage;
        this.errorCode = errorCode;
        return this;
    }

    public T getData() {
        return data;
    }

    public ApiResult<T> addData(T value) {
        this.data = value;
        return this;
    }

    public ApiResult() {
        super();
    }

    public ApiResult(T value) {
        this();
        this.data = value;
    }

    public String getMessage() {
        if ("pro".equals(env)) {
            return message;
        } else {
            return "[订单]" + message;
        }
    }

    public ApiResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public ApiResult<T> setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ApiResult<T> fromServiceResult(ServiceResult<T> serviceResult) {
        if (serviceResult.getSuccess()) {
            return this.addData(serviceResult.getData());
        } else {
            return this.addErrorMessage(serviceResult.getErrorMessage());
        }
    }


    public Long getTotal() {
        return total;
    }

    public ApiResult<T> setTotal(Long total) {
        this.total = total;
        return this;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public ApiResult<T> setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public boolean isSuccess() {
        return this.code.equals(CodeEnum.SUCCESS.code);
    }


}
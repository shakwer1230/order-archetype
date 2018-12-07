package com.microsoft.order.common.web.response;


/**
 * @author 周松柏  zhousongbai@fang.com
 * @date 2018-04-17 11:30
 **/
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static ApiResult<String> successful() {
        return new ApiResult<String>()
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .addData("");
    }

    public static <T> ApiResult<T> successful(T data) {
        return new ApiResult<T>()
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .addData(data);
    }


    public static ApiResult<String> fail(String errorMsg) {
        return new ApiResult<String>()
                .error()
                .setErrorCode(ApiResult.ErrorCodeEnum.FAILURE.code)
                .setMessage(errorMsg)
                .addData("");
    }

//    public static ApiResult<String> fail(String errorMsg, String errorCode) {
//        return new ApiResult<String>()
//                .error()
//                .setErrorCode(errorCode)
//                .setMessage(errorMsg)
//                .addData("");
//    }

    public static <T> ApiResult<T> fail(T obj, String errorMsg) {
        return new ApiResult<T>()
                .error()
                .setErrorCode(ApiResult.ErrorCodeEnum.FAILURE.code)
                .setMessage(errorMsg)
                .addData(obj);
    }

    public static <T> ApiResult<T> fail(T obj, String errorMsg, String errorCode) {
        return new ApiResult<T>()
                .error()
                .setErrorCode(errorCode)
                .setMessage(errorMsg)
                .addData(obj);
    }
}
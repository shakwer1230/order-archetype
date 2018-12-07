package com.microsoft.order.common.web.response;

public class ExecuteResult<ReturnType> {
    private boolean exeSuccess = false;
    private String exeMessage = "未知错误";
    private Integer errorCode = 0;
    private ReturnType Result;

    public boolean isExeSuccess() {
        return exeSuccess;
    }

    public void setExeSuccess(boolean exeSuccess) {
        this.exeSuccess = exeSuccess;
    }

    public String getExeMessage() {
        return exeMessage;
    }

    public void setExeMessage(String exeMessage) {
        this.exeMessage = exeMessage;
    }

    public ReturnType getResult() {
        return Result;
    }

    public void setResult(ReturnType result) {
        Result = result;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}

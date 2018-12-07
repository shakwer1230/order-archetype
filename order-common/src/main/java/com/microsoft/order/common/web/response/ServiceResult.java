package com.microsoft.order.common.web.response;


public final class ServiceResult<T> implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String errorMessage;

    private T data;

    private Boolean success;

    private String desc;

    private Long total;

    private Long totalPage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public T getData() {
        return data;
    }

    // TODO 去掉此处的判断data为空的,只为正确的数据,或者是后面添加错误信息,用于错误时加
    public ServiceResult<T> addData(T data) {
        if (data == null) {
            this.failure();
        } else {
            this.data = data;
        }
        return this;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Boolean isFailure() {
        return !success;
    }

    private ServiceResult<T> setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public ServiceResult<T> failure() {
        this.setSuccess(Boolean.FALSE);
        return this;
    }

    public ServiceResult<T> addErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        this.failure();
        return this;
    }

    public ServiceResult() {
        super();
        this.setSuccess(Boolean.TRUE);
    }

    public ServiceResult<T> fromErrorServiceResult(ServiceResult<?> serviceResult) {
        return this.addErrorMessage(serviceResult.getErrorMessage());
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ServiceResult{" +
                "errorMessage='" + errorMessage + '\'' +
                ", data=" + data +
                ", success=" + success +
                ", desc='" + desc + '\'' +
                ", total=" + total +
                ", totalPage=" + totalPage +
                '}';
    }
}

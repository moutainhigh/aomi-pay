package com.cloudbest.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author hdq
 * @Desc 基础返回封装
 * @Date 2020/7/15 15:34
 */
@ApiModel(value = "基础返回参数")
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 是否成功
     **/
    @ApiModelProperty(value = "是否成功", name = "success")
    private boolean success;

    /**
     * 状态码
     **/
    @ApiModelProperty(value = "返回状态码", name = "code")
    private int code;

    /**
     * 状态描述
     **/
    @ApiModelProperty(value = "返回码描述", name = "msg")
    private String msg;

    /**
     * 返回数据
     **/
    @ApiModelProperty(value = "返回内容", name = "data")
    private T data;


    public BaseResponse() {
    }

    public BaseResponse(boolean success, int code, String msg) {
        super();
        this.success = true;
        this.code = code;
        this.msg = msg;
    }

    /**
     * 状态+返回数据
     *
     * @param code  返回码
     * @param msg     返回描述
     */
    public BaseResponse(int code, String msg) {
        super();
        this.success = false;
        this.code = code;
        this.msg = msg;
    }

    /**
     * 状态+返回数据
     *
     * @param commonErrorCode 公共异常编码
     * @param data            泛型
     */
    public BaseResponse(CommonErrorCode commonErrorCode, T data) {
        super();
        this.success = true;
        this.code = commonErrorCode.getCode();
        this.msg = commonErrorCode.getDesc();
        this.data = data;
    }

    /**
     * 状态
     *
     * @param commonErrorCode
     */
    public BaseResponse(CommonErrorCode commonErrorCode) {
        super();
        this.success = commonErrorCode.success;
        this.code = commonErrorCode.getCode();
        this.msg = commonErrorCode.getDesc();
    }

    public static boolean isSuccess(BaseResponse<?> resp) {
        if (resp != null && CommonErrorCode.SUCCESS.getCode() == resp.getCode()) {
            return true;
        }
        return false;
    }

    public void setBaseResponse(CommonErrorCode commonErrorCode, String errorMsg) {
        this.code = commonErrorCode.getCode();
        this.msg = errorMsg;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static void main(String[] args) {

        System.out.println(CommonErrorCode.E_901001.success);
    }
}

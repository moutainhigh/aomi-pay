package com.aomi.pay.vo;

import com.aomi.pay.domain.CommonErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hdq
 * @Desc 基础返回封装
 * @Date 2020/8/3 15:34
 */
@Data
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
    private String code;

    /**
     * 状态描述
     **/
    @ApiModelProperty(value = "返回码描述", name = "message")
    private String message;

    /**
     * 返回数据
     **/
    @ApiModelProperty(value = "返回内容", name = "data")
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(boolean success, String code, String message) {
        super();
        this.success = success;
        this.code = code;
        this.message = message;
    }

    /**
     * 状态+返回数据
     *
     * @param code    返回码
     * @param message 返回描述
     */
    public BaseResponse(String code, String message) {
        super();
        this.success = false;
        this.code = code;
        this.message = message;
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
        this.message = commonErrorCode.getDesc();
        this.data = data;
    }

    /**
     * 状态
     *
     * @param commonErrorCode 公共异常编码
     */
    public BaseResponse(CommonErrorCode commonErrorCode) {
        super();
        this.success = commonErrorCode.isSuccess();
        this.code = commonErrorCode.getCode();
        this.message = commonErrorCode.getDesc();
    }

    public static boolean isSuccess(BaseResponse<?> resp) {
        return resp != null && CommonErrorCode.SUCCESS.getCode().equals(resp.getCode());
    }

    public void setBaseResponse(CommonErrorCode commonErrorCode, String errorMsg) {
        this.code = commonErrorCode.getCode();
        this.message = errorMsg;
    }


}

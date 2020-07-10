package com.cloudbest.common.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 数据响应对象
 *    {
 *      success ：是否成功
 *      code    ：返回码
 *      message ：返回信息
 *      //返回数据
 *      data：  ：{
 *
 *      }
 *    }
 */
@Data
public class Result {

    private boolean success;//是否成功

    private Integer code;// 返回码

    private String message;//返回信息

    private Object data;// 返回数据

    public Result(){

    }


    public Result(CommonErrorCode errorCode,Object result){
      this.success=errorCode.success;
      this.code=errorCode.code;
      this.message=errorCode.desc;
      this.data=result;
    }

    public Result(ErrorCode errorCode){
        this.success=false;
        this.code=errorCode.getCode();
        this.message=errorCode.getDesc();
    }

    public Result(CommonErrorCode code) {
        this.success = code.success;
        this.code = code.code;
        this.message = code.desc;
    }

    public Result(Integer code,String message,boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public static Result SUCCESS(){
        return new Result(CommonErrorCode.SUCCESS);
    }

    public static Result FAIL(){
        return new Result(CommonErrorCode.FAIL);
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

package com.cloudbest.common.domain;

import com.cloudbest.common.annotations.Validator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hdq
 * @Desc 基础请求封装
 * @Date 2020/7/15 16:46
 */
@ApiModel(value="基础请求参数")
@Data
public class BaseRequest<T> implements Serializable{
	
	private static final long serialVersionUID = 5637616777167610555L;
	
	@Validator(isNotNull = true,description = "token")
	@ApiModelProperty(value="授权token,登陆成功后返回",name="token", example="123456789")
	private String token;
	
	
	@ApiModelProperty(value="访问ip",name="ip", example="0.0.0.0")
	private String ip;
	
	@Validator(isNotNull = true,description = "param")
	@ApiModelProperty(value="业务参数",name="param")
	private T param;
}

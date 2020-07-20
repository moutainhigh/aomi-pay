package com.cloudbest.common.domain;

import com.cloudbest.common.annotations.Validator;
import com.cloudbest.common.util.RegexUtils;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author hdq
 * @Desc 分页请求封装
 * @Date 2020/7/15 16:46
 */
public class BasePageRequest implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1076425553661245215L;

	/**
     * 页码
     */
    @Validator(regexExpression = RegexUtils.REGEX_PAGENO,description = "页码")
    @ApiModelProperty(value="页码",example="1")
    private String pageNo;

    /**
     * 页面大小
     */
    @Validator(regexExpression = RegexUtils.REGEX_PAGESIZE,description = "页面大小")
    @ApiModelProperty(value="页面大小",example="10")
    private String pageSize;

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
    
    
    
}

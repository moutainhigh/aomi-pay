package com.cloudbest.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author hdq
 * @Date 2020/7/17 15:19
 * @Description 分页封装
 */
@Data
@ApiModel
public class PageResult implements Serializable {

    /**
     * 总行数
     */
    @ApiModelProperty(value="总行数",name="count")
    protected Long count;

    /**
     * 结果集
     */
    @ApiModelProperty(value="结果集",name="result")
    protected List<?> result;

    /**
     * 页码
     */
    @ApiModelProperty(value="页码",name="pageNo")
    protected String pageNo;

    /**
     * 页面大小
     */
    @ApiModelProperty(value="页面大小",name="pageSize")
    protected String pageSize;

    /**
     * @Description 添加无参构造
     * @author hdq
     */
    public PageResult(){

    }

    /**
     * 功能描述: 分页
     */
    public PageResult(List<?> result, Long count) {
        super();
        this.result = result;
        this.count = count;
    }
    
    /**
     * 功能描述: 获取总数
     */
    public Long getCount() {
        return count;
    }

    /**
     * 功能描述: 获取结果集合
     */
    public List<?> getResult() {
        return result;
    }

}

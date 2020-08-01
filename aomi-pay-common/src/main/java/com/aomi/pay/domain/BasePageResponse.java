package com.aomi.pay.domain;

import com.aomi.pay.constants.ParamConstans;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hdq
 * @Desc 分页返回封装
 * @Date 2020/7/15 16:48
 */
@ApiModel(value = "分页数据")
@Data
public class BasePageResponse<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1850353565814717921L;

    /**
     * 列表
     */
    @ApiModelProperty(value = "列表")
    private List<T> list;

    /**
     * 总行数
     */
    @ApiModelProperty(value = "总行数")
    private Long count;

    /**
     * 页码
     */
    @ApiModelProperty(value = "页码")
    protected String pageNo;

    /**
     * 页面大小
     */
    @ApiModelProperty(value = "页面大小")
    protected String pageSize;
    /**
     * 页面大小
     */
    @ApiModelProperty(value = "总页数")
    protected String pages;

    public BasePageResponse(PageResult pr, String pageNo, String pageSize) {
        this.list = (List<T>) pr.getResult();
        this.count = pr.getCount() == null ? ParamConstans.PAGE_COUNT_NULL : pr.getCount();
        this.pageNo = pageNo;
        this.pageSize = pageSize;

        int items = 0;
        if (Integer.parseInt(pageSize) > 0 && pr.getCount() != null) {
            items = (int) (pr.getCount() / Integer.parseInt(pageSize) + ((pr.getCount() % Integer.parseInt(pageSize) == 0) ? 0 : 1));
        }
        this.pages = items + "";
    }

    public BasePageResponse() {

    }

}

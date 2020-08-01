package com.aomi.pay.controller.app;


import com.aomi.pay.constants.ParamConstans;
import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.domain.PageResult;
import com.aomi.pay.model.*;
import com.aomi.pay.service.EsItemsService;
import com.aomi.pay.util.StringUtil;
import com.aomi.pay.util.ValidateUtil;
import com.aomi.pay.vo.BasePageResponse;
import com.aomi.pay.vo.BaseRequest;
import com.aomi.pay.vo.BaseResponse;
import com.aomi.pay.vo.EsItemsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 搜索商品管理Controller APP调用
 *
 * @author : hdq
 * @date 2020/7/11 11:31
 */
@Slf4j
@CrossOrigin
@RestController
@Api(value = "AppEsItemsController", tags = "搜索商品管理")
@RequestMapping("/app/pay/esItems")
public class AppEsItemsController {

    @Resource
    private EsItemsService esItemsService;


    /**
     * Desc: 简单搜索 ： 根据关键字搜索商品名称
     * TODO 暂搜商品名分词
     *
     * @author : hdq
     * @date : 2020/7/17 14:53
     */
    @ApiOperation(value = "简单搜索", notes = "根据关键字检索商品名")
    @PostMapping(value = "/simple")
    public BaseResponse<BasePageResponse<EsItemsVO>> simpleSearch(@RequestBody BaseRequest<SimpleSearchRequest> req) throws Exception {
        log.info("简单搜索，req：{}", req);

        //参数校验
        ValidateUtil.valid(req);

        SimpleSearchRequest param = req.getParam();

        //为空填充默认pageNo,pageSize
        //Integer pageNo = StringUtil.isBlank(param.getPageNo()) ? ParamConstans.PAGE_NO:Integer.parseInt(param.getPageNo());
        //Integer pageSize = StringUtil.isBlank(param.getPageSize()) ? ParamConstans.PAGE_SIZE:Integer.parseInt(param.getPageSize());

        //PageResult result = esItemsService.pay(param.getKeywords(), pageNo, pageSize);
        //BasePageResponse<EsItemsVO> resp = new BasePageResponse<EsItemsVO>(result, pageNo.toString(), pageSize.toString());

        return new BaseResponse<BasePageResponse<EsItemsVO>>(CommonErrorCode.SUCCESS, null);
    }

    /**
     * Desc: 综合搜索、筛选、排序
     *
     * @author : hdq
     * @date : 2020/7/22 9:25
     */
    @ApiOperation(value = "综合搜索", notes = "综合搜索、筛选、排序")
    @PostMapping(value = "/pay")
    public BaseResponse<BasePageResponse<EsItemsVO>> pay(@RequestBody BaseRequest<SearchRequest> req) throws Exception {
        log.info("综合搜索、筛选、排序，req：{}", req);
        SearchRequest param = req.getParam();
        //参数校验
        ValidateUtil.valid(req);

        //为空填充默认pageNo,pageSize
        //Integer pageNo = StringUtil.isBlank(param.getPageNo()) ? ParamConstans.PAGE_NO:Integer.parseInt(param.getPageNo());
        //Integer pageSize = StringUtil.isBlank(param.getPageSize()) ? ParamConstans.PAGE_SIZE:Integer.parseInt(param.getPageSize());
        //Integer categoryId = StringUtil.isBlank(param.getCategoryId()) ? null : Integer.parseInt(param.getCategoryId());
        //Integer sort = StringUtil.isBlank(param.getSort()) ? ParamConstans.SORT_NULL : Integer.parseInt(param.getSort());

        //PageResult result = esItemsService.pay(param.getKeywords(),categoryId, pageNo, pageSize, sort);
        //BasePageResponse<EsItemsVO> resp = new BasePageResponse<EsItemsVO>(result, pageNo.toString(), pageSize.toString());

        return new BaseResponse<BasePageResponse<EsItemsVO>>(CommonErrorCode.SUCCESS, null);
    }
}

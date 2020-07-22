package com.cloudbest.search.controller.app;


import com.cloudbest.common.constants.ParamConstans;
import com.cloudbest.common.domain.*;
import com.cloudbest.common.exception.SystemException;
import com.cloudbest.common.util.StringUtil;
import com.cloudbest.common.util.ValidateUtil;
import com.cloudbest.search.model.*;
import com.cloudbest.search.service.EsItemsService;
import com.cloudbest.search.vo.EsItemsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.bind.ValidationException;

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
@RequestMapping("/app/search/esItems")
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
        Integer pageNo = StringUtil.isBlank(param.getPageNo()) ? ParamConstans.PAGE_NO:Integer.parseInt(param.getPageNo());
        Integer pageSize = StringUtil.isBlank(param.getPageSize()) ? ParamConstans.PAGE_SIZE:Integer.parseInt(param.getPageSize());

        PageResult result = esItemsService.search(param.getKeywords(), pageNo, pageSize);
        BasePageResponse<EsItemsVO> resp = new BasePageResponse<EsItemsVO>(result, pageNo.toString(), pageSize.toString());

        return new BaseResponse<BasePageResponse<EsItemsVO>>(CommonErrorCode.SUCCESS, resp);
    }

    /**
     * Desc: 综合搜索、筛选、排序
     *
     * @author : hdq
     * @date : 2020/7/22 9:25
     */
    @ApiOperation(value = "综合搜索", notes = "综合搜索、筛选、排序")
    @PostMapping(value = "/search")
    public BaseResponse<BasePageResponse<EsItemsVO>> search(@RequestBody BaseRequest<SearchRequest> req) throws Exception {
        log.info("综合搜索、筛选、排序，req：{}", req);
        SearchRequest param = req.getParam();
        //参数校验
        ValidateUtil.valid(req);

        //为空填充默认pageNo,pageSize
        Integer pageNo = StringUtil.isBlank(param.getPageNo()) ? ParamConstans.PAGE_NO:Integer.parseInt(param.getPageNo());
        Integer pageSize = StringUtil.isBlank(param.getPageSize()) ? ParamConstans.PAGE_SIZE:Integer.parseInt(param.getPageSize());

        PageResult result = esItemsService.search(param.getKeywords(),Integer.parseInt(param.getCategoryId()), pageNo, pageSize, Integer.parseInt(param.getSort()));
        BasePageResponse<EsItemsVO> resp = new BasePageResponse<EsItemsVO>(result, pageNo.toString(), pageSize.toString());

        return new BaseResponse<BasePageResponse<EsItemsVO>>(CommonErrorCode.SUCCESS, resp);
    }
}

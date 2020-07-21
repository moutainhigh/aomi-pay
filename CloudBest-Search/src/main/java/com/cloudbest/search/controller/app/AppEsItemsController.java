package com.cloudbest.search.controller.app;


import com.cloudbest.common.constants.ParamConstans;
import com.cloudbest.common.domain.*;
import com.cloudbest.common.util.StringUtil;
import com.cloudbest.common.util.ValidateUtil;
import com.cloudbest.search.entity.EsItems;
import com.cloudbest.search.model.CreateEsItemsByIdRequest;
import com.cloudbest.search.model.DeleteEsItemsByIdRequest;
import com.cloudbest.search.model.DeleteEsItemsByIdsRequest;
import com.cloudbest.search.model.SimpleSearchRequest;
import com.cloudbest.search.service.EsItemsService;
import com.cloudbest.search.vo.EsItemsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
     * @author : hdq
     * @date : 2020/7/17 14:53
     */
    @ApiOperation(value = "简单搜索")
    @PostMapping(value = "/simple")
    @ResponseBody
    public BaseResponse<BasePageResponse<EsItemsVO>> simpleSearch(@RequestBody BaseRequest<SimpleSearchRequest> req) throws Exception {
        log.info("简单搜索，req：{}", req);
        SimpleSearchRequest param = req.getParam();
        //参数校验
        ValidateUtil.valid(param);

        //为空填充默认pageNo,pageSize
        Integer pageNo = StringUtil.isBlank(param.getPageNo()) ? ParamConstans.PAGE_NO:Integer.parseInt(param.getPageNo());
        Integer pageSize = StringUtil.isBlank(param.getPageSize()) ? ParamConstans.PAGE_SIZE:Integer.parseInt(param.getPageSize());
        PageResult result = esItemsService.search(param.getKeywords(),pageNo,pageSize);
        //BasePageResponse<EsItemsVO> resp = new BasePageResponse<EsItemsVO>(list,pageNo.toString(),pageSize.toString());

        return new BaseResponse<BasePageResponse<EsItemsVO>>(CommonErrorCode.SUCCESS, null);
    }

    @ApiOperation(value = "简单搜索")
    @RequestMapping(value = "/search/simple", method = RequestMethod.GET)
    @ResponseBody
    public void search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsItems> esProductPage = esItemsService.search1(keyword, pageNum, pageSize);
        List<EsItems> esItemsList = esProductPage.getContent().isEmpty() ? null : esProductPage.getContent();
        log.info("esProductPage:{}",esProductPage);
        log.info("esProductPage:{}",esItemsList);
    }
}

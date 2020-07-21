package com.cloudbest.search.controller.admin;


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
 * 搜索商品管理Controller
 *
 * @author : hdq
 * @date 2020/7/11 11:31
 */
@Slf4j
@CrossOrigin
@RestController
@Api(value = "EsItemsController", tags = "搜索商品管理")
@RequestMapping("/search/esItems")
public class EsItemsController {

    @Resource
    private EsItemsService esItemsService;

    /**
     * Desc:
     * 〈导入所有数据库中商品到ES〉
     *
     * @author : hdq
     * @date : 2020/7/12 11:42
     */
    @ApiOperation(value = "导入所有数据库中商品到ES")
    @PostMapping(value = "/importAll")
    public BaseResponse importAllList() {
        log.info("导入所有数据库中商品到ES");
        int count = esItemsService.importAll();
        log.info("商品导入数据条数：{}", count);
        return new BaseResponse(CommonErrorCode.SUCCESS);
    }

    /**
     * Desc: 根据id删除商品
     *
     * @param req
     * @author : hdq
     * @date : 2020/7/14 11:16
     */
    @ApiOperation(value = "根据id删除商品")
    @PostMapping(value = "/deleteById")
    public BaseResponse deleteById(@RequestBody BaseRequest<DeleteEsItemsByIdRequest> req) throws Exception {
        log.info("根据id删除商品，req：{}", req);
        DeleteEsItemsByIdRequest param = req.getParam();
        //参数校验
        ValidateUtil.valid(param);

        esItemsService.deleteById(param.getId());
        return new BaseResponse(CommonErrorCode.SUCCESS);
    }

    /**
     * Desc: 根据id批量删除商品
     *
     * @param req
     * @author : hdq
     * @date : 2020/7/15 11:32
     */
    @ApiOperation(value = "根据id批量删除商品")
    @PostMapping(value = "/deleteByIds")
    @ResponseBody
    public BaseResponse deleteByIds(@RequestBody BaseRequest<DeleteEsItemsByIdsRequest> req) throws Exception {
        log.info("根据ids批量删除商品，req：{}", req);
        DeleteEsItemsByIdsRequest param = req.getParam();
        //参数校验
        ValidateUtil.valid(param);

        esItemsService.deleteByIds(param.getIds());
        return new BaseResponse(CommonErrorCode.SUCCESS);
    }

}

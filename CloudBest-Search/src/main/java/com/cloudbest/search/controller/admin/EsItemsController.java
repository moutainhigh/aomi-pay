package com.cloudbest.search.controller.admin;


import com.cloudbest.common.domain.*;
import com.cloudbest.common.util.ValidateUtil;
import com.cloudbest.search.model.CreateEsItemsByIdRequest;
import com.cloudbest.search.model.DeleteEsItemsByIdRequest;
import com.cloudbest.search.model.DeleteEsItemsByIdsRequest;
import com.cloudbest.search.service.EsItemsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
     * @return BaseResponse
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
     * @param  req
     * @author : hdq
     * @date : 2020/7/14 11:16
     * @return BaseResponse
     */
    @ApiOperation(value = "根据id删除商品")
    @PostMapping(value = "/deleteById")
    public BaseResponse deleteById(@RequestBody BaseRequest<DeleteEsItemsByIdRequest> req) throws Exception {
        log.info("根据id删除商品，req：{}", req);
        //参数校验
        ValidateUtil.valid(req);

        DeleteEsItemsByIdRequest param = req.getParam();

        esItemsService.deleteById(Integer.valueOf(param.getId()));
        return new BaseResponse(CommonErrorCode.SUCCESS);
    }

    /**
     * Desc: 根据id批量删除商品
     *
     * @param req
     * @author : hdq
     * @date : 2020/7/15 11:32
     * @return BaseResponse
     */
    @ApiOperation(value = "根据id批量删除商品")
    @PostMapping(value = "/deleteByIds")
    @ResponseBody
    public BaseResponse deleteByIds(@RequestBody @Valid BaseRequest<DeleteEsItemsByIdsRequest> req) throws Exception {
        log.info("根据ids批量删除商品，req：{}", req);
        //参数校验
        ValidateUtil.valid(req);

        DeleteEsItemsByIdsRequest param = req.getParam();
        List<Integer> ids = param.getIds().stream().map(Integer::parseInt).collect(Collectors.toList());
        esItemsService.deleteByIds(ids);
        return new BaseResponse(CommonErrorCode.SUCCESS);
    }

    /**
     * Desc: 根据id导入商品到ES
     *
     * @param req 1
     * @return : com.cloudbest.common.domain.BaseResponse
     * @author : hdq
     * @date : 2020/7/23 11:38
     */
    @ApiOperation(value = "根据id导入商品到ES")
    @PostMapping(value = "/importById")
    public BaseResponse importById(@RequestBody BaseRequest<CreateEsItemsByIdRequest> req) throws Exception {
        log.info("根据id导入商品到ES");
        //参数校验
        ValidateUtil.valid(req);

        CreateEsItemsByIdRequest param = req.getParam();

        esItemsService.create(Integer.valueOf(param.getId()));

        return new BaseResponse(CommonErrorCode.SUCCESS);
    }
}

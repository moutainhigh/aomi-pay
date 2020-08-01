package com.aomi.pay.controller.admin;


import com.aomi.pay.util.ValidateUtil;
import com.aomi.pay.vo.BaseRequest;
import com.aomi.pay.vo.BaseResponse;
import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.model.CreateEsItemsByIdRequest;
import com.aomi.pay.model.DeleteEsItemsByIdRequest;
import com.aomi.pay.model.DeleteEsItemsByIdsRequest;
import com.aomi.pay.service.EsItemsService;
import com.iboxpay.open.security.util.SignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@RequestMapping("/pay/esItems")
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
     * @return : com.aomi.pay.domain.BaseResponse
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

    @PostMapping("test")
    public void test(){
        Map<String, String> params = new HashMap<>();
        params.put("licenseNo", "440602000434698");
        // 对请求参数进行签名，返回的map包含验证所需的公共参数
        //params = SignUtil.sign(params, "从开放平台得到的appId", "从开放平台得到的appSecret");
        params = SignUtil.sign(params, "135459893032255489", "7kyB+aUhF14f+zbE0ERd4Q==");

        //测试环境url
        String url = "https://payapi-sandbox.imipay.com";
        MultiValueMap<String, Object> requestEntity  = new LinkedMultiValueMap<String, Object>();
        //购物消费类型消费的购物券
        //requestEntity.add("transactionType", 1);
        RestTemplate restTemplate=new RestTemplate();
        requestEntity.add("params",params);
        String subScoreResult = restTemplate.postForObject(url, requestEntity, String.class);
        JSONObject subScoreObject =  JSONObject.fromObject(subScoreResult);

        System.out.println(subScoreObject.toString());
    }
}

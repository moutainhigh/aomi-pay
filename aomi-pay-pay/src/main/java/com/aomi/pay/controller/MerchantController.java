package com.aomi.pay.controller;


import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.model.GetMerchantInfoResponse;
import com.aomi.pay.model.GetSimpleNameRequest;
import com.aomi.pay.service.MerchantService;
import com.aomi.pay.util.ValidateUtil;
import com.aomi.pay.vo.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 商户接口Controller
 *
 * @author : hdq
 * @date 2020/8/18
 */
@Slf4j
@CrossOrigin
@RestController
@Api(value = "MerchantController", tags = "商户接口管理")
@RequestMapping("/merchant")
public class MerchantController {

    @Resource
    private MerchantService merchantService;

    @ApiOperation(value = "根据固码获取商户简称")
    @PostMapping("/getSimpleName")
    public BaseResponse getSimpleName(@RequestBody GetSimpleNameRequest req) throws Exception {
        log.info("------------根据固码获取商户简称开始------------");
        ValidateUtil.valid(req);
        String simpleName = merchantService.queryByQrCode(req.getFixedQrCode());
        log.info("--------根据固码获取商户简称结束--------");
        return new BaseResponse<>(CommonErrorCode.SUCCESS,simpleName);
    }

    @ApiOperation(value = "根据固码获取商户信息")
    @PostMapping("/getMerchantInfo")
    public BaseResponse getMerchantInfo(@RequestBody GetSimpleNameRequest req) throws Exception {
        log.info("------------根据固码获取商户信息开始------------");
        ValidateUtil.valid(req);
        GetMerchantInfoResponse res = merchantService.queryInfoByQrCode(req.getFixedQrCode());
        log.info("--------根据固码获取商户信息结束--------");
        return new BaseResponse<>(CommonErrorCode.SUCCESS,res);
    }

}

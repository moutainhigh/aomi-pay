package com.aomi.pay.controller;


import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.entity.MerchantQrBind;
import com.aomi.pay.entity.PaymentOrder;
import com.aomi.pay.mapper.order.PaymentOrderMapper;
import com.aomi.pay.mapper.user.MerchantQrBindMapper;
import com.aomi.pay.model.GetMerchantInfoResponse;
import com.aomi.pay.model.GetSimpleNameRequest;
import com.aomi.pay.model.JsPayRequest;
import com.aomi.pay.model.NotifyRequest;
import com.aomi.pay.service.MerchantService;
import com.aomi.pay.service.PaymentOrderService;
import com.aomi.pay.util.GeneralConvertorUtil;
import com.aomi.pay.util.ValidateUtil;
import com.aomi.pay.vo.BaseResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

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

    @Resource
    private MerchantQrBindMapper merchantQrBindMapper;

    @Resource
    private PaymentOrderMapper paymentOrderMapper;

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

    @ApiOperation(value = "test")
    @PostMapping("/test")
    public BaseResponse test() throws Exception {
        log.info("------------test------------");

        PaymentOrder paymentOrder = paymentOrderMapper.selectById(new BigInteger("12008172116240552666"));

        MerchantQrBind merchantQrBind = merchantQrBindMapper.selectById(1);

        log.info("------paymentOrder:{},merchantQrBind:{}",paymentOrder,merchantQrBind);
        return new BaseResponse<>(CommonErrorCode.SUCCESS);
    }

    @ApiOperation(value = "test")
    @PostMapping("/test1")
    public BaseResponse test1() throws Exception {
        log.info("------------test------------");

        MerchantQrBind merchantQrBind = merchantQrBindMapper.selectById(1);

        PaymentOrder paymentOrder = paymentOrderMapper.selectById(new BigInteger("12008172116240552666"));

        log.info("------paymentOrder:{},merchantQrBind:{}",paymentOrder,merchantQrBind);
        return new BaseResponse<>(CommonErrorCode.SUCCESS);
    }

}

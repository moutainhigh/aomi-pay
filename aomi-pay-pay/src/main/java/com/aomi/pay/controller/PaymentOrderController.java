package com.aomi.pay.controller;


import com.aomi.pay.config.DbContextHolder;
import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.enums.DBTypeEnum;
import com.aomi.pay.model.GetMerchantInfoResponse;
import com.aomi.pay.model.JsPayRequest;
import com.aomi.pay.service.MerchantService;
import com.aomi.pay.service.PaymentOrderService;
import com.aomi.pay.util.*;
import com.aomi.pay.vo.BaseResponse;
import com.aomi.pay.vo.JsPayVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 交易类接口Controller
 *
 * @author : hdq
 * @date 2020/8/5
 */
@Slf4j
@CrossOrigin
@RestController
@Api(value = "PaymentController", tags = "交易接口管理")
@RequestMapping("/payment")
public class PaymentOrderController {

    @Resource
    private PaymentOrderService paymentOrderService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private MerchantService merchantService;

    @ApiOperation(value = "h5支付")
    @PostMapping("/jsPay")
    public BaseResponse jsPay(@RequestBody JsPayRequest req) throws Exception {
        log.info("--------h5支付开始--------req:{}", req);
        //参数校验
        ValidateUtil.valid(req);

        //从redis获取所有的银行编码信息
        String result = redisUtil.getString("merchantInfoCache::".concat(req.getFixedQrCode()));

        GetMerchantInfoResponse res = new GetMerchantInfoResponse();

        if (StringUtil.isNotBlank(result)) {
            res = GeneralConvertorUtil.convertor(JSONObject.fromObject(result), GetMerchantInfoResponse.class);
        } else {//没取到就从数据库取，再存redis
            res = merchantService.queryInfoByQrCode(req.getFixedQrCode());
        }
        if(StringUtils.isEmpty(res)){
            CommonExceptionUtils.throwBusinessException(CommonErrorCode.FAIL);
        }

        JsPayVO jsPayVO = GeneralConvertorUtil.convertor(res, JsPayVO.class);
        jsPayVO.setAmount(new BigDecimal(req.getAmount()));
        jsPayVO.setUserId(req.getUserId());
        jsPayVO.setPayType(Integer.parseInt(req.getPayType()));
        jsPayVO.setMerchantId(res.getId());
        //进事务前手动设置数据源
        DbContextHolder.setDbType(DBTypeEnum.order);
        String payInfo = paymentOrderService.jsPay(jsPayVO);
        log.info("--------h5支付结束--------");
        return new BaseResponse(CommonErrorCode.SUCCESS, payInfo);
    }

}

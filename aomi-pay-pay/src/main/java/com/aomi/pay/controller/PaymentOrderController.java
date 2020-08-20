package com.aomi.pay.controller;


import com.aomi.pay.config.DbContextHolder;
import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.entity.MerchantQrBind;
import com.aomi.pay.entity.PaymentOrder;
import com.aomi.pay.enums.DBTypeEnum;
import com.aomi.pay.mapper.order.PaymentOrderMapper;
import com.aomi.pay.mapper.user.MerchantQrBindMapper;
import com.aomi.pay.model.GetMerchantInfoResponse;
import com.aomi.pay.model.JsPayRequest;
import com.aomi.pay.model.NotifyRequest;
import com.aomi.pay.service.MerchantService;
import com.aomi.pay.service.PaymentOrderService;
import com.aomi.pay.util.*;
import com.aomi.pay.vo.BaseResponse;
import com.aomi.pay.vo.JsPayVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

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
    private PaymentOrderMapper paymentOrderMapper;

    @Resource
    private MerchantQrBindMapper merchantQrBindMapper;

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

    @ApiOperation(value = "支付回调")
    @PostMapping("/notify")
    public void payNotify(@RequestBody String request) throws Exception {
        log.info("------------支付回调开始------------params:{}", request);

        JSONObject jsonObject = JSONObject.fromObject(request);
        Object data = jsonObject.get("data");
        if (!StringUtils.isEmpty(data)) {
            NotifyRequest notifyRequest = GeneralConvertorUtil.convertor(JSONObject.fromObject(data), NotifyRequest.class);
            //进事务前手动切数据源
            DbContextHolder.setDbType(DBTypeEnum.order);
            paymentOrderService.payNotify(notifyRequest);
        }
        log.info("--------支付回调结束--------");
    }

    @ApiOperation(value = "test")
    @PostMapping("/test")
    public BaseResponse test() throws Exception {
        log.info("------------交易列表查询开始------------");
        QueryWrapper<PaymentOrder> queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id", new BigInteger("12008172116240552666"));
        List<PaymentOrder> paymentOrderList = paymentOrderMapper.selectList(queryWrapper);
        log.info("--------交易列表查询结束--------");
        return new BaseResponse<>(CommonErrorCode.SUCCESS,paymentOrderList);
    }

    @ApiOperation(value = "test")
    @PostMapping("/test1")
    public BaseResponse test1() throws Exception {
        log.info("------------交易列表查询开始------------");
        MerchantQrBind merchantQrBind = merchantQrBindMapper.selectById(1);
        log.info("--------交易列表查询结束--------");
        return new BaseResponse<>(CommonErrorCode.SUCCESS,merchantQrBind);
    }

    @ApiOperation(value = "test")
    @PostMapping("/test2")
    public BaseResponse test2() throws Exception {
        log.info("------------交易列表查询开始------------");
        //从redis获取所有的银行编码信息
        String result = redisUtil.getString("merchantInfoCache::".concat("20082013579549900012"));

        GetMerchantInfoResponse res = new GetMerchantInfoResponse();

        if (StringUtil.isNotBlank(result)) {
            res = GeneralConvertorUtil.convertor(JSONObject.fromObject(result), GetMerchantInfoResponse.class);
        } else {//没取到就从数据库取，再存redis
            res = merchantService.queryInfoByQrCode("20082013579549900012");
        }
        QueryWrapper<PaymentOrder> queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_id", new BigInteger("12008172116240552666"));
        List<PaymentOrder> paymentOrderList = paymentOrderMapper.selectList(queryWrapper);
        /*PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setOrderId(new BigInteger("123456123456"));
        paymentOrder.setAmount(new BigDecimal("0.01"));
        paymentOrder.setSubject("aaaaa");
        paymentOrder.setMerchantId(12345678L);
        int i = paymentOrderMapper.insert(paymentOrder);*/
        //log.info("i:{}",i);
        //MerchantQrBind merchantQrBind = merchantQrBindMapper.selectById(1);
        log.info("--------交易列表查询结束--------");
        log.info("------result:{},paymentOrderList:{}",result,paymentOrderList);
        return new BaseResponse<>(CommonErrorCode.SUCCESS);
    }

}

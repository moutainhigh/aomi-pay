package com.aomi.pay.controller;


import com.aomi.pay.constants.ParamConstants;
import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.domain.PageResult;
import com.aomi.pay.entity.PaymentOrder;
import com.aomi.pay.model.JsPayRequest;
import com.aomi.pay.model.NotifyRequest;
import com.aomi.pay.model.QueryListResponse;
import com.aomi.pay.service.PaymentOrderService;
import com.aomi.pay.util.GeneralConvertorUtil;
import com.aomi.pay.util.StringUtil;
import com.aomi.pay.util.TokenUtil;
import com.aomi.pay.util.ValidateUtil;
import com.aomi.pay.vo.BasePageRequest;
import com.aomi.pay.vo.BasePageResponse;
import com.aomi.pay.vo.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 交易类(无卡类)接口Controller
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

    @ApiOperation(value = "h5支付")
    @PostMapping("/jsPay")
    public BaseResponse jsPay(@RequestBody JsPayRequest req) throws Exception {
        log.info("--------h5支付开始--------req:{}", req);
        //参数校验
        ValidateUtil.valid(req);
        String payInfo = paymentOrderService.jsPay(req);
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
            paymentOrderService.payNotify(notifyRequest);
        }
        log.info("--------支付回调结束--------");
    }

    @ApiOperation(value = "交易列表查询")
    @PostMapping("/queryList/{token}")
    public BaseResponse<BasePageResponse<QueryListResponse>> queryList(@PathVariable("token") String token, @RequestBody BasePageRequest req) throws Exception {
        log.info("------------交易列表查询开始------------param:{}", req);
        //参数校验
        ValidateUtil.valid(req);
        long merchantId = TokenUtil.getUserId(token);
        //为空填充默认pageNo,pageSize
        Integer pageNo = StringUtil.isBlank(req.getPageNo()) ? ParamConstants.PAGE_NO : Integer.parseInt(req.getPageNo());
        Integer pageSize = StringUtil.isBlank(req.getPageSize()) ? ParamConstants.PAGE_SIZE : Integer.parseInt(req.getPageSize());

        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setMerchantId(merchantId);
        PageResult page = paymentOrderService.queryListForPage(paymentOrder, pageNo, pageSize);
        BasePageResponse<QueryListResponse> resp = new BasePageResponse<>(page, pageNo.toString(), pageSize.toString());
        log.info("--------交易列表查询结束--------");
        return new BaseResponse<>(CommonErrorCode.SUCCESS, resp);
    }

}

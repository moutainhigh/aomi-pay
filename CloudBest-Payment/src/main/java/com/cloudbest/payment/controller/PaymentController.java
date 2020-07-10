package com.cloudbest.payment.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.payment.conf.AlipayConfig;
import com.cloudbest.payment.dto.AlipayBean;
import com.cloudbest.payment.dto.PaymentResponseDTO;
import com.cloudbest.payment.entity.PaymentLog;
import com.cloudbest.payment.feign.OrderClient;
import com.cloudbest.payment.service.PaymentLogService;
import com.cloudbest.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
public class PaymentController {

    private static Logger log = LoggerFactory.getLogger(PaymentController.class);

    // 网关
    String URL = "https://openapi.alipay.com/gateway.do";
    // 商户APP_ID
    String APP_ID = "2021001168632988";
    // 商户RSA 私钥
    String APP_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCNENyB8niHvexRzl/0c+SMHg9ZbT3iKkkDvqH0IaIpcXn6lxpvn6HPLRp40p0v0D1/fJJCPjjJMlBZz4yLGcTvC5/oL92ix6PaY4piySx+JqJ+zkS6YulDodATtgzBr60Em3vcWwFSq+ne6KNwb6nyBhUCIz+UZcWWH+X872+r8djZAeWVwa06zAbjeExTja+0DpuM4cbpeiHu6qkjbxQM7JX/Bb79V1uP5BqXWKIr9P8DDXaplEbrLqmLrpd+uMXrohIDfLTk4zdAjqOvLfnCb0gAk/RcbvZIdCcYszuVbCwUT8UnvuAW9wc8jMNAbgVLIAbCD/qtbF30mxWny8ZFAgMBAAECggEBAImh9+L5dcvK6/VtC4XwRP7InWU9BnHRm+eBaXZOEm+o/cAXCX37G96NPRTBOQfbfwURv4nON41l/6uAqrlU7SXmC6B4gyA89IsxL2XurfBvNX/PNJM9yiVojSGOL6gSBRdHtHNsfz/v54DLLhuXdxJiCMFLLs9U9JDiknxUCHqTPAM8JS0o9nXe1yBi72yk3GNDZC0nGr6hIxKXhtl9esTzKOmeIRi5l0jRS1+A91dI+cZLgKX2q3Qk7xo0GOzDKtnOuVQ8T1LGAeH0mc5TMgGdouxazFZ6rywl+csK5I2DKTlILZzo+TBnvWHISkD0VajQ8XYfxmTNcVca5hlzmckCgYEA/aKI5KrJgHWHZfIJykoT+wGKXex50kNGhWopqbeZ/0LlT2FKcnqYx2Y+RTH52v0vMkGvyLfTT+/tBiuEWZBRmWy7wIDiHK1zBAkhQYFWjMj6/os1Wb6sRTpS6JYt7iASPxtVnvVyuCYsvwDOuuZ3Ft/D+WVlCDE1Qjye3nPBBqsCgYEAjmGbbNWdD9Q6esBnKoLpjhoTRs8JA5eCPm0nc+w4KXZSIuBrfuWfkQ3iYjly9gIVODUnXMr+TX0VoiFFbhdqc0ixN70trHReQNAfrI4FSjg9llQDLq5rZd0c43J+1ZaRB1a0WKt8Jq+ZcFHR05AG96e3QwGezFIAbRggnJ8uJs8CgYEAz+LseSBS0GuEctaTl65n+hAlLR6qKuDhaHC/fU/zdPmLoiiIMJSzrJvs5iI0PH3QtUyJm+av2JE6oRgB/rb/atVofjwlGIRCBUjGbT5phjMlJoVLUSerY4KOefy94LOAwSDSaNudD7qaamNrDl3ftmvp4Pxudux+TrAJNyOBTskCgYB1b66JsxtL3gmqC55MIxROn9U8dzGBI+tKYC/FXpXoJO6UU7R2QyE8vE34JFhc7tTfun/P+qKL+VrpkjW5G1ky40dAqSnN72F6Ze9qHO3iDMdHgd3qF/T/XMQiGeLhesH+YpZ3+GF7ayTMzr+Zl1cX197BRsnaztwJH3hulqCzYwKBgQC1H4JnOeJ4crzT+N/oI2h3eJZOtZ+PxrVetMDHqgkgbcFaRJfBrGVh0Pk/goQezBUKDhbX4/WOrTEWcYoPs33V/7Zv6kjkldP3Phvzzvl7/Y0+yiLTIVzBx1E0sP4wyKIPYd2QyOtrTvX7abAqMDoOGSJisr5vnyvuBTFlJ5ekuQ==";
    // 请求方式 json
    String FORMAT = "json";
    // 编码格式，目前只支持UTF-8
    String CHARSET = "utf-8";
    // 支付宝公钥
    String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz6E8KWmPjRfxtg1X3twnPdkRUd6rwCsCtUfI8OTsyz+Ir3cPKd5yXhnAdhUS14ODXGCadCRTbH7MHTialAKsiab0tEE0BM6/MT2iYYU5pbx978HNk/HTOqFwnz1eh8XpAWe+BQS4aIujmOIxZ49IqixsLTA3GC60aeLnqKwNtZ3TXdjmVWEF02dKNdVV0Zaq45LAOnYCjQF1lRbt4qHaYCoG1fqNO+vob1bdtv/ubXHcfgVOh+nWa/c25Kq0Ne90CRfNj3z9IRiTJoN7lssMK6wrbwTmzqcs3E9jnkFcr0W2SL1InIpTNY15XOOYnGAHFeoSrOJMYEeEjyD3NIc/swIDAQAB";
    // 签名方式
    String SIGN_TYPE = "RSA2";


    String Notify_Url = "http://app.cloudbest.shop:8888/app/payment/pay_s";
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentLogService paymentLogService;

    @Autowired
    private OrderClient orderClient;

    @RequestMapping(value="/payment/sss",method= RequestMethod.POST)
    //传入商品信息
    public Map<String,String> pay(@RequestBody AlipayBean alipayBean) {
        Map<String,String> success = new HashMap<String,String>();
        AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY,FORMAT , CHARSET, ALIPAY_PUBLIC_KEY,SIGN_TYPE); //获得初始化的AlipayClient
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//        model.setBody("");//商品描述
//        model.setSubject("");//关键字
//        model.setOutTradeNo(""); //唯一订单不能重复
//        model.setTimeoutExpress("30m");//最晚付款时间
//        model.setTotalAmount("1.5");//商品价格
//        model.setProductCode("QUICK_MSECURITY_PAY");//产品销售码
        model.setOutTradeNo(alipayBean.getOutTradeNo());//商户的订单，就是本平台的订单
        model.setTotalAmount(alipayBean.getTotalAmount());//订单金额（元）
        model.setSubject(alipayBean.getSubject());//订单名称
        model.setBody(alipayBean.getBody());//商品描述，可空
        model.setProductCode("QUICK_MSECURITY_PAY");//产品代码，固定QUICK_WAP_PAY
        model.setTimeoutExpress("15m");//最晚付款时间
        //建议保存唯一订单号 方便于回调查看是否支付成功
//        user.set***(ordernum);
//        userDao.****(user);

        request.setBizModel(model);
        request.setNotifyUrl(Notify_Url);//异步请求地址  回调查看是否支付成功
        String result;
        //
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            result = response.getBody();
            log.info("调用支付宝下单接口，响应内容:{}",result);
		//	result = new String(result.getBytes("ISO-8859-1"), "utf-8");
            //就是orderString 可以直接给客户端请求，无需再做处理。
            success.put("success", result);

            return success;
        } catch (AlipayApiException e) {
            System.out.println("错误");
            e.printStackTrace();
        }
        success.put("success", "支付失败");
        return success;
    }
    /*
    * 支付宝回调
    * */
    @RequestMapping(value="/app/payment/pay_s")
    public String asy(HttpServletRequest request) throws UnsupportedEncodingException {
        log.info("===================支付宝回调开始==================================");
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        Map<Object, Object> resultMap = new HashMap<>();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        String message = "";
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean flag = false;
        try {
            flag = AlipaySignature.rsaCheckV1(params ,AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET,"RSA2");
            if (flag) {
                try {
                    //商户订单号
                    String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                    //支付宝交易号
                    String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
                    //付款金额
                    String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
                    String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
                    //判断是否支付成功
                    if ("TRADE_SUCCESS".equals(trade_status)) {
                        log.info("支付宝回调成功！订单号："+out_trade_no+",支付宝交易号:"+trade_no+",实际付款金额："+total_amount);
                        //接下来对业务进行数据处理，把订单状态修改为待发货

                        Result result = new Result();
                        try{
                            result = orderClient.updateOrder(out_trade_no,2);
                        }catch (Exception e){
                            message = "修改订单异常！"+"订单号："+out_trade_no+",异常捕获:"+e.getCause();
                            PaymentLog paymentLog = new PaymentLog();
                            paymentLog.setOrderNo(out_trade_no);
                            paymentLog.setAlipayTradeNo(trade_no);
                            paymentLog.setRealAmount(new BigDecimal(total_amount).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                            paymentLog.setMessage(message);
                            paymentLog.setStatus(3);
                            paymentLog.setDateTime(LocalDateTime.now());
                            paymentLogService.insertLog(paymentLog);
                            log.info(message);
                            log.info("===================支付宝回调结束==================================");
                            return "success";
                        }

                        JSONObject jsonObject = JSONObject.fromObject(result);
                        if (jsonObject.getInt("code")!=100000){
                            message = "修改订单失败！错误码："+jsonObject.getInt("code")+",订单号："+out_trade_no+",支付宝交易号:"+trade_no+",实际付款金额："+total_amount;
                            PaymentLog paymentLog = new PaymentLog();
                            paymentLog.setOrderNo(out_trade_no);
                            paymentLog.setAlipayTradeNo(trade_no);
                            paymentLog.setRealAmount(new BigDecimal(total_amount).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                            paymentLog.setMessage(message);
                            paymentLog.setStatus(2);
                            paymentLog.setDateTime(LocalDateTime.now());
                            paymentLogService.insertLog(paymentLog);
                            log.info(message);
                            log.info("===================支付宝回调结束==================================");
                            return "success";
                        }else {
                            message = "修改订单成功！"+"订单号："+out_trade_no+",支付宝交易号:"+trade_no+",实际付款金额："+total_amount;
                            PaymentLog paymentLog = new PaymentLog();
                            paymentLog.setOrderNo(out_trade_no);
                            paymentLog.setAlipayTradeNo(trade_no);
                            paymentLog.setRealAmount(new BigDecimal(total_amount).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                            paymentLog.setMessage(message);
                            paymentLog.setStatus(1);
                            paymentLog.setDateTime(LocalDateTime.now());
                            paymentLogService.insertLog(paymentLog);
                            log.info(message);
                            log.info("===================支付宝回调结束==================================");
                            return "success";
                        }
                    }else {
                        message = "1支付宝支付失败！订单号："+out_trade_no+",支付宝交易号:"+trade_no+",实际付款金额："+total_amount;
                        PaymentLog paymentLog = new PaymentLog();
                        paymentLog.setOrderNo(out_trade_no);
                        paymentLog.setAlipayTradeNo(trade_no);
                        paymentLog.setRealAmount(new BigDecimal(total_amount).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                        paymentLog.setMessage(message);
                        paymentLog.setStatus(0);
                        paymentLog.setDateTime(LocalDateTime.now());
                        paymentLogService.insertLog(paymentLog);
                        log.info(message);
                        log.info("===================支付宝回调结束==================================");
                        return "success";
                    }

                } catch (Exception e) {
                    //商户订单号
                    String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                    //支付宝交易号
                    String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
                    //付款金额
                    String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

                    message = "支付宝回调执行失败！订单号："+out_trade_no+",支付宝交易号:"+trade_no+",实际付款金额："+total_amount+";捕获异常："+e.getCause();

                    PaymentLog paymentLog = new PaymentLog();
                    paymentLog.setOrderNo(out_trade_no);
                    paymentLog.setAlipayTradeNo(trade_no);
                    paymentLog.setRealAmount(new BigDecimal(total_amount).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                    paymentLog.setMessage(message);
                    paymentLog.setStatus(3);
                    paymentLog.setDateTime(LocalDateTime.now());
                    paymentLogService.insertLog(paymentLog);
                    // TODO Auto-generated catch block
                    log.info(message);
                    e.printStackTrace();
                    log.info("===================支付宝回调结束==================================");
                    return "success";
                }

            } else {
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
                //付款金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

                message = "支付宝回调失败！";
                PaymentLog paymentLog = new PaymentLog();
                paymentLog.setOrderNo(out_trade_no);
                paymentLog.setAlipayTradeNo(trade_no);
                paymentLog.setRealAmount(new BigDecimal(total_amount).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                paymentLog.setMessage(message);
                paymentLog.setStatus(4);
                paymentLog.setDateTime(LocalDateTime.now());
                paymentLogService.insertLog(paymentLog);
                log.info(message);
                log.info("===================支付宝回调结束==================================");
                return "success";
            }

        } catch (AlipayApiException e) {

            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            message = "3支付宝回调执行失败！订单号："+out_trade_no+",支付宝交易号:"+trade_no+",实际付款金额："+total_amount+";捕获异常："+e.getCause();

            PaymentLog paymentLog = new PaymentLog();
            paymentLog.setOrderNo(out_trade_no);
            paymentLog.setAlipayTradeNo(trade_no);
            paymentLog.setRealAmount(new BigDecimal(total_amount).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            paymentLog.setMessage(message);
            paymentLog.setStatus(4);
            paymentLog.setDateTime(LocalDateTime.now());
            paymentLogService.insertLog(paymentLog);
            // TODO Auto-generated catch block
            log.info(message);
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.info("===================支付宝回调结束==================================");
            return "success";
        }
//        log.info("===================支付宝回调结束==================================");
//        return "f";
    }
    @RequestMapping(value="/payment/zfb",method= RequestMethod.POST)
    public Result zfb(@RequestBody AlipayBean alipayBean){
        String payOrderByAliWAP = paymentService.createPayOrderByAliWAP(alipayBean);
        return new Result(CommonErrorCode.SUCCESS,payOrderByAliWAP);
    }
    @RequestMapping(value="/hd",method= RequestMethod.GET)
    public Result zfb(HttpServletRequest request,@RequestParam String outTradeNo){
        PaymentResponseDTO paymentResponseDTO = paymentService.queryPayOrderByAli(outTradeNo);
        return new Result(CommonErrorCode.SUCCESS,paymentResponseDTO);
    }

}

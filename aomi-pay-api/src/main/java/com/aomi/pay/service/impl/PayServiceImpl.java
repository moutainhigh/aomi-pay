package com.aomi.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.aomi.pay.exception.BusinessException;
import com.aomi.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PayServiceImpl implements PayService {

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
    /**
     * 调用支付宝的下单接口
     *
     *
     * @param alipayBean 业务参数（商户订单号，订单标题，订单描述,,）F
     * @return 统一返回PaymentResponseDTO
     */
  /*  @Override
    public String createPayOrderByAliWAP(AlipayBean alipayBean) throws BusinessException {
        AliConfigParam aliConfigParam=new AliConfigParam();
        aliConfigParam.setUrl(URL);
        aliConfigParam.setAppId(APP_ID);
        aliConfigParam.setRsaPrivateKey(APP_PRIVATE_KEY);
        aliConfigParam.setFormat(FORMAT);
        aliConfigParam.setCharest(CHARSET);
        aliConfigParam.setAlipayPublicKey(ALIPAY_PUBLIC_KEY);
        aliConfigParam.setSigntype(SIGN_TYPE);
        //aliConfigParam.setReturnUrl("");
        aliConfigParam.setNotifyUrl("http://app.cloudbest.shop:8888/app/payment/pay_s");
        String url = aliConfigParam.getUrl();//支付宝接口网关地址
        String appId = aliConfigParam.getAppId();//支付宝应用id
        String rsaPrivateKey = aliConfigParam.getRsaPrivateKey();//应用私钥
        String format = aliConfigParam.getFormat();//json格式
        String charest = aliConfigParam.getCharest();//编码
        String alipayPublicKey = aliConfigParam.getAlipayPublicKey();//支付宝公钥
        String signtype = aliConfigParam.getSigntype();//签名算法
        // String returnUrl = aliConfigParam.getReturnUrl();//支付成功跳转的url
        String notifyUrl = aliConfigParam.getNotifyUrl();//支付结果异步通知的url
        //构造sdk的客户端对象
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, rsaPrivateKey, format, charest, alipayPublicKey, signtype); //获得初始化的AlipayClient
        AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();//创建API对应的request
        AlipayTradeAppPayModel model = new
                AlipayTradeAppPayModel();
        model.setOutTradeNo(alipayBean.getOutTradeNo());//商户的订单，就是本平台的订单
//        double v = 0.01 * (Integer.valueOf(RandomUuidUtil.generateNumString(1))+1);
//        model.setTotalAmount(String.valueOf(v));//订单金额（元）
        model.setTotalAmount(alipayBean.getTotalAmount());//订单金额（元）
        model.setSubject(alipayBean.getSubject());//订单名称
        model.setBody(alipayBean.getBody());//商品描述，可空
        model.setProductCode("QUICK_MSECURITY_PAY");//产品代码，固定QUICK_WAP_PAY
        model.setTimeoutExpress("15m");//最晚付款时间
        model.setTimeExpire(alipayBean.getExpireTime());//订单过期时间
        alipayRequest.setBizModel(model);
        //alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);
        String orderString = "";
        try {
            //请求支付宝下单接口,发起http请求
            log.info("支付宝SDK请求参数："+ JSON.toJSONString(alipayRequest));
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(alipayRequest);
            log.info("调用支付宝下单接口，响应内容:{}",response.getBody());
            orderString = response.getBody();
            //向MQ发一条延迟消息,支付结果查询
//            PaymentResponseDTO<AliConfigParam> notice = new PaymentResponseDTO<AliConfigParam>();
//            notice.setOutTradeNo(alipayBean.getOutTradeNo());//平台的订单
//            notice.setContent(aliConfigParam);
//            notice.setMsg("ALIPAY_WAP");//标识是查询支付宝的接口
            //发送消息
            //payProducer.payOrderNotice(notice);
            return orderString;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new BusinessException(CommonErrorCode.E_400002);
        }
    }
    @Override
    public PaymentResponseDTO queryPayOrderByAli(String outTradeNo) throws BusinessException {
        AliConfigParam aliConfigParam=new AliConfigParam();
        aliConfigParam.setUrl(URL);
        aliConfigParam.setAppId(APP_ID);
        aliConfigParam.setRsaPrivateKey(APP_PRIVATE_KEY);
        aliConfigParam.setFormat(FORMAT);
        aliConfigParam.setCharest(CHARSET);
        aliConfigParam.setAlipayPublicKey(ALIPAY_PUBLIC_KEY);
        aliConfigParam.setSigntype(SIGN_TYPE);
        aliConfigParam.setReturnUrl("");
        aliConfigParam.setNotifyUrl("http://app.cloudbest.shop:8888/app/payment/pay_s");
        String url = aliConfigParam.getUrl();//支付宝接口网关地址
        String appId = aliConfigParam.getAppId();//支付宝应用id
        String rsaPrivateKey = aliConfigParam.getRsaPrivateKey();//应用私钥
        String format = aliConfigParam.getFormat();//json格式
        String charest = aliConfigParam.getCharest();//编码
        String alipayPublicKey = aliConfigParam.getAlipayPublicKey();//支付宝公钥
        String signtype = aliConfigParam.getSigntype();//签名算法
        String returnUrl = aliConfigParam.getReturnUrl();//支付成功跳转的url
        String notifyUrl = aliConfigParam.getNotifyUrl();//支付结果异步通知的url

        //构造sdk的客户端对象
        AlipayClient alipayClient = new DefaultAlipayClient(url, appId, rsaPrivateKey, format, charest, alipayPublicKey, signtype); //获得初始化的AlipayClient
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeWapPayModel model  = new AlipayTradeWapPayModel();
        model.setOutTradeNo(outTradeNo);//商户的订单，就是闪聚平台的订单
        request.setBizModel(model);
        AlipayTradeQueryResponse response = null;
        try {
            //请求支付宝订单状态查询接口
            response = alipayClient.execute(request);
            //支付宝响应的code，10000表示接口调用成功
            String code = response.getCode();
            if(AliCodeConstants.SUCCESSCODE.equals(code)){
                String tradeStatusString = response.getTradeStatus();
                //解析支付宝返回的状态，解析成闪聚平台的TradeStatus
                TradeStatus tradeStatus = covertAliTradeStatusToShanjuCode(tradeStatusString);
                //String tradeNo(支付宝订单号), String outTradeNo（闪聚平台的订单号）, TradeStatus tradeState（订单状态）, String msg（返回信息）
                return PaymentResponseDTO.success(response.getTradeNo(),response.getOutTradeNo(),tradeStatus,response.getMsg());
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //String msg, String outTradeNo, TradeStatus tradeState
        return PaymentResponseDTO.fail("支付宝订单状态查询失败",outTradeNo,TradeStatus.UNKNOWN);
    }


    //解析支付宝的订单状态为本平台的状态
    private TradeStatus covertAliTradeStatusToShanjuCode(String aliTradeStatus){
        switch (aliTradeStatus){
            case AliCodeConstants.TRADE_FINISHED:
            case AliCodeConstants.TRADE_SUCCESS:
                return TradeStatus.SUCCESS;//业务交易支付 明确成功
            case AliCodeConstants.TRADE_CLOSED:
                return TradeStatus.REVOKED;//交易已撤销
            case    AliCodeConstants.WAIT_BUYER_PAY:
                return TradeStatus.USERPAYING;//交易新建，等待支付
            default:
                return TradeStatus.FAILED;//交易失败
        }
    }*/

}

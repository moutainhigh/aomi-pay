package com.aomi.pay.service.impl;

import com.aomi.pay.constants.ApiPayConstans;
import com.aomi.pay.constants.PayConstans;
import com.aomi.pay.dto.JsPayDTO;
import com.aomi.pay.service.PayService;
import com.aomi.pay.util.AmountUtil;
import com.aomi.pay.util.GeneralConvertorUtil;
import com.aomi.pay.util.SdkUtil;
import com.aomi.pay.vo.JsPayVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单交易管理Service实现类
 *
 * @author : hdq
 * @date 2020/8/7
 */
@Slf4j
@Service
@RefreshScope
public class PayServiceImpl implements PayService {

    /**
     * 扫码支付
     */
    @Value("${api_route.pay.online_trade}")
    private String routeOnlineTrade;

    /**
     * 查询订单
     */
    @Value("${api_route.pay.online_query}")
    private String routeOnlineQuery;

    /**
     * 机构应用id
     */
    @Value("${inst-id}")
    private String instId;

    /**
     * 机构应用id
     */
    @Value("${version}")
    private String version;

    /**
     * h5支付
     */
    @Override
    public Object onlineTrade(JsPayDTO jsPayDTO) throws Exception {
        JsPayVO jsPayVO = JsDTOCopyVO(jsPayDTO);
        //h5支付接口调用
        return SdkUtil.post(jsPayVO, routeOnlineTrade);
    }

    /**
     * @author hdq
     * @date 2020/8/8
     * @desc 本系统参数转换成调用环迅接口需要的参数
     **/
    private JsPayVO JsDTOCopyVO(JsPayDTO jsPayDTO) {
        JsPayVO jsPayVO = GeneralConvertorUtil.convertor(jsPayDTO, JsPayVO.class);
        //根据支付方式的不同调用支付接口
        if (jsPayDTO.getPayType() == PayConstans.PAY_TYPE_ZFB) {
            jsPayVO.setServiceId(ApiPayConstans.SERVICE_ID_ZFB);
        } else if (jsPayDTO.getPayType() == PayConstans.PAY_TYPE_WX) {
            jsPayVO.setServiceId(ApiPayConstans.SERVICE_ID_WX);
            //subAppid 只有微信支付是用到
            //TODO  subAppid暂时写死
            jsPayVO.setSubAppid("wx57f9d11132fc79c1");//测试用的微信 subAppid
            //jsPayVO.setSubAppid("wx57f9d11132fc79c1");//服务号appid
        }
        jsPayVO.setVersion(version);
        //TODO  userId暂时写死
        jsPayVO.setUserId("oXpzSv9AcnTkxsErnGUKCDzZIZBs");//测试用的微信  userid
        //结算周期转换
        switch (jsPayDTO.getSettleType()) {
            case PayConstans.SETTLE_TYPE_T1:
                jsPayVO.setSettleType(ApiPayConstans.SETTLE_TYPE_T1);
                break;
            case PayConstans.SETTLE_TYPE_T0:
                jsPayVO.setSettleType(ApiPayConstans.SETTLE_TYPE_T0);
                break;
            case PayConstans.SETTLE_TYPE_S0:
                jsPayVO.setSettleType(ApiPayConstans.SETTLE_TYPE_S0);
                break;
            case PayConstans.SETTLE_TYPE_D0:
                jsPayVO.setSettleType(ApiPayConstans.SETTLE_TYPE_D0);
                break;
            default:
                jsPayVO.setSettleType(ApiPayConstans.SETTLE_TYPE_T1);
        }
        jsPayVO.setIsvOrgId(instId);
        //金额转换
        jsPayVO.setAmount(AmountUtil.changeY2F(jsPayVO.getAmount()));
        log.info("jsPayVO:{}", jsPayVO);
        return jsPayVO;
    }

}




























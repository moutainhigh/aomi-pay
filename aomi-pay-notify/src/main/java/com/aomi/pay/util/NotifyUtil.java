package com.aomi.pay.util;

import com.aomi.pay.MessageVO;
import com.aomi.pay.constants.SysConstants;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @author hdq
 * @Description: 播报工具类
 */
@Slf4j
public class NotifyUtil {

    /**
     * @Param audioCode 播报设备码  audioType 播报设备类型 payType支付类型  amount 支付金额
     * @author hdq
     * @date 2020/8/22
     * @description: 播报调用
     **/
    public static void send(String audioCode, int audioType, int payType, BigDecimal amount) {
        //播报消息体
        String msg = ConversionUtil.convertPayStatus(payType).concat("到账").concat(amount.toString()).concat("元");
        if (audioType == SysConstants.AUTO_TYPE_DEFAULT) {
            //调通知
            MessageVO messageVO = new MessageVO();
            messageVO.setMsg(msg);
            PopPubUtil.send(audioCode, messageVO);
        }
    }

}

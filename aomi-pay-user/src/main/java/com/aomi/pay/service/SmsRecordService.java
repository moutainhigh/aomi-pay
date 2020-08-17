package com.aomi.pay.service;

import com.aomi.pay.entity.SmsRecord;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */

public interface SmsRecordService {
    /**
     * 发送短信
     * @param x
     * @param phone
     * @return
     */
    String sendSms(int x, String phone);

    /**
     * 创建短信记录
     * @param smsRecord
     * @return
     */
    SmsRecord createSmsRecord(SmsRecord smsRecord);

    /**
     * 通过手机号获得验证码
     * @param phone
     * @return
     */
    String getCodeByPhone(String phone);
    /**
     * 通过手机号获取最后一条短信记录
     */
    SmsRecord lastSms(String phone);
    /**
     * 通过ip地址获取最后一条短信记录
     */
    SmsRecord lastSmsIp(String ipAddr);
}

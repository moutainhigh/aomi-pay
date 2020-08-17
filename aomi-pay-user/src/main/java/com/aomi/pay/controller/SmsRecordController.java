package com.aomi.pay.controller;


import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.entity.SmsRecord;
import com.aomi.pay.exception.BusinessException;
import com.aomi.pay.mapper.SmsRecordMapper;
import com.aomi.pay.service.SmsRecordService;
import com.aomi.pay.util.IPUtil;
import com.aomi.pay.util.PhoneUtil;
import com.aomi.pay.util.StringUtil;
import com.aomi.pay.vo.BaseResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * <p>
 * 用户登录表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@RestController
@CrossOrigin
public class SmsRecordController {

    @Autowired
    private SmsRecordService smsRecordService;
    @Autowired
    private SmsRecordMapper smsRecordMapper;
    /**
     * 发送验证码
     */
    @RequestMapping(value = "/user/merchant/sendSms",method = RequestMethod.POST)
    public BaseResponse UserLogin(HttpServletRequest request, @RequestBody JSONObject str) {
        int x =0;
        String phone = str.getString("phone");
        /*String flag = null;
        try{
            flag = str.getString("flag");
        }catch (Exception e){
            flag = "register";
        }*/

        if (StringUtil.isBlank(phone)){
            return new BaseResponse("900112","手机号不能为空");
        }
        if(!PhoneUtil.isMobileSimple(phone)){
            return new BaseResponse("900123","手机号码格式不正确");
        }
        try{
            String ip = IPUtil.getIpAddr(request);

            int count = smsRecordMapper.selectCount(new LambdaQueryWrapper<SmsRecord>()
                    .eq(SmsRecord::getCreateDate, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .eq(SmsRecord::getPhone,phone));
            int count1 = smsRecordMapper.selectCount(new LambdaQueryWrapper<SmsRecord>()
                    .eq(SmsRecord::getCreateDate, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .eq(SmsRecord::getIpAddress,ip));
            if (count>=10|| count1>=10){
                return new BaseResponse("900131","今日已经发送十次，请明日再进行尝试");
            }else if (count>0){
                SmsRecord lastSms = smsRecordService.lastSms(phone);
                Duration duration = Duration.between(lastSms.getCreateTime(),LocalDateTime.now());
                int time = (int)duration.toMillis();
                if (time<60*1000){
                    return new BaseResponse("900132","请"+(60000-time)/1000+"秒后再尝试发送");
                }
            }
            Random random = new Random();
            x = random.nextInt(899999) + 100000;
            smsRecordService.sendSms(x,phone);
            SmsRecord smsRecord = new SmsRecord();

            smsRecord.setContent(String.valueOf(x));
            smsRecord.setVeriCode(String.valueOf(x));
            smsRecord.setPhone(phone);
            smsRecord.setIpAddress(ip);
            smsRecord.setCreateDate(LocalDateTime.now());
            smsRecord.setCreateTime(LocalDateTime.now());
            smsRecordService.createSmsRecord(smsRecord);
        }catch (BusinessException businessException){
            return new BaseResponse(false,businessException.getCode(),businessException.getDesc());
        }

        return new BaseResponse(CommonErrorCode.SUCCESS);
    }

}

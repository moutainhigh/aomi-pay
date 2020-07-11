package com.cloudbest.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.common.util.IPUtil;
import com.cloudbest.common.util.PhoneUtil;
import com.cloudbest.common.util.StringUtil;
import com.cloudbest.user.entity.CustomerLogin;
import com.cloudbest.user.entity.SmsRecord;
import com.cloudbest.user.mapper.CustomerLoginMapper;
import com.cloudbest.user.mapper.SmsRecordMapper;
import com.cloudbest.user.service.SmsRecordService;
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
    @Autowired
    private CustomerLoginMapper customerLoginMapper;
    /**
     * 发送验证码
     */
    @RequestMapping(value = "/app/user/sendSms",method = RequestMethod.POST)
    public Result UserLogin(HttpServletRequest request, @RequestBody JSONObject str) {
        int x =0;
        String phone = str.getString("phone");
        String flag = str.getString("flag");
        if (StringUtil.isBlank(phone)){
            return new Result(900112,"手机号不能为空",false);
        }
        if(!PhoneUtil.isMobileSimple(phone)){
            return new Result(900123,"手机号码格式不正确",false);
        }
        switch (flag){
            case "register":
                if (customerLoginMapper.selectCount(new LambdaQueryWrapper<CustomerLogin>().eq(CustomerLogin::getMobilePhone,phone))>0){
                    return new Result(900107,"发送验证码错误",false);
                }
                break;
            case "losePsw":
                if (customerLoginMapper.selectCount(new LambdaQueryWrapper<CustomerLogin>().eq(CustomerLogin::getMobilePhone,phone))==0){
                    return new Result(900107,"发送验证码错误",false);
                }
                break;
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
                return new Result(900131,"今日已经发送十次，请明日再进行尝试",false);
            }else if (count>0){
                SmsRecord lastSms = smsRecordService.lastSms(phone);
                Duration duration = Duration.between(lastSms.getCreateTime(),LocalDateTime.now());
                int time = (int)duration.toMillis();
                if (time<60*1000){
                    return new Result(900132,"请"+(60000-time)/1000+"秒后再尝试发送",false);
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
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }

        return new Result(CommonErrorCode.SUCCESS);
    }

}

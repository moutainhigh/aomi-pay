package com.aomi.pay.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.aomi.pay.entity.SmsRecord;
import com.aomi.pay.mapper.SmsRecordMapper;
import com.aomi.pay.service.SmsRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@Service
@Transactional
public class SmsRecordServiceImpl implements SmsRecordService {

    @Autowired
    private SmsRecordMapper smsRecordMapper;

    @Override
    public String sendSms(int x,String phone) {


        String param = "您的验证码是" + x + "，请于2分钟内填写。如非本人操作，请忽略本短信";
        String url = "";
        url = "http://118.31.17.45:8899/sms.aspx" + "?action=send&userid=4324&account=DY2020&password=app123456&mobile="+phone+"&content="+param+"&sendTime=&extno=";
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> resultStr = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        String responseCode = resultStr.getBody();
        return responseCode;
    }

    @Override
    public SmsRecord createSmsRecord(SmsRecord smsRecord) {
        int result = smsRecordMapper.insert(smsRecord);
        if (result==1){
            return smsRecord;
        }
        return null;
    }

    @Override
    public String getCodeByPhone(String phone) {
        if(lastSms(phone)!=null){
            return lastSms(phone).getVeriCode();
        }
        return null;
    }

    @Override
    public SmsRecord lastSms(String phone) {
        QueryWrapper<SmsRecord> queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone", phone);
        queryWrapper.orderByDesc("create_time");
        List<SmsRecord> smsRecords = smsRecordMapper.selectList(queryWrapper);
        if (smsRecords.size()>0){
            return smsRecords.get(0);
        }
        return null;
    }

    @Override
    public SmsRecord lastSmsIp(String ipAddr) {
        QueryWrapper<SmsRecord> queryWrapper = new QueryWrapper();
        queryWrapper.eq("ip_address", ipAddr);
        queryWrapper.orderByDesc("create_time");
        List<SmsRecord> smsRecords = smsRecordMapper.selectList(queryWrapper);
        if (smsRecords.size()>0){
            return smsRecords.get(0);
        }
        return null;
    }
}

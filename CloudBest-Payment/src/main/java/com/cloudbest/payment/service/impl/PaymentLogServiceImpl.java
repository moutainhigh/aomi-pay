package com.cloudbest.payment.service.impl;

import com.cloudbest.payment.entity.PaymentLog;
import com.cloudbest.payment.mapper.PaymentLogMapper;
import com.cloudbest.payment.service.PaymentLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentLogServiceImpl implements PaymentLogService {

    @Autowired
    private PaymentLogMapper paymentLogMapper;

    @Override
    public void insertLog(PaymentLog paymentLog) {
        paymentLogMapper.insert(paymentLog);
    }
}

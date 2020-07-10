package com.cloudbest.payment.service;

import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.payment.dto.AlipayBean;
import com.cloudbest.payment.dto.PaymentResponseDTO;
import com.cloudbest.payment.entity.PaymentLog;

public interface PaymentLogService {


    void insertLog(PaymentLog paymentLog);
}

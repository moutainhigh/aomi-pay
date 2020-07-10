package com.cloudbest.user.service.impl;


import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.user.entity.CustomerLoginLog;
import com.cloudbest.user.mapper.CustomerLoginLogMapper;
import com.cloudbest.user.service.CustomerLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户登陆日志表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@Service
@Transactional
public class CustomerLoginLogServiceImpl implements CustomerLoginLogService {
    @Autowired
    private CustomerLoginLogMapper customerLoginLogMapper;
    @Override
    public int loginLog(CustomerLoginLog customerLoginLog) {
        if (customerLoginLog==null){
            throw new BusinessException(CommonErrorCode.E_900129.getCode(),"登陆信息为空");
        }
        int i = customerLoginLogMapper.insert(customerLoginLog);
        return i;
    }
}

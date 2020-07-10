package com.cloudbest.user.service;

import com.cloudbest.user.entity.CustomerInf;
import com.cloudbest.user.entity.CustomerLogin;
import com.cloudbest.user.entity.CustomerLoginLog;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登陆日志表 服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Service
public interface CustomerLoginLogService {
    /**
     * 登陆纪录
     */
    int loginLog(CustomerLoginLog customerLoginLog);
}

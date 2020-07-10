package com.cloudbest.user.service;


import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.user.entity.CustomerLogin;
import com.cloudbest.user.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户登录表 服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Service
public interface CustomerLoginService {

    /*
     * 用户登陆
     * */
    UserVO findbyLogin(HttpServletRequest request, String mobile, String password, CustomerLogin customerLogin)throws BusinessException;
    /**
     * 修改密码
     */
    void updatePsw(String token,String oldPsw,String newPsw)throws Exception;
    /**
     * 忘记密码
     */
    void losePsw(String mobile,String code,String newPsw);
}

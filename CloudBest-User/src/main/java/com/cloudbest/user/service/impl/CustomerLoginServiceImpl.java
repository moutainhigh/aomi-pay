package com.cloudbest.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.util.IPUtil;
import com.cloudbest.common.util.MD5Util;
import com.cloudbest.common.util.PhoneUtil;
import com.cloudbest.common.util.TokenUtil;
import com.cloudbest.user.entity.CustomerInf;
import com.cloudbest.user.entity.CustomerLogin;
import com.cloudbest.user.entity.CustomerLoginLog;
import com.cloudbest.user.entity.SmsRecord;
import com.cloudbest.user.mapper.CustomerInfMapper;
import com.cloudbest.user.mapper.CustomerLoginLogMapper;
import com.cloudbest.user.mapper.CustomerLoginMapper;
import com.cloudbest.user.service.CustomerLoginLogService;
import com.cloudbest.user.service.CustomerLoginService;
import com.cloudbest.user.service.SmsRecordService;
import com.cloudbest.user.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * 用户登录表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@Service
@Transactional
public class CustomerLoginServiceImpl implements CustomerLoginService {
    @Autowired
    private CustomerLoginMapper loginMapper;
    @Autowired
    private CustomerLoginLogService customerLoginLogService;
    @Autowired
    private CustomerInfMapper customerInfMapper;
    @Autowired
    private CustomerLoginLogMapper customerLoginLogMapper;
    @Autowired
    private SmsRecordService smsRecordService;
    @Override
    public UserVO findbyLogin(HttpServletRequest request, String mobile, String password,CustomerLogin customerLogin) throws BusinessException {
        String ip = IPUtil.getIpAddr(request);
        UserVO user=new UserVO();
        CustomerInf customerInf = customerInfMapper.selectOne(new LambdaQueryWrapper<CustomerInf>().eq(CustomerInf::getMobilePhone,mobile));
        Long customerId = customerInf.getCustomerId();
        QueryWrapper<CustomerLoginLog> queryWrapper = new QueryWrapper();
        queryWrapper.eq("customer_id",customerId);
        queryWrapper.orderByDesc("login_time");
        List<CustomerLoginLog> customerLoginLogs = customerLoginLogMapper.selectList(queryWrapper);
        if(customerLoginLogs.size()>0){
            CustomerLoginLog customerLoginLog = customerLoginLogs.get(0);
            user.setLastLoginTime(customerLoginLog.getLoginTime());
        }
        user.setCustomerId(customerInf.getCustomerId());
        user.setMobilePhone(mobile);
        user.setHeadportrait(customerInf.getHeadportrait());
        if (customerInf.getScreenname()==null || customerInf.getScreenname().equals("") ){
            user.setScreenname(mobile);
        }else {
            user.setScreenname(customerInf.getScreenname());
        }
        user.setCustomerName(customerInf.getCustomerName());
        user.setIdentityCardNo(customerInf.getIdentityCardNo());
        user.setGender(customerInf.getGender());
        user.setRegisterTime(customerInf.getRegisterTime());
        user.setBirthday(customerInf.getBirthday());
        String token = TokenUtil.createToken(customerLogin.getCustomerId(),mobile);
        user.setToken(token);

        //插入登陆日志表
        CustomerLoginLog loginLog1 = new CustomerLoginLog();
        loginLog1.setCustomerId(customerLogin.getCustomerId());
        loginLog1.setLoginDate(LocalDateTime.now());
        loginLog1.setLoginTime(LocalDateTime.now());
        loginLog1.setLoginIp(ip);
        loginLog1.setLoginType(1);
        customerLoginLogService.loginLog(loginLog1);
        return user;
    }

    @Override
    public void updatePsw(String token,String oldPsw,String newPsw)throws Exception {
        if(token==null){
            throw new BusinessException(CommonErrorCode.E_900121.getCode(),"token验证失败");
        }
        if (oldPsw==null){
            throw new BusinessException(CommonErrorCode.E_900111.getCode(),"密码为空");
        }
        if (newPsw==null){
            throw new BusinessException(CommonErrorCode.E_900119.getCode(),"新密码为空");
        }
        if(oldPsw.equals(newPsw)){
            throw new BusinessException(CommonErrorCode.E_900128.getCode(),"新旧密码不能相同！请修改后重试~");
        }
        int count = loginMapper.selectCount(new LambdaQueryWrapper<CustomerLogin>()
                .eq(CustomerLogin::getCustomerId,TokenUtil.getUserId(token))
                .eq(CustomerLogin::getPassword,MD5Util.getMd5(oldPsw+"aomi1003")));
        if (count==0){
            throw new BusinessException(CommonErrorCode.E_900115.getCode(),"密码不正确");
        }
        String savePsw = MD5Util.getMd5(newPsw+"aomi1003");
        String phone = TokenUtil.getUserPhone(token);
        loginMapper.updatePsw(phone,savePsw);
    }

    @Override
    public void losePsw(String mobile, String code, String newPsw) {
        if(mobile==null){
            throw new BusinessException(CommonErrorCode.E_900112.getCode(),"手机号为空");
        }
        if(!PhoneUtil.isMobileSimple(mobile)){
            throw new BusinessException(CommonErrorCode.E_900123.getCode(),"手机号码格式不正确");
        }
        if(newPsw==null){
            throw new BusinessException(CommonErrorCode.E_900111.getCode(),"密码为空");
        }
        if (code==null){
            throw new BusinessException(CommonErrorCode.E_900103.getCode(),"验证码为空");
        }
        SmsRecord smsRecord = smsRecordService.lastSms(mobile);
        String codeByPhone = smsRecord.getVeriCode();
        if (!code.equals(codeByPhone)){
            throw new BusinessException(CommonErrorCode.E_900102.getCode(),"验证码不正确");
        }
        //判断验证码是否超过15分钟
        Duration duration = Duration.between(smsRecord.getCreateTime(), LocalDateTime.now());
        int time = (int)duration.toMillis();
        if (time>=15*60*1000){
            throw new BusinessException(CommonErrorCode.E_900134.getCode(),"验证码超时");
        }
        Integer integer = customerInfMapper.selectCount(new LambdaQueryWrapper<CustomerInf>().eq(CustomerInf::getMobilePhone,mobile));
        if(integer==0){
            throw new BusinessException(CommonErrorCode.E_900108.getCode(),"该手机号未注册");
        }
        String savePsw = MD5Util.getMd5(newPsw+"aomi1003");
        loginMapper.updatePsw(mobile,savePsw);
    }

}

package com.cloudbest.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.common.util.*;
import com.cloudbest.user.entity.CustomerInf;
import com.cloudbest.user.entity.CustomerLogin;
import com.cloudbest.user.entity.CustomerLoginLog;
import com.cloudbest.user.entity.SmsRecord;
import com.cloudbest.user.mapper.CustomerInfMapper;
import com.cloudbest.user.mapper.CustomerLoginLogMapper;
import com.cloudbest.user.mapper.CustomerLoginMapper;
import com.cloudbest.user.service.CustomerLoginService;
import com.cloudbest.user.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

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
public class CustomerLoginController {

    @Autowired
    private CustomerLoginService customerLoginService;
    @Autowired
    private CustomerLoginLogMapper customerLoginLogMapper;
    @Autowired
    private CustomerInfMapper customerInfMapper;
    @Autowired
    private CustomerLoginMapper customerLoginMapper;
    /**
     * 用户登录
     */
    @RequestMapping(value = "/app/user/login",method = RequestMethod.POST)
    public Result UserLogin(HttpServletRequest httpServletRequest, @RequestBody JSONObject str) {
        String phone = str.getString("phone");
        String password = str.getString("password");
        log.info("================================用户登录手机号："+phone+",登录密码："+password);
        if(phone==null){
            return new Result(900112,"手机号为空",false);
        }
        if(!PhoneUtil.isMobileSimple(phone)){
            return new Result(900123,"手机号码格式不正确",false);
        }
        String ip = IPUtil.getIpAddr(httpServletRequest);
        CustomerInf userInf = customerInfMapper.selectOne(new LambdaQueryWrapper<CustomerInf>().eq(CustomerInf::getMobilePhone,phone));
        QueryWrapper<CustomerLoginLog> queryWrapper = new QueryWrapper();
        queryWrapper.eq("login_date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        queryWrapper.eq("login_type",0);
        queryWrapper.eq("login_ip",ip);
        QueryWrapper<CustomerLoginLog> queryWrapper1 = new QueryWrapper();
        if (userInf!=null){
            queryWrapper1.eq("login_date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            queryWrapper1.eq("login_type",0);
            queryWrapper1.eq("customer_id",userInf.getCustomerId());
        }
        int count = customerLoginLogMapper.selectCount(queryWrapper);
        int count1 = customerLoginLogMapper.selectCount(queryWrapper1);
        if (count>=10||count1>=10){
            return new Result(900109,"今日输错密码超过10次，暂时无法登陆，请明日再进行尝试",false);
        }
        if(password==null){
            return new Result(900111,"密码为空",false);
        }
        String safePsw = MD5Util.getMd5(password+"aomi1003");
        log.info("================================用户登录手机号："+phone+",加密后登录密码："+safePsw);
        CustomerLogin customerLogin = customerLoginMapper.selectOne(new LambdaQueryWrapper<CustomerLogin>().eq(CustomerLogin::getMobilePhone, phone).eq(CustomerLogin::getPassword, safePsw));

        CustomerLoginLog loginLog = new CustomerLoginLog();
        if(customerLogin==null){
            if (userInf!=null){
                loginLog.setCustomerId(userInf.getCustomerId());
            }
            loginLog.setLoginDate(LocalDateTime.now());
            loginLog.setLoginTime(LocalDateTime.now());
            loginLog.setLoginIp(ip);
            loginLog.setLoginType(0);
            customerLoginLogMapper.insert(loginLog);
            return new Result(900127,"手机号码与密码不匹配",false);
        }
        UserVO user = new UserVO();
        try{
            user = customerLoginService.findbyLogin(httpServletRequest,phone, password,customerLogin);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }

        return new Result(CommonErrorCode.SUCCESS,user);
    }
    /**
     * 修改密码
     */
    @RequestMapping(value = "/app/user/updatePsw/{token}",method = RequestMethod.POST)
    public Result updatePsw(@RequestBody JSONObject str,@PathVariable("token")String token)throws Exception{
        try {
            TokenUtil.getUserId(token);
        }catch (BusinessException businessException){
            return new Result(900121,"token验证失败",false);
        }
        String newPsw = str.getString("newPsw");
        String oldPsw = str.getString("oldPsw");
        try {
            customerLoginService.updatePsw(token,oldPsw,newPsw);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }
    /**
     * 忘记密码
     */
    @RequestMapping(value = "/app/user/losePsw",method = RequestMethod.POST)
    public Result losePsw(@RequestBody JSONObject str){
        String phone = str.getString("phone");
        String code = str.getString("code");
        String newPsw = str.getString("newPsw");
        try {
            customerLoginService.losePsw(phone,code,newPsw);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }
}

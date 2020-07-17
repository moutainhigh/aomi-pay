package com.cloudbest.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudbest.common.constants.ScoreSystemConstants;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.util.MD5Util;
import com.cloudbest.common.util.PhoneUtil;
import com.cloudbest.common.util.RandomUuidUtil;
import com.cloudbest.common.util.TokenUtil;
import com.cloudbest.user.entity.CustomerInf;
import com.cloudbest.user.entity.CustomerLogin;
import com.cloudbest.user.entity.SmsRecord;
import com.cloudbest.user.feign.ItemClient;
import com.cloudbest.user.mapper.CustomerInfMapper;
import com.cloudbest.user.mapper.CustomerLoginMapper;
import com.cloudbest.user.model.GetScoreRecordAndSumScoreResponse;
import com.cloudbest.user.service.CustomerInfService;
import com.cloudbest.user.service.SmsRecordService;
import com.cloudbest.user.vo.CustomerInfVO;
import com.cloudbest.user.vo.ScoreSourceVO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@Service
@Transactional
public class CustomerInfServiceImpl implements CustomerInfService {

    @Autowired
    private CustomerInfMapper customerInfMapper;
    @Autowired
    private CustomerLoginMapper loginMapper;
    @Autowired
    private SmsRecordService smsRecordService;

    @Autowired
    private ItemClient itemClient;

    @Override
    public void authentication(String token, String identityCardNo, String customerName) throws Exception {
        long userId = TokenUtil.getUserId(token);
        if (token == null) {
            throw new BusinessException(CommonErrorCode.E_900121.getCode(), "token信息为空");
        }
        if (!PhoneUtil.isIDCard18(identityCardNo)) {
            throw new BusinessException(CommonErrorCode.E_900124.getCode(), "身份证验证失败");
        }
        if (customerName == null) {
            throw new BusinessException(CommonErrorCode.E_900125.getCode(), "姓名为空");
        }
        CustomerInf customerInf = customerInfMapper.selectById(userId);
        if (customerInf == null) {
            throw new BusinessException(CommonErrorCode.E_900126.getCode(), "用户对象为空");
        }
        Integer integer = customerInfMapper.selectCount(new LambdaQueryWrapper<CustomerInf>().eq(CustomerInf::getIdentityCardNo, identityCardNo));
        if (integer > 0) {
            throw new BusinessException(CommonErrorCode.E_900130.getCode(), "身份证号已存在");
        }
        customerInf.setIdentityCardNo(identityCardNo);
        customerInf.setCustomerName(customerName);
        customerInfMapper.updateById(customerInf);

    }

    @Override
    public CustomerInf userRegister(String mobile, String password, String code) throws BusinessException {
        if (mobile == null) {
            throw new BusinessException(CommonErrorCode.E_900112.getCode(), "手机号为空");
        }
        if (!PhoneUtil.isMobileSimple(mobile)) {
            throw new BusinessException(CommonErrorCode.E_900123.getCode(), "手机号码格式不正确");
        }
        if (password == null) {
            throw new BusinessException(CommonErrorCode.E_900111.getCode(), "密码为空");
        }
        if (code == null) {
            throw new BusinessException(CommonErrorCode.E_900103.getCode(), "验证码为空");
        }
        //如果短信验证码不为空， 那么需要
        SmsRecord smsRecord = smsRecordService.lastSms(mobile);
        //判断验证码是否正确
        String codeStr = smsRecord.getVeriCode();
        if (code.equalsIgnoreCase("null") || code == null || code.equalsIgnoreCase("") || !code.equalsIgnoreCase(codeStr)) {
            throw new BusinessException(CommonErrorCode.E_900102.getCode(),"验证码不正确");
        }
        //判断验证码是否超过15分钟
        Duration duration = Duration.between(smsRecord.getCreateTime(), LocalDateTime.now());
        int time = (int)duration.toMillis();
        if (time>=15*60*1000){
            throw new BusinessException(CommonErrorCode.E_900134.getCode(),"验证码超时");
        }
        Integer integer = customerInfMapper.selectCount(new LambdaQueryWrapper<CustomerInf>().eq(CustomerInf::getMobilePhone,mobile));
        if(integer>0){
            throw new BusinessException(CommonErrorCode.E_900113.getCode(),"手机号码已存在");
        }

        CustomerInf customerInf = new CustomerInf();
        CustomerLogin customerLogin = new CustomerLogin();
        //插入登陆表里面
        customerInf.setCustomerId(Long.valueOf(RandomUuidUtil.generateNumString(10)));
        customerLogin.setCustomerId(customerInf.getCustomerId());
        customerLogin.setMobilePhone(mobile);
        String safePsw = MD5Util.getMd5(password + "aomi1003");
        customerLogin.setPassword(safePsw);
        customerLogin.setUserStats(1);
        loginMapper.insert(customerLogin);
        //插入用户信息表里面
        customerInf.setMobilePhone(mobile);
        customerInf.setScreenname(mobile);
        customerInf.setRegisterTime(LocalDateTime.now());
        customerInf.setModifiedTime(LocalDateTime.now());
        customerInfMapper.insert(customerInf);
        return customerInf;
    }

    //根据用户id查询用户信息
    @Override
    public CustomerInf selectUserById(Long customerId) {
        if (customerId == null) {
            throw new BusinessException(CommonErrorCode.E_900117);
        }
        CustomerInf customerInf = this.customerInfMapper.selectOne(new LambdaQueryWrapper<CustomerInf>().eq(CustomerInf::getCustomerId, customerId));
        return customerInf;
    }

    @Override
    public void modifyInf(String token, CustomerInf customerInf) throws Exception {
        if (token == null) {
            throw new BusinessException(CommonErrorCode.E_900126.getCode(), "用户信息为空");
        }
        customerInf.setModifiedTime(LocalDateTime.now());
        /**
         * 更改头像
         */
       /* String img = itemClient.uploadImg(httpServletRequest).toString();
        customerInf.setHeadportrait(img);*/
        //更新用户信息
        customerInfMapper.updateById(customerInf);
    }

    @Override
    public List<CustomerInfVO> queryList(CustomerInfVO info) {
        List<CustomerInfVO> infVOList = new ArrayList<>();
        //分页参数
        int current = 0;
        int size = 0;
        if (info.getCurrent() != null && info.getSize() != null) {
            current = info.getCurrent();
            size = info.getSize();
        } else {
            current = 0;
            size = 200;
        }
        //查询可用图片
        QueryWrapper<CustomerInf> queryWrapper = new QueryWrapper();
        if (info.getCustomerId() != null) {
            queryWrapper.eq("customer_id", info.getCustomerId());
        }
        if (info.getMobilePhone() != null) {
            queryWrapper.eq("mobile_phone", info.getMobilePhone());
        }
        if (info.getScreenname() != null) {
            queryWrapper.eq("screenname", info.getScreenname());
        }
        if (info.getCustomerName() != null) {
            queryWrapper.eq("customer_name", info.getCustomerName());
        }
        if (info.getIdentityCardNo() != null) {
            queryWrapper.eq("identity_card_no", info.getIdentityCardNo());
        }
        Page<CustomerInf> page = new Page<>(current, size);
        IPage<CustomerInf> customerInfIPage = customerInfMapper.selectPage(page, queryWrapper);
        if (customerInfIPage.getTotal() > 0) {
            for (CustomerInf customerInf : customerInfIPage.getRecords()) {
                CustomerInfVO customerInfVO = new CustomerInfVO();
                BeanUtils.copyProperties(customerInf, customerInfVO);
                CustomerLogin loginInfo = loginMapper.selectById(customerInf.getCustomerId());
                if (loginInfo == null) {
                    throw new BusinessException(CommonErrorCode.E_900129);
                }
                customerInfVO.setPassword(loginInfo.getPassword());
                infVOList.add(customerInfVO);
            }
            return infVOList;
        }
        return new ArrayList<>();
    }

    @Override
    public CustomerInfVO queryById(Long customerId) {
        CustomerInf customerInf = customerInfMapper.selectById(customerId);
        if (customerInf == null) {
            throw new BusinessException(CommonErrorCode.E_900126);
        }
        CustomerInfVO customerInfVO = new CustomerInfVO();
        BeanUtils.copyProperties(customerInf, customerInfVO);
        CustomerLogin loginInfo = loginMapper.selectById(customerId);
        if (loginInfo == null) {
            throw new BusinessException(CommonErrorCode.E_900129);
        }
        customerInfVO.setPassword(loginInfo.getPassword());
        return customerInfVO;
    }

    @Override
    public BigDecimal sumScore(Long customerId) {
        BigDecimal score = BigDecimal.ZERO;
        String url = ScoreSystemConstants.SCORE_URL.concat(ScoreSystemConstants.SUM_SCORE);
        MultiValueMap<String, Long> requestEntity = new LinkedMultiValueMap<String, Long>();
        requestEntity.add("cbId", customerId);
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(url, requestEntity, String.class);
        JSONObject jsonObject = JSONObject.fromObject(result);
        if (!jsonObject.getBoolean("success")) {
            throw new BusinessException(999999, "查询购物券失败");
        }
        score = new BigDecimal(jsonObject.getDouble("data")).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return score;
    }

    @Override
    public void yhBind(String mobilePhone, String password, String yhKey) {
        String safePsw = MD5Util.getMd5(password + "aomi1003");
        CustomerLogin customerLogin = loginMapper.selectOne(new LambdaQueryWrapper<CustomerLogin>().eq(CustomerLogin::getMobilePhone, mobilePhone).eq(CustomerLogin::getPassword, safePsw));

        if (customerLogin == null) {
            throw new BusinessException(CommonErrorCode.E_900127.getCode(), "商城用户ID与密码不匹配");
        }
        String url = ScoreSystemConstants.SCORE_URL.concat(ScoreSystemConstants.BIND_YH);
        MultiValueMap<String, Object> requestEntity = new LinkedMultiValueMap<String, Object>();
        requestEntity.add("cbId", customerLogin.getCustomerId());
        requestEntity.add("yhKey", yhKey);
        RestTemplate restTemplate = new RestTemplate();
        String bindYhResult = restTemplate.postForObject(url, requestEntity, String.class);
        JSONObject subScoreObject = JSONObject.fromObject(bindYhResult);
        if (subScoreObject.getInt("code") != 100000) {
            throw new BusinessException(subScoreObject.getInt("code"), subScoreObject.getString("message"));
        }

    }

    @Override
    public Map<String, Object> bindStatus(String yhKey) {
        log.info("=====调用积分系统--获取绑定状态======");
        Map<String, Object> mapResult = new HashMap<>();
        String url = ScoreSystemConstants.SCORE_URL.concat(ScoreSystemConstants.BIND_YH_STATUS);
        MultiValueMap<String, Object> requestEntity = new LinkedMultiValueMap<String, Object>();
        requestEntity.add("yhKey", yhKey);
        RestTemplate restTemplate = new RestTemplate();
        String bindYhResult = restTemplate.postForObject(url, requestEntity, String.class);
        JSONObject subScoreObject = JSONObject.fromObject(bindYhResult);
        log.info("返回:{}", subScoreObject.toString());
        if (subScoreObject.getInt("code") != 100000) {
            throw new BusinessException(CommonErrorCode.E_900135.getCode(), "查询绑定状态失败");
        }
        mapResult.put("msg", subScoreObject.getString("message"));
        mapResult.put("success", subScoreObject.getBoolean("success"));
        return mapResult;
    }

    @Override
    public List<ScoreSourceVO> scoreRecord(Long userId) {
        List<ScoreSourceVO> scoreSourceVOList = new ArrayList<>();
        String url = ScoreSystemConstants.SCORE_URL.concat(ScoreSystemConstants.QUERY_SCORE_LIST);
        MultiValueMap<String, Object> requestEntity = new LinkedMultiValueMap<String, Object>();
        requestEntity.add("cbId", userId);
        RestTemplate restTemplate = new RestTemplate();
        String scoreListResult = restTemplate.postForObject(url, requestEntity, String.class);
        JSONObject scoreListObject = JSONObject.fromObject(scoreListResult);
        if (scoreListObject.getInt("code") != 100000) {
            throw new BusinessException(CommonErrorCode.E_900135.getCode(), "查询列表失败");
        }
        scoreSourceVOList = scoreListObject.getJSONArray("data");
        return scoreSourceVOList;
    }

    @Override
    public void yhBind(Long id, String yhKey) {

        MultiValueMap<String, Object> requestEntity = new LinkedMultiValueMap<String, Object>();
        requestEntity.add("cbId", id);
        requestEntity.add("yhKey", yhKey);
        JSONObject subScoreObject = post(ScoreSystemConstants.SCORE_URL.concat(ScoreSystemConstants.BIND_YH),requestEntity);
        if (subScoreObject.getInt("code") != 100000) {
            throw new BusinessException(subScoreObject.getInt("code"), subScoreObject.getString("message"));
        }

    }

    @Override
    public GetScoreRecordAndSumScoreResponse getScoreRecordAndSumScore(String yhKey) throws Exception {
        MultiValueMap<String, Object> requestEntity = new LinkedMultiValueMap<String, Object>();
        requestEntity.add("yhKey", yhKey);
        JSONObject subScoreObject = post(ScoreSystemConstants.SCORE_URL.concat(ScoreSystemConstants.QUERY_SCORERECORD_SUMSCORE),requestEntity);
        if (subScoreObject.getInt("code") != 100000) {
            throw new BusinessException(subScoreObject.getInt("code"), subScoreObject.getString("message"));
        }
        GetScoreRecordAndSumScoreResponse res = new GetScoreRecordAndSumScoreResponse();
        res.setScoreList(subScoreObject.getJSONObject("data").getJSONArray("scoreList"));
        res.setSumScore(new BigDecimal(subScoreObject.getJSONObject("data").getDouble("sumScore")).setScale(2, BigDecimal.ROUND_HALF_EVEN));
        return res;
    }

    /**
     * 公共调用
     * @param url 请求地址
     * @param map 请求参数
     */
    public JSONObject post(String url, Map map) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(url, map, String.class);
        return JSONObject.fromObject(result);
    }
}

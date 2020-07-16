package com.cloudbest.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.common.util.RSAUtil;
import com.cloudbest.common.util.AliOSSUtil;
import com.cloudbest.common.util.StringUtil;
import com.cloudbest.common.util.TokenUtil;
import com.cloudbest.user.entity.CustomerInf;
import com.cloudbest.user.mapper.CustomerInfMapper;
import com.cloudbest.user.model.GetScoreRecordAndSumScoreResponse;
import com.cloudbest.user.service.CustomerInfService;
import com.cloudbest.user.service.SmsRecordService;
import com.cloudbest.user.vo.CustomerInfVO;
import com.cloudbest.user.vo.ScoreSourceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@RestController
@CrossOrigin
public class CustomerInfController implements Serializable {

    @Autowired
    private CustomerInfService customerInfService;

    @Autowired
    private CustomerInfMapper customerInfMapper;

    /**
     * 身份验证
     */
    @RequestMapping(value = "/app/user/authentication/{token}", method = RequestMethod.POST)
    public Result Authentication(@RequestBody JSONObject str, @PathVariable("token") String token) throws Exception {
        try {
            TokenUtil.getUserId(token);
        } catch (BusinessException businessException) {
            return new Result(900121, "token验证失败", false);
        }
        String identityCardNo = str.getString("identityCardNo");
        String customerName = str.getString("customerName");
        try {
            customerInfService.authentication(token, identityCardNo, customerName);
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(), businessException.getDesc(), false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 用户注册
     */
    @RequestMapping(value = "/app/user/register", method = RequestMethod.POST)
    public Result UserRegister(@RequestBody JSONObject str) throws Exception {
        try {
            String phone = str.getString("phone");
            String password = str.getString("password");
            String code = null;
            try {
                code = str.getString("code");
            } catch (Exception e) {
                code = "0";
            }
            customerInfService.userRegister(phone, password, code);
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(), businessException.getDesc(), false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    //根据用户id查询用户信息
    @RequestMapping(method = RequestMethod.POST, value = "/app/user/selectUser/{token}")
    public Result selectUserById(@RequestParam("customerId") Long customerId, @PathVariable("token") String token) throws Exception {
        try {
            TokenUtil.getUserId(token);
        } catch (BusinessException businessException) {
            return new Result(900121, "token验证失败", false);
        }
        CustomerInf customerInf;
        try {
            customerInf = customerInfService.selectUserById(customerId);
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(), businessException.getDesc(), false);
        }
        return new Result(CommonErrorCode.SUCCESS, customerInf);
    }

    //根据用户id查询用户信息
    @RequestMapping(method = RequestMethod.POST, value = "/user/selectUser")
    public Result selectUserById(@RequestParam("customerId") Long customerId) throws Exception {
        if (customerId == null) {
            return new Result(900117, "用户ID为空", false);
        }
        CustomerInf customerInf;
        try {
            customerInf = customerInfService.selectUserById(customerId);
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(), businessException.getDesc(), false);
        }
        return new Result(CommonErrorCode.SUCCESS, customerInf);
    }

    /**
     * 修改信息
     *
     * @param customerInf
     * @param token
     * @return
     */
    @RequestMapping(value = "/app/user/modifyInf/{token}", method = RequestMethod.POST)
    public Result modifyInf(@RequestBody CustomerInf customerInf, @PathVariable String token) throws Exception {
        try {
            TokenUtil.getUserId(token);
        } catch (BusinessException businessException) {
            return new Result(900121, "token验证失败", false);
        }
        try {
            customerInfService.modifyInf(token, customerInf);
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(), businessException.getDesc(), false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 查询收藏列表
     */
    @RequestMapping(value = "/user/users/queryList", method = RequestMethod.POST)
    public Result queryAddr(@RequestBody CustomerInfVO vo) throws Exception {
        List<CustomerInfVO> customerInfs;
        try {
            customerInfs = customerInfService.queryList(vo);
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(), businessException.getDesc(), false);
        }
        return new Result(CommonErrorCode.SUCCESS, customerInfs);
    }


    /**
     * 查询收藏商品详情
     */
    @RequestMapping(value = "/user/users/queryById", method = RequestMethod.POST)
    public Result queryById(@RequestBody CustomerInfVO vo) throws Exception {
        CustomerInfVO customerInf;
        try {
            customerInf = customerInfService.queryById(vo.getCustomerId());
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(), businessException.getDesc(), false);
        }
        return new Result(CommonErrorCode.SUCCESS, customerInf);
    }

    /**
     * 查询用户总购物券
     */
    @RequestMapping(value = "/app/user/users/sumScore/{token}", method = RequestMethod.POST)
    public Result sumScore(@PathVariable String token) throws Exception {
        if (token == null) {
            return new Result(CommonErrorCode.E_900121);
        }
        Long userId;
        try {
            userId = TokenUtil.getUserId(token);
        } catch (Exception e) {
            return new Result(CommonErrorCode.E_900121);
        }
        BigDecimal score;
        try {
            score = customerInfService.sumScore(userId);
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(), businessException.getDesc(), false);
        }
        return new Result(CommonErrorCode.SUCCESS, score);
    }

    /**
     * 上传头像
     */
    @RequestMapping(value = "/app/user/uploadImg/{token}", method = RequestMethod.POST)
    public Result uploadImg(HttpServletRequest request, @PathVariable("token") String token) throws Exception {
        Long customerId;
        try {
            customerId = TokenUtil.getUserId(token);
        } catch (BusinessException businessException) {
            return new Result(900121, "token验证失败", false);
        }
        String imgurls = "";
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> files = multipartRequest.getFiles("image");
            if (files == null || files.size() == 0) {
                throw new BusinessException(CommonErrorCode.E_901007.getCode(), "请上传至少一张图片");
            }

            String ossObjectNamePrefix = AliOSSUtil.APP_SYS_IMAGETEXT + "-";
            String ossObjectName = "";

            int i = 1;

            if (files != null && files.size() > 0) {
                for (MultipartFile file : files) {

                    String fileName = file.getOriginalFilename();
                    String prefix = fileName.substring(fileName.lastIndexOf("."));
                    fileName = System.currentTimeMillis() + i + prefix;

                    ossObjectName = ossObjectNamePrefix + fileName;
                    AliOSSUtil aliOSSUtil = new AliOSSUtil();
                    try {
                        aliOSSUtil.uploadStreamToOss(ossObjectName, file.getInputStream());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    String fileUrl = aliOSSUtil.getFileUrl(ossObjectName);
                    if (i == 1) {
                        imgurls = imgurls + fileUrl.substring(0, fileUrl.lastIndexOf("?"));
                    } else {
                        imgurls = imgurls + "," + fileUrl.substring(0, fileUrl.lastIndexOf("?"));
                    }
                    i++;
                }
            }
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(), businessException.getDesc(), false);
        }
        CustomerInf customerInf = customerInfMapper.selectOne(new LambdaQueryWrapper<CustomerInf>().eq(CustomerInf::getCustomerId, customerId));
        customerInf.setHeadportrait(imgurls);
        customerInfMapper.updateById(customerInf);
        return new Result(CommonErrorCode.SUCCESS, imgurls);
    }

    /**
     * 判断友惠用户ID是否绑定过
     */
    @RequestMapping(value = "/brand/youhui/users/bindStatus/{yhBind}", method = RequestMethod.POST)
    public Result bindStatus(@PathVariable(value = "yhBind") String yhKey) throws Exception {
        log.info("判断友惠用户ID是否绑定过,密文：" + yhKey);
        Map<String, Object> mapResult = new HashMap<>();
        if (yhKey == null) {
            return new Result(CommonErrorCode.E_110006);
        }

        try {
            mapResult = customerInfService.bindStatus(yhKey);
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(), businessException.getDesc(), false);
        }
        String msg = (String) mapResult.get("msg");
        boolean success = (Boolean) mapResult.get("success");
        return new Result(CommonErrorCode.SUCCESS.getCode(), msg, success);
    }

    /**
     * 绑定友惠用户ID
     */
    @RequestMapping(value = "/brand/youhui/users/yhBind/{yhBind}", method = RequestMethod.POST)
    public Result yhBind(@PathVariable(value = "yhBind") String yhKey, @RequestBody CustomerInfVO vo) throws Exception {
        log.info("绑定友惠用户ID,密文：" + yhKey + ",手机号：" + vo.getMobilePhone() + ",密码：" + vo.getPassword());
        if (vo.getMobilePhone() == null || vo.getPassword() == null || yhKey == null) {
            return new Result(CommonErrorCode.E_110006);
        }

        try {
            customerInfService.yhBind(vo.getMobilePhone(), vo.getPassword(), yhKey);
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(), businessException.getDesc(), false);
        }
        return new Result(CommonErrorCode.SUCCESS.getCode(), "绑定成功！", true);
    }

    /**
     * 查询用户购物券变更记录
     */
    @RequestMapping(value = "/app/user/users/scoreRecord/{token}", method = RequestMethod.POST)
    public Result scoreRecord(@PathVariable String token) throws Exception {
        if (token == null) {
            return new Result(CommonErrorCode.E_900121);
        }
        Long userId;
        try {
            userId = TokenUtil.getUserId(token);
        } catch (Exception e) {
            return new Result(CommonErrorCode.E_900121);
        }
        List<ScoreSourceVO> scoreList = new ArrayList<>();
        try {
            scoreList = customerInfService.scoreRecord(userId);
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(), businessException.getDesc(), false);
        }
        return new Result(CommonErrorCode.SUCCESS, scoreList);
    }

    /**
     * 用户注册 + 绑定
     */
    @RequestMapping(value = "/brand/youhui/users/registerAndBind/{yhBind}", method = RequestMethod.POST)
    public Result registerAndBind(@PathVariable(value = "yhBind") String yhKey, @RequestBody JSONObject str) throws Exception {
        log.info("======用户注册+绑定========");
        log.info("入参:{}", str.toString());
        String mobilePhone = str.getString("mobilePhone");
        String password = str.getString("password");
        String code = null;

        try {
            try {
                code = str.getString("code");
            } catch (Exception e) {
                code = "0";
            }
            CustomerInf customerInf = customerInfService.userRegister(mobilePhone, password, code);
            log.info("注册后用户信息:{}", customerInf.toString());

            if (StringUtil.isBlank(yhKey)) {
                return new Result(CommonErrorCode.E_110006.getCode(), "yhKey为空", false);
            }

            customerInfService.yhBind(customerInf.getCustomerId(), yhKey);
        } catch (BusinessException businessException) {
            return new Result(businessException.getCode(), businessException.getDesc(), false);
        }

        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 根据加密商户号查询积分变动列表与总积分
     */
    @RequestMapping(value = "/brand/users/scoreRecordAndSumScore/{yhBind}", method = RequestMethod.POST)
    public Result getScoreRecordAndSumScore(@PathVariable(value = "yhBind") String yhKey) throws Exception {
        log.info("=======根据加密商户号查询积分变动列表与总积分=======");
        log.info("入参:{}", yhKey);
        if (StringUtil.isBlank(yhKey)) {
            return new Result(CommonErrorCode.E_110006.getCode(), "yhKey为空", false);
        }
        GetScoreRecordAndSumScoreResponse res = new GetScoreRecordAndSumScoreResponse();
        try {
            res = customerInfService.getScoreRecordAndSumScore(yhKey);
        } catch (BusinessException e) {
            return new Result(e.getCode(), e.getDesc(), false);
        }

        return new Result(CommonErrorCode.SUCCESS, res);
    }
}

package com.cloudbest.user.service;


import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.user.entity.CustomerInf;
import com.cloudbest.user.vo.CustomerInfVO;
import com.cloudbest.user.vo.ScoreSourceVO;
import com.cloudbest.user.vo.UserVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */

public interface CustomerInfService {

    /**
     * 身份认证
     */
    void authentication(String token,String identityCardNo ,String customerName)throws Exception;

    /*
     * 用户注册
     * */
    CustomerInf userRegister(String mobile , String password , String code)throws BusinessException;

    /**
     *通过用户id查询用户信息
     */
    CustomerInf selectUserById(Long customerId);

    /**
     * 修改信息
     */
    void modifyInf( String token,CustomerInf customerInf) throws Exception;

    List<CustomerInfVO> queryList(CustomerInfVO vo);

    CustomerInfVO queryById(Long customerId);

    BigDecimal sumScore(Long customerId);

    void yhBind(String mobilePhone, String password, String yhMerchantNo);

    Map<String,Object> bindStatus(String yhMerchantNo);

    List<ScoreSourceVO> scoreRecord(Long userId);
}

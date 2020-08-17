package com.aomi.pay.service;

import com.aomi.pay.vo.AcctVO;
import com.aomi.pay.vo.MerchantInfoVO;
import com.aomi.pay.vo.ProductVO;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface UserService {


    String create(JSONObject str) throws Exception;

    void openMcht(ProductVO productVO) throws Exception;

    String uploadImg(HttpServletRequest request, String picType, String userId) throws Exception;

    MerchantInfoVO queryMcht(JSONObject str) throws Exception;

    JSONObject queryMchtAudit(JSONObject str) throws Exception;

    void updateInfo(MerchantInfoVO merchantInfoVO) throws Exception;

    void updateStatus(JSONObject str) throws Exception;

    void updateProductModel(JSONObject str) throws Exception;

    void updateMchtAcct(AcctVO acctVO) throws Exception;

    MerchantInfoVO insertMerchantInfo(MerchantInfoVO merchantInfoVO) throws Exception;

    MerchantInfoVO findMerchantInfo(JSONObject str);

    //    String createTwo(JSONObject str) throws Exception;
}

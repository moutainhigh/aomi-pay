package com.aomi.pay.service;

import com.aomi.pay.vo.MerchantInfoVO;
import com.aomi.pay.vo.ProductVO;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface UserService {


     String create(MerchantInfoVO merchantInfoVO) throws Exception;

     void openMcht(ProductVO productVO) throws Exception;

     String uploadImg(HttpServletRequest request,String picType,String userId) throws Exception;

     MerchantInfoVO queryMcht(JSONObject str) throws Exception;

     JSONObject queryMchtAudit(JSONObject str) throws Exception;

     JSONObject updateInfo(MerchantInfoVO merchantInfoVO) throws Exception;

     JSONObject  addMerchant(String userPhone);
}

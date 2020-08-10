package com.aomi.pay.service;

import com.aomi.pay.vo.MerchantInfoVO;
import com.aomi.pay.vo.ProductVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface UserService {


     void create(MerchantInfoVO merchantInfoVO) throws Exception;

     void openMcht(ProductVO productVO) throws Exception;

     String uploadImg(HttpServletRequest request, String picType, String picName, String userId) throws Exception;
//
//    JSONObject queryMcht(String id) throws IOException;
//
//    JSONObject queryMchtAudit(String id) throws IOException;
}

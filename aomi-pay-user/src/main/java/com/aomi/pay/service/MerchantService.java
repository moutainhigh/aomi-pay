package com.aomi.pay.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import vo.MerchantInfoVO;
import vo.PictureVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public interface MerchantService {
    JSONObject uploadImg(HttpServletRequest request,PictureVO pictureVO) throws IOException;

    JSONObject create(MerchantInfoVO merchantInfoVO) throws IOException;

    JSONObject queryMcht(String id) throws IOException;

    JSONObject queryMchtAudit(String id) throws IOException;
}

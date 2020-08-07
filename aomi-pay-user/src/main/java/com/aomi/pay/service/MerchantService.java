package com.aomi.pay.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import com.aomi.pay.vo.MerchantInfoVO;
import com.aomi.pay.vo.PictureVO;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public interface MerchantService {
    JSONObject uploadImg(HttpServletRequest request,PictureVO pictureVO) throws Exception;

    JSONObject create(MerchantInfoVO merchantInfoVO) throws Exception;

    JSONObject queryMcht(String id) throws IOException;

    JSONObject queryMchtAudit(String id) throws IOException;
}

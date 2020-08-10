package com.aomi.pay.service;

import com.aomi.pay.vo.MerchantInfoVO;
import com.aomi.pay.vo.PictureVO;
import com.aomi.pay.vo.ProductVO;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface MerchantService {
    JSONObject uploadImg(PictureVO pictureVO) throws IOException;

    JSONObject createOrgMcht(MerchantInfoVO merchantInfoVO) throws IOException;

    void addProduct(ProductVO productVO) throws IOException;
}

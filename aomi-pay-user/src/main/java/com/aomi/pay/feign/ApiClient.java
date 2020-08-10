package com.aomi.pay.feign;

import com.aomi.pay.vo.BaseResponse;
import com.aomi.pay.vo.MerchantInfoVO;
import com.aomi.pay.vo.PictureVO;
import com.aomi.pay.vo.ProductVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("service-api")
public interface ApiClient {

    //商户信息入网
    @PostMapping("/merchant/createOrgMcht")
    public BaseResponse createOrgMcht(MerchantInfoVO merchantInfoVO) throws Exception;
    //商户上传图片
    @PostMapping("/merchant/uploadImg")
    public BaseResponse uploadImg(PictureVO pictureVO) throws Exception;
    //开通产品
    @PostMapping("/merchant/addProduct")
    public BaseResponse addProduct(ProductVO productVO) throws Exception;
}

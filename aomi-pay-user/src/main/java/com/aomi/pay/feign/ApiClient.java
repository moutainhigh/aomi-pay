package com.aomi.pay.feign;

import com.aomi.pay.vo.BaseResponse;
import com.aomi.pay.vo.MerchantInfoVO;
import com.aomi.pay.vo.PictureVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("service-api")
public interface ApiClient {

    @ApiOperation(value = "商户信息入网")
    @PostMapping("/createOrgMcht")
    public BaseResponse createOrgMcht(MerchantInfoVO merchantInfoVO) throws Exception;

    @ApiOperation(value = "商户上传图片")
    @PostMapping("/uploadImg")
    public BaseResponse uploadImg(PictureVO pictureVO) throws Exception;
}

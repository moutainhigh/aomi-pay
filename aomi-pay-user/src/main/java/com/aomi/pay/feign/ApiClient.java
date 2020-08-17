package com.aomi.pay.feign;

import com.aomi.pay.vo.*;
import net.sf.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-api")
public interface ApiClient {

    //商户信息入网
    @PostMapping("/merchant/createOrgMcht")
    public BaseResponse createOrgMcht(@RequestBody MerchantInfoVO merchantInfoVO) throws Exception;

    //商户上传图片
    @PostMapping("/merchant/uploadImg")
    public BaseResponse uploadImg(@RequestBody PictureVO pictureVO) throws Exception;

    //开通产品
    @PostMapping("/merchant/addProduct")
    public BaseResponse addProduct(@RequestBody ProductVO productVO) throws Exception;

    //商户入网信息查询
    @PostMapping("/merchant/queryMcht")
    public BaseResponse queryMcht(@RequestBody JSONObject str) throws Exception;

    //查询商户审核状态
    @PostMapping("/merchant/queryMchtAudit")
    public BaseResponse queryMchtAudit(@RequestBody JSONObject str) throws Exception;

    //修改商户入网信息
    @PostMapping("/merchant/updateMchtInfo")
    public BaseResponse updateMchtInfo(@RequestBody MerchantInfoVO merchantInfoVO) throws Exception;

    //修改商户状态
    @PostMapping("/merchant/updateMchtStatus")
    public BaseResponse updateMchtStatus(@RequestBody JSONObject str) throws Exception;

    //修改商户开通产品签约费率
    @PostMapping("/merchant/updateProductModel")
    public BaseResponse updateProductModel(@RequestBody JSONObject str) throws Exception;

    //修改商户结算银行卡信息
    @PostMapping("/merchant/updateMchtAcct")
    public BaseResponse updateMchtAcct(@RequestBody AcctVO acctVO) throws Exception;

}

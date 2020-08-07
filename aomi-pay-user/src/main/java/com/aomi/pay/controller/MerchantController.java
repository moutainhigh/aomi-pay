package com.aomi.pay.controller;

import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.service.MerchantService;
import com.aomi.pay.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@CrossOrigin
@RestController
@RefreshScope
public class MerchantController {

    @Autowired
    private MerchantService  merchantService;

    /**
     * 商户信息入网
     */
    @ApiOperation(value = "商户信息入网")
    @PostMapping("/merchant/merchantInfo/create")
    public BaseResponse create(@RequestBody MerchantInfoVO merchantInfoVO) throws Exception {
        MchtBase mchtBase = merchantInfoVO.getMchtBase();
        String mchtName = mchtBase.getMchtName();
        String simpleName = mchtBase.getSimpleName();
        String address = mchtBase.getAddress();
        String storePhone = mchtBase.getStorePhone();

        MchtUser mchtUser = merchantInfoVO.getMchtUser();
        String email = mchtUser.getEmail();
        String phone = mchtUser.getPhone();
        String name = mchtUser.getName();
        String cardNo = mchtUser.getCardNo();

        MchtAcct mchtAcct = merchantInfoVO.getMchtAcct();
        String acctNo = mchtAcct.getAcctNo();
        String agentCardNo = mchtAcct.getAgentCardNo();
        String acctName = mchtAcct.getAcctName();

        MchtComp mchtComp = merchantInfoVO.getMchtComp();
        String licenseNo = mchtComp.getLicenseNo();
        JSONObject jsonObject = merchantService.create(merchantInfoVO);
        return new BaseResponse(CommonErrorCode.SUCCESS,jsonObject);
    }



    /**
     * 上传图片到环迅平台
     */
    @ApiOperation(value = "商户上传图片")
    @PostMapping("merchant/merchantInfo/uploadImg")
    public BaseResponse uploadImg(HttpServletRequest request, @RequestBody PictureVO pictureVO) throws Exception {
        JSONObject jsonObject = merchantService.uploadImg(request,pictureVO);
        return new BaseResponse(CommonErrorCode.SUCCESS,jsonObject);
    }



//
//    /**
//     * 商户入网信息查询
//     */
//    @ApiOperation(value = "商户入网信息查询")
//    @PostMapping("/queryMcht")
//    public BaseResponse queryMcht(@RequestBody JSONObject str) throws Exception {
//        String id = str.getString("id");
//        JSONObject jsonObject = merchantService.queryMcht(id);
//        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
//    }


    /**
     * 查询商户审核状态
     */
    @ApiOperation(value = "查询商户审核状态")
    @PostMapping("/queryMchtAudit")
    public BaseResponse queryMchtAudit(@RequestBody JSONObject str) throws Exception {
        String id = str.getString("id");
        JSONObject jsonObject = merchantService.queryMchtAudit(id);
        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
    }

    /**
     * 商户开通产品
     */
    @ApiOperation(value = "商户开通产品")
    @PostMapping("/merchant/open/product")
    public BaseResponse queryMcht(@RequestBody JSONObject str) throws Exception {
        String id = str.getString("id");
        JSONObject jsonObject = merchantService.queryMcht(id);
        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
    }



}

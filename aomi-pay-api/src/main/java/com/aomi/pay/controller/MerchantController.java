package com.aomi.pay.controller;


import com.aomi.pay.util.SdkUtil;
import com.aomi.pay.util.StringUtil;
import com.aomi.pay.vo.*;
import com.aomi.pay.domain.CommonErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 商户入网类接口Controller
 *
 * @author : hdq
 * @date 2020/8/3 11:31
 */
@Slf4j
@CrossOrigin
@RestController
@RefreshScope
@Api(value = "MerchantController", tags = "商户入网类接口管理")
@RequestMapping("/merchant")
public class MerchantController {

    /**
     * 机构id
     */
    @Value("${inst-id}")
    private String intsId;

    /**
     * 商户图片上传路径
     */
    @Value("${api_route.mcht.upload_img}")
    private String routeUploadImg;

    /**
     * 商户信息入网路径
     */
    @Value("${api_route.mcht.create_org_mcht}")
    private String routeCreateOrgMcht;

    /**
     * 商户信息入网路径
     */
    @Value("${api_route.mcht.query_mcht}")
    private String routeQueryMcht;

    /**
     * 查询商户审核状态路径
     */
    @Value("${api_route.mcht.query_mcht_audit}")
    private String routeQueryMchtAudit;

    /**
     * 查询商户审核状态路径
     */
    @Value("${api_route.mcht.add_product}")
    private String routeAddProduct;

    /**
     * 查询商户审核状态路径
     */
    @Value("${api_route.mcht.update_org_mcht}")
    private String routeUpdateProduct;

    @ApiOperation(value = "商户上传图片")
    @PostMapping("/uploadImg")
    public BaseResponse uploadImg(@RequestParam String img,@RequestParam String picType) throws Exception {
        log.info("--------商户上传图片--------");
        Map<String, Object> paramsData = new HashMap<>();
        //TODO 这个接口是可以上传成功的， 现在参数是写死的,待改成对应的model入参
        paramsData.put("picType", picType);
        //TODO base64的图片
        paramsData.put("pic", img);
        paramsData.put("instId", intsId);
        Random random = new Random();
        int x = random.nextInt(8999999) + 1000000;
        //paramsData.put("pikName", x+".jpg");
        //paramsData.put("key-version","29");

        Object result = SdkUtil.post(paramsData, routeUploadImg);
        return new BaseResponse(CommonErrorCode.SUCCESS, result);
    }

    @ApiOperation(value = "商户信息入网")
    @PostMapping("/createOrgMcht")
    public BaseResponse createOrgMcht() throws Exception {
        log.info("--------商户信息入网--------");
        Map<String, Object> paramsData = new HashMap<>();
        Data data = new Data();
        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
        MchtBase mchtBase = new MchtBase();
        mchtBase.setMchtScope("181");//经营范围
        //mchtBase.setMchtScope("173");
        mchtBase.setMchtKind("B2");//商户种类 1个人  2个体工商、企业
        mchtBase.setInstMchtNo("135795456");//商户号
        mchtBase.setAddress(SdkUtil.encrypt("上海市浦东新区川沙路5007号"));//商户地址
        //mchtBase.setAddress(SdkUtil.encrypt("上海市崇明区堡镇堡镇南路58号15幢2楼227-3室"));
        mchtBase.setMchtName(SdkUtil.encrypt("上海力寰酒店管理有限公司"));//商户名称
        //mchtBase.setMchtName(SdkUtil.encrypt("上海引线物联科技有限公司"));
        mchtBase.setSimpleName(SdkUtil.encrypt("全季酒店(川沙店)"));//商户简称
        mchtBase.setAreaNo("310115");//经营区域码
        mchtBase.setMchtType("0");//是否特约
        mchtBase.setStorePhone(SdkUtil.encrypt("13801700257"));//店铺联系电话

        MchtUser mchtUser = new MchtUser();
        mchtUser.setPhone(SdkUtil.encrypt("13801700257"));//法人电话
        mchtUser.setName(SdkUtil.encrypt("洪邦耀"));//用户名称
        //mchtUser.setCardNo("eUXo2ePvDBmmjon4vvCzNGs45ZcQ7GDK60UO/cFufSg=");
        mchtUser.setCardNo(SdkUtil.encrypt("310109195601312412"));//证件号码
        mchtUser.setCardDate("20151027-长期");//身份证有效期
        //mchtUser.setEmail(SdkUtil.encrypt("aominet@qq.com"));//法人email

        MchtComp mchtComp = new MchtComp();
        mchtComp.setLicenseType("1");
        mchtComp.setLicenseNo(SdkUtil.encrypt("91310115078131852J"));//营业执照号码
        mchtComp.setLicenseDate("20130912");

        MchtAcct mchtAcct = new MchtAcct();
        mchtAcct.setAcctProxy("1");//是否代理清算账户
        mchtAcct.setAcctNo(SdkUtil.encrypt("6230520030057527272"));//银行卡号
        //mchtAcct.setAcctNo(SdkUtil.encrypt("1001213909200209221"));
        mchtAcct.setAcctType("1");//账户类型 0对公 1对私
        mchtAcct.setAcctZbankCode("310115");//支行区域编码
        mchtAcct.setAcctZbankNo("103290076250");//支行编码
        mchtAcct.setAcctName(SdkUtil.encrypt("洪邦耀"));//账户姓名
        mchtAcct.setAcctBankNo("103100000026");//开户行编码

        MchtMedia mchtMedia = new MchtMedia();
        mchtMedia.setCertFront("OSMb90Uww077AWe4SEFHye0RkO8vTcKttmcoCKSVDVg=");//身份证正面
        mchtMedia.setCertReverse("6P0L3jBpFf1QetgFyis1MO0RkO8vTcKttmcoCKSVDVg=");//身份证反面
        /*List<String> indestruLicenses = new LinkedList<String>();
        indestruLicenses.add("VQRX3pXP5g/4AVUr7ajon+0RkO8vTcKttmcoCKSVDVg=");
        indestruLicenses.add("VQRX3pXP5g/4AVUr7ajon+0RkO8vTcKttmcoCKSVDVg=");
        indestruLicenses.add("VQRX3pXP5g/4AVUr7ajon+0RkO8vTcKttmcoCKSVDVg=");
        indestruLicenses.add("VQRX3pXP5g/4AVUr7ajon+0RkO8vTcKttmcoCKSVDVg=");
        mchtMedia.setIndustryLicenses(indestruLicenses);*/
        mchtMedia.setBankCardPositive("5vnF2H4aWhQIPmufVjWixO0RkO8vTcKttmcoCKSVDVg=");//银行卡正面
        mchtMedia.setLicense("+yP/IDWhM4hyAZ+FJ3Wm5+0RkO8vTcKttmcoCKSVDVg=");//营业执照
        mchtMedia.setDoorHead("uzcKY2vnkV6AYws6FBFBje0RkO8vTcKttmcoCKSVDVg=");//门头照
        mchtMedia.setCashier("X/ekJOS8536EON0C70N9he0RkO8vTcKttmcoCKSVDVg=");//收银台照片

        //mchtMedia.setHandheld("VQRX3pXP5g/4AVUr7ajon+0RkO8vTcKttmcoCKSVDVg=");

        Product product = new Product();

        Map<String,String> productMap = new HashMap<>();
        productMap.put("100043","MHN10563");
        productMap.put("100044","MHN10563");
        productMap.put("100042","MHN10563");
        //productMap.put("100011","MHN11201");
        //productMap.put("100010","MHN90144");

        paramsData.put("mchtBase", mchtBase);
        paramsData.put("mchtUser", mchtUser);
        paramsData.put("mchtComp", mchtComp);
        paramsData.put("mchtAcct", mchtAcct);
        paramsData.put("mchtMedia", mchtMedia);
        paramsData.put("product", productMap);
        paramsData.put("instId", intsId);
        paramsData.put("key-version", "29");

        Object result = SdkUtil.post(paramsData, routeCreateOrgMcht);
        return new BaseResponse(CommonErrorCode.SUCCESS, result);
    }

    @ApiOperation(value = "商户入网信息查询")
    @PostMapping("/queryMcht")
    public BaseResponse queryMcht(@RequestParam String mchtNo,@RequestParam String instMchtNo) throws Exception {
        log.info("--------商户入网信息查询--------");
        Map<String, Object> paramsData = new HashMap<>();
        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
        paramsData.put("instId", intsId);
        paramsData.put("mchtNo", mchtNo);
        paramsData.put("instMchtNo", instMchtNo);
        Object result = SdkUtil.post(paramsData, routeQueryMcht);
        return new BaseResponse(CommonErrorCode.SUCCESS, result);
    }

    @ApiOperation(value = "商户开通产品")
    @PostMapping("/addProduct")
    public BaseResponse addProduct(String productCode) throws Exception {
        log.info("--------商户开通产品--------");
        Map<String, Object> paramsData = new HashMap<>();
        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
        paramsData.put("instId", intsId);
        paramsData.put("mchtNo", "027310103382119");
        //paramsData.put("modelId", "MPN10003");
        //paramsData.put("productCode", "100043");//支付宝
        //paramsData.put("productCode", "100042");//微信
        //paramsData.put("productCode", "100044");//银联
        paramsData.put("productCode", productCode);//银联
        paramsData.put("modelId", "MHN10563");
        //paramsData.put("modelId", "MHN20003");
        Object result = SdkUtil.post(paramsData, routeAddProduct);
        return new BaseResponse(CommonErrorCode.SUCCESS, result);
    }

    @ApiOperation(value = "查询商户审核状态")
    @PostMapping("/queryMchtAudit")
    public BaseResponse queryMchtAudit(@RequestParam String mchtNo) throws Exception {
        log.info("--------查询商户审核状态--------");
        Map<String, Object> paramsData = new HashMap<>();
        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
        paramsData.put("instId", intsId);
        paramsData.put("mchtNo", mchtNo);
        Object result = SdkUtil.post(paramsData, routeQueryMchtAudit);
        return new BaseResponse(CommonErrorCode.SUCCESS, result);
    }

    @ApiOperation(value = "修改商户信息入网")
    @PostMapping("/updateOrgMcht")
    public BaseResponse 修改商户信息入网() throws Exception {
        log.info("--------商户信息入网--------");
        Map<String, Object> paramsData = new HashMap<>();
        Data data = new Data();
        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
        MchtBase mchtBase = new MchtBase();
        mchtBase.setMchtNo("027310103388566");//商户号
        mchtBase.setMchtScope("181");//经营范围
        //mchtBase.setMchtScope("173");
        mchtBase.setMchtKind("B2");//商户种类 1个人  2个体工商、企业
        mchtBase.setInstMchtNo("135795489");//商户号
        mchtBase.setAddress(SdkUtil.encrypt("上海市浦东新区川沙路5007号"));//商户地址
        //mchtBase.setAddress(SdkUtil.encrypt("上海市崇明区堡镇堡镇南路58号15幢2楼227-3室"));
        mchtBase.setMchtName(SdkUtil.encrypt("上海力寰酒店管理有限公司"));//商户名称
        //mchtBase.setMchtName(SdkUtil.encrypt("上海引线物联科技有限公司"));
        mchtBase.setSimpleName(SdkUtil.encrypt("全季酒店(川沙店)"));//商户简称
        mchtBase.setAreaNo("310115");//经营区域码
        mchtBase.setMchtType("0");//是否特约
        mchtBase.setStorePhone(SdkUtil.encrypt("13801700257"));//店铺联系电话

        MchtUser mchtUser = new MchtUser();
        mchtUser.setPhone(SdkUtil.encrypt("13801700257"));//法人电话
        mchtUser.setName(SdkUtil.encrypt("洪邦耀"));//用户名称
        //mchtUser.setCardNo("eUXo2ePvDBmmjon4vvCzNGs45ZcQ7GDK60UO/cFufSg=");
        mchtUser.setCardNo(SdkUtil.encrypt("310109195601312412"));//证件号码
        mchtUser.setCardDate("20151027-长期");//身份证有效期
        //mchtUser.setEmail(SdkUtil.encrypt("aominet@qq.com"));//法人email

        MchtComp mchtComp = new MchtComp();
        mchtComp.setLicenseType("1");
        mchtComp.setLicenseNo(SdkUtil.encrypt("91310115078131852J"));//营业执照号码
        mchtComp.setLicenseDate("20130912");

        MchtAcct mchtAcct = new MchtAcct();
        mchtAcct.setAcctProxy("1");//是否代理清算账户
        mchtAcct.setAcctNo(SdkUtil.encrypt("6230520030057527272"));//银行卡号
        //mchtAcct.setAcctNo(SdkUtil.encrypt("1001213909200209221"));
        mchtAcct.setAcctType("1");//账户类型 0对公 1对私
        mchtAcct.setAcctZbankCode("310115");//支行区域编码
        mchtAcct.setAcctZbankNo("103290076250");//支行编码
        mchtAcct.setAcctName(SdkUtil.encrypt("洪邦耀"));//账户姓名
        mchtAcct.setAcctBankNo("103100000026");//开户行编码

        MchtMedia mchtMedia = new MchtMedia();
        mchtMedia.setCertFront(SdkUtil.encrypt("R/jf9qROMQimPu6r8qqVzu0RkO8vTcKttmcoCKSVDVg="));//身份证正面
        mchtMedia.setCertReverse(SdkUtil.encrypt("l8cpGV3Yof1Y1Gzm/iduLu0RkO8vTcKttmcoCKSVDVg="));//身份证反面
        /*List<String> indestruLicenses = new LinkedList<String>();
        indestruLicenses.add("VQRX3pXP5g/4AVUr7ajon+0RkO8vTcKttmcoCKSVDVg=");
        indestruLicenses.add("VQRX3pXP5g/4AVUr7ajon+0RkO8vTcKttmcoCKSVDVg=");
        indestruLicenses.add("VQRX3pXP5g/4AVUr7ajon+0RkO8vTcKttmcoCKSVDVg=");
        indestruLicenses.add("VQRX3pXP5g/4AVUr7ajon+0RkO8vTcKttmcoCKSVDVg=");
        mchtMedia.setIndustryLicenses(indestruLicenses);*/
        //mchtMedia.setBankCardPositive(SdkUtil.encrypt("Xlp6puEH492TCgDGt8fs3O0RkO8vTcKttmcoCKSVDVg="));//银行卡正面
        mchtMedia.setLicense(SdkUtil.encrypt("klshpa98Jjpwpwh43rodWO0RkO8vTcKttmcoCKSVDVg="));//营业执照
        mchtMedia.setDoorHead(SdkUtil.encrypt("UU+eeaK8kdVCXwJPVtmyu+0RkO8vTcKttmcoCKSVDVg="));//门头照
        mchtMedia.setCashier(SdkUtil.encrypt("j3A9uz4flDupzyB06YBage0RkO8vTcKttmcoCKSVDVg="));//收银台照片

        //mchtMedia.setHandheld("VQRX3pXP5g/4AVUr7ajon+0RkO8vTcKttmcoCKSVDVg=");

        Product product = new Product();

        Map<String,String> productMap = new HashMap<>();
        productMap.put("100043","MHN10563");
        productMap.put("100044","MHN10563");
        productMap.put("100042","MHN10563");
        //productMap.put("100011","MHN11201");
        //productMap.put("100010","MHN90144");

        paramsData.put("mchtBase", mchtBase);
        paramsData.put("mchtUser", mchtUser);
        paramsData.put("mchtComp", mchtComp);
        paramsData.put("mchtAcct", mchtAcct);
        paramsData.put("mchtMedia", mchtMedia);
        paramsData.put("product", productMap);
        paramsData.put("instId", intsId);
        paramsData.put("key-version", "29");

        Object result = SdkUtil.post(paramsData, routeUpdateProduct);
        return new BaseResponse(CommonErrorCode.SUCCESS, result);
    }

    @ApiOperation(value = "加密，解密")
    @PostMapping("/test")
    public BaseResponse test(@RequestParam String str,@RequestParam int type) throws Exception {
        log.info("--------加解密--------");
        String result = "";
        if(type == 0){
            result = SdkUtil.encrypt(str);
        }else if(type == 1){
            result = SdkUtil.decrypt(str);
        }
        return new BaseResponse(CommonErrorCode.SUCCESS, result);
    }
}

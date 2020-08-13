package com.aomi.pay.controller;


import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.service.MerchantService;
import com.aomi.pay.vo.BaseResponse;
import com.aomi.pay.vo.MerchantInfoVO;
import com.aomi.pay.vo.PictureVO;
import com.aomi.pay.vo.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @Autowired
    private MerchantService merchantService;

    @ApiOperation(value = "商户上传图片")
    @PostMapping("/uploadImg")
    public BaseResponse uploadImg(@RequestBody PictureVO pictureVO) throws IOException {
        JSONObject jsonObject = merchantService.uploadImg(pictureVO);
        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
    }


    @ApiOperation(value = "商户信息入网")
    @PostMapping("/createOrgMcht")
    public BaseResponse createOrgMcht(@RequestBody MerchantInfoVO merchantInfoVO) throws Exception {
        JSONObject resultJson = this.merchantService.createOrgMcht(merchantInfoVO);
        return new BaseResponse(CommonErrorCode.SUCCESS,resultJson);
    }


    @ApiOperation(value = "商户开通产品")
    @PostMapping("/addProduct")
    public BaseResponse addProduct(@RequestBody ProductVO productVO) throws Exception {
       JSONObject jsonObject = this.merchantService.addProduct(productVO);
        return new BaseResponse(CommonErrorCode.SUCCESS,jsonObject);
    }


    @ApiOperation(value = "商户入网信息查询")
    @PostMapping("/queryMcht")
    public BaseResponse queryMcht(@RequestBody JSONObject str) throws Exception {
        MerchantInfoVO merchantInfoVO = merchantService.queryMcht(str);
        return new BaseResponse(CommonErrorCode.SUCCESS,merchantInfoVO);
    }

    @ApiOperation(value = "查询商户审核状态")
    @PostMapping("/queryMchtAudit")
    public BaseResponse queryMchtAudit(@RequestBody JSONObject str) throws Exception {
        JSONObject jsonObject = merchantService.queryMchtAudit(str);
        return new BaseResponse(CommonErrorCode.SUCCESS,jsonObject);
    }

    @ApiOperation(value = "修改商户入网信息")
    @PostMapping("/updateMchtInfo")
    public BaseResponse updateMchtInfo(@RequestBody MerchantInfoVO merchantInfoVO) throws Exception {
        JSONObject jsonObject = merchantService.updateMchtInfo(merchantInfoVO);
        return new BaseResponse(CommonErrorCode.SUCCESS,jsonObject);
    }


//    @Value("${api_route.mcht.query_mcht}")
//    private String routeQueryMcht;
//
//    @ApiOperation(value = "商户入网信息查询")
//    @PostMapping("/queryMcht/test")
//    public BaseResponse queryMcht() throws Exception {
//        log.info("--------商户入网信息查询--------");
//        Map<String, Object> paramsData = new HashMap<>();
//        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
//        paramsData.put("instId", "015001");
//        paramsData.put("mchtNo", "015370109123528");
//        paramsData.put("instMchtNo", "test100000002");
//        Object post = SdkUtil.post(paramsData, routeQueryMcht);
//        JSONObject jsonObject = JSONObject.fromObject(post);
//        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
//    }

//    @ApiOperation(value = "查询商户审核状态")
//    @PostMapping("/queryMchtAudit")
//    public BaseResponse queryMchtAudit() throws Exception {
//        log.info("--------查询商户审核状态--------");
//        Map<String, Object> paramsData = new HashMap<>();
//        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
//        paramsData.put("instId", "015001");
//        paramsData.put("mchtNo", "015370109123528");
//        String result = SdkUtil.post(paramsData, routeQueryMchtAudit);
//        JSONObject jsonObject = JSONObject.fromObject(result);
//        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
//    }



//    @Value("${api_route.mcht.add_product}")
//    private String routeAddProduct;
//
//    @ApiOperation(value = "商户开通产品")
//    @PostMapping("/addProduct/test")
//    public BaseResponse addProduct() throws Exception {
//        log.info("--------商户开通产品--------");
//        Map<String, Object> paramsData = new HashMap<>();
//        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
//        paramsData.put("instId", "015001");
//        paramsData.put("mchtNo", "015370109123550");
//        //paramsData.put("productCode", "100011");//微信
//        //paramsData.put("modelId", "MPN10003");
//        paramsData.put("productCode", "100010");//支付宝
//        paramsData.put("modelId", "MHN90143");
//        //paramsData.put("productCode", "100004");//银联
//        //paramsData.put("modelId", "MHN20003");
//        Object result = SdkUtil.post(paramsData, routeAddProduct);
//        JSONObject jsonObject = JSONObject.fromObject(result);
//        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
//    }

}
//    /**
//     * 商户图片上传路径
//     */
//    @Value("${api_route.mcht.upload_img}")
//    private String routeUploadImg;
//
//    @ApiOperation(value = "商户上传图片测试")
//    @PostMapping("/uploadImg/test")
//    public BaseResponse uploadImg() throws Exception {
//        log.info("--------商户上传图片--------");
//        Map<String, Object> paramsData = new HashMap<>();
//        //TODO 这个接口是可以上传成功的， 现在参数是写死的,待改成对应的model入参
//        paramsData.put("picType", "01");
//        //TODO base64的图片
//        paramsData.put("pic", "data:image/jpg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAH0AfQDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDtqKKK80+pCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAK3dH0KO+tvtE7uFJwqr396wq7zSE2aTbAf3AfzrajFSlqcWOqyp01yvVmfL4WtWH7uaVD74IrMufDd7Dlotsy/wCycH8q7Cit3RgzzoY2tHrc84kjeJykiMjDqGGDTa9DuLSC7TZPErj3HI/GsC+8MEZezfP/AEzf+hrGVCS21O+lj4T0nozm6Kkmglt5DHNGyMOzDFR1gdyaeqCiiigYUUUUAFFFFABRRS7G/un8qAEop3lv/cb8qNjj+E/lTsK6G0UYx1opDCiiigAooooAKKKKACipYLea5kEcMbO3oBXQWPhgcPePn/pmh/mauMJS2MatenSXvM52KGSZwkSM7HsozWvbeGrubDTMkK+h5NdVBbQ2qbIIlRfYVLXRGgluebVzGb0grGJF4Ys1H7ySVz9cCqOsaFFaWpuLZnwpG5WOePWupqpqi79LuQf+eZNVKnHldkY0sVV9oryOBoooriPeCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACu+0znS7b/rmP5VwNd5pDbtJtj/sYrow+7POzL4F6l2iiiuo8cKKKKAIbm0gu49k8auPfqPoa5nUPDcsOZLQmVOuw/eH+NdZnFN3egzUThGW5vRxFSk/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qxVtyrpujLaOJpiHlHQDotaE9ykC8nLdlFVHurmThQIx+tRLGFO5jub1NaRgoqyOapVlUlzSYo3yyebJ17D0p5NIzBV3MwVfVjgVRn1eGLIgXzX/ALzcKP8AGiU1HcdOjOo7RRfYpHH5sziOMdz3+lY9/qzXCmGAGODv6t9aoz3M1zJvmcsf0FRVyzrOWiPWw+CjT96WrCiiisTuCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKUEqcgkH1FJRQBci1S8hwBKWHo/NXI9ebpLAD7q2Kx6KtVJLZmE8NSnvE6FdatW+9vX6ipF1O0bpMB9QRXNUVoq8jneX0ntc6kXluek8f/fQoN1B/z3j/AO+xXLUU/rD7Ef2bH+Y6Y3tsOs6fnUbalar/AMtQfoCa52il9Yl2Gsup9WzdbV7cfdDt+FQPrX9yD8S1ZNFS602axwNFdLl99XuW+7sX6CoWv7p+szfhxVaipc5PqbRoUo7RRIZ5m6yufqxpu9j/ABH86bRU3ZqopbIXe394/nThLIOjsPxplFFwsiUXVwvSaT/vo1Kuo3a9Jm/HmqtFPma6kulB7pGgmsXK/e2N9RU6a4f44B9Q1ZFFUqs11MZYSjLeJvJrNufvK6/hmpV1O0b/AJa4+oNc5RVKvIyeX0ntc6cXtsek6fnS/aoP+e8f/fQrl6Kr6w+xn/ZsP5mdObu3H/LeP/voVE2oWq9ZgfpzXO0UfWH2Gsth1bNt9XgX7iux/KqkurzN/q1VP1NZ9FQ6s31N4YKjHpf1HyTSTNukdmPuaZRRWZ0pJKyCiiikMKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigD/9k=");
//        paramsData.put("instId", "015001");
//        paramsData.put("pikName", "hahaha");
//        //paramsData.put("key-version","29");
//        Object post = SdkUtil.post(paramsData, routeUploadImg);
//        JSONObject jsonObject = JSONObject.fromObject(post);
//        //TODO 结果暂时看的日志，后续补齐
//        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
//    }

//    /**
//     * 机构id
//     */
//    private static String INST_ID;
//
//    @Value("${inst-id}")
//    public void setKeyVersion(String instId) {
//        INST_ID = instId;
//    }
//
//
//    /**
//     * 商户信息入网路径
//     */
//    @Value("${api_route.mcht.create_org_mcht}")
//    private String routeCreateOrgMcht;
//
//    /**
//     * 商户信息入网路径
//     */
//    @Value("${api_route.mcht.query_mcht}")
//    private String routeQueryMcht;
//
//    /**
//     * 查询商户审核状态路径
//     */
//    @Value("${api_route.mcht.query_mcht_audit}")
//    private String routeQueryMchtAudit;
//
//    /**
//     * 查询商户审核状态路径
//     */
//    @Value("${api_route.mcht.add_product}")
//    private String routeAddProduct;

//
//    @ApiOperation(value = "商户信息入网")
//    @PostMapping("/createOrgMcht/test")
//    public BaseResponse createOrgMcht() throws Exception {
//        log.info("--------商户信息入网--------");
//        Map<String, Object> paramsData = new HashMap<>();
//        Data data = new Data();
//        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
//        MchtBase mchtBase = new MchtBase();
//        mchtBase.setMchtScope("173");
//        mchtBase.setMchtKind("B1");
//        mchtBase.setInstMchtNo("test100000003");
//        mchtBase.setAddress("E/NMOdUvpT7mxuBoFqFCG9ecase2fJ21k4IXyGqA2H/jHRUZ0zR5vDi3UuugvUq6");
//        mchtBase.setMchtName("3DZburLpyTBSi4b64LnTl8i7/785UHBkB3efQ03ynZk=");
//        mchtBase.setSimpleName("dEZUiYp/DZ14YnabEAyb4A==");
//        mchtBase.setAreaNo("370126");
//        mchtBase.setMchtType("0");
//        mchtBase.setStorePhone("7bkQa7RulKi1CjSxhMhKuA==");
//
//        MchtUser mchtUser = new MchtUser();
//        mchtUser.setPhone("t6yaFJjAouRiamR7iDfWmQ==");
//        mchtUser.setName("woedubWd3WTfZ/jZ0jnQvA==");
//        //mchtUser.setCardNo("eUXo2ePvDBmmjon4vvCzNGs45ZcQ7GDK60UO/cFufSg=");
//        mchtUser.setCardNo(SdkUtil.encrypt("430981199403131717"));
//        mchtUser.setEmail("IuwYW+1WKOWmSACCpmpmT2xy8YrcfxQlbST+WLq6fsE=");
//
//        MchtComp mchtComp = new MchtComp();
//        mchtComp.setLicenseType("1");
//        mchtComp.setLicenseNo("3PnWEbcj2sYQx8g4VSILrg==");
//        mchtComp.setLicenseDate("20150125");
//
//        MchtAcct mchtAcct = new MchtAcct();
//        mchtAcct.setAcctProxy("0");
//        mchtAcct.setAcctNo("wXMBt7NyoUI5nzUrrmGtCnS1d4xmJFTbLOijuYnwWBU=");
//        mchtAcct.setAcctType("1");
//        mchtAcct.setAcctZbankCode("520102");
//        mchtAcct.setAcctZbankNo("403711001013");
//        mchtAcct.setAcctName("ktYiMDj4zpPPit8wsuY/tQ==");
//        mchtAcct.setAcctBankNo("15731922");
//        mchtAcct.setAgentCardNo("TPu2cNtqsN5/gFcSrNjmmeje2Ac1xcDbZkJFHehr0aM=");
//
//        MchtMedia mchtMedia = new MchtMedia();
//        mchtMedia.setCertFront("Y7kZ9x5N7f7OP+sDLgI9hc6y0kBbxi03zgnad76q0CQ=");
//        List<String> indestruLicenses = new LinkedList<String>();
//        indestruLicenses.add("Y7kZ9x5N7f7OP+sDLgI9hc6y0kBbxi03zgnad76q0CQ=");
//        indestruLicenses.add("Y7kZ9x5N7f7OP+sDLgI9hc6y0kBbxi03zgnad76q0CQ=");
//        indestruLicenses.add("VfQY65Q0OPO40V9PuGp6Qi7mK49nW23kzxnfXw26XK23hTiJACAE1ss6lZf8QgLG");
//        indestruLicenses.add("GD2miQARzl1DpNlKSRHrc9lDoXUTDGtHgytmaXl2371PQpe78I9K/NhKYHP9cTCG");
//
//        mchtMedia.setIndustryLicenses(indestruLicenses);
//        mchtMedia.setBankCardPositive("Y7kZ9x5N7f7OP+sDLgI9hc6y0kBbxi03zgnad76q0CQ=");
//        mchtMedia.setHandheld("Y7kZ9x5N7f7OP+sDLgI9hc6y0kBbxi03zgnad76q0CQ=");
//        mchtMedia.setCertReverse("Y7kZ9x5N7f7OP+sDLgI9hc6y0kBbxi03zgnad76q0CQ=");
//
//        paramsData.put("mchtBase", mchtBase);
//        paramsData.put("mchtUser", mchtUser);
//        paramsData.put("mchtComp", mchtComp);
//        paramsData.put("mchtAcct", mchtAcct);
//        paramsData.put("mchtMedia", mchtMedia);
//        paramsData.put("instId", "015001");
//        paramsData.put("key-version", "29");
//
//        //paramsData.put("up-appId", "135459893032255489");
//
//        Object post = SdkUtil.post(paramsData, routeCreateOrgMcht);
//        JSONObject jsonObject = JSONObject.fromObject(post);
//        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);





//    @ApiOperation(value = "商户入网信息查询")
//    @PostMapping("/queryMcht")
//    public BaseResponse queryMcht() throws Exception {
//        log.info("--------商户入网信息查询--------");
//        Map<String, Object> paramsData = new HashMap<>();
//        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
//        paramsData.put("instId", "015001");
//        paramsData.put("mchtNo", "015370109123528");
//        paramsData.put("instMchtNo", "test100000002");
//        String result = SdkUtil.post(paramsData, routeQueryMcht);
//        JSONObject jsonObject = JSONObject.fromObject(result);
//        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
//    }
//
//    @ApiOperation(value = "商户开通产品")
//    @PostMapping("/addProduct")
//    public BaseResponse addProduct() throws Exception {
//        log.info("--------商户开通产品--------");
//        Map<String, Object> paramsData = new HashMap<>();
//        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
//        paramsData.put("instId", "015001");
//        paramsData.put("mchtNo", "015370109123528");
//        //paramsData.put("productCode", "100011");//微信
//        //paramsData.put("modelId", "MPN10003");
//        paramsData.put("productCode", "100010");//支付宝
//        paramsData.put("modelId", "MHN90143");
//        //paramsData.put("productCode", "100004");//银联
//        //paramsData.put("modelId", "MHN20003");
//        Object result = SdkUtil.post(paramsData, routeAddProduct);
//        JSONObject jsonObject = JSONObject.fromObject(result);
//        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
//    }

//    @ApiOperation(value = "查询商户审核状态")
//    @PostMapping("/queryMchtAudit")
//    public BaseResponse queryMchtAudit() throws Exception {
//        log.info("--------查询商户审核状态--------");
//        Map<String, Object> paramsData = new HashMap<>();
//        //TODO 这个接口是可以请求成功的， 现在参数是写死的,待改成对应的model入参
//        paramsData.put("instId", "015001");
//        paramsData.put("mchtNo", "015370109123528");
//        String result = SdkUtil.post(paramsData, routeQueryMchtAudit);
//        JSONObject jsonObject = JSONObject.fromObject(result);
//        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
//    }

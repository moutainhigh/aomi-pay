package com.aomi.pay.controller;


import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.entity.UserInf;
import com.aomi.pay.exception.BusinessException;
import com.aomi.pay.mapper.UserMapper;
import com.aomi.pay.service.UserService;
import com.aomi.pay.util.IPUtil;
import com.aomi.pay.util.MD5Util;
import com.aomi.pay.util.PhoneUtil;
import com.aomi.pay.vo.AcctVO;
import com.aomi.pay.vo.BaseResponse;
import com.aomi.pay.vo.MerchantInfoVO;
import com.aomi.pay.vo.ProductVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Slf4j
@CrossOrigin
@RestController
@RefreshScope
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    /**
     * 新建商户
     */
    @ApiOperation(value = "新建商户")
    @PostMapping("user/merchant/addMerchant")
    public BaseResponse addMerchant(@RequestBody JSONObject str){
        String userPhone = str.getString("userPhone");
        JSONObject jsonObject = null;
        try {
            jsonObject = userService.addMerchant(userPhone);
        } catch (BusinessException businessException) {
            return new BaseResponse(businessException.getCode(), businessException.getMessage());
        }
        return new BaseResponse(CommonErrorCode.SUCCESS,jsonObject);
    }

    /**
     * 商户信息入网
     */
    @ApiOperation(value = "商户信息入网")
    @PostMapping("user/merchant/merchantInfo/create")
    public BaseResponse create(@RequestBody MerchantInfoVO merchantInfoVO) throws Exception {
        String mchtNo = null;
        try {
            mchtNo = userService.create(merchantInfoVO);
        } catch (BusinessException businessException) {
            return new BaseResponse(businessException.getCode(), businessException.getMessage());
        }
        return new BaseResponse(CommonErrorCode.SUCCESS,mchtNo);
    }


    /**
     * 上传图片到环迅平台
     */
    @ApiOperation(value = "商户上传图片")
    @PostMapping("user/merchant/merchantInfo/uploadImg")
    public BaseResponse uploadImg(HttpServletRequest request,
                                  @RequestParam("picType") String picType,
                                  @RequestParam("userId")String userId) throws Exception {
        String str = null;
        try {
            str = userService.uploadImg(request,picType,userId);
        } catch (BusinessException businessException) {
            return new BaseResponse(businessException.getCode(), businessException.getMessage());
        }
        return new BaseResponse(CommonErrorCode.SUCCESS, str);
    }

    /**
     * 商户开通产品
     */
    @ApiOperation(value = "商户开通产品")
    @PostMapping("user/merchant/open/product")
    public BaseResponse openMcht(@RequestBody ProductVO productVO) throws Exception {
        try {
            userService.openMcht(productVO);
        } catch (BusinessException businessException) {
            return new BaseResponse(businessException.getCode(), businessException.getMessage());
        }
        return new BaseResponse(CommonErrorCode.SUCCESS);
    }


    //todo 异常

    /**
     * 商户入网信息查询
     */
    @ApiOperation(value = "商户入网信息查询")
    @PostMapping("/user/merchant/queryMcht")
    public BaseResponse queryMcht(@RequestBody JSONObject str) throws Exception {
        MerchantInfoVO merchantInfoVO = null;
        try {
            merchantInfoVO = userService.queryMcht(str);
        }  catch (BusinessException businessException) {
            return new BaseResponse(businessException.getCode(), businessException.getMessage());
        }
        return new BaseResponse(CommonErrorCode.SUCCESS, merchantInfoVO);
    }

    /**
     * 查询商户审核状态
     */
    @ApiOperation(value = "查询商户审核状态")
    @PostMapping("/user/merchant/queryMchtAudit")
    public BaseResponse queryMchtAudit(@RequestBody JSONObject str) throws Exception {
        JSONObject jsonObject = userService.queryMchtAudit(str);
        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
    }


    /**
     * 修改商户入网信息
     */
    @ApiOperation(value = "修改商户入网信息")
    @PostMapping("/user/merchant/updateInfo")
    public BaseResponse updateInfo(@RequestBody MerchantInfoVO merchantInfoVO) throws Exception {
        userService.updateInfo(merchantInfoVO);
        return new BaseResponse(CommonErrorCode.SUCCESS);
    }

    /**
     * 修改商户状态
     */
    @ApiOperation(value = "修改商户状态")
    @PostMapping("/user/merchant/updateStatus")
    public BaseResponse updateStatus(@RequestBody JSONObject str) throws Exception {
        userService.updateStatus(str);
        return new BaseResponse(CommonErrorCode.SUCCESS);
    }

    /**
     *  修改商户开通产品签约费率
     */
    @ApiOperation(value = "修改商户开通产品签约费率")
    @PostMapping("/user/merchant/updateProductModel")
    public BaseResponse updateProductModel(@RequestBody JSONObject str) throws Exception {
        userService.updateProductModel(str);
        return new BaseResponse(CommonErrorCode.SUCCESS);
    }

    /**
     *  修改商户结算银行卡信息
     */
    @ApiOperation(value = "修改商户结算银行卡信息")
    @PostMapping("/user/merchant/updateMchtAcct")
    public BaseResponse updateMchtAcct(@RequestBody AcctVO acctVO) throws Exception {
        userService.updateMchtAcct(acctVO);
        return new BaseResponse(CommonErrorCode.SUCCESS);
    }


    /**
     * 用户注册
     */
    @RequestMapping(value = "/user/merchant/register", method = RequestMethod.POST)
    public BaseResponse UserRegister(@RequestBody JSONObject str) throws Exception {
        try {
            String phone = str.getString("phone");
            String password = str.getString("password");
            String code = null;
            try {
                code = str.getString("code");
            } catch (Exception e) {
                code = "0";
            }
            userService.userRegister(phone, password, code);
        } catch (BusinessException businessException) {
            return new BaseResponse(false,businessException.getCode(), businessException.getDesc());
        }
        return new BaseResponse(CommonErrorCode.SUCCESS);
    }

    /**
     * 用户登录
     */
    @RequestMapping(value = "/user/merchant/login",method = RequestMethod.POST)
    public BaseResponse userLogin(HttpServletRequest httpServletRequest, @RequestBody JSONObject str) {
        String phone = str.getString("phone");
        String password = str.getString("password");
        log.info("================================用户登录手机号："+phone+",登录密码："+password);
        if(phone==null){
            return new BaseResponse(false,"900112","手机号为空");
        }
        if(!PhoneUtil.isMobileSimple(phone)){
            return new BaseResponse(false,"900123","手机号码格式不正确");
        }
        if(password==null){
            return new BaseResponse(false,"900111","密码为空");
        }

        String token = userService.userLogin(phone,password);


        return new BaseResponse(CommonErrorCode.SUCCESS,token);
    }
//
//
//    /**
//     * 上传图片到阿里云
//     * 添加商品图片(上传图片)
//     */
//    @PostMapping(value = "user/oss/uploadImg")
//    public String uploadImgOss(HttpServletRequest request) {
//        String imgurl = "";
//        try {
//            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//            MultipartFile file = multipartRequest.getFile("image");
//
//            if (file == null) {
//                throw new BusinessException(CommonErrorCode.FAIL.getCode(), "请上传至少一张图片");
//            }
//            String ossObjectNamePrefix = AliOSSUtil.APP_SYS_IMAGETEXT + "-";
//            String ossObjectName = "";
//            if (file != null) {
//                String fileName = file.getOriginalFilename();
//                String prefix = fileName.substring(fileName.lastIndexOf("."));
//                fileName = System.currentTimeMillis() + prefix;
//                ossObjectName = ossObjectNamePrefix + fileName;
//                AliOSSUtil aliOSSUtil = new AliOSSUtil();
//                try {
//                    aliOSSUtil.uploadStreamToOss(ossObjectName, file.getInputStream());
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//                String fileUrl = aliOSSUtil.getFileUrl(ossObjectName);
//                imgurl = imgurl + fileUrl.substring(0, fileUrl.lastIndexOf("?"));
//            }
//        } catch (BusinessException businessException) {
//            throw new BusinessException(CommonErrorCode.E_301001);//暂定
//        }
//        return imgurl;
//    }

//
//    /**
//     * 上传图片文件返回图片byte[]
//     */
//    @PostMapping("user/change/photoByte")
//    public String getPhoto(HttpServletRequest request) throws IOException {
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        MultipartFile image = multipartRequest.getFile("image");
//        InputStream inStream = image.getInputStream();
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//
//        //创建一个Buffer字符串
//        byte[] buffer = new byte[1024];
//        //每次读取的字符串长度，如果为-1，代表全部读取完毕
//        int len = 0;
//        //使用一个输入流从buffer里把数据读取出来
//        while ((len = inStream.read(buffer)) != -1) {
//            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
//            outStream.write(buffer, 0, len);
//        }
//        inStream.close();
//        String imageStr = Base64Utils.encodeToString(buffer);
//
//
//        //todo 判断照片大小 不能超过2M ???
//        Integer equalIndex = imageStr.indexOf("=");//1.找到等号，把等号去掉
//        if (imageStr.indexOf("=") > 0) {
//            imageStr = imageStr.substring(0, equalIndex);
//        }
//        Integer size = imageSize(imageStr);
//        System.out.println(size);
//        if (size > 1048576) {
//            throw new BusinessException(CommonErrorCode.E_301005);//暂定
//        }
//        return imageStr;
//    }
//
//    //计算base64图片的字节数(单位:字节)
//    //传入的图片base64是去掉头部的data:image/png;base64,字符串
//
//    public Integer imageSize(String imageBase64Str) {
//
//        //1.找到等号，把等号也去掉(=用来填充base64字符串长度用)
//        Integer equalIndex = imageBase64Str.indexOf("=");
//        if (imageBase64Str.indexOf("=") > 0) {
//            imageBase64Str = imageBase64Str.substring(0, equalIndex);
//        }
//        //2.原来的字符流大小，单位为字节
//        Integer strLength = imageBase64Str.length();
//        System.out.println("imageBase64Str Length = " + strLength);
//        //3.计算后得到的文件流大小，单位为字节
//        Integer size = strLength - (strLength / 8) * 2;
//        return size;
//    }



}

//
    //        MchtBase mchtBase = merchantInfoVO.getMchtBase();
//        String mchtName = mchtBase.getMchtName();
//        String simpleName = mchtBase.getSimpleName();
//        String address = mchtBase.getAddress();
//        String storePhone = mchtBase.getStorePhone();
//
//        MchtUser mchtUser = merchantInfoVO.getMchtUser();
//        String email = mchtUser.getEmail();
//        String phone = mchtUser.getPhone();
//        String name = mchtUser.getName();
//        String cardNo = mchtUser.getCardNo();
//
//        MchtAcct mchtAcct = merchantInfoVO.getMchtAcct();
//        String acctNo = mchtAcct.getAcctNo();
//        String agentCardNo = mchtAcct.getAgentCardNo();
//        String acctName = mchtAcct.getAcctName();
//
//        MchtComp mchtComp = merchantInfoVO.getMchtComp();
//        String licenseNo = mchtComp.getLicenseNo();
//
//    //todo 异常
//    /**
//     * 上传图片到阿里云
//     * 添加商品图片(上传图片)
//     */
//    @PostMapping(value = "/oss/uploadImg")
//    public String uploadImgOss(HttpServletRequest request) {
//
//
//        String imgurls="";
//        try{
//            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//            List<MultipartFile> files = multipartRequest.getFiles("image");
//            if(files==null||files.size()==0){
//                throw new BusinessException(CommonErrorCode.FAIL.getCode(),"请上传至少一张图片");
//            }
//            String ossObjectNamePrefix = AliOSSUtil.APP_SYS_IMAGETEXT+ "-";
//            String ossObjectName = "";
//            int i=1;
//            if(files!=null&&files.size()>0){
//                for (MultipartFile file : files) {
//
//                    String fileName = file.getOriginalFilename();
//                    String prefix=fileName.substring(fileName.lastIndexOf("."));
//                    fileName = System.currentTimeMillis()+i+prefix;
//
//                    ossObjectName = ossObjectNamePrefix + fileName;
//                    AliOSSUtil aliOSSUtil = new AliOSSUtil();
//                    try {
//                        aliOSSUtil.uploadStreamToOss(ossObjectName,file.getInputStream());
//
//
//                    } catch (IOException e1) {
//                        e1.printStackTrace();
//                    }
//                    String fileUrl = aliOSSUtil.getFileUrl(ossObjectName);
//                    if (i==1){
//                        imgurls = imgurls + fileUrl.substring(0,fileUrl.lastIndexOf("?"));
//                    }else {
//                        imgurls = imgurls + "," + fileUrl.substring(0,fileUrl.lastIndexOf("?"));
//                    }
//                    i++;
//                }
//            }
//        }catch (BusinessException businessException){
//            throw new BusinessException(CommonErrorCode.E_301001);//暂定
//        }
//        return  imgurls;
//    }

    //    @ApiImplicitParams(
//            {
//                    @ApiImplicitParam(name = "image", value = "文件流对象,接收数据格式", required = true,dataType = "MultipartFile",allowMultiple = true)
//            }
//    )




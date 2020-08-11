package com.aomi.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.exception.BusinessException;
import com.aomi.pay.service.UserService;
import com.aomi.pay.util.AliOSSUtil;
import com.aomi.pay.vo.BaseResponse;
import com.aomi.pay.vo.MerchantInfoVO;
import com.aomi.pay.vo.ProductVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


@Slf4j
@CrossOrigin
@RestController
@RefreshScope
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 商户信息入网
     */
    @ApiOperation(value = "商户信息入网")
    @PostMapping("user/merchant/merchantInfo/create")
    public BaseResponse create(@RequestBody MerchantInfoVO merchantInfoVO) throws Exception {
        userService.create(merchantInfoVO);
        return new BaseResponse(CommonErrorCode.SUCCESS);
    }



    /**
     * 上传图片到环迅平台
     */
    @ApiOperation(value = "商户上传图片")
//    @ApiImplicitParams(
//            {
//                    @ApiImplicitParam(name = "image", value = "文件流对象,接收数据格式", required = true,dataType = "MultipartFile",allowMultiple = true)
//            }
//    )
    @PostMapping("user/merchant/merchantInfo/uploadImg")
    public BaseResponse uploadImg(@RequestBody JSONObject jsonStr) throws Exception {
        String picType = jsonStr.getString("picType");
        String picName = jsonStr.getString("picName");
        String userId = jsonStr.getString("userId");
        String imageStr = jsonStr.getString("imageStr");
        String imageUrl = jsonStr.getString("imageUrl");
        String str = null;
        try {
            str = userService.uploadImg(imageUrl,picType,picName,userId,imageStr);
        } catch (BusinessException businessException) {
            return new BaseResponse(businessException.getCode(),businessException.getMessage());
        }
        return new BaseResponse(CommonErrorCode.SUCCESS,str);
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
            return new BaseResponse(businessException.getCode(),businessException.getMessage());
        }
        return new BaseResponse(CommonErrorCode.SUCCESS);
    }


    //todo 异常
    /**
     * 上传图片到阿里云
     * 添加商品图片(上传图片)
     */
    @PostMapping(value = "user/oss/uploadImg")
    public String uploadImgOss(HttpServletRequest request) {
        String imgurl="";
        try{
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("image");

            if(file==null ){
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),"请上传至少一张图片");
            }
            String ossObjectNamePrefix = AliOSSUtil.APP_SYS_IMAGETEXT+ "-";
            String ossObjectName = "";
            if(file!=null ){
                    String fileName = file.getOriginalFilename();
                    String prefix=fileName.substring(fileName.lastIndexOf("."));
                    fileName = System.currentTimeMillis() + prefix;
                    ossObjectName = ossObjectNamePrefix + fileName;
                    AliOSSUtil aliOSSUtil = new AliOSSUtil();
                    try {
                        aliOSSUtil.uploadStreamToOss(ossObjectName,file.getInputStream());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    String fileUrl = aliOSSUtil.getFileUrl(ossObjectName);
                    imgurl = imgurl + fileUrl.substring(0,fileUrl.lastIndexOf("?"));
            }
        }catch (BusinessException businessException){
            throw new BusinessException(CommonErrorCode.E_301001);//暂定
        }
        return  imgurl;
    }


    /**
     * 上传图片文件返回图片byte[]
     * */
    @PostMapping("change/photoByte")
    public String getPhoto(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile image = multipartRequest.getFile("image");
        InputStream inStream = image.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        String imageStr = Base64Utils.encodeToString(buffer);


        //todo 判断照片大小 不能超过2M ???
        Integer equalIndex= imageStr.indexOf("=");//1.找到等号，把等号去掉
        if(imageStr.indexOf("=")>0) {
            imageStr=imageStr.substring(0, equalIndex);
        }
        Integer size = imageSize(imageStr);
        System.out.println(size);
        if (size > 1048576){
            throw new BusinessException(CommonErrorCode.E_301005);//暂定
        }
        return imageStr;
    }


    //计算base64图片的字节数(单位:字节)
    //传入的图片base64是去掉头部的data:image/png;base64,字符串
    public Integer imageSize(String imageBase64Str){

        //1.找到等号，把等号也去掉(=用来填充base64字符串长度用)
        Integer equalIndex= imageBase64Str.indexOf("=");
        if(imageBase64Str.indexOf("=")>0) {
            imageBase64Str=imageBase64Str.substring(0, equalIndex);
        }
        //2.原来的字符流大小，单位为字节
        Integer strLength=imageBase64Str.length();
        System.out.println("imageBase64Str Length = "+strLength);
        //3.计算后得到的文件流大小，单位为字节
        Integer size=strLength-(strLength/8)*2;
        return size;
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


//    /**
//     * 查询商户审核状态
//     */
//    @ApiOperation(value = "查询商户审核状态")
//    @PostMapping("/queryMchtAudit")
//    public BaseResponse queryMchtAudit(@RequestBody JSONObject str) throws Exception {
//        String id = str.getString("id");
//        JSONObject jsonObject = merchantService.queryMchtAudit(id);
//        return new BaseResponse(CommonErrorCode.SUCCESS, jsonObject);
//    }
//

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


}

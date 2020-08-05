package com.aomi.pay.controller;

import com.aomi.pay.service.MerchantService;
import com.aomi.pay.vo.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@CrossOrigin
@RestController
@RefreshScope
public class MerchantController {

    @Autowired
    private MerchantService userService;

    /**
     * 添加商品图片(上传图片)
     */
    @PostMapping(value = "/user/add/uploadImg")
    public BaseResponse uploadImg(HttpServletRequest request) {
//        userService.uploadImg(request);
//        String imgurls="";
//        try{
//            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//            List<MultipartFile> files = multipartRequest.getFiles("image");
//            if(files==null||files.size()==0){
//                throw new BusinessException(CommonErrorCode.FAIL.getCode(),"请上传至少一张图片");
//            }
//
//            String ossObjectNamePrefix = AliOSSUtil.APP_SYS_IMAGETEXT+ "-";
//            String ossObjectName = "";
//
//            int i=1;
//
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
//            return new BaseResponse(CommonErrorCode.E_301001.getCode(),"上传图片失败");
//        }
//        return new BaseResponse(CommonErrorCode.SUCCESS,imgurls);
//    }
        return null;
    }
}

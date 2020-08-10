package com.aomi.pay.controller;


import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.exception.BusinessException;
import com.aomi.pay.mapper.MerchantImgMapper;
import com.aomi.pay.service.ImageService;
import com.aomi.pay.util.AliOSSUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@CrossOrigin
@RestController
@RefreshScope
public class ImageController {

    @Autowired
    private MerchantImgMapper merchantImgMapper;
    @Autowired
    private ImageService imageService;

//    /**
//     * 插入图片信息
//    */
//    @PostMapping(value = "user/image/addImage")
//    private BaseResponse addImage(HttpServletRequest request,String type){
//        this.imageService.addImage( request);
//        return new BaseResponse(CommonErrorCode.SUCCESS);
//    }


        //todo 异常
    /**
     * 上传图片到阿里云
     * 添加商品图片(上传图片)
     * 单张图片
     */
    @PostMapping(value = "/merchant/add/uploadImg")
    public String uploadImgOss(HttpServletRequest request) {
        String imgurl="";
        try{
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("image");
            if(file==null ){
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),"请上传图片");
            }
            String ossObjectNamePrefix = AliOSSUtil.APP_SYS_IMAGETEXT+ "-";
            String ossObjectName = "";
            int i=1;
            if(file!=null ){
                    String fileName = file.getOriginalFilename();
                    String prefix=fileName.substring(fileName.lastIndexOf("."));
                    fileName = System.currentTimeMillis()+i+prefix;

                    ossObjectName = ossObjectNamePrefix + fileName;
                    AliOSSUtil aliOSSUtil = new AliOSSUtil();
                    try {
                        aliOSSUtil.uploadStreamToOss(ossObjectName,file.getInputStream());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    String fileUrl = aliOSSUtil.getFileUrl(ossObjectName);
                    if (i==1){
                        imgurl = imgurl + fileUrl.substring(0,fileUrl.lastIndexOf("?"));
                    }else {
                        imgurl = imgurl + "," + fileUrl.substring(0,fileUrl.lastIndexOf("?"));
                    }
                    i++;
            }
        }catch (BusinessException businessException){
            throw new BusinessException(CommonErrorCode.E_301001);//暂定
        }
        return  imgurl;
    }
}

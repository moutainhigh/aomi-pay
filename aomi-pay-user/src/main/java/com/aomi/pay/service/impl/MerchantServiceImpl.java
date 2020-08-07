package com.aomi.pay.service.impl;

import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.entity.MerchantImg;
import com.aomi.pay.entity.MerchantInfo;
import com.aomi.pay.exception.BusinessException;
import com.aomi.pay.feign.ApiClient;
import com.aomi.pay.mapper.MerchantImgMapper;
import com.aomi.pay.mapper.MerchantInfoMapper;
import com.aomi.pay.service.MerchantService;
import com.aomi.pay.util.AliOSSUtil;
import com.aomi.pay.util.SdkUtil;
import com.aomi.pay.vo.*;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
//@RefreshScope
public class MerchantServiceImpl implements MerchantService {


    @Autowired
    private MerchantInfoMapper merchantInfoMapper;
    @Autowired
    private MerchantImgMapper merchantImgMapper;
    @Autowired
    private ApiClient apiClient;

    /**
     * 商户信息入网
     */
    @Override
    public JSONObject create(MerchantInfoVO merchantInfoVO) throws Exception {
        MerchantInfo merchantInfo = new MerchantInfo();
        String substring = IdWorker.getTimeId().substring(0, 20);
        Long id = Long.parseLong(substring);
        merchantInfo.setSn(merchantInfoVO.getSn());
        merchantInfo.setSnModelId(merchantInfoVO.getSnModelId());
        merchantInfo.setServiceType(merchantInfoVO.getServiceType());
        merchantInfo.setId(id);
        merchantInfoVO.setInstId(substring);
        merchantInfo.setStatus("1");

        MchtBase mchtBase = merchantInfoVO.getMchtBase();
        merchantInfo.setMerchantName(mchtBase.getMchtName());
        merchantInfo.setSimpleName(mchtBase.getSimpleName());
        merchantInfo.setMerchantKind(mchtBase.getMchtKind());
        merchantInfo.setAreaNo(mchtBase.getAreaNo());
        merchantInfo.setAddress(mchtBase.getAddress());
        merchantInfo.setMerchantPhone(mchtBase.getStorePhone());
        merchantInfo.setMerchantScope(mchtBase.getMchtScope());
        merchantInfo.setMerchantType(mchtBase.getMchtType());
        merchantInfo.setUnionpayMerchant(mchtBase.getNuionpayMacht());

        MchtUser mchtUser = merchantInfoVO.getMchtUser();
        merchantInfo.setLegalPersonEmail(mchtUser.getEmail());
        merchantInfo.setLegalPersonName(mchtUser.getName());
        merchantInfo.setLegalPersonPhone(mchtUser.getPhone());
        merchantInfo.setLegalPersonCardno(mchtUser.getCardNo());
        merchantInfo.setLegalPersonCardnoDate(mchtUser.getCardDate());

        MchtAcct mchtAcct = merchantInfoVO.getMchtAcct();
        merchantInfo.setAcctProxy(mchtAcct.getAcctProxy());
        merchantInfo.setAgentCardNo(mchtAcct.getAgentCardNo());
        merchantInfo.setAgentCardDate(mchtAcct.getAgentCardDate());
        merchantInfo.setAcctType(mchtAcct.getAcctType());
        merchantInfo.setAcctNo(mchtAcct.getAcctNo());
        merchantInfo.setAcctName(mchtAcct.getAcctName());
        merchantInfo.setAcctBankNo(mchtAcct.getAcctBankNo());
        merchantInfo.setAcctZbankCode(mchtAcct.getAcctZbankCode());
        merchantInfo.setAcctZbankNo(mchtAcct.getAcctZbankNo());

        MchtComp mchtComp = merchantInfoVO.getMchtComp();
        merchantInfo.setLicenseDate(mchtComp.getLicenseDate());
        merchantInfo.setLicenseNo(mchtComp.getLicenseNo());
        merchantInfo.setLicenseType(mchtComp.getLicenseType());

        MchtMedia mchtMedia = merchantInfoVO.getMchtMedia();

        //todo 修改
        Map<String, String> product = merchantInfoVO.getProduct();
        merchantInfo.setProductCode(product.keySet().toString());
        merchantInfo.setModelId(product.values().toString());

        try {
            Object data = apiClient.createOrgMcht(merchantInfoVO).getData();
            //todo 行否？
            JSONObject jsonObject = JSONObject.fromObject(data);
            if (jsonObject.get("resultCode").equals("1")){
                String mchtNo = jsonObject.get("mchtNo").toString();
                merchantInfo.setPlatformId(Long.parseLong(mchtNo));
                String unionPayMchtNo = jsonObject.get("unionPayMchtNo").toString();
                if (StringUtils.isNotEmpty(unionPayMchtNo)){
                    merchantInfo.setUnionPayMchtNo(unionPayMchtNo);
                }
                merchantInfoMapper.insert(merchantInfo);
                return jsonObject;
            }
            throw new BusinessException(CommonErrorCode.FAIL.getDesc(),"信息入网失败");//暂定
        } catch (BusinessException businessException) {
            throw new BusinessException(CommonErrorCode.E_301002);//暂定
        }
    }


    /**
     * 商户上传图片
     */
    @Override
    public JSONObject uploadImg(HttpServletRequest request,PictureVO pictureVO) throws Exception {
        log.info("--------商户上传图片--------");
        MerchantImg merchantImg = new MerchantImg();
        pictureVO.getPicName();
        String url = this.uploadImgOss(request);
        byte[] bytes = url.getBytes("utf-8");
        String pic = Base64.getEncoder().encode(bytes).toString();

        pictureVO.setPic(pic);


//        String result = null;
//        try {
//            result = SdkUtil.post(pictureVO, routeUploadImg);
//        } catch (BusinessException businessException) {
//            throw new BusinessException(CommonErrorCode.E_301002);//暂定
//        }

        try {
            Object data = apiClient.uploadImg(pictureVO).getData();

        } catch (BusinessException businessException) {
            throw new BusinessException(CommonErrorCode.E_301002);//暂定
        }

        JSONObject jsonObject = JSONObject.fromObject(result);
        String imgCode = jsonObject.get("imgCode").toString();

        merchantImg.setImgCode(imgCode);
        merchantImg.setImgUrl(url);
        merchantImg.setPlatform("hx");
        merchantImg.setType(pictureVO.getPicType());
        merchantImg.setUserId(pictureVO.getUserId());
        merchantImg.setImgName(pictureVO.getPicName());
        merchantImg.setUpdateTime(LocalDateTime.now());
        merchantImgMapper.insert(merchantImg);

        return  jsonObject;
    }


    /**
     * 商户入网信息查询
     */
    @Override
    public JSONObject queryMcht(String id) throws IOException {
        if (StringUtils.isEmpty(id)){
            throw new BusinessException(CommonErrorCode.E_301003);//暂定
        }
        MerchantInfo merchantInfo = merchantInfoMapper.selectById(id);
        String platformId = merchantInfo.getPlatformId();
        log.info("--------商户入网信息查询--------");
        Map<String, Object> paramsData = new HashMap<>();

        paramsData.put("instId", "015001");
        paramsData.put("mchtNo", id);
        paramsData.put("instMchtNo", platformId);
        String result = null;
        try {
            result = SdkUtil.post(paramsData, routeQueryMcht);
        } catch (BusinessException businessException) {
            throw new BusinessException(CommonErrorCode.E_301002);//暂定
        }
        JSONObject jsonObject = JSONObject.fromObject(result);
        return jsonObject;
    }


    /**
     * 查询商户审核状态
     */
    @Override
    public JSONObject queryMchtAudit(String id) throws IOException {
        if (StringUtils.isEmpty(id)){
            throw new BusinessException(CommonErrorCode.E_301003);//暂定
        }
        MerchantInfo merchantInfo = merchantInfoMapper.selectById(id);
        String platformId = merchantInfo.getPlatformId();
        log.info("--------查询商户审核状态--------");
        Map<String, Object> paramsData = new HashMap<>();
        paramsData.put("instId", "015001");
        paramsData.put("mchtNo", platformId);
        String result = null;
        try {
            result = SdkUtil.post(paramsData, routeQueryMchtAudit);
        } catch (BusinessException businessException) {
            throw new BusinessException(CommonErrorCode.E_301002);//暂定
        }
        JSONObject jsonObject = JSONObject.fromObject(result);
        return jsonObject;
    }


    //todo 异常
    /**
     * 上传图片到阿里云
     * 添加商品图片(上传图片)
     */
    @PostMapping(value = "/merchant/add/uploadImg")
    public String uploadImgOss(HttpServletRequest request) {
        String imgurls="";
        try{
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            List<MultipartFile> files = multipartRequest.getFiles("image");
            if(files==null||files.size()==0){
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),"请上传至少一张图片");
            }
            String ossObjectNamePrefix = AliOSSUtil.APP_SYS_IMAGETEXT+ "-";
            String ossObjectName = "";
            int i=1;
            if(files!=null&&files.size()>0){
                for (MultipartFile file : files) {

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
                        imgurls = imgurls + fileUrl.substring(0,fileUrl.lastIndexOf("?"));
                    }else {
                        imgurls = imgurls + "," + fileUrl.substring(0,fileUrl.lastIndexOf("?"));
                    }
                    i++;
                }
            }
        }catch (BusinessException businessException){
            throw new BusinessException(CommonErrorCode.E_301001);//暂定
        }
        return  imgurls;
    }


}

































package com.aomi.pay.service.impl;

import com.aomi.pay.service.MerchantService;
import com.aomi.pay.util.SdkUtil;
import com.aomi.pay.vo.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MerchantServiceImpl implements MerchantService {

    /**
     * 机构id
     */
    private static String INST_ID;

    @Value("${inst-id}")
    public void setKeyVersion(String instId) {
        INST_ID = instId;
    }

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



    @Override
    public JSONObject uploadImg(PictureVO pictureVO) throws IOException {
        log.info("--------商户上传图片--------");
        PictureInfoVO pictureInfoVO = new PictureInfoVO();

        String imagStr = pictureVO.getImagStr();
        //加密后的图片
        String encrypt = SdkUtil.encrypt(imagStr);

        pictureInfoVO.setInstId(INST_ID);
        pictureInfoVO.setPicName(pictureVO.getPicName());
        pictureInfoVO.setPicType(pictureVO.getPicType());
        pictureInfoVO.setPic(encrypt);

        JSONObject imgObject = new JSONObject();
        imgObject.put("encrypt",encrypt);
        Object post = SdkUtil.post(pictureInfoVO, routeUploadImg);
        JSONObject jsonObject = JSONObject.fromObject(post);
        String imgCode = jsonObject.getString("imgCode");
        imgObject.put("imgCode",imgCode);
        return imgObject;
    }

    @Override
    public JSONObject createOrgMcht(MerchantInfoVO merchantInfoVO) throws IOException {

        log.info("--------商户信息入网--------");
        MchtBase mchtBase = merchantInfoVO.getMchtBase();
        mchtBase.setMchtName(SdkUtil.encrypt(mchtBase.getMchtName()));
        mchtBase.setSimpleName(SdkUtil.encrypt(mchtBase.getSimpleName()));
        mchtBase.setAddress(SdkUtil.encrypt(mchtBase.getAddress()));
        mchtBase.setStorePhone(SdkUtil.encrypt(mchtBase.getStorePhone()));
        merchantInfoVO.setMchtBase(mchtBase);

        MchtUser mchtUser = merchantInfoVO.getMchtUser();
        mchtUser.setCardNo(SdkUtil.encrypt(mchtUser.getCardNo()));
        mchtUser.setEmail(SdkUtil.encrypt(mchtUser.getEmail()));
        mchtUser.setName(SdkUtil.encrypt(mchtUser.getName()));
        mchtUser.setPhone(SdkUtil.encrypt(mchtUser.getPhone()));
        merchantInfoVO.setMchtUser(mchtUser);

        MchtAcct mchtAcct = merchantInfoVO.getMchtAcct();
        mchtAcct.setAgentCardNo(SdkUtil.encrypt(mchtAcct.getAgentCardNo()));
        mchtAcct.setAcctNo(SdkUtil.encrypt(mchtAcct.getAcctNo()));
        mchtAcct.setAcctName(SdkUtil.encrypt(mchtAcct.getAcctName()));
        merchantInfoVO.setMchtAcct(mchtAcct);

        MchtComp mchtComp = merchantInfoVO.getMchtComp();
        mchtComp.setLicenseNo(SdkUtil.encrypt(mchtComp.getLicenseNo()));
        merchantInfoVO.setMchtComp(mchtComp);

        MchtMedia mchtMedia = merchantInfoVO.getMchtMedia();
        mchtMedia.setCertFront(SdkUtil.encrypt(mchtMedia.getCertFront()));
        mchtMedia.setCertReverse(SdkUtil.encrypt(mchtMedia.getCertReverse()));
        if (StringUtils.isNotEmpty(mchtMedia.getHandheld())){
            mchtMedia.setHandheld(SdkUtil.encrypt(mchtMedia.getHandheld()));
        }
        mchtMedia.setBankCardPositive(SdkUtil.encrypt(mchtMedia.getBankCardPositive()));
        mchtMedia.setLicense(SdkUtil.encrypt(mchtMedia.getLicense()));
        if (StringUtils.isNotEmpty(mchtMedia.getOrgImage())){
            mchtMedia.setOrgImage(SdkUtil.encrypt(mchtMedia.getOrgImage()));
        }
        if (StringUtils.isNotEmpty(mchtMedia.getTaxImage())){
            mchtMedia.setTaxImage(SdkUtil.encrypt(mchtMedia.getTaxImage()));
        }
        if (StringUtils.isNotEmpty(mchtMedia.getOpenAccoLic())){
            mchtMedia.setOpenAccoLic(SdkUtil.encrypt(mchtMedia.getOpenAccoLic()));
        }
        mchtMedia.setDoorHead(SdkUtil.encrypt(mchtMedia.getDoorHead()));
        mchtMedia.setCashier(SdkUtil.encrypt(mchtMedia.getCashier()));
        mchtMedia.setShopPanoram(SdkUtil.encrypt(mchtMedia.getShopPanoram()));
        mchtMedia.setPriLicAgree(SdkUtil.encrypt(mchtMedia.getPriLicAgree()));
        mchtMedia.setAgenCardFront(SdkUtil.encrypt(mchtMedia.getAgenCardFront()));
        mchtMedia.setAgenIdCardBackPic(SdkUtil.encrypt(mchtMedia.getAgenIdCardBackPic()));
        mchtMedia.setAgentCardId(SdkUtil.encrypt(mchtMedia.getAgentCardId()));
        mchtMedia.setAgentProtocol(SdkUtil.encrypt(mchtMedia.getAgentProtocol()));

        List<String> industryLicenses = mchtMedia.getIndustryLicenses();
        if (CollectionUtils.isNotEmpty(industryLicenses)){
            List<String> collect = industryLicenses.stream().map(industryLicense -> {
                String string = SdkUtil.encrypt(industryLicense);
                return string;
            }).collect(Collectors.toList());
            mchtMedia.setIndustryLicenses(collect);
        }

        List<String> extraPics = mchtMedia.getIndustryLicenses();
        if (CollectionUtils.isNotEmpty(extraPics)){
            List<String> collectExtraPics = extraPics.stream().map(extraPic -> {
                String string = SdkUtil.encrypt(extraPic);
                return string;
            }).collect(Collectors.toList());
            mchtMedia.setExtraPics(collectExtraPics);
        }

        JSONObject resultJson = new JSONObject();
        //todo 处理返回值
        Object post = SdkUtil.post(merchantInfoVO, routeCreateOrgMcht);
        JSONObject jsonObject = JSONObject.fromObject(post);
        String mchtNo = jsonObject.getString("mchtNo");
        String unionPayMchtNo = jsonObject.getString("unionPayMchtNo");
        resultJson.put("mchtNo",mchtNo);
        resultJson.put("unionPayMchtNo",unionPayMchtNo);
        return resultJson;
    }

    @Override
    public JSONObject addProduct(ProductVO productVO) throws IOException {
        log.info("--------商户开通产品--------");

        JSONObject productJson = new JSONObject();
        productJson.put("instId",INST_ID);
        productJson.put("mchtNo",productVO.getMchtNO());
        productJson.put("productCode",productVO.getProductCode());
        productJson.put("modelId",productVO.getModelId());
        Object post = SdkUtil.post(productJson, routeAddProduct);
        JSONObject jsonObject = JSONObject.fromObject(post);
        return  jsonObject;
    }
}

































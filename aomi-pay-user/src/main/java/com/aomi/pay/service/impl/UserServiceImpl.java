package com.aomi.pay.service.impl;

import com.aomi.pay.domain.CommonErrorCode;
import com.aomi.pay.entity.MerchantImg;
import com.aomi.pay.entity.MerchantInfo;
import com.aomi.pay.entity.MerchantProduct;
import com.aomi.pay.entity.MerchantProductBind;
import com.aomi.pay.exception.BusinessException;
import com.aomi.pay.feign.ApiClient;
import com.aomi.pay.mapper.MerchantImgMapper;
import com.aomi.pay.mapper.MerchantInfoMapper;
import com.aomi.pay.mapper.MerchantProductBindMapper;
import com.aomi.pay.mapper.MerchantProductMapper;
import com.aomi.pay.service.UserService;
import com.aomi.pay.vo.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RefreshScope
public class UserServiceImpl implements UserService {


    @Autowired
    private MerchantProductMapper merchantProductMapper;
    @Autowired
    private MerchantProductBindMapper merchantProductBindMapper;
    @Autowired
    private MerchantInfoMapper merchantInfoMapper;
    @Autowired
    private MerchantImgMapper merchantImgMapper;
    @Autowired
    private ApiClient apiClient;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 商户信息入网
     */
    @Override
    public void create(MerchantInfoVO merchantInfoVO) throws Exception {

        MerchantInfo merchantInfo = new MerchantInfo();
//        String substring = IdWorker.getTimeId().substring(0,20);
//        Long id = Long.parseLong(substring);
        merchantInfo.setSn(merchantInfoVO.getSn());
        merchantInfo.setSnModelId(merchantInfoVO.getSnModelId());
        merchantInfo.setServiceType(merchantInfoVO.getServiceType());
//        merchantInfo.setId(id);
//        merchantInfoVO.setInstId(substring);
        merchantInfo.setStatus("1");
        merchantInfo.setPassword("000000");
//        merchantInfoMapper.insert(merchantInfo);

        MchtBase mchtBase = merchantInfoVO.getMchtBase();
        merchantInfo.setId(mchtBase.getInstMchtNo());
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


//        //todo 修改  商户开通产品之后才有？？？
//        Map<String, String> product = merchantInfoVO.getProduct();
//        merchantInfo.setProductCode(product.keySet().toString());
//        merchantInfo.setModelId(product.values().toString());

//        try {
//                List<MerchantImg> merchantImgs = merchantImgMapper.selectList(new LambdaQueryWrapper<MerchantImg>().eq(MerchantImg::getUserId, mchtBase.getInstMchtNo()));
//            Long count = merchantImgs.stream().map(merchantImg -> {
//                String type = merchantImg.getType();
//                return type;
//            }).distinct().count();
//            if (count < 14){
//                throw new BusinessException(CommonErrorCode.E_301006);//暂定
//            }else if (count < 18){
//                merchantImgs.forEach(merchantImg -> {
//                    String type = merchantImg.getType();
//                    if (type == "03" || type == "06" || type == "07" || type == "08" || type == "03" || type == "17" || type == "18"){
//                        throw new BusinessException(CommonErrorCode.E_301006);//暂定
//                    }
//                });
//            }
//
////                merchantInfoMapper.updateById(merchantInfo);
//        } catch (BusinessException businessException) {
//            throw new BusinessException(CommonErrorCode.E_301006);//暂定
//        }

        Object data = apiClient.createOrgMcht(merchantInfoVO).getData();
        JSONObject jsonObject = JSONObject.fromObject(data);
        String mchtNo = jsonObject.getString("mchtNo");
        merchantInfo.setPlatformId(mchtNo);
        String unionPayMchtNo = jsonObject.getString("unionPayMchtNo");
        if (StringUtils.isNotEmpty(unionPayMchtNo)){
            merchantInfo.setUnionPayMchtNo(unionPayMchtNo);
        }
        //存入redis
        redisTemplate.opsForValue().set(merchantInfo.getId(),mchtNo);
        merchantInfoMapper.insert(merchantInfo);
    }

    /**
     * 商户开通产品
     */
    @Override
    public void openMcht(ProductVO productVO) throws Exception {

        Integer productId = productVO.getProductId();//产品id
        MerchantProduct merchantProduct = merchantProductMapper.selectById(productId);
        productVO.setModelId(merchantProduct.getModelId());
        productVO.setProductCode(merchantProduct.getProductCode());

        Object data = apiClient.addProduct(productVO).getData();
        if (ObjectUtils.equals(data,null)){
            throw new BusinessException(CommonErrorCode.E_301004);
        }
        MerchantProductBind merchantProductBind = new MerchantProductBind();
        //插入绑定关系表
        merchantProductBind.setMerchantId(productVO.getInstMchtNo());//商户机构id
        merchantProductBind.setProductId(productId);
        merchantProductBindMapper.insert(merchantProductBind);
    }


    /**
     * 商户上传图片
     */
    @Override
    public String uploadImg(String imageUrl, String picType, String picName, String userId,String imageStr) throws Exception {
        log.info("--------商户上传图片--------");
        MerchantImg merchantImg = new MerchantImg();

//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        MultipartFile image = multipartRequest.getFile("image");
//        InputStream inStream = image.getInputStream();
//        //图片转化为字节
//        String photo = getPhoto(inStream);

        PictureVO pictureVO = new PictureVO();
        pictureVO.setImagStr(imageStr);
        pictureVO.setPicName(picName);
        pictureVO.setPicType(picType);

        //调用环迅图片上传接口
        Object data = apiClient.uploadImg(pictureVO).getData();
        JSONObject jsonObject = JSONObject.fromObject(data);
        String imgCode = jsonObject.getString("imgCode");

        //图片上传到阿里云
//        String imgOss = this.uploadImgOss(request);

        merchantImg.setImgUrl(imageUrl);
        merchantImg.setImgCode(imgCode);
        merchantImg.setType(picType);
        merchantImg.setUserId(userId);
        merchantImg.setImgName(picName);
        merchantImg.setUpdateTime(LocalDateTime.now());
        merchantImg.setPlatformTag("hx");

        //插入数据库
        merchantImgMapper.insert(merchantImg);

        return imgCode;
    }
}

//   /**
//    * 上传图片文件返回图片
//    * */
////    @PostMapping("user/merchant/receive/photo")
//    public String getPhoto(InputStream inStream) throws IOException {
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//        //todo 判断照片大小 不能超过2M
//        //创建一个Buffer字符串
//        byte[] buffer = new byte[1024];
//        //每次读取的字符串长度，如果为-1，代表全部读取完毕
//        int len = 0;
//        //使用一个输入流从buffer里把数据读取出来
//        while( (len=inStream.read(buffer)) != -1 ){
//            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
//            outStream.write(buffer, 0, len);
//        }
//        inStream.close();
//        String imageStr = Base64Utils.encodeToString(buffer);
//        return imageStr ;
//    }
//
//    /**
//     * 上传图片到阿里云
//     * 添加商品图片(上传图片)
//     */
//    public String uploadImgOss(HttpServletRequest request) {
//        String imgurl="";
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        MultipartFile file = multipartRequest.getFile("image");
//        if(file==null){
//            throw new BusinessException(CommonErrorCode.FAIL);
//        }
//        String ossObjectNamePrefix = AliOSSUtil.APP_SYS_IMAGETEXT+ "-";
//        String ossObjectName = "";
//        int i=1;
//        if(file!=null){
//            String fileName = file.getOriginalFilename();
//            String prefix=fileName.substring(fileName.lastIndexOf("."));
//            fileName = System.currentTimeMillis()+i+prefix;
//
//            ossObjectName = ossObjectNamePrefix + fileName;
//            AliOSSUtil aliOSSUtil = new AliOSSUtil();
//            try {
//                aliOSSUtil.uploadStreamToOss(ossObjectName,file.getInputStream());
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//            String fileUrl = aliOSSUtil.getFileUrl(ossObjectName);
//            imgurl = imgurl + fileUrl.substring(0,fileUrl.lastIndexOf("?")) + ",";
//        }
//        return imgurl.substring(0, imgurl.length()-1);
//    }




//        String url = this.uploadImgOss(request);
//        byte[] bytes = url.getBytes("utf-8");
//        String pic = Base64.getEncoder().encode(bytes).toString();

//        pictureVO.setPic(pic);


//        String result = null;
//        try {
//            result = SdkUtil.post(pictureVO, routeUploadImg);
//        } catch (BusinessException businessException) {
//            throw new BusinessException(CommonErrorCode.E_301002);//暂定
//        }

//        try {
//            Object data = apiClient.uploadImg(pictureVO).getData();
//
//        } catch (BusinessException businessException) {
//            throw new BusinessException(CommonErrorCode.E_301002);//暂定
//        }

//        JSONObject jsonObject = JSONObject.fromObject(result);
//        String imgCode = jsonObject.get("imgCode").toString();

//        merchantImg.setImgCode(imgCode);
//        merchantImg.setImgUrl(url);
//        merchantImg.setPlatform("hx");



//    /**
//     * 商户入网信息查询
//     */
//    @Override
//    public JSONObject queryMcht(String id) throws IOException {
//        if (StringUtils.isEmpty(id)){
//            throw new BusinessException(CommonErrorCode.E_301003);//暂定
//        }
//        MerchantInfo merchantInfo = merchantInfoMapper.selectById(id);
//        String platformId = merchantInfo.getPlatformId();
//        log.info("--------商户入网信息查询--------");
//        Map<String, Object> paramsData = new HashMap<>();
//
//        paramsData.put("instId", "015001");
//        paramsData.put("mchtNo", id);
//        paramsData.put("instMchtNo", platformId);
//        String result = null;
//        try {
//            result = SdkUtil.post(paramsData, routeQueryMcht);
//        } catch (BusinessException businessException) {
//            throw new BusinessException(CommonErrorCode.E_301002);//暂定
//        }
//        JSONObject jsonObject = JSONObject.fromObject(result);
//        return jsonObject;
//    }
//
//
//    /**
//     * 查询商户审核状态
//     */
//    @Override
//    public JSONObject queryMchtAudit(String id) throws IOException {
//        if (StringUtils.isEmpty(id)){
//            throw new BusinessException(CommonErrorCode.E_301003);//暂定
//        }
//        MerchantInfo merchantInfo = merchantInfoMapper.selectById(id);
//        String platformId = merchantInfo.getPlatformId();
//        log.info("--------查询商户审核状态--------");
//        Map<String, Object> paramsData = new HashMap<>();
//        paramsData.put("instId", "015001");
//        paramsData.put("mchtNo", platformId);
//        String result = null;
//        try {
//            result = SdkUtil.post(paramsData, routeQueryMchtAudit);
//        } catch (BusinessException businessException) {
//            throw new BusinessException(CommonErrorCode.E_301002);//暂定
//        }
//        JSONObject jsonObject = JSONObject.fromObject(result);
//        return jsonObject;
//    }

































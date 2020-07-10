package com.cloudbest.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.common.util.TokenUtil;
import com.cloudbest.order.controller.SecondarilyController;
import com.cloudbest.order.entity.ItemEntity;
import com.cloudbest.order.entity.MainEntity;
import com.cloudbest.order.entity.SecondarilyEntity;
import com.cloudbest.order.enums.StatusEnum;
import com.cloudbest.order.feign.ItemClient;
import com.cloudbest.order.mapper.ItemMapper;
import com.cloudbest.order.mapper.MainMapper;
import com.cloudbest.order.mapper.SecondarilyMapper;
import com.cloudbest.order.otherentity.CItemsImg;
import com.cloudbest.order.service.MainService;
import com.cloudbest.order.vo.MainEntityVO;
import com.cloudbest.order.vo.OrderMainVO;
import com.cloudbest.order.vo.OrderSubmitVO;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@Service
@Transactional
public class MainServiceImpl implements MainService {

    @Autowired
    private MainMapper mainMapper;
    @Autowired
    private SecondarilyMapper secondarilyMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemClient itemClient;
//    String Score_Url = "http://10.103.1.2:8976";
    String Score_Url = "http://localhost:8976";
    //    String Sum_Score = "/youhui/query/sumScore";
    String Sum_Score = "/manager/query/sumScore";
    //    String Add_Score = "/youhui/cloudbest/addScore";
    String Add_Score = "/cloudbest/cloudbest/addScore";
    //    String Sub_Score = "/youhui/cloudbest/subScore";
    String Sub_Score = "/manager/score/subScore";
    //查询用户所有订单（主订单）
    @Override
    public List<MainEntityVO> selectAllOrder(OrderMainVO vo,String token) {
        List<MainEntityVO> mainEntityVOS = new ArrayList<>();
        long userId;
        try {
            userId = TokenUtil.getUserId(token);
        } catch (Exception e) {
            throw new BusinessException(CommonErrorCode.E_110003);//暂定
        }
        //分页参数
        int current =0;
        int size =0;
        if (vo.getCurrent()!=null&&vo.getSize()!=null){
            current = vo.getCurrent();
            size = vo.getSize();
        }else {
            current = 0;
            size = 10;
        }

        QueryWrapper<MainEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("order_time");
        Page<MainEntity> page = new Page<>(current, size);

        IPage<MainEntity> mainEntityIPage = mainMapper.selectPage(page,queryWrapper);

        if (mainEntityIPage.getRecords().size()>0){
            for (MainEntity entity:mainEntityIPage.getRecords()){
                MainEntityVO entityVO = new MainEntityVO();
                BeanUtils.copyProperties(entity,entityVO);
                QueryWrapper<ItemEntity> itemEntityQueryWrapper = new QueryWrapper<>();
                itemEntityQueryWrapper.eq("order_id",entity.getMainOrderId());
                List<ItemEntity> itemEntityList = itemMapper.selectList(itemEntityQueryWrapper);
                for (ItemEntity itemEntity:itemEntityList){
                    entityVO.setSkuId(itemEntity.getProductId());
                    entityVO.setSpuId(itemEntity.getItemId());
                    entityVO.setCount(itemEntity.getProductQuantity());
                    entityVO.setPrice(itemEntity.getProductPrice());
                    entityVO.setName(itemEntity.getProductName());
                    //根据商品id名称查询图片信息
                    Result skuImg = itemClient.selecImgBySkuId(itemEntity.getProductId());
                    String toJSONStringImg = JSON.toJSONString(skuImg.getData());
                    CItemsImg cItemsImg = JSON.parseObject(toJSONStringImg, CItemsImg.class);
                    entityVO.setImg(cItemsImg.getImgUrl());
                }
                mainEntityVOS.add(entityVO);
            }
            return mainEntityVOS;
        }
        return new ArrayList<>();

        //根据主订单号查询子订单号，给出物流状态
        //暂未实现
       /* List<Result> collect = list.stream().map(listTo -> {
            String mainOrderId = listTo.getMainOrderId();
            Result deliveryStatus = secondarilyController.selectDeliveryStatus(mainOrderId);//获取物流状态
            return deliveryStatus;
        }).collect(Collectors.toList());*/

    }


    //分页查询所有状态的订单
    @Override
    public List<MainEntityVO> selectAllStatus(OrderMainVO vo, String token) {
        List<MainEntityVO> mainEntityVOS = new ArrayList<>();
        long userId;
        try {
            userId = TokenUtil.getUserId(token);
        } catch (Exception e) {
            throw new BusinessException(CommonErrorCode.E_110003);//暂定
        }
        //分页参数
        int current =0;
        int size =0;
        if (vo.getCurrent()!=null&&vo.getSize()!=null){
            current = vo.getCurrent();
            size = vo.getSize();
        }else {
            current = 0;
            size = 200;
        }

        QueryWrapper<MainEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("pay_status", vo.getStatus());
        queryWrapper.orderByDesc("order_time");
        Page<MainEntity> page = new Page<>(current, size);
        IPage<MainEntity> mainEntityIPage = mainMapper.selectPage(page,queryWrapper);

        if (mainEntityIPage.getRecords().size()>0){
            for (MainEntity entity:mainEntityIPage.getRecords()){
                MainEntityVO entityVO = new MainEntityVO();
                BeanUtils.copyProperties(entity,entityVO);
                QueryWrapper<ItemEntity> itemEntityQueryWrapper = new QueryWrapper<>();
                itemEntityQueryWrapper.eq("order_id",entity.getMainOrderId());
                List<ItemEntity> itemEntityList = itemMapper.selectList(itemEntityQueryWrapper);
                for (ItemEntity itemEntity:itemEntityList){
                    entityVO.setSkuId(itemEntity.getProductId());
                    entityVO.setSpuId(itemEntity.getItemId());
                    entityVO.setCount(itemEntity.getProductQuantity());
                    entityVO.setPrice(itemEntity.getProductPrice());
                    entityVO.setName(itemEntity.getProductName());
                    //根据商品id名称查询图片信息
                    Result skuImg = itemClient.selecImgBySkuId(itemEntity.getProductId());
                    String toJSONStringImg = JSON.toJSONString(skuImg.getData());
                    CItemsImg cItemsImg = JSON.parseObject(toJSONStringImg, CItemsImg.class);
                    entityVO.setImg(cItemsImg.getImgUrl());
                }
                mainEntityVOS.add(entityVO);
            }
            return mainEntityVOS;
        }
        return new ArrayList<>();
//        List<MainEntity> list = this.mainMapper.selectAllStatus(userId, status);
//        return list;
    }

    //修改订单状态
    @Override
    public void editMain(OrderSubmitVO vo) {
        MainEntity mainEntity = new MainEntity();
        SecondarilyEntity secondarilyEntity = new SecondarilyEntity();
        if((vo.getMainOrderId()==null&&vo.getAncillaryOrderId()==null)||vo.getStatus()==null){
            throw new BusinessException(CommonErrorCode.E_300122);
        }
        //查询主订单信息
        if (vo.getMainOrderId()!=null){
            mainEntity = mainMapper.selectOne(new LambdaQueryWrapper<MainEntity>().eq(MainEntity::getMainOrderId, vo.getMainOrderId()));
        }else {
            //查询子订单信息
            secondarilyEntity = secondarilyMapper.selectOne(new LambdaQueryWrapper<SecondarilyEntity>().eq(SecondarilyEntity::getAncillaryOrderId, vo.getAncillaryOrderId()));
            if (secondarilyEntity==null){
                throw new BusinessException(CommonErrorCode.E_300122);
            }
            //查询主订单信息
            mainEntity = mainMapper.selectOne(new LambdaQueryWrapper<MainEntity>().eq(MainEntity::getMainOrderId, secondarilyEntity.getMainOrderId()));
        }
        if (vo.getStatus()==3){
            if (mainEntity.getPayStatus()==2&&vo.getAncillaryOrderId()!=null){
                //修改子订单信息
                secondarilyEntity.setDeliveryCompany(vo.getDeliveryCompany());
                secondarilyEntity.setDeliveryId(vo.getDeliveryId());
                secondarilyEntity.setDeliveryTime(LocalDateTime.now());
                secondarilyMapper.updateById(secondarilyEntity);
                mainMapper.upd(mainEntity.getMainOrderId(),vo.getStatus());
            }else {
                throw new BusinessException(CommonErrorCode.E_300122.getCode(),"参数有误");
            }
        }

        //用户已收货后给用户添加商城购物券
        //待评价已完成 添加购物券
        if (vo.getStatus()==4){
            if (mainEntity.getPayStatus()==3&&vo.getMainOrderId()!=null){
                //添加用户商城购物券
                String url = Score_Url+Add_Score;
                MultiValueMap<String, Object> requestEntity  = new LinkedMultiValueMap<String, Object>();
                requestEntity.add("cbId", mainEntity.getUserId());
                requestEntity.add("orderNo", vo.getMainOrderId());
                requestEntity.add("amount", mainEntity.getPayAmount());
                //购物消费类型消费的购物券
                requestEntity.add("transactionType", 1);
                RestTemplate restTemplate=new RestTemplate();
                String subScoreResult = restTemplate.postForObject(url, requestEntity, String.class);
                JSONObject subScoreObject =  JSONObject.fromObject(subScoreResult);
                if (subScoreObject.getInt("code")!=100000){
                    throw new BusinessException(CommonErrorCode.E_300122.getCode(),"添加购物券失败");//暂定
                }
                mainMapper.upd(vo.getMainOrderId(),vo.getStatus());
            }else {
                throw new BusinessException(CommonErrorCode.E_300122.getCode(),"参数有误");
            }
        }
    }



    //支付成功后修改订单状态
    @Override
    public MainEntity updateOrderAfterPay(String orderId, Integer status) {

        //修改订单状态
        MainEntity mainEntity = this.mainMapper.selectOne(new LambdaQueryWrapper<MainEntity>().eq(MainEntity::getMainOrderId, orderId));
        if (mainEntity==null){
            throw new BusinessException(CommonErrorCode.E_300122.getCode(),"更新订单失败1");
        }
        if (mainEntity.getPayStatus()!=1&&mainEntity.getPayStatus()!=5){
            throw new BusinessException(CommonErrorCode.E_300125.getCode(),"该笔订单已支付完成，请勿重复提交");
        }
        //如果订单已经付款完成修改过状态，返回异常
        if (mainEntity.getPayStatus()==2){
            throw new BusinessException(CommonErrorCode.E_300125.getCode(),"该笔订单已支付完成，请勿重复提交");
        }

        //设置支付状态
        mainEntity.setPayStatus(2);
        mainEntity.setPayTime(LocalDateTime.now());

        //添加商品销量  sku销量
        List<ItemEntity> itemEntities = this.itemMapper.selectList(new LambdaQueryWrapper<ItemEntity>().eq(ItemEntity::getOrderId, orderId));
        itemEntities.forEach(itemEntity -> {
            Integer productId = itemEntity.getProductId();//skuId
            Integer itemId = itemEntity.getItemId();
            Integer count = itemEntity.getProductQuantity();//数量
            //添加商品的销量
            itemClient.updateSaleVolume(productId, count);
        });

        //this.itemController.secltItemsById()
        //this.itemClient.updateSaleVolume()
        //根据主订单id查询订单详情
        //查询

        //扣除用户积分
        BigDecimal subScore = BigDecimal.ZERO;
        if (null != String.valueOf(mainEntity.getCostScore())&&!String.valueOf(mainEntity.getCostScore()).equals("null")){
            subScore = mainEntity.getCostScore().setScale(2, BigDecimal.ROUND_HALF_EVEN);
        }
        if (subScore != BigDecimal.ZERO) {
            String url = Score_Url + Sub_Score;
            MultiValueMap<String, Object> requestEntity = new LinkedMultiValueMap<String, Object>();
            requestEntity.add("cbId", mainEntity.getUserId());
            requestEntity.add("orderNo", orderId);
            requestEntity.add("score", mainEntity.getCostScore().setScale(2, BigDecimal.ROUND_HALF_EVEN));
            //购物消费类型消费的购物券
            requestEntity.add("transactionType", 1);
            RestTemplate restTemplate = new RestTemplate();
            String subScoreResult = restTemplate.postForObject(url, requestEntity, String.class);
            JSONObject subScoreObject = JSONObject.fromObject(subScoreResult);
            if (subScoreObject.getInt("code") != 100000) {
                throw new BusinessException(CommonErrorCode.E_300122.getCode(),"更新订单失败2");
            }
        }
        mainMapper.updateById(mainEntity);

        return mainEntity;
    }


    //根据主订单号查询主订单
    @Override
    public MainEntity selectMainOrderById(String id) {
        if(id==null){
            throw new BusinessException(CommonErrorCode.FAIL);//未定义枚举
        }
        MainEntity mainEntity = this.mainMapper.selectOne(new LambdaQueryWrapper<MainEntity>().eq(MainEntity::getMainOrderId, id));
        return mainEntity;
    }

    @Override
    public int createMainOrder(MainEntity mainEntity) {
        if(mainEntity==null){
            throw new BusinessException(CommonErrorCode.E_300121);
        }
        int i = mainMapper.insert(mainEntity);
        return i;//创建了几条信息

    }



    @Override
    public MainEntity selectByid(Integer id) {
        if(id==null){
            throw new BusinessException(CommonErrorCode.E_300120);//未定义枚举
        }
        mainMapper.selectById(id);
        MainEntity main = mainMapper.selectOne(new LambdaQueryWrapper<MainEntity>().eq(MainEntity::getId, id));
        return main;
    }


    @Override
    public List<MainEntity> queryList(OrderMainVO info) {
        //分页参数
        int current =0;
        int size =0;
        if (info.getCurrent()!=null&&info.getSize()!=null){
            current = info.getCurrent();
            size = info.getSize();
        }else {
            current = 0;
            size = 200;
        }
        //查询可用图片
        QueryWrapper<MainEntity> queryWrapper = new QueryWrapper();
        if (info.getId()!=null){
            queryWrapper.eq("id", info.getId());
        }
        if (info.getMainOrderId()!=null){
            queryWrapper.eq("main_order_id", info.getMainOrderId());
        }
        if (info.getPayStatus()!=null){
            queryWrapper.eq("pay_status", info.getPayStatus());
        }
        if (info.getPayType()!=null){
            queryWrapper.eq("pay_type", info.getPayType());
        }
        if (info.getDeleteStatus()!=null){
            queryWrapper.eq("delete_status", info.getDeleteStatus());
        }else {
            queryWrapper.eq("delete_status", StatusEnum.SHELVES.getValue());
        }
        Page<MainEntity> page = new Page<>(current, size);
        IPage<MainEntity> mainEntityList = mainMapper.selectPage(page, queryWrapper);
        if (mainEntityList.getTotal()>0){
            return mainEntityList.getRecords();
        }
        return new ArrayList<>();
    }


    @Override
    public MainEntity offMain(Integer id) {
        MainEntity mainEntity = mainMapper.selectById(id);
        if(mainEntity==null||mainEntity.getId()==null){
            throw new BusinessException(CommonErrorCode.E_300122);
        }
        mainEntity.setDeleteStatus(0);
        mainMapper.updateById(mainEntity);
        return mainEntity;
    }

    //
    @Override
    public void deleteMainOrder(String id) {
        this.mainMapper.delete(new LambdaQueryWrapper<MainEntity>().eq(MainEntity::getMainOrderId, id));
    }

}

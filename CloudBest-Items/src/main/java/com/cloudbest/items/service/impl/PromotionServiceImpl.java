package com.cloudbest.items.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.util.DateUtil;
import com.cloudbest.common.util.RandomUuidUtil;
import com.cloudbest.common.util.StringUtil;
import com.cloudbest.items.entity.*;
import com.cloudbest.items.enums.StatusEnum;
import com.cloudbest.items.mapper.*;
import com.cloudbest.items.service.ItemsInfoService;
import com.cloudbest.items.service.PromotionService;
import com.cloudbest.items.vo.ItemsInfoVO;
import com.cloudbest.items.vo.ItemsStockVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 商品详情表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private ItemsInfoMapper itemsInfoMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private PromotionMapper promotionMapper;

    String errorMessage = "网络小憩~请稍后再试！";


    @Override
    public List<Map<String, Object>> queryPromotionItems(String ids) {
        List<Map<String,Object>> mapList = new ArrayList<>();

        String idStrs = ids;
        List<String> itemIds = new ArrayList<>();
        itemIds = Arrays.asList(idStrs.split(","));
        for (String itemId:itemIds){
            ItemsInfo itemInfo = itemsInfoMapper.selectById(itemId);
            if (null==itemInfo){
                throw new BusinessException(CommonErrorCode.E_901001.getCode(),errorMessage);
            }
            //查询商品数据的主图、名称、价格、描述
            Map<String,Object> map = new HashMap<>();
            //查询商品图片
            QueryWrapper<ItemsImg> ItemsImgQueryWrapper = new QueryWrapper();
            ItemsImgQueryWrapper.eq("item_id", itemInfo.getId());
            ItemsImgQueryWrapper.eq("status", 1);
            ItemsImgQueryWrapper.eq("sort", 0);
            List<ItemsImg> imgList = itemsImgMapper.selectList(ItemsImgQueryWrapper);
            if (imgList==null||imgList.size()==0){
                throw new BusinessException(CommonErrorCode.E_901009.getCode(),errorMessage);
            }

            //查询出商品的stock信息  获取对应skuId的价格
            QueryWrapper<Stock> stockQueryWrapper = new QueryWrapper();
            stockQueryWrapper.eq("item_id", itemInfo.getId());
            stockQueryWrapper.orderByAsc("sale_price");
            List<Stock> stockList = stockMapper.selectList(stockQueryWrapper);
            if (stockList==null||stockList.size()==0){
                throw new BusinessException(CommonErrorCode.E_901008.getCode(),errorMessage);
            }
            ItemsStockVO stockVO = new ItemsStockVO();
            BeanUtils.copyProperties(stockList.get(0),stockVO);
            //最大可用购物券 销售价格*最可用购物券比例，保留两位小数
            BigDecimal score = stockVO.getSalePrice().multiply(new BigDecimal(String.valueOf(stockVO.getScoreScale()))).setScale(2,BigDecimal.ROUND_HALF_EVEN);
            //实际抵扣购物券后的销售金额  销售金额-可抵用的购物券
            BigDecimal score_price = stockVO.getSalePrice().subtract(new BigDecimal(String.valueOf(score))).setScale(2,BigDecimal.ROUND_HALF_EVEN);
            map.put("itemId",itemInfo.getId());
            map.put("name",itemInfo.getName());
            map.put("desc",itemInfo.getDescription());
            map.put("img",imgList.get(0).getImgUrl());
            map.put("prePrice",stockVO.getPreSalePrice());
            map.put("price",stockVO.getSalePrice());
            map.put("score",score);
            map.put("score_price",score_price);
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public Promotion queryPromotionFirst() {
        //拼装参数构造器
        QueryWrapper<Promotion> promotionQuery = new QueryWrapper<>();
        promotionQuery.ge("end_time",DateUtil.getCurrDate());
        promotionQuery.eq("status",1);
        promotionQuery.orderByAsc("end_time");
        //查询该活动下商品信息

        List<Promotion> promotionList = promotionMapper.selectList(promotionQuery);
        if (promotionList.size()>0){
            return promotionList.get(0);
        }
        return null;
    }
}

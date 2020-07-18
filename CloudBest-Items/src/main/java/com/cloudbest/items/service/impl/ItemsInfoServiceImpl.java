package com.cloudbest.items.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.util.DateUtil;
import com.cloudbest.common.util.RandomUuidUtil;
import com.cloudbest.common.util.StringUtil;
import com.cloudbest.items.entity.ItemsImg;
import com.cloudbest.items.entity.ItemsInfo;
import com.cloudbest.items.entity.Repository;
import com.cloudbest.items.entity.Stock;
import com.cloudbest.items.enums.StatusEnum;
import com.cloudbest.items.mapper.ItemsImgMapper;
import com.cloudbest.items.mapper.ItemsInfoMapper;
import com.cloudbest.items.mapper.RepositoryMapper;
import com.cloudbest.items.mapper.StockMapper;
import com.cloudbest.items.service.ItemsInfoService;
import com.cloudbest.items.vo.ItemsInfoVO;
import com.cloudbest.items.vo.ItemsStockVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

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
public class ItemsInfoServiceImpl implements ItemsInfoService {

    @Autowired
    private ItemsInfoMapper itemsInfoMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private RepositoryMapper repositoryMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    String errorMessage = "网络小憩~请稍后再试！";

    @Override
    public ItemsInfo createNewItem(ItemsInfo info){
        ItemsInfo itemsInfo = new ItemsInfo();
        itemsInfo.setId(Integer.valueOf(RandomUuidUtil.generateNumString(8)));
        itemsInfo.setBarCode(info.getBarCode());
        itemsInfo.setDescription(info.getDescription());
        itemsInfo.setFirstCategoryId(info.getFirstCategoryId());
        itemsInfo.setSecondCategoryId(info.getSecondCategoryId());
        itemsInfo.setThirdCategoryId(info.getThirdCategoryId());
        itemsInfo.setIsView(info.getIsView());
        itemsInfo.setName(info.getName());
        itemsInfo.setSpuSaltVolume(info.getSpuSaltVolume());
        itemsInfo.setItemInfo(info.getItemInfo());
        itemsInfo.setSupplierId(info.getSupplierId());
        itemsInfo.setGroudingTime(info.getGroudingTime());
        itemsInfo.setValidityTime(info.getValidityTime());
        itemsInfo.setCreateTime(new Date());
        itemsInfo.setUpdateTime(new Date());
        int result = itemsInfoMapper.insert(itemsInfo);
        if (result==1){
            return itemsInfo;
        }else {
            throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
        }
    }

    @Override
    public ItemsInfo updateItems(ItemsInfo info){
        ItemsInfo item = itemsInfoMapper.selectById(info.getId());
        if (null!=item){
//            if (info.getId()!=null){
//                item.setId(Integer.valueOf(RandomUuidUtil.generateNumString(8)));
//            }
            if (info.getBarCode()!=null){
                item.setBarCode(info.getBarCode());
            }
            if (info.getDescription()!=null){
                item.setDescription(info.getDescription());
            }
            if (info.getItemInfo()!=null){
                item.setItemInfo(info.getItemInfo());
            }
            if (info.getFirstCategoryId()!=null){
                item.setFirstCategoryId(info.getFirstCategoryId());
            }
            if (info.getSecondCategoryId()!=null){
                item.setSecondCategoryId(info.getSecondCategoryId());
            }
            if (info.getThirdCategoryId()!=null){
                item.setThirdCategoryId(info.getThirdCategoryId());
            }
            if (info.getIsView()!=null){
                item.setIsView(info.getIsView());
            }
            if (info.getName()!=null){
                item.setName(info.getName());
            }
            if (info.getSpuSaltVolume()!=null){
                item.setSpuSaltVolume(info.getSpuSaltVolume());
            }
            if (info.getSupplierId()!=null){
                item.setSupplierId(info.getSupplierId());
            }
            if (info.getGroudingTime()!=null){
                item.setGroudingTime(info.getGroudingTime());
            }
            if (info.getValidityTime()!=null){
                item.setValidityTime(info.getValidityTime());
            }
            if (info.getCreateTime()!=null){
                item.setCreateTime(new Date());
            }
            item.setUpdateTime(new Date());
            int result = itemsInfoMapper.updateById(item);
            if (result==1){
                return item;
            }else {
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901001.getCode(),errorMessage);
        }
    }

    @Override
    public ItemsInfo offItems(Integer itemId){
        ItemsInfo item = itemsInfoMapper.selectById(itemId);
        if (null!=item){
            item.setUpdateTime(new Date());
            item.setIsView(9);
            int result = itemsInfoMapper.updateById(item);
            if (result==1){
                return item;
            }else {
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901001.getCode(),errorMessage);
        }
    }

    @Override
    public ItemsInfo onItems(Integer itemId){
        ItemsInfo item = itemsInfoMapper.selectById(itemId);
        if (null!=item){
            item.setUpdateTime(new Date());
            item.setIsView(1);
            int result = itemsInfoMapper.updateById(item);
            if (result==1){
                return item;
            }else {
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901001.getCode(),errorMessage);
        }
    }

    @Override
    public ItemsInfo onOffItems(Integer itemId,Integer isView){
        ItemsInfo item = itemsInfoMapper.selectById(itemId);
        if (null!=item){
            item.setUpdateTime(new Date());
            item.setIsView(isView);
            int result = itemsInfoMapper.updateById(item);
            if (result==1){
                return item;
            }else {
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901001.getCode(),errorMessage);
        }
    }

    @Override
    public List<Map<String, Object>> queryTopQualityItems() {
        //分页参数
        List<Map<String,Object>> mapList = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();
        idList.add(64155212);
        idList.add(51003153);
        idList.add(35835258);
        idList.add(25026340);
        idList.add(10260214);
        idList.add(76322684);
        //查询前端显示并且 当前时间处于商品的上架时间与下架时间中间+匹配参数的商品数据
        QueryWrapper<ItemsInfo> queryWrapper = new QueryWrapper();
        queryWrapper.in("id",idList);
        List<ItemsInfo> itemsInfoList = itemsInfoMapper.selectList(queryWrapper);
        if (itemsInfoList.size()>0){
            for (ItemsInfo itemInfo:itemsInfoList){
                //查询商品数据的主图、名称、价格、描述
                Map<String,Object> map = new HashMap<>();
                //查询商品图片
                QueryWrapper<ItemsImg> ItemsImgQueryWrapper = new QueryWrapper();
                ItemsImgQueryWrapper.eq("item_id", itemInfo.getId());
                ItemsImgQueryWrapper.eq("status", 1);
                ItemsImgQueryWrapper.eq("sort", 0);
                List<ItemsImg> imgList = itemsImgMapper.selectList(ItemsImgQueryWrapper);
                if (imgList==null||imgList.size()==0){
                    ItemsImg img = new ItemsImg();
                    img.setImgUrl("http://cloudbest.oss-cn-shanghai.aliyuncs.com/appsys-imagetext-uploadImg-1592994360706.jpg");
                    img.setStatus(1);
                    img.setSort(0);
                    img.setImgDesc("默认");
                    img.setSkuId(0);
                    img.setItemId(0);
                    img.setUpdateTime(DateUtil.getCurrDate());
                    imgList.add(img);
//                    throw new BusinessException(CommonErrorCode.E_901009.getCode(),errorMessage);
                }

                //查询出商品的stock信息  获取对应skuId的价格
                QueryWrapper<Stock> stockQueryWrapper = new QueryWrapper();
                stockQueryWrapper.eq("item_id", itemInfo.getId());
                stockQueryWrapper.orderByAsc("sale_price");
                List<Stock> stockList = stockMapper.selectList(stockQueryWrapper);
                if (stockList==null||stockList.size()==0){
//                    throw new BusinessException(CommonErrorCode.E_901008.getCode(),errorMessage);
                    continue;
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
        }
        return mapList;
    }

    @Override
    public ItemsInfo queryItemsById(Integer spuId) {
        ItemsInfo itemsInfo = this.itemsInfoMapper.selectById(spuId);
        return itemsInfo;
    }

    @Override
    public ItemsInfo getItemById(Integer itemId) {
        ItemsInfo item = itemsInfoMapper.selectById(itemId);
        if (null!=item){
            return item;
        }else {
            throw new BusinessException(CommonErrorCode.E_901001.getCode(),errorMessage);
        }
    }

    @Override
    public Map<String, Object> getItemInfo( Integer itemId) {
        Map<String, Object> map = new HashMap<>();
        //查询商品自身基本信息
        ItemsInfo itemsInfo = itemsInfoMapper.selectById(itemId);
        ItemsInfoVO infoVO = new ItemsInfoVO();
        BeanUtils.copyProperties(itemsInfo,infoVO);
        if (null!=itemsInfo){
            //查询商品sku相关信息
            QueryWrapper<Stock> stockQueryWrapper = new QueryWrapper();
            stockQueryWrapper.eq("item_id", itemId);
            stockQueryWrapper.orderByAsc("sale_price");
            List<Stock> stockList = stockMapper.selectList(stockQueryWrapper);
            if (stockList==null||stockList.size()==0){
                throw new BusinessException(CommonErrorCode.E_901008.getCode(),errorMessage);
            }
            List<ItemsStockVO> stockVOS = copyVos(stockList);
            //获取该商品的仓库，判断仓库是否可用
            Repository repository = repositoryMapper.selectById(stockList.get(0).getRepositoryId());
            if (null==repository||repository.getStatus()==0){
                repository.setAddress("上海市");
                repository.setAdmin("admin");
                repository.setCity("上海市 上海市");
                repository.setName("默认仓库");
                repository.setPhone("11234567890");
                repository.setStatus(1);
                repository.setSupplierId(0);
                repository.setTransportAmount(BigDecimal.ZERO);
                repository.setUpdateTime(DateUtil.getCurrDate());

//                throw new BusinessException(CommonErrorCode.E_901010.getCode(),errorMessage);
            }
            //查询商品图片
            QueryWrapper<ItemsImg> ItemsImgQueryWrapper = new QueryWrapper();
            ItemsImgQueryWrapper.eq("item_id", itemId);
            ItemsImgQueryWrapper.eq("status", 1);
            ItemsImgQueryWrapper.groupBy("sort");
            List<ItemsImg> imgList = itemsImgMapper.selectList(ItemsImgQueryWrapper);
            if (imgList==null||imgList.size()==0){
                ItemsImg img = new ItemsImg();
                img.setImgUrl("http://cloudbest.oss-cn-shanghai.aliyuncs.com/appsys-imagetext-uploadImg-1592994360706.jpg");
                img.setStatus(1);
                img.setSort(0);
                img.setImgDesc("默认");
                img.setSkuId(0);
                img.setItemId(0);
                img.setUpdateTime(DateUtil.getCurrDate());
                imgList.add(img);
//                throw new BusinessException(CommonErrorCode.E_901009.getCode(),errorMessage);
            }
            infoVO.setStockVOList(stockVOS);
            //最大可用购物券 销售价格*最可用购物券比例，保留两位小数
            BigDecimal score = stockVOS.get(0).getSalePrice().multiply(new BigDecimal(String.valueOf(stockVOS.get(0).getScoreScale()))).setScale(2,BigDecimal.ROUND_HALF_EVEN);
            //实际抵扣购物券后的销售金额  销售金额-可抵用的购物券
            BigDecimal score_price = stockVOS.get(0).getSalePrice().subtract(new BigDecimal(String.valueOf(score))).setScale(2,BigDecimal.ROUND_HALF_EVEN);
            infoVO.setPreSalePrice(stockVOS.get(0).getPreSalePrice());
            infoVO.setSalePrice(stockVOS.get(0).getSalePrice());
            infoVO.setScore(score);
            infoVO.setScorePrice(score_price);
            infoVO.setTransportAmount(repository.getTransportAmount());
            infoVO.setImgDefault(imgList.get(0).getImgUrl());
            //商品详情
            if (StringUtil.isNotBlank(itemsInfo.getItemInfo())){
                List<String> itemInfos = Arrays.asList(itemsInfo.getItemInfo().split(","));
                infoVO.setItemInfo(itemInfos);
            }
            //拼装返回map
            map.put("itemsInfo",infoVO);

        }else {
            throw new BusinessException(CommonErrorCode.E_901001.getCode(),errorMessage);
        }
        return map;
    }


    @Override
    public ItemsInfoVO getItemInfoById( Integer itemId) {
        //查询商品自身基本信息
        ItemsInfo itemsInfo = itemsInfoMapper.selectById(itemId);
        ItemsInfoVO infoVO = new ItemsInfoVO();
        BeanUtils.copyProperties(itemsInfo,infoVO);
        if (null!=itemsInfo){
            //查询商品sku相关信息
            QueryWrapper<Stock> stockQueryWrapper = new QueryWrapper();
            stockQueryWrapper.eq("item_id", itemId);
            stockQueryWrapper.orderByAsc("sale_price");
            List<Stock> stockList = stockMapper.selectList(stockQueryWrapper);
            if (stockList==null||stockList.size()==0){
                throw new BusinessException(CommonErrorCode.E_901008.getCode(),errorMessage);
            }
            List<ItemsStockVO> stockVOS = copyVos(stockList);
            //获取该商品的仓库，判断仓库是否可用
            Repository repository = repositoryMapper.selectById(stockList.get(0).getRepositoryId());
            if (null==repository||repository.getStatus()==0){
                repository.setAddress("上海市");
                repository.setAdmin("admin");
                repository.setCity("上海市 上海市");
                repository.setName("默认仓库");
                repository.setPhone("11234567890");
                repository.setStatus(1);
                repository.setSupplierId(0);
                repository.setUpdateTime(DateUtil.getCurrDate());
            }
            //查询商品图片
            QueryWrapper<ItemsImg> ItemsImgQueryWrapper = new QueryWrapper();
            ItemsImgQueryWrapper.eq("item_id", itemId);
            ItemsImgQueryWrapper.eq("status", 1);
            ItemsImgQueryWrapper.groupBy("sort");
            List<ItemsImg> imgList = itemsImgMapper.selectList(ItemsImgQueryWrapper);
            if (imgList==null||imgList.size()==0){
                ItemsImg img = new ItemsImg();
                img.setImgUrl("http://cloudbest.oss-cn-shanghai.aliyuncs.com/appsys-imagetext-uploadImg-1592994360706.jpg");
                img.setStatus(1);
                img.setSort(0);
                img.setImgDesc("默认");
                img.setSkuId(0);
                img.setItemId(0);
                img.setUpdateTime(DateUtil.getCurrDate());
                imgList.add(img);
//                throw new BusinessException(CommonErrorCode.E_901009.getCode(),errorMessage);
            }
            infoVO.setImgList(imgList);
            infoVO.setStockVOList(stockVOS);
            //最大可用购物券 销售价格*最可用购物券比例，保留两位小数
            BigDecimal score = stockVOS.get(0).getSalePrice().multiply(new BigDecimal(String.valueOf(stockVOS.get(0).getScoreScale()))).setScale(2,BigDecimal.ROUND_HALF_EVEN);
            //实际抵扣购物券后的销售金额  销售金额-可抵用的购物券
            BigDecimal score_price = stockVOS.get(0).getSalePrice().subtract(new BigDecimal(String.valueOf(score))).setScale(2,BigDecimal.ROUND_HALF_EVEN);
            infoVO.setPreSalePrice(stockVOS.get(0).getPreSalePrice());
            infoVO.setSalePrice(stockVOS.get(0).getSalePrice());
            infoVO.setScore(score);
            infoVO.setScorePrice(score_price);
            //商品详情
            List<String> itemInfos = new ArrayList<>();
            if (StringUtil.isNotBlank(itemsInfo.getItemInfo())) {
                itemInfos = Arrays.asList(itemsInfo.getItemInfo().split(","));
            }
            infoVO.setItemInfo(itemInfos);
        }else {
            throw new BusinessException(CommonErrorCode.E_901001.getCode(),errorMessage);
        }
        return infoVO;
    }

    private List<ItemsStockVO> copyVos(List<Stock> stockList) {
        List<ItemsStockVO> stockVOS = new ArrayList<>();
        for (int i=0;i<stockList.size();i++){
            ItemsStockVO stockVO = new ItemsStockVO();

            BeanUtils.copyProperties(stockList.get(i),stockVO);

            //查询出商品对应skuId的图片信息
            QueryWrapper<ItemsImg> ItemsImgQueryWrapper = new QueryWrapper();
            ItemsImgQueryWrapper.eq("item_id", stockVO.getItemId());
            ItemsImgQueryWrapper.eq("sku_id", stockVO.getId());
            ItemsImgQueryWrapper.eq("status", 1);
            ItemsImg img = itemsImgMapper.selectOne(ItemsImgQueryWrapper);
            if (img==null){
                QueryWrapper<ItemsImg> ItemsSkuImgQueryWrapper = new QueryWrapper();
                ItemsSkuImgQueryWrapper.eq("item_id", stockVO.getItemId());
                ItemsSkuImgQueryWrapper.eq("status", 1);
                ItemsSkuImgQueryWrapper.eq("sort", 0);
                img = itemsImgMapper.selectOne(ItemsImgQueryWrapper);
            }
            //最大可用购物券 销售价格*最可用购物券比例，保留两位小数
            BigDecimal score = stockList.get(i).getSalePrice().multiply(new BigDecimal(String.valueOf(stockList.get(i).getScoreScale()))).setScale(2,BigDecimal.ROUND_HALF_EVEN);
            //实际抵扣购物券后的销售金额  销售金额-可抵用的购物券
            BigDecimal score_price = stockList.get(i).getSalePrice().subtract(new BigDecimal(String.valueOf(score))).setScale(2,BigDecimal.ROUND_HALF_EVEN);
            stockVO.setScore_price(score_price);
            stockVO.setScore(score);
            stockVO.setImgUrl(img.getImgUrl());
            stockVOS.add(stockVO);
        }
        return stockVOS;
    }

    @Override
    public Map<String, Object> getItemInfoSku(Integer skuId, Integer itemId) {
        Map<String, Object> map = new HashMap<>();
        //查询出商品的stock信息  获取对应skuId的价格
        QueryWrapper<Stock> stockQueryWrapper = new QueryWrapper();
        stockQueryWrapper.eq("item_id", itemId);
        stockQueryWrapper.eq("id", skuId);
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
        stockVO.setScoreScale(score_price);
        stockVO.setScore(score);
        //查询出商品对应skuId的图片信息
        QueryWrapper<ItemsImg> ItemsImgQueryWrapper = new QueryWrapper();
        ItemsImgQueryWrapper.eq("item_id", itemId);
        ItemsImgQueryWrapper.eq("sku_id", skuId);
        ItemsImgQueryWrapper.eq("status", 1);
        List<ItemsImg> imgList = itemsImgMapper.selectList(ItemsImgQueryWrapper);
        if (imgList==null||imgList.size()==0){
            QueryWrapper<ItemsImg> ItemsSkuImgQueryWrapper = new QueryWrapper();
            ItemsSkuImgQueryWrapper.eq("item_id", itemId);
            ItemsSkuImgQueryWrapper.eq("status", 1);
            ItemsImgQueryWrapper.orderByAsc("sort");
            imgList = itemsImgMapper.selectList(ItemsImgQueryWrapper);
        }
        //拼装返回map
        map.put("itemId",itemId);
        map.put("skuId",skuId);
        map.put("itemImg",imgList.get(0));
        map.put("itemStock",stockVO);
        return map;
    }

    @Override
    public Map<String, BigDecimal> totalItemsPrice(Integer skuId, Integer itemId, Integer count) throws BusinessException {
        Map<String, BigDecimal> resultMap = new HashMap<>();
        //总金额
        BigDecimal totalPrice = BigDecimal.ZERO;
        //总购物券
        BigDecimal totalScore = BigDecimal.ZERO;
        //查询出商品的stock信息  获取对应skuId的价格
        QueryWrapper<Stock> stockQueryWrapper = new QueryWrapper();
        stockQueryWrapper.eq("item_id", itemId);
        stockQueryWrapper.eq("id", skuId);
        List<Stock> stockList = stockMapper.selectList(stockQueryWrapper);
        if (stockList==null||stockList.size()==0){
            throw new BusinessException(CommonErrorCode.E_901004.getCode(),errorMessage);
        }
        //获取该商品的仓库，判断仓库是否可用
        Repository repository = repositoryMapper.selectById(stockList.get(0).getRepositoryId());
        if (null==repository||repository.getStatus()==0){
            repository.setAddress("上海市");
            repository.setAdmin("admin");
            repository.setCity("上海市 上海市");
            repository.setName("默认仓库");
            repository.setPhone("11234567890");
            repository.setStatus(1);
            repository.setSupplierId(0);
            repository.setTransportAmount(BigDecimal.ZERO);
            repository.setUpdateTime(DateUtil.getCurrDate());
        }
        //最大可用购物券 销售价格*最可用购物券比例，保留两位小数
        BigDecimal score = stockList.get(0).getSalePrice().multiply(new BigDecimal(String.valueOf(stockList.get(0).getScoreScale()))).setScale(2,BigDecimal.ROUND_HALF_EVEN);
        //实际抵扣购物券后的销售金额  销售金额-可抵用的购物券
        BigDecimal score_price = stockList.get(0).getSalePrice().subtract(new BigDecimal(String.valueOf(score))).setScale(2,BigDecimal.ROUND_HALF_EVEN);

        totalScore = score.multiply(new BigDecimal(count)).setScale(2,BigDecimal.ROUND_HALF_EVEN);
        totalPrice = score_price.multiply(new BigDecimal(count)).setScale(2,BigDecimal.ROUND_HALF_EVEN);
        resultMap.put("score",totalScore);
        resultMap.put("price",totalPrice);
        resultMap.put("transportAmount",repository.getTransportAmount());
        return resultMap;
    }

    @Override
    public void deleteItems(Integer itemId) {
        ItemsInfo item = itemsInfoMapper.selectById(itemId);
        if (null!=item){
            int result = itemsInfoMapper.deleteById(itemId);
            if (result==0){
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901001.getCode(),errorMessage);
        }
    }

    @Override
    public List<Map<String,Object>> queryItems(ItemsInfoVO vo) {
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
        List<Map<String,Object>> mapList = new ArrayList<>();
        //拼装参数构造器
        QueryWrapper<ItemsInfo> itemQuery = new QueryWrapper<>();
        itemQuery.eq("is_view", StatusEnum.SHELVES.getValue());
//        itemQuery.le("grouding_time", DateUtil.getCurrDate());
//        itemQuery.ge("validity_time", DateUtil.getCurrDate());
        if (StringUtil.isNotBlank(vo.getName())){
            itemQuery.like("name",vo.getName());
        }

        if (null!=vo.getFirstCategoryId()){
            itemQuery.eq("first_category_id",vo.getFirstCategoryId());
        }

        if (null!=vo.getSecondCategoryId()){
            itemQuery.eq("second_category_id",vo.getSecondCategoryId());
        }

        if (null!=vo.getThirdCategoryId()){
            itemQuery.eq("third_category_id",vo.getThirdCategoryId());
        }

        itemQuery.orderByDesc("grouding_time");
        //查询前端显示并且 当前时间处于商品的上架时间与下架时间中间+匹配参数的商品数据

        Page<ItemsInfo> page = new Page<>(current, size);
        IPage<ItemsInfo> itemsInfoList = itemsInfoMapper.selectPage(page, itemQuery);
//        List<ItemsInfo> itemsInfoList = itemsInfoMapper.selectList(itemQuery);
        if (itemsInfoList.getTotal()>0){
            for (ItemsInfo itemInfo:itemsInfoList.getRecords()){
                //查询商品数据的主图、名称、价格、描述
                Map<String,Object> map = new HashMap<>();
                //查询商品图片
                QueryWrapper<ItemsImg> ItemsImgQueryWrapper = new QueryWrapper();
                ItemsImgQueryWrapper.eq("item_id", itemInfo.getId());
                ItemsImgQueryWrapper.eq("status", 1);
                ItemsImgQueryWrapper.eq("sort", 0);
                List<ItemsImg> imgList = itemsImgMapper.selectList(ItemsImgQueryWrapper);
                if (imgList==null||imgList.size()==0){
                    ItemsImg img = new ItemsImg();
                    img.setImgUrl("http://cloudbest.oss-cn-shanghai.aliyuncs.com/appsys-imagetext-uploadImg-1592994360706.jpg");
                    img.setStatus(1);
                    img.setSort(0);
                    img.setImgDesc("默认");
                    img.setSkuId(0);
                    img.setItemId(0);
                    img.setUpdateTime(DateUtil.getCurrDate());
                    imgList.add(img);
//                    throw new BusinessException(CommonErrorCode.E_901009.getCode(),errorMessage);
                }

                //查询出商品的stock信息  获取对应skuId的价格
                QueryWrapper<Stock> stockQueryWrapper = new QueryWrapper();
                stockQueryWrapper.eq("item_id", itemInfo.getId());
                stockQueryWrapper.orderByAsc("sale_price");
                List<Stock> stockList = stockMapper.selectList(stockQueryWrapper);
                if (stockList==null||stockList.size()==0){
//                    throw new BusinessException(CommonErrorCode.E_901008.getCode(),errorMessage);
                    continue;
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
                map.put("totalCount",itemsInfoList.getTotal());
                map.put("page",itemsInfoList.getPages());
                mapList.add(map);
            }
        }
        return mapList;
    }


    @Override
    public List<Map<String,Object>> queryFavoriteItems() {
        //分页参数
        List<Map<String,Object>> mapList = new ArrayList<>();

        //查询前端显示并且 当前时间处于商品的上架时间与下架时间中间+匹配参数的商品数据

        List<ItemsInfo> itemsInfoList = itemsInfoMapper.selectFavorityList(DateUtil.getCurrDate());
        if (itemsInfoList.size()>0){
            for (ItemsInfo itemInfo:itemsInfoList){
                //查询商品数据的主图、名称、价格、描述
                Map<String,Object> map = new HashMap<>();
                //查询商品图片
                QueryWrapper<ItemsImg> ItemsImgQueryWrapper = new QueryWrapper();
                ItemsImgQueryWrapper.eq("item_id", itemInfo.getId());
                ItemsImgQueryWrapper.eq("status", 1);
                ItemsImgQueryWrapper.eq("sort", 0);
                List<ItemsImg> imgList = itemsImgMapper.selectList(ItemsImgQueryWrapper);
                if (imgList==null||imgList.size()==0){
                    ItemsImg img = new ItemsImg();
                    img.setImgUrl("http://cloudbest.oss-cn-shanghai.aliyuncs.com/appsys-imagetext-uploadImg-1592994360706.jpg");
                    img.setStatus(1);
                    img.setSort(0);
                    img.setImgDesc("默认");
                    img.setSkuId(0);
                    img.setItemId(0);
                    img.setUpdateTime(DateUtil.getCurrDate());
                    imgList.add(img);
//                    throw new BusinessException(CommonErrorCode.E_901009.getCode(),errorMessage);
                }

                //查询出商品的stock信息  获取对应skuId的价格
                QueryWrapper<Stock> stockQueryWrapper = new QueryWrapper();
                stockQueryWrapper.eq("item_id", itemInfo.getId());
                stockQueryWrapper.orderByAsc("sale_price");
                List<Stock> stockList = stockMapper.selectList(stockQueryWrapper);
                if (stockList==null||stockList.size()==0){
//                    throw new BusinessException(CommonErrorCode.E_901008.getCode(),errorMessage);
                    continue;
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
        }
        return mapList;
    }

    @Override
    public List<ItemsInfo> queryItemsByName(Integer itemId,String name) {
        List<ItemsInfo> itemsInfos = new ArrayList<>();
        QueryWrapper<ItemsInfo> queryWrapper = new QueryWrapper();
        if (name!=null){
            queryWrapper.like("name", name);
        }
        if (itemId!=null){
            queryWrapper.eq("id", itemId);
        }
        itemsInfos = itemsInfoMapper.selectList(queryWrapper);
        if (itemsInfos==null||itemsInfos.size()==0){
            return new ArrayList<>();
        }
        return itemsInfos;
    }
}

package com.cloudbest.items.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.items.entity.ItemsInfo;
import com.cloudbest.items.entity.Stock;
import com.cloudbest.items.enums.StatusEnum;
import com.cloudbest.items.mapper.ItemsInfoMapper;
import com.cloudbest.items.mapper.StockMapper;
import com.cloudbest.items.service.StockService;
import com.cloudbest.items.vo.ItemsStockVO;
import com.cloudbest.items.vo.SkuLockVO;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 库存表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@Service
@Transactional
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private ItemsInfoMapper infoMapper;
    String errorMessage = "网络小憩~请稍后再试！";

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private StringRedisTemplate redisTemplate;


//测试
    @Override
    public void test(Integer id, Integer num) {
        List<Stock> stocks = stockMapper.checkStore(id, num);
        System.out.println(stocks);
    }
//测试
    @Override
    public void test2(Integer id, Integer num) {
        int store = stockMapper.lockStore(id, num);
        System.out.println(store);
    }

    @Override
    public Stock updateSaleVolume(Integer id, Integer num) {
        //增加sku销量
        Stock stock = stockMapper.selectById(id);
        if (stock==null){
            throw new BusinessException(CommonErrorCode.E_901001.getCode(),"没有对应的sku商品信息");
        }
        stock.setSkuSaltVolume(stock.getSkuSaltVolume()+num);
        stockMapper.updateById(stock);
        //增加SPU销量

        ItemsInfo info = infoMapper.selectById(stock.getItemId());
        if (info==null){
            throw new BusinessException(CommonErrorCode.E_901001.getCode(),"没有对应的SPU商品信息");
        }
        info.setSpuSaltVolume(info.getSpuSaltVolume()+num);
        infoMapper.updateById(info);
        return stock;
    }

    @Override
    public Stock offStock(Integer id) {
        Stock stock = stockMapper.selectById(id);
        if (null!=stock){
            stock.setUpdateTime(new Date());
            stock.setStatus(9);
            int result = stockMapper.updateById(stock);
            if (result==1){
                return stock;
            }else {
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901001.getCode(),errorMessage);
        }
    }

    @Override
    public Stock onStock(Integer id) {
        Stock stock = stockMapper.selectById(id);
        if (null!=stock){
            stock.setUpdateTime(new Date());
            stock.setStatus(1);
            int result = stockMapper.updateById(stock);
            if (result==1){
                return stock;
            }else {
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901001.getCode(),errorMessage);
        }
    }

    @Override
    public Stock onOffStock(Integer id,Integer status) {
        Stock stock = stockMapper.selectById(id);
        if (null!=stock){
            stock.setUpdateTime(new Date());
            stock.setStatus(status);
            int result = stockMapper.updateById(stock);
            if (result==1){
                return stock;
            }else {
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901001.getCode(),errorMessage);
        }
    }




    @Override
    public Stock createNewStock(Stock info) {
        Stock stock = new Stock();
        stock.setItemAttr(info.getItemAttr());
        stock.setItemId(info.getItemId());
        stock.setLenWidHeight(info.getLenWidHeight());
        stock.setRepositoryId(info.getRepositoryId());
        stock.setPreSalePrice(info.getPreSalePrice().setScale(2, BigDecimal.ROUND_HALF_EVEN));
        stock.setSalePrice(info.getSalePrice().setScale(2, BigDecimal.ROUND_HALF_EVEN));
        stock.setScoreScale(info.getScoreScale());
        stock.setWeight(info.getWeight());
        stock.setSkuSaltVolume(info.getSkuSaltVolume());
        stock.setUsableStock(info.getUsableStock());
        stock.setStatus(info.getStatus());
        stock.setUpdateTime(new Date());
        int result = stockMapper.insert(stock);
        if (result==1){
            return stock;
        }else {
            throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
        }
    }

    @Override
    public Stock updateStock(Stock info) {
        Stock stock = stockMapper.selectById(info.getId());
        if (null!=stock){
            if (info.getItemAttr()!=null){
                stock.setItemAttr(info.getItemAttr());
            }
            if (info.getItemId()!=null){
                stock.setItemId(info.getItemId());
            }
            if (info.getLenWidHeight()!=null){
                stock.setLenWidHeight(info.getLenWidHeight());
            }
            if (info.getRepositoryId()!=null){
                stock.setRepositoryId(info.getRepositoryId());
            }
            if (info.getPreSalePrice()!=null){
                stock.setPreSalePrice(info.getPreSalePrice().setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            if (info.getSalePrice()!=null){
                stock.setSalePrice(info.getSalePrice().setScale(2, BigDecimal.ROUND_HALF_EVEN));
            }
            if (info.getScoreScale()!=null){
                stock.setScoreScale(info.getScoreScale());
            }
            if (new Double(info.getWeight())!=null){
                stock.setWeight(info.getWeight());
            }
            if (info.getSkuSaltVolume()!=null){
                stock.setSkuSaltVolume(info.getSkuSaltVolume());
            }
            if (info.getUsableStock()!=null){
                stock.setUsableStock(info.getUsableStock());
            }
            if (info.getStatus()!=null){
                stock.setStatus(info.getStatus());
            }
            stock.setUpdateTime(new Date());
            int result = stockMapper.updateById(stock);
            if (result==1){
                return info;
            }else {
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901003.getCode(),errorMessage);
        }
    }

    @Override
    public void deleteStock(Integer stockId) {
        Stock stock = stockMapper.selectById(stockId);
        if (null!=stock){
            int result = stockMapper.deleteById(stockId);
            if (result==0){
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901003.getCode(),errorMessage);
        }
    }

    @Override
    public List<Stock> queryStock(ItemsStockVO info) {
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
        //查询状态为启用的一级分类
        QueryWrapper<Stock> queryWrapper = new QueryWrapper();
        if (info.getId()!=null){
            queryWrapper.eq("id", info.getId());
        }
        if (info.getItemId()!=null){
            queryWrapper.eq("item_id", info.getItemId());
        }
        if (info.getStatus()!=null){
            queryWrapper.eq("status", info.getStatus());
        }else {
            queryWrapper.eq("status", StatusEnum.SHELVES.getValue());
        }
        Page<Stock> page = new Page<>(current, size);
        IPage<Stock> stocks = stockMapper.selectPage(page, queryWrapper);
        if (stocks.getTotal()>0){
            return stocks.getRecords();
        }
        return new ArrayList<>();
    }


//根据商品id查询库存信息 和 商品信息
    @Override
    public Map<String,Object> selecStockBySkuId(Integer id) {
        Map<String,Object> map = new HashMap<>();
        if(id==null){
            throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
        }
        Stock cStock = stockMapper.selectById(id);
        ItemsInfo info = infoMapper.selectById(cStock.getItemId());
        map.put("SPU",info);
        map.put("SKU",cStock);
        return map;
    }


      //实现锁库存方法
    @Override
    public String checkAndLockStock(List<SkuLockVO> skuLockVOS) {

        // 遍历
        skuLockVOS.forEach(skuLockVO -> {
            lockSku(skuLockVO);
        });
        //查看有没有失败的记录
        //有失败的记录，回滚成功的记录

        List<SkuLockVO> error = skuLockVOS.stream().filter(skuLockVO -> !skuLockVO.getLock()).collect(Collectors.toList());

        List<SkuLockVO> success = skuLockVOS.stream().filter(skuLockVO -> skuLockVO.getLock()).collect(Collectors.toList());



        if(!CollectionUtils.isEmpty(error)){
            success.forEach(skuLockVO -> {
                stockMapper.unlockStore(skuLockVO.getSkuId(),skuLockVO.getNum());
            });
            //返回码暂未定义
            //返回获取锁定失败的商品的id     或者返回商品的名称
            String s = error.stream().map(skuLockVO -> skuLockVO.getSkuId()).collect(Collectors.toList()).toString();
            System.out.println(s);

            return "锁定库存失败: "+error.stream().map(skuLockVO -> skuLockVO.getSkuId()).collect(Collectors.toList()).toString();
        }

        //锁库存成功后执行该代码
        //保存锁定库存的信息到redis中
        String orderToken = skuLockVOS.get(0).getOrderToken();
        this.redisTemplate.opsForValue().set("order:stock:"+orderToken, JSON.toJSONString(skuLockVOS));

        //使用消息队列发送延时消息，固定时间内（）分钟解锁库存
        //暂未实现

        return null;//暂未定义
    }

    //为了保证验库存和锁库存的原子性，这里直接把验证和锁定库存封装到一个方法中，并在方法中使用分布式锁，防止多人同时锁库存。
    private void lockSku(SkuLockVO skuLockVO) {

        RLock lock = this.redissonClient.getLock("sku:lock:" + skuLockVO.getSkuId());
        lock.lock();

        // 验库存   返回查询到的数据
        List<Stock> stocks = this.stockMapper.checkStore(skuLockVO.getSkuId(), skuLockVO.getNum());

        //否则锁库存失败
        skuLockVO.setLock(false);

        if (!CollectionUtils.isEmpty(stocks)){
            //锁库存   可用库存数-购买数量
            if (this.stockMapper.lockStore(skuLockVO.getSkuId(), skuLockVO.getNum())==1){
                skuLockVO.setLock(true);
                skuLockVO.setSkuId(stocks.get(0).getItemId());   //注释 获取库存位置  商品id

            }
        }
        lock.unlock();
 }

    @Override
    public void unLockStock(String orderToken) {
        String stokcJson = this.redisTemplate.opsForValue().get("order:stock:" + orderToken);
        if (StringUtils.isEmpty(stokcJson)){
            return;
        }
        //反序列化
        List<SkuLockVO> skuLockVOS = JSON.parseArray(stokcJson, SkuLockVO.class);
        //遍历解锁库存
        skuLockVOS.forEach(skuLockVO -> {
            stockMapper.unlockStore(skuLockVO.getSkuId(),skuLockVO.getNum());
        });
        this.redisTemplate.delete("order:stock:" + orderToken);
    }

}



























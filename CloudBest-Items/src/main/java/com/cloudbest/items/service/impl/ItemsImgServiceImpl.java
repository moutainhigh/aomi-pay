package com.cloudbest.items.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.util.AliOSSUtil;
import com.cloudbest.items.entity.Catagory;
import com.cloudbest.items.entity.ItemsImg;
import com.cloudbest.items.entity.ItemsInfo;
import com.cloudbest.items.entity.Stock;
import com.cloudbest.items.enums.StatusEnum;
import com.cloudbest.items.mapper.ItemsImgMapper;
import com.cloudbest.items.mapper.StockMapper;
import com.cloudbest.items.service.ItemsImgService;
import com.cloudbest.items.service.ItemsInfoService;
import com.cloudbest.items.vo.ItemsImgVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商品图片表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@Service
@Transactional
public class ItemsImgServiceImpl implements ItemsImgService {

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private ItemsInfoService cItemsInfoService;

    String errorMessage = "网络小憩~请稍后再试！";

    //根据id查询图片信息
    @Override
    public ItemsImg selecImgBySkuId(Integer id) {
        if(id==null){
            throw new BusinessException(CommonErrorCode.FAIL);
        }
        ItemsImg cItemsImgs = new ItemsImg();
        cItemsImgs = itemsImgMapper.selectOne(new LambdaQueryWrapper<ItemsImg>().eq(ItemsImg::getSkuId, id).eq(ItemsImg::getStatus,StatusEnum.SHELVES.getValue()));
        if (cItemsImgs==null){
            Stock stock = stockMapper.selectById(id);
            cItemsImgs = itemsImgMapper.selectOne(new LambdaQueryWrapper<ItemsImg>().eq(ItemsImg::getItemId, stock.getItemId()).eq(ItemsImg::getSort,0).eq(ItemsImg::getStatus,StatusEnum.SHELVES.getValue()));
        }
        return  cItemsImgs;
    }

    @Override
    public List<ItemsImg> createNewItemsImg(ItemsImg itemsImg) {
        List<ItemsImg> imgList = new ArrayList<>();
        List<String> imgUrls = Arrays.asList(itemsImg.getImgUrl().split(","));
        if (imgUrls.size()==1){
            ItemsImg img = new ItemsImg();
            img.setImgDesc(itemsImg.getImgDesc());
            img.setImgUrl(itemsImg.getImgUrl());
            img.setItemId(itemsImg.getItemId());
            img.setSkuId(itemsImg.getSkuId());
            img.setSort(itemsImg.getSort()==null?0:itemsImg.getSort());
            img.setStatus(itemsImg.getStatus());
            img.setUpdateTime(new Date());
            itemsImgMapper.insert(img);
            imgList.add(img);
        }else {
            for (int i=0;i<imgUrls.size();i++){
                ItemsImg img = new ItemsImg();
                img.setImgDesc(itemsImg.getImgDesc());
                img.setImgUrl(imgUrls.get(i));
                img.setItemId(itemsImg.getItemId());
                img.setSkuId(itemsImg.getSkuId());
                img.setSort(i);
                img.setStatus(itemsImg.getStatus());
                img.setUpdateTime(new Date());
                itemsImgMapper.insert(img);
                imgList.add(img);
            }
        }

        return imgList;
    }

    @Override
    public ItemsImg queryById(Integer id) {
        ItemsImg img = itemsImgMapper.selectById(id);
        return img;
    }

    @Override
    public void deleteItemsImg(Integer itemsImgId) {
        ItemsImg img = itemsImgMapper.selectById(itemsImgId);
        int result = 0 ;
        if (null!=img){
            String ossObjectNamePrefix = AliOSSUtil.APP_SYS_IMAGETEXT + "-";
            String imgurl = img.getImgUrl();
            if (imgurl !=null && !"".equals(imgurl) && imgurl.contains(ossObjectNamePrefix)) {
                AliOSSUtil aliOSSUtil = new AliOSSUtil();
                String objectName = imgurl.substring(imgurl.indexOf(ossObjectNamePrefix), imgurl.indexOf("?"));
                aliOSSUtil.deleteFileFromOss(objectName);
                result = itemsImgMapper.deleteById(itemsImgId);
            }
            if (result==0){
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901007.getCode(),errorMessage);
        }
    }

    @Override
    public ItemsImg updateItemsImg(ItemsImg info) {
        ItemsImg img = itemsImgMapper.selectById(info.getId());
        if (null!=img){
            if (info.getImgDesc()!=null){
                img.setImgDesc(info.getImgDesc());
            }
            if (info.getImgUrl()!=null){
                img.setImgUrl(info.getImgUrl().substring(0, info.getImgUrl().length()-1));
            }
            if (info.getItemId()!=null){
                img.setItemId(info.getItemId());
            }
            if (info.getSkuId()!=null){
                img.setSkuId(info.getSkuId());
            }
            if (info.getSort()!=null){
                img.setSort(info.getSort()==null?0:info.getSort());
            }
            if (info.getStatus()!=null){
                img.setStatus(info.getStatus());
            }
            img.setUpdateTime(new Date());
            int result = itemsImgMapper.updateById(img);
            if (result==1){
                return info;
            }else {
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901009.getCode(),errorMessage);
        }
    }

    @Override
    public ItemsImg offItemsImg(Integer itemsImgId) {
        ItemsImg img = itemsImgMapper.selectById(itemsImgId);
        if (null!=img){
            img.setUpdateTime(new Date());
            img.setStatus(9);
            int result = itemsImgMapper.updateById(img);
            if (result==1){
                return img;
            }else {
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901009.getCode(),errorMessage);
        }
    }

    @Override
    public List<ItemsImg> queryItemsImg(ItemsImgVO info) {
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
        QueryWrapper<ItemsImg> queryWrapper = new QueryWrapper();
        if (info.getId()!=null){
            queryWrapper.eq("id", info.getId());
        }
        if (info.getSkuId()!=null){
            queryWrapper.eq("sku_id", info.getSkuId());
        }
        if (info.getItemId()!=null){
            queryWrapper.eq("item_id", info.getItemId());
        }
        if (info.getImgDesc()!=null){
            queryWrapper.eq("img_desc", info.getImgDesc());
        }
        if (info.getStatus()!=null){
            queryWrapper.eq("status", info.getStatus());
        }else {
            queryWrapper.eq("status", StatusEnum.SHELVES.getValue());
        }
        Page<ItemsImg> page = new Page<>(current, size);
        IPage<ItemsImg> itemsImgList = itemsImgMapper.selectPage(page, queryWrapper);
        if (itemsImgList.getTotal()>0){
            return itemsImgList.getRecords();
        }
        return new ArrayList<>();
    }

    @Override
    public ItemsImg insertItemsImg(HttpServletRequest request, String desc, Integer itemId, Integer skuId) {
        ItemsImg img = new ItemsImg();
        //查询对应的商品是否存在
        ItemsInfo info = new ItemsInfo();
        try {
            info = cItemsInfoService.getItemById(itemId);
        } catch (BusinessException businessException){
            throw new BusinessException(CommonErrorCode.E_901009.getCode(),errorMessage);
        }

        try {
            desc= URLDecoder.decode(desc, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multipartRequest.getFiles("image");
        if(files==null||files.size()==0){
            throw new BusinessException(CommonErrorCode.E_901007.getCode(),errorMessage);
        }

        String ossObjectNamePrefix = AliOSSUtil.APP_SYS_IMAGETEXT+ "-";
        String ossObjectName = "";

        int i=1;
        String imgurls="";
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
                imgurls = imgurls + fileUrl.substring(0,fileUrl.lastIndexOf("?")) + ",";
                i++;
            }
        }
        img.setImgDesc(desc);
        img.setImgUrl(imgurls.substring(0, imgurls.length()-1));
        img.setItemId(itemId);
        img.setSkuId(skuId);
        img.setSort(0);
        img.setStatus(1);
        img.setUpdateTime(new Date());
        itemsImgMapper.insert(img);
        return img;
    }




}

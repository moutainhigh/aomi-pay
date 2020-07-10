package com.cloudbest.items.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.items.entity.Catagory;
import com.cloudbest.items.entity.ItemsInfo;
import com.cloudbest.items.enums.StatusEnum;
import com.cloudbest.items.mapper.CatagoryMapper;
import com.cloudbest.items.service.CatagoryService;
import com.cloudbest.items.vo.CatagoryVO;
import com.cloudbest.items.vo.ItemsStockVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * <p>
 * 商品分类表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@Service
@Transactional
public class CatagoryServiceImpl implements CatagoryService {

    @Autowired
    private CatagoryMapper catagoryMapper;

    String errorMessage = "网络小憩~请稍后再试！";

    @Override
    public Catagory createNewCatagory(Catagory info) {
        Catagory catagory = new Catagory();
        if (info.getCatagoryName()==null){
            throw new BusinessException(CommonErrorCode.E_300009.getCode(),CommonErrorCode.E_300009.getDesc());
        }
        catagory.setCatagoryName(info.getCatagoryName());
        catagory.setParentCatagoryId(info.getParentCatagoryId());
        catagory.setImg(info.getImg());
        catagory.setStatus(1);
        catagory.setUpdateTime(new Date());
        int result = catagoryMapper.insert(catagory);
        if (result==1){
            return catagory;
        }else {
            throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
        }
    }

    @Override
    public Catagory updateCatagory(Catagory info) {
        Catagory catagory = catagoryMapper.selectById(info.getId());
        if (null!=catagory){
            if (info.getCatagoryName()!=null){
                catagory.setCatagoryName(info.getCatagoryName());
            }
            if (info.getParentCatagoryId()!=null){
                catagory.setParentCatagoryId(info.getParentCatagoryId());
            }
            if (info.getImg()!=null){
                catagory.setImg(info.getImg());
            }
            if (info.getStatus()!=null){
                catagory.setStatus(info.getStatus());
            }
            catagory.setUpdateTime(new Date());
            int result = catagoryMapper.updateById(catagory);
            if (result==1){
                return info;
            }else {
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901002.getCode(),errorMessage);
        }
    }

    @Override
    public void deleteCatagory(Integer catagoryId) {
        Catagory catagory = catagoryMapper.selectById(catagoryId);
        if (null!=catagory){
            int result = catagoryMapper.deleteById(catagoryId);
            if (result==0){
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901002.getCode(),errorMessage);
        }
    }

    @Override
    public Catagory offCatagory(Integer catagoryId) {
        Catagory catagory = catagoryMapper.selectById(catagoryId);
        if (null!=catagory){
            catagory.setUpdateTime(new Date());
            catagory.setStatus(9);
            int result = catagoryMapper.updateById(catagory);
            if (result==1){
                return catagory;
            }else {
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901002.getCode(),errorMessage);
        }
    }

    @Override
    public List<CatagoryVO> queryCatagory(Integer parentCatagoryId) {
        Map<String,Object> map = new HashMap<>();
        //查询状态为启用的一级分类
        QueryWrapper<Catagory> queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_catagory_id", parentCatagoryId);
        queryWrapper.eq("status", StatusEnum.SHELVES.getValue());
        List<Catagory> catagories = catagoryMapper.selectList(queryWrapper);
        List<CatagoryVO> catagoryVOS = new ArrayList<>();
        for (int i=0;i<catagories.size();i++) {
            CatagoryVO catagoryVO = new CatagoryVO();
            BeanUtils.copyProperties(catagories.get(i), catagoryVO);
            catagoryVOS.add(catagoryVO);
        }
        if (catagoryVOS.size()>0){
            if (parentCatagoryId==0){
                for (CatagoryVO catagory:catagoryVOS){
                    QueryWrapper<Catagory> querySonWrapper = new QueryWrapper();
                    querySonWrapper.eq("parent_catagory_id", catagory.getId());
                    querySonWrapper.eq("status", StatusEnum.SHELVES.getValue());
                    List<Catagory> sonCatagories = catagoryMapper.selectList(querySonWrapper);
                    catagory.setCatagoryList(sonCatagories);
                }
            }
            return catagoryVOS;
        }
        return new ArrayList<>();
    }

    @Override
    public Catagory queryById(Integer id) {
        Catagory catagory = catagoryMapper.selectById(id);
        return catagory;
    }

    @Override
    public List<Catagory> queryCatagoryList(CatagoryVO info) {
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

        Map<String,Object> map = new HashMap<>();
        //查询状态为启用的一级分类
        QueryWrapper<Catagory> queryWrapper = new QueryWrapper();
        if (info.getCatagoryName()!=null){
            queryWrapper.like("catagory_name", info.getCatagoryName());
        }
        if (info.getId()!=null){
            queryWrapper.eq("id", info.getId());
        }
        if (info.getParentCatagoryId()!=null){
            queryWrapper.eq("parent_catagory_id", info.getParentCatagoryId());
        }
        if (info.getStatus()!=null){
            queryWrapper.eq("status", info.getStatus());
        }else {
            queryWrapper.eq("status", StatusEnum.SHELVES.getValue());
        }
        Page<Catagory> page = new Page<>(current, size);
        IPage<Catagory> catagories = catagoryMapper.selectPage(page, queryWrapper);

        if (catagories.getTotal()>0){
            return catagories.getRecords();
        }
        return new ArrayList<>();
    }

}

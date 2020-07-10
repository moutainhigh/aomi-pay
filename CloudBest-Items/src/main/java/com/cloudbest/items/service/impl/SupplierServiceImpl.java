package com.cloudbest.items.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.items.entity.ItemsImg;
import com.cloudbest.items.entity.Supplier;
import com.cloudbest.items.enums.StatusEnum;
import com.cloudbest.items.mapper.SupplierMapper;
import com.cloudbest.items.service.SupplierService;
import com.cloudbest.items.vo.SupplierVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 供应商表 服务实现类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@Slf4j
@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;
    String errorMessage = "网络小憩~请稍后再试！";
    @Override
    public Supplier createNewSupplier(Supplier info) {
        Supplier supplier = new Supplier();
        supplier.setAddress(info.getAddress());
        supplier.setBankCardNo(info.getBankCardNo());
        supplier.setBankRedisterName(info.getBankRedisterName());
        supplier.setSupplierAdmin(info.getSupplierAdmin());
        supplier.setSupplierName(info.getSupplierName());
        supplier.setSupplierShortName(info.getSupplierShortName());
        supplier.setSupplierPhone(info.getSupplierPhone());
        supplier.setType(1);
        supplier.setStatus(1);
        supplier.setUpdateTime(new Date());
        int result = supplierMapper.insert(supplier);
        if (result==1){
            return supplier;
        }else {
            throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
        }
    }

    @Override
    public Supplier updateSupplier(Supplier info) {
        Supplier supplier = supplierMapper.selectById(info.getId());
        if (null!=supplier){
            if (info.getAddress()!=null){
                supplier.setAddress(info.getAddress());
            }
            if (info.getBankCardNo()!=null){
                supplier.setBankCardNo(info.getBankCardNo());
            }
            if (info.getBankRedisterName()!=null){
                supplier.setBankRedisterName(info.getBankRedisterName());
            }
            if (info.getSupplierAdmin()!=null){
                supplier.setSupplierAdmin(info.getSupplierAdmin());
            }
            if (info.getSupplierName()!=null){
                supplier.setSupplierName(info.getSupplierName());
            }
            if (info.getSupplierShortName()!=null){
                supplier.setSupplierShortName(info.getSupplierShortName());
            }
            if (info.getSupplierPhone()!=null){
                supplier.setSupplierPhone(info.getSupplierPhone());
            }
            if (info.getType()!=null){
                supplier.setType(info.getType());
            }
            if (info.getAddress()!=null){
                supplier.setStatus(info.getStatus());
            }
            supplier.setUpdateTime(new Date());
            int result = supplierMapper.updateById(supplier);
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
    public void deleteSupplier(Integer supplierId) {
        Supplier supplier = supplierMapper.selectById(supplierId);
        if (null!=supplier){
            int result = supplierMapper.deleteById(supplierId);
            if (result==0){
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901003.getCode(),errorMessage);
        }
    }

    @Override
    public Supplier offSupplier(Integer supplierId) {
        Supplier supplier = supplierMapper.selectById(supplierId);
        if (null!=supplier){
            supplier.setUpdateTime(new Date());
            supplier.setStatus(9);
            int result = supplierMapper.updateById(supplier);
            if (result==1){
                return supplier;
            }else {
                throw new BusinessException(CommonErrorCode.FAIL.getCode(),errorMessage);
            }
        }else {
            throw new BusinessException(CommonErrorCode.E_901003.getCode(),errorMessage);
        }
    }

    @Override
    public List<Supplier> querySupplier(SupplierVO info) {
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
        //查询状态为启用的供应商列表
        QueryWrapper<Supplier> queryWrapper = new QueryWrapper();
        if (info.getId()!=null){
            queryWrapper.eq("id", info.getId());
        }
        if (info.getSupplierName()!=null){
            queryWrapper.eq("supplier_name", info.getSupplierName());
        }
        if (info.getStatus()!=null){
            queryWrapper.eq("status", info.getStatus());
        }else {
            queryWrapper.eq("status" , StatusEnum.SHELVES.getValue());
        }
        Page<Supplier> page = new Page<>(current, size);
        IPage<Supplier> repositories = supplierMapper.selectPage(page, queryWrapper);
        if (repositories.getTotal()>0){
            return repositories.getRecords();
        }
        return new ArrayList<>();
    }

    @Override
    public Supplier queryById(Integer id) {
        Supplier supplier = supplierMapper.selectById(id);
        return supplier;
    }
}

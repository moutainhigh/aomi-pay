package com.cloudbest.items.controller;


import com.cloudbest.common.domain.BusinessException;
import com.cloudbest.common.domain.CommonErrorCode;
import com.cloudbest.common.domain.Result;
import com.cloudbest.items.entity.Supplier;
import com.cloudbest.items.service.SupplierService;
import com.cloudbest.items.vo.SupplierVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 供应商表 前端控制器
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
@CrossOrigin
@RestController
public class SupplierController {

    @Autowired
    private SupplierService cSupplierService;

    /**
     * 添加供应商信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/supplier/add/insertSupplier")
    public Result insertSupplier(HttpServletRequest request, @RequestBody Supplier info){
        Supplier supplier = new Supplier();

        try{
            supplier = cSupplierService.createNewSupplier(info);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,supplier);
    }

    /**
     * 修改供应商信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/supplier/update/updateSupplier")
    public Result updateSupplier(HttpServletRequest request,@RequestBody Supplier info){
        Supplier supplier = new Supplier();
        try{
            supplier = cSupplierService.updateSupplier(info);;
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,supplier);
    }

    /**
     * 删除供应商信息（物理删除）
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/supplier/delete/deleteSupplier")
    public Result deleteSupplier(HttpServletRequest request,@RequestParam(value = "supplierId", required = true) Integer supplierId){

        try{
            cSupplierService.deleteSupplier(supplierId);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 删除仓库信息（逻辑删除）
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/supplier/delete/offSupplier")
    public Result offSupplier(HttpServletRequest request,@RequestBody SupplierVO info){

        try{
            cSupplierService.offSupplier(info.getId());
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS);
    }

    /**
     * 查询供应商信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/supplier/query/querySupplier")
    public Result querySupplier(HttpServletRequest request,@RequestBody SupplierVO info){
        List<Supplier> supplierList = new ArrayList<>();
        try{
            supplierList = cSupplierService.querySupplier(info);
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,supplierList);
    }


    /**
     * 查询供应商信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/items/supplier/query/queryById")
    public Result queryById(HttpServletRequest request,@RequestBody SupplierVO info){
        Supplier supplier = new Supplier();
        try{
            supplier = cSupplierService.queryById(info.getId());
        }catch (BusinessException businessException){
            return new Result(businessException.getCode(),businessException.getDesc(),false);
        }
        return new Result(CommonErrorCode.SUCCESS,supplier);
    }
}

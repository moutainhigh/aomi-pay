package com.cloudbest.items.service;

import com.cloudbest.items.entity.Supplier;
import com.cloudbest.items.vo.SupplierVO;

import java.util.List;

/**
 * <p>
 * 供应商表 服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
public interface SupplierService {

    Supplier createNewSupplier(Supplier info);

    Supplier updateSupplier(Supplier info);

    void deleteSupplier(Integer supplierId);

    Supplier offSupplier(Integer supplierId);

    List<Supplier> querySupplier(SupplierVO info);

    Supplier queryById(Integer id);
}

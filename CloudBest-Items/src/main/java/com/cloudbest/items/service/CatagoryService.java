package com.cloudbest.items.service;

import com.cloudbest.items.entity.Catagory;
import com.cloudbest.items.vo.CatagoryVO;

import java.util.List;

/**
 * <p>
 * 商品分类表 服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
public interface CatagoryService {

    Catagory createNewCatagory(Catagory info);

    Catagory updateCatagory(Catagory info);

    void deleteCatagory(Integer catagoryId);

    List<CatagoryVO> queryCatagory(Integer parentCatagoryId);

    Catagory offCatagory(Integer catagoryId);

    Catagory queryById(Integer id);

    List<Catagory> queryCatagoryList(CatagoryVO info);
}

package com.cloudbest.items.service;

import com.cloudbest.items.entity.ItemsImg;
import com.cloudbest.items.vo.ItemsImgVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 商品图片表 服务类
 * </p>
 *
 * @author author
 * @since 2020-06-16
 */
public interface ItemsImgService {

    void deleteItemsImg(Integer itemsImgId);

    ItemsImg updateItemsImg(ItemsImg info);

    ItemsImg offItemsImg(Integer itemsImgId);

    List<ItemsImg> queryItemsImg(ItemsImgVO info);

    ItemsImg insertItemsImg(HttpServletRequest request, String desc, Integer itemId, Integer skuId);

    ItemsImg selecImgBySkuId(Integer id);

    List<ItemsImg> createNewItemsImg(ItemsImg itemsImg);

    ItemsImg queryById(Integer valueOf);
}

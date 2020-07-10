package com.cloudbest.items.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.cloudbest.items.entity.ItemsImg;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ItemsInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer spuId;
    private String name;
    private String imgDefault;

    /**
     * 一级分类ID
     */
    private Integer firstCategoryId;

    /**
     * 二级分类ID
     */
    private Integer secondCategoryId;

    /**
     * 三级分类ID
     */
    private Integer thirdCategoryId;

    private List<ItemsImg> imgList;

    private Integer isView;

    private List<ItemsStockVO> stockVOList;

    private String description;

    private BigDecimal preSalePrice;
    private BigDecimal salePrice;
    private BigDecimal score;
    private BigDecimal scorePrice;
    private BigDecimal transportAmount;
    /**
     * 商品详情
     */
    private List<String> itemInfo;

    /**
     * 当前页码
     */
    private Integer current;

    /**
     * 每页数据量
     */
    private Integer size;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsView() {
        return isView;
    }

    public void setIsView(Integer isView) {
        this.isView = isView;
    }

    public Integer getSpuId() {
        return spuId;
    }

    public List<String> getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(List<String> itemInfo) {
        this.itemInfo = itemInfo;
    }

    public void setSpuId(Integer spuId) {
        this.spuId = spuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFirstCategoryId() {
        return firstCategoryId;
    }

    public void setFirstCategoryId(Integer firstCategoryId) {
        this.firstCategoryId = firstCategoryId;
    }

    public Integer getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(Integer secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }

    public Integer getThirdCategoryId() {
        return thirdCategoryId;
    }

    public void setThirdCategoryId(Integer thirdCategoryId) {
        this.thirdCategoryId = thirdCategoryId;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<ItemsImg> getImgList() {
        return imgList;
    }

    public void setImgList(List<ItemsImg> imgList) {
        this.imgList = imgList;
    }

    public List<ItemsStockVO> getStockVOList() {
        return stockVOList;
    }

    public void setStockVOList(List<ItemsStockVO> stockVOList) {
        this.stockVOList = stockVOList;
    }

    public BigDecimal getPreSalePrice() {
        return preSalePrice;
    }

    public void setPreSalePrice(BigDecimal preSalePrice) {
        this.preSalePrice = preSalePrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getScorePrice() {
        return scorePrice;
    }

    public void setScorePrice(BigDecimal scorePrice) {
        this.scorePrice = scorePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgDefault() {
        return imgDefault;
    }

    public BigDecimal getTransportAmount() {
        return transportAmount;
    }

    public void setTransportAmount(BigDecimal transportAmount) {
        this.transportAmount = transportAmount;
    }

    public void setImgDefault(String imgDefault) {
        this.imgDefault = imgDefault;
    }

    @Override
    public String toString() {
        return "ItemsInfoVO{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", name='" + name + '\'' +
                ", imgDefault='" + imgDefault + '\'' +
                ", firstCategoryId=" + firstCategoryId +
                ", secondCategoryId=" + secondCategoryId +
                ", thirdCategoryId=" + thirdCategoryId +
                ", imgList=" + imgList +
                ", isView=" + isView +
                ", stockVOList=" + stockVOList +
                ", description='" + description + '\'' +
                ", preSalePrice=" + preSalePrice +
                ", salePrice=" + salePrice +
                ", score=" + score +
                ", scorePrice=" + scorePrice +
                ", transportAmount=" + transportAmount +
                ", itemInfo='" + itemInfo + '\'' +
                ", current=" + current +
                ", size=" + size +
                '}';
    }
}

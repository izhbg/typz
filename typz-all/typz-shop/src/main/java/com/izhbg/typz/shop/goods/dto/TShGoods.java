package com.izhbg.typz.shop.goods.dto;

import java.util.Date;

import javax.persistence.Column;
/**
 * 
* @ClassName: TShGoods 
* @Description: 商品DTO
* @author caixl 
* @date 2016-7-5 上午11:55:46 
*
 */
public class TShGoods {

	private String basicId;
	private String storeId;
	private String name;
	private Integer basicVersion;
	private String aliasName;
	private String brandId;
	private String specificationsId;
	private String unit;
	private String modelId;
	private String typeId;
	private String vender;
	private Date createTime;
	private String createUser;
	private Date updateTime;
	private Date updateUser;
	private String other;
	private String detailId;
	private Integer version;
	private String goodsId;
	private String content;
	private Integer saleStatus;
	private Integer stockStatus;
	private Date toProTime;
	private Date onSaleTime;
	private Date unSaleTime;
	private Date delTime;
	private Integer proStatus;
	private String unproReason;
	private Date proTime;
	private Double defaultPrice;
	private Integer saleCount;
	private Integer viewCount;
	
	private Integer delStatus;//删除状态
	private Integer status;//上下架状态
	private String random;
	
	private String picIds;
	public String getBasicId() {
		return basicId;
	}
	public String getName() {
		return name;
	}
	public Integer getBasicVersion() {
		return basicVersion;
	}
	public String getAliasName() {
		return aliasName;
	}
	public String getBrandId() {
		return brandId;
	}
	public String getSpecificationsId() {
		return specificationsId;
	}
	public String getUnit() {
		return unit;
	}
	public String getModelId() {
		return modelId;
	}
	public String getTypeId() {
		return typeId;
	}
	public String getVender() {
		return vender;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public Date getUpdateUser() {
		return updateUser;
	}
	public String getOther() {
		return other;
	}
	public Integer getVersion() {
		return version;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public String getContent() {
		return content;
	}
	public Integer getSaleStatus() {
		return saleStatus;
	}
	public Integer getStockStatus() {
		return stockStatus;
	}
	public Date getToProTime() {
		return toProTime;
	}
	public Date getOnSaleTime() {
		return onSaleTime;
	}
	public Date getUnSaleTime() {
		return unSaleTime;
	}
	public Date getDelTime() {
		return delTime;
	}
	public Integer getProStatus() {
		return proStatus;
	}
	public String getUnproReason() {
		return unproReason;
	}
	public Date getProTime() {
		return proTime;
	}
	public Double getDefaultPrice() {
		return defaultPrice;
	}
	public Integer getSaleCount() {
		return saleCount;
	}
	public Integer getViewCount() {
		return viewCount;
	}
	public void setBasicId(String basicId) {
		this.basicId = basicId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setBasicVersion(Integer basicVersion) {
		this.basicVersion = basicVersion;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public void setSpecificationsId(String specificationsId) {
		this.specificationsId = specificationsId;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public void setVender(String vender) {
		this.vender = vender;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setUpdateUser(Date updateUser) {
		this.updateUser = updateUser;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setSaleStatus(Integer saleStatus) {
		this.saleStatus = saleStatus;
	}
	public void setStockStatus(Integer stockStatus) {
		this.stockStatus = stockStatus;
	}
	public void setToProTime(Date toProTime) {
		this.toProTime = toProTime;
	}
	public void setOnSaleTime(Date onSaleTime) {
		this.onSaleTime = onSaleTime;
	}
	public void setUnSaleTime(Date unSaleTime) {
		this.unSaleTime = unSaleTime;
	}
	public void setDelTime(Date delTime) {
		this.delTime = delTime;
	}
	public void setProStatus(Integer proStatus) {
		this.proStatus = proStatus;
	}
	public void setUnproReason(String unproReason) {
		this.unproReason = unproReason;
	}
	public void setProTime(Date proTime) {
		this.proTime = proTime;
	}
	public void setDefaultPrice(Double defaultPrice) {
		this.defaultPrice = defaultPrice;
	}
	public void setSaleCount(Integer saleCount) {
		this.saleCount = saleCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	public String getPicIds() {
		return picIds;
	}
	public void setPicIds(String picIds) {
		this.picIds = picIds;
	}
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public Integer getDelStatus() {
		return delStatus;
	}
	public Integer getStatus() {
		return status;
	}
	public void setDelStatus(Integer delStatus) {
		this.delStatus = delStatus;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getRandom() {
		return random;
	}
	public void setRandom(String random) {
		this.random = random;
	}
	
	
}

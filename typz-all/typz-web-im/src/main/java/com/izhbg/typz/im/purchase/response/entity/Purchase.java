package com.izhbg.typz.im.purchase.response.entity;

import java.util.List;

public class Purchase {
	private List<PurchaseStore> purchaseStoreList;
	private Integer totalShopGoodsNum;
	private Double totalPrice;
	public List<PurchaseStore> getPurchaseStoreList() {
		return purchaseStoreList;
	}
	public void setPurchaseStoreList(List<PurchaseStore> purchaseStoreList) {
		this.purchaseStoreList = purchaseStoreList;
	}
	public Integer getTotalShopGoodsNum() {
		return totalShopGoodsNum;
	}
	public void setTotalShopGoodsNum(Integer totalShopGoodsNum) {
		this.totalShopGoodsNum = totalShopGoodsNum;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}

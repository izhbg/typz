package com.izhbg.typz.shop.purchase.dto;

import java.util.List;

import com.izhbg.typz.shop.store.dto.TShStore;

public class PurchaseStore {
	
	private TShStore store;
	private List<PurchaseGoods> goodsList;
	private Integer totalGoodsNum;
	public TShStore getStore() {
		return store;
	}
	public void setStore(TShStore store) {
		this.store = store;
	}
	public List<PurchaseGoods> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<PurchaseGoods> goodsList) {
		this.goodsList = goodsList;
	}
	public Integer getTotalGoodsNum() {
		return totalGoodsNum;
	}
	public void setTotalGoodsNum(Integer totalGoodsNum) {
		this.totalGoodsNum = totalGoodsNum;
	}
	
	
}

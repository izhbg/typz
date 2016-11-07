package com.izhbg.typz.shop.purchase.dto;

import com.izhbg.typz.shop.goods.dto.TShGoodsBasic;

public class PurchaseGoods {
	private TShGoodsBasic goods;
	private Integer num;
	private String purchaseId;
	public TShGoodsBasic getGoods() {
		return goods;
	}
	public void setGoods(TShGoodsBasic goods) {
		this.goods = goods;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
	
	
}

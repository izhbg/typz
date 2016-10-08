package com.izhbg.typz.shop.store.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="t_sh_store_goods")
@NamedQuery(name="TShStoreGoods.findAll", query="SELECT t FROM TShStoreGoods t")
public class TShStoreGoods {
	
	@Id
	private String id;
	@Column(name="goods_id")
	private String goodsId;
	@Column(name="store_id")
	private String storeId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	
}

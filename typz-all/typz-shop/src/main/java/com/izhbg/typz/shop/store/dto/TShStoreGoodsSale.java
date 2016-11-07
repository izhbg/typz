package com.izhbg.typz.shop.store.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.izhbg.typz.shop.goods.dto.TShGoodsBasic;

@Entity
@Table(name="t_sh_store_goods_sale")
@NamedQuery(name="TShStoreGoodsSale.findAll", query="SELECT t FROM TShStoreGoodsSale t")
public class TShStoreGoodsSale {
	
	@Id
	private String id;
	@Column(name="goods_id")
	private String goodsId;
	@Column(name="store_id")
	private String storeId;
	@Transient
	private TShGoodsBasic tShGoodsBasic;
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
	public TShGoodsBasic gettShGoodsBasic() {
		return tShGoodsBasic;
	}
	public void settShGoodsBasic(TShGoodsBasic tShGoodsBasic) {
		this.tShGoodsBasic = tShGoodsBasic;
	}
	
	
}

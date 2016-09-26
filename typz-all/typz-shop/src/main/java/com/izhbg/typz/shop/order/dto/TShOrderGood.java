package com.izhbg.typz.shop.order.dto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_sh_order_goods database table.
 * 
 */
@Entity
@Table(name="t_sh_order_goods")
@NamedQuery(name="TShOrderGood.findAll", query="SELECT t FROM TShOrderGood t")
public class TShOrderGood implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="goods_id")
	private String goodsId;

	private int num;

	@Column(name="order_id")
	private String orderId;

	private double price;

	@Column(name="store_id")
	private String storeId;

	public TShOrderGood() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public int getNum() {
		return this.num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	

}
package com.izhbg.typz.shop.order.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_sh_order database table.
 * 
 */
@Entity
@Table(name="t_sh_order")
@NamedQuery(name="TShOrder.findAll", query="SELECT t FROM TShOrder t")
public class TShOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="sc_bj")
	private byte scBj;

	@Column(name="store_id")
	private String storeId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	@Column(name="total_count")
	private int totalCount;

	@Column(name="total_price")
	private double totalPrice;

	private double yf;
	@Column(name="yh_id")
	private String yhId;
	@Transient
	private String goodsInfo;
	@Transient
	private String addressId;
	@Transient
	private String addressSelf;
	@Transient
	private Integer isDelivery;
	@Transient
	private Integer isSelf;
	@Transient
	private Integer isSend;
	@Transient
	private String tsyq;
	
	public TShOrder() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte getScBj() {
		return this.scBj;
	}

	public void setScBj(byte scBj) {
		this.scBj = scBj;
	}

	
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getYf() {
		return this.yf;
	}

	public void setYf(double yf) {
		this.yf = yf;
	}

	public String getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(String goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public String getYhId() {
		return yhId;
	}

	public void setYhId(String yhId) {
		this.yhId = yhId;
	}

	
}
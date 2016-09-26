package com.izhbg.typz.shop.purchase.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_sh_purchase database table.
 * 
 */
@Entity
@Table(name="t_sh_purchase")
@NamedQuery(name="TShPurchase.findAll", query="SELECT t FROM TShPurchase t")
public class TShPurchase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="add_time")
	private Date addTime;

	@Column(name="goods_id")
	private String goodsId;

	@Column(name="is_discut")
	private int isDiscut;

	private int num;

	@Column(name="store_id")
	private String storeId;

	@Column(name="yh_id")
	private String yhId;

	public TShPurchase() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getAddTime() {
		return this.addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public int getIsDiscut() {
		return this.isDiscut;
	}

	public void setIsDiscut(int isDiscut) {
		this.isDiscut = isDiscut;
	}

	public int getNum() {
		return this.num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getYhId() {
		return this.yhId;
	}

	public void setYhId(String yhId) {
		this.yhId = yhId;
	}

}
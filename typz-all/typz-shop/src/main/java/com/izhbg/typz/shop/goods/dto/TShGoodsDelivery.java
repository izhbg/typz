package com.izhbg.typz.shop.goods.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_sh_goods_delivery database table.
 * 
 */
@Entity
@Table(name="t_sh_goods_delivery")
@NamedQuery(name="TShGoodsDelivery.findAll", query="SELECT t FROM TShGoodsDelivery t")
public class TShGoodsDelivery implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="address_self")
	private String addressSelf;

	private String bytj;

	@Column(name="goods_id")
	private String goodsId;

	@Column(name="is_delivery")
	private byte isDelivery;

	@Column(name="is_qg_delivery")
	private byte isQgDelivery;

	@Column(name="is_self")
	private byte isSelf;

	@Column(name="is_send")
	private byte isSend;

	@Column(name="is_tc_delivery")
	private byte isTcDelivery;

	private String psfw;

	private String pssm;

	private double qgyf;

	@Column(name="shop_id")
	private String shopId;

	private double tcyf;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	private double tsyf;

	public TShGoodsDelivery() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddressSelf() {
		return this.addressSelf;
	}

	public void setAddressSelf(String addressSelf) {
		this.addressSelf = addressSelf;
	}

	public String getBytj() {
		return this.bytj;
	}

	public void setBytj(String bytj) {
		this.bytj = bytj;
	}

	public String getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public byte getIsDelivery() {
		return this.isDelivery;
	}

	public void setIsDelivery(byte isDelivery) {
		this.isDelivery = isDelivery;
	}

	public byte getIsQgDelivery() {
		return this.isQgDelivery;
	}

	public void setIsQgDelivery(byte isQgDelivery) {
		this.isQgDelivery = isQgDelivery;
	}

	public byte getIsSelf() {
		return this.isSelf;
	}

	public void setIsSelf(byte isSelf) {
		this.isSelf = isSelf;
	}

	public byte getIsSend() {
		return this.isSend;
	}

	public void setIsSend(byte isSend) {
		this.isSend = isSend;
	}

	public byte getIsTcDelivery() {
		return this.isTcDelivery;
	}

	public void setIsTcDelivery(byte isTcDelivery) {
		this.isTcDelivery = isTcDelivery;
	}

	public String getPsfw() {
		return this.psfw;
	}

	public void setPsfw(String psfw) {
		this.psfw = psfw;
	}

	public String getPssm() {
		return this.pssm;
	}

	public void setPssm(String pssm) {
		this.pssm = pssm;
	}

	public double getQgyf() {
		return this.qgyf;
	}

	public void setQgyf(double qgyf) {
		this.qgyf = qgyf;
	}

	public String getShopId() {
		return this.shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public double getTcyf() {
		return this.tcyf;
	}

	public void setTcyf(double tcyf) {
		this.tcyf = tcyf;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public double getTsyf() {
		return this.tsyf;
	}

	public void setTsyf(double tsyf) {
		this.tsyf = tsyf;
	}

}
package com.izhbg.typz.shop.order.dto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_sh_order_address database table.
 * 
 */
@Entity
@Table(name="t_sh_order_address")
@NamedQuery(name="TShOrderAddress.findAll", query="SELECT t FROM TShOrderAddress t")
public class TShOrderAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="address_self")
	private String addressSelf;

	@Column(name="is_delivery")
	private int isDelivery;

	@Column(name="is_self")
	private int isSelf;

	@Column(name="is_send")
	private int isSend;

	@Column(name="order_id")
	private String orderId;

	@Column(name="sh_address")
	private String shAddress;

	@Column(name="shr_name")
	private String shrName;

	@Column(name="shr_phone")
	private String shrPhone;

	private String tsyq;

	@Column(name="yd_no")
	private String ydNo;

	@Column(name="yd_type_id")
	private String ydTypeId;

	public TShOrderAddress() {
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

	public int getIsDelivery() {
		return this.isDelivery;
	}

	public void setIsDelivery(int isDelivery) {
		this.isDelivery = isDelivery;
	}

	public int getIsSelf() {
		return this.isSelf;
	}

	public void setIsSelf(int isSelf) {
		this.isSelf = isSelf;
	}

	public int getIsSend() {
		return this.isSend;
	}

	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getShAddress() {
		return this.shAddress;
	}

	public void setShAddress(String shAddress) {
		this.shAddress = shAddress;
	}

	public String getShrName() {
		return this.shrName;
	}

	public void setShrName(String shrName) {
		this.shrName = shrName;
	}

	public String getShrPhone() {
		return this.shrPhone;
	}

	public void setShrPhone(String shrPhone) {
		this.shrPhone = shrPhone;
	}

	public String getTsyq() {
		return this.tsyq;
	}

	public void setTsyq(String tsyq) {
		this.tsyq = tsyq;
	}

	public String getYdNo() {
		return this.ydNo;
	}

	public void setYdNo(String ydNo) {
		this.ydNo = ydNo;
	}

	public String getYdTypeId() {
		return this.ydTypeId;
	}

	public void setYdTypeId(String ydTypeId) {
		this.ydTypeId = ydTypeId;
	}

}
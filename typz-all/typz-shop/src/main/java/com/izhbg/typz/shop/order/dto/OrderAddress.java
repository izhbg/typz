package com.izhbg.typz.shop.order.dto;

import javax.persistence.Transient;

public class OrderAddress {
	private String orderId;
	private String addressId;
	private String addressSelf;
	private Integer isDelivery;
	private Integer isSelf;
	private Integer isSend;
	private String tsyq;
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getAddressSelf() {
		return addressSelf;
	}
	public void setAddressSelf(String addressSelf) {
		this.addressSelf = addressSelf;
	}
	public Integer getIsDelivery() {
		return isDelivery;
	}
	public void setIsDelivery(Integer isDelivery) {
		this.isDelivery = isDelivery;
	}
	public Integer getIsSelf() {
		return isSelf;
	}
	public void setIsSelf(Integer isSelf) {
		this.isSelf = isSelf;
	}
	public Integer getIsSend() {
		return isSend;
	}
	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTsyq() {
		return tsyq;
	}
	public void setTsyq(String tsyq) {
		this.tsyq = tsyq;
	}
	
	
}

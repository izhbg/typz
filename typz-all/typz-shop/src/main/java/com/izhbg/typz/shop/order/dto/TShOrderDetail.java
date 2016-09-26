package com.izhbg.typz.shop.order.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_sh_order_detail database table.
 * 
 */
@Entity
@Table(name="t_sh_order_detail")
@NamedQuery(name="TShOrderDetail.findAll", query="SELECT t FROM TShOrderDetail t")
public class TShOrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fh_time")
	private Date fhTime;

	@Column(name="order_id")
	private String orderId;

	@Column(name="pay_type")
	private byte payType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sh_time")
	private Date shTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sqth_time")
	private Date sqthTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="thcl_time")
	private Date thclTime;

	@Column(name="trade_no")
	private String tradeNo;

	@Column(name="trade_state")
	private byte tradeState;

	public TShOrderDetail() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getFhTime() {
		return this.fhTime;
	}

	public void setFhTime(Date fhTime) {
		this.fhTime = fhTime;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public byte getPayType() {
		return this.payType;
	}

	public void setPayType(byte payType) {
		this.payType = payType;
	}

	public Date getShTime() {
		return this.shTime;
	}

	public void setShTime(Date shTime) {
		this.shTime = shTime;
	}

	public Date getSqthTime() {
		return this.sqthTime;
	}

	public void setSqthTime(Date sqthTime) {
		this.sqthTime = sqthTime;
	}

	public Date getThclTime() {
		return this.thclTime;
	}

	public void setThclTime(Date thclTime) {
		this.thclTime = thclTime;
	}

	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public byte getTradeState() {
		return this.tradeState;
	}

	public void setTradeState(byte tradeState) {
		this.tradeState = tradeState;
	}

}
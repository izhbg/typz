package com.izhbg.typz.im.order.reponse.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.izhbg.typz.im.goods.response.entity.TShGoodsBasic;
import com.izhbg.typz.im.store.response.entity.TShStore;
import com.izhbg.typz.shop.order.dto.TShOrderAddress;


public class TShOrder implements Serializable {

	private String id;


	private String storeId;
	private Date time;

	private int totalCount;

	private double totalPrice;
	private Integer status; //订单状态
						     //状态  0：已下单    待付款                     1：已支付/到付  待发货
							  //   2：待收货   已发货                    3：已收货   完成 
	private double yf;
	private String yhId;
	private TShOrderAddress tShOrderAddress;
	private List<TShGoodsBasic> tShGoodsList;
	private TShStore tShStore;
	private String other;
	
	public TShOrder() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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


	public String getYhId() {
		return yhId;
	}

	public void setYhId(String yhId) {
		this.yhId = yhId;
	}

	public TShOrderAddress gettShOrderAddress() {
		return tShOrderAddress;
	}

	public void settShOrderAddress(TShOrderAddress tShOrderAddress) {
		this.tShOrderAddress = tShOrderAddress;
	}


	public List<TShGoodsBasic> gettShGoodsList() {
		return tShGoodsList;
	}

	public void settShGoodsList(List<TShGoodsBasic> tShGoodsList) {
		this.tShGoodsList = tShGoodsList;
	}

	public TShStore gettShStore() {
		return tShStore;
	}

	public void settShStore(TShStore tShStore) {
		this.tShStore = tShStore;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
package com.izhbg.typz.im.goods.response.entity;

import java.util.List;

import com.izhbg.typz.shop.goods.dto.TShGoodsDelivery;
import com.izhbg.typz.shop.store.dto.TShStore;

public class TShGoodsBasic {
	private String id;
	/**  店铺ID**/
	private String storeId;
	/**  名称**/
	private String name;
	/**  单位**/
	private String unit;
	/**  **/
	private String vender;
	private TShGoodsDetail tShGoodsDetail;
	private List<TShGoodsImage> tShGoodsImages;
	private TShGoodsImage IndexImage;
	private TShGoodsDelivery tShGoodsDelivery;
	private List<TShGoodsTags> tShGoodsTags;
	private double salePrice;//销售价格
	private double price;
	private TShStore tsShStore;
	public String getId() {
		return id;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getVender() {
		return vender;
	}
	public void setVender(String vender) {
		this.vender = vender;
	}
	public TShGoodsDetail gettShGoodsDetail() {
		return tShGoodsDetail;
	}
	public void settShGoodsDetail(TShGoodsDetail tShGoodsDetail) {
		this.tShGoodsDetail = tShGoodsDetail;
	}
	public List<TShGoodsImage> gettShGoodsImages() {
		return tShGoodsImages;
	}
	public void settShGoodsImages(List<TShGoodsImage> tShGoodsImages) {
		this.tShGoodsImages = tShGoodsImages;
	}
	public TShGoodsImage getIndexImage() {
		return IndexImage;
	}
	public void setIndexImage(TShGoodsImage indexImage) {
		IndexImage = indexImage;
	}
	public TShGoodsDelivery gettShGoodsDelivery() {
		return tShGoodsDelivery;
	}
	public void settShGoodsDelivery(TShGoodsDelivery tShGoodsDelivery) {
		this.tShGoodsDelivery = tShGoodsDelivery;
	}
	public List<TShGoodsTags> gettShGoodsTags() {
		return tShGoodsTags;
	}
	public void settShGoodsTags(List<TShGoodsTags> tShGoodsTags) {
		this.tShGoodsTags = tShGoodsTags;
	}
	public double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public TShStore getTsShStore() {
		return tsShStore;
	}
	public void setTsShStore(TShStore tsShStore) {
		this.tsShStore = tsShStore;
	}
}

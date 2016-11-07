package com.izhbg.typz.im.goods.response.entity;

import java.util.List;


public class TShGoodsTags {
	
	private String id;
	private String name;
	private String color;
	private Integer xh;
	private List<TShGoodsBasic> tsGoodsBasics;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getXh() {
		return xh;
	}
	public void setXh(Integer xh) {
		this.xh = xh;
	}
	public List<TShGoodsBasic> getTsGoodsBasics() {
		return tsGoodsBasics;
	}
	public void setTsGoodsBasics(List<TShGoodsBasic> tsGoodsBasics) {
		this.tsGoodsBasics = tsGoodsBasics;
	}
	
	
}

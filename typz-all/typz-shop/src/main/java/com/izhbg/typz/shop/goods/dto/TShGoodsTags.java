package com.izhbg.typz.shop.goods.dto;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_sh_goods_tags")
public class TShGoodsTags {
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	private String id;
	private String name;
	private String color;
	private Integer xh;
	@Transient
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

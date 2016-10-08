package com.izhbg.typz.shop.goods.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_sh_goods_tag")
public class TShGoodsTag {
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	private String id;
	@Column(name="goods_id")
	private String goodsId;
	@Column(name="tag_id")
	private String tagId;
	private Integer xh;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getXh() {
		return xh;
	}
	public void setXh(Integer xh) {
		this.xh = xh;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	
	

}

package com.izhbg.typz.shop.store.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_sh_store_account_bank")
public class TShStoreAccountBank {
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned") 
	private String id;
	private String name;//银行类型名称
	private Integer xh;//排序序号
	private String icon;//图标
	private String other;//其他
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
	public Integer getXh() {
		return xh;
	}
	public void setXh(Integer xh) {
		this.xh = xh;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	
}

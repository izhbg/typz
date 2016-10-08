package com.izhbg.typz.shop.store.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the t_sh_store database table.
 * 
 */
@Entity
@Table(name="t_sh_store")
@NamedQuery(name="TShStore.findAll", query="SELECT t FROM TShStore t")
public class TShStore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Lob
	private String content;//店铺描述

	private String other;

	private Integer state; // 0：待审核 1：审核通过 2：审核未通过
	@Column(name="sc_bj")
	private Integer scBj;//1:删除  2：正常
	
	private String shyj;//审核意见

	@Column(name="add_time")
	private Date addTime;//添加时间
	
	@Column(name="sq_time")
	private Date sqTime;//申请时间
	
	@Column(name="sh_time")
	private Date shTime;//审核时间

	private String title;//标题
	@Column(name="type")
	private Integer type;//类型 1：厂家   2：加盟商
	@Column(name="sh_user_id")
	private String shUserID;//审核人
	@Column(name="yh_id")//关联用户
	private String yhId;
	private double la;//维度
	private double lo;//经度
	private String address;//详细地址

	@Transient
	private TShStoreAttachefile  logoAttache;
	@Transient
	private List<TShStoreAttachefile> tShStoreAttachefiles;
	public TShStore() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}



	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getScBj() {
		return scBj;
	}

	public void setScBj(Integer scBj) {
		this.scBj = scBj;
	}

	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getSqTime() {
		return sqTime;
	}

	public void setSqTime(Date sqTime) {
		this.sqTime = sqTime;
	}

	public Date getShTime() {
		return shTime;
	}

	public void setShTime(Date shTime) {
		this.shTime = shTime;
	}

	public String getShUserID() {
		return shUserID;
	}

	public void setShUserID(String shUserID) {
		this.shUserID = shUserID;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public TShStoreAttachefile getLogoAttache() {
		return logoAttache;
	}

	public void setLogoAttache(TShStoreAttachefile logoAttache) {
		this.logoAttache = logoAttache;
	}

	public List<TShStoreAttachefile> gettShStoreAttachefiles() {
		return tShStoreAttachefiles;
	}

	public void settShStoreAttachefiles(List<TShStoreAttachefile> tShStoreAttachefiles) {
		this.tShStoreAttachefiles = tShStoreAttachefiles;
	}

	public String getYhId() {
		return yhId;
	}

	public void setYhId(String yhId) {
		this.yhId = yhId;
	}

	public double getLa() {
		return la;
	}

	public void setLa(double la) {
		this.la = la;
	}

	public double getLo() {
		return lo;
	}

	public void setLo(double lo) {
		this.lo = lo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
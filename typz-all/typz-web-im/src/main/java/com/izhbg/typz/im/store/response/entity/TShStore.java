package com.izhbg.typz.im.store.response.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the t_sh_store database table.
 * 
 */
public class TShStore implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String content;//店铺描述

	private String other;

	private String title;//标题
	private Integer type;//类型 1：厂家   2：加盟商
	private String address;//详细地址

	private TShStoreAttachefile  logoAttache;
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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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


	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
package com.izhbg.typz.shop.store.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_sh_store_attachefile database table.
 * 
 */
@Entity
@Table(name="t_sh_store_attachefile")
@NamedQuery(name="TShStoreAttachefile.findAll", query="SELECT t FROM TShStoreAttachefile t")
public class TShStoreAttachefile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String content;

	private String path;

	private double size;

	@Column(name="store_id")
	private String storeId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	private int xh;

	@Column(name="yh_id")
	private String yhId;

	private Integer state;
	
	public TShStoreAttachefile() {
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

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public double getSize() {
		return this.size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public String getStoreId() {
		return this.storeId;
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

	public int getXh() {
		return this.xh;
	}

	public void setXh(int xh) {
		this.xh = xh;
	}

	public String getYhId() {
		return this.yhId;
	}

	public void setYhId(String yhId) {
		this.yhId = yhId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
}
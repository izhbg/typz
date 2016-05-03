package com.izhbg.typz.sso.auth.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;



/**
 * TXtGnjsZy entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@Table(name="t_xt_gnjs_zy")
public class TXtGnjsZy implements java.io.Serializable {

	// Fields

	private String uuid;
	private String jsDm;
	private String gnzyDm;

	//reference
	private TXtGnzy gnzy;
	private TXtGnjs gnjs;
	// Constructors
	
	private Integer isRead;
	private Integer isCreate;
	private Integer isUpdate;
	private Integer isDelete;
	private Integer isAll;
	

	/** default constructor */
	public TXtGnjsZy() {
	}

	/** full constructor */
	public TXtGnjsZy(String uuid, String jsDm, String gnzyDm) {
		this.uuid = uuid;
		this.jsDm = jsDm;
		this.gnzyDm = gnzyDm;
	}


	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")   
	public String getUuid() {
		return this.uuid;
	}



	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name="JS_DM")
	public String getJsDm() {
		return this.jsDm;
	}

	public void setJsDm(String jsDm) {
		this.jsDm = jsDm;
	}

	@Column(name="GNZY_DM")
	public String getGnzyDm() {
		return this.gnzyDm;
	}

	public void setGnzyDm(String gnzyDm) {
		this.gnzyDm = gnzyDm;
	}
	
	@Transient
	public TXtGnzy getGnzy() {
		return gnzy;
	}

	public void setGnzy(TXtGnzy gnzy) {
		this.gnzy = gnzy;
	}

	@Transient
	public TXtGnjs getGnjs() {
		return gnjs;
	}

	public void setGnjs(TXtGnjs gnjs) {
		this.gnjs = gnjs;
	}

	@Column(name="is_read")
	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	@Column(name="is_create")
	public Integer getIsCreate() {
		return isCreate;
	}

	public void setIsCreate(Integer isCreate) {
		this.isCreate = isCreate;
	}

	@Column(name="is_update")
	public Integer getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(Integer isUpdate) {
		this.isUpdate = isUpdate;
	}

	@Column(name="is_delete")
	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	@Column(name="is_all")
	public Integer getIsAll() {
		return isAll;
	}

	public void setIsAll(Integer isAll) {
		this.isAll = isAll;
	}

	

}
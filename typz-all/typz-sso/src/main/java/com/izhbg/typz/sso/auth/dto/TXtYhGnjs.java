package com.izhbg.typz.sso.auth.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;



/**
 * TXtYhGnjs entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@Table(name="T_XT_YH_GNJS")
public class TXtYhGnjs implements java.io.Serializable {

	// Fields

	private String uuid;
	private String jsDm;
	private String yhId;

	//reference
	private TXtGnjs gnjs;
	private TXtYh yh;
	// Constructors

	/** default constructor */
	public TXtYhGnjs() {
	}

	/** full constructor */
	public TXtYhGnjs(String uuid, String jsDm, String yhId) {
		this.uuid = uuid;
		this.jsDm = jsDm;
		this.yhId = yhId;
	}

	// Property accessors

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

	@Column(name="YH_ID")
	public String getYhId() {
		return this.yhId;
	}

	public void setYhId(String yhId) {
		this.yhId = yhId;
	}
	@Transient
	public TXtGnjs getGnjs() {
		return gnjs;
	}

	public void setGnjs(TXtGnjs gnjs) {
		this.gnjs = gnjs;
	}

	@Transient
	public TXtYh getYh() {
		return yh;
	}

	public void setYh(TXtYh yh) {
		this.yh = yh;
	}


}
package com.izhbg.typz.sso.auth.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;


 
/**
 * TXtJgYh entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_xt_jg_yh")
public class TXtJgYh implements java.io.Serializable {

	// Fields

	private String duId;
	private String jgId;
	private String yhId;

	//reference
	private TXtYh yh;
	private TXtJg jg;
	// Constructors

	/** default constructor */
	public TXtJgYh() {
	}

	/** full constructor */
	public TXtJgYh(String duId, String jgId, String yhId) {
		this.duId = duId;
		this.jgId = jgId;
		this.yhId = yhId;
	}

	// Property accessors

	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")   
	@Column(name="DU_ID")
	public String getDuId() {
		return this.duId;
	}

	public void setDuId(String duId) {
		this.duId = duId;
	}

	@Column(name="JG_ID")
	public String getJgId() {
		return this.jgId;
	}

	public void setJgId(String jgId) {
		this.jgId = jgId;
	}

	@Column(name="YH_ID")
	public String getYhId() {
		return this.yhId;
	}

	public void setYhId(String yhId) {
		this.yhId = yhId;
	}
	
	@Transient
	public TXtYh getYh() {
		return yh;
	}

	public void setYh(TXtYh yh) {
		this.yh = yh;
	}

	@Transient
	public TXtJg getJg() {
		return jg;
	}

	public void setJg(TXtJg jg) {
		this.jg = jg;
	}



	
}
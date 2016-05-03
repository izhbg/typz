package com.izhbg.typz.sso.auth.dto;

import java.util.Comparator;
import java.util.Date;
import java.util.List;  

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * TXtJg entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_xt_jg")
public class TXtJg implements java.io.Serializable {

	// Fields

	private String jgId;
	private String jgDm;
	private String jgMc;
	private Integer scBj;
	private Integer yxBj;
	private Date lrRq;
	private String sjjgId;
	private Integer xh;
	private String bz;
	private Date xgRq;
	private String czryId;
	private String ccm;
	private Integer jgLx;
	private Integer jgBm;
	private String level;
	private String newurl;
	private String appId;


	// Constructors

	/** default constructor */
	public TXtJg() {
	}

	/** minimal constructor */
	public TXtJg(String jgId) {
		this.jgId = jgId;
	}

	/** full constructor */
	public TXtJg(String jgId, String jgDm, String jgMc, Integer scBj,
			Integer yxBj, Date lrRq, String sjjgId, Integer xh, String bz,
			Date xgRq, String czryId, String ccm, Integer jgLx, Integer jgBm,
			String level, String newurl) {
		this.jgId = jgId;
		this.jgDm = jgDm;
		this.jgMc = jgMc;
		this.scBj = scBj;
		this.yxBj = yxBj;
		this.lrRq = lrRq;
		this.sjjgId = sjjgId;
		this.xh = xh;
		this.bz = bz;
		this.xgRq = xgRq;
		this.czryId = czryId;
		this.ccm = ccm;
		this.jgLx = jgLx;
		this.jgBm = jgBm;
		this.level = level;
		this.newurl = newurl;
	}

	// Property accessors

	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")   
	@Column(name="JG_ID")
	public String getJgId() {
		return this.jgId;
	}

	public void setJgId(String jgId) {
		this.jgId = jgId;
	}

	@Column(name="JG_DM")
	public String getJgDm() {
		return this.jgDm;
	}

	public void setJgDm(String jgDm) {
		this.jgDm = jgDm;
	}

	@Column(name="JG_MC")
	public String getJgMc() {
		return this.jgMc;
	}

	public void setJgMc(String jgMc) {
		this.jgMc = jgMc;
	}

	@Column(name="SC_BJ")
	public Integer getScBj() {
		return this.scBj;
	}

	public void setScBj(Integer scBj) {
		this.scBj = scBj;
	}

	@Column(name="YX_BJ")
	public Integer getYxBj() {
		return this.yxBj;
	}

	public void setYxBj(Integer yxBj) {
		this.yxBj = yxBj;
	}

	@Column(name="LR_RQ")
	public Date getLrRq() {
		return this.lrRq;
	}

	public void setLrRq(Date lrRq) {
		this.lrRq = lrRq;
	}

	@Column(name="SJJG_ID")
	public String getSjjgId() {
		return this.sjjgId;
	}

	public void setSjjgId(String sjjgId) {
		this.sjjgId = sjjgId;
	}

	public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Column(name="XG_RQ")
	public Date getXgRq() {
		return this.xgRq;
	}

	public void setXgRq(Date xgRq) {
		this.xgRq = xgRq;
	}

	@Column(name="CZRY_ID")
	public String getCzryId() {
		return this.czryId;
	}

	public void setCzryId(String czryId) {
		this.czryId = czryId;
	}

	public String getCcm() {
		return this.ccm;
	}

	public void setCcm(String ccm) {
		this.ccm = ccm;
	}

	@Column(name="JG_LX")
	public Integer getJgLx() {
		return this.jgLx;
	}

	public void setJgLx(Integer jgLx) {
		this.jgLx = jgLx;
	}

	@Column(name="JG_BM")
	public Integer getJgBm() {
		return this.jgBm;
	}

	public void setJgBm(Integer jgBm) {
		this.jgBm = jgBm;
	}

	@Transient
	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getNewurl() {
		return this.newurl;
	}

	public void setNewurl(String newurl) {
		this.newurl = newurl;
	}
	@Column(name="APP_ID")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	
	
	
			

}
package com.izhbg.typz.sso.auth.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * TXtGnzy entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_xt_gnzy")
public class TXtGnzy implements java.io.Serializable {

	// Fields

	private String gnDm;
	private String gnMc;
	private Integer gnLx;
	private String sjgnDm;
	private String url;
	private Integer gnXh;
	private Integer yxBj;
	private Integer sqBj;	
	private String appId;
	private String gnLs;
	private String bz;
	private String gnIcon;
	private String newurl;

	// Constructors

	/** default constructor */
	public TXtGnzy() {
	}

	/** minimal constructor */
	public TXtGnzy(String gnDm, String gnMc) {
		this.gnDm = gnDm;
		this.gnMc = gnMc;
	}

	/** full constructor */
	public TXtGnzy(String gnDm, String gnMc, Integer gnLx, String sjgnDm,
			String url, Integer gnXh, Integer yxBj, Integer sqBj, String appId,String gnLs,
			String bz, String gnIcon, String newurl) {
		this.gnDm = gnDm;
		this.gnMc = gnMc;
		this.gnLx = gnLx;
		this.sjgnDm = sjgnDm;
		this.url = url;
		this.gnXh = gnXh;
		this.yxBj = yxBj;
		this.sqBj = sqBj;
		this.appId = appId;
		this.gnLs = gnLs;
		this.bz = bz;
		this.gnIcon = gnIcon;
		this.newurl = newurl;
	}

	// Property accessors

	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")   
	@Column(name="GN_DM")
	public String getGnDm() {
		return this.gnDm;
	}

	public void setGnDm(String gnDm) {
		this.gnDm = gnDm;
	}

	@Column(name="GN_MC")
	public String getGnMc() {
		return this.gnMc;
	}

	public void setGnMc(String gnMc) {
		this.gnMc = gnMc;
	}

	@Column(name="GN_LX")
	public Integer getGnLx() {
		return this.gnLx;
	}

	public void setGnLx(Integer gnLx) {
		this.gnLx = gnLx;
	}

	@Column(name="SJGN_DM")
	public String getSjgnDm() {
		return this.sjgnDm;
	}

	public void setSjgnDm(String sjgnDm) {
		this.sjgnDm = sjgnDm;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name="GN_XH")
	public Integer getGnXh() {
		return this.gnXh;
	}

	public void setGnXh(Integer gnXh) {
		this.gnXh = gnXh;
	}
	@Column(name="YX_BJ")
	public Integer getYxBj() {
		return this.yxBj;
	}

	public void setYxBj(Integer yxBj) {
		this.yxBj = yxBj;
	}

	@Column(name="SQ_BJ")
	public Integer getSqBj() {
		return this.sqBj;
	}

	public void setSqBj(Integer sqBj) {
		this.sqBj = sqBj;
	}

	@Column(name="APP_ID")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Column(name="GN_LS")
	public String getGnLs() {
		return this.gnLs;
	}

	public void setGnLs(String gnLs) {
		this.gnLs = gnLs;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Column(name="GN_ICON")
	public String getGnIcon() {
		return this.gnIcon;
	}

	public void setGnIcon(String gnIcon) {
		this.gnIcon = gnIcon;
	}

	public String getNewurl() {
		return this.newurl;
	}

	public void setNewurl(String newurl) {
		this.newurl = newurl;
	} 
	
	
	

}
package com.izhbg.typz.sso.auth.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * TXtGnjs entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_xt_gnjs")
public class TXtGnjs implements java.io.Serializable {

	// Fields

	private String gnjsDm;
	private String gnjsMc;
	private String jgId;
	private String appId;
	private Integer jsLx;
	private Integer yxBj;
	private String bz;
	private String code;
	private String viewDirIds;
	private String resourceIds;
	private String viewSelfDept;
	

	// Constructors

	/** default constructor */
	public TXtGnjs() {
	}

	/** minimal constructor */
	public TXtGnjs(String gnjsDm, String gnjsMc) {
		this.gnjsDm = gnjsDm;
		this.gnjsMc = gnjsMc;
	}

	/** full constructor */
	public TXtGnjs(String gnjsDm, String gnjsMc, String jgId, String appId, Integer jsLx,
			Integer yxBj, String bz) {
		this.gnjsDm = gnjsDm;
		this.gnjsMc = gnjsMc;
		this.jgId = jgId;
		this.appId = appId;
		this.jsLx = jsLx;
		this.yxBj = yxBj;
		this.bz = bz;
	}

	// Property accessors

	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")   
	@Column(name="GNJS_DM")
	public String getGnjsDm() {
		return this.gnjsDm;
	}

	public void setGnjsDm(String gnjsDm) {
		this.gnjsDm = gnjsDm;
	}

	@Column(name="GNJS_MC")
	public String getGnjsMc() {
		return this.gnjsMc;
	}

	public void setGnjsMc(String gnjsMc) {
		this.gnjsMc = gnjsMc;
	}

	@Column(name="JG_ID")
	public String getJgId() {
		return this.jgId;
	}

	public void setJgId(String jgId) {
		this.jgId = jgId;
	}

	@Column(name="APP_ID")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Column(name="JS_LX")
	public Integer getJsLx() {
		return this.jsLx;
	}

	public void setJsLx(Integer jsLx) {
		this.jsLx = jsLx;
	}

	@Column(name="YX_BJ")
	public Integer getYxBj() {
		return this.yxBj;
	}

	public void setYxBj(Integer yxBj) {
		this.yxBj = yxBj;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name="viewDirIds")
	public String getViewDirIds() {
		return viewDirIds;
	}
	
	public void setViewDirIds(String viewDirIds) {
		this.viewDirIds = viewDirIds;
	}
	@Column(name="resourceIds")
	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
	@Column(name="viewSelfDept")
	public String getViewSelfDept() {
		return viewSelfDept;
	}

	public void setViewSelfDept(String viewSelfDept) {
		this.viewSelfDept = viewSelfDept;
	}
	
	

}
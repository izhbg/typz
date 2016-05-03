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
@Table(name="t_xt_yy_api")
public class TXtYyApi implements java.io.Serializable {

	// Fields

	private String apiId;
	private String yyId;
	private String password;
	private String uuid;
	
	//reference
	private TXtYy app;
	
	public TXtYyApi() {
		super();
	}


	public TXtYyApi(String apiId, String yyId, String uuid) {
		super();
		this.apiId = apiId;
		this.yyId = yyId;
		this.uuid = uuid;
	}
	
	
	public TXtYyApi(String apiId, String yyId, String password, String uuid) {
		super();
		this.apiId = apiId;
		this.yyId = yyId;
		this.password = password;
		this.uuid = uuid;
	}

	
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")   
	@Column(name="API_ID")
	public String getApiId() {
		return apiId;
	}
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	@Column(name="YY_ID")
	public String getYyId() {
		return yyId;
	}
	public void setYyId(String yyId) {
		this.yyId = yyId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Transient
	public TXtYy getApp() {
		return app;
	}

	public void setApp(TXtYy app) {
		this.app = app;
	}

	
	
}
package com.izhbg.typz.sso.auth.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



/**
 * TXtJg entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@Table(name="t_xt_yy")
public class TXtYy implements java.io.Serializable {

	// Fields
	private String yyId;
	private String appName;
	private String chineseName;
	private String deployUrl;
	private String homePage;
	private String description;
	private String respectiveDivisions;
	private String charger;
	private String contact;
	private String logoMark;
	private String code;
	private String showFlag;
	private String loginFlag;
	private String operateTime;
	private String loginDisplay;
	private String password;
	private Integer classification;
	private Integer sortNo;
	private Integer yxBj;
	
	// Constructors
	
	/** default constructor */
	public TXtYy() {
		super();
	}

	/** minimal constructor */
	public TXtYy(String yyId, String appName, String deployUrl, String homePage) {
		super();
		this.yyId = yyId;
		this.appName = appName;
		this.deployUrl = deployUrl;
		this.homePage = homePage;
	}
	
	/** full constructor */
	public TXtYy(String yyId, String appName, String chineseName,
			String deployUrl, String homePage, String description,
			String respectiveDivisions, String charger, String contact,
			String logoMark, String code, String showFlag, String loginFlag,
			String operateTime, String loginDisplay, String password,
			Integer classification, Integer sortNo,Integer yxBj) {
		super();
		this.yyId = yyId;
		this.appName = appName;
		this.chineseName = chineseName;
		this.deployUrl = deployUrl;
		this.homePage = homePage;
		this.description = description;
		this.respectiveDivisions = respectiveDivisions;
		this.charger = charger;
		this.contact = contact;
		this.logoMark = logoMark;
		this.code = code;
		this.showFlag = showFlag;
		this.loginFlag = loginFlag;
		this.operateTime = operateTime;
		this.loginDisplay = loginDisplay;
		this.password = password;
		this.classification = classification;
		this.sortNo = sortNo;
		this.yxBj = yxBj;
	}

	
	// Property accessors
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")   
	@Column(name="YY_ID")
	public String getYyId() {
		return yyId;
	}

	public void setYyId(String yyId) {
		this.yyId = yyId;
	}

	@Column(name="APP_NAME")
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Column(name="CHINESE_NAME")
	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	@Column(name="DEPLOY_URL")
	public String getDeployUrl() {
		return deployUrl;
	}

	public void setDeployUrl(String deployUrl) {
		this.deployUrl = deployUrl;
	}

	@Column(name="HOME_PAGE")
	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="RESPECTIVE_DIVISIONS")
	public String getRespectiveDivisions() {
		return respectiveDivisions;
	}

	public void setRespectiveDivisions(String respectiveDivisions) {
		this.respectiveDivisions = respectiveDivisions;
	}

	public String getCharger() {
		return charger;
	}

	public void setCharger(String charger) {
		this.charger = charger;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name="LOGO_MARK")
	public String getLogoMark() {
		return logoMark;
	}

	public void setLogoMark(String logoMark) {
		this.logoMark = logoMark;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name="SHOW_FLAG")
	public String getShowFlag() {
		return showFlag;
	}

	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}

	@Column(name="LOGIN_FLAG")
	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	@Column(name="OPERATE_TIME")
	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name="LOGIN_DISPLAY")
	public String getLoginDisplay() {
		return loginDisplay;
	}

	public void setLoginDisplay(String loginDisplay) {
		this.loginDisplay = loginDisplay;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getClassification() {
		return classification;
	}

	public void setClassification(Integer classification) {
		this.classification = classification;
	}

	@Column(name="SORT_NO")
	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	
	@Column(name="YX_BJ")
    public Integer getYxBj() {
		return yxBj;
	}

	public void setYxBj(Integer yxBj) {
		this.yxBj = yxBj;
	}

	
   

}
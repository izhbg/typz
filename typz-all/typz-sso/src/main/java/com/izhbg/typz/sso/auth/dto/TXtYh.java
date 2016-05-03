package com.izhbg.typz.sso.auth.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List; 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;



/**
 * TXtYh entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="t_xt_yh")
public class TXtYh implements java.io.Serializable {

	// Fields
	private String yhId;
	private String yhDm;
	private String yhMc;
	private String mm;
	private Integer xb;
	private Integer yhLx;
	private Integer xh;
	/**
	 * 1、正常，其他不正常
	 */
	private Integer yxBj;
	/**
	 * 2、正常，其他删除
	 */
	private Integer scBj;
	private Date lrRq;
	private Date xgRq;
	private String czryId;
	private String email;
	private String bz;
	
	// Fields not in doc
	private String userOfficePhone;
	private String userMobile;
	private String userIcq;
	private String userSystemAdmin;
	private String userLastLogin;
	private Integer userPriority;
	private Integer userLevel;
	private String readOnly;
	private Integer userPwdDuration;
	private Integer userLoginFails;
	private Integer syncTag;
	private String uumsBj;
	private String photoPath;
	private String duty;
	private String branchedpassageJob;
	private String secretary;
	private String officeSpace;
	private String outEmail;
	private String staffId;
	
	private String ddqmPath;

	//display remark
	private Boolean check;
	
	private int isadmin;
	
	//reference
	private String jgId;
	
	private String appId;
	
	private String docRoleIds;
	
	// Constructors
	private Integer access=1;
	

	/** default constructor */
	public TXtYh() {
	}

	/** minimal constructor */
	public TXtYh(String yhId, String yhDm, String mm) {
		this.yhId = yhId;
		this.yhDm = yhDm;
		this.mm = mm;
	}

	/** full constructor */
	public TXtYh(String yhId, String yhDm, String yhMc, String mm, Integer xb,
			Integer yhLx, Integer xh, Integer yxBj, Integer scBj, Date lrRq,
			Date xgRq, String czryId, String email, String bz,
			String userOfficePhone, String userMobile, String userIcq,
			String userSystemAdmin, String userLastLogin, Integer userPriority,
			Integer userLevel, String readOnly, Integer userPwdDuration,
			Integer userLoginFails, Integer syncTag, String uumsBj,
			String photoPath, String duty, String branchedpassageJob,
			String secretary, String officeSpace, String outEmail,
			String staffId,String ddqmPath) {
		this.yhId = yhId;
		this.yhDm = yhDm;
		this.yhMc = yhMc;
		this.mm = mm;
		this.xb = xb;
		this.yhLx = yhLx;
		this.xh = xh;
		this.yxBj = yxBj;
		this.scBj = scBj;
		this.lrRq = lrRq;
		this.xgRq = xgRq;
		this.czryId = czryId;
		this.email = email;
		this.bz = bz;
		this.userOfficePhone = userOfficePhone;
		this.userMobile = userMobile;
		this.userIcq = userIcq;
		this.userSystemAdmin = userSystemAdmin;
		this.userLastLogin = userLastLogin;
		this.userPriority = userPriority;
		this.userLevel = userLevel;
		this.readOnly = readOnly;
		this.userPwdDuration = userPwdDuration;
		this.userLoginFails = userLoginFails;
		this.syncTag = syncTag;
		this.uumsBj = uumsBj;
		this.photoPath = photoPath;
		this.duty = duty;
		this.branchedpassageJob = branchedpassageJob;
		this.secretary = secretary;
		this.officeSpace = officeSpace;
		this.outEmail = outEmail;
		this.staffId = staffId;
		this.ddqmPath = ddqmPath;
	}

	// Property accessors

	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")   
	@Column(name="YH_ID" )
	public String getYhId() {
		return this.yhId;
	}

	public void setYhId(String yhId) {
		this.yhId = yhId;
	}

	@Column(name="YH_DM")
	public String getYhDm() {
		return this.yhDm;
	}

	public void setYhDm(String yhDm) {
		this.yhDm = yhDm;
	}

	@Column(name="YH_MC")
	public String getYhMc() {
		return this.yhMc;
	}

	public void setYhMc(String yhMc) {
		this.yhMc = yhMc;
	}

	public String getMm() {
		return this.mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public Integer getXb() {
		return this.xb;
	}

	public void setXb(Integer xb) {
		this.xb = xb;
	}

	@Column(name="YH_LX")
	public Integer getYhLx() {
		return this.yhLx;
	}

	public void setYhLx(Integer yhLx) {
		this.yhLx = yhLx;
	}

	public Integer getXh() 
	{  
		if(this.xh==null)
		{
			return 0;
		}
		else
		{
			return this.xh;
		}
	}

	public void setXh(Integer xh) {
		this.xh = xh;
	}

	@Column(name="YX_BJ")
	public Integer getYxBj() {
		return this.yxBj;
	}

	public void setYxBj(Integer yxBj) {
		this.yxBj = yxBj;
	}

	@Column(name="SC_BJ")
	public Integer getScBj() {
		return this.scBj;
	}

	public void setScBj(Integer scBj) {
		this.scBj = scBj;
	}

	@Column(name="LR_RQ")
	public Date getLrRq() {
		return this.lrRq;
	}

	public void setLrRq(Date lrRq) {
		this.lrRq = lrRq;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	@Column(name="USER_OFFICE_PHONE")
	public String getUserOfficePhone() {
		return this.userOfficePhone;
	}

	public void setUserOfficePhone(String userOfficePhone) {
		this.userOfficePhone = userOfficePhone;
	}

	@Column(name="USER_MOBILE")
	public String getUserMobile() {
		return this.userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	@Column(name="USER_ICQ")
	public String getUserIcq() {
		return this.userIcq;
	}

	public void setUserIcq(String userIcq) {
		this.userIcq = userIcq;
	}

	@Column(name="USER_SYSTEM_ADMIN")
	public String getUserSystemAdmin() {
		return this.userSystemAdmin;
	}

	public void setUserSystemAdmin(String userSystemAdmin) {
		this.userSystemAdmin = userSystemAdmin;
	}

	@Column(name="USER_LAST_LOGIN")
	public String getUserLastLogin() {
		return this.userLastLogin;
	}

	public void setUserLastLogin(String userLastLogin) {
		this.userLastLogin = userLastLogin;
	}

	@Column(name="USER_PRIORITY")
	public Integer getUserPriority() {
		return this.userPriority;
	}

	public void setUserPriority(Integer userPriority) {
		this.userPriority = userPriority;
	}

	@Column(name="USER_LEVEL")
	public Integer getUserLevel() {
		return this.userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	@Column(name="READ_ONLY")
	public String getReadOnly() {
		return this.readOnly;
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	@Column(name="USER_PWD_DURATION")
	public Integer getUserPwdDuration() {
		return this.userPwdDuration;
	}

	public void setUserPwdDuration(Integer userPwdDuration) {
		this.userPwdDuration = userPwdDuration;
	}

	@Column(name="USER_LOGIN_FAILS")
	public Integer getUserLoginFails() {
		return this.userLoginFails;
	}

	public void setUserLoginFails(Integer userLoginFails) {
		this.userLoginFails = userLoginFails;
	}

	@Column(name="SYNC_TAG")
	public Integer getSyncTag() {
		return this.syncTag;
	}

	public void setSyncTag(Integer syncTag) {
		this.syncTag = syncTag;
	}

	@Column(name="UUMS_BJ")
	public String getUumsBj() {
		return this.uumsBj;
	}

	public void setUumsBj(String uumsBj) {
		this.uumsBj = uumsBj;
	}

	@Column(name="PHOTO_PATH")
	public String getPhotoPath() {
		return this.photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getDuty() {
		return this.duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@Column(name="BRANCHEDPASSAGE_JOB")
	public String getBranchedpassageJob() {
		return this.branchedpassageJob;
	}

	public void setBranchedpassageJob(String branchedpassageJob) {
		this.branchedpassageJob = branchedpassageJob;
	}

	public String getSecretary() {
		return this.secretary;
	}

	public void setSecretary(String secretary) {
		this.secretary = secretary;
	}

	@Column(name="OFFICE_SPACE")
	public String getOfficeSpace() {
		return this.officeSpace;
	}

	public void setOfficeSpace(String officeSpace) {
		this.officeSpace = officeSpace;
	}

	@Column(name="OUT_EMAIL")
	public String getOutEmail() {
		return this.outEmail;
	}

	public void setOutEmail(String outEmail) {
		this.outEmail = outEmail;
	}

	@Column(name="STAFF_ID")
	public String getStaffId() {
		return this.staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	} 
	
	@Transient
	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}
	@Transient
	@Column(name="JG_ID")
	public String getJgId() {
		return jgId;
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

	@Transient
	public int getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(int isadmin) {
		this.isadmin = isadmin;
	}
	
	@Column(name="doc_role_ids")
	public String getDocRoleIds() {
		return docRoleIds;
	}

	public void setDocRoleIds(String docRoleIds) {
		this.docRoleIds = docRoleIds;
	}
	@Column(name="ddqm_path")
	public String getDdqmPath() {
		return ddqmPath;
	}

	public void setDdqmPath(String ddqmPath) {
		this.ddqmPath = ddqmPath;
	}
	@Transient
	public Integer getAccess()
	{
		return access;
	}

	public void setAccess(Integer access)
	{
		this.access = access;
	}
	
	
	
	

}
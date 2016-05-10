package com.izhbg.typz.sso.audit.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_xt_audit_log")
public class AuditLog
{
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")   
	private String id;
	private String description;
	private String method;
	private Integer type;
	@Column(name="request_ip")
	private String requestIp;
	@Column(name="exception_code")
	private String exceptionCode;
	@Column(name="exception_detail")
	private String exceptionDetail;
	private String params;
	@Column(name="create_by")
	private String createBy;
	@Column(name="create_date")
	private Date createDate;
	@Column(name="app_id")
	private String appId;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getMethod()
	{
		return method;
	}
	public void setMethod(String method)
	{
		this.method = method;
	}
	public Integer getType()
	{
		return type;
	}
	public void setType(Integer type)
	{
		this.type = type;
	}
	public String getRequestIp()
	{
		return requestIp;
	}
	public void setRequestIp(String requestIp)
	{
		this.requestIp = requestIp;
	}
	public String getExceptionCode()
	{
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode)
	{
		this.exceptionCode = exceptionCode;
	}
	public String getExceptionDetail()
	{
		return exceptionDetail;
	}
	public void setExceptionDetail(String exceptionDetail)
	{
		this.exceptionDetail = exceptionDetail;
	}
	public String getParams()
	{
		return params;
	}
	public void setParams(String params)
	{
		this.params = params;
	}
	public String getCreateBy()
	{
		return createBy;
	}
	public void setCreateBy(String createBy)
	{
		this.createBy = createBy;
	}
	public Date getCreateDate()
	{
		return createDate;
	}
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}
	public String getAppId()
	{
		return appId;
	}
	public void setAppId(String appId)
	{
		this.appId = appId;
	}
	
	
}

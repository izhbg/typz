package com.izhbg.typz.sso.auth.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_xt_authorities")
public class TXtAuthorities
{
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")   
	@Column(name="authority_id")
	private String authorityId;
	@Column(name="authority_name")
	private String authorityName;
	@Column(name="authority_desc")
	private String authorityDesc;
	private Integer enabled;
	private Integer issys;
	private String module;
	@Column(name="app_id")
	private String appId;
	 
	public String getAuthorityId()
	{
		return authorityId;
	}
	public void setAuthorityId(String authorityId)
	{
		this.authorityId = authorityId;
	}
	public String getAuthorityName()
	{
		return authorityName;
	}
	public void setAuthorityName(String authorityName)
	{
		this.authorityName = authorityName;
	}
	public String getAuthorityDesc()
	{
		return authorityDesc;
	}
	public void setAuthorityDesc(String authorityDesc)
	{
		this.authorityDesc = authorityDesc;
	}
	public Integer getEnabled()
	{
		return enabled;
	}
	public void setEnabled(Integer enabled)
	{
		this.enabled = enabled;
	}
	public Integer getIssys()
	{
		return issys;
	}
	public void setIssys(Integer issys)
	{
		this.issys = issys;
	}
	public String getModule()
	{
		return module;
	}
	public void setModule(String module)
	{
		this.module = module;
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

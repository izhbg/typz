package com.izhbg.typz.sso.auth.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_xt_resources")
public class TXtResources
{
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	@Column(name="resource_id")
	private String resourceId;
	@Column(name="resource_name")
	private String resourceName;
	@Column(name="resource_desc")
	private String resourceDesc;
	@Column(name="resource_type")
	private String resourceType;
	@Column(name="resource_string")
	private String resourceString;
	private Integer priority;
	private Integer enabled;
	private Integer issys;
	private String module;
	
	public String getResourceId()
	{
		return resourceId;
	}
	public void setResourceId(String resourceId)
	{
		this.resourceId = resourceId;
	}
	public String getResourceName()
	{
		return resourceName;
	}
	public void setResourceName(String resourceName)
	{
		this.resourceName = resourceName;
	}
	public String getResourceDesc()
	{
		return resourceDesc;
	}
	public void setResourceDesc(String resourceDesc)
	{
		this.resourceDesc = resourceDesc;
	}
	public String getResourceType()
	{
		return resourceType;
	}
	public void setResourceType(String resourceType)
	{
		this.resourceType = resourceType;
	}
	public String getResourceString()
	{
		return resourceString;
	}
	public void setResourceString(String resourceString)
	{
		this.resourceString = resourceString;
	}
	public Integer getPriority()
	{
		return priority;
	}
	public void setPriority(Integer priority)
	{
		this.priority = priority;
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
	
	
  
  
}

package com.izhbg.typz.sso.auth.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="t_xt_authorities_resources")
public class TXtAuthoritiesResources
{
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")   
	@Column(name="id")
	private String id;
	@Column(name="authority_id")
	private String authorityId;
	@Column(name="resource_id")
	private String resourceId;
	private Integer enabled;
	@Transient
	private TXtResources resources;
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getAuthorityId()
	{
		return authorityId;
	}
	public void setAuthorityId(String authorityId)
	{
		this.authorityId = authorityId;
	}
	public String getResourceId()
	{
		return resourceId;
	}
	public void setResourceId(String resourceId)
	{
		this.resourceId = resourceId;
	}
	public Integer getEnabled()
	{
		return enabled;
	}
	public void setEnabled(Integer enabled)
	{
		this.enabled = enabled;
	}
	public TXtResources getResources()
	{
		return resources;
	}
	public void setResources(TXtResources resources)
	{
		this.resources = resources;
	}
	
}

package com.izhbg.typz.sso.auth.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_xt_gnjs_authorities")
public class TXtGnjsAuthorities
{
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")   
	@Column(name="id")
	private String id;
	@Column(name="role_id")
	private String roleId;
	@Column(name="authority_id")
	private String authorityId;
	private Integer enabled;
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getRoleId()
	{
		return roleId;
	}
	public void setRoleId(String roleId)
	{
		this.roleId = roleId;
	}
	public String getAuthorityId()
	{
		return authorityId;
	}
	public void setAuthorityId(String authorityId)
	{
		this.authorityId = authorityId;
	}
	public Integer getEnabled()
	{
		return enabled;
	}
	public void setEnabled(Integer enabled)
	{
		this.enabled = enabled;
	}
	
	
}

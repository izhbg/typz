package com.izhbg.typz.sso.auth.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.izhbg.typz.sso.auth2.dto.OauthClientDetails;

@Entity
@Table(name="t_xt_gnjs_oauth")
public class TXtGnjsOauth
{
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")   
	@Column(name="id")
	private String id;
	@Column(name="role_id")
	private String roleId;
	@Column(name="client_id")
	private String clientId;
	private Integer enabled;
	@Transient
	private OauthClientDetails oauthClientDetails;
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
	public Integer getEnabled()
	{
		return enabled;
	}
	public void setEnabled(Integer enabled)
	{
		this.enabled = enabled;
	}
	public String getClientId() {
		return clientId;
	}
	public OauthClientDetails getOauthClientDetails() {
		return oauthClientDetails;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public void setOauthClientDetails(OauthClientDetails oauthClientDetails) {
		this.oauthClientDetails = oauthClientDetails;
	}
	
	
}

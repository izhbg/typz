package com.izhbg.typz.sso.auth;

import java.util.List;
import java.util.Map;


public class UserAuthDTO {
    private String id;
    private String appId;
    private String username;
    private String displayName;
    private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
    private List<Map<String, Object>> permissions;
    private List<Map<String, Object>> roles;
    private String password;
    
    
    private String depId;
    private String depName;
    
    private Object treeNode;
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }



    public List<Map<String, Object>> getRoles() {
        return roles;
    }

    public void setRoles(List<Map<String, Object>> roles) {
        this.roles = roles;
    }


	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public Object getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(Object treeNode) {
		this.treeNode = treeNode;
	}

	public List<Map<String, Object>> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Map<String, Object>> permissions) {
		this.permissions = permissions;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public boolean isAccountNonExpired()
	{
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired)
	{
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked()
	{
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked)
	{
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired()
	{
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired)
	{
		this.credentialsNonExpired = credentialsNonExpired;
	}
	
    
    
}

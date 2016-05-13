package com.izhbg.typz.base.commondto;

public class TreeObject {
	private String id;
	private String name;
	private String pid;
	private String icons;
	private String checkType;
	private String isCheckType;
	
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getIsCheckType() {
		return isCheckType;
	}
	public void setIsCheckType(String isCheckType) {
		this.isCheckType = isCheckType;
	}
	public String getIcons() {
		return icons;
	}
	public void setIcons(String icons) {
		this.icons = icons;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeObject other = (TreeObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pid == null) {
			if (other.pid != null)
				return false;
		} else if (!pid.equals(other.pid))
			return false;
		return true;
	}
	
}

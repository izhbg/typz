package com.izhbg.typz.database.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_DATABASE_MENUINFO")
public class MenuInfo {
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned") 
	private Long id;//
	private String name;//
	private String url;//
	private Long pid;//
	@Column(name="APP_ID")
	private String appId;
	private String ischecked;//
	
	public Long getId() {
		return id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getIschecked() {
		return ischecked;
	}
	public void setIschecked(String ischecked) {
		this.ischecked = ischecked;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((ischecked == null) ? 0 : ischecked.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pid == null) ? 0 : pid.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		MenuInfo other = (MenuInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ischecked == null) {
			if (other.ischecked != null)
				return false;
		} else if (!ischecked.equals(other.ischecked))
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
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
	
}

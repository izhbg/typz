package com.izhbg.typz.database.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_DATABASE_MAINTABLETYPE")
public class MainTableType {
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	private String id;
	private String pid;
	private String name;
	private Integer level;
	private String other;
	@Column(name="APP_ID")
	private String appId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	
}

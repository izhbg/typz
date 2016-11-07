package com.izhbg.typz.shop.member.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_sh_member database table.
 * 
 */
@Entity
@Table(name="t_sh_member")
@NamedQuery(name="TShMember.findAll", query="SELECT t FROM TShMember t")
public class TShMember implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date age;

	private double la;

	private double lo;

	private String phone;

	private int sex;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	private String username;
	private Integer type;

	public TShMember() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getAge() {
		return this.age;
	}

	public void setAge(Date age) {
		this.age = age;
	}

	public double getLa() {
		return this.la;
	}

	public void setLa(double la) {
		this.la = la;
	}

	public double getLo() {
		return this.lo;
	}

	public void setLo(double lo) {
		this.lo = lo;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getSex() {
		return this.sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}
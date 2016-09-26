package com.izhbg.typz.shop.person.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_sh_person_address database table.
 * 
 */
@Entity
@Table(name="t_sh_person_address")
@NamedQuery(name="TShPersonAddress.findAll", query="SELECT t FROM TShPersonAddress t")
public class TShPersonAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="district_id")
	private String districtId;

	@Column(name="is_enable")
	private int isEnable;

	private String other;

	@Column(name="shr_name")
	private String shrName;

	@Column(name="shr_phone")
	private String shrPhone;

	@Column(name="shr_xxdz")
	private String shrXxdz;

	@Column(name="shr_yb")
	private String shrYb;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	@Column(name="yh_id")
	private String yhId;

	public TShPersonAddress() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDistrictId() {
		return this.districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public int getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(int isEnable) {
		this.isEnable = isEnable;
	}

	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getShrName() {
		return this.shrName;
	}

	public void setShrName(String shrName) {
		this.shrName = shrName;
	}

	public String getShrPhone() {
		return this.shrPhone;
	}

	public void setShrPhone(String shrPhone) {
		this.shrPhone = shrPhone;
	}

	public String getShrXxdz() {
		return this.shrXxdz;
	}

	public void setShrXxdz(String shrXxdz) {
		this.shrXxdz = shrXxdz;
	}

	public String getShrYb() {
		return this.shrYb;
	}

	public void setShrYb(String shrYb) {
		this.shrYb = shrYb;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getYhId() {
		return this.yhId;
	}

	public void setYhId(String yhId) {
		this.yhId = yhId;
	}

}
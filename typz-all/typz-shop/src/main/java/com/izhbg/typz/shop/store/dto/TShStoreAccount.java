package com.izhbg.typz.shop.store.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_sh_store_account database table.
 * 
 */
@Entity
@Table(name="t_sh_store_account")
@NamedQuery(name="TShStoreAccount.findAll", query="SELECT t FROM TShStoreAccount t")
public class TShStoreAccount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="account_name")
	private String accountName;

	@Column(name="account_no")
	private String accountNo;

	@Column(name="account_type_id")
	private String accountTypeId;

	@Column(name="bank_id")
	private String bankId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="change_time")
	private Date changeTime;

	@Column(name="is_selected")
	private byte isSelected;

	private String other;

	@Column(name="store_id")
	private String storeId;

	@Column(name="sc_bj")
	private Integer scBj;
	
	public TShStoreAccount() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return this.accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountTypeId() {
		return this.accountTypeId;
	}

	public void setAccountTypeId(String accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public String getBankId() {
		return this.bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public Date getChangeTime() {
		return this.changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public byte getIsSelected() {
		return this.isSelected;
	}

	public void setIsSelected(byte isSelected) {
		this.isSelected = isSelected;
	}

	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getStoreId() {
		return this.storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public Integer getScBj() {
		return scBj;
	}

	public void setScBj(Integer scBj) {
		this.scBj = scBj;
	}

}
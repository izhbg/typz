package com.izhbg.typz.shop.store.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_sh_store_withdraw")
public class Withdraw {
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned") 
	private String id;
	private Double money;//提现额度
	@Column(name="ask_time")
	private Date askTime;//申请提现时间
	@Column(name="member_id")
	private String memberId;//申请用户
	private Integer state;//状态  1：待审核  申请提现   2：已提现  申请通过
	@Column(name="sh_time")
	private Date shTime;//提现时间
	private String other;//其他
	@Column(name="account_type_id")
	private String accountTypeId;//提现账户类型
	@Column(name="bank_id")
	private String bankId;//银行类型
	@Column(name="account_no")
	private String accountNo;//账号
	@Column(name="account_name")
	private String accountName;//持卡人姓名
	@Column(name="total_money")
	private Double totalModey;//可提现额度
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Date getAskTime() {
		return askTime;
	}
	public void setAskTime(Date askTime) {
		this.askTime = askTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getShTime() {
		return shTime;
	}
	public void setShTime(Date shTime) {
		this.shTime = shTime;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getAccountTypeId() {
		return accountTypeId;
	}
	public void setAccountTypeId(String accountTypeId) {
		this.accountTypeId = accountTypeId;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Double getTotalModey() {
		return totalModey;
	}
	public void setTotalModey(Double totalModey) {
		this.totalModey = totalModey;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	
}

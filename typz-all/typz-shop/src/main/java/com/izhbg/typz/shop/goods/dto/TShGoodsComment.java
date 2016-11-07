package com.izhbg.typz.shop.goods.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.izhbg.typz.shop.member.dto.TShMember;
import com.mysql.fabric.xmlrpc.base.Member;

@Entity
@Table(name="t_sh_goods_comment")
@NamedQuery(name="TShGoodsComment.findAll", query="SELECT t FROM TShGoodsComment t")
public class TShGoodsComment implements Serializable{
	 /**
	 * 
	 */
	 private static final long serialVersionUID = 1L;
	 @Id
	 private String id;
	 @Column(name="goods_id")
	 private String goodsId;
	 @Column(name="is_anonymous")
	 private Integer isAnonymous;
	 private Date time;
	 @Column(name="order_id")
	 private String orderId;
	 @Column(name="member_id")
	 private String memberId;
	 private Integer grade;
	 private String content;
	 @Transient
	 private  TShMember member;
	 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIsAnonymous() {
		return isAnonymous;
	}
	public void setIsAnonymous(Integer isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	public TShMember getMember() {
		return member;
	}
	public void setMember(TShMember member) {
		this.member = member;
	}
	 
	 
}

package com.izhbg.typz.im.person.admin.entity;
/**
 * 登陆注册实体
* @author xiaolong.cai@mtime.com
* @date 2016年10月2日 上午11:09:15 
* @version V1.0
 */
public class UserInfo {
	private String phone;//手机号
	private String codeId;//验证码ID
	private String code;//验证码
	private double la;//维度
	private double lo;//经度
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public double getLa() {
		return la;
	}
	public void setLa(double la) {
		this.la = la;
	}
	public double getLo() {
		return lo;
	}
	public void setLo(double lo) {
		this.lo = lo;
	}
	
	
}

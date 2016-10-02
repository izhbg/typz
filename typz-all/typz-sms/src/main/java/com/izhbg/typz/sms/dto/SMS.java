package com.izhbg.typz.sms.dto;
/**
 * 发送短信的实体
* @author xiaolong.cai@mtime.com
* @date 2016年10月1日 下午9:43:50 
* @version V1.0
 */
public class SMS {
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 验证码
	 */
	private int code;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	
}

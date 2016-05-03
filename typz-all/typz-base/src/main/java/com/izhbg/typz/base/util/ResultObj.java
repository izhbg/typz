package com.izhbg.typz.base.util;

import java.io.Serializable;

public class ResultObj implements Serializable{
	private Integer code;
	private String description;
	private Object content;
	public ResultObj(){
	}
	public ResultObj(Integer code, String description){
		this.code = code;
		this.description = description;
	}
	public ResultObj(Integer code, String description, String content){
		this.code = code;
		this.description = description;
		this.content = content;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
}

package com.izhbg.typz.database.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="T_DATABASE_MAINTABLECHART")
public class MainTableChart {
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned") 
	@Column(name="CHART_ID")
	private Long chartid;
	@Column(name="TABLE_ID")
	private Long tableid;
	@Column(name="CHART_TITLE")
	private String charttitle;
	@Column(name="XAXIS_NAME")
	private String axisNameX;
	@Column(name="YAXIS_NAME")
	private String axisNameY;
	@Column(name="SERIES_NAME")
	private String seriesName;
	@Column(name="XAXIS_VALUE")
	private String axisvalueX;
	@Column(name="YAXIS_VALUE")
	private String axisvalueY;
	@Column(name="ISEXTEND")
	private String isExtend;
	@Column(name="EXTENDSQL")
	private String extendSql;
	@Column(name="APP_ID")
	private String appId;
	public Long getChartid() {
		return chartid;
	}
	public void setChartid(Long chartid) {
		this.chartid = chartid;
	}
	
	public Long getTableid() {
		return tableid;
	}
	public void setTableid(Long tableid) {
		this.tableid = tableid;
	}
	public String getCharttitle() {
		return charttitle;
	}
	public void setCharttitle(String charttitle) {
		this.charttitle = charttitle;
	}
	
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	
	public String getAxisNameX() {
		return axisNameX;
	}
	public void setAxisNameX(String axisNameX) {
		this.axisNameX = axisNameX;
	}
	public String getAxisNameY() {
		return axisNameY;
	}
	public void setAxisNameY(String axisNameY) {
		this.axisNameY = axisNameY;
	}
	public String getAxisvalueX() {
		return axisvalueX;
	}
	public void setAxisvalueX(String axisvalueX) {
		this.axisvalueX = axisvalueX;
	}
	public String getAxisvalueY() {
		return axisvalueY;
	}
	public void setAxisvalueY(String axisvalueY) {
		this.axisvalueY = axisvalueY;
	}
	public String getIsExtend() {
		return isExtend;
	}
	public void setIsExtend(String isExtend) {
		this.isExtend = isExtend;
	}
	public String getExtendSql() {
		return extendSql;
	}
	public void setExtendSql(String extendSql) {
		this.extendSql = extendSql;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	
}

package com.izhbg.typz.database.dto;

import java.util.List;
/**
 * 查询模板
* @author caixl 
* @date 2016-5-13 上午9:20:28 
*
 */
public class QueryPanel {
	private String name;
	private String cname;
	private String value;
	private String type;
	private List lists;
	private String startdate;
	private String enddate;
	private MainTableColumn mainTableColumn;
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public List getLists() {
		return lists;
	}
	public void setLists(List lists) {
		this.lists = lists;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public MainTableColumn getMainTableColumn() {
		return mainTableColumn;
	}
	public void setMainTableColumn(MainTableColumn mainTableColumn) {
		this.mainTableColumn = mainTableColumn;
	}
	
}

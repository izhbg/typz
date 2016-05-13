package com.izhbg.typz.database.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="T_DATABASE_MAINTABLE")
public class MainTable {
	@Id
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned") 
	@Column(name="TABLE_ID")
	private Long tableid;//表的id，自己定义
	@Column(name="CLASS_NAME")
	private String className;//类名
	@Column(name="CLASS_PATH")
	private String classPath;//类路径
	@Column(name="TABLE_REAL_NAME")
	private String tableRealName;//数据真正表名，数据表名可以不相同
	@Column(name="TABLE_NAME")
	private String tableName;//数据表名，用来获取对应表
	@Column(name="TABLE_CNAME")
	private String tableCName;//数据表中文名
	@Column(name="TABLESQL")
	private String sql;
	@Column(name="FORMAT_TYPE")
	private String formatType;//格式类型，0，表，1图，2图表，默认表
	@Column(name="ISMULTI")
	private String isMulti;//是否是需要多表拼接
	@Column(name="MULTISQL")
	private String multiSql;//多表查询获取表名的sql
	@Column(name="KEY_COLUMN_NAME")
	private String keyColumnName;//唯一标识的字段名
	@Column(name="ISPUTTEMP")
	private String isPutTemp;
	@Column(name="TYPE")
	private String type;
	@Column(name="APP_ID")
	private String appId;
	
	
	public String getIsPutTemp() {
		return isPutTemp;
	}
	public void setIsPutTemp(String isPutTemp) {
		this.isPutTemp = isPutTemp;
	}
	public String getTableRealName() {
		return tableRealName;
	}
	public void setTableRealName(String tableRealName) {
		this.tableRealName = tableRealName;
	}
	public String getKeyColumnName() {
		return keyColumnName;
	}
	public void setKeyColumnName(String keyColumnName) {
		this.keyColumnName = keyColumnName;
	}
	public String getIsMulti() {
		return isMulti;
	}
	public void setIsMulti(String isMulti) {
		this.isMulti = isMulti;
	}
	public String getMultiSql() {
		return multiSql;
	}
	public void setMultiSql(String multiSql) {
		this.multiSql = multiSql;
	}
	public String getFormatType() {
		return formatType;
	}
	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Long getTableid() {
		return tableid;
	}
	public void setTableid(Long tableid) {
		this.tableid = tableid;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassPath() {
		return classPath;
	}
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableCName() {
		return tableCName;
	}
	public void setTableCName(String tableCName) {
		this.tableCName = tableCName;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((className == null) ? 0 : className.hashCode());
		result = prime * result
				+ ((classPath == null) ? 0 : classPath.hashCode());
		result = prime * result
				+ ((formatType == null) ? 0 : formatType.hashCode());
		result = prime * result + ((isMulti == null) ? 0 : isMulti.hashCode());
		result = prime * result
				+ ((keyColumnName == null) ? 0 : keyColumnName.hashCode());
		result = prime * result
				+ ((multiSql == null) ? 0 : multiSql.hashCode());
		result = prime * result + ((sql == null) ? 0 : sql.hashCode());
		result = prime * result
				+ ((tableCName == null) ? 0 : tableCName.hashCode());
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
		result = prime * result + ((tableid == null) ? 0 : tableid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MainTable other = (MainTable) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (classPath == null) {
			if (other.classPath != null)
				return false;
		} else if (!classPath.equals(other.classPath))
			return false;
		if (formatType == null) {
			if (other.formatType != null)
				return false;
		} else if (!formatType.equals(other.formatType))
			return false;
		if (isMulti == null) {
			if (other.isMulti != null)
				return false;
		} else if (!isMulti.equals(other.isMulti))
			return false;
		if (keyColumnName == null) {
			if (other.keyColumnName != null)
				return false;
		} else if (!keyColumnName.equals(other.keyColumnName))
			return false;
		if (multiSql == null) {
			if (other.multiSql != null)
				return false;
		} else if (!multiSql.equals(other.multiSql))
			return false;
		if (sql == null) {
			if (other.sql != null)
				return false;
		} else if (!sql.equals(other.sql))
			return false;
		if (tableCName == null) {
			if (other.tableCName != null)
				return false;
		} else if (!tableCName.equals(other.tableCName))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		if (tableid == null) {
			if (other.tableid != null)
				return false;
		} else if (!tableid.equals(other.tableid))
			return false;
		return true;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
}

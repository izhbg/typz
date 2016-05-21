package com.izhbg.typz.database.uitl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.ListOrderedMap;
import org.hibernate.annotations.common.util.StringHelper;

import com.izhbg.typz.base.util.DateUtil;
import com.izhbg.typz.base.util.PropertiesCommonUtil;
import com.izhbg.typz.database.dto.MainTable;
import com.izhbg.typz.database.dto.MainTableColumn;
import com.izhbg.typz.database.dto.QueryPanel;
import com.izhbg.typz.database.sql.service.SelectDataService;
import com.izhbg.typz.sso.auth.UserAuthDTO;
import com.izhbg.typz.sso.util.SpringContextWrapper;
import com.izhbg.typz.sso.util.SpringSecurityUtils;

/**
 * 
 * @author caixl
 * @date 2016-5-13 上午10:19:06
 * 
 */
public class SqlUtil {

	public static String getSql(List<QueryPanel> viewList,
			List<QueryPanel> queryList, MainTable maintable) {
		String selectsql = "select ";
		for (int i = 0; i < viewList.size(); i++) {
			QueryPanel qp = viewList.get(i);
			if (i == 0) {
				selectsql += "tables." + qp.getName();
			} else {
				selectsql += ",tables." + qp.getName();
			}
		}
		selectsql += " from (" + maintable.getSql() + ") tables ";
		int flag = 0;
		for (int i = 0; i < queryList.size(); i++) {
			QueryPanel qp = (QueryPanel) queryList.get(i);
			if (qp.getValue() != null && qp.getValue().length() > 0) {
				if (flag == 0) {
					selectsql += "where tables." + qp.getName() + "='"
							+ qp.getValue() + "'";
					flag = 1;
				} else {
					selectsql += " and tables." + qp.getName() + "='"
							+ qp.getValue() + "'";
				}
			}
		}
		return selectsql;
	}

	public static String getOracleSql(List<QueryPanel> viewList,
			List<QueryPanel> queryList, MainTable maintable, String newSql)
			throws Exception {
		String selectsql = "select rownum as oid,tables.* ";
		SelectDataService selectDataService = (SelectDataService) SpringContextWrapper
				.getBean("selectDataService");
		if (maintable.getIsMulti().equals("1")) {
			List list = selectDataService.getData(maintable.getMultiSql());
			String mainsql = "";
			for (Object obj : list) {
				ListOrderedMap map = (ListOrderedMap) obj;
				String tablename = map.get("tablename").toString();
				if (mainsql.length() == 0) {
					mainsql = newSql.replace(maintable.getTableName(),
							tablename);
				} else {
					mainsql += " union all "
							+ newSql.replace(maintable.getTableName(),
									tablename);
				}
			}
			selectsql += " from (" + mainsql + ") tables ";
		} else {
			selectsql += " from (" + newSql + ") tables ";
		}
		int flag = 0;//
		for (int i = 0; i < queryList.size(); i++) {
			QueryPanel qp = (QueryPanel) queryList.get(i);
			if (qp.getType().equals("5")) {
				if (qp.getMainTableColumn().getDataType().equals("3")) {
					if (qp.getStartdate() != null
							&& qp.getStartdate().length() > 0) {
						if (flag == 0) {
							selectsql += "where tables." + qp.getName()
									+ ">= TO_DATE('" + qp.getStartdate()
									+ "','yyyy-mm-dd hh24:mi:ss')";
							flag = 1;
						} else {
							selectsql += "and tables." + qp.getName()
									+ ">= TO_DATE('" + qp.getStartdate()
									+ "','yyyy-mm-dd hh24:mi:ss')";
						}
					}
					if (qp.getEnddate() != null && qp.getEnddate().length() > 0) {
						if (flag == 0) {
							selectsql += "where tables." + qp.getName()
									+ "<= TO_DATE('" + qp.getEnddate()
									+ "','yyyy-mm-dd hh24:mi:ss')";
							flag = 1;
						} else {
							selectsql += "and tables." + qp.getName()
									+ "<= TO_DATE('" + qp.getEnddate()
									+ "','yyyy-mm-dd hh24:mi:ss')";
						}
					}
				} else {
					if (qp.getValue() != null && qp.getValue().length() > 0) {
						if (flag == 0) {
							selectsql += "where tables." + qp.getName()
									+ " = '" + qp.getValue() + "'";
							flag = 1;
						} else {
							selectsql += "and tables." + qp.getName() + " = '"
									+ qp.getValue() + "'";
						}
					}
				}

			} else if (qp.getType().equals("10")) {
				if (qp.getMainTableColumn().getDataType().equals("3")) {
					if (qp.getStartdate() != null
							&& qp.getStartdate().length() > 0) {
						if (flag == 0) {
							selectsql += "where tables." + qp.getName()
									+ ">= TO_DATE('" + qp.getStartdate()
									+ "','yyyy-mm-dd')";
							flag = 1;
						} else {
							selectsql += "and tables." + qp.getName()
									+ ">= TO_DATE('" + qp.getStartdate()
									+ "','yyyy-mm-dd')";
						}
					}
					if (qp.getEnddate() != null && qp.getEnddate().length() > 0) {
						String enddate = DateUtil.convertDateToString(DateUtil
								.addDays(DateUtil.convertStringToDate(qp
										.getEnddate()), 1));
						if (flag == 0) {
							selectsql += "where tables." + qp.getName()
									+ "<= TO_DATE('" + qp.getEnddate()
									+ "','yyyy-mm-dd')";
							flag = 1;
						} else {
							selectsql += "and tables." + qp.getName()
									+ "<= TO_DATE('" + enddate
									+ "','yyyy-mm-dd')";
						}
					}
				} else {
					if (qp.getValue() != null && qp.getValue().length() > 0) {
						if (flag == 0) {
							selectsql += "where tables." + qp.getName()
									+ " = '" + qp.getValue() + "'";
							flag = 1;
						} else {
							selectsql += "and tables." + qp.getName() + " = '"
									+ qp.getValue() + "'";
						}
					}
				}
			} else {
				if (qp.getMainTableColumn().getDataType().equals("2")) {
					if (qp.getValue() != null && qp.getValue().length() > 0) {
						if (flag == 0) {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else if ("4".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							} else {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							}
							flag = 1;
						} else {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else if ("4".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							} else {
								selectsql += "and tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							}
						}
					}
				} else {
					if (qp.getValue() != null && qp.getValue().length() > 0) {
						if (flag == 0) {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else if ("4".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							} else {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							}
							flag = 1;
						} else {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else if ("4".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							} else {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							}
						}
					}
				}
			}
		}
		return selectsql;
	}

	public static String getOracleSqlForPage(String sql, int page, int pagesize) {
		String sqlpage = "select * from (" + sql
				+ ") tables1 where tables1.oid>=" + ((page - 1) * pagesize + 1)
				+ " and tables1.oid<=" + page * pagesize;

		return sqlpage;
	}

	public static String getMySql(List viewList, List queryList,
			MainTable maintable, String newSql) throws Exception {
		String selectsql = "select tables.* ";
		SelectDataService selectDataService = (SelectDataService) SpringContextWrapper
				.getBean("selectDataService");
		if (maintable.getIsMulti().equals("1")) {
			List list = selectDataService.getData(maintable.getMultiSql());
			String mainsql = "";
			for (Object obj : list) {
				ListOrderedMap map = (ListOrderedMap) obj;
				String tablename = map.get("tablename").toString();
				if (mainsql.length() == 0) {
					mainsql = newSql.replace(maintable.getTableName(),
							tablename);
				} else {
					mainsql += " union all "
							+ newSql.replace(maintable.getTableName(),
									tablename);
				}
			}
			selectsql += " from (" + mainsql + ") tables ";
		} else {
			selectsql += " from (" + newSql + ") tables ";
		}
		int flag = 0;//
		for (int i = 0; i < queryList.size(); i++) {
			QueryPanel qp = (QueryPanel) queryList.get(i);
			if (qp.getType().equals("5") || qp.getType().equals("10")) {
				if (qp.getStartdate() != null && qp.getStartdate().length() > 0) {
					if (qp.getStartdate() != null
							&& qp.getStartdate().length() > 0) {
						if (flag == 0) {
							selectsql += "where UNIX_TIMESTAMP(tables."
									+ qp.getName() + ") >= UNIX_TIMESTAMP('"
									+ qp.getStartdate() + "') ";
							flag = 1;
						} else {
							selectsql += "and UNIX_TIMESTAMP(tables."
									+ qp.getName() + ") >= UNIX_TIMESTAMP('"
									+ qp.getStartdate() + "') ";
						}
					}
					if (qp.getEnddate() != null && qp.getEnddate().length() > 0) {
						if (flag == 0) {
							selectsql += "where UNIX_TIMESTAMP(tables."
									+ qp.getName() + ") <= UNIX_TIMESTAMP('"
									+ qp.getEnddate() + "') ";
							flag = 1;
						} else {
							selectsql += "and UNIX_TIMESTAMP(tables."
									+ qp.getName() + ") <= UNIX_TIMESTAMP('"
									+ qp.getEnddate() + "') ";
						}
					}
				}
			} else {
				if (qp.getMainTableColumn().getDataType().equals("2")) {
					if (qp.getValue() != null && qp.getValue().length() > 0) {
						if (flag == 0) {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else if ("4".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							} else {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							}
							flag = 1;
						} else {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else if ("4".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							} else {
								selectsql += "and tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							}
						}
					}
				} else {
					if (qp.getValue() != null && qp.getValue().length() > 0) {
						if (flag == 0) {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else if ("4".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							} else {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							}
							flag = 1;
						} else {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else if ("4".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							} else {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							}
						}
					}
				}
			}

		}
		return selectsql;
	}

	public static String getMySqlForPage(String sql, int page, int pagesize) {
		String sqlpage = sql + " limit " + ((page - 1) * pagesize) + ","
				+ pagesize;
		return sqlpage;
	}

	public static String getServerSql(List viewList, List queryList,
			MainTable maintable) throws Exception {
		String selectsql = "select tables.* ";
		SelectDataService selectDataService = (SelectDataService) SpringContextWrapper
				.getBean("selectDataService");
		if (maintable.getIsMulti().equals("1")) {
			List list = selectDataService.getData(maintable.getMultiSql());
			String mainsql = "";
			for (Object obj : list) {
				ListOrderedMap map = (ListOrderedMap) obj;
				String tablename = map.get("tablename").toString();
				if (mainsql.length() == 0) {
					mainsql = maintable.getSql().replace(
							maintable.getTableName(), tablename);
				} else {
					mainsql += " union all "
							+ maintable.getSql().replace(
									maintable.getTableName(), tablename);
				}
			}
			selectsql += " from (" + mainsql + ") tables ";
		} else {
			selectsql += " from (" + maintable.getSql() + ") tables ";
		}
		int flag = 0;// ��ʾ��û��ѯ���
		for (int i = 0; i < queryList.size(); i++) {
			QueryPanel qp = (QueryPanel) queryList.get(i);
			if (qp.getType().equals("5") || qp.getType().equals("10")) {
				if (qp.getStartdate() != null && qp.getStartdate().length() > 0) {
					if (qp.getStartdate() != null
							&& qp.getStartdate().length() > 0) {
						if (flag == 0) {
							selectsql += "where tables." + qp.getName()
									+ " >= '" + qp.getStartdate() + "' ";
							flag = 1;
						} else {
							selectsql += "and tables." + qp.getName() + " >= '"
									+ qp.getStartdate() + "' ";
						}
					}
					if (qp.getEnddate() != null && qp.getEnddate().length() > 0) {
						if (flag == 0) {
							selectsql += "where tables." + qp.getName()
									+ " <= '" + qp.getEnddate() + "' ";
							flag = 1;
						} else {
							selectsql += "and tables." + qp.getName() + " <= '"
									+ qp.getEnddate() + "' ";
						}
					}
				}
			} else {
				if (qp.getMainTableColumn().getDataType().equals("2")) {
					if (qp.getValue() != null && qp.getValue().length() > 0) {
						if (flag == 0) {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else if ("4".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							} else {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							}
							flag = 1;
						} else {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else if ("4".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							} else {
								selectsql += "and tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							}
						}
					}
				} else {
					if (qp.getValue() != null && qp.getValue().length() > 0) {
						if (flag == 0) {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else if ("4".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							} else {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							}
							flag = 1;
						} else {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else if ("4".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							} else {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							}
							// selectsql+="and tables."+qp.getName()+" like '%"+qp.getValue()+"%'";
						}
					}
				}
			}
		}
		return selectsql;
	}

	public static String getServerSqlForPage(String sql, int page,
			int pagesize, MainTable maintable) throws Exception {
		String sqlpage = "select top " + ((page) * pagesize)
				+ " tables3.* from (" + sql + ") tables3 where tables3."
				+ maintable.getKeyColumnName() + " not in (select top "
				+ ((page - 1) * pagesize) + " tables4."
				+ maintable.getKeyColumnName() + " from (" + sql + ") tables4)";
		return sqlpage;
	}

	public static String getDataSqlBySql(List viewList, List queryList,
			MainTable maintable, String newSql, int page, int pagesize)
			throws Exception {
		PropertiesCommonUtil propertiesUtil = new PropertiesCommonUtil(
				"application.properties");
		String databasetype = propertiesUtil
				.getProp("application.database.type");
		String sql = "";
		if (databasetype.indexOf("mysql") >= 0) {
			sql = getMySql(viewList, queryList, maintable, newSql);
			sql = getMySqlForPage(sql, page, pagesize);
		} else if (databasetype.indexOf("oracle") >= 0) {
			sql = getOracleSql(viewList, queryList, maintable, newSql);
			sql = getOracleSqlForPage(sql, page, pagesize);
		} else if (databasetype.indexOf("sqlserver") >= 0) {
			sql = getServerSql(viewList, queryList, maintable);
			sql = getServerSqlForPage(sql, page, pagesize, maintable);
		}
		UserAuthDTO user = (UserAuthDTO) SpringSecurityUtils.getCurrentUser();
		sql = sql.replaceAll("@CurrentUserName",
				SpringSecurityUtils.getCurrentUsername());
		sql = sql.replaceAll("@CurrentUserId",
				SpringSecurityUtils.getCurrentUserId());
		sql = sql.replaceAll("@CurrentAppId", user.getAppId());
		sql = sql.replaceAll("@CurrentDepId", user.getDepId());
		sql = sql.replaceAll("@CurrentDepName", user.getDepName());

		return sql;
	}

	public static String getMainSqlForPage(String sql, int page, int pagesize,
			MainTable maintable) throws Exception {
		PropertiesCommonUtil propertiesUtil = new PropertiesCommonUtil(
				"application.properties");
		String databasetype = propertiesUtil
				.getProp("application.database.type");
		String sqlpage = "";
		if (databasetype.indexOf("mysql") >= 0) {
			sqlpage = getMySqlForPage(sql, page, pagesize);
		} else if (databasetype.indexOf("oracle") >= 0) {
			sqlpage = getOracleSqlForPage(sql, page, pagesize);
		} else if (databasetype.indexOf("sqlserver") >= 0) {
			sqlpage = getServerSqlForPage(sql, page, pagesize, maintable);
		}
		return sqlpage;
	}

	public static String getMainSql(List viewList, List queryList,
			MainTable maintable, String newSql) throws Exception {
		PropertiesCommonUtil propertiesUtil = new PropertiesCommonUtil(
				"application.properties");
		String databasetype = propertiesUtil
				.getProp("application.database.type");
		String sql = "";
		if (databasetype.indexOf("mysql") >= 0) {
			sql = getMySql(viewList, queryList, maintable, newSql);
		} else if (databasetype.indexOf("oracle") >= 0) {
			sql = getOracleSql(viewList, queryList, maintable, newSql);
		} else if (databasetype.indexOf("sqlserver") >= 0) {
			sql = getServerSql(viewList, queryList, maintable);
		}
		return sql;
	}

	public static List<Map<String,Object>> getExtList(MainTableColumn maintablecolumn, String value)
			throws Exception {
		SelectDataService selectDataService = (SelectDataService) SpringContextWrapper
				.getBean("selectDataService");
		List<Map<String,Object>> list = null;
		if (maintablecolumn.getPropertyType().equals("8")) {
			if (value != null) {
				String[] ids = value.split(",");
				String rid = "";
				String rname = "";
				for (String id : ids) {
					String sql = maintablecolumn.getTypeSql();
					String[] sqls = sql.split("#");
					sql = sqls[0];
					sql = "select * from (" + sql + ") tables where tables."
							+ "id='" + id + "'";
					List<Map<String,Object>> list1 = selectDataService.getData(sql);
					if (list1 != null && list1.size() > 0) {
						Map<String,Object> map =  list1.get(0);
						rid += map.get("id") + ",";
						rname += map.get("name") + ",";
					}
				}
				if (rid.length() != 0
						&& rid.length() == rid.lastIndexOf(",") + 1) {
					rid = rid.substring(0, rid.length() - 1);
				}
				if (rname.length() != 0
						&& rname.length() == rname.lastIndexOf(",") + 1) {
					rname = rname.substring(0, rname.length() - 1);
				}
				list = new ArrayList<Map<String,Object>>();
				Map<String,Object> rmap = new HashMap<String,Object>();
				rmap.put("id", rid);
				rmap.put("name", rname);
				list.add(rmap);
			} else {
				list = selectDataService.getData(maintablecolumn.getTypeSql());
			}

		} else {
			if (maintablecolumn.getTypeSql().indexOf("#") != -1) {
				String sql = maintablecolumn.getTypeSql();
				sql = sql.substring(1, sql.length());
				String[] sqls = sql.split(",");
				list = new ArrayList<Map<String,Object>>();
				if (sqls[0].length() > 0) {
					for (String subsql : sqls) {
						String[] sql1 = subsql.split(":");
						Map<String,Object> map = new HashMap<String,Object>();
						map.put("id", sql1[0]);
						map.put("name", sql1[1]);
						list.add(map);
					}
				}

			} else {
				String sql = maintablecolumn.getTypeSql();
				if(StringHelper.isNotEmpty(sql))
				{
					sql=filterAuth(sql);
					list = selectDataService.getData(sql);
				}
			}
		}

		return list;
	}

	public static String getSqlForLoadColumn(MainTable maintable)
			throws Exception {
		SelectDataService selectDataService = (SelectDataService) SpringContextWrapper
				.getBean("selectDataService");
		String sql = "";
		if (maintable.getIsMulti().equals("1")) {
			List list = selectDataService.getData(maintable.getMultiSql());
			String mainsql = "";
			for (Object obj : list) {
				ListOrderedMap map = (ListOrderedMap) obj;
				String tablename = map.get("tablename").toString();
				if (mainsql.length() == 0) {
					mainsql = maintable.getSql().replace(
							maintable.getTableName(), tablename);
				} else {
					mainsql += " union all "
							+ maintable.getSql().replace(
									maintable.getTableName(), tablename);
				}
			}
			sql += mainsql;
		} else {
			sql += maintable.getSql();
		}
		return sql;
	}

	public static String getDeleteSql(String tableName, String key, String value) {
		String sql = "delete from " + tableName + " where " + key + "='"
				+ value + "'";
		return sql;
	}

	public static String getInsertSql(List detailList, MainTable maintable,
			boolean haskey) {
		PropertiesCommonUtil propertiesUtil = new PropertiesCommonUtil(
				"application.properties");
		String databasetype = propertiesUtil
				.getProp("application.database.type");
		String sql = "";
		// sql=getMySqlForInsert(detailList,maintable,haskey);
		if (databasetype.indexOf("mysql") >= 0) {
			sql = getMySqlForInsert(detailList, maintable, haskey);
		} else if (databasetype.indexOf("oracle") >= 0) {
			sql = getOrcalForInsert(detailList, maintable, haskey);
		} else if (databasetype.indexOf("sqlserver") >= 0) {
			sql = getServerForInsert(detailList, maintable, haskey);
		}
		return sql;
	}

	public static String getMySqlForInsert(List detailList,
			MainTable maintable, boolean haskey) {
		String sql = "";
		if (haskey == true) {
			sql += "UPDATE " + maintable.getTableRealName() + " SET ";
			String lastsql = "";
			for (int i = 0; i < detailList.size(); i++) {
				QueryPanel qp = (QueryPanel) detailList.get(i);
				if (qp.getMainTableColumn().getColumnName()
						.equals(maintable.getKeyColumnName())) {
					lastsql += "where "
							+ qp.getMainTableColumn().getColumnName() + "='"
							+ qp.getValue() + "'";
				} else {
					if (qp.getMainTableColumn().getDataType().equals("1")) {
						sql += qp.getMainTableColumn().getColumnName() + "='"
								+ qp.getValue() + "' ,";

					} else if (qp.getMainTableColumn().getDataType()
							.equals("2")) {
						if (qp.getValue() != null && qp.getValue() != "") {
							sql += qp.getMainTableColumn().getColumnName()
									+ "=" + qp.getValue() + " ,";
						} else {
							sql += qp.getMainTableColumn().getColumnName()
									+ "=" + 0 + " ,";
						}
					} else if (qp.getMainTableColumn().getDataType()
							.equals("3")) {
						if (qp.getMainTableColumn().getPropertyType()
								.equals("6")) {
							if (qp.getValue() != null
									&& qp.getValue().length() > 0) {
								sql += qp.getMainTableColumn().getColumnName()
										+ "='"
										+ DateUtil.getDateToStringFull(DateUtil
												.getStringToDateFull(qp
														.getValue())) + "',";
							} else {
								sql += qp.getMainTableColumn().getColumnName()
										+ "='"
										+ DateUtil
												.getDateToStringFull(new Date())
										+ "',";
							}
						} else {
							sql += qp.getMainTableColumn().getColumnName()
									+ "='" + qp.getValue() + "',";
						}
					} else {
						sql += qp.getMainTableColumn().getColumnName() + "='"
								+ qp.getValue() + "' ,";
					}
				}
			}
			sql = sql.substring(0, sql.length() - 1) + lastsql;

		} else {
			sql += "INSERT INTO " + maintable.getTableRealName() + " (";
			for (int i = 0; i < detailList.size(); i++) {
				QueryPanel qp = (QueryPanel) detailList.get(i);
				sql += qp.getMainTableColumn().getColumnName() + ",";
			}
			sql = sql.substring(0, sql.length() - 1) + ") values (";
			for (int i = 0; i < detailList.size(); i++) {
				QueryPanel qp = (QueryPanel) detailList.get(i);
				if (qp.getMainTableColumn().getColumnName()
						.equals(maintable.getKeyColumnName())) {
					sql += "'" + DateUtil.getdateAndRandom() + "',";
				} else {
					if (qp.getMainTableColumn().getDataType().equals("1")) {
						sql += "'" + qp.getValue() + "',";
					} else if (qp.getMainTableColumn().getDataType()
							.equals("2")) {
						sql += qp.getValue() + ",";
					} else if (qp.getMainTableColumn().getDataType()
							.equals("3")) {
						if (qp.getMainTableColumn().getPropertyType()
								.equals("6")) {
							if (qp.getValue() != null
									&& qp.getValue().length() > 0) {
								sql += "'" + qp.getValue() + "',";
							} else {
								sql += "'"
										+ DateUtil
												.getDateToStringFull(new Date())
										+ "',";
							}
						} else {
							sql += "'" + qp.getValue() + "',";
						}

					} else {
						sql += "'" + qp.getValue() + "',";
					}
				}
			}
			sql = sql.substring(0, sql.length() - 1) + ")";
		}
		return sql;
	}

	public static String getOrcalForInsert(List detailList,
			MainTable maintable, boolean haskey) {
		String sql = "";
		if (haskey == true) {
			sql += "UPDATE " + maintable.getTableRealName() + " SET ";
			String lastsql = "";
			for (int i = 0; i < detailList.size(); i++) {
				QueryPanel qp = (QueryPanel) detailList.get(i);
				if (qp.getMainTableColumn().getColumnName()
						.equals(maintable.getKeyColumnName())) {
					lastsql += "where "
							+ qp.getMainTableColumn().getColumnName() + "='"
							+ qp.getValue() + "'";
				} else {
					if (qp.getMainTableColumn().getDataType().equals("1")) {
						sql += qp.getMainTableColumn().getColumnName() + "='"
								+ qp.getValue() + "' ,";

					} else if (qp.getMainTableColumn().getDataType()
							.equals("2")) {
						if (qp.getValue() != null && qp.getValue() != "") {
							sql += qp.getMainTableColumn().getColumnName()
									+ "=" + qp.getValue() + " ,";
						} else {
							sql += qp.getMainTableColumn().getColumnName()
									+ "=null ,";
						}
					} else if (qp.getMainTableColumn().getDataType()
							.equals("3")) {
						if (qp.getMainTableColumn().getPropertyType()
								.equals("6")) {
							if (qp.getValue() != null
									&& qp.getValue().length() > 0) {
								sql += qp.getMainTableColumn().getColumnName()
										+ "=to_date('"
										+ qp.getValue().substring(0, 19)
										+ "','yyyy-mm-dd hh24:mi:ss'),";
							} else {
								sql += qp.getMainTableColumn().getColumnName()
										+ "=to_date('"
										+ DateUtil
												.getDateToStringFull(new Date())
										+ "','yyyy-mm-dd hh24:mi:ss'),";
							}
						} else if (qp.getMainTableColumn().getPropertyType()
								.equals("5")) {
							sql += qp.getMainTableColumn().getColumnName()
									+ "=to_date('" + qp.getValue()
									+ "','yyyy-mm-dd hh24:mi:ss'),";
						} else if (qp.getMainTableColumn().getPropertyType()
								.equals("10")) {
							sql += qp.getMainTableColumn().getColumnName()
									+ "=to_date('" + qp.getValue()
									+ "','yyyy-mm-dd'),";
						}
					}
				}
			}
			sql = sql.substring(0, sql.length() - 1) + lastsql;

		} else {
			sql += "INSERT INTO " + maintable.getTableRealName() + " (";
			for (int i = 0; i < detailList.size(); i++) {
				QueryPanel qp = (QueryPanel) detailList.get(i);
				sql += qp.getMainTableColumn().getColumnName() + ",";
			}
			sql = sql.substring(0, sql.length() - 1) + ") values (";
			for (int i = 0; i < detailList.size(); i++) {
				QueryPanel qp = (QueryPanel) detailList.get(i);
				if (qp.getMainTableColumn().getColumnName()
						.equals(maintable.getKeyColumnName())) {
					if (qp.getValue() != null && qp.getValue().length() > 0) {
						sql += "'" + qp.getValue() + "',";
					} else {
						sql += "'" + DateUtil.getdateAndRandom() + "',";
					}
				} else {
					if (qp.getMainTableColumn().getDataType().equals("1")) {
						sql += "'" + qp.getValue() + "',";
					} else if (qp.getMainTableColumn().getDataType()
							.equals("2")) {
						if (qp.getMainTableColumn().getPropertyType()
								.equals("6")) {
							sql += qp.getMainTableColumn().getTolerant() + ",";
						} else if (qp.getValue() != null
								&& qp.getValue().length() > 0) {
							sql += qp.getValue() + ",";
						} else {
							sql += "'" + qp.getValue() + "',";
						}
					} else if (qp.getMainTableColumn().getDataType()
							.equals("3")) {
						if (qp.getMainTableColumn().getPropertyType()
								.equals("6")) {
							if (qp.getValue() != null
									&& qp.getValue().length() > 0) {
								sql += "to_date('" + qp.getValue()
										+ "','yyyy-mm-dd hh24:mi:ss'),";
							} else {
								sql += "to_date('"
										+ DateUtil
												.getDateToStringFull(new Date())
										+ "','yyyy-mm-dd hh24:mi:ss'),";
							}
						} else if (qp.getMainTableColumn().getPropertyType()
								.equals("5")) {
							sql += "to_date('" + qp.getValue()
									+ "','yyyy-mm-dd hh24:mi:ss'),";
						} else if (qp.getMainTableColumn().getPropertyType()
								.equals("10")) {
							sql += "to_date('" + qp.getValue()
									+ "','yyyy-mm-dd'),";
						}

					}
				}
			}
			sql = sql.substring(0, sql.length() - 1) + ")";
		}
		return sql;
	}

	public static String getServerForInsert(List detailList,
			MainTable maintable, boolean haskey) {
		String sql = "";
		if (haskey == true) {
			sql += "UPDATE " + maintable.getTableRealName() + " SET ";
			String lastsql = "";
			for (int i = 0; i < detailList.size(); i++) {
				QueryPanel qp = (QueryPanel) detailList.get(i);
				if (qp.getMainTableColumn().getColumnName()
						.equals(maintable.getKeyColumnName())) {
					lastsql += "where "
							+ qp.getMainTableColumn().getColumnName() + "='"
							+ qp.getValue() + "'";
				} else {
					if (qp.getMainTableColumn().getDataType().equals("1")) {
						sql += qp.getMainTableColumn().getColumnName() + "='"
								+ qp.getValue() + "' ,";

					} else if (qp.getMainTableColumn().getDataType()
							.equals("2")) {
						if (qp.getValue() != null && qp.getValue() != "") {
							sql += qp.getMainTableColumn().getColumnName()
									+ "=" + qp.getValue() + " ,";
						} else {
							sql += qp.getMainTableColumn().getColumnName()
									+ "=" + 0 + " ,";
						}
					} else if (qp.getMainTableColumn().getDataType()
							.equals("3")) {
						if (qp.getMainTableColumn().getPropertyType()
								.equals("6")) {
							if (qp.getValue() != null
									&& qp.getValue().length() > 0) {
								sql += qp.getMainTableColumn().getColumnName()
										+ "='" + qp.getValue().substring(0, 19)
										+ "',";
							} else {
								sql += qp.getMainTableColumn().getColumnName()
										+ "='"
										+ DateUtil
												.getDateToStringFull(new Date())
										+ "',";
							}
						} else {
							sql += qp.getMainTableColumn().getColumnName()
									+ "='" + qp.getValue() + "',";
						}
					}
				}
			}
			sql = sql.substring(0, sql.length() - 1) + lastsql;

		} else {
			sql += "INSERT INTO " + maintable.getTableRealName() + " (";
			for (int i = 0; i < detailList.size(); i++) {
				QueryPanel qp = (QueryPanel) detailList.get(i);
				sql += qp.getMainTableColumn().getColumnName() + ",";
			}
			sql = sql.substring(0, sql.length() - 1) + ") values (";
			for (int i = 0; i < detailList.size(); i++) {
				QueryPanel qp = (QueryPanel) detailList.get(i);
				if (qp.getMainTableColumn().getColumnName()
						.equals(maintable.getKeyColumnName())) {
					sql += "'" + DateUtil.getdateAndRandom() + "',";
				} else {
					if (qp.getMainTableColumn().getDataType().equals("1")) {
						sql += "'" + qp.getValue() + "',";
					} else if (qp.getMainTableColumn().getDataType()
							.equals("2")) {
						sql += qp.getValue() + ",";
					} else if (qp.getMainTableColumn().getDataType()
							.equals("3")) {
						if (qp.getMainTableColumn().getPropertyType()
								.equals("6")) {
							if (qp.getValue() != null
									&& qp.getValue().length() > 0) {
								sql += "'" + qp.getValue() + "',";
							} else {
								sql += "'"
										+ DateUtil
												.getDateToStringFull(new Date())
										+ "',";
							}
						} else {
							sql += "'" + qp.getValue() + "',";
						}

					}
				}
			}
			sql = sql.substring(0, sql.length() - 1) + ")";
		}
		return sql;
	}

	public static Connection getConnect(String drivername, String url,
			String name, String passwd) throws Exception {
		Connection con = null;
		Class.forName(drivername);
		con = DriverManager.getConnection(url, name, passwd);
		return con;
	}

	public static void main(String[] args) {
		Connection c = null;
		Map map1 = new HashMap();
		try {
			String drivername = "oracle.jdbc.driver.OracleDriver";
			String url = "jdbc:oracle:thin:@192.168.10.158:1521:sdh";
			String name = "telexnms";
			String passwd = "telexnms";
			c = SqlUtil.getConnect(drivername, url, name, passwd);
			PreparedStatement sqlps = c
					.prepareStatement("select VIEW_NAME from user_views");
			ResultSet rs = sqlps.executeQuery();
			while (rs.next()) {
				Object ob = rs.getObject("VIEW_NAME");
				String obs = ob.toString();
				map1.put(obs, obs);
			}
			rs.close();
			sqlps.close();
			c.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map map2 = new HashMap();
		try {
			String drivername = "oracle.jdbc.driver.OracleDriver";
			String url = "jdbc:oracle:thin:@192.168.10.158:1521:sdh";
			String name = "netmanage";
			String passwd = "netmanage";
			c = SqlUtil.getConnect(drivername, url, name, passwd);
			PreparedStatement sqlps = c
					.prepareStatement("select VIEW_NAME from user_views");
			ResultSet rs = sqlps.executeQuery();
			while (rs.next()) {
				Object ob = rs.getObject("VIEW_NAME");
				String obs = ob.toString();
				map2.put(obs, obs);
			}
			rs.close();
			sqlps.close();
			c.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set key = map2.keySet();
		Iterator it = key.iterator();
		while (it.hasNext()) {
			Object ob = it.next();
			Object value = map2.get(ob);
			if (map1.get(ob) != null) {
				// map1.remove(ob);
			}
		}
		System.out.println("Դmap1:" + map1.size() + "|" + map1.toString());
		System.out.println("��map2:" + map2.size() + "|" + map2.toString());
	}

	public static Map getExtListForAjax(MainTableColumn maintablecolumn,
			String value) throws Exception {
		SelectDataService selectDataService = (SelectDataService) SpringContextWrapper
				.getBean("selectDataService");
		// List list=null;
		Map rmap = new HashMap();
		if (maintablecolumn.getPropertyType().equals("8")) {
			if (value != null && value.length() > 0) {
				String[] ids = value.split(",");
				String rname = "";
				for (String id : ids) {
					String sql = maintablecolumn.getTypeSql();
					sql = "select * from (" + sql + ") tables where tables."
							+ "id='" + id + "'";
					List list1 = selectDataService.getData(sql);
					if (list1 != null && list1.size() > 0) {
						Map map = (Map) list1.get(0);
						rname += map.get("name") + ",";
					}
				}
				if (rname.length() != 0
						&& rname.length() == rname.lastIndexOf(",") + 1) {
					rname = rname.substring(0, rname.length() - 1);
				}
				rmap.put(value, rname);
			}
			List<Map<String, Object>> list = selectDataService
					.getData(maintablecolumn.getTypeSql());
			for (Map map : list) {
				rmap.put(map.get("id"), map.get("name"));

			}
		} else {
			if (maintablecolumn.getTypeSql().indexOf("#") != -1) {
				String sql = maintablecolumn.getTypeSql();
				sql = sql.substring(1, sql.length());
				String[] sqls = sql.split(",");
				if (sqls[0].length() > 0) {
					for (String subsql : sqls) {
						String[] sql1 = subsql.split(":");
						rmap.put(sql1[0], sql1[1]);
					}
				}

			} else {
				List<Map<String, Object>> list = selectDataService
						.getData(maintablecolumn.getTypeSql());
				for (Map map : list) {
					rmap.put(map.get("id"), map.get("name"));
				}
			}
		}
		return rmap;
	}

	public static String getOracleQuerySql(List viewList, List queryList,
			MainTable maintable) throws Exception {
		String selectsql = "select tables.* ";
		SelectDataService selectDataService = (SelectDataService) SpringContextWrapper
				.getBean("selectDataService");
		if (maintable.getIsMulti().equals("1")) {
			List list = selectDataService.getData(maintable.getMultiSql());
			String mainsql = "";
			for (Object obj : list) {
				ListOrderedMap map = (ListOrderedMap) obj;
				String tablename = map.get("tablename").toString();
				if (mainsql.length() == 0) {
					mainsql = maintable.getSql().replace(
							maintable.getTableName(), tablename);
				} else {
					mainsql += " union all "
							+ maintable.getSql().replace(
									maintable.getTableName(), tablename);
				}
			}
			selectsql += " from (" + mainsql + ") tables ";
		} else {
			selectsql += " from (" + maintable.getSql() + ") tables ";
		}
		int flag = 0;// 表示有没查询条件
		for (int i = 0; i < queryList.size(); i++) {
			QueryPanel qp = (QueryPanel) queryList.get(i);
			if (qp.getType().equals("5")) {
				if (qp.getMainTableColumn().getDataType().equals("3")) {
					if (qp.getStartdate() != null
							&& qp.getStartdate().length() > 0) {
						if (flag == 0) {
							selectsql += "where tables." + qp.getName()
									+ ">= TO_DATE('" + qp.getStartdate()
									+ "','yyyy-mm-dd hh24:mi:ss')";
							flag = 1;
						} else {
							selectsql += "and tables." + qp.getName()
									+ ">= TO_DATE('" + qp.getStartdate()
									+ "','yyyy-mm-dd hh24:mi:ss')";
						}
					}
					if (qp.getEnddate() != null && qp.getEnddate().length() > 0) {
						if (flag == 0) {
							selectsql += "where tables." + qp.getName()
									+ "<= TO_DATE('" + qp.getEnddate()
									+ "','yyyy-mm-dd hh24:mi:ss')";
							flag = 1;
						} else {
							selectsql += "and tables." + qp.getName()
									+ "<= TO_DATE('" + qp.getEnddate()
									+ "','yyyy-mm-dd hh24:mi:ss')";
						}
					}
				} else {
					if (qp.getValue() != null && qp.getValue().length() > 0) {
						if (flag == 0) {
							selectsql += "where tables." + qp.getName()
									+ " = '" + qp.getValue() + "'";
							flag = 1;
						} else {
							selectsql += "and tables." + qp.getName() + " = '"
									+ qp.getValue() + "'";
						}
					}
				}

			} else if (qp.getType().equals("10")) {
				if (qp.getMainTableColumn().getDataType().equals("3")) {
					if (qp.getStartdate() != null
							&& qp.getStartdate().length() > 0) {
						if (flag == 0) {
							selectsql += "where tables." + qp.getName()
									+ ">= TO_DATE('" + qp.getStartdate()
									+ "','yyyy-mm-dd')";
							flag = 1;
						} else {
							selectsql += "and tables." + qp.getName()
									+ ">= TO_DATE('" + qp.getStartdate()
									+ "','yyyy-mm-dd')";
						}
					}
					if (qp.getEnddate() != null && qp.getEnddate().length() > 0) {
						if (flag == 0) {
							selectsql += "where tables." + qp.getName()
									+ "<= TO_DATE('" + qp.getEnddate()
									+ "','yyyy-mm-dd')";
							flag = 1;
						} else {
							selectsql += "and tables." + qp.getName()
									+ "<= TO_DATE('" + qp.getEnddate()
									+ "','yyyy-mm-dd')";
						}
					}
				} else {
					if (qp.getValue() != null && qp.getValue().length() > 0) {
						if (flag == 0) {
							selectsql += "where tables." + qp.getName()
									+ " = '" + qp.getValue() + "'";
							flag = 1;
						} else {
							selectsql += "and tables." + qp.getName() + " = '"
									+ qp.getValue() + "'";
						}
					}
				}
			} else if (qp.getType().equals("8")) {
				if (qp.getValue() != null && qp.getValue().length() > 0) {
					if (flag == 0) {
						if ("1".equals(qp.getMainTableColumn().getIsChecked())) {
							selectsql += "where tables." + qp.getName()
									+ " in (" + qp.getValue() + ")";
						} else {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							}
						}
						flag = 1;
					} else {
						if ("1".equals(qp.getMainTableColumn().getIsChecked())) {
							selectsql += "and tables." + qp.getName() + " in ("
									+ qp.getValue() + ")";
						} else {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							}
						}
						// selectsql+="and tables."+qp.getName()+" like '%"+qp.getValue()+"%'";
					}
				}
			} else {
				if (qp.getMainTableColumn().getDataType().equals("2")) {
					if (qp.getValue() != null && qp.getValue().length() > 0) {
						if (flag == 0) {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							}
							flag = 1;
						} else {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else {
								selectsql += "and tables." + qp.getName()
										+ " like '" + qp.getValue() + "'";
							}
						}
					}
				} else {
					if (qp.getValue() != null && qp.getValue().length() > 0) {
						if (flag == 0) {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else {
								selectsql += "where tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							}
							flag = 1;
						} else {
							if ("1".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "'";
							} else if ("2".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '" + qp.getValue() + "%'";
							} else if ("3".equals(qp.getMainTableColumn()
									.getLikescope())) {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							} else {
								selectsql += "and tables." + qp.getName()
										+ " like '%" + qp.getValue() + "%'";
							}
							// selectsql+="and tables."+qp.getName()+" like '%"+qp.getValue()+"%'";
						}
					}
				}
			}
		}
		return selectsql;
	}

	public static String getMainSql(List viewList, List queryList,
			MainTable maintable) throws Exception {
		PropertiesCommonUtil propertiesUtil = new PropertiesCommonUtil(
				"application.properties");
		String databasetype = propertiesUtil
				.getProp("application.database.type");
		String sql = "";
		if (databasetype.indexOf("mysql") >= 0) {
			sql = getMySql(viewList, queryList, maintable, null);
		} else if (databasetype.indexOf("oracle") >= 0) {
			sql = getOracleSql(viewList, queryList, maintable, null);
		} else if (databasetype.indexOf("sqlserver") >= 0) {
			sql = getServerSql(viewList, queryList, maintable);
		}
		return sql;
	}
	/**
	 * 过滤 sql中的的权限
	 * @param sql
	 * @return
	 */
	public static String filterAuth(String sql){
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		sql = sql.replaceAll("@CurrentUserName", SpringSecurityUtils.getCurrentUsername());
		sql = sql.replaceAll("@CurrentUserId", SpringSecurityUtils.getCurrentUserId());
		sql = sql.replaceAll("@CurrentAppId", user.getAppId());
		sql = sql.replaceAll("@CurrentDepId", user.getDepId());
		sql = sql.replaceAll("@CurrentDepName", user.getDepName());
		return sql;
	}
}

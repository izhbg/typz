package com.izhbg.typz.database.sql.dao.imp;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.database.dto.QueryPanel;
import com.izhbg.typz.database.sql.dao.SelectDataDao;

/**
 * sql 查询实现
 * 
 * @author caixl
 * @date 2016-5-13 上午9:42:47
 * 
 */
@Repository("selectDataDao")
public class SelectDataDaoImpl extends JdbcDaoSupport implements SelectDataDao {

	@Transactional(readOnly = true)
	public List<Map<String, Object>> selectData(String sql) throws Exception {
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(
				sql);
		return list;
	}

	@Transactional
	public boolean deletebyid(String sql) throws Exception {
		try {
			this.getJdbcTemplate().execute(sql);
		} catch (DataAccessException e) {
			throw e;
		}
		return true;
	}

	@Transactional(readOnly = true)
	public String[] getColumnName(String sql) throws Exception {
		SqlRowSet sqlset = this.getJdbcTemplate().queryForRowSet(sql);
		String[] columnNames = sqlset.getMetaData().getColumnNames();
		return columnNames;
	}

	@Transactional(readOnly = true)
	public int selectDataRowNum(String sql) throws Exception {
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(
				"select count(*) count from (" + sql + ") tablecount");
		int i = 0;
		if (list != null && list.size() > 0) {
			i = list.get(0).get("count") == null ? 0 : Integer.parseInt(list
					.get(0).get("count") + "");
		}
		return i;
	}

	@Transactional
	public void insertRealTable(String sql) throws Exception {
		this.getJdbcTemplate().update(sql);
	}

	@Transactional(readOnly = true)
	public List getMutiChecked(List<Map<String, Object>> resoultList,
			List<QueryPanel> treelist) throws Exception {
		for (int i = 0; i < resoultList.size(); i++) {
			Map<String, Object> map = resoultList.get(i);
			for (QueryPanel qp : treelist) {
				Object ob = map.get(qp.getName());
				if (ob != null) {
					String[] vas = ob.toString().split(",");
					if (vas.length > 1 && qp.getType() != null
							&& qp.getType().equals("8")) {
						map.put(qp.getName(), vas[1]);
					} else {
						String van = "";
						for (String va : vas) {
							String vsql = "select tb.name from ("
									+ qp.getMainTableColumn().getTypeSql()
											.split("#")[0] + ") tb where id='"
									+ va + "'";
							List<Map<String, Object>> vList = this
									.selectData(vsql);
							if (vList.size() > 0) {
								Map<String, Object> vmap = vList.get(0);
								van += vmap.get("name") + ",";
							}
						}
						if (van.length() != 0
								&& van.length() == van.lastIndexOf(",") + 1) {
							van = van.substring(0, van.length() - 1);
						}
						map.put(qp.getName(), van);
					}

				}
			}
			resoultList.set(i, map);
		}
		return resoultList;
	}

}

package com.izhbg.typz.database.sql.service;

import java.util.List;
import java.util.Map;


import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.database.dto.QueryPanel;
/**
 * sql 查询服务
* @author caixl 
* @date 2016-5-13 上午9:43:47 
*
 */
public interface SelectDataService {
	@Transactional(readOnly = true)
	public List<Map<String,Object>> getData(String sql) throws Exception;

	@Transactional
	public boolean remove(String sql)throws Exception;

	@Transactional(readOnly = true)
	public String[] getColumnName(String sql)throws Exception;

	@Transactional(readOnly = true)
	public int getDataRowNum(String sql)throws Exception;

	@Transactional(readOnly = true)
	public boolean executeSql(String sql)throws Exception;

	@Transactional
	public void saveRealTable(String sql)throws Exception;


	@Transactional(readOnly = true)
	public Map<String,Object> getListForPage(Page page, String sql)throws Exception;

	@Transactional(readOnly = true)
	public List<Map<String,Object>> getMutiChecked(List<Map<String,Object>> resoultList, List<QueryPanel> treelist)throws Exception;

}

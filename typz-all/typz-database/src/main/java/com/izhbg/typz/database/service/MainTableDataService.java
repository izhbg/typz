package com.izhbg.typz.database.service;

import java.util.Map;

import com.izhbg.typz.base.page.Page;
/**
 * maintbale 表单服务
* @author caixl 
* @date 2016-5-16 上午9:54:58 
*
 */
public interface MainTableDataService {

	/**
	 * 获取 maintble对应表单分页数据
	 * @param parameterMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> mainTableData_pageList(Map<String,Object> parameterMap,Page page) throws Exception;
	/**
	 * 获取maintable对应表单 编辑时初始化数据
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> mainTableData_editData(Map<String,Object>parameterMap) throws Exception;
	/**
	 * 保存表单数据
	 * @param parameterMap
	 * @throws Exception
	 */
	public void mainTableData_save(Map<String,Object>parameterMap) throws Exception;
	/**
	 * 删除表单数据
	 * @param parameterMap
	 * @throws Exception
	 */
	public void mainTableData_remove(String tableName,String[] realtableids) throws Exception;
}

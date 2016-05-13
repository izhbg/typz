package com.izhbg.typz.database.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.izhbg.typz.database.dto.MainTableColumn;

/**
 *  主表列接口
* @author caixl 
* @date 2016-5-12 下午5:25:21 
*
 */
public interface MainTableColumnService {

	/**
	 * 根据主表Id获取主表列信息
	 * @param mainTableId
	 * @return
	 * @throws Exception
	 */
	public List<MainTableColumn> findByMainTableId(String mainTableId) throws Exception;
	/**
	 * 根据列ID获取列信息
	 * @param mainTableColumnId
	 * @return
	 * @throws Exception
	 */
	public MainTableColumn findByMainTableColumnId(String mainTableColumnId) throws Exception;
	/**
	 * 根据主表ID删除主表列信息
	 * @param mainTableId
	 * @throws Exception
	 */
	public void deleteByMainTableId(String mainTableId) throws Exception;
	/**
	 * 加载主表列信息
	 * @param mainTableId
	 * @throws Exception
	 */
	public void saveLoadMainTableColumn(String mainTableId) throws Exception;
	/**
	 * 添加主表列信息
	 * @param mainTableColumn
	 * @throws Exception
	 */
	public void add(MainTableColumn mainTableColumn) throws Exception;
	/**
	 * 更新主表列信息
	 * @param mainTableColumn
	 * @throws Exception
	 */
	public void update(MainTableColumn mainTableColumn) throws Exception;
	/**
	 * 根据主表列主键ID删除主表列信息
	 * @param mainTableColumnId
	 * @throws Exception
	 */
	public void deleteByMainTableColumnId(String mainTableColumnId) throws Exception;
	/**
	 * 批量修改主表列信息
	 * @param request
	 * @throws Exception
	 */
	public void updateMainTableColumns(HttpServletRequest request) throws Exception;
}

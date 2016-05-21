package com.izhbg.typz.database.service;

import java.util.List;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.database.dto.MainTable;

/**
 * 主表接口
* @author caixl 
* @date 2016-5-12 下午5:00:01 
*
 */
public interface MainTableService {
	/**
	 * 根据appId 和TypeId获取主表列表
	 * @param appId
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public Page findByAppIdAndTypeId(String appId,String typeId,Page page) throws Exception;
	
	/**
	 * 根据主表ID 获取主表
	 * @param maintableId
	 * @return
	 * @throws Exception
	 */
	public MainTable findByMainTableId(String maintableId) throws Exception;
	
	/**
	 * 添加
	 * @param mainTable
	 * @throws Exception
	 */
	public void add(MainTable mainTable) throws Exception;
	
	/**
	 * 更新
	 * @param mainTable
	 * @throws Exception
	 */
	public void update(MainTable mainTable) throws Exception;
	/**
	 * 根据ID删除
	 * @param tableId
	 * @throws Exception
	 */
	public void delete(String tableId) throws Exception;
	/**
	 * 拷贝主表信息
	 * @param targetId
	 * @return
	 * @throws Exception
	 */
	public MainTable copy(String targetId,String formname) throws Exception;
	/**
	 * 根据mainTableName获取miantable
	 * @param mainTableName
	 * @return
	 * @throws Exception
	 */
	public List<MainTable> findByMainTableName(String mainTableName) throws Exception;
}

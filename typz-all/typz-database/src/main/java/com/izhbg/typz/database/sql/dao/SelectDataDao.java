package com.izhbg.typz.database.sql.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.database.dto.QueryPanel;
/**
 * sql 查询接口
* @author caixl 
* @date 2016-5-13 上午9:30:03 
*
 */
@Repository
public interface SelectDataDao {
	/**
	 * 根据sql 获取哈希结果
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<Map<String,Object>> selectData(String sql) throws Exception;
	
	/**
	 * 根据id 删除操作
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean deletebyid(String sql) throws Exception;
	
	/**
	 * 获取主表的列
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public String[] getColumnName(String sql) throws Exception;

	/**
	 * 获取行数
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public int selectDataRowNum(String sql) throws Exception;

	/**
	 * 插入主表数据
	 * @param sql
	 * @throws Exception
	 */
	@Transactional
	public void insertRealTable(String sql) throws Exception;

	/**
	 * 多选操作
	 * @param resoultList
	 * @param treelist
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<Map<String,Object>> getMutiChecked(List<Map<String,Object>> resoultList, List<QueryPanel> treelist)throws Exception;
}

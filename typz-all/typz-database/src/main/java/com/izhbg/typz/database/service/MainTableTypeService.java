package com.izhbg.typz.database.service;

import java.util.List;

import com.izhbg.typz.database.dto.MainTableType;

public interface MainTableTypeService {
	/**
	 * 根据 appId 获取表单类型
	 * @param appId
	 * @return
	 */
	public List<MainTableType> findByAppId(String appId) throws Exception;
	/**
	 * 添加表单类型
	 * @param mainTableType
	 * @return
	 */
	public void add(MainTableType mainTableType) throws Exception;
	/**
	 * 更新表单类型
	 * @param mainTableType
	 * @return
	 */
	public void update(MainTableType mainTableType) throws Exception;
	/**
	 * 删除表单类型
	 * @param id
	 */
	public void deleteById(String id) throws Exception;
	/**
	 * 批量删除表单类型
	 * @param ids
	 * @throws Exception
	 */
	public void deleteByIds(List<String> ids) throws Exception;
}

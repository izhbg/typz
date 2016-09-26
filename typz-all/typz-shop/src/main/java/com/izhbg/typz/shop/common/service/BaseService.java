package com.izhbg.typz.shop.common.service;

import java.util.List;

import com.izhbg.typz.base.page.Page;
/**
 * 
* @ClassName: BaseService 
* @Description: 通用服务
* @author caixl 
* @date 2016-6-21 上午9:23:50 
* 
* @param <T>
 */
public interface BaseService<T> {
	/**
	 * 添加
	 * @param entityClass
	 * @throws Exception
	 */
	public void add(T entity)throws Exception;
	/**
	 * 更新
	 * @param entityClass
	 * @throws Exception
	 */
	public void update(T entity) throws Exception;
	/**
	 * 删除
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception;
	/**
	 * 批量删除
	 * @param ids
	 * @throws Exception
	 */
	public void deleteBatche(String[] ids) throws Exception;
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public T getById(String id) throws Exception;
	/**
	 * 无查询条件分页列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page pageList(Page page) throws Exception;
	/**
	 * 获取所有数据
	 * @return
	 * @throws Exception
	 */
	public List<T> getAll() throws Exception;
}

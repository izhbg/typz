package com.izhbg.typz.database.sql.dao;

import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface BaseDao {
	/**
	 * 保存实体
	 * 
	 * @param obj
	 * @param clazz
	 * @param key
	 * @return
	 */
	@Transactional
	public Object save(Object obj, Class clazz, String key);

	/**
	 * 删除实体
	 * 
	 * @param obj
	 */
	@Transactional
	public void remove(Object obj);

	/**
	 * 通过参数查找实体
	 * 
	 * @param clazz
	 * @param propName
	 * @param propValue
	 * @return
	 */
	@Transactional(readOnly = true)
	public List findObjectByPar(Class clazz, String propName, Object propValue);

	/**
	 * 出入参数,和排序的名称
	 * 
	 * @param clazz
	 * @param propName
	 * @param propValue
	 * @param orderName
	 * @param ascoOrdesc
	 * @return
	 */
	@Transactional(readOnly = true)
	public List findObjectByParOrder(Class clazz, String propName,
			Object propValue, String orderName, String ascoOrdesc);

	/**
	 * 通过传多个参数来查询相应数据
	 * 
	 * @param clazz
	 * @param propNames
	 * @param propValues
	 * @return
	 */
	@Transactional(readOnly = true)
	public List findObjectByPars(Class clazz, String[] propNames,
			Object[] propValues);

	/**
	 * 通过传多个参数来模糊查询相应数据
	 * 
	 * @param clazz
	 * @param propNames
	 * @param propValues
	 * @return
	 */
	@Transactional(readOnly = true)
	public List findObjectByLikeParsOrder(Class clazz, String[] propNames,
			Object[] propValues, String[] orderNames, String[] ascoOrdescs);

	@Transactional(readOnly = true)
	public List findObjectByParsOrder(Class clazz, String[] propNames,
			Object[] propValues, String orderName, String ascoOrdesc);

	/**
	 * 查询
	 * 
	 * @param clazz
	 * @param proMap
	 * @param orderMap
	 * @return
	 */
	@Transactional(readOnly = true)
	public List findObjectListByParamAndOrder(Class clazz,
			LinkedHashMap proMap, LinkedHashMap orderMap);

	/**
	 * 获取唯一的主键值
	 */
	@Transactional(readOnly = true)
	public Long getUniqueId();

	@Transactional(readOnly = true)
	public Long selectSequence();

	/**
	 * 获取Criteria
	 * 
	 * @param clazz
	 * @return
	 */
	@Transactional(readOnly = true)
	public Criteria getCriteria(Class clazz);
}

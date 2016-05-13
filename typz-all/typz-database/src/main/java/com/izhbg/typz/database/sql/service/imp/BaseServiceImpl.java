package com.izhbg.typz.database.sql.service.imp;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.database.sql.dao.BaseDao;
import com.izhbg.typz.database.sql.service.BaseService;

@Service("baseService")
public class BaseServiceImpl implements BaseService {
	private BaseDao baseDao;

	@Resource
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List findObjectByLikeParsOrder(Class clazz, String[] propNames,
			Object[] propValues, String[] orderNames, String[] ascoOrdescs) {
		return baseDao.findObjectByLikeParsOrder(clazz, propNames, propValues,
				orderNames, ascoOrdescs);
	}

	@Override
	@Transactional(readOnly = true)
	public List findObjectByPar(Class clazz, String propName, Object propValue) {
		return baseDao.findObjectByPar(clazz, propName, propValue);
	}

	@Override
	@Transactional(readOnly = true)
	public List findObjectByParOrder(Class clazz, String propName,
			Object propValue, String orderName, String ascoOrdesc) {
		return baseDao.findObjectByParOrder(clazz, propName, propValue,
				orderName, ascoOrdesc);
	}

	@Override
	@Transactional(readOnly = true)
	public List findObjectByPars(Class clazz, String[] propNames,
			Object[] propValues) {
		return baseDao.findObjectByPars(clazz, propNames, propValues);
	}

	@Override
	@Transactional(readOnly = true)
	public List findObjectByParsOrder(Class clazz, String[] propNames,
			Object[] propValues, String orderName, String ascoOrdesc) {
		return baseDao.findObjectByParsOrder(clazz, propNames, propValues,
				orderName, ascoOrdesc);
	}

	@Override
	@Transactional(readOnly = true)
	public List findObjectListByParamAndOrder(Class clazz,
			LinkedHashMap proMap, LinkedHashMap orderMap) {
		return baseDao.findObjectListByParamAndOrder(clazz, proMap, orderMap);
	}

	@Override
	@Transactional
	public void remove(Object obj) {
		baseDao.remove(obj);
	}

	@Override
	@Transactional
	public Object save(Object obj, Class clazz, String key) {
		return baseDao.save(obj, clazz, key);
	}

	@Transactional(readOnly = true)
	public Long getUniqueId() {
		return baseDao.getUniqueId();
	}
}

package com.izhbg.typz.database.sql.dao.imp;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.util.DateUtil;
import com.izhbg.typz.database.sql.dao.BaseDao;
import com.izhbg.typz.database.uitl.WebUtils;

@Repository("baseDao")
public class BaseDaoImpl extends HibernateTransactionManager implements BaseDao {

	@Transactional(readOnly = true)
	public List findObjectByPar(Class clazz, String propName, Object propValue) {
		Criteria c = this.getSessionFactory().getCurrentSession()
				.createCriteria(clazz);
		if (propName != null) {
			c.add(Restrictions.eq(propName, propValue));
		}
		return c.list();
	}

	@Transactional(readOnly = true)
	public List findObjectByPars(Class clazz, String[] propNames,
			Object[] propValues) {
		Criteria c = this.getSessionFactory().getCurrentSession()
				.createCriteria(clazz);
		for (int i = 0; i < propNames.length; i++) {
			c.add(Restrictions.eq(propNames[i], propValues[i]));
		}
		return c.list();
	}

	@Transactional
	public void remove(Object obj) {
		this.getSessionFactory().getCurrentSession().delete(obj);
	}

	@Transactional(readOnly = true)
	public Long selectMaxIdFromTable(Class clazz, String propertyName) {
		Criteria c = this.getSessionFactory().getCurrentSession()
				.createCriteria(clazz);
		c.setProjection(Projections.projectionList().add(
				Projections.max(propertyName)));
		Object ob = c.uniqueResult();
		Long orderMax = Long.parseLong(WebUtils.getRandomId());
		if (ob != null) {
			orderMax = Long.parseLong(ob.toString());
		}
		return orderMax;
	}

	@Transactional(readOnly = true)
	public Long selectSequence() {
		Object object = this.getSessionFactory().getCurrentSession()
				.createSQLQuery("select seq_number.nextval from dual")
				.uniqueResult();

		return Long.parseLong(object.toString());
	}

	@Transactional
	public Object save(Object obj, Class clazz, String key) {
		// TODO Auto-generated method stub
		BeanWrapper bw = new BeanWrapperImpl(obj);
		Object provalue = bw.getPropertyValue(key);
		if (provalue == null || provalue.equals("")) {
			// Long id=this.selectMaxIdFromTable(clazz, key)+1;
			// selectSequence();
			Long id = Long.parseLong(DateUtil.getDateToStringFull2(new Date())
					+ selectSequence());
			// String id=DateUtil.getDateToStringFull2(new
			// Date())+System.currentTimeMillis()+DateUtil.getRandom(1000,9999);
			bw.setPropertyValue(key, id.toString());
			this.getSessionFactory().getCurrentSession().save(obj);
			// this.getHibernateTemplate().flush();
		} else {
			this.getSessionFactory().getCurrentSession().merge(obj);
			// getHibernateTemplate().flush();
		}
		return obj;
	}

	@Transactional(readOnly = true)
	public List findObjectByParOrder(Class clazz, String propName,
			Object propValue, String orderName, String ascoOrdesc) {
		// TODO Auto-generated method stub
		Criteria c = this.getSessionFactory().getCurrentSession()
				.createCriteria(clazz);
		c.add(Restrictions.eq(propName, propValue));
		if (orderName != null && ascoOrdesc.equals("desc")) {
			c.addOrder(Order.desc(orderName));
		} else if (orderName != null && ascoOrdesc.equals("asc")) {
			c.addOrder(Order.asc(orderName));
		}
		return c.list();
	}

	@Transactional(readOnly = true)
	public List findObjectByLikeParsOrder(Class clazz, String[] propNames,
			Object[] propValues, String[] orderNames, String[] ascoOrdescs) {
		// TODO Auto-generated method stub
		Criteria c = this.getSessionFactory().getCurrentSession()
				.createCriteria(clazz);
		for (int i = 0; i < propNames.length; i++) {
			if (propValues[i].toString().length() > 0) {
				c.add(Restrictions.ilike(propNames[i],
						propValues[i].toString(), MatchMode.ANYWHERE));
			}

		}
		for (int i = 0; i < orderNames.length; i++) {
			String orderName = orderNames[i];
			String ascoOrdesc = ascoOrdescs[i];
			if (orderName != null && ascoOrdesc.equals("desc")) {
				c.addOrder(Order.desc(orderName));
			} else if (orderName != null && ascoOrdesc.equals("asc")) {
				c.addOrder(Order.asc(orderName));
			}
		}
		return c.list();
	}

	@Transactional(readOnly = true)
	public List findObjectByParsOrder(Class clazz, String[] propNames,
			Object[] propValues, String orderName, String ascoOrdesc) {
		// TODO Auto-generated method stub
		Criteria c = this.getSessionFactory().getCurrentSession()
				.createCriteria(clazz);
		for (int i = 0; i < propNames.length; i++) {
			c.add(Restrictions.eq(propNames[i], propValues[i]));
		}
		if (orderName != null && ascoOrdesc.equals("desc")) {
			c.addOrder(Order.desc(orderName));
		} else if (orderName != null && ascoOrdesc.equals("asc")) {
			c.addOrder(Order.asc(orderName));
		}
		return c.list();
	}

	@Transactional(readOnly = true)
	public List findObjectListByParamAndOrder(Class clazz,
			LinkedHashMap proMap, LinkedHashMap orderMap) {
		// TODO Auto-generated method stub
		Criteria c = this.getSessionFactory().getCurrentSession()
				.createCriteria(clazz);
		if (proMap != null) {
			Set set = proMap.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()) {
				Object obkey = it.next();
				Object obvalue = proMap.get(obkey);
				c.add(Restrictions.eq(obkey.toString(), obvalue));
			}
		}
		if (orderMap != null) {
			Set set1 = orderMap.keySet();
			Iterator it1 = set1.iterator();
			while (it1.hasNext()) {
				Object obkey = it1.next();
				Object obvalue = orderMap.get(obkey);
				if (obvalue != null && obvalue.equals("desc")) {
					c.addOrder(Order.desc(obkey.toString()));
				} else if (obvalue != null && obvalue.equals("asc")) {
					c.addOrder(Order.asc(obkey.toString()));
				}
			}
		}
		return c.list();
	}

	@Transactional(readOnly = true)
	public Long getUniqueId() {
		Long id = Long.parseLong(DateUtil.getDateToStringFull2(new Date())
				+ selectSequence());
		return id;
	}

	@Transactional(readOnly = true)
	public Criteria getCriteria(Class clazz) {
		Criteria c = this.getSessionFactory().getCurrentSession()
				.createCriteria(clazz);
		return c;
	}
}

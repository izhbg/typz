package com.izhbg.typz.sso.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
* @ClassName: SpringContextWrapper 
* @Description: 封装Spring ApplicationConext引用, 方便工程通过API获取bean实例
* @author caixl 
* @date 2016-4-26 下午3:45:48 
*
 */
@Component
public class SpringContextWrapper implements ApplicationContextAware
{

	private static ApplicationContext appContext;

	/**
	 * 根据beanName 获取bean实例
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName)
	{
		Object obj = null;
		if (null != appContext)
		{
			obj = appContext.getBean(beanName);
		}
		return obj;
	}

	/**
	 * 根据bean名称和类型进行获取Bean的实例
	 * 
	 * @param beanName
	 * @param clsType
	 * @return
	 */
	public static <T> T getBean(String beanName, Class<T> clsType)
	{
		T obj = null;
		if (null != appContext)
		{
			obj = appContext.getBean(beanName, clsType);
		}
		return obj;
	}

	/**
	 * 根据类型进行获取Bean的实例
	 * 
	 * @param clsType
	 * @return
	 */
	public static <T> T getBean(Class<T> clsType)
	{
		T obj = null;
		if (null != appContext)
		{
			obj = appContext.getBean(clsType);
		}
		return obj;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		SpringContextWrapper.appContext = applicationContext;
	}
}
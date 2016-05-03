package com.izhbg.typz.sso.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.izhbg.typz.sso.auth.dao.UserDao;

public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource
{


	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	private UserDao userDAO = new UserDao();
	
	public MyInvocationSecurityMetadataSourceService()
	{
		loadResourceDefine();
	}

	private void loadResourceDefine()
	{
		// 在Web服务器启动时，提取系统中的所有权限。
		List<Map<String,Object>> authoritesNames = userDAO.loadAllAuthoritesName();
		/**//*
			 * 应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。
			 * 一个资源可以由多个权限来访问。 sparta
			 */
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		String auth = null;
		List<Map<String,Object>> resourcesNames = null;
		String url = null;
		for (Map<String,Object> map : authoritesNames)
		{
			auth = map.get("authority_name")+"";
			ConfigAttribute ca = new SecurityConfig(auth);

			resourcesNames = userDAO.loadResourcesNameByAuthoritiesName(auth);

			for (Map<String,Object> res : resourcesNames)
			{
				url = res.get("resource_string")+"";
				/**//*
					 * 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，将权限增加到权限集合中
					 * 。 sparta
					 */
				if (resourceMap.containsKey(url))
				{
					Collection<ConfigAttribute> value = resourceMap.get(url);
					value.add(ca);
					resourceMap.put(url, value);
				} else
				{
					Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
					atts.add(ca);
					resourceMap.put(url, atts);
				}
			}
		}
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes()
	{

		return null;
	}

	// 根据URL，找到相关的权限配置。
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException
	{

		// object 是一个URL，被用户请求的url。
		FilterInvocation filterInvocation = (FilterInvocation) object;
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
		    String requestURL = ite.next();
		    RequestMatcher requestMatcher = new AntPathRequestMatcher(requestURL);
		    if(requestMatcher.matches(filterInvocation.getHttpRequest())) {
		        return resourceMap.get(requestURL);
		    }
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> arg0)
	{

		return true;
	}

}
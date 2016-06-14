package com.izhbg.typz.sso.security.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.izhbg.typz.sso.auth.SpringSecurityUserAuth;
import com.izhbg.typz.sso.auth.dao.UserDao;

/**
 * 
 * @ClassName: CustomUserDetailsService
 * @Description: 用户登录信息验证
 * @author caixl
 * @date 2016-4-22 下午4:25:05
 * 
 */
public class CustomUserDetailsService implements UserDetailsService
{

	protected static Logger logger = Logger.getLogger("CustomUserDetailsService");

	private UserDao userDAO = new UserDao();

	private UserCache userCache;
	
	public SpringSecurityUserAuth loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
	{

		SpringSecurityUserAuth user = null;
		try
		{
			//取得用户的密码  
			user = userDAO.loadUserByUserName(username);  
			
			//得到用户的权限  
			Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>(); 
			auths = userDAO.loadUserAuthoritiesByUserName( username ); 
			user.setAuthorities(auths);
			//user = new User(username, user.getPassword(), true, true, true, true, auths);

		} catch (Exception e)
		{
			logger.error("Error in retrieving user");
			throw new UsernameNotFoundException("Error in retrieving user");
		}
		return user;
	}
	
	//设置用户缓存功能。  
    public void setUserCache(UserCache userCache) {  
        this.userCache = userCache;  
    }  
    public UserCache getUserCache(){  
     return this.userCache;  
    }  
}

package com.izhbg.typz.sso.auth.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.izhbg.typz.sso.auth.SpringSecurityUserAuth;
import com.izhbg.typz.sso.auth.dto.TXtAuthorities;
import com.izhbg.typz.sso.auth.dto.TXtGnjsAuthorities;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.dto.TXtYhGnjs;
import com.izhbg.typz.sso.auth.manager.TXtAuthoritiesManager;
import com.izhbg.typz.sso.auth.manager.TXtGnjsAuthoritiesManger;
import com.izhbg.typz.sso.auth.manager.TXtYhGnjsManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.common.TreeNodeUtil;
import com.izhbg.typz.sso.util.SpringContextWrapper;

public class UserDao {  
	   
    protected static Logger logger = Logger.getLogger("UserDao");  
    
    private static final String QUERY_BY_YHDM_HQL = " from TXtYh where yhDm=? and yxBj=1 and scBj=2 ";
    private static final String QUERY_ALL_AUTHORITIES_SQL = " select authority_name from t_xt_authorities ";
    private static final String QUERY_RESOURCESNAMEBYAUTHORITIESNAME_SQL = " select b.resource_string from " +
    		" t_xt_authorities_resources a, " +
    		" t_xt_resources b, " +
    		" t_xt_authorities c " +
    		" where " +
    		" a.resource_id = b.resource_id " +
    		" and a.authority_id=c.authority_id " +
    		" and c.authority_name=? ";
    private static final String  QUERY_FINDPASSWORD_SQL = "select MM from t_xt_yh where YH_ID=?";
    private static final String QUERY_FINDPERMISSIONS_SQL = "select * from t_xt_gnjs_zy";
    private static final String QUERY_FINDROLES_SQL = "select js_dm as role from t_xt_yh_gnjs where YH_ID=?";
    
    
    TXtYhManager tXtYhManager = (TXtYhManager) SpringContextWrapper.getBean("TXtYhManager");
    /**
     * 根据username 获取唯一用户
     * @param username
     * @return
     */
    public SpringSecurityUserAuth loadUserByUserName(String username) {
    	
    	TXtYh tXtYh = tXtYhManager.findUnique(QUERY_BY_YHDM_HQL, username);
    	if(tXtYh!=null){
    		
    		return process(tXtYh);
    	}
        logger.error("User does not exist!");  
        throw new RuntimeException("User does not exist!");  
    }
    /**
     * 设置权限
     * @param auth
     * @return
     */
    private SpringSecurityUserAuth process(TXtYh tXtYh){
    	
    	SpringSecurityUserAuth userAuthResult = new SpringSecurityUserAuth();
		userAuthResult.setAppId(tXtYh.getAppId());
		userAuthResult.setDisplayName(tXtYh.getYhMc());
		userAuthResult.setId(tXtYh.getYhId());
		userAuthResult.setUsername(tXtYh.getYhDm());
		userAuthResult.setPassword(tXtYh.getMm());
		
		if(tXtYh.getScBj()==2)
			userAuthResult.setEnabled(true);
		else
			userAuthResult.setEnabled(false);
		
		if(tXtYh.getYxBj()==1)
			userAuthResult.setAccountNonLocked(true);
		else
			userAuthResult.setAccountNonLocked(false);
		
		// roles
        List<Map<String, Object>> roles = tXtYhManager.getJdbcTemplate().queryForList(
        		QUERY_FINDROLES_SQL, tXtYh.getYhId());
        userAuthResult.setRoles(roles);
        
        List<Map<String, Object>> permissions = null;
        String findsql = QUERY_FINDPERMISSIONS_SQL;
        if(roles!=null&&roles.size()>0){
        	findsql = findsql+" where ";
        	String jsDm = "";
        	for(Map role:roles){
        		jsDm = role.get("role")+"";
        		if(!jsDm.equals(""))
        			findsql+=" JS_DM='"+jsDm+"' or";
        	}
        	findsql = findsql.substring(0,findsql.length()-2);
        	permissions = tXtYhManager.getJdbcTemplate().queryForList(findsql);
        }
        
        List<Map<String,Object>> txgJgYhList =tXtYhManager.getJdbcTemplate().queryForList("select * from t_xt_jg_yh where yh_id ='"+tXtYh.getYhId()+"'");
		String jg_mc = "";
		String jg_id = "";
		if(txgJgYhList!=null&&txgJgYhList.size()>0)
		{
			Map map2 = txgJgYhList.get(0);
			Object obj = map2.get("jg_id");
			if(obj!=null){
				List<Map<String,Object>> txgJgList = tXtYhManager.getJdbcTemplate().queryForList("select jg_mc from t_xt_jg where jg_id ='"+obj+"'");
				if(txgJgList!=null&&txgJgList.size()>0){
					Map map3 = txgJgList.get(0);
					jg_mc = map3.get("jg_mc")+"";
					jg_id = obj+"";
				}
			}
		}
		userAuthResult.setDepName(jg_mc);
		userAuthResult.setDepId(jg_id);
		userAuthResult.setPermissions(permissions);
        //左侧功能树
        TreeNodeUtil treeNodeUtil = new TreeNodeUtil(tXtYhManager.getJdbcTemplate(),tXtYh.getYhId());
        userAuthResult.setTreeNode(treeNodeUtil.getTreeNode());
        return userAuthResult;
    }
    /**
     * 根据username 获取用户资源权限
     * @param username
     * @return
     */
    public Collection<GrantedAuthority> loadUserAuthoritiesByUserName(String username) {
    	
    	TXtYhGnjsManager tXtYhGnjsManager = (TXtYhGnjsManager) SpringContextWrapper.getBean("TXtYhGnjsManager");
        TXtGnjsAuthoritiesManger tXtGnjsAuthoritiesManger = (TXtGnjsAuthoritiesManger) SpringContextWrapper.getBean("TXtGnjsAuthoritiesManger");
        TXtAuthoritiesManager tXtAuthoritiesManager = (TXtAuthoritiesManager) SpringContextWrapper.getBean("TXtAuthoritiesManager");
        
    	Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
    	//获取用户，获取用户角色，获取权限
    	TXtYh tXtYh = tXtYhManager.findUnique(QUERY_BY_YHDM_HQL, username);
    	if(tXtYh!=null){
    		List<TXtYhGnjs> tXtYhGnjs = tXtYhGnjsManager.findBy("yhId", tXtYh.getYhId());
    		if(tXtYhGnjs!=null){
    			
    			List<TXtGnjsAuthorities> tgauthorities = null;
    			TXtAuthorities authorities = null;
    			for(TXtYhGnjs gnjs:tXtYhGnjs){
    				tgauthorities = tXtGnjsAuthoritiesManger.findBy("roleId", gnjs.getJsDm());
    				if(tgauthorities!=null){
    					for(TXtGnjsAuthorities tgauthoritie:tgauthorities){
    						authorities = tXtAuthoritiesManager.findUniqueBy("authorityId", tgauthoritie.getAuthorityId());
    						if(authorities!=null)
    							auths.add(new GrantedAuthorityImpl(authorities.getAuthorityName()));
    					}
    				}
    			}
    		}
    	}
    	return auths;
    }
    
    /**
     * 获取所有的资源权限
     * @return
     */
    public List<Map<String,Object>> loadAllAuthoritesName(){
    	List<Map<String,Object>> authoritesNames  = tXtYhManager.getJdbcTemplate().queryForList(QUERY_ALL_AUTHORITIES_SQL);
    	return authoritesNames;
    }
    /**
     * 获取资源名称
     * @param authName
     * @return
     */
    public List<Map<String,Object>> loadResourcesNameByAuthoritiesName(String authName){
    	List<Map<String,Object>> resourcesNames  = tXtYhManager.getJdbcTemplate().queryForList(QUERY_RESOURCESNAMEBYAUTHORITIESNAME_SQL,authName);
    	return resourcesNames;
    }
}

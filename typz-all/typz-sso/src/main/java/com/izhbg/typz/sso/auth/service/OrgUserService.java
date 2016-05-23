package com.izhbg.typz.sso.auth.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.JSONParam;
import com.izhbg.typz.sso.auth.dto.OrgUserQuery;

/**
 * 组织结构授权用户
* @author caixl 
* @date 2016-5-23 上午11:21:22 
*
 */
public interface OrgUserService {
	
	public Page queryPageList(Page page,OrgUserQuery orgUserQuery) throws Exception;
	
	public String getOrgUserPage(JSONParam[] params) throws Exception;
	
	public void add(String jgId,String[] checkdel) throws Exception;
	
	public void deleteByIds(String[] checkdel) throws Exception;
	public JSONObject getRootOrganCheck(String jgId,String appId) throws Exception;
	public JSONArray getSubOrganUserCheck(String jgId,String appId) throws Exception;
}

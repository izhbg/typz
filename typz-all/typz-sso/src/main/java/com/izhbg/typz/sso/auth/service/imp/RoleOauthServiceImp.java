package com.izhbg.typz.sso.auth.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.sso.auth.dto.TXtAuthorities;
import com.izhbg.typz.sso.auth.dto.TXtAuthoritiesResources;
import com.izhbg.typz.sso.auth.dto.TXtGnjsAuthorities;
import com.izhbg.typz.sso.auth.dto.TXtGnjsOauth;
import com.izhbg.typz.sso.auth.manager.TXtAuthoritiesManager;
import com.izhbg.typz.sso.auth.manager.TXtGnjsAuthoritiesManger;
import com.izhbg.typz.sso.auth.manager.TXtGnjsOauthManager;
import com.izhbg.typz.sso.auth.service.RoleAuthoritiesService;
import com.izhbg.typz.sso.auth.service.RoleOauthService;
import com.izhbg.typz.sso.auth2.dto.OauthClientDetailsDto;
import com.izhbg.typz.sso.auth2.service.OauthService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;

@Service("roleOauthService")
@Transactional(rollbackFor=Exception.class)
public class RoleOauthServiceImp implements RoleOauthService {

	private TXtGnjsOauthManager tXtGnjsOauthManager;
	
	private OauthService oauthService;
	
	@Override
	public Page queryPageList(Page page, String jsDm) throws Exception {
		StringBuffer str = new StringBuffer(" from TXtGnjsOauth where roleId=:jsDm");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jsDm", jsDm);
		page = tXtGnjsOauthManager.pagedQuery(str.toString(), page.getPageNo(), page.getPageSize(), params);
		return page;
	}

	@Override
	public void add(String gnjsDm,String[] checkdel) throws Exception {
		if(StringHelper.isEmpty(gnjsDm)||checkdel==null)
			throw new ServiceException("参数为空，添加授权关系失败");
		TXtGnjsOauth item = null;
		for(String id : checkdel){
			List uid = tXtGnjsOauthManager.find("select a.id from TXtGnjsOauth a where a.clientId=? and a.roleId=?", id,gnjsDm);
			if(uid == null||uid.size()<1) {
				item = new TXtGnjsOauth();
				item.setRoleId(gnjsDm);
				item.setClientId(id);
				item.setId(IdGenerator.getInstance().getUniqTime()+"");
				item.setEnabled(1);
				tXtGnjsOauthManager.save(item);
			}
		}
		
	}

	@Override
	public void deleteByIds(String[] ids) throws Exception {
		if(ids==null)
			throw new ServiceException("参数为空，删除授权关系失败");
		List<String> lst = new ArrayList<String>();
		for(String s : ids) 
			lst.add(s);
		List<TXtGnjsOauth> items = tXtGnjsOauthManager.findByIds(lst);
		
		for(Object o : items)
			tXtGnjsOauthManager.remove(o);
	}

	@Override
	public String getOauthTreeJson(String jsDm) throws Exception {
		JSONObject one = new JSONObject();
		one.put("id", "-1");
		one.put("name", "角色树");
		one.put("pId", "");
		one.put("isParent", true);
		one.put("nocheck", true);
		one.put("open", true);
		List<OauthClientDetailsDto> clientDetailsDtoList = oauthService.loadAllOauthClientDetailsDtos();
		JSONObject node = null;
		JSONArray jaTree = new JSONArray();
		TXtGnjsOauth tXtGnjsAuthorities = null;
		Map<String,Object> map = new HashMap<String,Object>();
		for(OauthClientDetailsDto client:clientDetailsDtoList){
			node = new JSONObject();
			node.put("id", client.getClientId());
			node.put("name",client.getClientId());
			node.put("pId", "-1");
			
			map.clear();
			map.put("clientId", client.getClientId());
			map.put("roleId", jsDm);
			tXtGnjsAuthorities = tXtGnjsOauthManager.findUnique(" from TXtGnjsOauth where clientId=:clientId and roleId=:roleId ", map);
			if(tXtGnjsAuthorities!=null){
				node.put("chkDisabled", true);
				node.put("checked", true);
			}
			jaTree.add(node);
		}
		jaTree.add(one);
		String result = jaTree.toString();
		if(StringHelper.isEmpty(result))
			result = "[]";
		return result;
	}

	@Resource
	public void settXtGnjsOauthManager(TXtGnjsOauthManager tXtGnjsOauthManager) {
		this.tXtGnjsOauthManager = tXtGnjsOauthManager;
	}
	@Resource
	public void setOauthService(OauthService oauthService) {
		this.oauthService = oauthService;
	}
	
	
	

}

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
import com.izhbg.typz.sso.auth.manager.TXtAuthoritiesManager;
import com.izhbg.typz.sso.auth.manager.TXtGnjsAuthoritiesManger;
import com.izhbg.typz.sso.auth.service.RoleAuthoritiesService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;

@Service("roleAuthoritiesService")
@Transactional(rollbackFor=Exception.class)
public class RoleAuthoritiesServiceImp implements RoleAuthoritiesService {

	private TXtGnjsAuthoritiesManger tXtGnjsAuthoritiesManger;
	private TXtAuthoritiesManager tXtAuthoritiesManager;
	
	@Override
	public Page queryPageList(Page page, String jsDm) throws Exception {
		StringBuffer str = new StringBuffer(" from TXtGnjsAuthorities where roleId=:jsDm");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jsDm", jsDm);
		page = tXtGnjsAuthoritiesManger.pagedQuery(str.toString(), page.getPageNo(), page.getPageSize(), params);
		List<TXtGnjsAuthorities> gnjsAuthorities = (List<TXtGnjsAuthorities>) page.getResult();
		TXtAuthorities tXtAuthorities = null;
		for(TXtGnjsAuthorities gnjsAuthority:gnjsAuthorities){
			tXtAuthorities = tXtAuthoritiesManager.findUniqueBy("authorityId", gnjsAuthority.getAuthorityId());
			if(tXtAuthorities!=null)
				gnjsAuthority.setAuthorities(tXtAuthorities);
		}
		page.setResult(gnjsAuthorities);
		return page;
	}

	@Override
	public void add(String gnjsDm,String[] checkdel) throws Exception {
		if(StringHelper.isEmpty(gnjsDm)||checkdel==null)
			throw new ServiceException("参数为空，添加授权关系失败");
		TXtGnjsAuthorities item = null;
		for(String id : checkdel){
			List uid = tXtGnjsAuthoritiesManger.find("select a.id from TXtGnjsAuthorities a where a.authorityId=? and a.roleId=?", id,gnjsDm);
			if(uid == null||uid.size()<1) {
				item = new TXtGnjsAuthorities();
				item.setRoleId(gnjsDm);
				item.setAuthorityId(id);
				item.setId(IdGenerator.getInstance().getUniqTime()+"");
				item.setEnabled(1);
				tXtGnjsAuthoritiesManger.save(item);
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
		List<TXtGnjsAuthorities> items = tXtGnjsAuthoritiesManger.findByIds(lst);
		
		for(Object o : items)
			tXtGnjsAuthoritiesManger.remove(o);
	}

	@Override
	public String getRolesTreeJson(String jsDm) throws Exception {
		JSONObject one = new JSONObject();
		one.put("id", "-1");
		one.put("name", "角色树");
		one.put("pId", "");
		one.put("isParent", true);
		one.put("nocheck", true);
		one.put("open", true);
		List<TXtAuthorities> authorities = tXtAuthoritiesManager.findBy("appId", SpringSecurityUtils.getCurrentUserAppId());
		JSONObject node = null;
		JSONArray jaTree = new JSONArray();
		TXtGnjsAuthorities tXtGnjsAuthorities = null;
		Map<String,Object> map = new HashMap<String,Object>();
		for(TXtAuthorities authority:authorities){
			node = new JSONObject();
			node.put("id", authority.getAuthorityId());
			node.put("name",authority.getAuthorityName());
			node.put("pId", "-1");
			
			map.clear();
			map.put("authorityId", authority.getAuthorityId());
			map.put("roleId", jsDm);
			tXtGnjsAuthorities = tXtGnjsAuthoritiesManger.findUnique(" from TXtGnjsAuthorities where authorityId=:authorityId and roleId=:roleId ", map);
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
	public void settXtGnjsAuthoritiesManger(
			TXtGnjsAuthoritiesManger tXtGnjsAuthoritiesManger) {
		this.tXtGnjsAuthoritiesManger = tXtGnjsAuthoritiesManger;
	}
	@Resource
	public void settXtAuthoritiesManager(TXtAuthoritiesManager tXtAuthoritiesManager) {
		this.tXtAuthoritiesManager = tXtAuthoritiesManager;
	}
	
	
	

}

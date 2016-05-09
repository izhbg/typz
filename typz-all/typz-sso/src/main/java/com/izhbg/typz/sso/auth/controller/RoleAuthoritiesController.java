package com.izhbg.typz.sso.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.sso.auth.dto.TXtAuthorities;
import com.izhbg.typz.sso.auth.dto.TXtAuthoritiesResources;
import com.izhbg.typz.sso.auth.dto.TXtGnjs;
import com.izhbg.typz.sso.auth.dto.TXtGnjsAuthorities;
import com.izhbg.typz.sso.auth.manager.TXtAuthoritiesManager;
import com.izhbg.typz.sso.auth.manager.TXtGnjsAuthoritiesManger;
import com.izhbg.typz.sso.auth.manager.TXtGnjsManager;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
import com.mysql.jdbc.Messages;

@Controller
@RequestMapping("/role_authorities")
public class RoleAuthoritiesController
{

	private TXtGnjsAuthoritiesManger tXtGnjsAuthoritiesManger;
	private TXtAuthoritiesManager tXtAuthoritiesManager;
	private TXtGnjsManager tXtGnjsManager;
	
	@RequestMapping("role_authorities_list")
	 public String list(@ModelAttribute  Page page,
	            @RequestParam Map<String, Object> parameterMap, Model model) {
		
		String jsDm = parameterMap.get("jsDm")==null?"":parameterMap.get("jsDm").toString();
		
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
		model.addAttribute("page", page);
		model.addAttribute("parameterMap", parameterMap);
		//获取资源列表形成结构树
		model.addAttribute("result", getRolesTreeJson(jsDm));
		
		TXtGnjs gnjs = tXtGnjsManager.findUniqueBy("gnjsDm", jsDm);
		model.addAttribute("gnjs", gnjs);
		return "admin/role/role_authorities_list";
	}
	/**
	 * 添加权限资源
	 * @param authorityId
	 * @param checkdel
	 * @return
	 */
	@RequestMapping(value="role_authorities_add",method=RequestMethod.POST)
	public @ResponseBody String authoritiesResourcesAdd(String authorityId,String[] checkdel){
		if(checkdel == null || checkdel.length < 1 
				|| StringHelper.isEmpty(authorityId)){
			return null;		
		}
		TXtAuthoritiesResources item = null;
		for(String id : checkdel){
			List uid = tXtGnjsAuthoritiesManger.find("select a.id from TXtGnjsAuthorities a where a.authorityId=? and a.roleId=?", authorityId,id);
			if(uid == null||uid.size()<1) {
				item = new TXtAuthoritiesResources();
				item.setResourceId(id);
				item.setAuthorityId(authorityId);
				item.setId(IdGenerator.getInstance().getUniqTime()+"");
				tXtGnjsAuthoritiesManger.save(item);
			}
		}
		return "sucess";
	}
	/**
	 * 删除权限资源
	 * @param checkdel
	 * @return
	 */
	@RequestMapping(value="role_authorities_dell",method=RequestMethod.POST)
	public @ResponseBody  String authoritiesResourcesDell(String[] checkdel){
		String result="";
		try{
			if(checkdel == null || checkdel.length < 1){
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));
			}
			List<String> lst = new ArrayList<String>();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtGnjsAuthorities> items = tXtGnjsAuthoritiesManger.findByIds(lst);
			
			for(Object o : items)
				tXtGnjsAuthoritiesManger.remove(o);
			result = "sucess";
		} catch (Exception ex) {
		}
		return result;
	}
	
	/**
	 * 获取资源树 json串
	 * @return
	 */
	public String getRolesTreeJson(String jsDm){
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
	public void settXtGnjsAuthoritiesManger(TXtGnjsAuthoritiesManger tXtGnjsAuthoritiesManger)
	{
		this.tXtGnjsAuthoritiesManger = tXtGnjsAuthoritiesManger;
	}
	@Resource
	public void settXtAuthoritiesManager(TXtAuthoritiesManager tXtAuthoritiesManager)
	{
		this.tXtAuthoritiesManager = tXtAuthoritiesManager;
	}
	@Resource
	public void settXtGnjsManager(TXtGnjsManager tXtGnjsManager)
	{
		this.tXtGnjsManager = tXtGnjsManager;
	}
	
}

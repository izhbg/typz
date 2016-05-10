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
import com.izhbg.typz.sso.annotation.SystemControllerLog;
import com.izhbg.typz.sso.auth.dto.TXtAuthorities;
import com.izhbg.typz.sso.auth.dto.TXtAuthoritiesResources;
import com.izhbg.typz.sso.auth.dto.TXtGnjsZy;
import com.izhbg.typz.sso.auth.dto.TXtResources;
import com.izhbg.typz.sso.auth.manager.TXtAuthoritiesManager;
import com.izhbg.typz.sso.auth.manager.TXtAuthoritiesResourcesManager;
import com.izhbg.typz.sso.auth.manager.TXtResourcesManager;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
import com.mysql.jdbc.Messages;

/**
 * 
* @ClassName: AuthoritiesResourcesController 
* @Description: 权限分配资源
* @author caixl 
* @date 2016-5-9 下午1:39:00 
*
 */
@Controller
@RequestMapping("/authorities_resources")
public class AuthoritiesResourcesController
{
	private TXtAuthoritiesResourcesManager tXtAuthoritiesResourcesManager;
	private TXtResourcesManager tXtResourcesManager;
	private TXtAuthoritiesManager tXtAuthoritiesManager;
	
	@RequestMapping("authorities_resources_list")
	 public String list(@ModelAttribute  Page page,
	            @RequestParam Map<String, Object> parameterMap, Model model) {
		
		String authorityId = parameterMap.get("authorityId")==null?"":parameterMap.get("authorityId").toString();
		
		StringBuffer str = new StringBuffer(" from TXtAuthoritiesResources where authorityId=:authorityId");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("authorityId", authorityId);
		
		page = tXtAuthoritiesResourcesManager.pagedQuery(str.toString(), page.getPageNo(), page.getPageSize(), params);
		List<TXtAuthoritiesResources> authoritiesResources = (List<TXtAuthoritiesResources>) page.getResult();
		TXtResources tXtResources = null;
		for(TXtAuthoritiesResources authoritiesResource:authoritiesResources){
			tXtResources = tXtResourcesManager.findUniqueBy("resourceId", authoritiesResource.getResourceId());
			if(tXtResources!=null)
				authoritiesResource.setResources(tXtResources);
		}
		page.setResult(authoritiesResources);
		model.addAttribute("page", page);
		model.addAttribute("parameterMap", parameterMap);
		//获取资源列表形成结构树
		model.addAttribute("result", getResourcesTreeJson(authorityId));
		
		TXtAuthorities tXtAuthorities = tXtAuthoritiesManager.findUniqueBy("authorityId", authorityId);
		model.addAttribute("tXtAuthorities", tXtAuthorities);
		return "admin/authorities/authorities_resources_list";
	}
	/**
	 * 添加权限资源
	 * @param authorityId
	 * @param checkdel
	 * @return
	 */
	@RequestMapping(value="authorities_resources_add",method=RequestMethod.POST)
	@SystemControllerLog(description = "授权资源权限")
	public @ResponseBody String authoritiesResourcesAdd(String authorityId,String[] checkdel){
		if(checkdel == null || checkdel.length < 1 
				|| StringHelper.isEmpty(authorityId)){
			return null;		
		}
		TXtAuthoritiesResources item = null;
		for(String id : checkdel){
			List uid = tXtAuthoritiesResourcesManager.find("select a.id from TXtAuthoritiesResources a where a.authorityId=? and a.resourceId=?", authorityId,id);
			if(uid == null||uid.size()<1) {
				item = new TXtAuthoritiesResources();
				item.setResourceId(id);
				item.setAuthorityId(authorityId);
				item.setId(IdGenerator.getInstance().getUniqTime()+"");
				tXtAuthoritiesResourcesManager.save(item);
			}
		}
		return "sucess";
	}
	/**
	 * 删除权限资源
	 * @param checkdel
	 * @return
	 */
	@RequestMapping(value="authorities_resources_dell",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除资源权限授权关系")
	public @ResponseBody  String authoritiesResourcesDell(String[] checkdel){
		String result="";
		try{
			if(checkdel == null || checkdel.length < 1){
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));
			}
			List<String> lst = new ArrayList<String>();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtAuthoritiesResources> items = tXtAuthoritiesResourcesManager.findByIds(lst);
			
			for(Object o : items)
				tXtAuthoritiesResourcesManager.remove(o);
			result = "sucess";
		} catch (Exception ex) {
		}
		return result;
	}
	
	/**
	 * 获取资源树 json串
	 * @return
	 */
	public String getResourcesTreeJson(String authorityId){
		JSONObject one = new JSONObject();
		one.put("id", "-1");
		one.put("name", "资源树");
		one.put("pId", "");
		one.put("isParent", true);
		one.put("nocheck", true);
		one.put("open", true);
		List<TXtResources> resources = tXtResourcesManager.findBy("appId", SpringSecurityUtils.getCurrentUserAppId());
		JSONObject node = null;
		JSONArray jaTree = new JSONArray();
		TXtAuthoritiesResources authoritiesResources = null;
		Map<String,Object> map = new HashMap<String,Object>();
		for(TXtResources resource:resources){
			node = new JSONObject();
			node.put("id", resource.getResourceId());
			node.put("name", resource.getResourceName());
			node.put("pId", "-1");
			
			map.clear();
			map.put("authorityId", authorityId);
			map.put("resourceId", resource.getResourceId());
			authoritiesResources = tXtAuthoritiesResourcesManager.findUnique(" from TXtAuthoritiesResources where authorityId=:authorityId and resourceId=:resourceId ", map);
			if(authoritiesResources!=null){
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
	public void settXtAuthoritiesResourcesManager(TXtAuthoritiesResourcesManager tXtAuthoritiesResourcesManager)
	{
		this.tXtAuthoritiesResourcesManager = tXtAuthoritiesResourcesManager;
	}
	@Resource
	public void settXtResourcesManager(TXtResourcesManager tXtResourcesManager)
	{
		this.tXtResourcesManager = tXtResourcesManager;
	}
	@Resource
	public void settXtAuthoritiesManager(TXtAuthoritiesManager tXtAuthoritiesManager)
	{
		this.tXtAuthoritiesManager = tXtAuthoritiesManager;
	}
	
	
}

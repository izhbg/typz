package com.izhbg.typz.sso.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.izhbg.typz.sso.auth.dto.TXtGnjs;
import com.izhbg.typz.sso.auth.dto.TXtGnjsZy;
import com.izhbg.typz.sso.auth.dto.TXtResources;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.manager.TXtResourcesManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
import com.mysql.jdbc.Messages;

/**
 * 
* @ClassName: ResourcesController 
* @Description: 资源管理
* @author caixl 
* @date 2016-5-9 上午9:35:25 
*
 */
@Controller
@RequestMapping("/resources")
public class ResourcesController
{
	private TXtYhManager tXtYhManager;
	
	private TXtResourcesManager tXtResourcesManager;
	
	/**
	 * 资源列表
	 * @param page
	 * @param parameterMap
	 * @param model
	 * @return
	 */
	@RequestMapping("resources_list")
	 public String list(@ModelAttribute  Page page,
	            @RequestParam Map<String, Object> parameterMap, Model model) {
		
		String appId = SpringSecurityUtils.getCurrentUserAppId();
		
		StringBuffer str = new StringBuffer(" from TXtResources where appId=:appId");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appId", appId);
		
		page = tXtResourcesManager.pagedQuery(str.toString(), page.getPageNo(), page.getPageSize(), params);
		
		model.addAttribute("page", page);
		model.addAttribute("parameterMap", parameterMap);
		return "admin/resources/resources_list";
	}
	/**
	 * 编辑资源信息
	 * @param resourcesId
	 * @param model
	 * @param parameterMap
	 * @return
	 */
	@RequestMapping("resources_edit")
	public String roleEdit(String resourceId, 
						   Model model,
						   @RequestParam Map<String, Object> parameterMap) {
		try {
			
			TXtResources tXtResources = null;
			if(StringHelper.isNotEmpty(resourceId)){
				tXtResources = tXtResourcesManager.findUniqueBy("resourceId", resourceId);
			}else{
				tXtResources = new TXtResources();
			}
			model.addAttribute("tXtResources", tXtResources);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/resources/resources_edit";
	}
	/**
	 * 添加或更新资源信息
	 * @param tXtResources
	 * @param model
	 * @param appId2
	 * @return
	 */
	@RequestMapping(value="resources_addORupdate",method=RequestMethod.POST)
	public String addRole(TXtResources tXtResources, Model model){
		
		if(StringHelper.isEmpty(tXtResources.getResourceName())
				||StringHelper.isEmpty(tXtResources.getResourceString())){
			return null;
		}
		
		if(StringHelper.isEmpty(tXtResources.getResourceId())){
			tXtResources.setResourceId(IdGenerator.getInstance().getUniqTime()+"");
			tXtResources.setAppId(SpringSecurityUtils.getCurrentUserAppId());
			tXtResourcesManager.save(tXtResources);
		}else{
			tXtResources.setAppId(SpringSecurityUtils.getCurrentUserAppId());
			tXtResourcesManager.update(tXtResources);
		}
		
		return "redirect:/resources/resources_list.izhbg";
	}
	/**
	 * 删除资源
	 * @param checkdel
	 * @return
	 */
	@RequestMapping(value="resources_dell",method=RequestMethod.POST)
	public @ResponseBody  String deleteResources(String[] checkdel){
		String result="";
		try{
			if(checkdel == null || checkdel.length < 1){
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
			}
			List<String> lst = new ArrayList<String>();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtResources> items = tXtResourcesManager.findByIds(lst);
			
			for(Object o : items)
				tXtResourcesManager.remove(o);
			result = "sucess";
		} catch (Exception ex) {
		}
		return result;
	}
	/**
	 * 更新状态
	 * @param checkdel
	 * @return
	 */
	@RequestMapping(value="resources_updStatus",method=RequestMethod.POST)
	public @ResponseBody  String updRoleStatus(String[] checkdel){
		String result="";
		try {
			
			if (checkdel == null || checkdel.length < 1) {
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));
			}
			List lst = new ArrayList();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtResources> itemLst = (List<TXtResources>)tXtResourcesManager.findByIds(lst); 
			
			for(TXtResources item : itemLst ){
				if(item.getEnabled()!=null&&item.getEnabled()==2){
					item.setEnabled(1);
				}else{
					item.setEnabled(2);
				}
				tXtResourcesManager.update(item);
			}
			result = "sucess";
		} catch (Exception ex) {
			
		}
		
		return result;
	}
	
	@Resource	
	public void settXtYhManager(TXtYhManager tXtYhManager)
	{
		this.tXtYhManager = tXtYhManager;
	}
	@Resource	
	public void settXtResourcesManager(TXtResourcesManager tXtResourcesManager)
	{
		this.tXtResourcesManager = tXtResourcesManager;
	}
	
	

}

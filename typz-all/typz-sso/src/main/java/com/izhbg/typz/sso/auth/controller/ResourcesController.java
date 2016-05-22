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
import com.izhbg.typz.sso.annotation.SystemControllerLog;
import com.izhbg.typz.sso.auth.dto.TXtGnjs;
import com.izhbg.typz.sso.auth.dto.TXtGnjsZy;
import com.izhbg.typz.sso.auth.dto.TXtResources;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.manager.TXtResourcesManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.auth.service.ResourcesService;
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

    	private ResourcesService resourcesService;
	/**
	 * 资源列表
	 * @param page
	 * @param parameterMap
	 * @param model
	 * @return
	 */
	@RequestMapping("resources_list")
	 public String list(@ModelAttribute  Page page,
	            @RequestParam Map<String, Object> parameterMap, Model model)throws Exception {
		String appId = SpringSecurityUtils.getCurrentUserAppId();
		model.addAttribute("page", resourcesService.queryPageList(page, appId));
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
	@SystemControllerLog(description = "编辑资源")
	public String roleEdit(String resourceId, 
						   Model model,
						   @RequestParam Map<String, Object> parameterMap) {
		try {
			
			TXtResources tXtResources = null;
			if(StringHelper.isNotEmpty(resourceId)){
				tXtResources = resourcesService.queryById(resourceId);
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
	@SystemControllerLog(description = "添加或更新资源")
	public String addRole(TXtResources tXtResources, Model model) throws Exception{
		
		if(StringHelper.isEmpty(tXtResources.getResourceName())
				||StringHelper.isEmpty(tXtResources.getResourceString())){
			return null;
		}
		
		if(StringHelper.isEmpty(tXtResources.getResourceId())){
			tXtResources.setResourceId(IdGenerator.getInstance().getUniqTime()+"");
			tXtResources.setAppId(SpringSecurityUtils.getCurrentUserAppId());
			resourcesService.add(tXtResources);
		}else{
			tXtResources.setAppId(SpringSecurityUtils.getCurrentUserAppId());
			resourcesService.update(tXtResources);
		}
		
		return "redirect:/resources/resources_list.izhbg";
	}
	/**
	 * 删除资源
	 * @param checkdel
	 * @return
	 */
	@RequestMapping(value="resources_dell",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除资源")
	public @ResponseBody  String deleteResources(String[] checkdel) throws Exception{
		String result="";
		if(checkdel == null || checkdel.length < 1){
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
		}
		resourcesService.deleteByIds(checkdel);
		result = "sucess";
		return result;
	}
	/**
	 * 更新状态
	 * @param checkdel
	 * @return
	 */
	@RequestMapping(value="resources_updStatus",method=RequestMethod.POST)
	@SystemControllerLog(description = "更新资源状态")
	public @ResponseBody  String updRoleStatus(String[] checkdel) throws Exception{
		String result="";
		if (checkdel == null || checkdel.length < 1) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));
		}
		resourcesService.updateStatus(checkdel);
		result = "sucess";
		return result;
	}
	@Resource
	public void setResourcesService(ResourcesService resourcesService) {
	    this.resourcesService = resourcesService;
	}
	
	
	

}

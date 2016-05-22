package com.izhbg.typz.sso.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
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
import com.izhbg.typz.sso.annotation.SystemControllerLog;
import com.izhbg.typz.sso.auth.dto.TXtGnjs;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.dto.TXtYhGnjs;
import com.izhbg.typz.sso.auth.dto.UserRoleQuery;
import com.izhbg.typz.sso.auth.manager.TXtGnjsManager;
import com.izhbg.typz.sso.auth.manager.TXtYhGnjsManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.auth.service.TXtGnjsService;
import com.izhbg.typz.sso.auth.service.TXtJgService;
import com.izhbg.typz.sso.auth.service.TXtYhService;
import com.izhbg.typz.sso.auth.service.UserRolesService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
import com.mysql.jdbc.Messages;

@Controller
@RequestMapping("/user-role")
public class UserRolesController {
	private TXtJgService tXtJgService;
	private TXtGnjsService tXtGnjsService;
	private UserRolesService userRolesService;
	private TXtYhService tXtYhService;
	
	
	
	@RequestMapping("user-role-list")
	 public String list(@ModelAttribute  Page page,
	            @ModelAttribute UserRoleQuery userRoleQuery, Model model) throws Exception{
		if(StringHelper.isEmpty(userRoleQuery.getCurrentAppId()))
		    userRoleQuery.setCurrentAppId(SpringSecurityUtils.getCurrentUserAppId());
		if(StringHelper.isEmpty(userRoleQuery.getYhId()))
		    userRoleQuery.setYhId(SpringSecurityUtils.getCurrentUserId());
		TXtYh user = tXtYhService.findByYhId(userRoleQuery.getYhId());
		String result = userRolesService.getJsJson(userRoleQuery.getYhId(), userRoleQuery.getCurrentAppId());
		model.addAttribute("page", userRolesService.queryPageList(page, userRoleQuery));
		model.addAttribute("result", result);
		model.addAttribute("user", user);
		model.addAttribute("parameterMap", userRoleQuery);
		model.addAttribute("currentAppId", userRoleQuery.getCurrentAppId());
		
		return "admin/guser/dirguserrole";
	}
	@RequestMapping(value="addUserRloe",method=RequestMethod.POST)
	@SystemControllerLog(description = "用户授权角色")
	public @ResponseBody String addUserRloe(String yhId,String[] checkdel)throws Exception{
		if(checkdel == null || checkdel.length < 1 
				|| StringHelper.isEmpty(yhId)){
			return null;		
		}
		userRolesService.add(yhId, checkdel);
		return "sucess";
	}
	@RequestMapping(value="delUserRloe",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除用户角色")
	public @ResponseBody  String delUserRloe(String[] checkdel) throws Exception{
		String result="";
		if(checkdel == null || checkdel.length < 1){
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
			return null;		
		}
		userRolesService.deleteByIds(checkdel);
		result = "sucess";
		return result;
	}
	@Resource
	public void settXtJgService(TXtJgService tXtJgService) {
	    this.tXtJgService = tXtJgService;
	}
	@Resource
	public void settXtGnjsService(TXtGnjsService tXtGnjsService) {
	    this.tXtGnjsService = tXtGnjsService;
	}
	@Resource
	public void setUserRolesService(UserRolesService userRolesService) {
	    this.userRolesService = userRolesService;
	}
	@Resource
	public void settXtYhService(TXtYhService tXtYhService) {
	    this.tXtYhService = tXtYhService;
	}
	
	

	
	
	

}

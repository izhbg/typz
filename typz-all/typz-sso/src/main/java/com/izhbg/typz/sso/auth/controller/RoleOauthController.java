package com.izhbg.typz.sso.auth.controller;

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
import com.izhbg.typz.sso.annotation.SystemControllerLog;
import com.izhbg.typz.sso.auth.dto.TXtGnjs;
import com.izhbg.typz.sso.auth.service.RoleAuthoritiesService;
import com.izhbg.typz.sso.auth.service.RoleOauthService;
import com.izhbg.typz.sso.auth.service.TXtGnjsService;
import com.mysql.jdbc.Messages;

@Controller
@RequestMapping("/role_oauth")
public class RoleOauthController
{

	private TXtGnjsService tXtGnjsService;
	private RoleOauthService roleOauthService;
	
	@RequestMapping("role_oauth_list")
	 public String list(@ModelAttribute  Page page,
	            @RequestParam Map<String, Object> parameterMap, Model model)throws Exception {
		
		String jsDm = parameterMap.get("jsDm")==null?"":parameterMap.get("jsDm").toString();
		page = roleOauthService.queryPageList(page, jsDm);
		model.addAttribute("page", page);
		model.addAttribute("parameterMap", parameterMap);
		//获取资源列表形成结构树
		model.addAttribute("result", roleOauthService.getOauthTreeJson(jsDm));
		TXtGnjs gnjs = tXtGnjsService.queryById(jsDm);
		model.addAttribute("gnjs", gnjs);
		return "admin/role/role_oauth_list";
	}
	/**
	 * 添加权限资源
	 * @param authorityId
	 * @param checkdel
	 * @return
	 */
	@RequestMapping(value="role_oauth_add",method=RequestMethod.POST)
	@SystemControllerLog(description = "角色授权接口规则")
	public @ResponseBody String authoritiesResourcesAdd(String gnjsDm,String[] checkdel)throws Exception{
		if(checkdel == null || checkdel.length < 1 
				|| StringHelper.isEmpty(gnjsDm)){
			return null;		
		}
		try{
			roleOauthService.add(gnjsDm, checkdel);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "sucess";
	}
	/**
	 * 删除权限资源
	 * @param checkdel
	 * @return
	 */
	@RequestMapping(value="role_oauth_dell",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除角色接口规则")
	public @ResponseBody  String authoritiesResourcesDell(String[] checkdel)throws Exception{
		String result="";
		try{
			if(checkdel == null || checkdel.length < 1){
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));
			}
			roleOauthService.deleteByIds(checkdel);
			result = "sucess";
		} catch (Exception ex) {
		}
		return result;
	}
	@Resource
	public void settXtGnjsService(TXtGnjsService tXtGnjsService) {
		this.tXtGnjsService = tXtGnjsService;
	}
	@Resource
	public void setRoleOauthService(RoleOauthService roleOauthService) {
		this.roleOauthService = roleOauthService;
	}
	
	
	
}

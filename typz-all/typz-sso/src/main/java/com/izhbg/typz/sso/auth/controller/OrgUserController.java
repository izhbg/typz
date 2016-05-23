package com.izhbg.typz.sso.auth.controller;

import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.JSONParam;
import com.izhbg.typz.sso.annotation.SystemControllerLog;
import com.izhbg.typz.sso.auth.UserAuthDTO;
import com.izhbg.typz.sso.auth.dto.OrgUserQuery;
import com.izhbg.typz.sso.auth.dto.TXtJg;
import com.izhbg.typz.sso.auth.service.OrgUserService;
import com.izhbg.typz.sso.auth.service.TXtJgService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
import com.mysql.jdbc.Messages;

@Controller
@RequestMapping("/org-user")
public class OrgUserController {
	
	private TXtJgService tXtJgService;
	
	private OrgUserService orgUserService;
	
	@RequestMapping("org-user-list")
	 public String list(@ModelAttribute  Page page,
	            @ModelAttribute OrgUserQuery orgUserQuery, Model model)throws Exception {
		if(orgUserQuery==null||StringHelper.isEmpty(orgUserQuery.getCurrentAppId()))
			orgUserQuery.setCurrentAppId(SpringSecurityUtils.getCurrentUserAppId());
		
		TXtJg organ = null;
		if(StringHelper.isNotEmpty(orgUserQuery.getJgId()))
			organ = tXtJgService.queryById(orgUserQuery.getJgId());
		model.addAttribute("page", orgUserService.queryPageList(page, orgUserQuery));
		model.addAttribute("organ", organ);
		model.addAttribute("parameterMap", orgUserQuery);
		model.addAttribute("currentAppId", orgUserQuery.getCurrentAppId());
		
		return "admin/organ/dirorganguser";
	}
	
	@RequestMapping(value="getPostGUserTree",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getPostGUserTree(@RequestParam Map<String, Object> parameterMap)throws Exception{
		
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		
		String id = parameterMap.get("id")==null?null:parameterMap.get("id").toString();
		String result = "";
		JSONArray ja = orgUserService.getSubOrganUserCheck(id,user.getAppId());
		result = ja.toString();
		
		return result;
	}
	@RequestMapping(value="getOrgUserPage",method=RequestMethod.POST)
	public @ResponseBody String getOrgUserPage(@RequestBody JSONParam[] params)throws Exception{
		String result = orgUserService.getOrgUserPage(params);
		return result.substring(1, result.length()-1);
	}
	@RequestMapping(value="addOrgUser",method=RequestMethod.POST)
	@SystemControllerLog(description = "组织机构授权用户")
	public @ResponseBody String addOrgUser(String jgId,String[] checkdel2)throws Exception{
		if(checkdel2 == null || checkdel2.length < 1 
				|| StringHelper.isEmpty(jgId)){
			return null;		
		}
		orgUserService.add(jgId, checkdel2);
		return "sucess";
	}
	@RequestMapping(value="getOrgUser",method=RequestMethod.POST)
	public @ResponseBody String getOrgUser()throws Exception{
		//组织管理 当前登陆用户 所在的应用  切为根节点的 项
		String result = "[]";
		JSONObject jo = orgUserService.getRootOrganCheck("",SpringSecurityUtils.getCurrentUserAppId());
		JSONArray ja = orgUserService.getSubOrganUserCheck("",SpringSecurityUtils.getCurrentUserAppId());
		if(jo.size()!=0)
			ja.add(jo);
		result = ja.toString();
		return result;
	}
	@RequestMapping(value="delOrgUser",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除组织机构授权用户")
	public @ResponseBody  String delPostUser(String[] checkdel)throws Exception{
		String result="";
		if(checkdel == null || checkdel.length < 1){
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
			return null;		
		}
		orgUserService.deleteByIds(checkdel);
		result = "sucess";
		return result;
	}
	@Resource
	public void settXtJgService(TXtJgService tXtJgService) {
		this.tXtJgService = tXtJgService;
	}
	@Resource
	public void setOrgUserService(OrgUserService orgUserService) {
		this.orgUserService = orgUserService;
	}

	
	

	
	

}

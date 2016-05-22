package com.izhbg.typz.sso.auth.controller;

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
import com.izhbg.typz.sso.annotation.SystemControllerLog;
import com.izhbg.typz.sso.auth.dto.RoleFunQuery;
import com.izhbg.typz.sso.auth.dto.TXtGnjs;
import com.izhbg.typz.sso.auth.service.TXtGnjsFunService;
import com.izhbg.typz.sso.auth.service.TXtGnjsService;
import com.izhbg.typz.sso.auth.service.TXtGnzyService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
import com.mysql.jdbc.Messages;

@Controller
@RequestMapping("/rolFun")
public class RoleFunController {
	private TXtGnzyService tXtGnzyService;
	private TXtGnjsFunService tXtGnjsFunService;
	private TXtGnjsService tXtGnjsService;
	
	@RequestMapping("roleFun_list")
	 public String list(@ModelAttribute  Page page,
	            @ModelAttribute RoleFunQuery roleFunQuery, Model model)throws Exception {
		
		if(StringHelper.isEmpty(roleFunQuery.getCurrentAppId()))
		    roleFunQuery.setCurrentAppId(SpringSecurityUtils.getCurrentUserAppId());
		TXtGnjs role = null;
		if(StringHelper.isNotEmpty(roleFunQuery.getJsDm()))
			role = tXtGnjsService.queryById(roleFunQuery.getJsDm()); 
		String result = tXtGnzyService.getFunTreeJson(roleFunQuery.getCurrentAppId());
		model.addAttribute("role", role);
		model.addAttribute("result", result);
		model.addAttribute("page", tXtGnjsFunService.queryPageList(page, roleFunQuery));
		model.addAttribute("parameterMap", roleFunQuery);
		return "admin/role/roleFun_list";
	}
	@RequestMapping(value="getOrgTree",method=RequestMethod.POST)
	public @ResponseBody String getOrgTree(@RequestParam Map<String, Object> parameterMap)throws Exception{
		String jsDm = parameterMap.get("jsDm")==null?"":parameterMap.get("jsDm").toString();
		String root = tXtGnzyService.getRootGnjsDm(SpringSecurityUtils.getCurrentUserAppId());
		String result = null;
		JSONObject jo = tXtGnjsFunService.getRootRoleFunc(root);
		JSONArray ja = tXtGnjsFunService.getSubRoleFunc(root, jsDm,SpringSecurityUtils.getCurrentUserAppId());
		if(ja != null){
			ja.add(jo);
			result = ja.toString();
		}
		if(result==null){
			result = "[]";
		}
		
		return result;
	}
	@RequestMapping(value="getRoleFuncTree",method=RequestMethod.POST)
	public @ResponseBody String getRoleFuncTree(@RequestParam Map<String, Object> parameterMap)throws Exception{
		
		String id = parameterMap.get("id")==null?null:parameterMap.get("id").toString();
		String jsDm = parameterMap.get("jsDm")==null?null:parameterMap.get("jsDm").toString();
		String result = "";
		JSONArray ja = tXtGnjsFunService.getSubRoleFunc(id, jsDm,SpringSecurityUtils.getCurrentUserAppId());
		result = ja.toString();
		return result;
	}
	@RequestMapping(value="addRoleFunc",method=RequestMethod.POST)
	@SystemControllerLog(description = "角色授权功能")
	public @ResponseBody String addRoleFunc(String jsDm,String[] checkdel,String[] isRead,String[] isCreate,String[] isUpdate,String[] isDelete,String[] isAll)throws Exception{
		if(checkdel == null || checkdel.length < 1 
				|| StringHelper.isEmpty(jsDm)){
			return null;		
		}
		tXtGnjsFunService.add(jsDm, checkdel, isRead, isCreate, isUpdate, isDelete, isAll);
		return "sucess";
	}
	@RequestMapping(value="delRoleFunc",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除角色授权的功能")
	public @ResponseBody  String delRoleFunc(String[] checkdel)throws Exception{
		String result="";
		if(checkdel == null || checkdel.length < 1){
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
		}
		tXtGnjsFunService.deleteByIds(checkdel);
		result = "sucess";
		return result;
	}
	@RequestMapping(value="changState",method=RequestMethod.POST)
	@SystemControllerLog(description = "修改角色功能权限")
	public @ResponseBody  String changState(String categery,String uuid)throws Exception{
		String result="";
		if(StringHelper.isEmpty(uuid)||StringHelper.isEmpty(categery)){
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
		}
		tXtGnjsFunService.changState(categery, uuid);
		result = "sucess";
		return result;
	}
	@Resource
	public void settXtGnzyService(TXtGnzyService tXtGnzyService) {
	    this.tXtGnzyService = tXtGnzyService;
	}
	@Resource
	public void settXtGnjsFunService(TXtGnjsFunService tXtGnjsFunService) {
	    this.tXtGnjsFunService = tXtGnjsFunService;
	}
	@Resource
	public void settXtGnjsService(TXtGnjsService tXtGnjsService) {
	    this.tXtGnjsService = tXtGnjsService;
	}
	
	
	
	
	

}

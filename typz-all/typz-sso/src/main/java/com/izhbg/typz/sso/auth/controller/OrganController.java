package com.izhbg.typz.sso.auth.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.common.service.ControllerException;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.StringUtils;
import com.izhbg.typz.sso.annotation.SystemControllerLog;
import com.izhbg.typz.sso.auth.dto.TXtJg;
import com.izhbg.typz.sso.auth.dto.TXtJgQuery;
import com.izhbg.typz.sso.auth.service.TXtJgService;
import com.izhbg.typz.sso.auth.service.TXtYyService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;

@Controller
@RequestMapping("/org")
public class OrganController {
	
	
	private TXtJgService tXtJgService;
	private TXtYyService tXtYyService;	
	
	@RequestMapping("org-list")
	 public String list(@ModelAttribute  Page page,
	            @ModelAttribute TXtJgQuery tXtJgQuery, Model model) throws Exception {
		
		if(StringHelper.isEmpty(tXtJgQuery.getCurrentAppId()))
			tXtJgQuery.setCurrentAppId(SpringSecurityUtils.getCurrentUserAppId());
		page = tXtJgService.queryPageList(page, tXtJgQuery);
		String result = tXtJgService.getJgsJSON(tXtJgQuery.getCurrentAppId());
		model.addAttribute("result", result);
		model.addAttribute("tXtYyList", tXtYyService.queryAll());
		model.addAttribute("page",page);
		model.addAttribute("parameterMap", tXtJgQuery);
		
		return "admin/organ/dirorganaction";
	 }
	
	@RequestMapping("org-edit")
	@SystemControllerLog(description = "编辑组织机构")
	public String orgEdit(@RequestParam Map<String, Object> parameterMap, Model model) throws Exception {
		String jgId= parameterMap.get("jgId")==null?"":parameterMap.get("jgId").toString();
		String currentAppId= StringUtils.getString(parameterMap.get("currentAppId"));
		
		if(StringHelper.isEmpty(currentAppId))
			currentAppId = SpringSecurityUtils.getCurrentUserAppId();
		if(StringHelper.isNotEmpty(jgId))
			model.addAttribute("organ", tXtJgService.queryById(jgId));
		String result = tXtJgService.getJgsJSON(currentAppId);
		model.addAttribute("result", result);
		model.addAttribute("currentAppId", currentAppId);
		
		model.addAttribute("txtYy", tXtYyService.queryAll());
		return "admin/organ/getorganaction";
	}
	
	@RequestMapping(value="getOrgTree")
	public @ResponseBody String getOrgTree(@RequestParam Map<String, Object> parameterMap)throws Exception{
		String id = StringUtils.getString(parameterMap.get("id"));
		String appId = StringUtils.getString(parameterMap.get("appId"));
		if(StringHelper.isEmpty(appId)){
			appId = SpringSecurityUtils.getCurrentUserAppId();
		}
		String result = tXtJgService.getSubOrgan(id.toString(),appId).toString();
		return result;
	}
	@RequestMapping(value="getOrgnTree")
	public @ResponseBody String getOrgnTree(@RequestParam Map<String, Object> parameterMap) throws Exception{
		String currentAppId = StringUtils.getString(parameterMap.get("currentAppId"));
		if(StringHelper.isEmpty(currentAppId))
			currentAppId = SpringSecurityUtils.getCurrentUserAppId();
		String result = tXtJgService.getJgsJSON(currentAppId);
		return result;
	}
	@RequestMapping(value="getGUserOrganCheckTree",method=RequestMethod.POST)
	public @ResponseBody String getGUserOrganCheckTree(@RequestParam Map<String, Object> parameterMap) throws Exception{
		String id = StringUtils.getString(parameterMap.get("id"));
		String jgId = StringUtils.getString(parameterMap.get("jgId"));
		JSONArray ja = tXtJgService.getSubRoleOrgan(id, jgId,SpringSecurityUtils.getCurrentUserAppId());
		return ja.toString();
	}
	@RequestMapping(value="validateJgDm",method=RequestMethod.POST)
	public @ResponseBody String validateJgDm(@RequestParam String jgDm) throws Exception{
		
		String result = "yes";
		if(StringHelper.isNotEmpty(jgDm)){
			TXtJg jg = tXtJgService.queryByJgDm(jgDm);
			if(jg!=null)
				result = "no";
		}
		return result;	
	}
	@RequestMapping(value="addOrgan",method=RequestMethod.POST)
	@SystemControllerLog(description = "添加组织机构")
	public String addOrgan(TXtJg organ,String[] checkdel, Model model,String currentAppId) throws Exception{
		if(StringHelper.isEmpty(organ.getJgDm()) 
				|| StringHelper.isEmpty(organ.getJgMc())){
			throw new ControllerException("参数为空,添加机构信息失败");
		}
		organ.setJgId(IdGenerator.getInstance().getUniqTime()+"");
		organ.setLrRq(new Date());
		organ.setXgRq(new Date());
		organ.setScBj(Integer.valueOf(Constants.UN_DELETE_STATE));
		tXtJgService.add(organ);
		return "redirect:/org/org-list.izhbg?sjjgId="+organ.getSjjgId()+"&currentAppId="+currentAppId;
	}
	
	@RequestMapping(value="updateOrg",method=RequestMethod.POST)
	@SystemControllerLog(description = "更新组织机构")
	public String updateOrg(TXtJg organ,String[] checkdel, Model model,String currentAppId) throws Exception{
		if(StringHelper.isEmpty(organ.getJgId()) 
				|| StringHelper.isEmpty(organ.getJgDm()) 
				|| StringHelper.isEmpty(organ.getJgMc())){
			throw new ControllerException("参数为空,更新机构信息失败");
		}
		tXtJgService.update(organ);
		return "redirect:/org/org-list.izhbg?sjjgId="+organ.getSjjgId()+"&currentAppId="+currentAppId;
		
	}
	
	@RequestMapping(value="deleteOrg",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除组织机构")
	public @ResponseBody  String deleteOrg(String[] checkdel, String sjjgId) throws Exception{
		String result="sucess";
		tXtJgService.deleteByIds(checkdel);
		return result;
	}
	@RequestMapping(value="updOrgStatus",method=RequestMethod.POST)
	@SystemControllerLog(description = "更新组织机构状态")
	public @ResponseBody  String updOrgStatus(String[] checkdel, String sjjgId) throws Exception{
		String result="sucess";
		if(checkdel == null || checkdel.length < 1){
			return null;
		}
		tXtJgService.updateStatus(checkdel);
		return result;
	}
	  // ~ ======================================================================
	@Resource
	public void settXtJgService(TXtJgService tXtJgService) {
		this.tXtJgService = tXtJgService;
	}
	@Resource
	public void settXtYyService(TXtYyService tXtYyService) {
		this.tXtYyService = tXtYyService;
	}
	
	
	
	
}

package com.izhbg.typz.sso.auth.controller;

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

import com.izhbg.typz.base.common.service.ControllerException;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.StringUtils;
import com.izhbg.typz.sso.annotation.SystemControllerLog;
import com.izhbg.typz.sso.auth.dto.TXtGnjs;
import com.izhbg.typz.sso.auth.dto.TXtGnjsQuery;
import com.izhbg.typz.sso.auth.dto.TXtYy;
import com.izhbg.typz.sso.auth.service.TXtGnjsService;
import com.izhbg.typz.sso.auth.service.TXtJgService;
import com.izhbg.typz.sso.auth.service.TXtYhService;
import com.izhbg.typz.sso.auth.service.TXtYyService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;

@Controller
@RequestMapping("/role")
public class RolesController {
	
	private TXtJgService tXtJgService;
	private TXtYyService tXtYyService;
	private TXtGnjsService tXtGnjsService;
	private TXtYhService tXtYhService;
	
	@RequestMapping("role-list")
	 public String list(@ModelAttribute  Page page,
	            @ModelAttribute TXtGnjsQuery tXtGnjsQuery, Model model) throws Exception {
		
		List<TXtYy> tXtYyList = tXtYyService.queryAll();
		page = tXtGnjsService.qeuryPageList(tXtGnjsQuery, page);
		
		model.addAttribute("tXtYyList", tXtYyList);
		model.addAttribute("page", page);
		model.addAttribute("parameterMap", tXtGnjsQuery);
		
		return "admin/role/dirrole";
	}
	@RequestMapping("role-edit")
	@SystemControllerLog(description = "编辑角色")
	public String roleEdit(String gnjsDm, Model model,
            @RequestParam Map<String, Object> parameterMap)throws Exception {
		
		String result = tXtJgService.getJgsJSON(SpringSecurityUtils.getCurrentUserAppId());
		if(StringHelper.isNotEmpty(gnjsDm))
			model.addAttribute("role", tXtGnjsService.queryById(gnjsDm));
		model.addAttribute("currentYh", tXtYhService.findByYhId(SpringSecurityUtils.getCurrentUserId()));
		model.addAttribute("txtYy", tXtYyService.queryAll());
		model.addAttribute("parameterMap", parameterMap);
		return "admin/role/getrole";
	}
	
	@RequestMapping(value="getRoleOrganCheckTree",method=RequestMethod.POST)
	public @ResponseBody String getRoleOrganCheckTree(@RequestParam Map<String, Object> parameterMap) throws Exception{
		String id = StringUtils.getString(parameterMap.get("id"));
		String result = tXtJgService.getSubOrgan(id, SpringSecurityUtils.getCurrentUserAppId());
		return result;
	}
	
	@RequestMapping(value="validateJsDm",method=RequestMethod.POST)
	public @ResponseBody String validateJsDm(@RequestParam String jsDm) throws Exception{
		String result = "yes";
		TXtGnjs js = tXtGnjsService.queryById(jsDm);
		if(js!=null)
			result = "no";
		return result;
	}
	@RequestMapping(value="addRole",method=RequestMethod.POST)
	@SystemControllerLog(description = "添加角色")
	public String addRole(TXtGnjs role,@RequestParam String appId2) throws Exception{
		if(StringHelper.isEmpty(role.getCode())
				||StringHelper.isEmpty(role.getGnjsMc())){
			throw new ControllerException("参数为空,添加角色失败");
		}
			role.setGnjsDm(IdGenerator.getInstance().getUniqTime()+"");
			tXtGnjsService.add(role);
		return "redirect:/role/role-list.izhbg?appId="+appId2;
	}
	
	@RequestMapping(value="updRole",method=RequestMethod.POST)
	@SystemControllerLog(description = "更新角色")
	public String updRole(TXtGnjs role,@RequestParam String appId2) throws Exception{
		if(StringHelper.isEmpty(role.getGnjsDm())
				||StringHelper.isEmpty(role.getCode())
				||StringHelper.isEmpty(role.getGnjsMc())){
			throw new ControllerException("参数为空,更新角色失败");
		}
		tXtGnjsService.update(role);
		return "redirect:/role/role-list.izhbg?appId="+appId2;
	}
	@RequestMapping(value="deleteRole",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除角色")
	public @ResponseBody  String deleteRole(String[] checkdel) throws Exception{
		String result="";
		tXtGnjsService.deleteByIds(checkdel);
		result = "sucess";
		return result;
	}
	@RequestMapping(value="updRoleStatus",method=RequestMethod.POST)
	@SystemControllerLog(description = "更新角色状态")
	public @ResponseBody  String updRoleStatus(String[] checkdel) throws Exception{
		String result = "sucess";
		tXtGnjsService.updateStatus(checkdel);
		return result;
	}
	
	
	
	
	@Resource
	public void setTXtJgService(TXtJgService xtJgService) {
		tXtJgService = xtJgService;
	}
	@Resource
	public void setTXtYyService(TXtYyService xtYyService) {
		tXtYyService = xtYyService;
	}
	@Resource
	public void settXtJgService(TXtJgService tXtJgService) {
		this.tXtJgService = tXtJgService;
	}
	@Resource
	public void settXtYyService(TXtYyService tXtYyService) {
		this.tXtYyService = tXtYyService;
	}
	@Resource
	public void settXtGnjsService(TXtGnjsService tXtGnjsService) {
		this.tXtGnjsService = tXtGnjsService;
	}
	@Resource
	public void settXtYhService(TXtYhService tXtYhService) {
		this.tXtYhService = tXtYhService;
	}
	
	

}

package com.izhbg.typz.sso.auth.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.hibernate.internal.util.StringHelper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.common.service.ControllerException;
import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.StringUtils;
import com.izhbg.typz.sso.annotation.SystemControllerLog;
import com.izhbg.typz.sso.auth.UserAuthDTO;
import com.izhbg.typz.sso.auth.dto.TXtJg;
import com.izhbg.typz.sso.auth.dto.TXtJgYh;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.dto.TXtYhGnjs;
import com.izhbg.typz.sso.auth.dto.TXtYhQuery;
import com.izhbg.typz.sso.auth.dto.TXtYy;
import com.izhbg.typz.sso.auth.manager.TXtJgManager;
import com.izhbg.typz.sso.auth.manager.TXtJgYhManager;
import com.izhbg.typz.sso.auth.manager.TXtYhGnjsManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.auth.service.TXtJgService;
import com.izhbg.typz.sso.auth.service.TXtYhService;
import com.izhbg.typz.sso.auth.service.TXtYyService;
import com.izhbg.typz.sso.util.SimplePasswordEncoder;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
import com.mysql.jdbc.Messages;

@Controller
@RequestMapping("/user")
public class TXtYhController {
	
	private TXtYhService tXtYhService;
	
	private TXtYyService tXtYyService;
	
	private TXtJgService tXtJgService;
	
	private SimplePasswordEncoder simplePasswordEncoder;
	
	
	@RequestMapping("user-list")
	 public String list(@ModelAttribute  Page page,
	            @ModelAttribute TXtYhQuery tXtYhQuery, Model model) throws Exception {
		//组织管理 当前登陆用户 所在的应用  切为根节点的 项
		TXtYh currentYh = tXtYhService.findByYhId(SpringSecurityUtils.getCurrentUserId());
		if(currentYh==null){
			return null;
		}
		List<TXtYy> tXtYyList = tXtYyService.queryAll();
			
		if(StringHelper.isEmpty(tXtYhQuery.getCurrentAppId()))
			tXtYhQuery.setCurrentAppId(SpringSecurityUtils.getCurrentUserAppId());
		String result = tXtJgService.getJgsJSON(tXtYhQuery.getCurrentAppId());
		page = tXtYhService.queryYhPageList(page, tXtYhQuery);
		model.addAttribute("result", result);
		model.addAttribute("page",page);
		model.addAttribute("parameterMap", tXtYhQuery);
		model.addAttribute("tXtYyList", tXtYyList);
		return "admin/guser/dirguser";
	 }
	
	@RequestMapping("user-edit")
	@SystemControllerLog(description = "编辑用户")
	public String userEdit(@RequestParam Map<String, Object> parameterMap, Model model) throws Exception{
		
		String yhId= StringUtils.getString(parameterMap.get("yhId"));
		String currentAppId= StringUtils.getString(parameterMap.get("currentAppId"));
		TXtYh user = null;
		if(StringHelper.isNotEmpty(yhId))
			user = tXtYhService.findByYhId(yhId);
		String result = tXtJgService.getJgsJSON(currentAppId);
		model.addAttribute("user", user);
		model.addAttribute("result", result);
		model.addAttribute("currentAppId", currentAppId);
		
		List<TXtYy> tXtYyList = tXtYyService.queryAll();
		model.addAttribute("txtYy", tXtYyList);
		return "admin/guser/getguser";
	}
	
	@RequestMapping(value="getOrgTree",method=RequestMethod.POST)
	public @ResponseBody String getOrgTree(@RequestParam Map<String, Object> parameterMap) throws Exception{
		String id = StringUtils.getString(parameterMap.get("id"));
		String appId = StringUtils.getString(parameterMap.get("appId"));
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,获取子节点失败");
		if(StringHelper.isEmpty(appId))
			appId = SpringSecurityUtils.getCurrentUserAppId();
		String result = tXtJgService.getSubOrgan(id, appId);
		
		return result;
	}
	
	@RequestMapping(value="validateYhDm",method=RequestMethod.POST)
	public @ResponseBody String validateYhDm(@RequestParam String username) throws Exception{
		if(StringHelper.isEmpty(username))
			throw new ControllerException("参数为空,验证用户DM失败");
		String result = "yes";
		TXtYh tXtYh  = tXtYhService.findByYhDm(username);
		if(tXtYh!=null){
			result = "no";
		}
		return result;
	}
	
	@RequestMapping(value="getGUserOrganCheckTree",method=RequestMethod.POST)
	public @ResponseBody String getGUserOrganCheckTree(@RequestParam Map<String, Object> parameterMap) throws Exception{
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		Object id = parameterMap.get("id");
		Object jgId = parameterMap.get("jgId");
		String appId = parameterMap.get("appId")==null?"":parameterMap.get("appId")+"";
		if(StringHelper.isEmpty(appId)){
			appId = user.getAppId();
		}
		JSONArray ja = tXtJgService.getSubUserOrgan(id.toString(), jgId.toString(),appId);
		String result = ja.toString();
		return result;
	}
	
	@RequestMapping(value="addGUser",method=RequestMethod.POST)
	@SystemControllerLog(description = "添加用户")
	public String addGUser(TXtYh user,String[] checkdel, Model model,String currentAppId) throws Exception{
		if(StringHelper.isEmpty(user.getYhDm())
				||StringHelper.isEmpty(user.getYhMc())
				||StringHelper.isEmpty(user.getMm())
				||checkdel == null 
				||checkdel.length < 1){
			throw new ControllerException("参数为空,添加用户失败");
		}
		
		user.setYhId(IdGenerator.getInstance().getUniqTime()+"");
		user.setLrRq(new Date());
		user.setXgRq(new Date());
		user.setScBj(Integer.valueOf(Constants.UN_DELETE_STATE));
		if (simplePasswordEncoder != null) {
			user.setMm(simplePasswordEncoder.encode(user.getMm()));
        }
		tXtYhService.add(user,checkdel);
		return "redirect:/user/user-list.izhbg?sjjgId="+user.getJgId()+"&currentAppId="+currentAppId;
		
	}
	
	@RequestMapping(value="updateGUser",method=RequestMethod.POST)
	@SystemControllerLog(description = "更新用户")
	public String updateGUser(TXtYh user,String checkdel,String currentAppId) throws Exception{
		if(StringHelper.isEmpty(user.getYhId())
				||StringHelper.isEmpty(user.getYhDm())
				||StringHelper.isEmpty(checkdel)){
			return null;
		}
		tXtYhService.update(user,checkdel);
		return "redirect:/user/user-list.izhbg?sjjgId="+user.getJgId()+"&currentAppId="+currentAppId;
	}
	
	@RequestMapping(value="deleteGuser",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除用户")
	public @ResponseBody  String deleteGuser(String[] checkdel) throws Exception{
		String result="";
		if(checkdel == null || checkdel.length < 1){
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
		}
		tXtYhService.deleteByIds(checkdel);
		result = "sucess";
		return result;
	}
	
	@RequestMapping(value="removeGuserFromGroup",method=RequestMethod.POST)
	@SystemControllerLog(description = "从组织机构移除用户")
	public @ResponseBody  String removeGuserFromGroup(String[] checkdel, String jgId) throws Exception{
		String result="";
		if((checkdel == null || checkdel.length < 1)&&StringHelper.isEmpty(jgId)){
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
		}
		tXtYhService.removeYhFromJg(checkdel, jgId);
		result = "sucess";
		return result;
	}
	
	@RequestMapping(value="updPassword",method=RequestMethod.POST)
	@SystemControllerLog(description = "重置用户密码")
	public @ResponseBody  String updPassword(String[] checkdel) throws Exception{
		if (checkdel == null || checkdel.length < 1 ) {
			throw new ControllerException("参数为空，重置密码失败");
		}
		tXtYhService.resetPassword(checkdel,simplePasswordEncoder.encode("123456"));
		String result = "sucess";
		return result;
	}
	
	@RequestMapping(value="updGUserStatus",method=RequestMethod.POST)
	@SystemControllerLog(description = "更改用户状态")
	public @ResponseBody  String updGUserStatus(TXtYh user,String[] checkdel, String sjjgId) throws Exception{
		String result="";
		if (checkdel == null || checkdel.length < 1 ) {
			return null;
		}
		tXtYhService.updGUserStatus(checkdel);
		result = "sucess";
		return result;
	}
	
	@RequestMapping("/updateUserInfo")
	@SystemControllerLog(description = "修改用户信息")
	@ResponseBody
	public String updateUserInfo(HttpServletRequest request, HttpServletResponse response ,HttpSession session) throws Exception {
		String userMail = request.getParameter("user");
		if (userMail == null || userMail.length() == 0) {
			return "false";
		}
		
		TXtYh userInfo = tXtYhService.findByYhId(userMail);
		String picId = request.getParameter("picId");
		if (picId != null && !picId.equals("")) {
			userInfo.setPhotoPath(picId);
		}
		
		String nickName = request.getParameter("nickName");
		if (nickName != null && !nickName.equals("")) {
			userInfo.setYhMc(nickName);
		}
		
		String nowPass = request.getParameter("nowPass");
		String changePass = request.getParameter("changePass");
		if (nowPass != null && !nowPass.equals("") && changePass != null && !changePass.equals("")) {
			Object principal =SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!simplePasswordEncoder.matches(nowPass, ((UserDetails)principal).getPassword())) {
				return "当前密码错误!";
			}else {
				if (simplePasswordEncoder != null) {
					userInfo.setMm(simplePasswordEncoder.encode(changePass));
		        }
			}
		}
		tXtYhService.update(userInfo);
		return "true";
	}
	@Resource
	public void settXtYhService(TXtYhService tXtYhService) {
		this.tXtYhService = tXtYhService;
	}
	@Resource
	public void settXtYyService(TXtYyService tXtYyService) {
		this.tXtYyService = tXtYyService;
	}
	@Resource
	public void settXtJgService(TXtJgService tXtJgService) {
		this.tXtJgService = tXtJgService;
	}
	@Resource
	public void setSimplePasswordEncoder(SimplePasswordEncoder simplePasswordEncoder) {
		this.simplePasswordEncoder = simplePasswordEncoder;
	}
	
	
	
}

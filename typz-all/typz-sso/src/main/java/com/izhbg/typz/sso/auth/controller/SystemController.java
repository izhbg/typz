package com.izhbg.typz.sso.auth.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.izhbg.typz.sso.auth.dto.TXtGnzy;
import com.izhbg.typz.sso.auth.dto.TXtYy;
import com.izhbg.typz.sso.auth.manager.TXtGnjsManager;
import com.izhbg.typz.sso.auth.manager.TXtGnzyManager;
import com.izhbg.typz.sso.auth.manager.TXtYyManager;
import com.izhbg.typz.sso.auth.service.TXtYyService;
import com.izhbg.typz.sso.util.SimplePasswordEncoder;

@Controller
@RequestMapping("/sys")
public class SystemController {
	private TXtYyService tXtYyService;
	private SimplePasswordEncoder simplePasswordEncoder;
	
	@RequestMapping("sys_list")
	 public String list(@ModelAttribute  Page page,
	            @RequestParam Map<String, Object> parameterMap, Model model) {
		String code= parameterMap.get("code")==null?"":parameterMap.get("code").toString();
		String appName= parameterMap.get("appName")==null?"":parameterMap.get("appName").toString();
		String classification= parameterMap.get("classification")==null?"":parameterMap.get("classification").toString();
		page = tXtYyService.page(page, code, appName, classification);
		model.addAttribute("page",page);
		model.addAttribute("parameterMap", parameterMap);
		return "admin/system/dirsystem";
	 }
	
	@RequestMapping("sys-edit")
	@SystemControllerLog(description = "编辑应用")
	public String sysEdit(@RequestParam Map<String, Object> parameterMap, Model model)throws Exception {
		String yyId= StringUtils.getString(parameterMap.get("yyId"));
		if(StringHelper.isNotEmpty(yyId))
		    model.addAttribute("app", tXtYyService.getSystem(yyId));
		return "admin/system/getsystem";
	}
	@RequestMapping(value="validateCode",method=RequestMethod.POST)
	public @ResponseBody String validateCode(@RequestParam String code) throws Exception{
		
		String result = "yes";
		if(StringHelper.isEmpty(code))
		    throw new ControllerException("参数为空,验证失败");
		TXtYy yy = tXtYyService.getByCode(code);
		if(yy!=null)
		    result = "no";
		return result;
	}
	@RequestMapping(value="addSystem",method=RequestMethod.POST)
	@SystemControllerLog(description = "添加应用")
	public String addSystem(TXtYy app,String[] checkdel, Model model) throws Exception{
		if(StringHelper.isEmpty(app.getAppName())
				||StringHelper.isEmpty(app.getCode()))
		    throw new ControllerException("参数为空,添加应用信息失败");
		TXtYy tXtYy = tXtYyService.getByCode(app.getCode());
		if(tXtYy != null)
		    throw new ControllerException("系统编码已存在，添加失败");
		app.setYyId(IdGenerator.getInstance().getUniqTime()+"");
		app.setPassword(simplePasswordEncoder.encode(app.getPassword()));
		app.setOperateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
		tXtYyService.add(app);
		return "redirect:/sys/sys_list.izhbg";
	}
	@RequestMapping(value="updateSys",method=RequestMethod.POST)
	@SystemControllerLog(description = "更新应用")
	public String updateSys(TXtYy app)throws Exception{
		
		if(StringHelper.isEmpty(app.getYyId())
				||StringHelper.isEmpty(app.getAppName())
				||StringHelper.isEmpty(app.getCode()))
		    throw new ControllerException("参数为空，更新应用信息失败");
		tXtYyService.update(app);
		return "redirect:/sys/sys_list.izhbg";
		
	}
	
	@RequestMapping(value="deleteSys",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除应用")
	public @ResponseBody  String deleteSys(String[] checkdel) throws Exception{
		String result="";
		if(checkdel == null || checkdel.length < 1){
			result = "请选择你要操作的条目";
		}
		tXtYyService.deleteByIds(checkdel);
		result = "sucess";
		return result;
	}
	@RequestMapping(value="updStatus",method=RequestMethod.POST)
	@SystemControllerLog(description = "更新应用状态")
	public @ResponseBody  String updStatus(String[] checkdel,String type) throws Exception{
		String result="";
		if(checkdel == null || checkdel.length < 1||StringHelper.isEmpty(type))
		    throw new ControllerException("参数为空，更新状态失败");
		tXtYyService.updateStatus(checkdel, simplePasswordEncoder.encode("123456"), type);
		result = "sucess";
		return result;
	}
	  // ~ ======================================================================
	
	@Resource
	public void settXtYyService(TXtYyService tXtYyService) {
	    this.tXtYyService = tXtYyService;
	}
	@Resource
	public void setSimplePasswordEncoder(SimplePasswordEncoder simplePasswordEncoder) {
	    this.simplePasswordEncoder = simplePasswordEncoder;
	}
	

	
	
	
	
}

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

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.IdGenerator;
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
	private TXtYyManager txtYyManager;
	private SimplePasswordEncoder simplePasswordEncoder;
	private TXtGnjsManager txtGnjsManager;
	private TXtGnzyManager txtGnzyManager;
	
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
	public String sysEdit(@RequestParam Map<String, Object> parameterMap, Model model) {
		String yyId= parameterMap.get("yyId")==null?"":parameterMap.get("yyId").toString();
		TXtYy app = null;
		try {
			if(StringHelper.isNotEmpty(yyId)){
				app = txtYyManager.findUniqueBy("yyId", yyId);//QueryCache.get(TXtGnzy.class, gnDm);
			}
			model.addAttribute("app", app);
		} catch (Exception ex) {
		}
		return "admin/system/getsystem";
	}
	@RequestMapping(value="validateCode",method=RequestMethod.POST)
	public @ResponseBody String validateCode(@RequestParam String code){
		
		String result = "yes";
		if(code!=null){
			try {
				List<TXtYy> yhs = txtYyManager.findBy("code", code);
				if(yhs!=null&&yhs.size()>0){
					result = "no";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	@RequestMapping(value="addSystem",method=RequestMethod.POST)
	public String addSystem(TXtYy app,String[] checkdel, Model model){
		if(StringHelper.isEmpty(app.getAppName())
				||StringHelper.isEmpty(app.getCode())){
			return null;
		}
		List liste = txtYyManager.findBy("code", app.getCode());
		if(liste != null&&liste.size()>0){
			return null;
		}
		app.setYyId(IdGenerator.getInstance().getUniqTime()+"");
		app.setPassword(simplePasswordEncoder.encode(app.getPassword()));
		app.setOperateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
		txtYyManager.save(app);
		return "redirect:/sys/sys_list.izhbg";
	}
	@RequestMapping(value="updateSys",method=RequestMethod.POST)
	public String updateSys(TXtYy app,String[] checkdel, Model model){
		
		if(StringHelper.isEmpty(app.getYyId())
				||StringHelper.isEmpty(app.getAppName())
				||StringHelper.isEmpty(app.getCode())){
			return null;
		}
		TXtYy item = txtYyManager.findUniqueBy("yyId", app.getYyId());//QueryCache.get(TXtYy.class, app.getYyId());
		item.setYyId(app.getYyId());
		item.setAppName(app.getAppName());
		item.setChineseName(app.getChineseName());
		item.setDeployUrl(app.getDeployUrl());
		item.setHomePage(app.getHomePage());
		item.setDescription(app.getDescription());
		item.setRespectiveDivisions(app.getRespectiveDivisions());
		item.setCharger(app.getCharger());
		item.setContact(app.getContact());
		item.setLogoMark(app.getLogoMark());
		item.setCode(app.getCode());
		item.setShowFlag(app.getShowFlag());
		item.setLoginFlag(app.getLoginFlag());
		item.setLoginDisplay(app.getLoginDisplay());
		item.setClassification(app.getClassification());
		item.setSortNo(app.getSortNo());
		item.setYxBj(app.getYxBj());
		txtYyManager.update(item);
		return "redirect:/sys/sys_list.izhbg";
		
	}
	
	@RequestMapping(value="deleteSys",method=RequestMethod.POST)
	public @ResponseBody  String deleteSys(String[] checkdel){
		String result="";
		try{
			if(checkdel == null || checkdel.length < 1){
				result = "请选择你要操作的条目";
			}
			for(String s : checkdel) {
				List ids = txtGnjsManager.findBy("appId", s);//new QueryCache("select a.gnjsDm from TXtGnjs a where a.appId =:appId)")
					//.setParameter("appId", s).list(); 
				if(ids != null && ids.size() > 0) {
					return "请先删除该系统对应的功能角色";
				}
			}
			List lst = new ArrayList();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtYy> items = txtYyManager.findByIds(lst);//QueryCache.idToObj(TXtYy.class, lst);
			
			for(Object o : items)
				txtYyManager.remove(o);
			
			List ids2 = null;
			List<TXtGnzy> objs2 = null;
			for(String str:checkdel){
				ids2 = txtGnzyManager.find("select a.gnDm from TXtGnzy a where a.appId =?", str);
				if(ids2!=null&&ids2.size()>0)
					objs2 = txtGnzyManager.findByIds(ids2);
				
				if(objs2!=null&&objs2.size()>0){
					for(TXtGnzy o:objs2){
						txtGnzyManager.remove(o);
					}
				}
			}
			result = "sucess";
		} catch (Exception ex) {
		}
		return result;
	}
	@RequestMapping(value="updStatus",method=RequestMethod.POST)
	public @ResponseBody  String updStatus(String[] checkdel,String type){
		String result="";
		try {
			if(checkdel == null || checkdel.length < 1||StringHelper.isEmpty(type)){
				return null;
			}
			List lst = new ArrayList();
			for(String s : checkdel) 
				lst.add(s);
			
			List<TXtYy> itemLst =txtYyManager.findByIds(lst); //(List<TXtYh>) QueryCache.idToObj(TXtYh.class, lst);
			
			for(TXtYy item : itemLst ){
				if("yxBj".equals(type)){
					if(item.getYxBj()!=null&&item.getYxBj()==2){
						item.setYxBj(1);
					}else{
						item.setYxBj(2);
					}
				}else if("password".equals(type)){
					item.setPassword(simplePasswordEncoder.encode("123456"));
				}else if("showFlag".equals(type)){
					if(item.getShowFlag()!=null&&item.getShowFlag().equals("2")){
						item.setShowFlag("1");
					}else{
						item.setShowFlag("2");
					}
				} else if("loginFlag".equals(type)){
					if(item.getLoginFlag()!=null&&item.getLoginFlag().equals("2")){
						item.setLoginFlag("1");
					}else{
						item.setLoginFlag("2");
					}
				} else if("loginDisplay".equals(type)){
					if(item.getLoginDisplay()!=null&&item.getLoginDisplay().equals("2")){
						item.setLoginDisplay("1");
					}else{
						item.setLoginDisplay("2");
					}
				 }
				
				txtGnzyManager.update(item);
			}
			
			result = "sucess";
		} catch (Exception ex) {
		}
		
		return result;
	}
	  // ~ ======================================================================
	@Resource
	public void setTXtYyService(TXtYyService xtYyService) {
		tXtYyService = xtYyService;
	}
	@Resource
	public void setTxtYyManager(TXtYyManager txtYyManager) {
		this.txtYyManager = txtYyManager;
	}
	@Resource
	public void setSimplePasswordEncoder(SimplePasswordEncoder simplePasswordEncoder) {
		this.simplePasswordEncoder = simplePasswordEncoder;
	}
	@Resource
	public void setTxtGnjsManager(TXtGnjsManager txtGnjsManager) {
		this.txtGnjsManager = txtGnjsManager;
	}
	@Resource
	public void setTxtGnzyManager(TXtGnzyManager txtGnzyManager) {
		this.txtGnzyManager = txtGnzyManager;
	}
	

	
	
	
	
}

package com.izhbg.typz.sso.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONException;

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
import com.izhbg.typz.sso.auth.UserAuthDTO;
import com.izhbg.typz.sso.auth.dto.TXtGnjs;
import com.izhbg.typz.sso.auth.dto.TXtGnjsZy;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.dto.TXtYy;
import com.izhbg.typz.sso.auth.manager.TXtGnjsManager;
import com.izhbg.typz.sso.auth.manager.TXtGnjsZyManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.auth.service.TXtJgService;
import com.izhbg.typz.sso.auth.service.TXtYyService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
import com.mysql.jdbc.Messages;

@Controller
@RequestMapping("/role")
public class RolesController {
	private String code,gnjsMc,openFlag,appId;
	private String yxBj;
	
	private TXtGnjsManager tXtGnjsManager;
	private TXtJgService tXtJgService;
	private TXtGnjsZyManager tXtGnjsZyManager;
	private TXtYyService tXtYyService;
	
	private TXtYhManager tXtYhManager;
	
	@RequestMapping("role-list")
	 public String list(@ModelAttribute  Page page,
	            @RequestParam Map<String, Object> parameterMap, Model model) {
		
		code = parameterMap.get("code")==null?"":parameterMap.get("code").toString();
		gnjsMc = parameterMap.get("gnjsMc")==null?"":parameterMap.get("gnjsMc").toString();
		openFlag = parameterMap.get("openFlag")==null?"":parameterMap.get("openFlag").toString();
		appId = parameterMap.get("appId")==null?"":parameterMap.get("appId").toString();
		yxBj = parameterMap.get("yxBj")==null?"":parameterMap.get("yxBj").toString();
		
		TXtYh currentYh = tXtYhManager.findUniqueBy("yhId", SpringSecurityUtils.getCurrentUserId());
		if(currentYh==null){
			return null;
		}
		if(StringHelper.isEmpty(appId)){
			appId = currentYh.getAppId();
		}
		List<TXtYy> tXtYyList = null;
		String hql = "select a.yhId from TXtYh a,TXtGnjs b,TXtYhGnjs c where a.yhId=c.yhId and c.jsDm=b.gnjsDm and a.yhId=? and b.jgId='2'";
		List list22 = tXtYhManager.find(hql, SpringSecurityUtils.getCurrentUserId());
		if(list22!=null&&list22.size()>0){
			currentYh.setIsadmin(2);
		}else{
			currentYh.setIsadmin(1);
		}
		if(currentYh.getIsadmin()==2){
			tXtYyList = tXtYyService.getSystems();
		}else{
			TXtYy txtyy = tXtYyService.getSystem(currentYh.getAppId());
			if(txtyy!=null){
				tXtYyList = new ArrayList<TXtYy>();
				tXtYyList.add(txtyy);
			}
			
		}
		
		StringBuffer str = new StringBuffer("select a.gnjsDm from TXtGnjs a ");
		
		str.append(getWhere()).append(getOrder(page));
		
		page = tXtGnjsManager.pagedQuery(str.toString(), page.getPageNo(), page.getPageSize(), setWhere());
		
		List<String> list = (List)page.getResult();
		List<TXtGnjs> listYh=null;
		try {
			if(list!=null&&list.size()>0)
				listYh = tXtGnjsManager.findByIds(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//find(sb.toString(), list.toArray()).list();
		page.setResult(listYh);
		
		model.addAttribute("tXtYyList", tXtYyList);
		model.addAttribute("page", page);
		model.addAttribute("parameterMap", parameterMap);
		return "admin/role/dirrole";
	}
	@RequestMapping("role-edit")
	public String roleEdit(String gnjsDm, Model model,
            @RequestParam Map<String, Object> parameterMap) {
		try {
			TXtYh currentYh = tXtYhManager.findUniqueBy("yhId", SpringSecurityUtils.getCurrentUserId());
			String hql = "select a.yhId from TXtYh a,TXtGnjs b,TXtYhGnjs c where a.yhId=c.yhId and c.jsDm=b.gnjsDm and a.yhId=? and b.jgId='2'";
			List list = tXtYhManager.find(hql, SpringSecurityUtils.getCurrentUserId());
			if(list!=null&&list.size()>0){
				currentYh.setIsadmin(2);
			}else{
				currentYh.setIsadmin(1);
			}
			
			TXtGnjs role = null;
			if(StringHelper.isNotEmpty(gnjsDm)){
				role = tXtGnjsManager.findUniqueBy("gnjsDm", gnjsDm);//QueryCache.get(TXtGnjs.class, gnjsDm);
				model.addAttribute("role", role);
			}else{
				role = new TXtGnjs();
			}
			
			/*JSONObject jo = tXtJgService.getRootRoleOrgan("22", role.getJgId());
			JSONArray ja = tXtJgService.getSubRoleOrgan("22", role.getJgId(),currentYh.getAppId());
			ja.put(jo);
			String result = ja.toString();*/
			
			List<TXtYy> tXtYyList = null;
			if(currentYh.getIsadmin()==2){
				tXtYyList = tXtYyService.getSystems();
			}else{
				TXtYy txtyy = tXtYyService.getSystem(currentYh.getAppId());
				if(txtyy!=null){
					tXtYyList = new ArrayList<TXtYy>();
					tXtYyList.add(txtyy);
				}
				
			}
			model.addAttribute("currentYh", currentYh);
			model.addAttribute("txtYy", tXtYyList);
			
			//model.addAttribute("result", result);
			model.addAttribute("parameterMap", parameterMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "admin/role/getrole";
	}
	@RequestMapping(value="getRoleOrganCheckTree",method=RequestMethod.POST)
	public @ResponseBody String getRoleOrganCheckTree(@RequestParam Map<String, Object> parameterMap){
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		Object id = parameterMap.get("id");
		Object jgId = parameterMap.get("jgId");
		String result = "";
		if(id!=null){
			try {
				result = tXtJgService.getSubRoleOrgan(id.toString(),jgId==null?"":jgId.toString(),user.getAppId()).toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	@RequestMapping(value="validateJsDm",method=RequestMethod.POST)
	public @ResponseBody String validateJsDm(@RequestParam String jsDm){
		
		String result = "yes";
		if(jsDm!=null){
			try {
				List<TXtGnjs> yhs = tXtGnjsManager.findBy("code", jsDm);
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
	@RequestMapping(value="addRole",method=RequestMethod.POST)
	public String addRole(TXtGnjs role, Model model,@RequestParam String appId2){
		if(StringHelper.isEmpty(role.getCode())
				||StringHelper.isEmpty(role.getGnjsMc())){
			return null;
		}
			role.setGnjsDm(IdGenerator.getInstance().getUniqTime()+"");
			tXtGnjsManager.save(role);
		return "redirect:/role/role-list.izhbg?appId="+appId2;
	}
	@RequestMapping(value="updRole",method=RequestMethod.POST)
	public String updRole(TXtGnjs role, Model model,@RequestParam String appId2){
		if(StringHelper.isEmpty(role.getGnjsDm())
				||StringHelper.isEmpty(role.getCode())
				||StringHelper.isEmpty(role.getGnjsMc())){
			return null;
		}
		tXtGnjsManager.update(role);
		return "redirect:/role/role-list.izhbg?appId="+appId2;
	}
	@RequestMapping(value="deleteRole",method=RequestMethod.POST)
	public @ResponseBody  String deleteRole(String[] checkdel){
		String result="";
		try{
			if(checkdel == null || checkdel.length < 1){
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
			}
			List lst = new ArrayList();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtGnjs> items = tXtGnjsManager.findByIds(lst);//QueryCache.idToObj(TXtGnjs.class, lst);
			
			for(Object o : items)
				tXtGnjsManager.remove(o);
			List<TXtGnjsZy> objs1 = tXtGnjsZyManager.findBy("jsDm", checkdel);//QueryCache.idToObj(TXtGnjsZy.class, ids1);
			List<TXtGnjs> objs2 = tXtGnjsManager.findBy("jsDm", checkdel);//QueryCache.idToObj(TXtYhGnjs.class, ids2);
			tXtGnjsZyManager.remove(objs1);
			tXtGnjsManager.remove(objs2);
			result = "sucess";
		} catch (Exception ex) {
		}
		return result;
	}
	@RequestMapping(value="updRoleStatus",method=RequestMethod.POST)
	public @ResponseBody  String updRoleStatus(String[] checkdel){
		String result="";
		try {
			
			if (checkdel == null || checkdel.length < 1) {
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));
			}
			List lst = new ArrayList();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtGnjs> itemLst = (List<TXtGnjs>)tXtGnjsManager.findByIds(lst); //QueryCache.idToObj(TXtGnjs.class, lst);
			
			for(TXtGnjs item : itemLst ){
				if(item.getYxBj()!=null&&item.getYxBj()==2){
					item.setYxBj(1);
				}else{
					item.setYxBj(2);
				}
				tXtGnjsManager.update(item);
			}
			result = "sucess";
		} catch (Exception ex) {
		}
		
		return result;
	}
	
	
	
	public String getOrder(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? " order by "+page.getOrderBy() +" "+ page.getOrder() : " order by a.gnjsDm  ";
	}
	public String getWhere() {
		StringBuffer sb = new StringBuffer("where 1=1");
		if(StringHelper.isNotEmpty(code))
			sb.append(" and a.code like :code ");
		if(StringHelper.isNotEmpty(gnjsMc))
			sb.append(" and a.gnjsMc like :gnjsMc ");
		if(StringHelper.isNotEmpty(yxBj))
			sb.append(" and a.yxBj = :yxBj ");
		if(StringHelper.isNotEmpty(appId))
			sb.append(" and a.appId = :appId ");
		return sb.toString();
	}
	
	public Map<String, Object> setWhere() {
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringHelper.isNotEmpty(code)) 
			params.put("code", "%" + code.trim() + "%");
		if(StringHelper.isNotEmpty(gnjsMc))
			params.put("gnjsMc", "%" + gnjsMc.trim() + "%");
		if(StringHelper.isNotEmpty(yxBj))
			params.put("yxBj", Integer.parseInt(yxBj));
		if(StringHelper.isNotEmpty(appId))
			params.put("appId", appId.trim());
		
		return params;
			
	}
	@Resource
	public void setTXtGnjsManager(TXtGnjsManager xtGnjsManager) {
		tXtGnjsManager = xtGnjsManager;
	}
	@Resource
	public void setTXtJgService(TXtJgService xtJgService) {
		tXtJgService = xtJgService;
	}
	@Resource
	public void setTXtGnjsZyManager(TXtGnjsZyManager xtGnjsZyManager) {
		tXtGnjsZyManager = xtGnjsZyManager;
	}
	@Resource
	public void setTXtYyService(TXtYyService xtYyService) {
		tXtYyService = xtYyService;
	}
	@Resource
	public void setTXtYhManager(TXtYhManager xtYhManager) {
		tXtYhManager = xtYhManager;
	}
	
	

}

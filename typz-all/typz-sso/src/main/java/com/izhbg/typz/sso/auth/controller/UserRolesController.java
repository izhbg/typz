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
import com.izhbg.typz.sso.auth.manager.TXtGnjsManager;
import com.izhbg.typz.sso.auth.manager.TXtYhGnjsManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.auth.service.TXtGnjsService;
import com.izhbg.typz.sso.auth.service.TXtJgService;
import com.mysql.jdbc.Messages;

@Controller
@RequestMapping("/user-role")
public class UserRolesController {
	private String yhId,code,gnjsMc,jgId,yyId = Constants.APP_DEFAULT;
	
	private TXtYhManager xtYhManager;
	private TXtGnjsManager xtGnjsManager;
	private TXtYhGnjsManager xtYhGnjsManager;
	private TXtJgService tXtJgService;
	private TXtGnjsService tXtGnjsService;
	
	
	
	@RequestMapping("user-role-list")
	 public String list(@ModelAttribute  Page page,
	            @RequestParam Map<String, Object> parameterMap, Model model) {
		yhId = parameterMap.get("yhId")==null?"":parameterMap.get("yhId").toString();
		code = parameterMap.get("code")==null?"":parameterMap.get("code").toString();
		gnjsMc = parameterMap.get("gnjsMc")==null?"":parameterMap.get("gnjsMc").toString();
		yyId = parameterMap.get("yyId")==null?Constants.APP_DEFAULT:parameterMap.get("yyId").toString();
		jgId = parameterMap.get("jgId")==null?"":parameterMap.get("jgId").toString();
		String currentAppId = parameterMap.get("currentAppId")==null?"":parameterMap.get("currentAppId").toString();
		
		TXtYh user = null;
		if(StringHelper.isNotEmpty(yhId))
			user = xtYhManager.findUniqueBy("yhId", yhId);//QueryCache.get(TXtYh.class, yhId);
		StringBuffer sb = new StringBuffer("select a.uuid from TXtYhGnjs a ,TXtGnjs b where a.jsDm=b.gnjsDm  ");
		sb.append(getWhere());
		sb.append(getOrder(page));
		
		page = xtYhGnjsManager.pagedQuery(sb.toString(), page.getPageNo(), page.getPageSize(), setWhere());
		
		List<String> list = (List)page.getResult();
		List<TXtYhGnjs> listYh=null;
		try {
			if(list!=null&&list.size()>0)
				listYh = xtYhGnjsManager.findByIds(list);
			if(listYh!=null)
			for(TXtYhGnjs o : listYh) {
				TXtGnjs gnjs = xtGnjsManager.findUniqueBy("gnjsDm", o.getJsDm());//QueryCache.get(TXtGnzy.class, o.getGnzyDm()); 
				o.setGnjs(gnjs);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//find(sb.toString(), list.toArray()).list();
		page.setResult(listYh);
		
		
		String result = "[]";
		if(StringHelper.isNotEmpty(yhId)){
			try {
				JSONObject jo = tXtJgService.getRootGUserRole();
				JSONArray ja = tXtGnjsService.getSubGUserRole(yhId, user.getAppId());
				ja.add(jo);
				result = ja.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("result", result);
		model.addAttribute("user", user);
		model.addAttribute("parameterMap", parameterMap);
		model.addAttribute("currentAppId", currentAppId);
		
		return "admin/guser/dirguserrole";
	}
	@RequestMapping(value="addUserRloe",method=RequestMethod.POST)
	@SystemControllerLog(description = "用户授权角色")
	public @ResponseBody String addUserRloe(String yhId,String[] checkdel){
		if(checkdel == null || checkdel.length < 1 
				|| StringHelper.isEmpty(yhId)){
			return null;		
		}
		
		TXtYhGnjs item;
		for(String id : checkdel){
			List uid = xtYhGnjsManager.find("select a.uuid from TXtYhGnjs a where a.jsDm=? and a.yhId=?", id,yhId);//new QueryCache()
				//.setParameter("jsDm", id).setParameter("yhId", yhId).setMaxResults(1).uniqueResult();
			if(uid == null||uid.size()<1) {
				item = new TXtYhGnjs();
				item.setJsDm(id);
				item.setYhId(yhId);
				item.setUuid(com.izhbg.typz.base.util.IdGenerator.getInstance().getUniqTime()+"");
				xtYhGnjsManager.save(item);
			}
		}
		return "sucess";
	}
	@RequestMapping(value="delUserRloe",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除用户角色")
	public @ResponseBody  String delUserRloe(String[] checkdel){
		String result="";
		try{
			if(checkdel == null || checkdel.length < 1){
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
				return null;		
			}
			List lst = new ArrayList();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtYhGnjs> items = xtYhGnjsManager.findByIds(lst);//QueryCache.idToObj(TXtYhGnjs.class, lst);
			
			if(items != null)
				for(TXtYhGnjs o : items) {
					xtYhGnjsManager.remove(o);
				}
			result = "sucess";
		} catch (Exception ex) {
		}
		return result;
	}

	public String getOrder(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? " order by "+page.getOrderBy() +" "+ page.getOrder() : " order by a.uuid ";
	}
	public String getWhere() {
		StringBuffer sb = new StringBuffer(" ");
		if(StringHelper.isNotEmpty(yhId))
			sb.append(" and a.yhId = :yhId ");
		if(StringHelper.isNotEmpty(code))
			sb.append(" and b.code like :code ");
		if(StringHelper.isNotEmpty(gnjsMc))
			sb.append(" and b.gnjsMc like :gnjsMc ");
		return sb.toString();
	}
	
	public Map<String, Object> setWhere() {
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringHelper.isNotEmpty(yhId)) 
			params.put("yhId", yhId.trim());
		if(StringHelper.isNotEmpty(code))
			params.put("code", "%" + code.trim() + "%");
		if(StringHelper.isNotEmpty(gnjsMc))
			params.put("gnjsMc", "%" + gnjsMc.trim() + "%");
		
		return params;
			
	}

	@Resource
	public void setXtYhManager(TXtYhManager xtYhManager) {
		this.xtYhManager = xtYhManager;
	}
	@Resource
	public void setXtGnjsManager(TXtGnjsManager xtGnjsManager) {
		this.xtGnjsManager = xtGnjsManager;
	}
	@Resource
	public void setXtYhGnjsManager(TXtYhGnjsManager xtYhGnjsManager) {
		this.xtYhGnjsManager = xtYhGnjsManager;
	}
	@Resource
	public void setTXtJgService(TXtJgService xtJgService) {
		tXtJgService = xtJgService;
	}
	@Resource
	public void setTXtGnjsService(TXtGnjsService xtGnjsService) {
		tXtGnjsService = xtGnjsService;
	}
	
	
	

}

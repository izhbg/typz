/*package com.izhbg.typz.sso.auth.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.JSONParam;
import com.izhbg.typz.sso.annotation.SystemControllerLog;
import com.izhbg.typz.sso.auth.UserAuthDTO;
import com.izhbg.typz.sso.auth.dto.TXtJg;
import com.izhbg.typz.sso.auth.dto.TXtJgYh;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.manager.TXtJgManager;
import com.izhbg.typz.sso.auth.manager.TXtJgYhManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.auth.service.TXtJgService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
import com.mysql.jdbc.Messages;

@Controller
@RequestMapping("/org-user")
public class OrgUserController {
	private String jgId,yhDm,yhMc,sjjgId;
	
	private TXtJgManager txtjgManager;
	private TXtJgYhManager tXtJgYhManager;
	
	private TXtYhManager xtYhManager;
	private TXtJgService tXtJgService;
	
	private TXtYhManager tXtYhManager;
	
	
	
	
	@RequestMapping("org-user-list")
	 public String list(@ModelAttribute  Page page,
	            @RequestParam Map<String, Object> parameterMap, Model model) {
		yhDm = parameterMap.get("yhDm")==null?"":parameterMap.get("yhDm").toString();
		jgId = parameterMap.get("jgId")==null?"":parameterMap.get("jgId").toString();
		yhMc = parameterMap.get("yhMc")==null?"":parameterMap.get("yhMc").toString();
		sjjgId = parameterMap.get("sjjgId")==null?"":parameterMap.get("sjjgId").toString();
		String currentAppId = parameterMap.get("currentAppId")==null?"":parameterMap.get("currentAppId").toString();
		
		
		TXtJg organ = null;
		if(StringHelper.isNotEmpty(jgId))
			organ = txtjgManager.findUniqueBy("jgId", jgId);//QueryCache.get(TXtYh.class, yhId);
		StringBuffer sb = new StringBuffer("select a.duId from TXtJgYh a , TXtYh b where a.yhId=b.yhId ");
		sb.append(getWhere());
		sb.append(getOrder(page));
		
		page = tXtJgYhManager.pagedQuery(sb.toString(), page.getPageNo(), page.getPageSize(), setWhere());
		
		List<String> list = (List)page.getResult();
		List<TXtJgYh> listYh=null;
		try {
			if(list!=null&&list.size()>0)
				listYh = tXtJgYhManager.findByIds(list);
			if(listYh!=null)
			for(TXtJgYh o : listYh) {
				TXtYh gnjs = xtYhManager.findUniqueBy("yhId", o.getYhId());// QueryCache.get(TXtGw.class, o.getGwDm()); 
				o.setYh(gnjs);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//find(sb.toString(), list.toArray()).list();
		page.setResult(listYh);
		
		model.addAttribute("page", page);
		model.addAttribute("organ", organ);
		model.addAttribute("parameterMap", parameterMap);
		model.addAttribute("currentAppId", currentAppId);
		
		return "admin/organ/dirorganguser";
	}
	@RequestMapping(value="getPostGUserTree",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getPostGUserTree(@RequestParam Map<String, Object> parameterMap){
		
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		
		String id = parameterMap.get("id")==null?null:parameterMap.get("id").toString();
		String result = "";
		if(StringHelper.isNotEmpty(id)){
			try {
				JSONArray ja = tXtJgService.getSubOrganUserCheck(id,user.getAppId());
				result = ja.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	@RequestMapping(value="getOrgUserPage",method=RequestMethod.POST)
	public @ResponseBody String getOrgUserPage(@RequestBody JSONParam[] params ){
		HashMap parameterMap = new HashMap();
		for(JSONParam son:params){
			parameterMap.put(son.getName(), son.getValue());
		}
		
		yhDm = parameterMap.get("yhDm")==null?"":parameterMap.get("yhDm").toString();
		yhMc = parameterMap.get("yhMc")==null?"":parameterMap.get("yhMc").toString();
		jgId = parameterMap.get("jgId")==null?"":parameterMap.get("jgId").toString();
		String sEcho = parameterMap.get("sEcho")==null?"":parameterMap.get("sEcho").toString();
		String iDisplayLength = parameterMap.get("iDisplayLength")==null?"":parameterMap.get("iDisplayLength").toString();
		String iDisplayStart = parameterMap.get("iDisplayStart")==null?"":parameterMap.get("iDisplayStart").toString();
		
		//int displayLength = iDisplayLength;//pagesize  
        //int displayStart = iDisplayStart ; //cong na tiao kaishi 
		Page page2 = new Page();
		if(StringHelper.isNotEmpty(iDisplayLength)){
			page2.setPageSize(Integer.parseInt(iDisplayLength));
		}
		if(StringHelper.isNotEmpty(iDisplayStart)&&StringHelper.isNotEmpty(iDisplayLength)){
			page2.setPageNo(Integer.parseInt(iDisplayStart)/Integer.parseInt(iDisplayLength)+1);
		}
		
		
		
		StringBuffer sb = new StringBuffer("select a.yhId from TXtYh a ");
		sb.append(getYhWhere());
		sb.append(getYhOrder(page2));
		
		page2 = tXtJgYhManager.pagedQuery(sb.toString(), page2.getPageNo(), page2.getPageSize(), setYhWhere());
		List<String> list = (List)page2.getResult();
		List<TXtYh> listYh=null;
		try {
			if(list!=null&&list.size()>0)
				listYh = xtYhManager.findByIds(list);
			if(listYh!=null)
				page2.setResult(listYh);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(StringHelper.isNotEmpty(jgId)) {
			//List lst = new QueryCache("select a.duId from TXtJgYh a where a.jgId=:jgId").setParameter("jgId", jgId).listCache();
			List<TXtJgYh> objs = tXtJgYhManager.findBy("jgId", jgId);//QueryCache.idToObj(TXtJgYh.class, lst);
			List ids = new ArrayList();
			for(TXtJgYh o : objs)
				ids.add(o.getYhId());
			List<TXtYh> yhs = (List<TXtYh>) page2.getResult();
			for(TXtYh o : yhs)
				if(ids.contains(o.getYhId()))
					o.setCheck(true);
		}
		listYh =page2.getResult()==null?null:(List<TXtYh>)page2.getResult();	
		net.sf.json.JSONArray  json = new net.sf.json.JSONArray ();
		HashMap map = new HashMap();
		map.put("aaData", listYh);
		map.put("sEcho", sEcho);
		for(JSONParam son:params){
			map.put(son.getName(), son.getValue());
		}
		
		map.put("iTotalRecords ", page2.getTotalCount());
		map.put("iTotalDisplayRecords ", page2.getTotalCount());
		if(listYh!=null)
		{
			json.add(map);
			json.listIterator();
		}
		String result = json.toString();
		return result.substring(1, result.length()-1);
	}
	@RequestMapping(value="addOrgUser",method=RequestMethod.POST)
	@SystemControllerLog(description = "组织机构授权用户")
	public @ResponseBody String addOrgUser(String jgId,String[] checkdel2){
		if(checkdel2 == null || checkdel2.length < 1 
				|| StringHelper.isEmpty(jgId)){
			return null;		
		}
		TXtJgYh item;
		for(String id : checkdel2){
			List uid = tXtJgYhManager.find("select a.duId from TXtJgYh a where a.jgId=? and a.yhId=?", jgId,id);//new QueryCache()
			//.setParameter("jsDm", id).setParameter("yhId", yhId).setMaxResults(1).uniqueResult();
			if(uid == null||uid.size()<1) {
				item = new TXtJgYh();
				item.setJgId(jgId);
				item.setYhId(id);
				item.setDuId(IdGenerator.getInstance().getUniqTime()+"");
				tXtJgYhManager.save(item);
			}
		}
		return "sucess";
	}
	@RequestMapping(value="getOrgUser",method=RequestMethod.POST)
	public @ResponseBody String getOrgUser(){
		//组织管理 当前登陆用户 所在的应用  切为根节点的 项
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		String result = "[]";
		try {
			JSONObject jo = tXtJgService.getRootOrganCheck("",user.getAppId());
			JSONArray ja = tXtJgService.getSubOrganUserCheck("",user.getAppId());
			if(jo.size()!=0)
				ja.add(jo);
			result = ja.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	@RequestMapping(value="delOrgUser",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除组织机构授权用户")
	public @ResponseBody  String delPostUser(String[] checkdel){
		String result="";
		try{
			if(checkdel == null || checkdel.length < 1){
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
				return null;		
			}
			List lst = new ArrayList();
			for(String s : checkdel) 
				lst.add(s);
			
			List<TXtJgYh> items = tXtJgYhManager.findByIds(lst);//QueryCache.idToObj(TXtYhGnjs.class, lst);
			
			if(items != null)
				for(TXtJgYh o : items) {
					tXtJgYhManager.remove(o);
				}
			result = "sucess";
		} catch (Exception ex) {
		}
		return result;
	}

	public String getOrder(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? " order by "+page.getOrderBy() +" "+ page.getOrder() : " order by a.duId ";
	}
	public String getWhere() {
		
		StringBuffer sb = new StringBuffer("and b.scBj=" + Constants.UN_DELETE_STATE + " ");
		if(StringHelper.isNotEmpty(jgId))
			sb.append(" and a.jgId = :jgId ");
		if(StringHelper.isNotEmpty(yhDm))
			sb.append(" and b.yhDm like :yhDm ");
		if(StringHelper.isNotEmpty(yhMc))
			sb.append(" and b.yhMc like :yhMc ");
		return sb.toString();
	}
	
	public Map<String, Object> setWhere() {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(StringHelper.isNotEmpty(jgId)) 
			params.put("jgId", jgId.trim());
		if(StringHelper.isNotEmpty(yhDm))
			params.put("yhDm", "%" + yhDm.trim() + "%");
		if(StringHelper.isNotEmpty(yhMc))
			params.put("yhMc", "%" + yhMc.trim() + "%");
		
		return params;
			
	}
	public String getYhOrder(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? " order by "+page.getOrderBy() +" "+ page.getOrder() : " order by a.lrRq ";
	}
	public String getYhWhere() {
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		StringBuffer sb = new StringBuffer("where a.scBj=" + Constants.UN_DELETE_STATE + " and a.appId='"+user.getAppId()+"' ");
		if(StringHelper.isNotEmpty(yhDm))
			sb.append(" and a.yhDm like :yhDm ");
		if(StringHelper.isNotEmpty(yhMc))
			sb.append(" and a.yhMc like :yhMc ");
		return sb.toString();
	}
	
	public Map<String, Object> setYhWhere() {
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringHelper.isNotEmpty(yhDm)) 
			params.put("yhDm", "%" + yhDm + "%");
		if(StringHelper.isNotEmpty(yhMc))
			params.put("yhMc", "%" + yhMc + "%");
		
		return params;
		
	}

	@Resource
	public void setXtYhManager(TXtYhManager xtYhManager) {
		this.xtYhManager = xtYhManager;
	}
	@Resource
	public void setTXtJgService(TXtJgService xtJgService) {
		tXtJgService = xtJgService;
	}
	@Resource
	public void setTxtjgManager(TXtJgManager txtjgManager) {
		this.txtjgManager = txtjgManager;
	}
	@Resource
	public void setTXtJgYhManager(TXtJgYhManager xtJgYhManager) {
		tXtJgYhManager = xtJgYhManager;
	}
	@Resource
	public void setTXtYhManager(TXtYhManager xtYhManager) {
		tXtYhManager = xtYhManager;
	}
	
	
	

}
*/
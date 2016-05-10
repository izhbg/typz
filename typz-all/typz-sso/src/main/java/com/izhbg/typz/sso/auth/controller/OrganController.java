package com.izhbg.typz.sso.auth.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.sso.annotation.SystemControllerLog;
import com.izhbg.typz.sso.auth.UserAuthDTO;
import com.izhbg.typz.sso.auth.dto.TXtJg;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.dto.TXtYy;
import com.izhbg.typz.sso.auth.manager.TXtJgManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.auth.service.ComparatorTXtJg;
import com.izhbg.typz.sso.auth.service.TXtJgService;
import com.izhbg.typz.sso.auth.service.TXtYyService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
import com.mysql.jdbc.Messages;

@Controller
@RequestMapping("/org")
public class OrganController {
	
	private List<String> organs = new ArrayList();
	private String jgId,jgDm,jgMc,sjjgId,yxBj,currentAppId;
	
	private TXtJgManager tXtJgManager;
	private TXtJgService tXtJgService;
	private TXtYyService tXtYyService;	
	private TXtYhManager tXtYhManager;
	@RequestMapping("org-list")
	 public String list(@ModelAttribute  Page page,
	            @RequestParam Map<String, Object> parameterMap, Model model) {
		
		
		jgId= parameterMap.get("jgId")==null?"":parameterMap.get("jgId").toString();
		jgDm= parameterMap.get("jgDm")==null?"":parameterMap.get("jgDm").toString();
		jgMc= parameterMap.get("jgMc")==null?"":parameterMap.get("jgMc").toString();
		yxBj= parameterMap.get("yxBj")==null?"":parameterMap.get("yxBj").toString();
		sjjgId= parameterMap.get("sjjgId")==null?"":parameterMap.get("sjjgId").toString();
	    currentAppId= parameterMap.get("currentAppId")==null?"":parameterMap.get("currentAppId").toString();
		
		//组织管理 当前登陆用户 所在的应用  切为根节点的 项
		String currentjgId = null;
		TXtYh currentYh = tXtYhManager.findUniqueBy("yhId", SpringSecurityUtils.getCurrentUserId());
		String hql = "select a.yhId from TXtYh a,TXtGnjs b,TXtYhGnjs c where a.yhId=c.yhId and c.jsDm=b.gnjsDm and a.yhId=? and b.jgId='2'";
		List list22 = tXtYhManager.find(hql, SpringSecurityUtils.getCurrentUserId());
		if(list22!=null&&list22.size()>0){
			currentYh.setIsadmin(2);
		}else{
			currentYh.setIsadmin(1);
		}
		if(currentYh==null){
			return null;
		}
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
		if(StringHelper.isEmpty(currentAppId)){
			if(tXtYyList!=null&&tXtYyList.size()>0){
				currentAppId = tXtYyList.get(0).getYyId();
			}
		}
		TXtJg tXtJg = tXtJgManager.findUnique("from TXtJg where sjjgId=?", "");
		if(tXtJg!=null){
			currentjgId=tXtJg.getJgId();
		}
		
		if(StringHelper.isEmpty(sjjgId)){
			if(StringHelper.isEmpty(currentAppId)){
				sjjgId = "false";
			}else{
				sjjgId = currentjgId;
			}
			parameterMap.put("sjjgId", sjjgId);
		}
		
		try {
			StringBuffer sb = new StringBuffer("select a.jgId from TXtJg a  ");
			sb.append(getWhere())
			  .append(getOrder(page));
			
			page = tXtJgManager.pagedQuery(sb.toString(), page.getPageNo(), page.getPageSize(), setWhere());
			List<String> list = (List)page.getResult();
			List listYh=null;
			try {
				if(list!=null&&list.size()>0)
					listYh = tXtJgManager.findByIds(list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//find(sb.toString(), list.toArray()).list();
			 if(listYh!=null){
				 ComparatorTXtJg comparator=new ComparatorTXtJg();
				 Collections.sort(listYh, comparator); 
			 }
				 
			page.setResult(listYh);
			
			
			String result = "[]";
			if(StringHelper.isNotEmpty(currentjgId)){
				JSONObject jo = tXtJgService.getRootOrgan(currentjgId);
				
				JSONArray ja = tXtJgService.getSubOrgan(currentjgId,currentAppId);
				if(jo.size()>0)
					ja.add(jo);
				result = ja.toString();
			}
			
			tXtJg=tXtJgService.getOrgan(sjjgId);
			if(tXtJg!=null)
				model.addAttribute("jgMc", tXtJg.getJgMc());
			model.addAttribute("result", result);
			model.addAttribute("tXtYyList", tXtYyList);
			model.addAttribute("page",page);
			parameterMap.put("currentAppId", currentAppId);
			model.addAttribute("parameterMap", parameterMap);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "admin/organ/dirorganaction";
	 }
	@RequestMapping("org-edit")
	@SystemControllerLog(description = "编辑组织机构")
	public String orgEdit(@RequestParam Map<String, Object> parameterMap, Model model) {
		jgId= parameterMap.get("jgId")==null?"":parameterMap.get("jgId").toString();
		sjjgId= parameterMap.get("sjjgId")==null?"":parameterMap.get("sjjgId").toString();
		String currentAppId= parameterMap.get("currentAppId")==null?"":parameterMap.get("currentAppId").toString();
		if(sjjgId==null||"".equals(sjjgId)){
			TXtJg tXtJg = tXtJgManager.findUnique("from TXtJg where sjjgId=?", "");
			if(tXtJg!=null){
				sjjgId=tXtJg.getJgId();
			}
		}
		TXtJg organ = null;
		String result = "[]";
		try {
			if(StringHelper.isNotEmpty(jgId)){
				organ = tXtJgManager.findUniqueBy("jgId", jgId);//QueryCache.get(TXtJg.class, jgId);
			}else{
				organ = new TXtJg();
				organ.setSjjgId(sjjgId);
			}
			UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
			JSONObject jo = tXtJgService.getRootRoleOrgan(sjjgId, organ.getSjjgId());//逻辑同roleorgan，所以复用roleorgan
			JSONArray ja = tXtJgService.getSubRoleOrgan(sjjgId, organ.getSjjgId(),user.getAppId());
			ja.add(jo);
			result = ja.toString();
			
		} catch (Exception ex) {
			result ="[]";
		}finally{
			model.addAttribute("result", result);
			model.addAttribute("currentAppId", currentAppId);
			model.addAttribute("organ", organ);
			model.addAttribute("jgMc2", organ.getJgId()==null||"".equals(organ.getJgId())?"":tXtJgService.getOrgan(organ.getJgId()).getJgMc());
		}
		List<TXtYy> tXtYyList = null;
		TXtYh currentYh = tXtYhManager.findUniqueBy("yhId", SpringSecurityUtils.getCurrentUserId());
		String hql = "select a.yhId from TXtYh a,TXtGnjs b,TXtYhGnjs c where a.yhId=c.yhId and c.jsDm=b.gnjsDm and a.yhId=? and b.jgId='2'";
		List list = tXtYhManager.find(hql, SpringSecurityUtils.getCurrentUserId());
		if(list!=null&&list.size()>0){
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
		model.addAttribute("txtYy", tXtYyList);
		return "admin/organ/getorganaction";
	}
	@RequestMapping(value="getOrgTree")
	public @ResponseBody String getOrgTree(@RequestParam Map<String, Object> parameterMap){
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		Object id = parameterMap.get("id");
		String appId = parameterMap.get("appId")==null?"":parameterMap.get("appId")+"";
		if(appId==null){
			appId = user.getAppId();
		}
		String result = "";
		if(id!=null){
			try {
				result = tXtJgService.getSubOrgan(id.toString(),appId).toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	@RequestMapping(value="getOrgnTree")
	public @ResponseBody String getOrgnTree(@RequestParam Map<String, Object> parameterMap){
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		if(sjjgId==null||"".equals(sjjgId)){
			TXtJg tXtJg = tXtJgManager.findUnique("from TXtJg where sjjgId=?", "");
			if(tXtJg!=null){
				sjjgId=tXtJg.getJgId();
			}
		}
		TXtJg organ = null;
		String result = "[]";
		try {
			organ = new TXtJg();
			organ.setSjjgId(sjjgId);
			JSONObject jo = tXtJgService.getRootRoleOrgan(sjjgId, organ.getSjjgId());//逻辑同roleorgan，所以复用roleorgan
			JSONArray ja = tXtJgService.getSubRoleOrgan(sjjgId, organ.getSjjgId(),user.getAppId());
			ja.add(jo);
			result = ja.toString();
			
		} catch (Exception ex) {
			result ="[]";
		}finally{
		}
		
		return result;
	}
	@RequestMapping(value="getGUserOrganCheckTree",method=RequestMethod.POST)
	public @ResponseBody String getGUserOrganCheckTree(@RequestParam Map<String, Object> parameterMap){
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		Object id = parameterMap.get("id");
		Object jgId = parameterMap.get("jgId");
		String result = "";
		if(id!=null){
			try {
				JSONArray ja = tXtJgService.getSubRoleOrgan(id.toString(), jgId.toString(),user.getAppId());
				result = ja.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	@RequestMapping(value="validateJgDm",method=RequestMethod.POST)
	public @ResponseBody String validateJgDm(@RequestParam String jgDm){
		
		String result = "yes";
		if(jgDm!=null){
			try {
				List<TXtJg> yhs = tXtJgManager.findBy("jgDm", jgDm);
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
	@RequestMapping(value="addOrgan",method=RequestMethod.POST)
	@SystemControllerLog(description = "添加组织机构")
	public String addOrgan(TXtJg organ,String[] checkdel, Model model,String currentAppId){
		if(StringHelper.isEmpty(organ.getJgDm()) 
				|| StringHelper.isEmpty(organ.getJgMc())){
			return null;
		}
		
		organ.setJgId(IdGenerator.getInstance().getUniqTime()+"");
		organ.setLrRq(new Date());
		organ.setXgRq(new Date());
		organ.setScBj(Integer.valueOf(Constants.UN_DELETE_STATE));
		//organ.setCzryId(gUser.getYhId());当前登陆用户
		//docStoreService.saveDepartDir(organ);
		tXtJgManager.save(organ);
		return "redirect:/org/org-list.izhbg?sjjgId="+organ.getSjjgId()+"&currentAppId="+currentAppId;
	}
	
	@RequestMapping(value="updateOrg",method=RequestMethod.POST)
	@SystemControllerLog(description = "更新组织机构")
	public String updateOrg(TXtJg organ,String[] checkdel, Model model,String currentAppId){
		if(StringHelper.isEmpty(organ.getJgId()) 
				|| StringHelper.isEmpty(organ.getJgDm()) 
				|| StringHelper.isEmpty(organ.getJgMc())){
			return null;
		}
		TXtJg item =tXtJgManager.findUniqueBy("jgId", organ.getJgId());// QueryCache.get(TXtJg.class, );
		item.setBz(organ.getBz());
		item.setYxBj(organ.getYxBj());
		item.setXh(organ.getXh());
		item.setJgId(organ.getJgId());
		item.setJgDm(organ.getJgDm());
		item.setJgMc(organ.getJgMc());
		item.setSjjgId(organ.getSjjgId());
		item.setJgLx(organ.getJgLx());
		item.setAppId(organ.getAppId());
		//docStoreService.saveDepartDir(item);
		tXtJgManager.update(item);
		return "redirect:/org/org-list.izhbg?sjjgId="+organ.getSjjgId()+"&currentAppId="+currentAppId;
		
	}
	
	@RequestMapping(value="deleteOrg",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除组织机构")
	public @ResponseBody  String deleteOrg(String[] checkdel, String sjjgId){
		String result="";
		try{
			if(checkdel == null || checkdel.length < 1){
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
			}
			List lst = new ArrayList();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtJg> itemLst = (List<TXtJg>)tXtJgManager.findByIds(lst); //QueryCache.idToObj(TXtYh.class, lst);
			
			for(TXtJg item : itemLst ){
				item.setScBj(new Integer(Constants.DELETE_STATE));
				tXtJgManager.update(item);
				//docStoreService.remove(item.getJgId(), true);
			}
			result = "sucess";
		} catch (Exception ex) {
		}
		
		return result;
	}
	@RequestMapping(value="updOrgStatus",method=RequestMethod.POST)
	@SystemControllerLog(description = "更新组织机构状态")
	public @ResponseBody  String updOrgStatus(String[] checkdel, String sjjgId){
		String result="";
		try {
			if(checkdel == null || checkdel.length < 1){
				return null;
			}
			List lst = new ArrayList();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtJg> itemLst = (List<TXtJg>)tXtJgManager.findByIds(lst); //(List<TXtYh>) QueryCache.idToObj(TXtYh.class, lst);
			
			for(TXtJg item : itemLst ){
				if(item.getYxBj()!=null&&item.getYxBj()==2){
					item.setYxBj(1);
				}else{
					item.setYxBj(2);
				}
				tXtJgManager.update(item);
			}
			
			result = "sucess";
		} catch (Exception ex) {
		}
		
		return result;
	}
	  // ~ ======================================================================
	@Resource
	public void setTXtJgManager(TXtJgManager xtJgManager) {
		tXtJgManager = xtJgManager;
	}
	@Resource
	public void setTXtJgService(TXtJgService xtJgService) {
		tXtJgService = xtJgService;
	}
	public void getOrgan(String organId){
		organs.add(organId);
		List<String> temp = tXtJgService.getOrganIds(organId);
		for(String s : temp)
			getOrgan(s);
	}
	@Resource
	public void setTXtYhManager(TXtYhManager xtYhManager) {
		tXtYhManager = xtYhManager;
	}
	@Resource
	public void setTXtYyService(TXtYyService xtYyService) {
		tXtYyService = xtYyService;
	}
	public String getOrder(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? " order by "+page.getOrderBy() +" "+ page.getOrder() : " order by a.xh ";
	}
	public String getOrderColumn(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? ", " + page.getOrderBy() : ", a.xh ";
	}
	public String getWhere() {
		StringBuffer sb = new StringBuffer("where a.scBj=" + Constants.UN_DELETE_STATE + " ");
		if(StringHelper.isNotEmpty(jgId))
			sb.append(" and a.jgId like :jgId ");
		if(StringHelper.isNotEmpty(jgDm))
			sb.append(" and a.jgDm like :jgDm ");
		if(StringHelper.isNotEmpty(jgMc))
			sb.append(" and a.jgMc like :jgMc ");
		if(StringHelper.isNotEmpty(sjjgId))
		{
			sb.append(" and a.sjjgId = :sjjgId ");
		}else{
			sb.append(" and a.sjjgId = 'false' ");
		}
		if(StringHelper.isNotEmpty(yxBj))
			sb.append(" and a.yxBj = :yxBj ");
		if(StringHelper.isNotEmpty(currentAppId)){
			sb.append(" and a.appId= :appId");
		}
		return sb.toString();
	}
	
	public Map<String, Object> setWhere() {
//		if(StringHelper.isNotEmpty(sjjgId)) 
//			qc.setParameter("sjjgId", sjjgId);
		 Map<String, Object> params = new HashMap<String, Object>();
		 if(StringHelper.isNotEmpty(jgId))
			 params.put("jgId","%" + jgId.trim() + "%");
		if(StringHelper.isNotEmpty(jgDm))
			params.put("jgDm","%" + jgDm.trim() + "%");
		if(StringHelper.isNotEmpty(jgMc))
			params.put("jgMc","%" + jgMc.trim() + "%");
		if(StringHelper.isNotEmpty(sjjgId))
			params.put("sjjgId",sjjgId.trim());
		if(StringHelper.isNotEmpty(yxBj))
			params.put("yxBj",Integer.parseInt(yxBj.trim()));
		if(StringHelper.isNotEmpty(currentAppId))
			params.put("appId",currentAppId);
		return params;
	}
	
	
	
}

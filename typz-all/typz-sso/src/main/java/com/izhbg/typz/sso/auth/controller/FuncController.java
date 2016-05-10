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
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.sso.annotation.SystemControllerLog;
import com.izhbg.typz.sso.auth.UserAuthDTO;
import com.izhbg.typz.sso.auth.dto.TXtGnzy;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.dto.TXtYy;
import com.izhbg.typz.sso.auth.manager.TXtGnjsZyManager;
import com.izhbg.typz.sso.auth.manager.TXtGnzyManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.auth.service.TXtGnzyService;
import com.izhbg.typz.sso.auth.service.TXtJgService;
import com.izhbg.typz.sso.auth.service.TXtYyService;
import com.izhbg.typz.sso.common.TreeNode;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
import com.mysql.jdbc.Messages;

/**
 * 
* @ClassName: FuncController 
* @Description: 功能管理
* @author caixl 
* @date 2016-5-9 下午5:25:27 
*
 */
@Controller
@RequestMapping("/fun")
public class FuncController {
	
	private List<String> organs = new ArrayList<String>();
	private String gnDm,gnMc,sjgnDm,yxBj,appId,currentAppId;//办公系统Id;
	
	private TXtGnzyManager tXtGnzyManager ;
	private TXtGnjsZyManager tXtGnjsZyManager;
	private TXtGnzyService tXtGnzyService;
	private TXtJgService tXtJgService;
	private TXtYyService tXtYyService;
	private TXtYhManager tXtYhManager;
	
	
	/**
	 * 功能列表
	 * @param page
	 * @param parameterMap
	 * @param model
	 * @return
	 */
	@RequestMapping("fun_list")
	 public String list(@ModelAttribute  Page page,
	            @RequestParam Map<String, Object> parameterMap, Model model) {
		
		gnDm= parameterMap.get("gnDm")==null?"":parameterMap.get("gnDm").toString();
		gnMc= parameterMap.get("gnMc")==null?"":parameterMap.get("gnMc").toString();
		sjgnDm= parameterMap.get("sjgnDm")==null?"":parameterMap.get("sjgnDm").toString();
		yxBj= parameterMap.get("yxBj")==null?"":parameterMap.get("yxBj").toString();
		appId= parameterMap.get("appId")==null?"":parameterMap.get("appId").toString();
		currentAppId= parameterMap.get("currentAppId")==null?"":parameterMap.get("currentAppId").toString();
		
		List<TXtYy> tXtYyList = null;
		TXtYh currentYh = tXtYhManager.findUniqueBy("yhId", SpringSecurityUtils.getCurrentUserId());
		String hql = "select a.yhId from TXtYh a,TXtGnjs b,TXtYhGnjs c where a.yhId=c.yhId and c.jsDm=b.gnjsDm and a.yhId=? and b.jgId='2'";
		List<String> list22 = tXtYhManager.find(hql, SpringSecurityUtils.getCurrentUserId());
		if(list22!=null&&list22.size()>0){
			tXtYyList = tXtYyService.getSystems();
		}else{
			TXtYy txtyy = tXtYyService.getSystem(currentYh.getAppId());
			if(txtyy!=null){
				tXtYyList = new ArrayList<TXtYy>();
				tXtYyList.add(txtyy);
			}
		}
		if(tXtYyList!=null&&tXtYyList.size()>0){
			if(StringHelper.isEmpty(currentAppId)){
				currentAppId = tXtYyList.get(0).getYyId();
			}
		}
		if(StringHelper.isEmpty(appId)){
			appId = currentAppId;
		}
		if(StringHelper.isEmpty(sjgnDm)){
			TXtGnzy gnzy = tXtGnzyManager.findUnique("from TXtGnzy where sjgnDm=?","-1");
			if(gnzy!=null){
				sjgnDm = gnzy.getGnDm();
				parameterMap.put("sjgnDm", sjgnDm);
			}
		}
		parameterMap.put("appId", appId);
		
		try {
			StringBuffer sb = new StringBuffer("select a.gnDm from TXtGnzy a  ");
			sb.append(getWhere())
			  .append(getOrder(page));
			
			page = tXtGnzyManager.pagedQuery(sb.toString(), page.getPageNo(), page.getPageSize(), setWhere());
			List<String> list = (List)page.getResult();
			List<TXtGnzy> listYh=null;
			try {
				if(list!=null&&list.size()>0)
					listYh = tXtGnzyManager.findByIdsOrder(list,"gnXh",true);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			page.setResult(listYh);
			
			model.addAttribute("systems", tXtYyList);
			model.addAttribute("result", getFunTreeJson());
			model.addAttribute("page",page);
			parameterMap.put("currentAppId", currentAppId);
			model.addAttribute("parameterMap", parameterMap);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "admin/func/dirfunc";
	 }
	
	/**
	 * 功能列表 json串
	 * @return
	 */
	public String getFunTreeJson(){
		String result = null;
		String root = null;
		if(StringHelper.isNotEmpty(appId)) {
			List<String> list2 = tXtGnzyManager.find("select a.gnDm from TXtGnzy a where a.sjgnDm='-1'");
			if(list2!=null&&list2.size()>0){
				root = list2.get(0);
			}
			if(StringHelper.isEmpty(root)) {
				TXtGnzy func = new TXtGnzy();
				func.setGnDm(IdGenerator.getInstance().getUniqTime()+"");
				func.setGnMc("系统功能");
				func.setSjgnDm("-1");
				func.setAppId(appId);
				tXtGnzyManager.save(func);
				root = func.getGnDm();
			}
			JSONObject jo = tXtGnzyService.getRootFunc(root);
			JSONArray ja = tXtJgService.getSubFunc(root,appId);
			if(ja != null){
				ja.add(jo);
				result = ja.toString();
			}
		}
		if(StringHelper.isEmpty(result))
			result = "[]";
	
		return result;
	}
	
	/**
	 * 功能子节点
	 * @param parameterMap
	 * @param model
	 * @return
	 */
	@RequestMapping("fun-item")
	 public String item( @RequestParam Map<String, Object> parameterMap, Model model) {
		gnDm= parameterMap.get("gnDm")==null?"":parameterMap.get("gnDm").toString();
		if(StringHelper.isNotEmpty(gnDm)){
			UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
			TreeNode nods =user.getTreeNode()==null?null:(TreeNode)user.getTreeNode();
			if(nods!=null&&nods.getChildren()!=null){
				for(TreeNode node:nods.getChildren()){
					if(node.getChildren()!=null){
						for(TreeNode nod:node.getChildren()){
							if(gnDm.equals(nod.getCode()))
							{
								model.addAttribute("nodes", nod.getChildren());
							}
						}
					}
					
				}
			}
			model.addAttribute("gnDm", gnDm);
		}
		return "admin/func/fun-item";
	 }
	/**
	 * 功能编辑
	 * @param parameterMap
	 * @param model
	 * @param currentAppId
	 * @return
	 */
	@RequestMapping("fun-edit")
	@SystemControllerLog(description = "编辑功能节点")
	public String funEdit(@RequestParam Map<String, Object> parameterMap, Model model,String currentAppId) {
		gnDm= parameterMap.get("gnDm")==null?"":parameterMap.get("gnDm").toString();
		if(StringHelper.isEmpty(appId)){
			appId = currentAppId;
		}
		TXtGnzy func = null;
		String result = null;
		try {
			String root = null;
			if(StringHelper.isNotEmpty(gnDm)){
				func = tXtGnzyManager.findUniqueBy("gnDm", gnDm);//QueryCache.get(TXtGnzy.class, gnDm);
			}else{
				func = new TXtGnzy();
				func.setSjgnDm(sjgnDm);
				func.setAppId(appId);
			}
			if(StringHelper.isNotEmpty(appId)) {
				
				List<String> list2 = tXtGnzyManager.find("select a.gnDm from TXtGnzy a where a.appId=? and a.sjgnDm='-1'", appId);// (String) new QueryCache("")
				//.setParameter("appId", appId).setMaxResults(1).uniqueResultCache();
				if(list2!=null&&list2.size()>0){
					root = list2.get(0);
				}
				JSONObject jo = tXtGnzyService.getRootFuncCheck(root, func.getSjgnDm());
				JSONArray ja = tXtGnzyService.getSubFuncCheck(root, func.getSjgnDm(),appId);
				if(ja != null){
					ja.add(jo);
					result = ja.toString();
				}
			}
			if(StringHelper.isEmpty(result))
				result = "[]";
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
			TXtGnzy txtGnzy = tXtGnzyService.getFunc(func.getSjgnDm());
			model.addAttribute("txtYy", tXtYyList);
			model.addAttribute("result", result);
			model.addAttribute("currentAppId", currentAppId);
			model.addAttribute("func", func);
			model.addAttribute("currentYh", currentYh);
			model.addAttribute("sjgnzyname", txtGnzy.getGnMc());
			TXtGnzy	tXtJg=tXtGnzyService.getFunc(root);//tXtJgService.getOrgan(root);
			model.addAttribute("gnMc", tXtJg.getGnMc());
		} catch (Exception ex) {
			result ="";
		}
		
		return "admin/func/getfunc";
	}
	
	@RequestMapping(value="getSubFunc",method=RequestMethod.POST)
	public @ResponseBody String getSubFunc(@RequestParam Map<String, Object> parameterMap){
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		Object id = parameterMap.get("id");
		String appId = parameterMap.get("appId")==null?"":parameterMap.get("appId")+"";
		if(appId==null){
			appId = user.getAppId();
		}
		String result = "";
		if(id!=null){
			try {
				result = tXtJgService.getSubFunc(id.toString(),appId).toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	@RequestMapping(value="getFuncCheckTree",method=RequestMethod.POST)
	public @ResponseBody String getFuncCheckTree(@RequestParam Map<String, Object> parameterMap){
		
		Object dm = parameterMap.get("dm");
		Object gnDm = parameterMap.get("gnDm");
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		String result = "";
		if(dm!=null){
			try {
				JSONArray ja =tXtGnzyService.getSubFuncCheck(dm.toString(), gnDm.toString(),user.getAppId());
				result = ja.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	@RequestMapping(value="validateGnDm",method=RequestMethod.POST)
	public @ResponseBody String validateGnDm(@RequestParam String gnDm){
		
		String result = "yes";
		if(gnDm!=null){
			try {
				List<TXtGnzy> yhs = tXtGnzyManager.findBy("gnDm", gnDm);
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
	@SystemControllerLog(description = "添加功能节点")
	@RequestMapping(value="addFun",method=RequestMethod.POST)
	public String addFun(TXtGnzy func,String[] checkdel, Model model){
		if(StringHelper.isEmpty(func.getGnDm())
				||StringHelper.isEmpty(func.getGnMc())
				||StringHelper.isEmpty(func.getAppId())
				||StringHelper.isEmpty(func.getSjgnDm())){
			return null;
		}
		if(StringHelper.isEmpty(func.getSjgnDm())){
			TXtGnzy id = tXtGnzyManager.findUniqueBy("appId", appId);//new QueryCache("select a.gnDm from TXtGnzy a where a.appId=:appId")
				//.setParameter("appId",func.getAppId()).setMaxResults(1).uniqueResult();
			if(id != null) {
				return null;
			}
		}
		tXtGnzyManager.save(func);
		return "redirect:/fun/fun-list.izhbg?sjgnDm="+func.getSjgnDm()+"&appId="+appId;
	}
	@SystemControllerLog(description = "更新功能节点")
	@RequestMapping(value="updateFun",method=RequestMethod.POST)
	public String updateOrg(TXtGnzy func,String[] checkdel, Model model,String currentAppId){
		
		if(StringHelper.isEmpty(func.getGnDm())
				||StringHelper.isEmpty(func.getGnMc())
				||StringHelper.isEmpty(func.getAppId())
				||StringHelper.isEmpty(func.getSjgnDm())){
			return null;
		}
		tXtGnzyManager.update(func);
		return "redirect:/fun/fun-list.izhbg?sjgnDm="+func.getSjgnDm()+"&appId="+appId+"&currentAppId="+currentAppId;
		
	}
	
	@RequestMapping(value="deleteFun",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除功能节点")
	public @ResponseBody  String deleteFun(String[] checkdel){
		String result="";
		try{
			if(checkdel == null || checkdel.length < 1){
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
			}
			List lst = new ArrayList();
			for(String s : checkdel) 
				lst.add(s);
			delete(lst);
			result = "sucess";
		} catch (Exception ex) {
		}
		return result;
	}
	@RequestMapping(value="updFunStatus",method=RequestMethod.POST)
	@SystemControllerLog(description = "更新功能节点状态")
	public @ResponseBody  String updFunStatus(String[] checkdel){
		String result="";
		try {
			if(checkdel == null || checkdel.length < 1){
				return null;
			}
			List lst = new ArrayList();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtGnzy> itemLst = (List<TXtGnzy>) tXtGnzyManager.findByIds(lst); //(List<TXtYh>) QueryCache.idToObj(TXtYh.class, lst);
			
			for(TXtGnzy item : itemLst ){
				if(item.getYxBj()!=null&&item.getYxBj()==2){
					item.setYxBj(1);
				}else{
					item.setYxBj(2);
				}
				tXtGnzyManager.update(item);
			}
			
			result = "sucess";
		} catch (Exception ex) {
		}
		
		return result;
	}
	  // ~ ======================================================================
	@Resource
	public void setTXtJgService(TXtJgService xtJgService) {
		tXtJgService = xtJgService;
	}
	@Resource
	public void setTXtGnzyManager(TXtGnzyManager xtGnzyManager) {
		tXtGnzyManager = xtGnzyManager;
	}
	@Resource
	public void setTXtGnzyService(TXtGnzyService xtGnzyService) {
		tXtGnzyService = xtGnzyService;
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
	public void getOrgan(String organId){
		organs.add(organId);
		List<String> temp = tXtJgService.getOrganIds(organId);
		for(String s : temp)
			getOrgan(s);
	}

	private void delete(List ids) {
		List list =  tXtGnzyManager.findByIds(ids);//QueryCache.idToObj(TXtGnzy.class, ids);
		for(Object o : list)
			tXtGnzyManager.remove(o);
		List objs1 = tXtGnjsZyManager.findBy("gnzyDm", ids);///QueryCache.idToObj(TXtGnjsZy.class, ids1);
		for(Object o : objs1)
			tXtGnjsZyManager.remove(o);
		
		List subids = tXtGnzyManager.find("select a.gnDm from TXtGnzy a where a.sjgnDm in (?)", ids);//new QueryCache("select a.gnDm from TXtGnzy a where a.sjgnDm in (:sjgnDm)")
		//.setParameter("sjgnDm", ids).list();
		if(subids != null && subids.size() > 0)
			delete(subids);
	}
	
	public String getOrder(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? " order by "+page.getOrderBy() +" "+ page.getOrder() : " order by a.gnXh asc";
	}
	public String getOrderColumn(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? ", " + page.getOrderBy() : ", a.xgRq ";
	}
	public String getWhere() {
		StringBuffer sb = new StringBuffer(" where 1=1 ");
		if(StringHelper.isNotEmpty(gnDm))
			sb.append(" and a.gnDm like :gnDm ");
		if(StringHelper.isNotEmpty(gnMc))
			sb.append(" and a.gnMc like :gnMc ");
		if(StringHelper.isNotEmpty(sjgnDm))
			sb.append(" and a.sjgnDm = :sjgnDm ");
		if(StringHelper.isNotEmpty(yxBj))
			sb.append(" and a.yxBj = :yxBj ");
		if(StringHelper.isNotEmpty(currentAppId))
			sb.append(" and (a.appId = :appId or a.gnLx=2) ");
		
	/*	else
			sb.append(" order by a.gnDm ");*/
		return sb.toString();
	}
	
	public Map<String, Object> setWhere() {
//		if(StringHelper.isNotEmpty(sjjgId)) 
//			qc.setParameter("sjjgId", sjjgId);
		 Map<String, Object> params = new HashMap<String, Object>();
		 if(StringHelper.isNotEmpty(gnDm))
			 params.put("gnDm", "%" + gnDm.trim() + "%");
		if(StringHelper.isNotEmpty(gnMc))
			params.put("gnMc", "%" + gnMc.trim() + "%");
		if(StringHelper.isNotEmpty(sjgnDm))
			params.put("sjgnDm", sjgnDm.trim());
		if(StringHelper.isNotEmpty(appId))
			params.put("appId", currentAppId);
		if(StringHelper.isNotEmpty(yxBj))
			params.put("yxBj",Integer.parseInt(yxBj.trim()));
		return params;
	}
	
	
	
}

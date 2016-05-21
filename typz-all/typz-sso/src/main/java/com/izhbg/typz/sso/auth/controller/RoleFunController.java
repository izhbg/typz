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
import com.izhbg.typz.sso.auth.dto.TXtGnjs;
import com.izhbg.typz.sso.auth.dto.TXtGnjsZy;
import com.izhbg.typz.sso.auth.dto.TXtGnzy;
import com.izhbg.typz.sso.auth.manager.TXtGnjsManager;
import com.izhbg.typz.sso.auth.manager.TXtGnjsZyManager;
import com.izhbg.typz.sso.auth.manager.TXtGnzyManager;
import com.izhbg.typz.sso.auth.service.TXtGnzyService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
import com.mysql.jdbc.Messages;

@Controller
@RequestMapping("/rolFun")
public class RoleFunController {
	private String jsDm,gnzyDm,gnMc;
	private TXtGnjsManager tXtGnjsManager;
	private TXtGnjsZyManager tXtGnjsZyManager;
	private TXtGnzyManager tXtGnzyManager;
	private TXtGnzyService tXtGnzyService;
	
	@RequestMapping("roleFun_list")
	 public String list(@ModelAttribute  Page page,
	            @RequestParam Map<String, Object> parameterMap, Model model) {
		
		jsDm = parameterMap.get("jsDm")==null?"":parameterMap.get("jsDm").toString();
		gnzyDm = parameterMap.get("gnzyDm")==null?"":parameterMap.get("gnzyDm").toString();
		gnMc = parameterMap.get("gnMc")==null?"":parameterMap.get("gnMc").toString();
		String currentAppId = parameterMap.get("currentAppId")==null?"":parameterMap.get("currentAppId").toString();
		
		TXtGnjs role = null;
		if(StringHelper.isNotEmpty(jsDm))
			role = tXtGnjsManager.findUniqueBy("gnjsDm", jsDm);//QueryCache.get(TXtGnjs.class, jsDm); 
		StringBuffer sb = new StringBuffer("select a.uuid from TXtGnjsZy a , TXtGnzy b where a.gnzyDm=b.gnDm ");
		sb.append(getWhere());
		sb.append(getOrder(page));
		
		page = tXtGnjsManager.pagedQuery(sb.toString(), page.getPageNo(), page.getPageSize(), setWhere());
		
		List<String> list = (List)page.getResult();
		List<TXtGnjsZy> listYh=null;
		try {
			if(list!=null&&list.size()>0)
				listYh = tXtGnjsZyManager.findByIds(list);
			if(listYh!=null)
			for(TXtGnjsZy o : listYh) {
				TXtGnzy gnzy = tXtGnzyManager.findUniqueBy("gnDm", o.getGnzyDm());//QueryCache.get(TXtGnzy.class, o.getGnzyDm()); 
				o.setGnzy(gnzy);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//find(sb.toString(), list.toArray()).list();
		page.setResult(listYh);
		
		String result = null;
		TXtGnjs js = tXtGnjsManager.findUniqueBy("gnjsDm", role.getGnjsDm());
		if(StringHelper.isNotEmpty(js.getAppId())){
			List<String> list2 = tXtGnzyManager.find("select a.gnDm from TXtGnzy a where  a.sjgnDm='-1'");
			String root = "";
			if(list2!=null&&list2.size()>0){
				root = list2.get(0);
			}
			try {
				if(StringHelper.isNotEmpty(root)){
					JSONObject jo = tXtGnzyService.getRootRoleFunc(root);
					JSONArray ja = tXtGnzyService.getSubRoleFunc(root, jsDm,currentAppId);
					if(ja != null){
						ja.add(jo);
						result = ja.toString();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if(result==null){
			result = "[]";
		}
		model.addAttribute("role", role);
		model.addAttribute("result", result);
		model.addAttribute("page", page);
		model.addAttribute("parameterMap", parameterMap);
		
		return "admin/role/roleFun_list";
	}
	@RequestMapping("roleDoc-edit")
	 public String roleDocEdit(@RequestParam Map<String, Object> parameterMap, Model model) {
		jsDm = parameterMap.get("jsDm")==null?"":parameterMap.get("jsDm").toString();
		TXtGnjs js = tXtGnjsManager.findUniqueBy("gnjsDm", jsDm);
		model.addAttribute("js", js);
		return "admin/role/roleDoc-edit";
	}
	@RequestMapping("roleDoc-add")
	public String roleDocAdd(@RequestParam Map<String, Object> parameterMap, Model model) {
		String gnjsDm = parameterMap.get("gnjsDm")==null?"":parameterMap.get("gnjsDm").toString();
		String resourceIds = parameterMap.get("resourceIds")==null?"":parameterMap.get("resourceIds").toString();
		String viewDirIds = parameterMap.get("viewDirIds")==null?"":parameterMap.get("viewDirIds").toString();
		String viewSelfDept = parameterMap.get("viewSelfDept")==null?"":parameterMap.get("viewSelfDept").toString();
		if(StringHelper.isNotEmpty(gnjsDm)){
			TXtGnjs js = tXtGnjsManager.findUniqueBy("gnjsDm", gnjsDm);
			if(js!=null){
				js.setResourceIds(resourceIds);
				js.setViewDirIds(viewDirIds);
				js.setViewSelfDept(viewSelfDept);
				tXtGnjsManager.update(js);
			}
		}
		return "redirect:/role/role-list.izhbg";
	}
	@RequestMapping(value="getOrgTree",method=RequestMethod.POST)
	public @ResponseBody String getOrgTree(@RequestParam Map<String, Object> parameterMap){
		jsDm = parameterMap.get("jsDm")==null?"":parameterMap.get("jsDm").toString();
		String result = null;
		TXtGnjs js = tXtGnjsManager.findUniqueBy("gnjsDm", jsDm);//QueryCache.get(TXtGnjs.class, jsDm);
		if(StringHelper.isNotEmpty(js.getAppId())){
			List<String> list2 = tXtGnzyManager.find("select a.gnDm from TXtGnzy a where a.appId=? and a.sjgnDm='-1'", js.getAppId());
			String root = "";
			if(list2!=null&&list2.size()>0){
				root = list2.get(0);
			}
			UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
			try {
				if(StringHelper.isNotEmpty(root)){
					JSONObject jo = tXtGnzyService.getRootRoleFunc(root);
					JSONArray ja = tXtGnzyService.getSubRoleFunc(root, jsDm,user.getAppId());
					if(ja != null){
						ja.add(jo);
						result = ja.toString();
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(result==null){
			result = "[]";
		}
		
		return result;
	}
	@RequestMapping(value="getRoleFuncTree",method=RequestMethod.POST)
	public @ResponseBody String getRoleFuncTree(@RequestParam Map<String, Object> parameterMap){
		
		String id = parameterMap.get("id")==null?null:parameterMap.get("id").toString();
		String jsDm = parameterMap.get("jsDm")==null?null:parameterMap.get("jsDm").toString();
		String result = "";
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		if(StringHelper.isNotEmpty(jsDm) 
				&& StringHelper.isNotEmpty(id)){
			try {
				JSONArray ja = tXtGnzyService.getSubRoleFunc(id, jsDm,user.getAppId());
				result = ja.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	@RequestMapping(value="addRoleFunc",method=RequestMethod.POST)
	@SystemControllerLog(description = "角色授权功能")
	public @ResponseBody String addRoleFunc(String jsDm,String[] checkdel,String[] isRead,String[] isCreate,String[] isUpdate,String[] isDelete,String[] isAll){
		if(checkdel == null || checkdel.length < 1 
				|| StringHelper.isEmpty(jsDm)){
			return null;		
		}
		TXtGnjsZy item,item1;
		int i=0;
		for(String id : checkdel){
			List uid = tXtGnjsZyManager.find("select a.uuid from TXtGnjsZy a where a.jsDm=? and a.gnzyDm=?", jsDm,id);
			if(uid == null||uid.size()<1) {
				item = new TXtGnjsZy();
				item.setGnzyDm(id);
				item.setJsDm(jsDm);
				item.setIsCreate(Integer.parseInt(isCreate[i]));
				item.setIsRead(Integer.parseInt(isRead[i]));
				item.setIsUpdate(Integer.parseInt(isUpdate[i]));
				item.setIsDelete(Integer.parseInt(isDelete[i]));
				item.setIsAll(Integer.parseInt(isAll[i]));
				item.setUuid(IdGenerator.getInstance().getUniqTime()+"");
				tXtGnjsZyManager.save(item);
				i++;
			}
		}
		return "sucess";
	}
	@RequestMapping(value="delRoleFunc",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除角色授权的功能")
	public @ResponseBody  String delRoleFunc(String[] checkdel){
		String result="";
		try{
			if(checkdel == null || checkdel.length < 1){
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
			}
			List lst = new ArrayList();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtGnjsZy> items = tXtGnjsZyManager.findByIds(lst);//QueryCache.idToObj(TXtGnjs.class, lst);
			
			for(Object o : items)
				tXtGnjsZyManager.remove(o);
			result = "sucess";
		} catch (Exception ex) {
		}
		return result;
	}
	@RequestMapping(value="changState",method=RequestMethod.POST)
	@SystemControllerLog(description = "修改角色功能权限")
	public @ResponseBody  String changState(String categery,String uuid){
		String result="";
		try{
			if(StringHelper.isEmpty(uuid)||StringHelper.isEmpty(categery)){
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
			}
			List<TXtGnjsZy> tXtGnjsZys = tXtGnjsZyManager.findBy("uuid", uuid);
			if(tXtGnjsZys!=null&&tXtGnjsZys.size()>0){
				TXtGnjsZy tXtGnjsZy = tXtGnjsZys.get(0);
				if("isCreate".equals(categery)){
					tXtGnjsZy.setIsCreate(tXtGnjsZy.getIsCreate()==null||tXtGnjsZy.getIsCreate()==0?1:0);
				}else if("isDelete".equals(categery)){
					tXtGnjsZy.setIsDelete(tXtGnjsZy.getIsDelete()==null||tXtGnjsZy.getIsDelete()==0?1:0);
				}else if("isUpdate".equals(categery)){
					tXtGnjsZy.setIsUpdate(tXtGnjsZy.getIsUpdate()==null||tXtGnjsZy.getIsUpdate()==0?1:0);
				}else if("isRead".equals(categery)){
					tXtGnjsZy.setIsRead(tXtGnjsZy.getIsRead()==null||tXtGnjsZy.getIsRead()==0?1:0);
				}
				tXtGnjsZyManager.update(tXtGnjsZy);
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
		if(StringHelper.isNotEmpty(jsDm))
			sb.append(" and a.jsDm = :jsDm ");
		if(StringHelper.isNotEmpty(gnzyDm))
			sb.append(" and a.gnzyDm like :gnzyDm ");
		if(StringHelper.isNotEmpty(gnMc))
			sb.append(" and b.gnMc like :gnMc ");
		return sb.toString();
	}
	
	public Map<String, Object> setWhere() {
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringHelper.isNotEmpty(jsDm)) 
			params.put("jsDm", jsDm.trim());
		if(StringHelper.isNotEmpty(gnzyDm))
			params.put("gnzyDm", "%" + gnzyDm.trim() + "%");
		if(StringHelper.isNotEmpty(gnMc))
			params.put("gnMc", "%" + gnMc.trim() + "%");
		
		return params;
			
	}

	@Resource
	public void setTXtGnjsManager(TXtGnjsManager xtGnjsManager) {
		tXtGnjsManager = xtGnjsManager;
	}
	@Resource
	public void setTXtGnjsZyManager(TXtGnjsZyManager xtGnjsZyManager) {
		tXtGnjsZyManager = xtGnjsZyManager;
	}
	@Resource
	public void setTXtGnzyManager(TXtGnzyManager xtGnzyManager) {
		tXtGnzyManager = xtGnzyManager;
	}
	@Resource
	public void setTXtGnzyService(TXtGnzyService xtGnzyService) {
		tXtGnzyService = xtGnzyService;
	}
	
	
	

}

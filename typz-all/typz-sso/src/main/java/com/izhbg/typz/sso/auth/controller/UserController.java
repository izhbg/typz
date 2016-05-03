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

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.sso.auth.UserAuthDTO;
import com.izhbg.typz.sso.auth.dto.TXtJg;
import com.izhbg.typz.sso.auth.dto.TXtJgYh;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.dto.TXtYhGnjs;
import com.izhbg.typz.sso.auth.dto.TXtYy;
import com.izhbg.typz.sso.auth.manager.TXtJgManager;
import com.izhbg.typz.sso.auth.manager.TXtJgYhManager;
import com.izhbg.typz.sso.auth.manager.TXtYhGnjsManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.auth.service.TXtJgService;
import com.izhbg.typz.sso.auth.service.TXtYyService;
import com.izhbg.typz.sso.util.SimplePasswordEncoder;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
import com.mysql.jdbc.Messages;

@Controller
@RequestMapping("/user")
public class UserController {
	private JdbcTemplate jdbcTemplate;
	private List<String> organs =null;
	private String yhId,yhDm,yhMc,yxBj,scBj,password,openFlag,sjjgId,includeFlag,currentAppId;
	private TXtYhManager tXtYhManager;
	private TXtJgService tXtJgService;
	private TXtJgYhManager tXtJgYhManager;
	private TXtYhGnjsManager tXtYhGnjsManager;
	private TXtJgManager tXtJgManager;
	private TXtYyService tXtYyService;
	
	private SimplePasswordEncoder simplePasswordEncoder;
	
	
	@RequestMapping("user-list")
	 public String list(@ModelAttribute  Page page,
	            @RequestParam Map<String, Object> parameterMap, Model model) {
		yhId= parameterMap.get("yhId")==null?"":parameterMap.get("yhId").toString();
		yhDm= parameterMap.get("yhDm")==null?"":parameterMap.get("yhDm").toString();
		yhMc= parameterMap.get("yhMc")==null?"":parameterMap.get("yhMc").toString();
		yxBj= parameterMap.get("yxBj")==null?"":parameterMap.get("yxBj").toString();
		scBj= parameterMap.get("scBj")==null?"":parameterMap.get("scBj").toString();
		password= parameterMap.get("password")==null?"":parameterMap.get("password").toString();
		openFlag= parameterMap.get("openFlag")==null?"":parameterMap.get("openFlag").toString();
		sjjgId= parameterMap.get("sjjgId")==null?"":parameterMap.get("sjjgId").toString();
		currentAppId= parameterMap.get("currentAppId")==null?"":parameterMap.get("currentAppId").toString();
		includeFlag = parameterMap.get("includeFlag")==null?"":parameterMap.get("includeFlag").toString();
		
		String currentjgId = null;
		
		//组织管理 当前登陆用户 所在的应用  切为根节点的 项
		TXtYh currentYh = tXtYhManager.findUniqueBy("yhId", SpringSecurityUtils.getCurrentUserId());
		if(currentYh==null){
			return null;
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
			
		if(StringHelper.isEmpty(currentAppId)){
			if(tXtYyList!=null&&tXtYyList.size()>0){
				currentAppId = tXtYyList.get(0).getYyId();
				
			}
		}
		if(StringHelper.isNotEmpty(currentAppId)){
			TXtJg tXtJg = tXtJgManager.findUnique("from TXtJg where sjjgId=?","");
			if(tXtJg!=null){
				currentjgId=tXtJg.getJgId();
			}
		}
		if(StringHelper.isEmpty(sjjgId)){
			if(StringHelper.isEmpty(currentAppId)){
				sjjgId = "false";
			}else{
				sjjgId = currentjgId;
			}
			parameterMap.put("sjjgId", sjjgId);
		}
		
		String result = "[]";
		try {
			StringBuffer sb = new StringBuffer("select DISTINCT a.yhId  ");
			sb.append(" from TXtYh a ,TXtJgYh b where a.yhId=b.yhId and a.yhId!='is8v1wz7g0eyl86iz7g1'")
			  .append(getWhere())
			  .append(getOrder(page));
			
			page = tXtYhManager.pagedQuery(sb.toString(), page.getPageNo(), page.getPageSize(), setWhere());
			List<String> list = (List)page.getResult();
			List listYh=null;
			try {
				if(list!=null&&list.size()>0)
					listYh = tXtYhManager.findByIds(list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//find(sb.toString(), list.toArray()).list();
			page.setResult(listYh);
			
			
			if(StringHelper.isNotEmpty(currentjgId)){
				JSONObject jo = tXtJgService.getRootOrgan(currentjgId);
				
				JSONArray ja = tXtJgService.getSubOrgan(currentjgId,currentAppId);
				if(jo.size()>0)
					ja.add(jo);
				result = ja.toString();
			}
			organs = new ArrayList();
			TXtJg	tXtJg=tXtJgService.getOrgan(sjjgId);
			if(tXtJg!=null)
				model.addAttribute("jgMc", tXtJg.getJgMc());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			model.addAttribute("result", result);
			model.addAttribute("page",page);
			parameterMap.put("currentAppId", currentAppId);
			model.addAttribute("parameterMap", parameterMap);
			model.addAttribute("tXtYyList", tXtYyList);
		}
		
		return "admin/guser/dirguser";
	 }
	@RequestMapping("user-edit")
	public String userEdit(@RequestParam Map<String, Object> parameterMap, Model model) {
		yhId= parameterMap.get("yhId")==null?"":parameterMap.get("yhId").toString();
		sjjgId= parameterMap.get("sjjgId")==null?"":parameterMap.get("sjjgId").toString();
		String jgId= parameterMap.get("jgId")==null?"":parameterMap.get("jgId").toString();
		String currentAppId= parameterMap.get("currentAppId")==null?"":parameterMap.get("currentAppId").toString();
		if(sjjgId==null||"".equals(sjjgId)){
			TXtJg tXtJg = tXtJgManager.findUnique("from TXtJg where  sjjgId=?","");
			if(tXtJg!=null){
				sjjgId=tXtJg.getJgId();
			}
		}
		TXtYh user = null;
		String result = "[]";
		try {
			if(StringHelper.isNotEmpty(yhId)){
				user = tXtYhManager.findUniqueBy("yhId", yhId);//QueryCache.get(TXtYh.class, yhId);
				List<TXtJgYh> tXtJgYhs= tXtJgYhManager.findBy("yhId", yhId);//tXtJgService.getOrgan(jgId)//new QueryCache("select a.jgId from TXtJgYh a where a.yhId=:yhId")
					//.setParameter("yhId", yhId).setMaxResults(1).uniqueResultCache();
				
				if(tXtJgYhs != null&&tXtJgYhs.size()>0){
					user.setJgId(tXtJgYhs.get(0).getJgId());
				}
			} else {
				user = new TXtYh();
				user.setJgId(jgId);
			}
			JSONObject jo = tXtJgService.getRootUserOrgan(sjjgId,user.getJgId());
			JSONArray ja = tXtJgService.getSubUserOrgan(sjjgId, user.getJgId(),currentAppId);
			if(jo.size()>0)
				ja.add(jo);
			result = ja.toString();
			
			if(StringHelper.isNotEmpty(yhId)){
				List<TXtJgYh> list = tXtJgYhManager.findBy("yhId", yhId);
				if(list!=null&&list.size()>0){
					organs = new ArrayList();
					model.addAttribute("jgMc",list.get(0)==null?"":tXtJgService.getOrgan(list.get(0).getJgId()).getJgMc());
					user.setJgId(list.get(0).getJgId());
				}
			}
			
			
		} catch (Exception ex) {
			result ="[]";
		}
		model.addAttribute("result", result);
		model.addAttribute("user", user);
		model.addAttribute("currentAppId", currentAppId);
		List<TXtYy> tXtYyList = null;
		UserAuthDTO user2 = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
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
			TXtYy txtyy = tXtYyService.getSystem(user2.getAppId());
			if(txtyy!=null){
				tXtYyList = new ArrayList<TXtYy>();
				tXtYyList.add(txtyy);
			}
			
		}
		model.addAttribute("txtYy", tXtYyList);
		return "admin/guser/getguser";
	}
	@RequestMapping(value="getOrgTree",method=RequestMethod.POST)
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
	@RequestMapping(value="validateYhDm",method=RequestMethod.POST)
	public @ResponseBody String validateYhDm(@RequestParam String username){
		
		String result = "yes";
		if(username!=null){
			try {
				List<TXtYh> yhs = tXtYhManager.findBy("yhDm", username);
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
	@RequestMapping(value="getGUserOrganCheckTree",method=RequestMethod.POST)
	public @ResponseBody String getGUserOrganCheckTree(@RequestParam Map<String, Object> parameterMap){
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		Object id = parameterMap.get("id");
		Object jgId = parameterMap.get("jgId");
		String appId = parameterMap.get("appId")==null?"":parameterMap.get("appId")+"";
		if(StringHelper.isEmpty(appId)){
			appId = user.getAppId();
		}
		String result = "";
		if(id!=null){
			try {
				JSONArray ja = tXtJgService.getSubUserOrgan(id.toString(), jgId.toString(),appId);
				result = ja.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@RequestMapping(value="addGUser",method=RequestMethod.POST)
	public String addGUser(TXtYh user,String[] checkdel, Model model,String currentAppId){
		if(StringHelper.isEmpty(user.getYhDm())
				||StringHelper.isEmpty(user.getYhMc())
				||StringHelper.isEmpty(user.getMm())
				||checkdel == null 
				||checkdel.length < 1){
			return null;
		}
		
		user.setYhId(IdGenerator.getInstance().getUniqTime()+"");
		user.setLrRq(new Date());
		user.setXgRq(new Date());
		user.setScBj(Integer.valueOf(Constants.UN_DELETE_STATE));
		//user.setMm(EncryptUtils.encrypt(user.getMm(), EncryptUtils.SHA));
		if (simplePasswordEncoder != null) {
			user.setMm(simplePasswordEncoder.encode(user.getMm()));
        }
		//当前登陆用户信息user.setCzryId(gUser.getYhId());
		tXtYhManager.save(user);
		
	/*	TXtYhGw yhgw = new TXtYhGw();
		yhgw.setGwDm("11010");
		yhgw.setYhId(user.getYhId());
		yhgw.buildId();
		tXtYhGwManager.save(yhgw);
		yhgw = new TXtYhGw();
		yhgw.setGwDm("11021");
		yhgw.setYhId(user.getYhId());
		yhgw.buildId();
		tXtYhGwManager.save(yhgw);*/
		
		if(StringHelper.isNotEmpty(user.getYhId())){
			for(int i = 0; i < checkdel.length; i++){
				TXtJgYh jgyh = new TXtJgYh();
				jgyh.setJgId(checkdel[i]);
				user.setJgId(checkdel[i]);
				jgyh.setYhId(user.getYhId());
				jgyh.setDuId(IdGenerator.getInstance().getUniqTime()+"");
				tXtJgYhManager.save(jgyh);
			}
		}
		return "redirect:/user/user-list.izhbg?sjjgId="+user.getJgId()+"&currentAppId="+currentAppId;
		
	}
	@RequestMapping(value="updateGUser",method=RequestMethod.POST)
	public String updateGUser(TXtYh user,String checkdel, Model model,String currentAppId){
		if(StringHelper.isEmpty(user.getYhId())
				||StringHelper.isEmpty(user.getYhDm())
				||StringHelper.isEmpty(checkdel)){
			return null;
		}
		TXtYh item = tXtYhManager.findUniqueBy("yhId", user.getYhId());//QueryCache.get(TXtYh.class, user.getYhId());
		item.setYhId(user.getYhId());
		item.setYhDm(user.getYhDm());
		item.setYhMc(user.getYhMc());
		//item.setMm(EncryptUtils.encrypt(user.getMm(), EncryptUtils.SHA));
		item.setXb(user.getXb());
		item.setEmail(user.getEmail());
		item.setYxBj(user.getYxBj());
		item.setYhLx(user.getYhLx());
		item.setXh(user.getXh());
		item.setBz(user.getBz());
		item.setXgRq(new Date());
		item.setUserOfficePhone(user.getUserOfficePhone());
		item.setUserMobile(user.getUserMobile());
		item.setUserIcq(user.getUserIcq());
		item.setUserSystemAdmin(user.getUserSystemAdmin());
		item.setUserPriority(user.getUserPriority());
		item.setUserLevel(user.getUserLevel());
		item.setReadOnly(user.getReadOnly());
		item.setUserPwdDuration(user.getUserPwdDuration());
		item.setPhotoPath(user.getPhotoPath());
		item.setDuty(user.getDuty());
		item.setBranchedpassageJob(user.getBranchedpassageJob());
		item.setSecretary(user.getSecretary());
		item.setOfficeSpace(user.getOfficeSpace());
		item.setOutEmail(user.getOutEmail());
		item.setStaffId(user.getStaffId());
		item.setAppId(user.getAppId());
		item.setDdqmPath(user.getDdqmPath());
		tXtYhManager.update(item);
		
		//List ids = new QueryCache("select a.duId from TXtJgYh a where a.yhId=:yhId")
		//	.setParameter("yhId", user.getYhId()).list();
		List<TXtJgYh> objs = tXtJgYhManager.findBy("yhId", user.getYhId());//QueryCache.idToObj(TXtJgYh.class, ids);
		Boolean save = true;
		if(objs != null) 
			for(TXtJgYh o : objs) {
				user.setJgId(o.getJgId());
				if(o.getJgId().equals(checkdel))
					save = false;
				else
					tXtJgYhManager.remove(o);
			}
		if(save){
			TXtJgYh jgyh = new TXtJgYh();
			jgyh.setJgId(checkdel);
			jgyh.setYhId(user.getYhId());
			jgyh.setDuId(IdGenerator.getInstance().getUniqTime()+"");
			tXtJgYhManager.save(jgyh);
		}
		return "redirect:/user/user-list.izhbg?sjjgId="+user.getJgId()+"&currentAppId="+currentAppId;
		
	}
	
	@RequestMapping(value="deleteGuser",method=RequestMethod.POST)
	public @ResponseBody  String deleteGuser(TXtYh user,String[] checkdel, String sjjgId){
		String result="";
		try{
			if(checkdel == null || checkdel.length < 1){
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
			}
			List lst = new ArrayList();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtYh> itemLst = (List<TXtYh>)tXtYhManager.findByIds(lst); //QueryCache.idToObj(TXtYh.class, lst);
			
			for(TXtYh item : itemLst ){
				item.setScBj(new Integer(Constants.DELETE_STATE));
				tXtYhManager.update(item);
			}
			List<TXtJgYh> objs1 = null;
			List<TXtYhGnjs> objs2 = null;
			for(String s : checkdel) {
				objs1 = tXtJgYhManager.findBy("yhId", s);//QueryCache.idToObj(TXtJgYh.class, ids1);
				objs2 = tXtYhGnjsManager.findBy("yhId", s);//QueryCache.idToObj(TXtYhGnjs.class, ids2);
				if(objs1!=null&&objs1.size()>0)
					for(TXtJgYh tj:objs1)
						tXtJgYhManager.remove(tj);
				if(objs2!=null&&objs2.size()>0)
					for(TXtYhGnjs tyg:objs2)
						tXtYhGnjsManager.remove(tyg);
			}
			
			result = "sucess";
		} catch (Exception ex) {
		}
		
		return result;
	}
	
	@RequestMapping(value="removeGuserFromGroup",method=RequestMethod.POST)
	public @ResponseBody  String removeGuserFromGroup(TXtYh user,String[] checkdel, String jgId){
		String result="";
		try{
			if((checkdel == null || checkdel.length < 1)&&StringHelper.isEmpty(jgId)){
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
			}
			List lst = new ArrayList();
			String temp_sql =  "select duId from TXtJgYh where yhId=? and jgId=? ";
			Object obj = null;
			for(String s : checkdel) 
			{
				obj = tXtJgYhManager.findUnique(temp_sql, s,jgId);
				if(obj!=null){
					lst.add(obj.toString());
				}
			}
			List<TXtJgYh> txtjgyh = null;
			if(lst.size()>0)
				txtjgyh = tXtJgYhManager.findByIds(lst);
			if(txtjgyh!=null&&txtjgyh.size()>0)
			{
				for(TXtJgYh tx:txtjgyh){
					tXtJgYhManager.remove(tx);
				}
			}
				//List<TXtJgYh> objs1 = tXtJgYhManager.find(" ", values) //.findBy("yhId", yhId);//QueryCache.idToObj(TXtJgYh.class, ids1);
			result = "sucess";
		} catch (Exception ex) {
		}
		
		return result;
	}
	
	@RequestMapping(value="updPassword",method=RequestMethod.POST)
	public @ResponseBody  String updPassword(TXtYh user,String[] checkdel, String sjjgId){
		String result="";
		try {
			if (checkdel == null || checkdel.length < 1 ) {
				return null;
			}
			List lst = new ArrayList();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtYh> itemLst = (List<TXtYh>)tXtYhManager.findByIds(lst); //(List<TXtYh>) QueryCache.idToObj(TXtYh.class, lst);
			
			for(TXtYh item : itemLst ){
				if (simplePasswordEncoder != null) {
					item.setMm(simplePasswordEncoder.encode("123456"));
		        }
				tXtYhManager.update(item);
			}
			
			result = "sucess";
		} catch (Exception ex) {
		}
		
		return result;
	}
	@RequestMapping(value="updGUserStatus",method=RequestMethod.POST)
	public @ResponseBody  String updGUserStatus(TXtYh user,String[] checkdel, String sjjgId){
		String result="";
		try {
			if (checkdel == null || checkdel.length < 1 ) {
				return null;
			}
			List lst = new ArrayList();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtYh> itemLst = (List<TXtYh>)tXtYhManager.findByIds(lst); //(List<TXtYh>) QueryCache.idToObj(TXtYh.class, lst);
			
			for(TXtYh item : itemLst ){
				if(item.getYxBj()!=null&&item.getYxBj()==2){
					item.setYxBj(1);
				}else{
					item.setYxBj(2);
				}
				tXtYhManager.update(item);
			}
			
			result = "sucess";
		} catch (Exception ex) {
		}
		
		return result;
	}
	
	@RequestMapping("/updateUserInfo.izhbg")
	@ResponseBody
	public String updateUserInfo(HttpServletRequest request, HttpServletResponse response ,HttpSession session) throws Exception {
		String userMail = request.getParameter("user");
		if (userMail == null || userMail.length() == 0) {
			return "false";
		}
		TXtYh userInfo = tXtYhManager.findUniqueBy("yhId", userMail);
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
		tXtYhManager.update(userInfo);
		return "true";
	}
	
	
	


	  // ~ ======================================================================
	
    @Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
    @Resource
	public void setTXtYhGnjsManager(TXtYhGnjsManager xtYhGnjsManager) {
		tXtYhGnjsManager = xtYhGnjsManager;
	}
	@Resource
	public void setTXtYhManager(TXtYhManager xtYhManager) {
		tXtYhManager = xtYhManager;
	}

    @Resource
	public void setTXtJgService(TXtJgService xtJgService) {
		tXtJgService = xtJgService;
	}
    @Resource
	public void setTXtJgYhManager(TXtJgYhManager xtJgYhManager) {
		tXtJgYhManager = xtJgYhManager;
	}
    @Resource
    public void setSimplePasswordEncoder(
            SimplePasswordEncoder simplePasswordEncoder) {
        this.simplePasswordEncoder = simplePasswordEncoder;
    }
    @Resource
	public void setTXtJgManager(TXtJgManager xtJgManager) {
		tXtJgManager = xtJgManager;
	}
    
    @Resource
	public void setTXtYyService(TXtYyService xtYyService) {
		tXtYyService = xtYyService;
	}
	public void getOrgan(String organId){
		organs.add(organId);
		List<String> temp = tXtJgService.getOrganIds(organId);
		for(String s : temp)
			getOrgan(s);
	}
	public String getOrder(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? "order by "+page.getOrderBy() +" "+ page.getOrder() : " order by a.xgRq desc ";
	}
	public String getOrderColumn(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? ", " + page.getOrderBy() : ", a.xgRq ";
	}
	public String getWhere() {
		StringBuffer sb = new StringBuffer(" and a.scBj=" + Constants.UN_DELETE_STATE + " ");
		if(StringHelper.isNotEmpty(sjjgId)) {//神奇呀。
			if(StringHelper.isNotEmpty(includeFlag)) {
				organs = new ArrayList();
				getOrgan(sjjgId);
				StringBuilder sbd = new StringBuilder();
				for(String str : organs) {
					sbd.append("'").append(str).append("',");
				}
				sbd.deleteCharAt(sbd.length()-1);
				sb.append(" and b.jgId in("+sbd.toString()+") ");
			}
			else
				sb.append(" and b.jgId ='" + sjjgId + "' ");
		}else{
			sb.append(" and b.jgId ='false' ");
		}	 
		if(StringHelper.isNotEmpty(yhId))
			sb.append(" and a.yhId like :yhId ");
		if(StringHelper.isNotEmpty(yhDm))
			sb.append(" and a.yhDm like :yhDm ");
		if(StringHelper.isNotEmpty(yhMc))
			sb.append(" and a.yhMc like :yhMc ");
		if(StringHelper.isNotEmpty(yxBj))
			sb.append(" and a.yxBj = :yxBj ");
		if(StringHelper.isNotEmpty(scBj))
			sb.append(" and a.scBj = :scBj ");
		if(StringHelper.isNotEmpty(currentAppId))
			sb.append(" and a.appId = :appId ");
		return sb.toString();
	}
	
	public Map<String, Object> setWhere() {
//		if(StringHelper.isNotEmpty(sjjgId)) 
//			qc.setParameter("sjjgId", sjjgId);
		 Map<String, Object> params = new HashMap<String, Object>();
		if(StringHelper.isNotEmpty(yhId)) 
			params.put("yhId","%" + yhId.trim() + "%");
		if(StringHelper.isNotEmpty(yhDm)) 
			params.put("yhDm","%" + yhDm.trim() + "%");
		if(StringHelper.isNotEmpty(yhMc))
			params.put("yhMc","%" + yhMc.trim() + "%");
		if(StringHelper.isNotEmpty(yxBj))
			params.put("yxBj",Integer.parseInt(yxBj.trim()));
		if(StringHelper.isNotEmpty(scBj))
			params.put("scBj",scBj.trim());
		if(StringHelper.isNotEmpty(currentAppId))
			params.put("appId",currentAppId);
		
		return params;
			
	}
	
	
}

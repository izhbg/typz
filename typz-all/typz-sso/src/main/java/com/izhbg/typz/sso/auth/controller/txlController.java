package com.izhbg.typz.sso.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.sso.auth.UserAuthDTO;
import com.izhbg.typz.sso.auth.dto.TXtJg;
import com.izhbg.typz.sso.auth.manager.TXtJgManager;
import com.izhbg.typz.sso.auth.manager.TXtJgYhManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.auth.service.TXtJgService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;


@Controller
@RequestMapping("/txl")
public class txlController {
	private TXtJgManager tXtJgManager;
	private TXtJgYhManager tXtJgYhManager;
	private TXtYhManager tXtYhManager;
	private TXtJgService tXtJgService;
	private String yhId,yhDm,yhMc,yxBj,sjjgId,includeFlag="true",currentSysId;
	private List<String> organs = null;
	
	@RequestMapping("list")
	public String list(@ModelAttribute Page page,
					   @RequestParam Map<String, Object> parameterMap, 
					   Model model) {
		sjjgId= parameterMap.get("sjjgId")==null?"":parameterMap.get("sjjgId").toString();
		String key= parameterMap.get("key")==null?"":parameterMap.get("key").toString();
		//获取机构表中的 根节点为空的 列表 作为根基组织，且默认 取根基组织的第一个 的下级节点作为树节点
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		List<TXtJg> tXtJgList = tXtJgManager.find("from TXtJg where sjjgId=?", "");
		if(tXtJgList!=null&&tXtJgList.size()>0)
		{
			currentSysId=tXtJgList.get(0).getJgId();
			parameterMap.put("currentSysId", currentSysId);
			if(StringHelper.isEmpty(sjjgId)){
				sjjgId = currentSysId;
			}
		}
		String result = "";
		StringBuffer sb = new StringBuffer("select DISTINCT a  ");
		sb.append(" from TXtYh a ,TXtJgYh b,TXtJg c where a.yhId=b.yhId and b.jgId=c.jgId and a.yhId!='is8v1wz7g0eyl86iz7g1' and a.yhId!='1410920398925'");
		if(StringHelper.isNotEmpty(key)){
			sb.append(" and (a.yhMc like '%"+key+"%' or a.duty like '%"+key+"%' or c.jgMc like '%"+key+"%'  or a.userOfficePhone like '%"+key+"%')");
			sb.append(getSearchWhere());
		}else{
			sb.append(getWhere());
		}
		sb.append(getOrder());
		HashMap map = new HashMap();
		page =  tXtYhManager.pagedQuery(sb.toString(), page.getPageNo(), page.getPageSize(), map);
		try {
			if(tXtJgList!=null&&tXtJgList.size()>0){
				JSONObject jo = tXtJgService.getRootOrgan(currentSysId);
				
				JSONArray ja = tXtJgService.getSubOrgan(currentSysId,user.getAppId());
				if(jo.size()>0)
					ja.add(jo);
				result = ja.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			parameterMap.put("sjjgId", sjjgId);
			model.addAttribute("result", result);
			model.addAttribute("page",page);
			model.addAttribute("key",key);
			model.addAttribute("parameterMap", parameterMap);
			model.addAttribute("tXtJgList", tXtJgList);
			model.addAttribute("appId", user.getAppId());
		}
		
		return "txl/txl";
	}
	
	public String getOrder() {
		return " order by c.xh , a.xh ";
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
		if(true)
			sb.append(" and a.scBj = 2 ");
		return sb.toString();
	}
	public String getSearchWhere() {
		StringBuffer sb = new StringBuffer(" and a.scBj=" + Constants.UN_DELETE_STATE + " ");
		
		if(StringHelper.isNotEmpty(yhId))
			sb.append(" and a.yhId like :yhId ");
		if(StringHelper.isNotEmpty(yhDm))
			sb.append(" and a.yhDm like :yhDm ");
		if(StringHelper.isNotEmpty(yhMc))
			sb.append(" and a.yhMc like :yhMc ");
		if(StringHelper.isNotEmpty(yxBj))
			sb.append(" and a.yxBj = :yxBj ");
		if(true)
			sb.append(" and a.scBj = 2 ");
		return sb.toString();
	}
	public void getOrgan(String organId){
		organs.add(organId);
		List<String> temp = tXtJgService.getOrganIds(organId);
		for(String s : temp)
			getOrgan(s);
	}
	@Resource
	public void setTXtJgManager(TXtJgManager xtJgManager) {
		tXtJgManager = xtJgManager;
	}

	@Resource
	public void setTXtJgYhManager(TXtJgYhManager xtJgYhManager) {
		tXtJgYhManager = xtJgYhManager;
	}

	@Resource
	public void setTXtYhManager(TXtYhManager xtYhManager) {
		tXtYhManager = xtYhManager;
	}

	@Resource
	public void setTXtJgService(TXtJgService xtJgService) {
		tXtJgService = xtJgService;
	}
	
	
}

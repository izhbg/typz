package com.izhbg.typz.sso.auth.service.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.JSONParam;
import com.izhbg.typz.base.util.StringUtils;
import com.izhbg.typz.sso.auth.UserAuthDTO;
import com.izhbg.typz.sso.auth.dto.OrgUserQuery;
import com.izhbg.typz.sso.auth.dto.TXtJg;
import com.izhbg.typz.sso.auth.dto.TXtJgYh;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.manager.TXtJgManager;
import com.izhbg.typz.sso.auth.manager.TXtJgYhManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.auth.service.ComparatorTXtJg;
import com.izhbg.typz.sso.auth.service.ComparatorTXtYh;
import com.izhbg.typz.sso.auth.service.OrgUserService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;

@Service("orgUserService")
@Transactional(rollbackFor=Exception.class)
public class OrgUserServiceImp implements OrgUserService{

	private TXtJgManager tXtJgManager;
	private TXtJgYhManager tXtJgYhManager;
	private TXtYhManager tXtYhManager;
	@Override
	public Page queryPageList(Page page, OrgUserQuery orgUserQuery)
			throws Exception {
		StringBuffer sb = new StringBuffer("select a.duId from TXtJgYh a , TXtYh b where a.yhId=b.yhId ");
		sb.append(getWhere(orgUserQuery));
		sb.append(getOrder(page));
		page = tXtJgYhManager.pagedQuery(sb.toString(), page.getPageNo(), page.getPageSize(), setWhere(orgUserQuery));
		List<String> list = (List)page.getResult();
		List<TXtJgYh> listYh=null;
		if(list!=null&&list.size()>0)
			listYh = tXtJgYhManager.findByIds(list);
		if(listYh!=null)
		for(TXtJgYh o : listYh) {
			TXtYh gnjs = tXtYhManager.findUniqueBy("yhId", o.getYhId());
			o.setYh(gnjs);
		}
		page.setResult(listYh);
		return page;
	}

	@Override
	public String getOrgUserPage(JSONParam[] params)
			throws Exception {
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		for(JSONParam son:params){
			parameterMap.put(son.getName(), son.getValue());
		}
		String jgId = StringUtils.getString(parameterMap.get("jgId"));
		String sEcho = StringUtils.getString(parameterMap.get("sEcho"));
		String iDisplayLength = StringUtils.getString(parameterMap.get("iDisplayLength"));
		String iDisplayStart = StringUtils.getString(parameterMap.get("iDisplayStart"));
		Page page2 = new Page();
		if(StringHelper.isNotEmpty(iDisplayLength)){
			page2.setPageSize(Integer.parseInt(iDisplayLength));
		}
		if(StringHelper.isNotEmpty(iDisplayStart)&&StringHelper.isNotEmpty(iDisplayLength)){
			page2.setPageNo(Integer.parseInt(iDisplayStart)/Integer.parseInt(iDisplayLength)+1);
		}
		StringBuffer sb = new StringBuffer("select a.yhId from TXtYh a ");
		sb.append(getYhWhere(parameterMap));
		sb.append(getYhOrder(page2));
		
		page2 = tXtJgYhManager.pagedQuery(sb.toString(), page2.getPageNo(), page2.getPageSize(), setYhWhere(parameterMap));
		List<String> list = (List)page2.getResult();
		List<TXtYh> listYh=null;
		if(list!=null&&list.size()>0)
			listYh = tXtYhManager.findByIds(list);
		if(listYh!=null)
			page2.setResult(listYh);
		if(StringHelper.isNotEmpty(jgId)) {
			List<TXtJgYh> objs = tXtJgYhManager.findBy("jgId", jgId);
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
		Map<String,Object> map = new HashMap<String,Object>();
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
		return json.toString();
		
	}

	@Override
	public void add(String jgId,String[] checkdel2) throws Exception {
		TXtJgYh item;
		for(String id : checkdel2){
			List uid = tXtJgYhManager.find("select a.duId from TXtJgYh a where a.jgId=? and a.yhId=?", jgId,id);
			if(uid == null||uid.size()<1) {
				item = new TXtJgYh();
				item.setJgId(jgId);
				item.setYhId(id);
				item.setDuId(IdGenerator.getInstance().getUniqTime()+"");
				tXtJgYhManager.save(item);
			}
		}
		
	}

	@Override
	public void deleteByIds(String[] checkdel) throws Exception {
		List lst = new ArrayList();
		for(String s : checkdel) 
			lst.add(s);
		
		List<TXtJgYh> items = tXtJgYhManager.findByIds(lst);
		
		if(items != null)
			for(TXtJgYh o : items) {
				tXtJgYhManager.remove(o);
			}
		
	}
	
	public String getOrder(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? " order by "+page.getOrderBy() +" "+ page.getOrder() : " order by a.duId ";
	}
	public String getWhere(OrgUserQuery orgUserQuery) {
		
		StringBuffer sb = new StringBuffer("and b.scBj=" + Constants.UN_DELETE_STATE + " ");
		if(StringHelper.isNotEmpty(orgUserQuery.getJgId()))
			sb.append(" and a.jgId = :jgId ");
		if(StringHelper.isNotEmpty(orgUserQuery.getYhDm()))
			sb.append(" and b.yhDm like :yhDm ");
		if(StringHelper.isNotEmpty(orgUserQuery.getYhMc()))
			sb.append(" and b.yhMc like :yhMc ");
		return sb.toString();
	}
	
	public Map<String, Object> setWhere(OrgUserQuery orgUserQuery) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(StringHelper.isNotEmpty(orgUserQuery.getJgId())) 
			params.put("jgId", orgUserQuery.getJgId().trim());
		if(StringHelper.isNotEmpty(orgUserQuery.getYhDm()))
			params.put("yhDm", "%" + orgUserQuery.getYhDm().trim() + "%");
		if(StringHelper.isNotEmpty(orgUserQuery.getYhMc()))
			params.put("yhMc", "%" + orgUserQuery.getYhMc().trim() + "%");
		
		return params;
			
	}

	@Override
	public JSONArray getSubOrganUserCheck(String jgId, String appId)
			throws Exception {
		List<TXtJg> lst = tXtJgManager.find("from TXtJg a where a.sjjgId=? and a.scBj=? and a.appId=? order by a.xh ", jgId,Constants.UN_DELETE_STATE,appId);
		if(lst!=null){
			 ComparatorTXtJg comparator=new ComparatorTXtJg();
			 Collections.sort(lst, comparator);
		}
		List<TXtYh> ulst = tXtYhManager.find("select a from TXtYh a ,TXtJgYh b where a.yhId=b.yhId and b.jgId=? and a.scBj=? and a.yxBj=? order by a.xh", jgId, Constants.UN_DELETE_STATE,Constants.STATUS_VALID);
		if(ulst!=null){
			 ComparatorTXtYh comparator=new ComparatorTXtYh();
			 Collections.sort(ulst, comparator);
		}
		JSONArray jaTree = new JSONArray();
		JSONObject one;
		Object id;
		if(lst != null)
			for(TXtJg item : lst) {
				one = new JSONObject();
				one.put("id", item.getJgId());
				one.put("name", item.getJgMc());
				one.put("pId", item.getSjjgId());
				one.put("nocheck", false);
				one.put("isParent", true);
				jaTree.add(one);
			}
		if(ulst != null)
			for(TXtYh o : ulst) {
				one = new JSONObject();
				one.put("id", o.getYhId());
				one.put("name", o.getYhMc());
				one.put("pId", jgId);
				one.put("nocheck", false);
				one.put("isParent", false);
				jaTree.add(one);
			}
		return jaTree;
	}
	public String getYhOrder(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? " order by "+page.getOrderBy() +" "+ page.getOrder() : " order by a.lrRq ";
	}
	public String getYhWhere(Map<String,Object> parameterMap) {
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		StringBuffer sb = new StringBuffer("where a.scBj=" + Constants.UN_DELETE_STATE + " and a.appId='"+user.getAppId()+"' ");
		if(StringHelper.isNotEmpty(StringUtils.getString(parameterMap.get("yhDm"))))
			sb.append(" and a.yhDm like :yhDm ");
		if(StringHelper.isNotEmpty(StringUtils.getString(parameterMap.get("yhMc"))))
			sb.append(" and a.yhMc like :yhMc ");
		return sb.toString();
	}
	
	public Map<String, Object> setYhWhere(Map<String,Object> parameterMap) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringHelper.isNotEmpty(StringUtils.getString(parameterMap.get("yhDm")))) 
			params.put("yhDm", "%" + StringUtils.getString(parameterMap.get("yhDm")) + "%");
		if(StringHelper.isNotEmpty(StringUtils.getString(parameterMap.get("yhMc"))))
			params.put("yhMc", "%" + StringUtils.getString(parameterMap.get("yhMc")) + "%");
		
		return params;
		
	}

	@Override
	public JSONObject getRootOrganCheck(String jgId, String appId)
			throws Exception {
		TXtJg item = tXtJgManager.findUnique("from TXtJg where jgId=? and appId=?", jgId,appId);
		JSONObject one = new JSONObject();
		if(item!=null){
			one.put("id", item.getJgId());
			one.put("name", item.getJgMc());
			one.put("pId", item.getSjjgId());
			one.put("isParent", true);
			one.put("nocheck", true);
			one.put("open", true);
		}
		return one;
	}
	@Resource
	public void settXtJgManager(TXtJgManager tXtJgManager) {
		this.tXtJgManager = tXtJgManager;
	}
	@Resource
	public void settXtJgYhManager(TXtJgYhManager tXtJgYhManager) {
		this.tXtJgYhManager = tXtJgYhManager;
	}
	@Resource
	public void settXtYhManager(TXtYhManager tXtYhManager) {
		this.tXtYhManager = tXtYhManager;
	}
	
}

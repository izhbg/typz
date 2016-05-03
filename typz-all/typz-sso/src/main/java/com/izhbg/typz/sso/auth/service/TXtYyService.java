package com.izhbg.typz.sso.auth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.sso.auth.dto.TXtYy;
import com.izhbg.typz.sso.auth.manager.TXtYyManager;

@Service
@Transactional(rollbackFor = Exception.class)
public class TXtYyService {
	
	private TXtYyManager tXtYyManager;
	public  List<TXtYy> getSystems(){
		
		return tXtYyManager.getAll();//QueryCache.idToObj(TXtYy.class, new QueryCache("select a.yyId from TXtYy a").listCache());
	}
	public  TXtYy getSystem(String appId){
		TXtYy app;
		if(StringHelper.isEmpty(appId))
			app = new TXtYy();
		else
			app = tXtYyManager.findUniqueBy("yyId", appId);//QueryCache.get(TXtYy.class, appId); 
		return app;
	}
	public   JSONArray getAppTree() throws JSONException{
		//List ids = tXtYyManager.//new QueryCache("select a.yyId from TXtYy a").listCache();
		List<TXtYy> lst = tXtYyManager.getAll();
		JSONArray jaTree = new JSONArray();
		JSONObject one = new JSONObject();
		one.put("id", "0");
		one.put("name", "应用系统");
		one.put("pId", "-1");
		one.put("isParent", true);
		one.put("nocheck", true);
		one.put("open", true);
		jaTree.add(one);
		for(TXtYy item : lst) {
			one = new JSONObject();
			one.put("id", item.getYyId());
			one.put("name", item.getAppName());
			one.put("pId", "0");
			one.put("nocheck", true);
			one.put("isParent", true);
			jaTree.add(one);
		}
		return jaTree;
	}
	
	public  Page page(Page page, String code, String appName, String classification){
		
		StringBuffer sb = new StringBuffer("from TXtYy a where 1=1 ");
		if(StringHelper.isNotEmpty(code))
			sb.append(" and a.code like :code ");
		if(StringHelper.isNotEmpty(appName))
			sb.append(" and a.appName like :appName ");
		if(StringHelper.isNotEmpty(classification))
			sb.append(" and a.classification = :classification ");
		if(page != null && StringHelper.isNotEmpty(page.getOrderBy())) 
			sb.append("order by "+page.getOrderBy());
		else
			sb.append(" order by a.sortNo ");
		
		//QueryCache qc = new QueryCache(sb.toString());
		Map<String, Object> map = new HashMap();
		if(StringHelper.isNotEmpty(code)) 
			map.put("code", "%" + code.trim() + "%");
		if(StringHelper.isNotEmpty(appName))
			map.put("appName", "%" + appName.trim() + "%");
		if(StringHelper.isNotEmpty(classification))
			map.put("classification", Integer.parseInt(classification));
		
		page = tXtYyManager.pagedQuery(sb.toString(), page.getPageNo(), page.getPageSize(), map);
		
		/*List<String> list = (List)page.getResult();
		List<TXtYy> listYh=null;
		try {
			if(list!=null&&list.size()>0)
				listYh = tXtYyManager.findByIds(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//find(sb.toString(), list.toArray()).list();
		page.setResult(listYh);*/
		return page;
	}
	/*public static String isAccess(String code,String password)
    {
    	if(StringHelper.isEmpty(code) || StringHelper.isEmpty(password))
    	{
    		return APIVeryfyErrorMessage.PASSWORD_OR_YYID_IS_NULL;
    	}
    		
    	TXtYy tXtYy = TXtYy.getByCode(code);
    	if(tXtYy==null)
    	{
    		return APIVeryfyErrorMessage.YYID_NOT_EXIST;
    	}	
    	
    	if(tXtYy !=null && StringHelper.isNotEmpty(tXtYy.getPassword()) && password.equals(tXtYy.getPassword()))
    	{
    		return APIVeryfyErrorMessage.NULL_EXCEPTION;
    	}
    	else
    	{	
        	return APIVeryfyErrorMessage.PASSWORD_NOT_CORRECT;
    	}

    }
    
    public static TXtYy getByCode( String code )
	{
		String key =  
				MemHelp.MD5Encode(TXtYy.class.getName()  + "xtYyCode" + code);
		String id = (String) MemCachedFactory.get(key);
		if( id == null )
		{
			id = getByCodeFromDb(code);
		}
		
		TXtYy cache = null;
		if( id != null )
		{
			MemCachedFactory.set(key, id);
			cache = QueryCache.get(TXtYy.class, id);
			if( cache == null )
			{
				MemCachedFactory.delete(key);
			}
		} 
		
		return cache;
	}
    
    public static String getByCodeFromDb(String code )
	{
		return  HibernateUtil.currentSession()
			.find("select a.yyId from TXtYy a where code = :code ")
				.setParameter("code", code).uniqueResult().toString();
	}*/

	@Resource
	public void setTXtYyManager(TXtYyManager xtYyManager) {
		tXtYyManager = xtYyManager;
	} 
}

package com.izhbg.typz.sso.auth.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.sso.auth.dto.TXtGnjs;
import com.izhbg.typz.sso.auth.manager.TXtGnjsManager;

@Service
@Transactional(rollbackFor = Exception.class)
public class TXtGnjsService {
	private TXtGnjsManager tXtGnjsManager;
	public  List<TXtGnjs> getByAppCode( String appCode )
	{
		List roleIdList = getRoleListByAppCode(appCode);
		List<TXtGnjs> cacheTXtGnjsList = new ArrayList<TXtGnjs>();
		if(roleIdList == null){
			return cacheTXtGnjsList; 
		}
		for(int i=0;i<roleIdList.size();i++){
			TXtGnjs  gnJs = tXtGnjsManager.findUniqueBy("gnjsDm", (String)roleIdList.get(i));//QueryCache.get(TXtGnjs.class, (String)roleIdList.get(i));
         	cacheTXtGnjsList.add(gnJs);
		}
		return cacheTXtGnjsList;
	}
	
	public  List getRoleListByAppCode(String appCode )
	{
		return tXtGnjsManager.find("select a.gnjsDm from TXtGnjs a where (appId = ? or jsLx=2)", appCode);
		  /*HibernateUtil.currentSession()
			.find("select a.gnjsDm from TXtGnjs a where appId = :appCode ")
				.setParameter("appCode", appCode).list();*/
	}

	public   JSONArray getSubGUserRole(String yhId, String appId) throws JSONException{
		List jsids = tXtGnjsManager.find("select a.jsDm from TXtYhGnjs a where a.yhId=?", yhId);//new QueryCache("select a.jsDm from TXtYhGnjs a where a.yhId=:yhId")
			//.setParameter("yhId", yhId).listCache();
		List ids = tXtGnjsManager.find("select a.gnjsDm from TXtGnjs a where (a.appId=? or a.jsLx=2) and a.yxBj=1", appId);//new QueryCache("select a.gnjsDm from TXtGnjs a where a.appId=:appId and a.yxBj=1")
			//.setParameter("appId", appId).listCache();
		List<TXtGnjs> lst = null;//QueryCache.idToObj(TXtGnjs.class, ids);
		if(ids!=null&&ids.size()>0){
			lst = tXtGnjsManager.findByIds(ids);
		}
		JSONArray jaTree = new JSONArray();
		JSONObject one;
		Object id;
		if(lst != null) 
			for(TXtGnjs item : lst) {
				one = new JSONObject();
				one.put("id", item.getGnjsDm());
				one.put("name", item.getGnjsMc());
				one.put("pId", "0");
				one.put("nocheck", false);
				one.put("isParent", false);
				if(jsids.contains(item.getGnjsDm())){
					one.put("chkDisabled", true);
					one.put("checked", true);
				}
				jaTree.add(one);
			}
		return jaTree;
	}
	@Resource
	public void setTXtGnjsManager(TXtGnjsManager xtGnjsManager) {
		tXtGnjsManager = xtGnjsManager;
	}
	
	

}

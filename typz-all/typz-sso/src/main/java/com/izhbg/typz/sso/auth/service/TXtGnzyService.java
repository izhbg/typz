package com.izhbg.typz.sso.auth.service;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.sso.auth.dto.TXtGnjsZy;
import com.izhbg.typz.sso.auth.dto.TXtGnzy;
import com.izhbg.typz.sso.auth.manager.TXtGnjsZyManager;
import com.izhbg.typz.sso.auth.manager.TXtGnzyManager;

@Service
@Transactional(rollbackFor = Exception.class)
public class TXtGnzyService {
	
	private TXtGnzyManager tXtGnzyManager;
	private TXtGnjsZyManager tXtGnjsZyManager;
	
	
	public  List getZeroDB()
	{
		return tXtGnzyManager.find("select a.gnDm  from TXtGnzy a where a.gnLx = ?", 0);/*HibernateUtil.currentSession().find(
			"select a.gnDm  from TXtGnzy a where a.gnLx = :gnLx")
		.setParameter("gnLx", 0).list(); */
	}
	
	public  TXtGnzy getFunc(String funcId){
		TXtGnzy func;
		if(StringHelper.isEmpty(funcId))
			func = new TXtGnzy();
		else
			func = tXtGnzyManager.findUniqueBy("gnDm", funcId);//QueryCache.get(TXtGnzy.class, funcId); 
		return func;
	}
	public   JSONObject getRootFunc(String gnDm) throws JSONException{//10
		if(StringHelper.isEmpty(gnDm))
			return null;
		TXtGnzy item = tXtGnzyManager.findUniqueBy("gnDm", gnDm);//QueryCache.get(TXtGnzy.class, gnDm);
		JSONObject one = new JSONObject();
		one.put("id", item.getGnDm());
		one.put("name", item.getGnMc());
		one.put("pId", item.getSjgnDm());
		one.put("isParent", true);
		one.put("open", true);
		return one;
	}
	public   JSONArray getSubRoleFunc(String sjgnDm, String jsDm,String appId) throws JSONException{
		
		List fids = tXtGnzyManager.find("select a.gnzyDm from TXtGnjsZy a where a.jsDm=?", jsDm);//new QueryCache("select a.gnzyDm from TXtGnjsZy a where a.jsDm=:jsDm")
			//.setParameter("jsDm", jsDm).listCache();
		
		List ids = tXtGnzyManager.find("select a.gnDm from TXtGnzy a where a.sjgnDm=?  and a.yxBj=1 and (a.appId=? or a.gnLx=2 ) order by a.gnXh",sjgnDm,appId);//new QueryCache("select a.gnDm from TXtGnzy a where a.sjgnDm=:sjgnDm and a.yxBj=1 order by a.gnXh")
			//.setParameter("sjgnDm", sjgnDm).listCache();
		List<TXtGnzy> lst = null;
		if(ids!=null&&ids.size()>0)
			lst= tXtGnzyManager.findByIdsOrder(ids,"gnXh",true);//QueryCache.idToObj(TXtGnzy.class, ids);
		
		JSONArray jaTree = new JSONArray();
		JSONObject one;
		Object id=null;
		if(lst!=null)
			for(TXtGnzy item : lst) {
				one = new JSONObject();
				one.put("id", item.getGnDm());
				one.put("name", item.getGnMc());
				one.put("pId", item.getSjgnDm());
				List list = tXtGnzyManager.find("select a.gnDm from TXtGnzy a where a.sjgnDm=? and (a.appId=? or a.gnLx=2 )", item.getGnDm(),appId);//new QueryCache("select a.gnDm from TXtGnzy a where a.sjgnDm=:sjgnDm")
				if(list!=null&&list.size()>0){
					id = list.get(0);
				}
					//.setParameter("sjgnDm", item.getGnDm()).setMaxResults(1).uniqueResultCache();
				if(id == null){//leaf
					one.put("nocheck", false);
					one.put("isParent", false);
					if(fids.contains(item.getGnDm())){
						List listid = tXtGnjsZyManager.find("select uuid from TXtGnjsZy where jsDm=? and gnzyDm=?", jsDm,item.getGnDm());
						if(listid!=null&&listid.size()>0){
							List<TXtGnjsZy> lis = tXtGnjsZyManager.findByIds(listid);
							if(lis!=null&&lis.size()>0){
								TXtGnjsZy tjz = lis.get(0);
								one.put("isRead", tjz.getIsRead()+"");
								one.put("isCreate", tjz.getIsCreate()+"");
								one.put("isUpdate", tjz.getIsUpdate()+"");
								one.put("isDelete", tjz.getIsDelete()+"");
								one.put("isAll", tjz.getIsAll()+"");
							}
						}
						one.put("chkDisabled", true);
						one.put("checked", true);
					}
				} else {
					one.put("nocheck", true);
					one.put("isParent", true);
				}
				jaTree.add(one);
			}
		return jaTree;
	}
	
	public   JSONObject getRootRoleFunc(String gnDm) throws JSONException{//10
		TXtGnzy item = tXtGnzyManager.findUniqueBy("gnDm", gnDm);//QueryCache.get(TXtGnzy.class, gnDm);
		JSONObject one = new JSONObject();
		one.put("id", item.getGnDm());
		one.put("name", item.getGnMc());
		one.put("pId", item.getSjgnDm());
		one.put("isParent", true);
		one.put("nocheck", true);
		one.put("open", true);
		one.put("isRead", "0");
		one.put("isCreate", "0");
		one.put("isUpdate", "0");
		one.put("isDelete", "0");
		one.put("isAll", "0");
		return one;
	}
	public   JSONArray getSubFuncCheck(String sjgnDm, String gnDm,String appId) throws JSONException{
		
		StringBuffer sb = new StringBuffer("select a.gnDm from TXtGnzy a where a.sjgnDm=? and (a.appId=? or a.gnLx=2 ) order by a.gnXh");
		List idss = tXtGnzyManager.find(sb.toString(), sjgnDm,appId);/*new QueryCache()
			.setParameter("sjgnDm", sjgnDm).listCache();*/
		List<TXtGnzy> lst = null;
		if(idss!=null&&idss.size()>0)
			lst = tXtGnzyManager.findByIdsOrder(idss,"gnXh",true);//QueryCache.idToObj(TXtGnzy.class, idss);
		
		JSONArray jaTree = new JSONArray();
		JSONObject one;
		Object id;
		List ids;
		if(lst!=null&&lst.size()>0)
			for(TXtGnzy item : lst) {
				one = new JSONObject();
				one.put("id", item.getGnDm());
				one.put("name", item.getGnMc());
				one.put("pId", item.getSjgnDm());
				ids = tXtGnzyManager.find("select a.gnDm from TXtGnzy a where a.sjgnDm=?", item.getGnDm());//new QueryCache("select a.gnDm from TXtGnzy a where a.sjgnDm=:sjgnDm")
					//.setParameter("sjgnDm", item.getGnDm()).listCache();
				if(ids == null || ids.size() < 1) 
					one.put("isParent", false);
				else
					one.put("isParent", true);
				if(gnDm != null && gnDm.equals(item.getGnDm()) )
					one.put("checked", true);
				jaTree.add(one);
			}
		return jaTree;
	}
	public   JSONObject getRootFuncCheck(String gnDm, String iGnDm) throws JSONException{//10
		TXtGnzy item = tXtGnzyManager.findUniqueBy("gnDm", gnDm);//QueryCache.get(TXtGnzy.class, gnDm);
		JSONObject one = new JSONObject();
		one.put("id", item.getGnDm());
		one.put("name", item.getGnMc());
		one.put("pId", item.getSjgnDm());
		one.put("isParent", true);
		if(iGnDm != null && iGnDm.equals(item.getGnDm())){
			one.put("checked", true);
		}
		one.put("open", true);
		return one;
	}
	public void setTopToList( List all,TXtGnzy temp )
	{
		TXtGnzy top = temp;
		while( top != null )
		{
			if( !all.contains(top.getGnDm()) )
			{
				all.add( top.getGnDm() );
			}
			if( StringHelper.isEmpty(top.getSjgnDm()) || top.getSjgnDm().equals("-1"))
			{
				top = null;
			}else
			{ 
				try {
					top =tXtGnzyManager.findUniqueBy("sjgnDm", top.getSjgnDm()); //QueryCache.get(TXtGnzy.class, top.getSjgnDm());
				} catch (ClassCastException e) {
					//System.out.println(  top.sjgnDm +  "<-id key-> " + QueryCache.Key(TXtGnzy.class.getName(), top.sjgnDm) + " " + top );
					e.printStackTrace();
				}
				
			}
		}
	}


	@Resource
	public void setTXtGnzyManager(TXtGnzyManager xtGnzyManager) {
		tXtGnzyManager = xtGnzyManager;
	}
	@Resource
	public void setTXtGnjsZyManager(TXtGnjsZyManager xtGnjsZyManager) {
		tXtGnjsZyManager = xtGnjsZyManager;
	} 
	
	
	
}

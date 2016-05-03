package com.izhbg.typz.sso.auth.service;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.sso.auth.dto.TXtGnzy;
import com.izhbg.typz.sso.auth.dto.TXtJg;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.manager.TXtJgManager;
import com.izhbg.typz.sso.auth.manager.TXtJgYhManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;

@Service
@Transactional(rollbackFor = Exception.class)
public class TXtJgService {
	private TXtJgManager tXtJgManager;
	private TXtYhManager tXtYhManager;
	private TXtJgYhManager tXtJgYhManager;
	
	
	public List getSubJgfromDB(TXtJg tXtJg)
	{
	// 旧的树结构查询语句，新的已修改by xingzhc
//		return HibernateUtil.currentSession().find("select a.jgId from TXtJg a where sjjgId = :sjjgId")
//		.setParameter("sjjgId", this.jgId ).list();
		return tXtJgManager.find("select a.jgId from TXtJg a where a.sjjgId =? and  a.yxBj = '1' and a.scBj = '2' and a.jgLx = '0' order by a.xh ", tXtJg.getJgId());/*HibernateUtil.currentSession().find("select a.jgId from TXtJg a where a.sjjgId = :sjjgId and  a.yxBj = '1' and a.scBj = '2' and a.jgLx = '0' order by a.xh ")
		.setParameter("sjjgId", this.jgId ).list();*/
	}
	/**
	 * 
	 * 领导日程机构人员树，为了不影响发文，新写方法
	 * @zhaojf
	 */
	/*public List getTleaderScheduleSubJgfromDB()
	{
		return HibernateUtil.currentSession().find("select a.jgId from TXtJg a where a.sjjgId = :sjjgId and  a.yxBj = '1' and a.scBj = '2' and a.jgLx = '0' and a.jgMc not like '%通信管理局%' order by a.xh asc ")
		.setParameter("sjjgId", this.jgId ).list();
	}*/			
	public TXtJg getSJJG(TXtJg tXtJg)
	{
		return tXtJgManager.findUniqueBy("sjjgId", tXtJg.getSjjgId());//QueryCache.get(TXtJg.class, this.sjjgId );
	}
	
	public  TXtJg getRoot()
	{
		//获取当前登陆人员所在的机构ID
		return tXtJgManager.findUniqueBy("jgId", "1");// QueryCache.get(TXtJg.class, "1" );
	}
	
	public void setTopToList( List all,TXtJg tXtJg )
	{
		TXtJg top = tXtJg;
		while( top!=null&&top.getSjjgId() != null )
		{
			if( !all.contains(top.getJgId()) )
			{
				all.add( top.getJgId() );
			}
			if( StringHelper.isEmpty(top.getSjjgId()) )
			{
				top = null;
			}else
			{ 
				top = tXtJgManager.findUniqueBy("sjjgId", tXtJg.getSjjgId());//QueryCache.get(TXtJg.class, top.getSjjgId());
			}
		}
	}
	public  List getOrganIds(String sjjgId){
		List list = tXtJgManager.find("select a.jgId from TXtJg a where a.sjjgId=? and a.scBj=? order by a.xh", sjjgId, Constants.UN_DELETE_STATE);
		return list;
	}
	
	public  TXtJg getOrgan(String jgId){
		TXtJg jg;
		if(StringHelper.isEmpty(jgId))
			jg = new TXtJg();
		else
			jg = tXtJgManager.findUniqueBy("jgId", jgId);//QueryCache.get(TXtJg.class, jgId); 
		return jg;
	}
	
	
	public  JSONArray getSubOrganUserCheck(String sjjgId,String appId) throws JSONException{
		List<TXtJg> lst = getOrgans(sjjgId,appId);
		if(lst!=null){
			 ComparatorTXtJg comparator=new ComparatorTXtJg();
			 Collections.sort(lst, comparator);
		}
		List<TXtYh> ulst = getUsers(sjjgId);
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
				one.put("pId", sjjgId);
				one.put("nocheck", false);
				one.put("isParent", false);
				jaTree.add(one);
			}
		return jaTree;
	}
	public   JSONArray getSubOrgan(String sjjgId,String appId) throws JSONException{
		List<TXtJg> lst = getOrgans(sjjgId,appId);
		if(lst!=null){
			 ComparatorTXtJg comparator=new ComparatorTXtJg();
			 Collections.sort(lst, comparator);
		}
		JSONArray jaTree = new JSONArray();
		JSONObject one;
		List ids;
		if(lst != null)
			for(TXtJg item : lst) {
				one = new JSONObject();
				one.put("id", item.getJgId());
				one.put("name", item.getJgMc());
				one.put("pId", item.getSjjgId());
				ids = getOrganIds(item.getJgId());
				if(ids == null || ids.size() < 1)
					one.put("isParent", false);
				else
					one.put("isParent", true);
				jaTree.add(one);
			}
		return jaTree;
	}
	public   JSONObject getRootOrganCheck(String jgId,String appId) throws JSONException{//22
		TXtJg item = tXtJgManager.findUnique("from TXtJg where jgId=? and appId=?", jgId,appId);//.findUniqueBy("jgId", jgId);//QueryCache.get(TXtJg.class, jgId);
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
	public   JSONObject getRootOrgan(String jgId) throws JSONException{//22
		TXtJg item = tXtJgManager.findUniqueBy("jgId", jgId);//QueryCache.get(TXtJg.class, jgId);
		JSONObject one = new JSONObject();
		if(item!=null){
			one.put("id", item.getJgId());
			one.put("name", item.getJgMc());
			one.put("pId", item.getSjjgId());
			one.put("isParent", true);
			one.put("open", true);
		}
		
		return one;
	}
	public   JSONArray getSubFunc(String sjgnDm,String appId) throws JSONException{
		if(StringHelper.isEmpty(sjgnDm))
			return null;
		List<TXtGnzy> lst = getFuncs(sjgnDm,appId);
		JSONArray jaTree = new JSONArray();
		JSONObject one;
		List ids;
		if(lst != null) 
			for(TXtGnzy item : lst) {
				one = new JSONObject();
				one.put("id", item.getGnDm());
				one.put("name", item.getGnMc());
				one.put("pId", item.getSjgnDm());
				ids = getFuncIds(item.getGnDm(),appId);
				if(ids == null || ids.size() < 1)
					one.put("isParent", false);
				else
					one.put("isParent", true);
				jaTree.add(one);
			}
		return jaTree;
	}
	
	
	public   JSONArray getSubRoleOrgan(String sjjgId, String jgId,String appId) throws JSONException{
		List<TXtJg> lst = getOrgans(sjjgId,appId);
		JSONArray jaTree = new JSONArray();
		JSONObject one;
		Object id;
		List ids;
		if(lst!=null&&lst.size()>0)
			for(TXtJg item : lst) {
				one = new JSONObject();
				one.put("id", item.getJgId());
				one.put("name", item.getJgMc());
				one.put("pId", item.getSjjgId());
				ids = getOrganIds(item.getJgId());
				if(ids == null || ids.size() < 1) 
					one.put("isParent", false);
				else
					one.put("isParent", true);
				if(jgId != null && jgId.equals(item.getJgId()) )
					one.put("checked", true);
				jaTree.add(one);
			}
		return jaTree;
	}
	public   JSONObject getRootRoleOrgan(String jgId, String iJgId) throws JSONException{//10
		TXtJg item = tXtJgManager.findUniqueBy("jgId", jgId);//QueryCache.get(TXtJg.class, jgId);
		JSONObject one = new JSONObject();
		if(item!=null){
			one.put("id", item.getJgId());
			one.put("name", item.getJgMc());
			one.put("pId", item.getSjjgId());
			one.put("isParent", true);
			if(iJgId != null && iJgId.equals(item.getJgId())){
				one.put("checked", true);
			}
			one.put("open", true);
		}
		return one;
	}
	public   JSONArray getSubUserOrgan(String sjjgId, String iJgId,String appId) throws JSONException{
//		List yhJgs = null;
//		if(StringHelper.isNotEmpty(yhId))
//			yhJgs = getGUserOrganIds(yhId);
		List<TXtJg> lst = getOrgans(sjjgId,appId);
		JSONArray jaTree = new JSONArray();
		JSONObject one;
		Object id;
		List ids;
		if(lst != null)
			for(TXtJg item : lst) {
				one = new JSONObject();
				one.put("id", item.getJgId());
				one.put("name", item.getJgMc());
				one.put("pId", item.getSjjgId());
				ids = getOrganIds(item.getJgId());
				if(ids == null || ids.size() < 1) 
					one.put("isParent", false);
				else
					one.put("isParent", true);
				if(iJgId != null && iJgId.equals(item.getJgId()))
					one.put("checked", true);
				jaTree.add(one);
			}
		return jaTree;
	}
	public   JSONObject getRootUserOrgan(String jgId, String iJgId) throws JSONException{//10
//		List yhJgs = null;
//		if(StringHelper.isNotEmpty(yhId))
//			yhJgs = getGUserOrganIds(yhId);
		TXtJg item =tXtJgManager.findUniqueBy("jgId", jgId);// QueryCache.get(TXtJg.class, jgId);
		JSONObject one = new JSONObject();
		if(item!=null){
			one.put("id", item.getJgId());
			one.put("name", item.getJgMc());
			one.put("pId", item.getSjjgId());
			one.put("isParent", true);
		}
		
		if(iJgId != null && iJgId.equals(item.getJgId())){
			one.put("checked", true);
		}
		one.put("open", true);
		return one;
	}
	
	
	
	
	
	public   JSONObject getRootGUserRole() throws JSONException{//10
		JSONObject one = new JSONObject();
		one.put("id", "0");
		one.put("name", "所有角色");
		one.put("pId", "00");
		one.put("isParent", true);
		one.put("nocheck", true);
		one.put("open", true);
		return one;
	}
	
	public   JSONArray getSubGUserOrgan(String sjjgId, String yhId) throws JSONException{
		List jgids = tXtJgManager.find("select a.jgId from TXtJgYh a where a.yhId=?", yhId);//new QueryCache("select a.jgId from TXtJgYh a where a.yhId=:yhId")
			//.setParameter("yhId", yhId).listCache();
		List ids = tXtJgManager.find("select a.jgId from TXtJg a where a.sjjgId=? and a.scBj=? order by a.xh", sjjgId, Constants.UN_DELETE_STATE);//new QueryCache("select a.jgId from TXtJg a where a.sjjgId=:sjjgId and a.scBj=:scBj order by a.xh")
			//.setParameter("sjjgId", sjjgId).setParameter("scBj", Constants.UN_DELETE_STATE).listCache();
		List<TXtJg> lst = tXtJgManager.findByIds(ids);//QueryCache.idToObj(TXtJg.class, ids);
		JSONArray jaTree = new JSONArray();
		JSONObject one;
		Object id=null;
		if(lst != null)
			for(TXtJg item : lst) {
				one = new JSONObject();
				one.put("id", item.getJgId());
				one.put("name", item.getJgMc());
				one.put("pId", item.getSjjgId());
				List list = tXtJgManager.find("select a.jgId from TXtJg a where a.sjjgId=:sjjgId and a.scBj=:scBj ", item.getJgId(),Constants.UN_DELETE_STATE);//new QueryCache("select a.jgId from TXtJg a where a.sjjgId=:sjjgId and a.scBj=:scBj ")
					//.setParameter("sjjgId", item.getJgId()).setParameter("scBj", Constants.UN_DELETE_STATE).setMaxResults(1).uniqueResultCache();
				if(list!=null&&list.size()>0){
					id = list.get(0);
				}
				if(id == null){//leaf
					one.put("nocheck", false);
					one.put("isParent", false);
					if(jgids.contains(item.getJgId())){
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
	public  JSONObject getRootGUserOrgan(String jgId) throws JSONException{//10
		TXtJg item = tXtJgManager.findUniqueBy("jgId", jgId);//QueryCache.get(TXtJg.class, jgId);
		JSONObject one = new JSONObject();
		one.put("id", item.getJgId());
		one.put("name", item.getJgMc());
		one.put("pId", item.getSjjgId());
		one.put("isParent", true);
		one.put("nocheck", true);
		one.put("open", true);
		return one;
	}
	public   List getOrgans(String sjjgId,String appId){
		List list = getOrganIdsQC(sjjgId,appId);
		List orglist = null;
		 if(list!=null&&list.size()>0)
			 orglist = tXtJgManager.findByIds(list);
		return orglist;//QueryCache.idToObj(TXtJg.class, qc.listCache());
	}
	
	private   List getUserIdsQC(String sjjgId) {
		return tXtJgManager.find("select a.yhId from TXtYh a ,TXtJgYh b where a.yhId=b.yhId and b.jgId=? and a.scBj=? and a.yxBj=? order by a.xh", sjjgId, Constants.UN_DELETE_STATE,Constants.STATUS_VALID);//new QueryCache("select a.yh_id from t_xt_yh a left join t_xt_jg_yh b on a.yh_id=b.yh_id where b.jg_id=:sjjgId and a.sc_bj=:scBj order by a.xh", true)
		//.setParameter("sjjgId", sjjgId).setParameter("scBj", Constants.UN_DELETE_STATE);
	}
	public   List getUsers(String sjjgId){
		List list = getUserIdsQC(sjjgId);
		List<TXtYh> yhs = null;
		if(list!=null&&list.size()>0)
			yhs = tXtYhManager.findByIds(TXtYh.class, list);
		return yhs;//QueryCache.idToObj(TXtYh.class, qc.listCache());
	}
	
	/*private   Query getFuncIdsQC(String sjgnDm,String appId) {
		return tXtJgManager.find("select a.gnDm from TXtGnzy a where a.sjgnDm=? and (appId=? or gnLx=2) order by a.gnXh", sjgnDm,appId);//new QueryCache("select a.gnDm from TXtGnzy a where a.sjgnDm=:sjgnDm order by a.gnXh")
		//.setParameter("sjgnDm", sjgnDm);
	}*/
	public   List getFuncIds(String sjgnDm,String appId){
		return tXtJgManager.find("select a.gnDm from TXtGnzy a where a.sjgnDm=? and (appId=? or gnLx=2) order by a.gnXh", sjgnDm,appId);//new QueryCache("select a.gnDm from TXtGnzy a where a.sjgnDm=:sjgnDm order by a.gnXh");
	}
	public   List getFuncs(String sjgnDm,String appId){
		List temp = getFuncIds(sjgnDm,appId);
		if(temp!=null&&temp.size()>0)
			return tXtJgManager.findByIdsOrder(TXtGnzy.class,temp,"gnXh",true);//QueryCache.idToObj(TXtGnzy.class, qc.listCache());
		else
			return null;
	}
	private   List getOrganIdsQC(String sjjgId,String appId) {
		return tXtJgManager.find("select a.jgId from TXtJg a where a.sjjgId=? and a.scBj=? and a.appId=? order by a.xh ", sjjgId,Constants.UN_DELETE_STATE,appId);//new QueryCache("select a.jgId from TXtJg a where a.sjjgId=:sjjgId and a.scBj=:scBj order by a.xh")
		//.setParameter("sjjgId", sjjgId).setParameter("scBj", Constants.UN_DELETE_STATE);
	}
	
	@Resource
	public void setTXtJgManager(TXtJgManager xtJgManager) {
		tXtJgManager = xtJgManager;
	}
	@Resource
	public void setTXtYhManager(TXtYhManager xtYhManager) {
		tXtYhManager = xtYhManager;
	}
	
	
	
}

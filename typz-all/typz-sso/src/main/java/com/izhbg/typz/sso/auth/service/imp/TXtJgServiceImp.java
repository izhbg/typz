package com.izhbg.typz.sso.auth.service.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.base.commondto.TreeObject;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.CommonUtil;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.sso.auth.dto.TXtJg;
import com.izhbg.typz.sso.auth.dto.TXtJgQuery;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.dto.TXtYhQuery;
import com.izhbg.typz.sso.auth.manager.TXtJgManager;
import com.izhbg.typz.sso.auth.service.ComparatorTXtJg;
import com.izhbg.typz.sso.auth.service.TXtJgService;
import com.izhbg.typz.sso.auth.service.TXtYhService;
/**
* @author caixl 
* @date 2016-5-20 上午10:19:58 
*
 */
@Service("tXtJgService")
@Transactional(rollbackFor=Exception.class)
public class TXtJgServiceImp implements TXtJgService{

	private TXtJgManager tXtJgManager;
	
	@Override
	public void add(TXtJg tXtJg) throws Exception {
		if(tXtJg==null)
			throw new ServiceException("参数为空，保存机构信息失败");
		tXtJgManager.save(tXtJg);
	}

	@Override
	public void deleteById(String jgId) throws Exception {
		if(StringHelper.isEmpty(jgId))
			throw new ServiceException("参数为空，删除机构信息失败");
		List<TXtJg> tXtJgs = tXtJgManager.findBy("sjjgId", jgId);
		if(tXtJgs!=null&&tXtJgs.size()>0)
			throw new ServiceException("存在子机构，请先删除子机构");
		TXtJg jg = tXtJgManager.findUniqueBy("jgId", jgId);
		jg.setScBj(1);
		tXtJgManager.update(jg);
	}
	public void deleteByIds(String[] jgIds) throws Exception {
		if(jgIds==null||jgIds.length==0)
			throw new ServiceException("参数为空,删除机构信息失败");
		for(String id:jgIds)
			this.deleteById(id);
	}
	@Override
	public void deleteBySjjgId(String sjjgId) throws Exception {
		if(StringHelper.isEmpty(sjjgId))
			throw new ServiceException("参数为空，删除结构信息失败");
		List<TXtJg> tXtJgs = tXtJgManager.findBy("sjjgId", sjjgId);
		if(tXtJgs!=null)
			for(TXtJg jg:tXtJgs){
				jg.setScBj(1);
				tXtJgManager.update(jg);
			}
		TXtJg jg = tXtJgManager.findUniqueBy("jgId", sjjgId);
		if(jg!=null){
			jg.setScBj(1);
			tXtJgManager.update(jg);
		}
	}

	@Override
	public void update(TXtJg tXtJg) throws Exception {
		if(tXtJg==null)
			throw new ServiceException("参数为空更新机构信息失败");
		tXtJgManager.update(tXtJg);
		
	}
	@Override
	public void updateStatus(String[] jgIds) throws Exception {
		List<String> lst = new ArrayList<String> ();
		for(String s : jgIds) 
			lst.add(s);
		List<TXtJg> itemLst = (List<TXtJg>)tXtJgManager.findByIds(lst); 
		
		for(TXtJg item : itemLst ){
			if(item.getYxBj()!=null&&item.getYxBj()==2){
				item.setYxBj(1);
			}else{
				item.setYxBj(2);
			}
			tXtJgManager.update(item);
		}
		
	}
	@Override
	public List getOrganIds(String sjjgId) throws Exception {
		if(StringHelper.isEmpty(sjjgId))
			throw new ServiceException("参数为空,获取子机构信息失败");
		return tXtJgManager.find("select a.jgId from TXtJg a where a.sjjgId=? and a.scBj=? order by a.xh", sjjgId, Constants.UN_DELETE_STATE);
	}

	@Override
	public String getJgsJSON(String currentAppId) throws Exception {
		if(StringHelper.isEmpty(currentAppId))
			throw new ServiceException("参数为空,获取组织机构树机构失败");
		List<TXtJg> tXtJgs = tXtJgManager.findBy("appId", currentAppId);
		List<TreeObject> trList = new ArrayList<TreeObject>();
		TreeObject to = null;
		if(trList!=null)
		for(TXtJg jg:tXtJgs){
			if(StringHelper.isEmpty(jg.getSjjgId()))
				jg.setSjjgId("-1");
			to = new TreeObject();
			to.setId(jg.getJgId());
			to.setName(jg.getJgMc());
			to.setPid(jg.getSjjgId());
			trList.add(to);
		}
		return CommonUtil.getTreeJson(trList);
	}

	@Override
	public String getSubOrgan(String sjjgId,String appId) throws Exception {
		if(StringHelper.isEmpty(sjjgId)||StringHelper.isEmpty(appId))
			throw new ServiceException("参数为空,获取组织机构树机构失败");
		List<TXtJg> tXtJgs = tXtJgManager.find(" from TXtJg where sjjgId=? and appId=?", sjjgId,appId);
		List<TreeObject> trList = new ArrayList<TreeObject>();
		TreeObject to = null;
		if(trList!=null)
		for(TXtJg jg:tXtJgs){
			to = new TreeObject();
			to.setId(jg.getJgId());
			to.setName(jg.getJgMc());
			to.setPid(jg.getSjjgId());
			trList.add(to);
		}
		return CommonUtil.getTreeJson(trList);
	}

	@Override
	public JSONArray getSubUserOrgan(String sjjgId, String iJgId, String appId)
			throws Exception {
		List<TXtJg> lst = tXtJgManager.find(" from TXtJg where sjjgId=? and scBj=2 and appId=? order by xh", sjjgId,appId);
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
	@Override
	public   JSONArray getSubRoleOrgan(String sjjgId, String jgId,String appId) throws Exception{
		List<TXtJg> lst = tXtJgManager.find(" from TXtJg where sjjgId=? and scBj=2 and appId=? order by xh", sjjgId,appId);
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
	@Override
	public Page queryPageList(Page page, TXtJgQuery tXtJgQuery)
			throws Exception {
		StringBuffer sb = new StringBuffer("select a.jgId from TXtJg a  ");
		sb.append(getWhere(tXtJgQuery))
		  .append(getOrder(page));
		
		page = tXtJgManager.pagedQuery(sb.toString(), page.getPageNo(), page.getPageSize(), setWhere(tXtJgQuery));
		List<String> list = (List)page.getResult();
		List listYh=null;
		try {
			if(list!=null&&list.size()>0)
				listYh = tXtJgManager.findByIds(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 if(listYh!=null){
			 ComparatorTXtJg comparator=new ComparatorTXtJg();
			 Collections.sort(listYh, comparator); 
		 }
		page.setResult(listYh);
		return page;
	}
	
	public String getOrder(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? " order by "+page.getOrderBy() +" "+ page.getOrder() : " order by a.xh ";
	}
	public String getOrderColumn(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? ", " + page.getOrderBy() : ", a.xh ";
	}
	public String getWhere(TXtJgQuery tXtJgQuery) {
		StringBuffer sb = new StringBuffer("where a.scBj=" + Constants.UN_DELETE_STATE + " ");
		if(StringHelper.isNotEmpty(tXtJgQuery.getJgId()))
			sb.append(" and a.jgId like :jgId ");
		if(StringHelper.isNotEmpty(tXtJgQuery.getJgDm()))
			sb.append(" and a.jgDm like :jgDm ");
		if(StringHelper.isNotEmpty(tXtJgQuery.getJgMc()))
			sb.append(" and a.jgMc like :jgMc ");
		if(StringHelper.isNotEmpty(tXtJgQuery.getSjjgId()))
		{
			sb.append(" and a.sjjgId = :sjjgId ");
		}else{
			sb.append(" and a.sjjgId = 'false' ");
		}
		if(StringHelper.isNotEmpty(tXtJgQuery.getYxBj()))
			sb.append(" and a.yxBj = :yxBj ");
		if(StringHelper.isNotEmpty(tXtJgQuery.getCurrentAppId())){
			sb.append(" and a.appId= :appId");
		}
		return sb.toString();
	}
	
	public Map<String, Object> setWhere(TXtJgQuery tXtJgQuery) {
		 Map<String, Object> params = new HashMap<String, Object>();
		 if(StringHelper.isNotEmpty(tXtJgQuery.getJgId()))
			 params.put("jgId","%" + tXtJgQuery.getJgId().trim() + "%");
		if(StringHelper.isNotEmpty(tXtJgQuery.getJgDm()))
			params.put("jgDm","%" + tXtJgQuery.getJgDm().trim() + "%");
		if(StringHelper.isNotEmpty(tXtJgQuery.getJgMc()))
			params.put("jgMc","%" + tXtJgQuery.getJgMc().trim() + "%");
		if(StringHelper.isNotEmpty(tXtJgQuery.getSjjgId()))
			params.put("sjjgId",tXtJgQuery.getSjjgId().trim());
		if(StringHelper.isNotEmpty(tXtJgQuery.getYxBj()))
			params.put("yxBj",Integer.parseInt(tXtJgQuery.getYxBj().trim()));
		if(StringHelper.isNotEmpty(tXtJgQuery.getCurrentAppId()))
			params.put("appId",tXtJgQuery.getCurrentAppId());
		return params;
	}
	@Override
	public TXtJg queryById(String jgId) throws Exception {
		if(StringHelper.isEmpty(jgId))
			throw new ServiceException("参数为为空,获取机构信息失败");
		return tXtJgManager.findUniqueBy("jgId", jgId);
	}
	@Override
	public TXtJg queryByJgDm(String jgDm) throws Exception {
		if(StringHelper.isEmpty(jgDm))
			throw new ServiceException("参数为为空,获取机构信息失败");
		return tXtJgManager.findUniqueBy("jgDm", jgDm);
	}
	@Resource
	public void settXtJgManager(TXtJgManager tXtJgManager) {
		this.tXtJgManager = tXtJgManager;
	}

	

	

	

	

	
	
}

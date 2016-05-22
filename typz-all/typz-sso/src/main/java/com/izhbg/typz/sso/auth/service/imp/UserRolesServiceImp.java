package com.izhbg.typz.sso.auth.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.sso.auth.dto.TXtGnjs;
import com.izhbg.typz.sso.auth.dto.TXtYhGnjs;
import com.izhbg.typz.sso.auth.dto.UserRoleQuery;
import com.izhbg.typz.sso.auth.manager.TXtGnjsManager;
import com.izhbg.typz.sso.auth.manager.TXtYhGnjsManager;
import com.izhbg.typz.sso.auth.service.UserRolesService;
@Service("userRolesService")
@Transactional(rollbackFor=Exception.class)
public class UserRolesServiceImp implements UserRolesService {

    private TXtYhGnjsManager tXtYhGnjsManager;
    private TXtGnjsManager tXtGnjsManager;
    @Override
    public Page queryPageList(Page page, UserRoleQuery userRoleQuery)
	    throws Exception {
	StringBuffer sb = new StringBuffer("select a.uuid from TXtYhGnjs a ,TXtGnjs b where a.jsDm=b.gnjsDm  ");
	sb.append(getWhere(userRoleQuery));
	sb.append(getOrder(page));
	page = tXtYhGnjsManager.pagedQuery(sb.toString(), page.getPageNo(), page.getPageSize(), setWhere(userRoleQuery));
	List<String> list = (List)page.getResult();
	List<TXtYhGnjs> listYh=null;
	if(list!=null&&list.size()>0)
		listYh = tXtYhGnjsManager.findByIds(list);
	if(listYh!=null)
	for(TXtYhGnjs o : listYh) {
		TXtGnjs gnjs = tXtGnjsManager.findUniqueBy("gnjsDm", o.getJsDm());//QueryCache.get(TXtGnzy.class, o.getGnzyDm()); 
		o.setGnjs(gnjs);
	}
	page.setResult(listYh);
	return page;
    }

    @Override
    public void add(String yhId, String[] checkdel) throws Exception {
	TXtYhGnjs item;
	for(String id : checkdel){
		List uid = tXtYhGnjsManager.find("select a.uuid from TXtYhGnjs a where a.jsDm=? and a.yhId=?", id,yhId);//new QueryCache()
		if(uid == null||uid.size()<1) {
			item = new TXtYhGnjs();
			item.setJsDm(id);
			item.setYhId(yhId);
			item.setUuid(com.izhbg.typz.base.util.IdGenerator.getInstance().getUniqTime()+"");
			tXtYhGnjsManager.save(item);
		}
	}
	
    }

    @Override
    public void deleteByIds(String[] checkdel) throws Exception {
	List lst = new ArrayList();
	for(String s : checkdel) 
		lst.add(s);
	List<TXtYhGnjs> items = tXtYhGnjsManager.findByIds(lst);//QueryCache.idToObj(TXtYhGnjs.class, lst);
	
	if(items != null)
		for(TXtYhGnjs o : items) {
		    tXtYhGnjsManager.remove(o);
		}
	
    }
    public String getOrder(Page page) {
	return StringHelper.isNotEmpty(page.getOrderBy()) ? " order by "+page.getOrderBy() +" "+ page.getOrder() : " order by a.uuid ";
    }
    public String getWhere(UserRoleQuery userRoleQuery) {
    	StringBuffer sb = new StringBuffer(" ");
    	if(StringHelper.isNotEmpty(userRoleQuery.getYhId()))
    		sb.append(" and a.yhId = :yhId ");
    	if(StringHelper.isNotEmpty(userRoleQuery.getCode()))
    		sb.append(" and b.code like :code ");
    	if(StringHelper.isNotEmpty(userRoleQuery.getGnjsMc()))
    		sb.append(" and b.gnjsMc like :gnjsMc ");
    	return sb.toString();
    }
    
    public Map<String, Object> setWhere(UserRoleQuery userRoleQuery) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	if(StringHelper.isNotEmpty(userRoleQuery.getYhId())) 
    		params.put("yhId", userRoleQuery.getYhId().trim());
    	if(StringHelper.isNotEmpty(userRoleQuery.getCode()))
    		params.put("code", "%" + userRoleQuery.getCode().trim() + "%");
    	if(StringHelper.isNotEmpty(userRoleQuery.getGnjsMc()))
    		params.put("gnjsMc", "%" + userRoleQuery.getGnjsMc().trim() + "%");
    	
    	return params;
    		
    }
    @Resource
    public void settXtYhGnjsManager(TXtYhGnjsManager tXtYhGnjsManager) {
        this.tXtYhGnjsManager = tXtYhGnjsManager;
    }
    @Resource
    public void settXtGnjsManager(TXtGnjsManager tXtGnjsManager) {
        this.tXtGnjsManager = tXtGnjsManager;
    }

    @Override
    public String getJsJson(String yhId, String appId) throws Exception {
	List jsids = tXtGnjsManager.find("select a.jsDm from TXtYhGnjs a where a.yhId=?", yhId);
        List ids = tXtGnjsManager.find("select a.gnjsDm from TXtGnjs a where (a.appId=? or a.jsLx=2) and a.yxBj=1", appId);
        List<TXtGnjs> lst = null;
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
        JSONObject jo = this.getRootGUserRole();
        jaTree.add(jo);
        return jaTree.toString();
    }
    private JSONObject getRootGUserRole() throws Exception{
	JSONObject one = new JSONObject();
	one.put("id", "0");
	one.put("name", "所有角色");
	one.put("pId", "00");
	one.put("isParent", true);
	one.put("nocheck", true);
	one.put("open", true);
	return one;
}
}

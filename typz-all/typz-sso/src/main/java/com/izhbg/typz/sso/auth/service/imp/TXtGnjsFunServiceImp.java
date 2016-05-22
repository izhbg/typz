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
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.sso.auth.dto.RoleFunQuery;
import com.izhbg.typz.sso.auth.dto.TXtGnjsZy;
import com.izhbg.typz.sso.auth.dto.TXtGnzy;
import com.izhbg.typz.sso.auth.manager.TXtGnjsManager;
import com.izhbg.typz.sso.auth.manager.TXtGnjsZyManager;
import com.izhbg.typz.sso.auth.manager.TXtGnzyManager;
import com.izhbg.typz.sso.auth.service.TXtGnjsFunService;

@Service("tXtGnjsFunService")
@Transactional(rollbackFor=Exception.class)
public class TXtGnjsFunServiceImp implements TXtGnjsFunService{

    private TXtGnjsManager tXtGnjsManager;
    private TXtGnjsZyManager tXtGnjsZyManager;
    private TXtGnzyManager tXtGnzyManager;
    @Override
    public Page queryPageList(Page page, RoleFunQuery roleFunQuery)
	    throws Exception {
	StringBuffer sb = new StringBuffer("select a.uuid from TXtGnjsZy a , TXtGnzy b where a.gnzyDm=b.gnDm ");
	sb.append(getWhere(roleFunQuery));
	sb.append(getOrder(page));
	
	page = tXtGnjsManager.pagedQuery(sb.toString(), page.getPageNo(), page.getPageSize(), setWhere(roleFunQuery));
	
	List<String> list = (List)page.getResult();
	List<TXtGnjsZy> listYh=null;
	if(list!=null&&list.size()>0)
		listYh = tXtGnjsZyManager.findByIds(list);
	if(listYh!=null)
	for(TXtGnjsZy o : listYh) {
		TXtGnzy gnzy = tXtGnzyManager.findUniqueBy("gnDm", o.getGnzyDm());//QueryCache.get(TXtGnzy.class, o.getGnzyDm()); 
		o.setGnzy(gnzy);
	}
	page.setResult(listYh);
	return page;
    }

    @Override
    public void add(String jsDm, String[] checkdel, String[] isRead,
	    String[] isCreate, String[] isUpdate, String[] isDelete,
	    String[] isAll) throws Exception {
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
    }

    @Override
    public void deleteByIds(String[] checkdel) throws Exception {
	List lst = new ArrayList();
	for(String s : checkdel) 
		lst.add(s);
	List<TXtGnjsZy> items = tXtGnjsZyManager.findByIds(lst);//QueryCache.idToObj(TXtGnjs.class, lst);
	
	for(Object o : items)
		tXtGnjsZyManager.remove(o);
    }
    public String getOrder(Page page) {
	return StringHelper.isNotEmpty(page.getOrderBy()) ? " order by "+page.getOrderBy() +" "+ page.getOrder() : " order by a.uuid ";
    }
    public String getWhere(RoleFunQuery roleFunQuery) {
    	StringBuffer sb = new StringBuffer(" ");
    	if(StringHelper.isNotEmpty(roleFunQuery.getJsDm()))
    		sb.append(" and a.jsDm = :jsDm ");
    	if(StringHelper.isNotEmpty(roleFunQuery.getGnzyDm()))
    		sb.append(" and a.gnzyDm like :gnzyDm ");
    	if(StringHelper.isNotEmpty(roleFunQuery.getGnMc()))
    		sb.append(" and b.gnMc like :gnMc ");
    	return sb.toString();
    }
    
    public Map<String, Object> setWhere(RoleFunQuery roleFunQuery) {
    	Map<String, Object> params = new HashMap<String, Object>();
    	if(StringHelper.isNotEmpty(roleFunQuery.getJsDm())) 
    		params.put("jsDm", roleFunQuery.getJsDm().trim());
    	if(StringHelper.isNotEmpty(roleFunQuery.getGnzyDm()))
    		params.put("gnzyDm", "%" + roleFunQuery.getGnzyDm().trim() + "%");
    	if(StringHelper.isNotEmpty(roleFunQuery.getGnMc()))
    		params.put("gnMc", "%" + roleFunQuery.getGnMc().trim() + "%");
    	return params;
    		
    }

    @Override
    public void changState(String categery, String uuid) throws Exception {
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
	
    }

    @Override
    public JSONObject getRootRoleFunc(String gnDm) throws Exception {
	TXtGnzy item = tXtGnzyManager.findUniqueBy("gnDm", gnDm);
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

    @Override
    public JSONArray getSubRoleFunc(String sjgnDm, String jsDm, String appId)
	    throws Exception {
	List fids = tXtGnzyManager.find("select a.gnzyDm from TXtGnjsZy a where a.jsDm=?", jsDm);
        List ids = tXtGnzyManager.find("select a.gnDm from TXtGnzy a where a.sjgnDm=?  and a.yxBj=1 and (a.appId=? or a.gnLx=2 ) order by a.gnXh",sjgnDm,appId);
        List<TXtGnzy> lst = null;
        if(ids!=null&&ids.size()>0)
        	lst= tXtGnzyManager.findByIdsOrder(ids,"gnXh",true);
        
        JSONArray jaTree = new JSONArray();
        JSONObject one;
        Object id=null;
        if(lst!=null)
        	for(TXtGnzy item : lst) {
        		one = new JSONObject();
        		one.put("id", item.getGnDm());
        		one.put("name", item.getGnMc());
        		one.put("pId", item.getSjgnDm());
        		List list = tXtGnzyManager.find("select a.gnDm from TXtGnzy a where a.sjgnDm=? and (a.appId=? or a.gnLx=2 )", item.getGnDm(),appId);
        		if(list!=null&&list.size()>0){
        			id = list.get(0);
        		}
        		if(id == null){
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
    @Resource
    public void settXtGnjsManager(TXtGnjsManager tXtGnjsManager) {
        this.tXtGnjsManager = tXtGnjsManager;
    }
    @Resource
    public void settXtGnjsZyManager(TXtGnjsZyManager tXtGnjsZyManager) {
        this.tXtGnjsZyManager = tXtGnjsZyManager;
    }
    @Resource
    public void settXtGnzyManager(TXtGnzyManager tXtGnzyManager) {
        this.tXtGnzyManager = tXtGnzyManager;
    }
    
}

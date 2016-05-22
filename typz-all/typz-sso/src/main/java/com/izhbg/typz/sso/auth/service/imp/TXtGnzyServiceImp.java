package com.izhbg.typz.sso.auth.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.sso.auth.dto.TXtGnjsZy;
import com.izhbg.typz.sso.auth.dto.TXtGnzy;
import com.izhbg.typz.sso.auth.dto.TXtGnzyQuery;
import com.izhbg.typz.sso.auth.manager.TXtGnjsZyManager;
import com.izhbg.typz.sso.auth.manager.TXtGnzyManager;
import com.izhbg.typz.sso.auth.manager.TXtJgManager;
import com.izhbg.typz.sso.auth.service.TXtGnzyService;
/**
 * 功能节点 服务实现
 * @author CXL
 *
 */
@Service("tXtGnzyService")
@Transactional(rollbackFor=Exception.class)
public class TXtGnzyServiceImp implements TXtGnzyService {
    
    private TXtGnzyManager tXtGnzyManager;
    
    private BeanMapper beanMapper = new BeanMapper();
    
    private TXtJgManager tXtJgManager;
    
    private TXtGnjsZyManager tXtGnjsZyManager;
    
    @Override
    public Page queryPageList(Page page, TXtGnzyQuery tXtGnzyQuery)
	    throws Exception {
	StringBuffer sb = new StringBuffer("select a.gnDm from TXtGnzy a  ");
	sb.append(getWhere(tXtGnzyQuery))
	  .append(getOrder(page));
	page = tXtGnzyManager.pagedQuery(sb.toString(), page.getPageNo(), page.getPageSize(), setWhere(tXtGnzyQuery));
	List<String> list = (List)page.getResult();
	List<TXtGnzy> listYh=null;
	try {
		if(list!=null&&list.size()>0)
			listYh = tXtGnzyManager.findByIdsOrder(list,"gnXh",true);
	} catch (Exception e) {
		e.printStackTrace();
	}
	page.setResult(listYh);
	return page;
    }

    @Override
    public void add(TXtGnzy tXtGnzy) throws Exception {
	if(tXtGnzy==null)
	    throw new ServiceException("参数为空,保存功能节点失败");
	tXtGnzyManager.save(tXtGnzy);
    }

    @Override
    public void deleteById(String gnDm) throws Exception {
	if(StringHelper.isEmpty(gnDm))
	    throw new ServiceException("参数为空,删除功能节点失败");
	TXtGnzy gn  = tXtGnzyManager.findUniqueBy("gnDm", gnDm);
	if(gn!=null)
	    gn.setSqBj(1);
	tXtGnzyManager.update(gn);
	//gnjs 中移除
	List<TXtGnjsZy> tXtGnjsZies = tXtGnjsZyManager.findBy("gnzyDm", gnDm);
	if(tXtGnjsZies!=null)
	    for(TXtGnjsZy tXtGnjsZy:tXtGnjsZies)
		tXtGnjsZyManager.remove(tXtGnjsZy);
    }
    

    @Override
    public void deleteByIds(String[] gnDm) throws Exception {
	if(gnDm==null||gnDm.length==0)
	    throw new ServiceException("参数为空,批量删除功能节点失败");
	for(String gnId:gnDm)
	    this.deleteById(gnId);
    }

    @Override
    public void update(TXtGnzy tXtGnzy) throws Exception {
	if(tXtGnzy==null)
	    throw new ServiceException("参数为空,更新功能节点失败");
	TXtGnzy gn = tXtGnzyManager.findUniqueBy("gnDm", tXtGnzy.getGnDm());
	if(gn!=null) {
	    beanMapper.copy(tXtGnzy, gn);
	    tXtGnzyManager.update(gn);
	}
    }

    @Override
    public TXtGnzy queryById(String gnDm) throws Exception {
	if(StringHelper.isEmpty(gnDm))
	    throw new ServiceException("参数为空,获取功能节点失败");
	return tXtGnzyManager.findUniqueBy("gnDm", gnDm);
    }

    @Override
    public String getFunTreeJson(String appId) throws Exception {
	if(StringHelper.isEmpty(appId))
	    throw new ServiceException("参数为空,获取功能结构树失败");
	String result = null;
	String root = null;
	List<String> list2 = tXtGnzyManager.find("select a.gnDm from TXtGnzy a where a.sjgnDm='-1'");
	if(list2!=null&&list2.size()>0){
		root = list2.get(0);
	}
	if(StringHelper.isEmpty(root)) {
		TXtGnzy func = new TXtGnzy();
		func.setGnDm(IdGenerator.getInstance().getUniqTime()+"");
		func.setGnMc("系统功能");
		func.setSjgnDm("-1");
		func.setAppId(appId);
		tXtGnzyManager.save(func);
		root = func.getGnDm();
	}
	JSONObject jo = this.getRootFunc(root);
	JSONArray ja = this.getSubFunc(root,appId);
	if(ja != null){
		ja.add(jo);
		result = ja.toString();
	}
	if(StringHelper.isEmpty(result))
		result = "[]";

	return result;
    }
    private JSONObject getRootFunc(String gnDm) throws Exception{
	if(StringHelper.isEmpty(gnDm))
		return null;
	TXtGnzy item = tXtGnzyManager.findUniqueBy("gnDm", gnDm);
	JSONObject one = new JSONObject();
	one.put("id", item.getGnDm());
	one.put("name", item.getGnMc());
	one.put("pId", item.getSjgnDm());
	one.put("isParent", true);
	one.put("open", true);
	return one;
    }
    public JSONArray getSubFunc(String pId,String appId) {
	
	List<TXtGnzy> lst = tXtGnzyManager.find(" from TXtGnzy  where sjgnDm=? and appId=?",pId,appId);
	JSONArray jaTree = new JSONArray();
	JSONObject one;
	List ids;
	if(lst != null) 
	for(TXtGnzy item : lst) {
	    one = new JSONObject();
	    one.put("id", item.getGnDm());
	    one.put("name", item.getGnMc());
	    one.put("pId", item.getSjgnDm());
	    ids = this.getFuncIds(item.getGnDm(),appId);
	    if(ids == null || ids.size() < 1)
		one.put("isParent", false);
	    else
		one.put("isParent", true);
	    jaTree.add(one);
	}
	return jaTree;
    }
    
    private List getFuncIds(String sjgnDm,String appId){
	return tXtJgManager.find("select a.gnDm from TXtGnzy a where a.sjgnDm=? and (appId=? or gnLx=2) order by a.gnXh", sjgnDm,appId);
    }
    public String getOrder(Page page) {
	return StringHelper.isNotEmpty(page.getOrderBy()) ? " order by "+page.getOrderBy() +" "+ page.getOrder() : " order by a.gnXh asc";
    }
    public String getOrderColumn(Page page) {
    	return StringHelper.isNotEmpty(page.getOrderBy()) ? ", " + page.getOrderBy() : ", a.xgRq ";
    }
    public String getWhere(TXtGnzyQuery tXtGnzyQuery) {
    	StringBuffer sb = new StringBuffer(" where 1=1 ");
    	if(StringHelper.isNotEmpty(tXtGnzyQuery.getGnDm()))
    		sb.append(" and a.gnDm like :gnDm ");
    	if(StringHelper.isNotEmpty(tXtGnzyQuery.getGnMc()))
    		sb.append(" and a.gnMc like :gnMc ");
    	if(StringHelper.isNotEmpty(tXtGnzyQuery.getSjgnDm()))
    		sb.append(" and a.sjgnDm = :sjgnDm ");
    	if(StringHelper.isNotEmpty(tXtGnzyQuery.getYxBj()))
    		sb.append(" and a.yxBj = :yxBj ");
    	if(StringHelper.isNotEmpty(tXtGnzyQuery.getCurrentAppId()))
    		sb.append(" and (a.appId = :appId or a.gnLx=2) ");
    	
    	else
    		sb.append(" order by a.gnDm ");
    	return sb.toString();
    }

    public Map<String, Object> setWhere(TXtGnzyQuery tXtGnzyQuery) {
    	 Map<String, Object> params = new HashMap<String, Object>();
    	 if(StringHelper.isNotEmpty(tXtGnzyQuery.getGnDm()))
    		 params.put("gnDm", "%" + tXtGnzyQuery.getGnDm().trim() + "%");
    	if(StringHelper.isNotEmpty(tXtGnzyQuery.getGnMc()))
    		params.put("gnMc", "%" + tXtGnzyQuery.getGnMc().trim() + "%");
    	if(StringHelper.isNotEmpty(tXtGnzyQuery.getSjgnDm()))
    		params.put("sjgnDm", tXtGnzyQuery.getSjgnDm().trim());
    	if(StringHelper.isNotEmpty(tXtGnzyQuery.getCurrentAppId()))
    		params.put("appId", tXtGnzyQuery.getCurrentAppId());
    	if(StringHelper.isNotEmpty(tXtGnzyQuery.getYxBj()))
    		params.put("yxBj",Integer.parseInt(tXtGnzyQuery.getYxBj().trim()));
    	return params;
    }

    @Override
    public JSONArray getSubFuncCheck(String sjgnDm, String gnDm, String appId)
	    throws Exception {
	StringBuffer sb = new StringBuffer("select a.gnDm from TXtGnzy a where a.sjgnDm=? and (a.appId=? or a.gnLx=2 ) order by a.gnXh");
	List idss = tXtGnzyManager.find(sb.toString(), sjgnDm,appId);
	List<TXtGnzy> lst = null;
	if(idss!=null&&idss.size()>0)
		lst = tXtGnzyManager.findByIdsOrder(idss,"gnXh",true);
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
			ids = tXtGnzyManager.find("select a.gnDm from TXtGnzy a where a.sjgnDm=?", item.getGnDm());
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

    @Override
    public void updFunStatus(String[] gnDms) throws Exception {
	if(gnDms==null||gnDms.length==0)
	    throw new ServiceException("参数为空,更新状态失败");
	List lst = new ArrayList();
	for(String s : gnDms) 
		lst.add(s);
	List<TXtGnzy> itemLst = (List<TXtGnzy>) tXtGnzyManager.findByIds(lst); 
	for(TXtGnzy item : itemLst ){
		if(item.getYxBj()!=null&&item.getYxBj()==2){
			item.setYxBj(1);
		}else{
			item.setYxBj(2);
		}
		tXtGnzyManager.update(item);
	}
	
    }
    @Resource
    public void settXtGnzyManager(TXtGnzyManager tXtGnzyManager) {
        this.tXtGnzyManager = tXtGnzyManager;
    }
    @Resource
    public void settXtJgManager(TXtJgManager tXtJgManager) {
        this.tXtJgManager = tXtJgManager;
    }
    @Resource
    public void settXtGnjsZyManager(TXtGnjsZyManager tXtGnjsZyManager) {
        this.tXtGnjsZyManager = tXtGnjsZyManager;
    }

    @Override
    public String getRootGnjsDm(String appId) throws Exception {
	List<String> list2 = tXtGnzyManager.find("select a.gnDm from TXtGnzy a where a.appId=? and a.sjgnDm='-1'", appId);
	String root = "";
	if(list2!=null&&list2.size()>0){
		root = list2.get(0);
	}
	return root;
    }
    
    
}

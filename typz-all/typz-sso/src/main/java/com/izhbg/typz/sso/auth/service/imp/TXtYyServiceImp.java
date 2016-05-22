package com.izhbg.typz.sso.auth.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.sso.auth.dto.TXtYy;
import com.izhbg.typz.sso.auth.manager.TXtGnjsManager;
import com.izhbg.typz.sso.auth.manager.TXtYyManager;
import com.izhbg.typz.sso.auth.service.TXtYyService;
@Service("tXtYyService")
@Transactional(rollbackFor=Exception.class)
public class TXtYyServiceImp implements TXtYyService{

	private TXtYyManager tXtYyManager;
	
	private BeanMapper beanMapper = new BeanMapper();
	
	private TXtGnjsManager tXtGnjsManager;
	
	@Override
	public TXtYy getSystem(String appId) throws Exception {
		if(StringHelper.isEmpty(appId))
			throw new ServiceException("参数为空,获取应用信息失败");
		return tXtYyManager.findUniqueBy("yyId", appId);
	}

	@Override
	public List<TXtYy> queryAll() throws Exception {
		return tXtYyManager.getAll();
	}
	
	@Override
	public Page page(Page page, String code, String appName,
		String classification) {
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
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringHelper.isNotEmpty(code)) 
			map.put("code", "%" + code.trim() + "%");
		if(StringHelper.isNotEmpty(appName))
			map.put("appName", "%" + appName.trim() + "%");
		if(StringHelper.isNotEmpty(classification))
			map.put("classification", Integer.parseInt(classification));
		
		page = tXtYyManager.pagedQuery(sb.toString(), page.getPageNo(), page.getPageSize(), map);
		return page;
	}
	@Override
	public void add(TXtYy tXtYy) throws Exception {
	    if(tXtYy==null)
		throw new ServiceException("参数为空,添加应用失败");
	    tXtYyManager.save(tXtYy);
	}

	@Override
	public void update(TXtYy tXtYy) throws Exception {
	    if(tXtYy==null)
		throw new ServiceException("参数为空，更新应用信息失败");
	    TXtYy tart = tXtYyManager.findUniqueBy("yyId", tXtYy.getYyId());
	    beanMapper.copy(tXtYy, tart);
	    tXtYyManager.update(tart);
	}

	@Override
	public void deleteByIds(String[] ids) throws Exception {
	   if(ids==null||ids.length==0)
	       throw new ServiceException("参数为空,删除应用信息失败");
	   List temlist = null;
	   for(String id:ids) {
	        temlist = tXtGnjsManager.findBy("appId", id);
        	if(temlist != null && temlist.size() > 0) {
        	    throw new ServiceException("请先删除该系统对应的功能角色");
        	}
	       tXtYyManager.removeById(id);
	   }
	    
	}

	@Resource
	public void settXtYyManager(TXtYyManager tXtYyManager) {
		this.tXtYyManager = tXtYyManager;
	}

	@Override
	public TXtYy getByCode(String code) throws Exception {
	    if(StringHelper.isEmpty(code))
		throw new ServiceException("参数为空，获取应用信息失败");
	    return tXtYyManager.findUniqueBy("code", code);
	}

	@Override
	public void updateStatus(String[] ids, String defaultPas,String type)
		throws Exception {
	    	if(ids==null||ids.length==0||StringHelper.isEmpty(type))
	    	    throw new ServiceException("参数为空，更新状态失败");
	    	List lst = new ArrayList();
		for(String s : ids) 
			lst.add(s);
		List<TXtYy> itemLst =tXtYyManager.findByIds(lst); 
		for(TXtYy item : itemLst ){
			if("yxBj".equals(type)){
				if(item.getYxBj()!=null&&item.getYxBj()==2){
					item.setYxBj(1);
				}else{
					item.setYxBj(2);
				}
			}else if("password".equals(type)){
				item.setPassword(defaultPas);
			}else if("showFlag".equals(type)){
				if(item.getShowFlag()!=null&&item.getShowFlag().equals("2")){
					item.setShowFlag("1");
				}else{
					item.setShowFlag("2");
				}
			} else if("loginFlag".equals(type)){
				if(item.getLoginFlag()!=null&&item.getLoginFlag().equals("2")){
					item.setLoginFlag("1");
				}else{
					item.setLoginFlag("2");
				}
			} else if("loginDisplay".equals(type)){
				if(item.getLoginDisplay()!=null&&item.getLoginDisplay().equals("2")){
					item.setLoginDisplay("1");
				}else{
					item.setLoginDisplay("2");
				}
			 }
			
			tXtYyManager.update(item);
		}
	    
	}
	@Resource
	public void settXtGnjsManager(TXtGnjsManager tXtGnjsManager) {
	    this.tXtGnjsManager = tXtGnjsManager;
	}
	
	

	
	
}

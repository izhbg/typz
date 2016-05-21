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
import com.izhbg.typz.sso.auth.dto.TXtGnjs;
import com.izhbg.typz.sso.auth.dto.TXtGnjsQuery;
import com.izhbg.typz.sso.auth.dto.TXtYhGnjs;
import com.izhbg.typz.sso.auth.manager.TXtGnjsManager;
import com.izhbg.typz.sso.auth.manager.TXtYhGnjsManager;
import com.izhbg.typz.sso.auth.service.TXtGnjsService;
/**
 * 角色接口实现
* @author caixl 
* @date 2016-5-20 下午4:07:04 
*
 */
@Service("tXtGnjsService")
@Transactional(rollbackFor=Exception.class)
public class TXtGnjsServiceImp implements TXtGnjsService{

	private TXtGnjsManager tXtGnjsManager;
	
	private TXtYhGnjsManager tXtYhGnjsManager;
	
	private BeanMapper beanMapper = new BeanMapper();
	
	@Override
	public void add(TXtGnjs tXtGnjs) throws Exception {
		if(tXtGnjs==null)
			throw new ServiceException("参数为空,添加角色失败");
		tXtGnjsManager.save(tXtGnjs);
	}

	@Override
	public void deleteById(String jsId) throws Exception {
		if(StringHelper.isEmpty(jsId))
			throw new ServiceException("参数为空,删除角色失败");
		List<TXtYhGnjs> tXtYhGnjs = tXtYhGnjsManager.findBy("jsDm", jsId);
		if(tXtYhGnjs!=null)
			for(TXtYhGnjs tXtYhGnjs2:tXtYhGnjs){
				tXtYhGnjsManager.remove(tXtYhGnjs2);
			}
		TXtGnjs tXtGnjs = tXtGnjsManager.findUniqueBy("gnjsDm", jsId);
		if(tXtGnjs!=null)
			tXtGnjsManager.remove(tXtGnjs);
	}

	@Override
	public void deleteByIds(String[] jsIds) throws Exception {
		if(jsIds==null||jsIds.length==0)
			throw new ServiceException("参数为空,删除角色失败");
		for(String id:jsIds){
			this.deleteById(id);
		}
	}

	@Override
	public void update(TXtGnjs tXtGnjs) throws Exception {
		if(tXtGnjs==null||StringHelper.isEmpty(tXtGnjs.getGnjsDm()))
			throw new ServiceException("参数为空，更新角色失败");
		TXtGnjs js = this.queryById(tXtGnjs.getGnjsDm());
		beanMapper.copy(tXtGnjs, js);
		tXtGnjsManager.update(js);
	}

	@Override
	public Page qeuryPageList(TXtGnjsQuery tXtGnjsQuery,Page page) throws Exception {
		
		StringBuffer str = new StringBuffer("select a.gnjsDm from TXtGnjs a ");
		
		str.append(getWhere(tXtGnjsQuery)).append(getOrder(page));
		
		page = tXtGnjsManager.pagedQuery(str.toString(), page.getPageNo(), page.getPageSize(), setWhere(tXtGnjsQuery));
		
		List<String> list = (List)page.getResult();
		List<TXtGnjs> listYh=null;
		if(list!=null&&list.size()>0)
			listYh = tXtGnjsManager.findByIds(list);
		page.setResult(listYh);
		
		return page;
	}

	@Override
	public List<TXtGnjs> queryByAppId(String appId) throws Exception {
		if(StringHelper.isEmpty(appId))
			throw new ServiceException("参数为空，获取角色列表失败");
		return tXtGnjsManager.findBy("appId", appId);
	}

	@Override
	public TXtGnjs queryById(String jsId) throws Exception {
		if(StringHelper.isEmpty(jsId))
			throw new ServiceException("参数为空，获取角色信息失败");
		return tXtGnjsManager.findUniqueBy("gnjsDm", jsId);
	}
	
	public String getOrder(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? " order by "+page.getOrderBy() +" "+ page.getOrder() : " order by a.gnjsDm  ";
	}
	public String getWhere(TXtGnjsQuery tXtGnjsQuery) {
		StringBuffer sb = new StringBuffer("where 1=1");
		if(StringHelper.isNotEmpty(tXtGnjsQuery.getCode()))
			sb.append(" and a.code like :code ");
		if(StringHelper.isNotEmpty(tXtGnjsQuery.getGnjsMc()))
			sb.append(" and a.gnjsMc like :gnjsMc ");
		if(StringHelper.isNotEmpty(tXtGnjsQuery.getYxBj()))
			sb.append(" and a.yxBj = :yxBj ");
		if(StringHelper.isNotEmpty(tXtGnjsQuery.getAppId()))
			sb.append(" and a.appId = :appId ");
		return sb.toString();
	}
	
	public Map<String, Object> setWhere(TXtGnjsQuery tXtGnjsQuery) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringHelper.isNotEmpty(tXtGnjsQuery.getCode())) 
			params.put("code", "%" + tXtGnjsQuery.getCode().trim() + "%");
		if(StringHelper.isNotEmpty(tXtGnjsQuery.getGnjsMc()))
			params.put("gnjsMc", "%" + tXtGnjsQuery.getGnjsMc().trim() + "%");
		if(StringHelper.isNotEmpty(tXtGnjsQuery.getYxBj()))
			params.put("yxBj", Integer.parseInt(tXtGnjsQuery.getYxBj()));
		if(StringHelper.isNotEmpty(tXtGnjsQuery.getAppId()))
			params.put("appId", tXtGnjsQuery.getAppId().trim());
		
		return params;
			
	}
	@Override
	public void updateStatus(String[] jsIds) throws Exception {
		if(jsIds==null||jsIds.length==0)
			throw new ServiceException("参数为空,更新角色状态失败");
		List<String> lst = new ArrayList<String>();
		for(String s : jsIds) 
			lst.add(s);
		List<TXtGnjs> itemLst = (List<TXtGnjs>)tXtGnjsManager.findByIds(lst);
		
		for(TXtGnjs item : itemLst ){
			if(item.getYxBj()!=null&&item.getYxBj()==2){
				item.setYxBj(1);
			}else{
				item.setYxBj(2);
			}
			tXtGnjsManager.update(item);
		}
	}
	
	@Resource
	public void settXtGnjsManager(TXtGnjsManager tXtGnjsManager) {
		this.tXtGnjsManager = tXtGnjsManager;
	}
	@Resource
	public void settXtYhGnjsManager(TXtYhGnjsManager tXtYhGnjsManager) {
		this.tXtYhGnjsManager = tXtYhGnjsManager;
	}

	
	
}

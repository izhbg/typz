package com.izhbg.typz.sso.auth.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.common.redis.RedisCache;
import com.izhbg.typz.base.common.redis.RedisEvict;
import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.sso.auth.dto.TXtJg;
import com.izhbg.typz.sso.auth.dto.TXtJgYh;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.dto.TXtYhGnjs;
import com.izhbg.typz.sso.auth.dto.TXtYhQuery;
import com.izhbg.typz.sso.auth.manager.TXtJgManager;
import com.izhbg.typz.sso.auth.manager.TXtJgYhManager;
import com.izhbg.typz.sso.auth.manager.TXtYhGnjsManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.auth.service.TXtJgService;
import com.izhbg.typz.sso.auth.service.TXtYhService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
/**
* @author caixl 
* @date 2016-5-20 上午8:49:48 
*
 */
@Transactional
@Service("tXtYhService")
public class TXtYhServiceImp implements TXtYhService{

	private TXtJgYhManager tXtJgYhManager;
	
	private TXtJgManager tXtJgManager;
	
	private TXtYhManager tXtYhManager;
	
	private BeanMapper beanMapper = new BeanMapper();
	
	private TXtJgService tXtJgService;
	
	private TXtYhGnjsManager tXtYhGnjsManager;
	
	
	
	@Override
	public TXtJg findJgByYhId(String yhId) throws Exception {
		if(StringHelper.isEmpty(yhId))
			throw new ServiceException("参数为空,根据用户获取结构失败");
		TXtJgYh tXtJgYh = tXtJgYhManager.findUniqueBy("yhId", yhId);
		if(tXtJgYh==null)
			throw new ServiceException("该用户没有分配归属机构，请联系管理员");
		return tXtJgManager.findUniqueBy("jgId", tXtJgYh.getJgId());
	}

	@Override
	public void add(TXtYh tXtYh) throws Exception {
		if(tXtYh==null)
			throw new ServiceException("参数为空，保存用户信息失败");
		tXtYhManager.save(tXtYh);
	}
	@Override
	public void add(TXtYh tXtYh, String[] jgIds) throws Exception {
		this.add(tXtYh);
		if(jgIds!=null)
			for(int i = 0; i < jgIds.length; i++){
				TXtJgYh jgyh = new TXtJgYh();
				jgyh.setJgId(jgIds[i]);
				jgyh.setYhId(tXtYh.getYhId());
				jgyh.setDuId(IdGenerator.getInstance().getUniqTime()+"");
				tXtJgYhManager.save(jgyh);
			}
	}
	@Override
	@RedisEvict(type=TXtYh.class,fieldKey="#yhId")
	public void delete(String yhId) throws Exception {
		if(StringHelper.isEmpty(yhId))
			throw new ServiceException("参数为空，删除用户信息失败");
		TXtYh yh = tXtYhManager.findUniqueBy("yhId", yhId);
		yh.setScBj(1);
		tXtYhManager.update(yh);
		List<TXtJgYh> objs1 = tXtJgYhManager.findBy("yhId", yhId);
		List<TXtYhGnjs> objs2 = tXtYhGnjsManager.findBy("yhId", yhId);
		if(objs1!=null&&objs1.size()>0)
			for(TXtJgYh tj:objs1)
				tXtJgYhManager.remove(tj);
		if(objs2!=null&&objs2.size()>0)
			for(TXtYhGnjs tyg:objs2)
				tXtYhGnjsManager.remove(tyg);
	}
	
	@Override
	@RedisEvict(type=TXtYh.class,fieldKey="#yhIds")
	public void deleteByIds(String[] yhIds) throws Exception {
		if(yhIds==null||yhIds.length<=0)
			throw new ServiceException("参数为空，删除用户信息失败");
		List<String> lst = new ArrayList<String>();
		for(String s : yhIds) 
			lst.add(s);
		List<TXtYh> itemLst = (List<TXtYh>)tXtYhManager.findByIds(lst);
		
		for(TXtYh item : itemLst ){
			item.setScBj(new Integer(Constants.DELETE_STATE));
			tXtYhManager.update(item);
		}
		List<TXtJgYh> objs1 = null;
		List<TXtYhGnjs> objs2 = null;
		for(String s : yhIds) {
			objs1 = tXtJgYhManager.findBy("yhId", s);
			objs2 = tXtYhGnjsManager.findBy("yhId", s);
			if(objs1!=null&&objs1.size()>0)
				for(TXtJgYh tj:objs1)
					tXtJgYhManager.remove(tj);
			if(objs2!=null&&objs2.size()>0)
				for(TXtYhGnjs tyg:objs2)
					tXtYhGnjsManager.remove(tyg);
		}
	}
	@Override
	public void resetPassword(String[] yhIds,String defaultPas) throws Exception {
		if(yhIds==null||yhIds.length<=0)
			throw new ServiceException("参数为空，重置密码失败");
		List<String> lst = new ArrayList<String>();
		for(String s : yhIds) 
			lst.add(s);
		List<TXtYh> itemLst = (List<TXtYh>)tXtYhManager.findByIds(lst);
		
		for(TXtYh item : itemLst ){
			item.setMm(defaultPas);
			tXtYhManager.update(item);
		}
	}
	@Override
	@RedisCache(type=TXtYh.class,fieldKey="#yhId")
	public TXtYh findByYhId(String yhId) throws Exception {
		if(StringHelper.isEmpty(yhId))
			throw new ServiceException("参数为空，获取用户信息失败");
		return tXtYhManager.findUniqueBy("yhId", yhId);
	}

	@Override
	public TXtYh findByYhDm(String yhDm) throws Exception {
		if(StringHelper.isEmpty(yhDm))
			throw new ServiceException("参数为空，获取用户信息失败");
		return tXtYhManager.findUniqueBy("yhDm", yhDm);
		
	}

	@Override
	public void update(TXtYh tXtYh) throws Exception {
		if(tXtYh==null)
			throw new ServiceException("参数为空,保存用户信息失败");
		TXtYh dest = this.findByYhId(tXtYh.getYhId());
		beanMapper.copy(tXtYh, dest);
		tXtYhManager.update(dest);
	}
	@Override
	public void update(TXtYh tXtYh, String jgId) throws Exception {
		this.update(tXtYh);
		List<TXtJgYh> objs = tXtJgYhManager.findBy("yhId", tXtYh.getYhId());
		Boolean save = true;
		if(objs != null) 
			for(TXtJgYh o : objs) {
				tXtYh.setJgId(o.getJgId());
				if(o.getJgId().equals(jgId))
					save = false;
				else
					tXtJgYhManager.remove(o);
			}
		if(save){
			TXtJgYh jgyh = new TXtJgYh();
			jgyh.setJgId(jgId);
			jgyh.setYhId(tXtYh.getYhId());
			jgyh.setDuId(IdGenerator.getInstance().getUniqTime()+"");
			tXtJgYhManager.save(jgyh);
		}
	}
	@Override
	public Page queryYhPageList(Page page,TXtYhQuery tXtYhQuery)
			throws Exception {
		//组织管理 当前登陆用户 所在的应用  切为根节点的 项
		TXtYh currentYh = tXtYhManager.findUniqueBy("yhId", SpringSecurityUtils.getCurrentUserId());
		if(currentYh==null)
			throw new ServiceException("非法用户，请重新操作");
		
		StringBuffer sb = new StringBuffer("select DISTINCT a.yhId  ");
		sb.append(" from TXtYh a ,TXtJgYh b where a.yhId=b.yhId and a.yhId!='is8v1wz7g0eyl86iz7g1'")
		  .append(getWhere(tXtYhQuery))
		  .append(getOrder(page));
		
		page = tXtYhManager.pagedQuery(sb.toString(), page.getPageNo(), page.getPageSize(), setWhere(tXtYhQuery));
		List<String> list = (List<String>) page.getResult();
		List<TXtYh> listYh=null;
		if(list!=null&&list.size()>0)
			listYh = tXtYhManager.findByIds(list);
		page.setResult(listYh);
		return page;
	}
	
	public String getOrder(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? "order by "+page.getOrderBy() +" "+ page.getOrder() : " order by a.xgRq desc ";
	}
	public String getOrderColumn(Page page) {
		return StringHelper.isNotEmpty(page.getOrderBy()) ? ", " + page.getOrderBy() : ", a.xgRq ";
	}
	/**
	 * 组装查询sql
	 * @param tXtYhQuery
	 * @return
	 */
	public String getWhere(TXtYhQuery tXtYhQuery)throws Exception {
		StringBuffer sb = new StringBuffer(" and a.scBj=" + Constants.UN_DELETE_STATE + " ");
		if(StringHelper.isNotEmpty(tXtYhQuery.getSjjgId())) {//神奇呀。
			if(StringHelper.isNotEmpty(tXtYhQuery.getIncludeFlag())) {
				List<String> organs = new ArrayList<String>();
				organs = getOrgan(tXtYhQuery.getSjjgId(),organs);
				StringBuilder sbd = new StringBuilder();
				for(String str : organs) {
					sbd.append("'").append(str).append("',");
				}
				sbd.deleteCharAt(sbd.length()-1);
				sb.append(" and b.jgId in("+sbd.toString()+") ");
			}
			else
				sb.append(" and b.jgId ='" + tXtYhQuery.getSjjgId() + "' ");
		}else{
			sb.append(" and b.jgId ='false' ");
		}	 
		if(StringHelper.isNotEmpty(tXtYhQuery.getYhId()))
			sb.append(" and a.yhId like :yhId ");
		if(StringHelper.isNotEmpty(tXtYhQuery.getYhDm()))
			sb.append(" and a.yhDm like :yhDm ");
		if(StringHelper.isNotEmpty(tXtYhQuery.getYhMc()))
			sb.append(" and a.yhMc like :yhMc ");
		if(StringHelper.isNotEmpty(tXtYhQuery.getYxBj()))
			sb.append(" and a.yxBj = :yxBj ");
		if(StringHelper.isNotEmpty(tXtYhQuery.getScBj()))
			sb.append(" and a.scBj = :scBj ");
		if(StringHelper.isNotEmpty(tXtYhQuery.getCurrentAppId()))
			sb.append(" and a.appId = :appId ");
		return sb.toString();
	}
	/**
	 * 查询条件 赋值
	 * @param tXtYhQuery
	 * @return
	 */
	public Map<String, Object> setWhere(TXtYhQuery tXtYhQuery) {
		 Map<String, Object> params = new HashMap<String, Object>();
		if(StringHelper.isNotEmpty(tXtYhQuery.getYhId())) 
			params.put("yhId","%" + tXtYhQuery.getYhId().trim() + "%");
		if(StringHelper.isNotEmpty(tXtYhQuery.getYhDm())) 
			params.put("yhDm","%" + tXtYhQuery.getYhDm().trim() + "%");
		if(StringHelper.isNotEmpty(tXtYhQuery.getYhMc()))
			params.put("yhMc","%" + tXtYhQuery.getYhMc().trim() + "%");
		if(StringHelper.isNotEmpty(tXtYhQuery.getYxBj()))
			params.put("yxBj",Integer.parseInt(tXtYhQuery.getYxBj().trim()));
		if(StringHelper.isNotEmpty(tXtYhQuery.getScBj()))
			params.put("scBj",tXtYhQuery.getScBj().trim());
		if(StringHelper.isNotEmpty(tXtYhQuery.getCurrentAppId()))
			params.put("appId",tXtYhQuery.getCurrentAppId());
		
		return params;
			
	}
	/**
	 * 组装服务
	 * @param organId
	 * @param organs
	 * @return
	 */
	public List<String> getOrgan(String organId,List<String> organs) throws Exception{
		organs.add(organId);
		List<String> temp = tXtJgService.getOrganIds(organId);
		for(String s : temp)
			organs=getOrgan(s,organs);
		return organs;
	}
	@Override
	public void removeYhFromJg(String[] yhIds, String jgId) throws Exception {
		List<String> lst = new ArrayList<String>();
		String temp_sql =  "select duId from TXtJgYh where yhId=? and jgId=? ";
		Object obj = null;
		for(String s : yhIds) 
		{
			obj = tXtJgYhManager.findUnique(temp_sql, s,jgId);
			if(obj!=null){
				lst.add(obj.toString());
			}
		}
		List<TXtJgYh> txtjgyh = null;
		if(lst.size()>0)
			txtjgyh = tXtJgYhManager.findByIds(lst);
		if(txtjgyh!=null&&txtjgyh.size()>0)
		{
			for(TXtJgYh tx:txtjgyh){
				tXtJgYhManager.remove(tx);
			}
		}
		
	}
	@Override
	public void updGUserStatus(String[] yhIds) throws Exception {
		List<String> lst = new ArrayList<String>();
		for(String s : yhIds) 
			lst.add(s);
		List<TXtYh> itemLst = (List<TXtYh>)tXtYhManager.findByIds(lst);
		
		for(TXtYh item : itemLst ){
			if(item.getYxBj()!=null&&item.getYxBj()==2){
				item.setYxBj(1);
			}else{
				item.setYxBj(2);
			}
			tXtYhManager.update(item);
		}
	}
	@Resource
	public void settXtJgYhManager(TXtJgYhManager tXtJgYhManager) {
		this.tXtJgYhManager = tXtJgYhManager;
	}
	
	@Resource
	public void settXtJgManager(TXtJgManager tXtJgManager) {
		this.tXtJgManager = tXtJgManager;
	}
	@Resource
	public void settXtYhManager(TXtYhManager tXtYhManager) {
		this.tXtYhManager = tXtYhManager;
	}
	@Resource
	public void settXtJgService(TXtJgService tXtJgService) {
		this.tXtJgService = tXtJgService;
	}
	@Resource
	public void settXtYhGnjsManager(TXtYhGnjsManager tXtYhGnjsManager) {
		this.tXtYhGnjsManager = tXtYhGnjsManager;
	}

	

	

	

	

	

	


}

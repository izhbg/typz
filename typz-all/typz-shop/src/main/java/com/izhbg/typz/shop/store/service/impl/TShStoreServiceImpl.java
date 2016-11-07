package com.izhbg.typz.shop.store.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.shop.member.dto.TShMember;
import com.izhbg.typz.shop.member.manager.TShMemberManager;
import com.izhbg.typz.shop.store.dto.TShStore;
import com.izhbg.typz.shop.store.manager.TShStoreManager;
import com.izhbg.typz.shop.store.service.TShStoreService;
import com.izhbg.typz.sso.auth.dto.TXtGnjs;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.dto.TXtYhGnjs;
import com.izhbg.typz.sso.auth.manager.TXtYhGnjsManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.util.CustomPasswordEncoder;
import com.izhbg.typz.sso.util.SimplePasswordEncoder;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
/**
 * 店铺相关服务
* @author xiaolong.cai@mtime.com
* @date 2016年9月25日 上午11:10:58 
* @version V1.0
 */
@Service("tShStoreService")
public class TShStoreServiceImpl implements TShStoreService{

	@Autowired
	private TShStoreManager tShStoremanager;
	@Autowired
	private TShMemberManager tShMemberManager;
	@Autowired
	private TXtYhManager tXtYhManager;
	@Autowired
	private TXtYhGnjsManager tXGnjsManager;
	@Autowired
	private CustomPasswordEncoder customPasswordEncoder;
	
	private BeanMapper beanMapper = new BeanMapper();
	/**
	 * 添加店铺信息（申请入驻）
	 */
	@Override
	public void add(TShStore entity) throws Exception {
		if(entity==null||StringHelper.isEmpty(entity.getPhone()))
			throw new ServiceException("参数为空，保存店铺信息失败");
		TShMember tShMember = initMember(entity);
		entity.setYhId(tShMember.getId());
		entity.setScBj(Constants.UN_DELETE_STATE);
		entity.setState(Constants.VERIFY_WAIT);
		tShStoremanager.save(entity);
	}
	/**
	 * member
	 * @param entity
	 */
	private TShMember initMember(TShStore entity){
		//memeber
		TShMember tShMember = tShMemberManager.findUniqueBy("phone", entity.getPhone());
		if(tShMember==null){
			tShMember = new TShMember();
			tShMember.setId(IdGenerator.getInstance().getUniqTime()+"");
			tShMember.setTime(new Date());
			tShMember.setLa(entity.getLa());
			tShMember.setLo(entity.getLo());
			tShMember.setType(entity.getState());
			tShMember.setPhone(entity.getPhone());
			tShMemberManager.save(tShMember);
		}else{
			tShMember.setLa(entity.getLa());
			tShMember.setLo(entity.getLo());
			tShMember.setType(entity.getState());
			tShMemberManager.update(tShMember);
		}
		//admin xk 
		if(Constants.STORE_CHANGJIA .equals(entity.getType())){
			TXtYh tXtYh = tXtYhManager.findUniqueBy("yhDm", entity.getPhone());
			if(tXtYh==null){
				tXtYh = new TXtYh();
				tXtYh.setYhId(IdGenerator.getInstance().getUniqTime()+"");
				tXtYh.setYhDm(entity.getPhone());
				tXtYh.setYhMc(entity.getPhone());
				tXtYh.setAppId(SpringSecurityUtils.getCurrentUserAppId());
				tXtYh.setMm(customPasswordEncoder.encode("123456"));
				tXtYh.setYxBj(1);
				tXtYh.setScBj(2);
				tXtYhManager.save(tXtYh);
			}
			TXtYhGnjs tXtYhGnjs = tXGnjsManager.findUniqueBy("yhId", tXtYh.getYhId());
			if(tXtYhGnjs==null){
				tXtYhGnjs = new TXtYhGnjs();
				tXtYhGnjs.setUuid(IdGenerator.getInstance().getUniqTime()+"");
				tXtYhGnjs.setYhId(tXtYh.getYhId());
				tXtYhGnjs.setJsDm("3");
				tXGnjsManager.save(tXtYhGnjs);
			}
		}
		return tShMember;
	}
	/**
	 * 更新店铺信息 更新后 店铺状态置为待审核
	 */
	@Override
	public void update(TShStore entity) throws Exception {
		if(entity==null||entity.getId()==null)
			throw new ServiceException("参数为空，更新店铺信息失败");
		TShStore tShStore = tShStoremanager.findUniqueBy("id", entity.getId());
		
		beanMapper.copy(entity,tShStore );
		tShStore.setState(Constants.VERIFY_WAIT);
		tShStoremanager.update(tShStore);
	}
	/**
	 * 删除店铺 逻辑删除
	 */
	@Override
	public void delete(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空，删除店铺信息失败");
		TShStore tShStore = tShStoremanager.findUniqueBy("id", id);
		tShStore.setScBj(Constants.DELETE_STATE);
		tShStoremanager.update(tShStore);
	}
	/**
	 * 批量删除 平台限管理员操作
	 */
	@Override
	public void deleteBatche(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空,批量删除店铺信息失败");
		for(String id:ids)
			delete(id);
	}
	/**
	 * 根据ID获取店铺信息
	 */
	@Override
	public TShStore getById(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空，获取店铺信息失败");
		TShStore tShStore = tShStoremanager.findUniqueBy("id", id);
		return tShStore;
	}
	/**
	 * 分页操作
	 */
	@Override
	public Page pageList(Page page) throws Exception {
		Map map = new HashMap<String, Object>();
		return tShStoremanager.pagedQuery(" from TShStore", page.getPageNo(), page.getPageSize(), map);
	}
	/**
	 * 获取所有店铺信息
	 */
	@Override
	public List<TShStore> getAll() throws Exception {
		return tShStoremanager.getAll();
	}
	/**
	 * 审核未通过，需有审核意见
	 */
	@Override
	public void verifyPassError(String[] checkdel) throws Exception {
		if(checkdel==null||checkdel.length<=0)
			throw new ServiceException("参数为空，审核失败，");
		TShStore tsShStore_ = null;
		for(String id:checkdel){
			tsShStore_ = tShStoremanager.findUniqueBy("id", id);
			tsShStore_.setState(Constants.VERIFY_ERRORPASS);
			tShStoremanager.update(tsShStore_);
		}
		
	}
	/**
	 * 审核通过
	 */
	@Override
	public void verifyPassOn(String[] checkdel) throws Exception {
		if(checkdel==null||checkdel.length<=0)
			throw new ServiceException("参数为空，审核失败，");
		TShStore tsShStore_ =  null;
		for(String id:checkdel){
			tsShStore_ = tShStoremanager.findUniqueBy("id", id);
			tsShStore_.setState(Constants.VERIFY_ONRPASS);
			tShStoremanager.update(tsShStore_);
		}
		
	}
	/**
	 * 分页查询
	 */
	@Override
	public Page pageList(Page page, TShStore tShStore) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		String hql = " from TShStore where scBj=2 and state=:state ";
		if(StringHelper.isNotEmpty(tShStore.getTitle())){
			hql = hql+" and title like :title";
			map.put("title","%"+tShStore.getTitle()+"%");
		}
		if(tShStore.getType()!=null&&(tShStore.getType()==Constants.STORE_CHANGJIA||tShStore.getType()==Constants.STORE_JIAMENSHANG))
		{
			hql = hql+" and type=:type";
			map.put("type", tShStore.getType());
		}
		if(tShStore.getState()==null)
			map.put("state",Constants.STORE_ITEM_WAITE);
		else
			map.put("state", tShStore.getState());
		
		page = tShStoremanager.pagedQuery(hql, page.getPageNo(), page.getPageSize(), map);
		return page;
	}
	/**
	 * 
	 */
	@Override
	public TShStore getByMemberId(String memberId) throws Exception {
		if(StringHelper.isEmpty(memberId))
			throw new ServiceException("参数为空，获取店铺信息失败");
		return tShStoremanager.findUniqueBy("yhId", memberId);
	}
}

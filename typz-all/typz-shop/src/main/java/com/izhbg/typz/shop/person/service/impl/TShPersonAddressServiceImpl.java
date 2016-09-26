package com.izhbg.typz.shop.person.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.shop.person.dto.TShPersonAddress;
import com.izhbg.typz.shop.person.manager.TShPersonAddressManager;
import com.izhbg.typz.shop.person.service.TShPersonAddressService;

@Service("tShPersonAddressService")
public class TShPersonAddressServiceImpl implements TShPersonAddressService{

	@Autowired
	private TShPersonAddressManager tShPersonAddressManager;
	
	private BeanMapper beanMapper = new BeanMapper();
	/**
	 * 添加收货地址信息
	 */
	@Override
	public void add(TShPersonAddress entity) throws Exception {
		if(entity==null)
			throw new ServiceException("参数为空，保存收货地址信息失败");
		tShPersonAddressManager.save(entity);
	}
	/**
	 * 更新收货地址信息
	 */
	@Override
	public void update(TShPersonAddress entity) throws Exception {
		if(entity==null||StringHelper.isEmpty(entity.getId()))
			throw new ServiceException("参数为空，更新收货地址失败");
		TShPersonAddress tpa = tShPersonAddressManager.findUniqueBy("id", entity.getId());
		beanMapper.copy(entity, tpa);
		tShPersonAddressManager.update(entity);
	}
	/**
	 * 根据ID删除
	 */
	@Override
	public void delete(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空，删除收货地址信息失败");
		tShPersonAddressManager.removeById(id);
	}
	/**
	 * 批量删除
	 */
	@Override
	public void deleteBatche(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空，批量删除收货地址信息失败");
		for(String id:ids)
			delete(id);
		
	}
	/**
	 * 根据ID获取收货地址信息
	 */
	@Override
	public TShPersonAddress getById(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空，获取收货地址信息失败");
		
		return tShPersonAddressManager.findUniqueBy("id", id);
	}

	@Override
	public Page pageList(Page page) throws Exception {
		return null;
	}

	@Override
	public List<TShPersonAddress> getAll() throws Exception {
		return null;
	}
	/**
	 * 获取用户收货地址
	 */
	@Override
	public List<TShPersonAddress> getPersonAddressByYhId(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空，获取收货地址信息失败");
		Map<String, Object> map = new HashMap<>();
		map.put("yhId", id);
		return tShPersonAddressManager.find(" from TShPersonAddress where yhId=:yhId ", map);
	}
	/**
	 * 设置默认收货地址
	 */
	@Override
	public void setDefalutAddress(String id,String yhId) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空，设置默认收货地址信息失败");
		
		Map<String, Object> map = new HashMap<>();
		map.put("yhId", yhId);
		map.put("isEnable", Constants.ADDRESS_DEFAULT);
		TShPersonAddress tpa = tShPersonAddressManager.findUnique(" from TShPersonAddress where yhId=:yhId and isEnable=:isEnable ", map);
		if(tpa!=null&&!id.equals(tpa.getId())){
			tpa.setIsEnable(Constants.ADDRESS_NOT_DEFAULT);
			tShPersonAddressManager.update(tpa);
		}else{
			tpa = tShPersonAddressManager.findUniqueBy("id", id);
			tpa.setIsEnable(Constants.ADDRESS_DEFAULT);
			tShPersonAddressManager.update(tpa);
		}
	}

}

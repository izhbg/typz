package com.izhbg.typz.shop.store.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;

import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.shop.store.dto.TShStoreAttachefile;
import com.izhbg.typz.shop.store.manager.TShStoreAttachefileManager;
import com.izhbg.typz.shop.store.service.TShStoreAttacheFileService;

public class TShStoreAttacheFileServiceImpl implements TShStoreAttacheFileService{

	@Autowired
	private TShStoreAttachefileManager tsAttachefileManager;
	
	private BeanMapper beanMapper = new BeanMapper();
	
	@Override
	public void add(TShStoreAttachefile entity) throws Exception {
		if(entity==null)
			throw new ServiceException("参数为空,保存店铺附件信息失败");
		tsAttachefileManager.save(entity);
	}

	@Override
	public void update(TShStoreAttachefile entity) throws Exception {
		if(entity==null||StringHelper.isEmpty(entity.getId()))
			throw new ServiceException("参数为空,更新附件信息失败");
		TShStoreAttachefile tShStoreAttachefile = tsAttachefileManager.findUniqueBy("id", entity.getId());
		beanMapper.copy(entity, tShStoreAttachefile);
		tsAttachefileManager.update(entity);
	}

	@Override
	public void delete(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空，删除信息失败");
		tsAttachefileManager.removeById(id);
	}

	@Override
	public void deleteBatche(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空,批量删除信息失败");
		for(String id:ids)
			delete(id);
		
	}

	@Override
	public TShStoreAttachefile getById(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,获取店铺附件信息失败");
		return tsAttachefileManager.findUniqueBy("id", id);
	}

	@Override
	public Page pageList(Page page) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		return tsAttachefileManager.pagedQuery("from TShStoreAttachefile", page.getPageNo(), page.getPageSize(), map);
	}

	@Override
	public List<TShStoreAttachefile> getAll() throws Exception {
		return tsAttachefileManager.getAll();
	}

	@Override
	public List<TShStoreAttachefile> getStoreAttacheFile(String storeId) throws Exception {
		if(StringHelper.isEmpty(storeId))
			throw new ServiceException("参数为空，获取附件失败");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("storeId", storeId);
		map.put("state", Constants.ATTACHE_NORMAL);
		return tsAttachefileManager.find("from TShStoreAttachefile where storeId=:storeId and state=:state", map);
	}

	@Override
	public TShStoreAttachefile getIndexAttacheFile(String storeId) throws Exception {
		if(StringHelper.isEmpty(storeId))
			throw new ServiceException("参数为空，获取头像失败");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("storeId", storeId);
		map.put("state", Constants.ATTACHE_INDEX);
		return tsAttachefileManager.findUnique("from TShStoreAttachefile where storeId=:storeId and state=:state", map);
	}

}

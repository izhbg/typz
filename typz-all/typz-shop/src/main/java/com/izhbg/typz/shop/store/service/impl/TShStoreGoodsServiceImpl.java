package com.izhbg.typz.shop.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.shop.store.dto.TShStoreGoods;
import com.izhbg.typz.shop.store.manager.TShStoreGoodsManager;
import com.izhbg.typz.shop.store.service.TShStoreGoodsService;

public class TShStoreGoodsServiceImpl implements TShStoreGoodsService {

	@Autowired
	private TShStoreGoodsManager tShStoreGoodsManager;
	private BeanMapper beanMapper = new BeanMapper();
	
	@Override
	public void add(TShStoreGoods entity) throws Exception {
		if(entity==null)
			throw new ServiceException("参数为空，添加店铺产品失败");
		tShStoreGoodsManager.save(entity);
	}

	@Override
	public void update(TShStoreGoods entity) throws Exception {
		if(entity==null||StringHelper.isEmpty(entity.getId()))
			throw new ServiceException("参数为空,更新店铺产品失败");
		TShStoreGoods tsg = tShStoreGoodsManager.findUniqueBy("id", entity.getId());
		beanMapper.copy(entity, tsg);
		tShStoreGoodsManager.update(tsg);
	}

	@Override
	public void delete(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空，删除店铺产品失败");
		tShStoreGoodsManager.removeById(id);
	}

	@Override
	public void deleteBatche(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空，批量删除店铺产品失败");
		for(String id:ids)
			this.delete(id);
	}

	@Override
	public TShStoreGoods getById(String id) throws Exception {
		return null;
	}

	@Override
	public Page pageList(Page page) throws Exception {
		return null;
	}

	@Override
	public List<TShStoreGoods> getAll() throws Exception {
		return tShStoreGoodsManager.getAll();
	}

}

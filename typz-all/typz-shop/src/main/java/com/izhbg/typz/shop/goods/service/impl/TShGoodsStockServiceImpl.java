package com.izhbg.typz.shop.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.shop.goods.dto.TShGoodsPrice;
import com.izhbg.typz.shop.goods.dto.TShGoodsStock;
import com.izhbg.typz.shop.goods.manager.TShGoodsStockManager;
import com.izhbg.typz.shop.goods.service.TShGoodsStockService;
@Service("tShGoodsStockService")
public class TShGoodsStockServiceImpl implements TShGoodsStockService{

	private TShGoodsStockManager tShGoodsStockManager;
	private BeanMapper beanMapper = new BeanMapper();
	
	public void add(TShGoodsStock entity) throws Exception {
		if(entity==null)
			throw new ServiceException("参数为空，保存产品库存信息失败");
		tShGoodsStockManager.save(entity);
	}

	public void update(TShGoodsStock entity) throws Exception {
		if(entity==null||entity.getId()==null)
			throw new ServiceException("参数为空,更新产品库存信息失败");
		TShGoodsStock TShGoodsStock =  tShGoodsStockManager.findUniqueBy("id", entity.getId());
		beanMapper.copy(entity, TShGoodsStock);
		tShGoodsStockManager.update(TShGoodsStock);
	}

	public void delete(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,删除产品库存信息失败");
		TShGoodsStock TShGoodsStock =  tShGoodsStockManager.findUniqueBy("id", id);
		TShGoodsStock.setDelStatus(-1);
		tShGoodsStockManager.removeById(id);
	}

	public void deleteBatche(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空,批量删除产品库存信息失败");
		for(String id:ids){
			delete(id);
		}
	}

	public TShGoodsStock getById(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,获取产品库存信息失败");
		return tShGoodsStockManager.findUniqueBy("id", id);
	}

	public Page pageList(Page page) throws Exception {
		Map map = new HashMap<String, Object>();
		return tShGoodsStockManager.pagedQuery(" from TShGoodsStock ", page.getPageNo(), page.getPageSize(), map);
	}

	public List<TShGoodsStock> getAll() throws Exception {
		return tShGoodsStockManager.getAll();
	}

	@Resource
	public void settShGoodsStockManager(TShGoodsStockManager tShGoodsStockManager) {
		this.tShGoodsStockManager = tShGoodsStockManager;
	}

	@Override
	public TShGoodsStock queryByGoodsIdAndVersion(String id, Integer version)
			throws Exception {
		if(StringHelper.isEmpty(id)||version==null)
			throw new ServiceException("参数为空，获取商品价格信息失败");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("version", version);
		String hql = " from TShGoodsStock where version=:version and goodsId=:id";
		TShGoodsStock tShGoodsStock = tShGoodsStockManager.findUnique(hql, map);
		return tShGoodsStock;
	}
	
	

}

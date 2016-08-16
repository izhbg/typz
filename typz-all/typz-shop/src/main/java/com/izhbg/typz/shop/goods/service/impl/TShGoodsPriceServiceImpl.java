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
import com.izhbg.typz.shop.goods.dto.TShGoodsImage;
import com.izhbg.typz.shop.goods.dto.TShGoodsPrice;
import com.izhbg.typz.shop.goods.manager.TShGoodsPriceManager;
import com.izhbg.typz.shop.goods.service.TShGoodsPriceService;
@Service("tShGoodsPriceService")
public class TShGoodsPriceServiceImpl implements TShGoodsPriceService{

	private TShGoodsPriceManager tShGoodsPriceManager;
	private BeanMapper beanMapper = new BeanMapper();
	
	public void add(TShGoodsPrice entity) throws Exception {
		if(entity==null)
			throw new ServiceException("参数为空，保存产品价格信息失败");
		tShGoodsPriceManager.save(entity);
	}

	public void update(TShGoodsPrice entity) throws Exception {
		if(entity==null||entity.getId()==null)
			throw new ServiceException("参数为空,更新产品价格信息失败");
		TShGoodsPrice TShGoodsPrice =  tShGoodsPriceManager.findUniqueBy("id", entity.getId());
		beanMapper.copy(entity, TShGoodsPrice);
		tShGoodsPriceManager.update(TShGoodsPrice);
	}

	public void delete(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,删除产品价格信息失败");
		TShGoodsPrice TShGoodsPrice =  tShGoodsPriceManager.findUniqueBy("id", id);
		TShGoodsPrice.setDelStatus(-1);
		tShGoodsPriceManager.removeById(id);
	}

	public void deleteBatche(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空,批量删除产品价格信息失败");
		for(String id:ids){
			delete(id);
		}
	}

	public TShGoodsPrice getById(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,获取产品价格信息失败");
		return tShGoodsPriceManager.findUniqueBy("id", id);
	}

	public Page pageList(Page page) throws Exception {
		Map map = new HashMap<String, Object>();
		return tShGoodsPriceManager.pagedQuery(" from TShGoodsPrice ", page.getPageNo(), page.getPageSize(), map);
	}

	public List<TShGoodsPrice> getAll() throws Exception {
		return tShGoodsPriceManager.getAll();
	}

	@Resource
	public void settShGoodsPriceManager(TShGoodsPriceManager tShGoodsPriceManager) {
		this.tShGoodsPriceManager = tShGoodsPriceManager;
	}

	@Override
	public TShGoodsPrice queryByGoodsIdAndVersion(String id, Integer version)
			throws Exception {
		if(StringHelper.isEmpty(id)||version==null)
			throw new ServiceException("参数为空，获取商品价格信息失败");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("version", version);
		String hql = " from TShGoodsPrice where version=:version and goodsId=:id";
		TShGoodsPrice tShGoodsPrice = tShGoodsPriceManager.findUnique(hql, map);
		return tShGoodsPrice;
	}

	
	

}

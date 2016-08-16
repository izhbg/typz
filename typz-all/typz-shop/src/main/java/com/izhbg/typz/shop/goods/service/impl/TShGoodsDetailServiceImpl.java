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
import com.izhbg.typz.shop.goods.dto.TShGoodsBrand;
import com.izhbg.typz.shop.goods.dto.TShGoodsDetail;
import com.izhbg.typz.shop.goods.manager.TShGoodsDetailManager;
import com.izhbg.typz.shop.goods.service.TShGoodsDetailService;
@Service("tShGoodsDetailService")
public class TShGoodsDetailServiceImpl implements TShGoodsDetailService{

	private TShGoodsDetailManager tShGoodsDetailManager;
	private BeanMapper beanMapper = new BeanMapper();
	
	public void add(TShGoodsDetail entity) throws Exception {
		if(entity==null)
			throw new ServiceException("参数为空，保存产品详细信息失败");
		tShGoodsDetailManager.save(entity);
	}

	public void update(TShGoodsDetail entity) throws Exception {
		if(entity==null||entity.getId()==null)
			throw new ServiceException("参数为空,更新产品详细信息失败");
		TShGoodsDetail TShGoodsDetail =  tShGoodsDetailManager.findUniqueBy("id", entity.getId());
		beanMapper.copy(entity, TShGoodsDetail);
		tShGoodsDetailManager.update(TShGoodsDetail);
	}

	public void delete(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,删除产品详细信息失败");
		TShGoodsDetail TShGoodsDetail =  tShGoodsDetailManager.findUniqueBy("id", id);
		TShGoodsDetail.setDelStatus(-1);
		tShGoodsDetailManager.removeById(id);
	}
	@Override
	public void deleteBatche(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空,批量删除产品详细信息失败");
		for(String id:ids){
			delete(id);
		}
	}

	public TShGoodsDetail getById(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,获取产品详细信息失败");
		return tShGoodsDetailManager.findUniqueBy("id", id);
	}

	public Page pageList(Page page) throws Exception {
		Map map = new HashMap<String, Object>();
		return tShGoodsDetailManager.pagedQuery(" from TShGoodsDetail ", page.getPageNo(), page.getPageSize(), map);
	}

	public List<TShGoodsDetail> getAll() throws Exception {
		return tShGoodsDetailManager.getAll();
	}
	@Override
	public TShGoodsDetail queryBy(String name, String value) throws Exception {
		return tShGoodsDetailManager.findUniqueBy(name, value);
	}
	@Resource
	public void settShGoodsDetailManager(TShGoodsDetailManager tShGoodsDetailManager) {
		this.tShGoodsDetailManager = tShGoodsDetailManager;
	}

	@Override
	public TShGoodsDetail queryByGoodsIdAndVersion(String id, Integer version)
			throws Exception {
		if(StringHelper.isEmpty(id)||version==null)
			throw new ServiceException("参数为空，获取商品详细信息失败");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("version", version);
		String hql = " from TShGoodsDetail where version=:version and goodsId=:id";
		TShGoodsDetail tShGoodsDetail = tShGoodsDetailManager.findUnique(hql, map);
		return tShGoodsDetail;
	}

	

	


	
}

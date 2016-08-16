package com.izhbg.typz.shop.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.shop.goods.dto.TShGoodsImage;
import com.izhbg.typz.shop.goods.manager.TShGoodsImageManager;
import com.izhbg.typz.shop.goods.service.TShGoodsImageService;
@Service("tShGoodsImageService")
public class TShGoodsImageServiceImpl implements TShGoodsImageService {

	private TShGoodsImageManager tShGoodsImageManager;
	private BeanMapper beanMapper = new BeanMapper();
	
	public void add(TShGoodsImage entity) throws Exception {
		if(entity==null)
			throw new ServiceException("参数为空，保存产品图片信息失败");
		tShGoodsImageManager.save(entity);
	}

	public void update(TShGoodsImage entity) throws Exception {
		if(entity==null||entity.getId()==null)
			throw new ServiceException("参数为空,更新产品图片信息失败");
		TShGoodsImage TShGoodsImage =  tShGoodsImageManager.findUniqueBy("id", entity.getId());
		beanMapper.copy(entity, TShGoodsImage);
		tShGoodsImageManager.update(TShGoodsImage);
	}

	public void delete(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,删除产品图片信息失败");
		TShGoodsImage TShGoodsImage =  tShGoodsImageManager.findUniqueBy("id", id);
		TShGoodsImage.setDelStatus(-1);
		tShGoodsImageManager.removeById(id);
	}

	public void deleteBatche(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空,批量删除产品图片信息失败");
		for(String id:ids){
			delete(id);
		}
	}

	public TShGoodsImage getById(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,获取产品图片信息失败");
		return tShGoodsImageManager.findUniqueBy("id", id);
	}

	public Page pageList(Page page) throws Exception {
		Map map = new HashMap<String, Object>();
		return tShGoodsImageManager.pagedQuery(" from TShGoodsImage ", page.getPageNo(), page.getPageSize(), map);
	}

	public List<TShGoodsImage> getAll() throws Exception {
		return tShGoodsImageManager.getAll();
	}
	@Override
	public List<TShGoodsImage> findByGoodsId(String goodsId) throws Exception {
		return tShGoodsImageManager.findBy("goodsId", goodsId);
	}
	@Resource
	public void settShGoodsImageManager(TShGoodsImageManager tShGoodsImageManager) {
		this.tShGoodsImageManager = tShGoodsImageManager;
	}

	@Override
	public List<TShGoodsImage> queryByGoodsIdAndVersion(String id, Integer version)
			throws Exception {
		if(StringHelper.isEmpty(id)||version==null)
			throw new ServiceException("参数为空，获取商品图片信息失败");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("version", version);
		String hql = " from TShGoodsImage where version=:version and goodsId=:id";
		List<TShGoodsImage> tShGoodsImages = tShGoodsImageManager.find(hql, map);
		return tShGoodsImages;
	}

	

	

}

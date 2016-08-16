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
import com.izhbg.typz.shop.goods.dto.TShGoodsBrand;
import com.izhbg.typz.shop.goods.manager.TShGoodsBrandManager;
import com.izhbg.typz.shop.goods.service.TShGoodsBrandService;
@Service("tShGoodsBrandService")
public class TShGoodsBrandServiceImpl implements TShGoodsBrandService{

	private TShGoodsBrandManager tShGoodsBrandManager;
	private BeanMapper beanMapper = new BeanMapper();
	
	public void add(TShGoodsBrand entity) throws Exception {
		if(entity==null)
			throw new ServiceException("参数为空，保存产品品牌信息失败");
		tShGoodsBrandManager.save(entity);
	}

	public void update(TShGoodsBrand entity) throws Exception {
		if(entity==null||entity.getId()==null)
			throw new ServiceException("参数为空,更新产品品牌信息失败");
		TShGoodsBrand TShGoodsBrand =  tShGoodsBrandManager.findUniqueBy("id", entity.getId());
		beanMapper.copy(entity, TShGoodsBrand);
		tShGoodsBrandManager.update(TShGoodsBrand);
	}

	public void delete(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,删除产品品牌信息失败");
		TShGoodsBrand TShGoodsBrand =  tShGoodsBrandManager.findUniqueBy("id", id);
		TShGoodsBrand.setDelStatus(-1);
		tShGoodsBrandManager.removeById(id);
	}

	public void deleteBatche(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空,批量删除产品品牌信息失败");
		for(String id:ids){
			delete(id);
		}
	}

	public TShGoodsBrand getById(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,获取产品品牌信息失败");
		return tShGoodsBrandManager.findUniqueBy("id", id);
	}

	public Page pageList(Page page) throws Exception {
		Map map = new HashMap<String, Object>();
		return tShGoodsBrandManager.pagedQuery(" from TShGoodsBrand ", page.getPageNo(), page.getPageSize(), map);
	}

	public List<TShGoodsBrand> getAll() throws Exception {
		return tShGoodsBrandManager.getAll();
	}

	@Resource
	public void settShGoodsBrandManager(TShGoodsBrandManager tShGoodsBrandManager) {
		this.tShGoodsBrandManager = tShGoodsBrandManager;
	}


	

}

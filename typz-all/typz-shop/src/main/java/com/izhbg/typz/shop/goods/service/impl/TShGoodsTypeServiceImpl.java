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
import com.izhbg.typz.shop.goods.dto.TShGoodsStock;
import com.izhbg.typz.shop.goods.dto.TShGoodsType;
import com.izhbg.typz.shop.goods.manager.TShGoodsTypeManager;
import com.izhbg.typz.shop.goods.service.TShGoodsTypeService;
@Service("tShGoodsTypeService")
public class TShGoodsTypeServiceImpl implements TShGoodsTypeService{

	private TShGoodsTypeManager tShGoodsTypeManager;
	private BeanMapper beanMapper = new BeanMapper();
	
	public void add(TShGoodsType entity) throws Exception {
		if(entity==null)
			throw new ServiceException("参数为空，保存产品类目信息失败");
		tShGoodsTypeManager.save(entity);
	}

	public void update(TShGoodsType entity) throws Exception {
		if(entity==null||entity.getId()==null)
			throw new ServiceException("参数为空,更新产品类目信息失败");
		TShGoodsType TShGoodsType =  tShGoodsTypeManager.findUniqueBy("id", entity.getId());
		beanMapper.copy(entity, TShGoodsType);
		tShGoodsTypeManager.update(TShGoodsType);
	}

	public void delete(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,删除产品类目信息失败");
		TShGoodsType TShGoodsType =  tShGoodsTypeManager.findUniqueBy("id", id);
		TShGoodsType.setDelStatus(-1);
		tShGoodsTypeManager.removeById(id);
	}

	public void deleteBatche(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空,批量删除产品类目信息失败");
		for(String id:ids){
			delete(id);
		}
	}

	public TShGoodsType getById(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,获取产品类目信息失败");
		return tShGoodsTypeManager.findUniqueBy("id", id);
	}

	public Page pageList(Page page) throws Exception {
		Map map = new HashMap<String, Object>();
		return tShGoodsTypeManager.pagedQuery(" from TShGoodsType ", page.getPageNo(), page.getPageSize(), map);
	}

	public List<TShGoodsType> getAll() throws Exception {
		return tShGoodsTypeManager.getAll();
	}

	@Resource
	public void settShGoodsTypeManager(TShGoodsTypeManager tShGoodsTypeManager) {
		this.tShGoodsTypeManager = tShGoodsTypeManager;
	}

	@Override
	public List<TShGoodsType> getListByPid(String pid) throws Exception {
		/*if(StringHelper.isEmpty(pid))
			throw new ServiceException("参数为空，获取类目列表失败");*/
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringHelper.isEmpty(pid)){
			return tShGoodsTypeManager.find(" from TShGoodsType where pid =''  order by sort ", map);
		}else{
			map.put("pid", pid+",%");
			return tShGoodsTypeManager.find(" from TShGoodsType where pid like :pid  order by sort ", map);
		}
		
	}


	

}

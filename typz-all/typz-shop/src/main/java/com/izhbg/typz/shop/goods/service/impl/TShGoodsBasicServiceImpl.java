package com.izhbg.typz.shop.goods.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.shop.common.util.ConstantUtils;
import com.izhbg.typz.shop.goods.dto.TShGoods;
import com.izhbg.typz.shop.goods.dto.TShGoodsBasic;
import com.izhbg.typz.shop.goods.dto.TShGoodsDetail;
import com.izhbg.typz.shop.goods.dto.TShGoodsImage;
import com.izhbg.typz.shop.goods.manager.TShGoodsBasicManager;
import com.izhbg.typz.shop.goods.manager.TShGoodsDetailManager;
import com.izhbg.typz.shop.goods.manager.TShGoodsImageManager;
import com.izhbg.typz.shop.goods.service.TShGoodsBasicService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;

@Service("tShGoodsBasicService")
@Transactional(rollbackOn=Exception.class)
public class TShGoodsBasicServiceImpl implements TShGoodsBasicService {
	
	@Autowired
	private TShGoodsBasicManager tShGoodsBasicManager;
	@Autowired
	private TShGoodsDetailManager tShGoodsDetailManager;
	@Autowired
	private TShGoodsImageManager tShGoodsImageManager;
	private BeanMapper beanMapper = new BeanMapper();
	
	public void add(TShGoodsBasic entity) throws Exception {
		if(entity==null)
			throw new ServiceException("参数为空，保存产品基本信息失败");
		tShGoodsBasicManager.save(entity);
	}

	public void update(TShGoodsBasic entity) throws Exception {
		if(entity==null||entity.getId()==null)
			throw new ServiceException("参数为空,更新产品基本信息失败");
		TShGoodsBasic tShGoodsBasic =  tShGoodsBasicManager.findUniqueBy("id", entity.getId());
		beanMapper.copy(entity, tShGoodsBasic);
		tShGoodsBasicManager.update(tShGoodsBasic);
	}

	public void delete(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,删除产品基本信息失败");
		//将商品下架后 删除该商品
		underGoods(id);
		TShGoodsBasic tShGoodsBasic = queryByGoodsIdAndVersion(id, -1);
		tShGoodsBasic.setDelStatus(-1);
		tShGoodsBasicManager.update(tShGoodsBasic);
	}
	
	public void recover(String id) throws Exception{
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,恢复产品基本信息失败");
		//将商品下架后 删除该商品
		underGoods(id);
		TShGoodsBasic tShGoodsBasic = queryByGoodsIdAndVersion(id, -1);
		tShGoodsBasic.setDelStatus(1);
		tShGoodsBasicManager.update(tShGoodsBasic);
	}
	public void deleteBatche(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空,批量删除产品基本信息失败");
		for(String id:ids){
			delete(id);
		}
	}

	public TShGoodsBasic getById(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,获取产品基本信息失败");
		return tShGoodsBasicManager.findUniqueBy("id", id);
	}

	public Page pageList(Page page) throws Exception {
		Map map = new HashMap<String, Object>();
		return tShGoodsBasicManager.pagedQuery(" from TShGoodsBasic ", page.getPageNo(), page.getPageSize(), map);
	}

	public List<TShGoodsBasic> getAll() throws Exception {
		return tShGoodsBasicManager.getAll();
	}

	

	/**
	 * 默认Version为NULL 对应的记录为 B端维护记录
	 * 点击发布（生成静态页面时） 生成版本号，发布一条上线的商品信息
	 * 上/下架操作对应 version为最大的记录信息
	 */
	@Override
	public void addOrUpdateGoods(TShGoods tShGoods) throws Exception {
		if(tShGoods==null)
			throw new ServiceException("参数为空，保存或更新商品失败");
		TShGoodsBasic tShGoodsBasic = setValueTShGoodsBasic(tShGoods);
		TShGoodsDetail tShGoodsDetail = setValueTShGoodsDetail(tShGoods);
		//基本信息
		if(StringHelper.isEmpty(tShGoodsBasic.getId())){
			//新增 goodsbasic
			tShGoodsBasic.setId(IdGenerator.getInstance().getUniqTime()+"");
			tShGoodsBasic.setCreateUser(SpringSecurityUtils.getCurrentUserId());
			tShGoodsBasic.setCreateTime(new Date());
			tShGoodsBasic.setDelStatus(1);
			tShGoodsBasic.setVersion(-1);
			tShGoodsBasic.setStatus(-1);
			tShGoodsBasicManager.save(tShGoodsBasic);
			//新增 goodsdetail
			tShGoodsDetail.setGoodsId(tShGoodsBasic.getId());
			tShGoodsDetail.setVersion(-1);
			tShGoodsDetail.setDelStatus(0);
			tShGoodsDetail.setId(IdGenerator.getInstance().getUniqTime()+"");
			tShGoodsDetailManager.save(tShGoodsDetail);
			//图片
			if(StringHelper.isNotEmpty(tShGoods.getPicIds())){
				String[] picIds = tShGoods.getPicIds().split(",");
				TShGoodsImage tShGoodsImage = null;
				for(String picId:picIds){
					if(StringHelper.isNotEmpty(picId)){
						tShGoodsImage = tShGoodsImageManager.findUniqueBy("id", picId);
						tShGoodsImage.setGoodsId(tShGoodsBasic.getId());
						tShGoodsImage.setCreateUser(SpringSecurityUtils.getCurrentUserId());
						tShGoodsImage.setCreateTime(new Date());
						tShGoodsImageManager.update(tShGoodsImage);
					}
				}
			}
		}else{
			//更新
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", tShGoodsBasic.getId());
			map.put("version", ConstantUtils.GOODS_INIT_VERSION);
			TShGoodsBasic tShGoodsBasic_ = tShGoodsBasicManager.findUnique(" from TShGoodsBasic where id=:id and version=:version",map);
			beanMapper.copy(tShGoodsBasic, tShGoodsBasic_);
			tShGoodsBasic_.setUpdateUser(SpringSecurityUtils.getCurrentUserId());
			tShGoodsBasic_.setUpdateTime(new Date());
			tShGoodsBasicManager.update(tShGoodsBasic_);
			//详细信息
			//更新
			TShGoodsDetail tShGoodsDetail_= tShGoodsDetailManager.findUniqueBy("id",tShGoodsDetail.getId());
			beanMapper.copy(tShGoodsDetail, tShGoodsDetail_);
			tShGoodsDetailManager.update(tShGoodsDetail_);
			
			//图片
			if(StringHelper.isNotEmpty(tShGoods.getPicIds())){
				String[] picIds = tShGoods.getPicIds().split(",");
				TShGoodsImage tShGoodsImage = null;
				//清除 关联的图片信息
				//新增 修改后的图片关系信息
				for(String picId:picIds){
					if(StringHelper.isNotEmpty(picId)){
						map.clear();
						map.put("id", picId);
						map.put("version", ConstantUtils.GOODS_INIT_VERSION);
						tShGoodsImage = tShGoodsImageManager.findUnique(" from TShGoodsImage where id=:id and version=:version", map);
						if(StringHelper.isNotEmpty(tShGoodsImage.getGoodsId()))
						{
							tShGoodsImage.setCreateUser(SpringSecurityUtils.getCurrentUserId());
							tShGoodsImage.setCreateTime(new Date());
							tShGoodsImage.setGoodsId(tShGoodsBasic_.getId());
							tShGoodsImageManager.update(tShGoodsImage);
						}else{
							tShGoodsImage.setUpdateTime(new Date());
							tShGoodsImage.setGoodsId(tShGoodsBasic_.getId());
							tShGoodsImage.setUpdateUser(SpringSecurityUtils.getCurrentUserId());
							tShGoodsImageManager.update(tShGoodsImage);
						}
					}
				}
			}
		}
		
	}
	/**
	 * 
	 * @param tShGoods
	 * @return
	 */
	private TShGoodsBasic setValueTShGoodsBasic(TShGoods tShGoods){
		if(tShGoods==null)
			return null;
		
		TShGoodsBasic tShGoodsBasic = new TShGoodsBasic();
		if(StringHelper.isNotEmpty(tShGoods.getBasicId()))
			tShGoodsBasic.setId(tShGoods.getBasicId());
		if(StringHelper.isNotEmpty(tShGoods.getAliasName()))
			tShGoodsBasic.setAliasName(tShGoods.getAliasName());
		if(StringHelper.isNotEmpty(tShGoods.getAliasName()))
			tShGoodsBasic.setBrandId(tShGoods.getBrandId());
		if(StringHelper.isNotEmpty(tShGoods.getModelId()))
			tShGoodsBasic.setModelId(tShGoods.getModelId());
		if(StringHelper.isNotEmpty(tShGoods.getName()))
			tShGoodsBasic.setName(tShGoods.getName());
		if(StringHelper.isNotEmpty(tShGoods.getShopBasicId()))
			tShGoodsBasic.setShopBasicId(tShGoods.getShopBasicId());
		if(StringHelper.isNotEmpty(tShGoods.getSpecificationsId()))
			tShGoodsBasic.setSpecificationsId(tShGoods.getSpecificationsId());
		if(StringHelper.isNotEmpty(tShGoods.getTypeId()))
			tShGoodsBasic.setTypeId(tShGoods.getTypeId());
		if(StringHelper.isNotEmpty(tShGoods.getUnit()))
			tShGoodsBasic.setUnit(tShGoods.getUnit());
		if(StringHelper.isNotEmpty(tShGoods.getVender()))
			tShGoodsBasic.setVender(tShGoods.getVender());
		return tShGoodsBasic;
	}
	/**
	 * 
	 * @param tShGoods
	 * @return
	 */
	private TShGoodsDetail setValueTShGoodsDetail(TShGoods tShGoods){
		if(tShGoods==null)
			return null;
		TShGoodsDetail tShGoodsDetail = new TShGoodsDetail();
		if(StringHelper.isNotEmpty(tShGoods.getContent()))
			tShGoodsDetail.setContent(tShGoods.getContent());
		if(tShGoods.getDefaultPrice()!=null)
			tShGoodsDetail.setDefaultPrice(tShGoods.getDefaultPrice());
		if(StringHelper.isNotEmpty(tShGoods.getGoodsId()))
			tShGoodsDetail.setGoodsId(tShGoods.getGoodsId());
		if(StringHelper.isNotEmpty(tShGoods.getDetailId()))
			tShGoodsDetail.setId(tShGoods.getDetailId());
		if(tShGoods.getStockStatus()!=null)
			tShGoodsDetail.setStockStatus(tShGoods.getStockStatus());
		return tShGoodsDetail;
	}
	@Override
	public Page pageList(Page page, TShGoods tShGoods) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		String hql = " from TShGoodsBasic where version=-1";
		if(tShGoods.getStatus()==null)
			map.put("status", ConstantUtils.GOODS_ITEM_UP);
		else
			map.put("status", tShGoods.getStatus());
		
		if(tShGoods.getDelStatus()!=null)
			map.put("delStatus", tShGoods.getDelStatus());
		else
			map.put("delStatus", ConstantUtils.ITEM_DELETE_UN);
		
		page = tShGoodsBasicManager.pagedQuery(hql+getWhere(tShGoods), page.getPageNo(), page.getPageSize(), map);
		List<TShGoodsBasic> tShGoodsBasics = (List<TShGoodsBasic>) page.getResult();
		TShGoodsDetail tShGoodsDetail = null;
		for(TShGoodsBasic tShGoodsBasic:tShGoodsBasics){
			tShGoodsDetail = tShGoodsDetailManager.findUniqueBy("goodsId", tShGoodsBasic.getId());
			if(tShGoodsDetail!=null)
				tShGoodsBasic.settShGoodsDetail(tShGoodsDetail);
		}
		page.setResult(tShGoodsBasics);
		return page;
	}
	private String getWhere(TShGoods tShGoods) throws Exception{
		StringBuffer  sb = new StringBuffer(" and status=:status ");
		if(tShGoods.getDelStatus()!=null)
			sb.append(" and delStatus=:delStatus ");
		return sb.toString();
	}
	/**
	 * 根据goodsId version获取商品基本信息
	 */
	@Override
	public TShGoodsBasic queryByGoodsIdAndVersion(String id, Integer version)
			throws Exception {
		if(StringHelper.isEmpty(id)||version==null)
			throw new ServiceException("参数为空，获取商品基本信息失败");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("version", version);
		String hql = " from TShGoodsBasic where version=:version and id=:id";
		TShGoodsBasic tShGoodsBasic = tShGoodsBasicManager.findUnique(hql, map);
		return tShGoodsBasic;
	}
	/**
	 * 下架
	 */
	@Override
	public void underGoods(String goodsId) throws Exception {
		Integer version = getMaxVersionByGoodsId(goodsId);
		if(version!=null){
			TShGoodsBasic tShGoodsBasic = this.queryByGoodsIdAndVersion(goodsId, version);
			if(tShGoodsBasic!=null){
				tShGoodsBasic.setStatus(ConstantUtils.GOODS_ITEM_UNDER);
				tShGoodsBasicManager.update(tShGoodsBasic);
			}
		}
		
		TShGoodsBasic tShGoodsBasic = this.queryByGoodsIdAndVersion(goodsId, ConstantUtils.GOODS_INIT_VERSION);
		if(tShGoodsBasic!=null){
			tShGoodsBasic.setStatus(ConstantUtils.GOODS_ITEM_UNDER);
			tShGoodsBasicManager.update(tShGoodsBasic);
		}
	}
	/**
	 * 批量下架
	 */
	@Override
	public void batchUnderGoods(String[] goodsIds) throws Exception {
		if(goodsIds==null||goodsIds.length<=0)
			throw new ServiceException("参数为空，批量下架商品失败");
		for(String goodsId:goodsIds){
			this.underGoods(goodsId);
		}
	}
	/**
	 * 上架商品
	 */
	@Override
	public void upGoods(String goodsId) throws Exception {
		// TODO 页面静态化
		Integer version = getMaxVersionByGoodsId(goodsId);
		if(version!=null){
			TShGoodsBasic tShGoodsBasic = this.queryByGoodsIdAndVersion(goodsId, version);
			if(tShGoodsBasic!=null){
				tShGoodsBasic.setStatus(ConstantUtils.GOODS_ITEM_UP);
				tShGoodsBasicManager.update(tShGoodsBasic);
			}
		}
		
		TShGoodsBasic tShGoodsBasic = this.queryByGoodsIdAndVersion(goodsId, ConstantUtils.GOODS_INIT_VERSION);
		if(tShGoodsBasic!=null){
			tShGoodsBasic.setStatus(ConstantUtils.GOODS_ITEM_UP);
			tShGoodsBasicManager.update(tShGoodsBasic);
		}
	}
	/**
	 * 批量上架商品
	 */
	@Override
	public void batchUpGoods(String[] goodsIds) throws Exception {
		if(goodsIds==null||goodsIds.length<=0)
			throw new ServiceException("参数为空，批量上架商品失败");
		for(String goodsId:goodsIds){
			this.upGoods(goodsId);
		}
		
	}
	/**
	 * 获取商品的最大版本号
	 */
	@Override
	public Integer getMaxVersionByGoodsId(String goodsId) throws Exception {
		if(StringHelper.isEmpty(goodsId))
			throw new ServiceException("参数为空，获取商品版本号失败");
		List<Map<String,Object>> list = tShGoodsBasicManager.getJdbcTemplate().queryForList("select max(version) version from t_sh_goods_basic where id=? and version!=-1 GROUP BY version ", goodsId);
				//(" select max(t.version) version from TShGoodsBasic t where t.delStatus!=-1 and t.version!=-1 and t.id=:id group by t.version", map).list();
		if(list!=null&&list.size()>0)
		{
			Map map = list.get(0);
			return Integer.parseInt(map.get("version")+"");
		}else
			return null;
	}
	/**
	 * 批量恢复商品信息
	 */
	@Override
	public void recoverBatche(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空,批量恢复产品基本信息失败");
		for(String id:ids){
			recover(id);
		}
	}

	
	
}

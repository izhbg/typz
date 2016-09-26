package com.izhbg.typz.shop.order.service.impl;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.shop.order.dto.TShOrder;
import com.izhbg.typz.shop.order.dto.TShOrderGood;
import com.izhbg.typz.shop.order.manager.TShOrderGoodManager;
import com.izhbg.typz.shop.order.manager.TShOrderManager;
import com.izhbg.typz.shop.order.service.TShOrderService;
import com.izhbg.typz.shop.purchase.dto.TShPurchase;
import com.izhbg.typz.shop.purchase.manager.TShPurchaseManager;
@Service("tShOrderService")
public class TShOrderServiceImpl implements TShOrderService {
	
	@Autowired
	private TShOrderManager tShOrdermanager;
	@Autowired
	private TShOrderGoodManager tShOrderGoodsManager;
	@Autowired
	private TShPurchaseManager tShPurchaseManager;
	
	private BeanMapper beanMapper = new BeanMapper();
	
	
	/**
	 * 添加订单
	 * 1、订单信息
	 * 2、订单关联的产品
	 * goodsInfo格式：goodsId,num,price#goodsId,num,price...
	 * 	 */
	@Override
	public void add(TShOrder entity) throws Exception {
		if(entity==null||StringHelper.isEmpty(entity.getGoodsInfo()))
			throw new ServiceException("参数为空，添加订单失败");
		tShOrdermanager.save(entity);
		
		String goodsInfo = entity.getGoodsInfo();
		String[] goodsinfos = goodsInfo.split("#");
		String goodsId = null;
		String num = null;
		String price = null;
		TShOrderGood tsog = null;
		TShPurchase tp = null;
		for(String gi:goodsinfos){
			goodsId = gi.split(",")[0];
			num = gi.split(",")[1];
			price = gi.split(",")[2];
			tsog = new TShOrderGood();
			tsog.setId(IdGenerator.getInstance().getUniqTime()+"");
			tsog.setGoodsId(goodsId);
			tsog.setNum(Integer.parseInt(num));
			tsog.setOrderId(entity.getId());
			tsog.setStoreId(entity.getStoreId());
			tsog.setPrice(Double.parseDouble(price));
			tShOrderGoodsManager.save(tsog);
			
			//从购物车中移除
			tp = tShPurchaseManager.findUnique(" from TShPurchase where yhId=:yhId and goodsId=:goodsId", entity.getYhId(),goodsId);
			if(tp!=null)
				tShPurchaseManager.remove(tp);
		}
		
	}

	@Override
	public void update(TShOrder entity) throws Exception {
		if(entity==null||StringHelper.isEmpty(entity.getId()))
			throw new ServiceException("参数为空,更新数据失败");
		TShOrder to = tShOrdermanager.findUniqueBy("id", entity.getId());
		beanMapper.copy(entity, to);
		tShOrdermanager.update(entity);
		// TODO
	}

	@Override
	public void delete(String id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBatche(String[] ids) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TShOrder getById(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page pageList(Page page) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TShOrder> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.izhbg.typz.shop.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.shop.goods.dto.TShGoodsBasic;
import com.izhbg.typz.shop.goods.manager.TShGoodsBasicManager;
import com.izhbg.typz.shop.order.dto.OrderAddress;
import com.izhbg.typz.shop.order.dto.TShOrder;
import com.izhbg.typz.shop.order.dto.TShOrderAddress;
import com.izhbg.typz.shop.order.dto.TShOrderGood;
import com.izhbg.typz.shop.order.manager.TShOrderAddressManager;
import com.izhbg.typz.shop.order.manager.TShOrderGoodManager;
import com.izhbg.typz.shop.order.manager.TShOrderManager;
import com.izhbg.typz.shop.order.service.TShOrderService;
import com.izhbg.typz.shop.person.dto.TShPersonAddress;
import com.izhbg.typz.shop.person.manager.TShPersonAddressManager;
import com.izhbg.typz.shop.purchase.dto.TShPurchase;
import com.izhbg.typz.shop.purchase.manager.TShPurchaseManager;
import com.izhbg.typz.shop.store.dto.TShStore;
import com.izhbg.typz.shop.store.manager.TShStoreManager;
@Service("tShOrderService")
public class TShOrderServiceImpl implements TShOrderService {
	
	@Autowired
	private TShOrderManager tShOrdermanager;
	@Autowired
	private TShOrderGoodManager tShOrderGoodsManager;
	@Autowired
	private TShPurchaseManager tShPurchaseManager;
	@Autowired
	private TShOrderAddressManager tShOrderAddressManager;
	@Autowired
	private TShStoreManager tShStoreManager;
	@Autowired
	private TShGoodsBasicManager tShGoodsBasicManager;
	@Autowired
	private TShPersonAddressManager tShPersonAddressManager;
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
			/*tp = tShPurchaseManager.findUnique(" from TShPurchase where yhId=:yhId and goodsId=:goodsId", entity.getYhId(),goodsId);
			if(tp!=null)
				tShPurchaseManager.remove(tp);*/
		}
		
	}
	/**
	 * 更新订单 总数量和
	 */
	@Override
	public void update(TShOrder entity) throws Exception {
		if(entity==null||StringHelper.isEmpty(entity.getId()))
			throw new ServiceException("参数为空,更新数据失败");
		//更新订单金额，总数量
		TShOrder to = tShOrdermanager.findUniqueBy("id", entity.getId());
		beanMapper.copy(entity, to);
		tShOrdermanager.update(entity);
	}

	@Override
	public void delete(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空，删除数据失败");
		TShOrder to = tShOrdermanager.findUniqueBy("id", id);
		to.setScBj(Constants.DELETE_STATE);
		tShOrdermanager.update(to);
	}

	@Override
	public void deleteBatche(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空，批量删除失败");
		
	}

	@Override
	public TShOrder getById(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,获取数据失败");
		//获取订单
		TShOrder to = tShOrdermanager.findUniqueBy("id", id);
		//获取订单店铺
		TShStore tss = tShStoreManager.findUniqueBy("id", to.getStoreId());
		if(tss!=null)
			to.settShStore(tss);
		//获取订单产品
		List<TShOrderGood> tShOrderGoods = tShOrderGoodsManager.findBy("orderId", to.getId());
		List<TShGoodsBasic> tShGoodsBasics = new ArrayList<>();
		TShGoodsBasic tsgb = null;
		if(tShOrderGoods!=null)
			for(TShOrderGood tsog:tShOrderGoods){
				tsgb = tShGoodsBasicManager.findUniqueBy("id", tsog.getGoodsId());
				if(tsgb!=null)
					tShGoodsBasics.add(tsgb);
			}
		to.settShGoodsList(tShGoodsBasics);
		return to;
	}

	@Override
	public Page pageList(Page page) throws Exception {
		Map map = new HashMap<String, Object>();
		page = tShOrdermanager.pagedQuery(" from TShOrder", page.getPageNo(), page.getPageSize(), map);
		List<TShOrder> tShOrders = (List<TShOrder>) page.getResult();
		TShStore tss = null;
		List<TShOrderGood> tShOrderGoods = null;
		List<TShGoodsBasic> tShGoodsBasics = null;
		for(TShOrder to:tShOrders){
			//获取订单店铺
			tss = tShStoreManager.findUniqueBy("id", to.getStoreId());
			if(tss!=null)
				to.settShStore(tss);
			//获取订单产品
			tShOrderGoods = tShOrderGoodsManager.findBy("orderId", to.getId());
			tShGoodsBasics = new ArrayList<>();
			TShGoodsBasic tsgb = null;
			if(tShOrderGoods!=null)
				for(TShOrderGood tsog:tShOrderGoods){
					tsgb = tShGoodsBasicManager.findUniqueBy("id", tsog.getGoodsId());
					if(tsgb!=null)
						tShGoodsBasics.add(tsgb);
				}
			to.settShGoodsList(tShGoodsBasics);
		}
		page.setResult(tShOrders);
		return page;
	}

	@Override
	public List<TShOrder> getAll() throws Exception {
		return null;
	}
	
	@Override
	public void addOrUpdateOrderAddress(OrderAddress orderAddress) throws Exception {
		if(orderAddress==null)
			throw new ServiceException("参数为空,操作收货地址失败");
		TShPersonAddress tspa = null;
		if(StringHelper.isNotEmpty(orderAddress.getAddressId()))
			tspa = tShPersonAddressManager.findUniqueBy("id", orderAddress.getAddressId());
		
		TShOrderAddress tShOrderAddress = tShOrderAddressManager.findUniqueBy("orderId", orderAddress.getOrderId());
		TShOrderAddress tsoa = new TShOrderAddress();
		if(StringHelper.isNotEmpty(orderAddress.getAddressSelf()))
			tsoa.setAddressSelf(orderAddress.getAddressSelf());
		if(orderAddress.getIsDelivery()!=null)
			tsoa.setIsDelivery(orderAddress.getIsDelivery());
		else
			tsoa.setIsDelivery(Constants.STATUS_NO_VALID);
		
		if(orderAddress.getIsSelf()!=null)
			tsoa.setIsSelf(orderAddress.getIsSelf());
		else
			tsoa.setIsSelf(Constants.STATUS_NO_VALID);
		
		if(orderAddress.getIsSend()!=null)
			tsoa.setIsSend(orderAddress.getIsSend());
		else
			tsoa.setIsSend(Constants.STATUS_NO_VALID);
		
		if(StringHelper.isNotEmpty(orderAddress.getAddressId())){
			tsoa.setOrderId(orderAddress.getOrderId());
			tsoa.setShAddress(tspa.getShrXxdz());
			tsoa.setShrName(tspa.getShrName());
			tsoa.setShrPhone(tspa.getShrName());
		}
		
		if(tShOrderAddress==null){
			//insert
			tsoa.setId(IdGenerator.getInstance().getUniqTime()+"");
			tShOrderAddressManager.save(tsoa);
		}else{
			//update
			tShOrderAddressManager.update(tsoa);
		}
	}

}

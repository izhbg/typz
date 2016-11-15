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
import com.izhbg.typz.shop.goods.dto.TShGoodsImage;
import com.izhbg.typz.shop.goods.dto.TShGoodsPrice;
import com.izhbg.typz.shop.goods.manager.TShGoodsBasicManager;
import com.izhbg.typz.shop.goods.service.TShGoodsImageService;
import com.izhbg.typz.shop.goods.service.TShGoodsPriceService;
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
import com.izhbg.typz.shop.store.dto.TShStore;
import com.izhbg.typz.shop.store.manager.TShStoreManager;
@Service("tShOrderService")
public class TShOrderServiceImpl implements TShOrderService {
	
	@Autowired
	private TShOrderManager tShOrdermanager;
	@Autowired
	private TShOrderGoodManager tShOrderGoodsManager;
	@Autowired
	private TShGoodsImageService tShGoodsImageService;
	@Autowired
	private TShOrderAddressManager tShOrderAddressManager;
	@Autowired
	private TShStoreManager tShStoreManager;
	@Autowired
	private TShGoodsBasicManager tShGoodsBasicManager;
	@Autowired
	private TShPersonAddressManager tShPersonAddressManager;
	@Autowired
	private TShGoodsPriceService tShGoodsPriceService;
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
		entity.setScBj(Constants.UN_DELETE_STATE);
		tShOrdermanager.save(entity);
		
		String goodsInfo = entity.getGoodsInfo();
		String[] goodsinfos = goodsInfo.split("\\$");
		String goodsId = null;
		String num = null;
		String price = null;
		TShOrderGood tsog = null;
		TShGoodsPrice tsgp = null;
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
			tsgp = tShGoodsPriceService.queryGuidByGoodsIdAndVersion(goodsId, Constants.GOODS_VERSION_DEFAULT);
			if(tsgp!=null)
			tsog.setGuidPrice(tsgp.getPrice());
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
		TShGoodsImage tShGoodsImage = null;
		TShGoodsPrice tShGoodsPrice = null;
		if(tShOrderGoods!=null)
			for(TShOrderGood tsog:tShOrderGoods){
				tsgb = tShGoodsBasicManager.findUniqueBy("id", tsog.getGoodsId());
				if(tsgb!=null){
					tShGoodsImage = tShGoodsImageService.getIndexImage(tsgb.getId(), tsgb.getVersion());
					 if(tShGoodsImage!=null){
						 tsgb.setIndexImage(tShGoodsImage);
					 }
					tsgb.setGoodsNum(tsog.getNum());
					tShGoodsPrice = tShGoodsPriceService.querySaleByGoodsIdAndVersion(tsgb.getId(), tsgb.getVersion());
					if(tShGoodsPrice!=null)
						tsgb.setPrice(tShGoodsPrice.getPrice());
					tShGoodsBasics.add(tsgb);
				}
			}
		to.settShGoodsList(tShGoodsBasics);
		return to;
	}

	@Override
	public Page pageList(Page page) throws Exception {
		Map map = new HashMap<String, Object>();
		page = tShOrdermanager.pagedQuery(" from TShOrder", page.getPageNo(), page.getPageSize(), map);
		return this.mapPage(page);
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
	/**
	 * 订单确认
	 */
	@Override
	public List<TShOrder> orderConfirm(String[] orderIds) throws Exception {
		if(orderIds==null)
			throw new ServiceException("参数为空，确认订单失败");
		List<TShOrder> orders = new ArrayList<>();
		TShOrder order = null;
		for(String orderId:orderIds){
			order = this.getById(orderId);
			if(order!=null)
				orders.add(order);
		}
		return orders;
	}
	
	@Override
	public Page getMemberOrder(Page page, String memberId, Integer status) throws Exception {
		if(StringHelper.isEmpty(memberId))
			throw new ServiceException("参数为空，操作失败");
		Map<String,Object> map = new HashMap<String, Object>();
		String hql = " from TShOrder ";
		if(StringHelper.isNotEmpty(memberId)){
			hql = hql+" where yhId=:yhId and scBj=:scBj";
			map.put("scBj", Constants.UN_DELETE_STATE);
			map.put("yhId", memberId);
		}
		if(status!=null){
			hql = hql+" and status=:status";
			map.put("status", status);
		}
		page = tShOrdermanager.pagedQuery(hql, page.getPageNo(), page.getPageSize(), map);
		page = initOrderInfo(page);
		return page;
	}
	
	@Override
	public Page getStoreOrder(Page page, String storeId, Integer status) throws Exception {
		if(StringHelper.isEmpty(storeId))
			throw new ServiceException("参数为空，操作失败");
		Map<String,Object> map = new HashMap<String, Object>();
		String hql = " from TShOrder ";
		if(StringHelper.isNotEmpty(storeId)){
			hql = hql+" where storeId=:storeId";
			map.put("storeId", storeId);
		}
		if(status!=null){
			hql = hql+" and status=:status";
			map.put("status", status);
		}
		page = tShOrdermanager.pagedQuery(hql, page.getPageNo(), page.getPageSize(), map);
		page = initOrderInfo(page);
		return page;
	}
	
	/**
	 * 初始化订单
	 * @param page
	 * @return
	 */
	private Page initOrderInfo(Page page) throws Exception{
		List<TShOrder> tShOrders = (List<TShOrder>) page.getResult();
		
		List<TShOrderGood> tShOrderGoods = null;
		TShGoodsBasic tShGoodsBasic;
		TShStore tss = null;
		List<TShGoodsBasic> tShGoodsList = null;
		if(tShOrders!=null)
			for(TShOrder tor:tShOrders){
				//获取订单店铺
				tss = tShStoreManager.findUniqueBy("id", tor.getStoreId());
				if(tss!=null)
					tor.settShStore(tss);
				tShOrderGoods = tShOrderGoodsManager.findBy("orderId", tor.getId());
				if(tShOrderGoods!=null){
					tShGoodsList = new ArrayList<>();
					for(TShOrderGood tog:tShOrderGoods){
						
						tShGoodsBasic = tShGoodsBasicManager.findUniqueBy("id", tog.getGoodsId());
						if(tShGoodsBasic!=null){
							tShGoodsBasic.setPrice(tog.getPrice());
							TShGoodsImage tShGoodsImage = tShGoodsImageService.getIndexImage(tog.getGoodsId(), Constants.GOODS_VERSION_DEFAULT);
							if(tShGoodsImage!=null)
								tShGoodsBasic.setIndexImage(tShGoodsImage);
							tShGoodsBasic.setGoodsNum(tog.getNum());
						}
						tShGoodsList.add(tShGoodsBasic);
					}
					tor.settShGoodsList(tShGoodsList);
				}
			}
		page.setResult(tShOrders);
		return page;
	}
	@Override
	public void setOrderStatus(String orderId, Integer status) throws Exception {
		if(StringHelper.isEmpty(orderId))
			throw new ServiceException("参数为空，操作失败");
		TShOrder order = tShOrdermanager.findUniqueBy("id", orderId);
		if(order==null)
			throw new ServiceException("参数有误，操作失败");
		order.setStatus(status);
		tShOrdermanager.update(order);
	}
	/**
	 * 
	 * @param page
	 * @return
	 */
	private Page mapPage(Page page){
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

}

package com.izhbg.typz.shop.purchase.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.shop.goods.dto.TShGoodsBasic;
import com.izhbg.typz.shop.goods.dto.TShGoodsDelivery;
import com.izhbg.typz.shop.goods.dto.TShGoodsImage;
import com.izhbg.typz.shop.goods.dto.TShGoodsPrice;
import com.izhbg.typz.shop.goods.manager.TShGoodsBasicManager;
import com.izhbg.typz.shop.goods.manager.TShGoodsDeliveryManager;
import com.izhbg.typz.shop.goods.manager.TShGoodsImageManager;
import com.izhbg.typz.shop.goods.manager.TShGoodsPriceManager;
import com.izhbg.typz.shop.purchase.dto.Purchase;
import com.izhbg.typz.shop.purchase.dto.PurchaseGoods;
import com.izhbg.typz.shop.purchase.dto.PurchaseStore;
import com.izhbg.typz.shop.purchase.dto.TShPurchase;
import com.izhbg.typz.shop.purchase.manager.TShPurchaseManager;
import com.izhbg.typz.shop.purchase.service.TShPurchaseService;
import com.izhbg.typz.shop.store.dto.TShStore;
import com.izhbg.typz.shop.store.manager.TShStoreManager;

@Service("tShPurchaseService")
public class TShPurchaseServiceImpl implements TShPurchaseService{

	@Autowired
	private TShPurchaseManager tShPurchaseManager;
	@Autowired
	private TShStoreManager tsShStoreManager;
	@Autowired
	private TShGoodsBasicManager tShGoodsBasicManager;
	@Autowired
	private TShGoodsImageManager tShGoodsImageManager;
	@Autowired
	private TShGoodsDeliveryManager tShGoodsDeliveryManager;
	@Autowired
	private TShGoodsPriceManager tShGoodsPriceManager;
	
	
	private BeanMapper beanMapper = new BeanMapper();
	
	@Override
	public void add(TShPurchase entity) throws Exception {
		if(entity==null)
			throw new ServiceException("参数为空，添加购物车失败");
		if(entity.getNum()==0)
			throw new ServiceException("参数不全，添加购物车失败");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("yhId", entity.getYhId());
		map.put("goodsId", entity.getGoodsId());
		TShPurchase pur = tShPurchaseManager.findUnique("from TShPurchase where yhId=:yhId and goodsId=:goodsId", map);
		if(pur!=null){
			pur.setNum(entity.getNum()+pur.getNum());
			tShPurchaseManager.update(pur);
		}else{
			entity.setAddTime(new Date());
			tShPurchaseManager.save(entity);
		}
	}

	@Override
	public void update(TShPurchase entity) throws Exception {
		TShPurchase tShPurchase = tShPurchaseManager.findUniqueBy("id", entity.getId());
		beanMapper.copy(entity, tShPurchase);
		tShPurchaseManager.update(tShPurchase);
	}

	@Override
	public void delete(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,删除购物车信息失败");
		tShPurchaseManager.removeById(id);
	}

	@Override
	public void deleteBatche(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空,批量删除购物车信息失败");
	}

	@Override
	public TShPurchase getById(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空,获取购物车信息失败");
		return tShPurchaseManager.findUniqueBy("id", id);
	}

	@Override
	public Page pageList(Page page) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TShPurchase> getAll() throws Exception {
		return tShPurchaseManager.getAll();
	}

	@Override
	public Purchase getPurchaseByYhId(String yhId) throws Exception {
		
		List<Map<String,String>> list = tShPurchaseManager.find(" select DISTINCT(store_id) from t_sh_purchase where yh_id='"+yhId+"' ");
		Purchase purchase = new Purchase();
		if(list==null||list.size()==0) 
			return null;
		else{
			int totalShopGoodsNum = 0;
			Double totalPrice = 0.00;
			List<PurchaseStore> purchaseStoreList = new ArrayList<PurchaseStore>();
			
			//puchase store
			PurchaseStore purchaseStore  = null;
			String storeId = null;
			TShStore store = null;
			List<PurchaseGoods> purchaseGoodsList = null;
			PurchaseGoods purchaseGoods;
			int totalGoodsNum = 0;
			
			
			//goodsStore
			TShGoodsBasic goods = null;
			
			Map<String,Object> map_temp = new HashMap<String,Object>();
			List<TShPurchase> purchases = null;
			TShGoodsImage goodsImage = null;
			TShGoodsDelivery goodsDelivery = null;
			TShGoodsPrice tShGoodsprice = null;
			for(Map<String,String> map:list) {
				storeId = map.get("store_id")+"";
				store = tsShStoreManager.findUniqueBy("id", storeId);
				if(store!=null) {
					purchaseGoodsList = new ArrayList<PurchaseGoods>();
					purchaseStore = new PurchaseStore();
					totalGoodsNum = 0;
					map_temp.clear();
					map_temp.put("yhId", yhId);
					map_temp.put("storeId", store.getId());
					purchases = tShPurchaseManager.find(" from TShPurchase where yhId=:yhId and storeId=:storeId", map_temp);
					if(purchases!=null) {
						for(TShPurchase pu:purchases) {
							goods = tShGoodsBasicManager.findUniqueBy("id", pu.getGoodsId());
							if(goods!=null) {
								//缩略图
								map_temp.clear();
								map_temp.put("type", Constants.ATTACHE_INDEX);
								map_temp.put("goodsId", goods.getId());
								goodsImage = tShGoodsImageManager.findUnique("from TShGoodsImage type=:type and goodsId=:goodsId", map_temp);
								goods.setIndexImage(goodsImage);
								
								//价格
								map_temp.clear();
								map_temp.put("priceType", Constants.PRICE_DSCUT);
								map_temp.put("goodsId", goods.getId());
								tShGoodsprice = tShGoodsPriceManager.findUnique(" from TShGoodsPrice where goodsId=:goodsId and priceType=:priceType", map_temp);
								if(tShGoodsprice!=null)
									goods.setPrice(tShGoodsprice.getPrice());
								else
									goods.setPrice(0);
								goodsDelivery = tShGoodsDeliveryManager.findUniqueBy("goodsId", goods.getId());
								if(goodsDelivery!=null)
									goods.settShGoodsDelivery(goodsDelivery);
								purchaseGoods = new PurchaseGoods();
								purchaseGoods.setGoods(goods);
								purchaseGoods.setNum(pu.getNum());
								totalGoodsNum = totalGoodsNum+pu.getNum();
								purchaseGoodsList.add(purchaseGoods);
								
								totalPrice = totalPrice+(goods.getPrice()*pu.getNum());
								totalShopGoodsNum = totalShopGoodsNum+pu.getNum();
							}
						}
					}
					
					purchaseStore.setTotalGoodsNum(totalGoodsNum);
					purchaseStore.setStore(store);
					purchaseStore.setGoodsList(purchaseGoodsList);
					purchaseStoreList.add(purchaseStore);
				}
				
			}
			purchase.setPurchaseStoreList(purchaseStoreList);
			purchase.setTotalPrice(totalPrice);
			purchase.setTotalShopGoodsNum(totalShopGoodsNum);
		}
		return purchase;
	}

}

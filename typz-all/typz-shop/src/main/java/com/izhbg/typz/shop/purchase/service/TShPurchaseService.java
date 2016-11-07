package com.izhbg.typz.shop.purchase.service;

import com.izhbg.typz.shop.common.service.TShBaseService;
import com.izhbg.typz.shop.purchase.dto.Purchase;
import com.izhbg.typz.shop.purchase.dto.TShPurchase;

public interface TShPurchaseService  extends TShBaseService<TShPurchase>{
	/**
	 * 获取个人 购物车
	 * @param yhId
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Purchase getPurchaseByYhId(String yhId) throws Exception;
	/**
	 * 获取个人购物车数量
	 * @param yhId
	 * @return
	 * @throws Exception
	 */
	public int getPersonPurchaseNum(String yhId) throws Exception;
	/**
	 * 个人购物车减1动作
	 * @param yhId
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public int reduce(String yhId,String goodsId) throws Exception;
}

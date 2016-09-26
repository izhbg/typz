package com.izhbg.typz.shop.purchase.service;

import com.izhbg.typz.shop.common.service.BaseService;
import com.izhbg.typz.shop.purchase.dto.Purchase;
import com.izhbg.typz.shop.purchase.dto.TShPurchase;

public interface TShPurchaseService  extends BaseService<TShPurchase>{
	/**
	 * 获取个人 购物车
	 * @param yhId
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Purchase getPurchaseByYhId(String yhId) throws Exception;
}

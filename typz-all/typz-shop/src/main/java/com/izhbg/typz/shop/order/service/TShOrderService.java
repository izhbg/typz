package com.izhbg.typz.shop.order.service;

import com.izhbg.typz.shop.common.service.BaseService;
import com.izhbg.typz.shop.order.dto.OrderAddress;
import com.izhbg.typz.shop.order.dto.TShOrder;

public interface TShOrderService extends BaseService<TShOrder>{
	/**
	 * 添加或更新
	 * @param tShOrderAddress
	 * @throws Exception
	 */
	public void addOrUpdateOrderAddress(OrderAddress orderAddress) throws Exception;
}

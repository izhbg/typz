package com.izhbg.typz.shop.order.service;

import java.util.List;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.shop.common.service.TShBaseService;
import com.izhbg.typz.shop.order.dto.OrderAddress;
import com.izhbg.typz.shop.order.dto.TShOrder;

public interface TShOrderService extends TShBaseService<TShOrder>{
	/**
	 * 添加或更新
	 * @param tShOrderAddress
	 * @throws Exception
	 */
	public void addOrUpdateOrderAddress(OrderAddress orderAddress) throws Exception;
	/**
	 * 订单确认
	 * @param orderIds
	 * @return
	 * @throws Exception
	 */
	public List<TShOrder> orderConfirm(String[] orderIds) throws Exception;
	/**
	 * 获取会员订单列表
	 * @param page
	 * @param memberId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public Page getMemberOrder(Page page,String memberId,Integer status) throws Exception;
	/**
	 * 获取店铺订单列表
	 * @param page
	 * @param storeId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public Page getStoreOrder(Page page,String storeId,Integer status) throws Exception;
	/**
	 * 更改订单状态
	 * @param orderId
	 * @param status
	 * @throws Exception
	 */
	public void setOrderStatus(String orderId,Integer status) throws Exception;
}

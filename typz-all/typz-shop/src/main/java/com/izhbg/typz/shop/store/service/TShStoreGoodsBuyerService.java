package com.izhbg.typz.shop.store.service;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.shop.common.service.TShBaseService;
import com.izhbg.typz.shop.store.dto.TShStoreGoodsBuyer;

public interface TShStoreGoodsBuyerService extends TShBaseService<TShStoreGoodsBuyer> {
	/**
	 * 添加 进货产品
	 * @param storeId
	 * @param checkdel
	 * @throws Exception
	 */
	public void addBuyerGoods(String storeId,String[] checkdel) throws Exception;
	/**
	 * 分页查询
	 * @param page
	 * @param storeId
	 * @return
	 * @throws Exception
	 */
	public Page pageList(Page page,String storeId) throws Exception;
}

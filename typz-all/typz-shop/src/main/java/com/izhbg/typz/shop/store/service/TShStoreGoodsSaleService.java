package com.izhbg.typz.shop.store.service;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.shop.common.service.TShBaseService;
import com.izhbg.typz.shop.store.dto.TShStoreGoodsSale;

public interface TShStoreGoodsSaleService extends TShBaseService<TShStoreGoodsSale> {
	/**
	 * 添加 销售产品
	 * @param storeId
	 * @param checkdel
	 * @throws Exception
	 */
	public void addSaleGoods(String storeId,String[] checkdel) throws Exception;
	/**
	 * 分页查询
	 * @param page
	 * @param storeId
	 * @return
	 * @throws Exception
	 */
	public Page pageList(Page page,String storeId) throws Exception;
}

package com.izhbg.typz.shop.store.service;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.shop.common.service.BaseService;
import com.izhbg.typz.shop.store.dto.TShStore;

public interface TShStoreService extends BaseService<TShStore> {
	/**
	 * 审核未通过
	 * @param tShStore
	 * @throws Exception
	 */
	public void verifyPassError(String[] checkdel) throws Exception;
	
	/**
	 * 审核通过
	 * @param tShStore
	 * @throws Exception
	 */
	public void verifyPassOn(String[] checkdel) throws Exception;
	/**
	 * 分页查询
	 * @param page
	 * @param tShStore
	 * @return
	 * @throws Exception
	 */
	public Page pageList(Page page,TShStore tShStore) throws Exception;
}

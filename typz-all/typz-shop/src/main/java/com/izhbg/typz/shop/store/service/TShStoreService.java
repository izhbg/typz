package com.izhbg.typz.shop.store.service;

import com.izhbg.typz.shop.common.service.BaseService;
import com.izhbg.typz.shop.store.dto.TShStore;

public interface TShStoreService extends BaseService<TShStore> {
	/**
	 * 审核未通过
	 * @param tShStore
	 * @throws Exception
	 */
	public void verifyPassError(TShStore tShStore) throws Exception;
	
	/**
	 * 审核通过
	 * @param tShStore
	 * @throws Exception
	 */
	public void verifyPassOn(TShStore tShStore) throws Exception;
}

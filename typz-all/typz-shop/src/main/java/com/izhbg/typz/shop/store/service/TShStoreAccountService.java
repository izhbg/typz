package com.izhbg.typz.shop.store.service;

import java.util.List;

import com.izhbg.typz.shop.common.service.BaseService;
import com.izhbg.typz.shop.store.dto.TShStoreAccount;

public interface TShStoreAccountService extends BaseService<TShStoreAccount> {
	/**
	 * 获取店铺 账户
	 * @param storeId
	 * @return
	 * @throws Exception
	 */
	public List<TShStoreAccount> getStoreAccounts(String storeId) throws Exception;
}

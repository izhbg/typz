package com.izhbg.typz.shop.store.service;

import java.util.List;

import com.izhbg.typz.shop.common.service.TShBaseService;
import com.izhbg.typz.shop.store.dto.TShStoreAccount;

public interface TShStoreAccountService extends TShBaseService<TShStoreAccount> {
	/**
	 * 获取店铺 账户
	 * @param storeId
	 * @return
	 * @throws Exception
	 */
	public List<TShStoreAccount> getStoreAccounts(String storeId) throws Exception;
	/**
	 * 根据会员ID获取会员账户
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public TShStoreAccount getByMemberId(String memberId) throws Exception;
}

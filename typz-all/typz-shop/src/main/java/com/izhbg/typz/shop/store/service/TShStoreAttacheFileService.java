package com.izhbg.typz.shop.store.service;

import java.util.List;

import com.izhbg.typz.shop.common.service.BaseService;
import com.izhbg.typz.shop.store.dto.TShStoreAttachefile;

public interface TShStoreAttacheFileService extends BaseService<TShStoreAttachefile>{
	
	/**
	 * 店铺其他附件
	 * @param storeId
	 * @return
	 * @throws Exception
	 */
	public List<TShStoreAttachefile> getStoreAttacheFile(String storeId) throws Exception;
	
	/**
	 * 店铺 头像 LOGO
	 * @param storeId
	 * @return
	 * @throws Exception
	 */
	public TShStoreAttachefile getIndexAttacheFile(String storeId) throws Exception;
	
	
}

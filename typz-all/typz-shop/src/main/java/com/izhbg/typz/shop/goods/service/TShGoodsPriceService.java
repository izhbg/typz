package com.izhbg.typz.shop.goods.service;

import com.izhbg.typz.shop.common.service.TShBaseService;
import com.izhbg.typz.shop.goods.dto.TShGoodsPrice;

public interface TShGoodsPriceService extends TShBaseService<TShGoodsPrice> {
	/**
	 * 获取商品信息
	 * @param id
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public TShGoodsPrice querySaleByGoodsIdAndVersion(String id,Integer version) throws Exception;
	/**
	 * 获取指导价
	 * @param id
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public TShGoodsPrice queryGuidByGoodsIdAndVersion(String id,Integer version) throws Exception;
}

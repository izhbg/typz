package com.izhbg.typz.shop.goods.service;

import com.izhbg.typz.shop.goods.dto.TShGoodsPrice;

public interface TShGoodsPriceService extends BaseService<TShGoodsPrice> {
	/**
	 * 获取商品信息
	 * @param id
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public TShGoodsPrice queryByGoodsIdAndVersion(String id,Integer version) throws Exception;
}

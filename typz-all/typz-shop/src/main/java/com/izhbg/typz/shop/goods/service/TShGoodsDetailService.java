package com.izhbg.typz.shop.goods.service;

import com.izhbg.typz.shop.common.service.TShBaseService;
import com.izhbg.typz.shop.goods.dto.TShGoodsDetail;

public interface TShGoodsDetailService extends TShBaseService<TShGoodsDetail> {
	/**
	 * 获取商品详情
	 * @param name
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public TShGoodsDetail queryBy(String name,String value) throws Exception;
	
	/**
	 * 获取商品信息
	 * @param id
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public TShGoodsDetail queryByGoodsIdAndVersion(String id,Integer version) throws Exception;
	
}

package com.izhbg.typz.shop.goods.service;

import java.util.List;

import com.izhbg.typz.shop.common.service.BaseService;
import com.izhbg.typz.shop.goods.dto.TShGoodsImage;

public interface TShGoodsImageService extends BaseService<TShGoodsImage>{
	
	/**
	 * 获取商品信息
	 * @param id
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public List<TShGoodsImage> queryByGoodsIdAndVersion(String id,Integer version) throws Exception;
	/**
	 * 获取商品图片
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public List<TShGoodsImage> findByGoodsId(String goodsId) throws Exception;
	
	/**
	 * 获取图片缩略图
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public TShGoodsImage getIndexImage(String goodsId, Integer version) throws Exception;
}

package com.izhbg.typz.shop.goods.service;

public interface TShGoodsTagService{
	/**
	 * 设置商品标签
	 * @param tagId
	 * @param goodsIds
	 * @param xh
	 * @throws Exception
	 */
	public void setTag(String tagId,String[] goodsIds,int xh) throws Exception;
	/**
	 * 删除产品标签
	 * @param tagId
	 * @param goodsId
	 * @throws Exception
	 */
	public void delTag(String tagId,String goodsId) throws Exception;
}

package com.izhbg.typz.shop.goods.service;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.shop.common.service.TShBaseService;
import com.izhbg.typz.shop.goods.dto.GoodsCommentCount;
import com.izhbg.typz.shop.goods.dto.TShGoodsComment;

public interface TShGoodsCommentService extends TShBaseService<TShGoodsComment> {
	/**
	 * 获取产品评价
	 * @param page
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public Page getListByGoodsId(Page page,String goodsId) throws Exception;
	/**
	 * 产品评价数
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public GoodsCommentCount getCountByGoodsId(String goodsId) throws Exception;
	/**
	 * 订单是否已评价
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	public boolean isCommonent(String orderId,String goodsId) throws Exception;
}

package com.izhbg.typz.shop.goods.service;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.shop.common.service.BaseService;
import com.izhbg.typz.shop.goods.dto.TShGoods;
import com.izhbg.typz.shop.goods.dto.TShGoodsBasic;

/**
 * 
* @ClassName: TShGoodsBasicService 
* @Description: 商品基本信息服务
* @author caixl 
* @date 2016-6-16 上午9:49:30 
*
 */
public interface TShGoodsBasicService extends BaseService<TShGoodsBasic> {
	/**
	 * 获取产品的最大版本号
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public Integer getMaxVersionByGoodsId(String goodsId) throws Exception;
	/**
	 * 获取商品信息
	 * @param id
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public TShGoodsBasic queryByGoodsIdAndVersion(String id,Integer version) throws Exception;
	/**
	 * 添加或更新商品
	 * @param tShGoods
	 * @throws Exception
	 */
	public void addOrUpdateGoods(TShGoods tShGoods) throws Exception;
	/**
	 * 分页查询
	 * @param page
	 * @param tShGoods
	 * @return
	 * @throws Exception
	 */
	public Page pageList(Page page,TShGoods tShGoods) throws Exception;
	/**
	 * 下架商品
	 * @param goodsId
	 * @throws Exception
	 */
	public void underGoods(String goodsId) throws Exception;
	/**
	 * 批量下架
	 * @param goodsIds
	 * @throws Exception
	 */
	public void batchUnderGoods(String[] goodsIds) throws Exception;
	/**
	 * 上架
	 * @param goodsId
	 * @throws Exception
	 */
	public void upGoods(String goodsId) throws Exception;
	/**
	 * 批量上架
	 * @param goodsIds
	 * @throws Exception
	 */
	public void batchUpGoods(String[] goodsIds) throws Exception;
	/**
	 * 批量恢复
	 * @param ids
	 * @throws Exception
	 */
	public void recoverBatche(String[] ids) throws Exception;
}

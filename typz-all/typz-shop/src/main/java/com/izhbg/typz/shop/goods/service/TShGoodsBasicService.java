package com.izhbg.typz.shop.goods.service;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.shop.common.service.TShBaseService;
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
public interface TShGoodsBasicService extends TShBaseService<TShGoodsBasic> {
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
	 * 店铺分页查询
	 * @param page
	 * @param tShGoods
	 * @return
	 * @throws Exception
	 */
	public Page pageStoreList(Page page,TShGoods tShGoods) throws Exception;
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
	
	/**
	 * 给商品定销售价
	 * @param goodsId
	 * @throws Exception
	 */
	public void setSalePrice(String goodsId,double price) throws Exception;
	/**
	 * 设置推广价 和推广者获利比例
	 * @param price
	 * @param percent
	 * @throws Exception
	 */
	public void setCostPrice(String goodsId,double price) throws Exception;
	/**
	 * 设置产品推广者获利比例
	 * @param goodsId
	 * @param percent
	 * @throws Exception
	 */
	public void setPercent(String goodsId,String percent) throws Exception;
	
}

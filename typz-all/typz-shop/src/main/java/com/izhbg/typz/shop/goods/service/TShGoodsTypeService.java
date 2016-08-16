package com.izhbg.typz.shop.goods.service;

import java.util.List;

import com.izhbg.typz.shop.goods.dto.TShGoodsType;
/**
 * 
* @ClassName: TShGoodsTypeService 
* @Description: 产品类目 接口
* @author caixl 
* @date 2016-6-29 上午11:14:57 
*
 */
public interface TShGoodsTypeService extends BaseService<TShGoodsType> {
	/**
	 * 根据父级节点ID获取 类型列表
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	public List<TShGoodsType> getListByPid(String pid) throws Exception;
}

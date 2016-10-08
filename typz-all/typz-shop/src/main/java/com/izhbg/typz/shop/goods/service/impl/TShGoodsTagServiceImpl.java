package com.izhbg.typz.shop.goods.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.shop.goods.dto.TShGoodsTag;
import com.izhbg.typz.shop.goods.manager.TShGoodsTagManager;
import com.izhbg.typz.shop.goods.service.TShGoodsTagService;

@Service("tShGoodsTagService")
public class TShGoodsTagServiceImpl implements TShGoodsTagService{
	
	@Autowired
	private TShGoodsTagManager tShGoodsTagManager;
	
	/**
	 * 设置产品标签
	 */
	@Override
	public void setTag(String tagId, String[] goodsIds, int xh) throws Exception {
		if(StringHelper.isEmpty(tagId)||goodsIds==null)
			throw new ServiceException("参数为空,设置标签失败");
		Map<String,Object> map = new HashMap<>();
		TShGoodsTag goodsTag;
		for(String goodsId:goodsIds) {
			map.clear();
			map.put("goodsId", goodsId);
			map.put("tagId", tagId);
			goodsTag = tShGoodsTagManager.findUnique(" from TShGoodsTag where goodsId=:goodsId and tagId=:tagId ", map);
			if(goodsTag==null) {
				goodsTag = new TShGoodsTag();
				goodsTag.setId(IdGenerator.getInstance().getUniqTime()+"");
				goodsTag.setGoodsId(goodsId);
				goodsTag.setTagId(tagId);
				goodsTag.setXh(xh);
				tShGoodsTagManager.save(goodsTag);
			}else {
				goodsTag.setXh(xh);
				tShGoodsTagManager.update(goodsTag);
			}
		}
	}

}

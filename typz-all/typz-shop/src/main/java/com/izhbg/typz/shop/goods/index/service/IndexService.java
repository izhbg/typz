package com.izhbg.typz.shop.goods.index.service;

import java.util.List;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.shop.goods.dto.TShGoodsTags;
/**
 * 
* @author xiaolong.cai@mtime.com
* @date 2016年10月5日 下午8:19:24 
* @version V1.0
 */
public interface IndexService {
	/**
	 * 获取首页数据
	 * @param la
	 * @param lo
	 * @return
	 * @throws Exception
	 */
	public List<TShGoodsTags> dashboard(double la,double lo) throws Exception;
	
	/**
	 * 二级页面
	 * @param tagId
	 * @return
	 * @throws Exception
	 */
	public TShGoodsTags secondPage(String tagId,double la, double lo,Page page) throws Exception;
}

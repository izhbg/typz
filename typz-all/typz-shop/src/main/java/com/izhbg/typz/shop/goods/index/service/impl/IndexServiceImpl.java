package com.izhbg.typz.shop.goods.index.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izhbg.typz.shop.goods.dto.TShGoodsBasic;
import com.izhbg.typz.shop.goods.dto.TShGoodsImage;
import com.izhbg.typz.shop.goods.dto.TShGoodsTags;
import com.izhbg.typz.shop.goods.index.service.IndexService;
import com.izhbg.typz.shop.goods.manager.TShGoodsTagManager;
import com.izhbg.typz.shop.goods.manager.TShGoodsTagsManager;
import com.izhbg.typz.shop.goods.service.TShGoodsBasicService;
import com.izhbg.typz.shop.goods.service.TShGoodsImageService;

@Service("indexService")
public class IndexServiceImpl implements IndexService{

	@Autowired
	private TShGoodsTagsManager tShGoodsTagsManager;
	@Autowired
	private TShGoodsTagManager tShGoodsTagManager;
	@Autowired
	private TShGoodsBasicService tShGoodsBasicService;
	@Autowired
	private TShGoodsImageService tShGoodsImageService;
	
	/**
	 * 获取首页数据
	 */
	@Override
	public List<TShGoodsTags> dashboard(double la, double lo) throws Exception {
		if(la==0.0&&lo==0.0)
			throw new ServiceException("参数为空,操作失败");
		List<TShGoodsTags> tsGoodsTags = tShGoodsTagsManager.getAll();
		for(TShGoodsTags tt:tsGoodsTags){
			//获取每个分类下面 距离最新的 3个产品
			tt = this.getTShGoodsTags(tt, la, lo);
		}
		return tsGoodsTags;
	}
	/**
	 * 组装 标记
	 * @param tt
	 * @param la
	 * @param lo
	 * @return
	 * @throws Exception
	 */
	private TShGoodsTags getTShGoodsTags(TShGoodsTags tt,double la,double lo) throws Exception{
		String sql = " SELECT " + 
				"	tsgb.id," + 
				"	ROUND(" + 
				"		6378.138 * 2 * ASIN(" + 
				"			SQRT(" + 
				"				POW(" + 
				"					SIN(" + 
				"						(" + la+
				"							 * PI() / 180 - tss.la * PI() / 180" + 
				"						) / 2" + 
				"					)," + 
				"					2" + 
				"				) + COS("+la+" * PI() / 180) * COS(tss.la * PI() / 180) * POW(" + 
				"					SIN(" + 
				"						(" + lo +
				"							 * PI() / 180 - tss.lo * PI() / 180" + 
				"						) / 2" + 
				"					)," + 
				"					2" + 
				"				)" + 
				"			)" + 
				"		) * 1000" + 
				"	) AS juli" + 
				" FROM" + 
				"	t_sh_goods_tag tst,t_sh_goods_basic tsgb,t_sh_store tss " + 
				" where tag_id='"+tt.getId()+"' and tst.goods_id=tsgb.id and tsgb.create_user=tss.yh_id " + 
				" ORDER BY" + 
				" juli desc";
		  sql+= "  limit 0,3";
		 List<Map<String,Object>> list = tShGoodsTagManager.getJdbcTemplate().queryForList(sql);
		 
		 List<TShGoodsBasic> goodsList = new ArrayList<TShGoodsBasic>();
		 
		 TShGoodsBasic tShGoodsBasic = null;
		 TShGoodsImage tShGoodsImage = null;
		 for(Map<String, Object> map_temp:list){
			 tShGoodsBasic = tShGoodsBasicService.getById(map_temp.get("id").toString());
			 if(tShGoodsBasic!=null){
				 tShGoodsImage = tShGoodsImageService.getIndexImage(tShGoodsBasic.getId(), tShGoodsBasic.getVersion());
				 if(tShGoodsImage!=null){
					 tShGoodsBasic.setIndexImage(tShGoodsImage);
				 }
				 goodsList.add(tShGoodsBasic);
			 }
			 tt.setTsGoodsBasics(goodsList);
		 }
		return tt;
	}
	/**
	 * 二级页面
	 */
	@Override
	public TShGoodsTags secondPage(String tagId,double la, double lo) throws Exception {
		TShGoodsTags tt = tShGoodsTagsManager.findUniqueBy("id", tagId);
		return this.getTShGoodsTags(tt, la, lo);
	}

}

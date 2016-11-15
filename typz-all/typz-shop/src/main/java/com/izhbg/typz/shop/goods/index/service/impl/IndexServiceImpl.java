package com.izhbg.typz.shop.goods.index.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.shop.goods.dto.TShGoodsBasic;
import com.izhbg.typz.shop.goods.dto.TShGoodsImage;
import com.izhbg.typz.shop.goods.dto.TShGoodsPrice;
import com.izhbg.typz.shop.goods.dto.TShGoodsTags;
import com.izhbg.typz.shop.goods.index.service.IndexService;
import com.izhbg.typz.shop.goods.manager.TShGoodsPriceManager;
import com.izhbg.typz.shop.goods.manager.TShGoodsTagManager;
import com.izhbg.typz.shop.goods.manager.TShGoodsTagsManager;
import com.izhbg.typz.shop.goods.service.TShGoodsBasicService;
import com.izhbg.typz.shop.goods.service.TShGoodsImageService;
import com.izhbg.typz.shop.goods.service.TShGoodsPriceService;

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
	@Autowired
	private TShGoodsPriceService tShGoodsPriceService;
	
	/**
	 * 获取首页数据
	 */
	@Override
	public List<TShGoodsTags> dashboard(double la, double lo) throws Exception {
		if(la==0.0&&lo==0.0)
			throw new ServiceException("参数为空,操作失败");
		List<TShGoodsTags> tsGoodsTags = tShGoodsTagsManager.getAll();
		Page page = new Page();
		page.setPageSize(3);
		for(TShGoodsTags tt:tsGoodsTags){
			//获取每个分类下面 距离最新的 3个产品
			tt = this.getTShGoodsTags(tt, la, lo,page);
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
	private TShGoodsTags getTShGoodsTags(TShGoodsTags tt,double la,double lo,Page page) throws Exception{
		String sql = " SELECT " + 
				"	tssgs.goods_id,tss.id store_id," + 
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
				"	t_sh_store_goods_sale tssgs,t_sh_store tss,t_sh_goods_tag tsgt,t_sh_goods_basic tsgb " + 
				" where tsgt.tag_id='"+tt.getId()+"' and tss.id=tssgs.store_id and tsgt.goods_id=tssgs.goods_id and tsgb.id=tssgs.goods_id and tsgb.del_status="+Constants.UN_DELETE_STATE+" and status=1 " + 
				" ORDER BY" + 
				" juli desc";
		  sql+= "  limit "+page.getStart()+","+page.getPageSize();
		 List<Map<String,Object>> list = tShGoodsTagManager.getJdbcTemplate().queryForList(sql);
		 
		 List<TShGoodsBasic> goodsList = new ArrayList<TShGoodsBasic>();
		 
		 TShGoodsBasic tShGoodsBasic = null;
		 TShGoodsImage tShGoodsImage = null;
		 TShGoodsPrice tShGoodsPrice = null;
		 Map<String, Object> map = new HashMap<>();
		 for(Map<String, Object> map_temp:list){
			 tShGoodsBasic = tShGoodsBasicService.getById(map_temp.get("goods_id").toString());
			 if(tShGoodsBasic!=null){
				 tShGoodsBasic.setStoreId(map_temp.get("store_id").toString());
				 tShGoodsImage = tShGoodsImageService.getIndexImage(tShGoodsBasic.getId(), tShGoodsBasic.getVersion());
				 if(tShGoodsImage!=null){
					 tShGoodsBasic.setIndexImage(tShGoodsImage);
				 }
				map.clear();
				map.put("goodsId", tShGoodsBasic.getId());
				map.put("priceType1", Constants.GOODS_PRICE_ORIGINAL);
				tShGoodsPrice = tShGoodsPriceService.querySaleByGoodsIdAndVersion(tShGoodsBasic.getId(),Constants.GOODS_VERSION_DEFAULT);
				if(tShGoodsPrice!=null)
					tShGoodsBasic.setPrice(tShGoodsPrice.getPrice());
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
	public TShGoodsTags secondPage(String tagId,double la, double lo,Page page) throws Exception {
		TShGoodsTags tt = tShGoodsTagsManager.findUniqueBy("id", tagId);
		return this.getTShGoodsTags(tt, la, lo,page);
	}

}

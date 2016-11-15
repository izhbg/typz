package com.izhbg.typz.im.goods.restf.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.shop.goods.dto.TShGoodsBasic;
import com.izhbg.typz.shop.goods.dto.TShGoodsDetail;
import com.izhbg.typz.shop.goods.dto.TShGoodsImage;
import com.izhbg.typz.shop.goods.dto.TShGoodsPrice;
import com.izhbg.typz.shop.goods.dto.TShGoodsTags;
import com.izhbg.typz.shop.goods.service.TShGoodsBasicService;
import com.izhbg.typz.shop.goods.service.TShGoodsDetailService;
import com.izhbg.typz.shop.goods.service.TShGoodsImageService;
import com.izhbg.typz.shop.goods.service.TShGoodsPriceService;
import com.izhbg.typz.shop.store.dto.TShStore;
import com.izhbg.typz.shop.store.dto.TShStoreAttachefile;
import com.izhbg.typz.shop.store.service.TShStoreAttacheFileService;
import com.izhbg.typz.shop.store.service.TShStoreService;
/**
 * 产品服务接口
* @author xiaolong.cai@mtime.com
* @date 2016年10月6日 下午4:15:42 
* @version V1.0
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/im/goods/")
public class GoodsController {
	
	@Autowired
	private TShGoodsBasicService tShGoodsBasicService;
	@Autowired
	private TShGoodsDetailService tShGoodsDetailService;
	@Autowired
	private TShGoodsImageService tShGoodsImageService;
	@Autowired
	private TShStoreService tShStoreService;
	@Autowired
	private TShGoodsPriceService tShGoodsPriceService;
	@Autowired
	private TShStoreAttacheFileService tShStoreAttacheFileService;
	/**
	 * 产品详情
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("goodsDetail")
	@ResponseBody
	public String goodsDetail(@RequestParam(value = "goodsId", required = true) String goodsId,
							  @RequestParam(value = "storeId", required = true) String storeId){
		String result = null;
		try {
			TShGoodsBasic tShGoodsBasic = tShGoodsBasicService.getById(goodsId);
			if(tShGoodsBasic!=null){
				tShGoodsBasic.setStoreId(storeId);
				/*TShGoodsDetail tShGoodsDetail = tShGoodsDetailService.getById(tShGoodsBasic.getId());
				if(tShGoodsDetail!=null)
					tShGoodsBasic.settShGoodsDetail(tShGoodsDetail);*/
				TShGoodsPrice price = tShGoodsPriceService.querySaleByGoodsIdAndVersion(tShGoodsBasic.getId(), Constants.GOODS_VERSION_DEFAULT);
				if(price!=null)
					tShGoodsBasic.setPrice(price.getPrice());
				List<TShGoodsImage> tShGoodsImages = tShGoodsImageService.findByGoodsId(tShGoodsBasic.getId());
				if(tShGoodsImages!=null)
					tShGoodsBasic.settShGoodsImages(tShGoodsImages);
				TShStore ts = tShStoreService.getById(tShGoodsBasic.getStoreId());
				if(ts!=null){
					TShStoreAttachefile tssa = tShStoreAttacheFileService.getIndexAttacheFile(ts.getId());
					if(tssa!=null)
						ts.setLogoAttache(tssa);
					tShGoodsBasic.setTsShStore(ts);
				}
				List<TShGoodsBasic> tShGoodsBasics = new ArrayList<>();
				tShGoodsBasics.add(tShGoodsBasic);
				BeanMapper beanMapper = new BeanMapper();
				com.izhbg.typz.im.goods.response.entity.TShGoodsBasic tShGoodsBasic_ = new com.izhbg.typz.im.goods.response.entity.TShGoodsBasic();
				List<com.izhbg.typz.im.goods.response.entity.TShGoodsBasic> list = beanMapper.copyList(tShGoodsBasics, com.izhbg.typz.im.goods.response.entity.TShGoodsBasic.class);
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,list==null?null:list.get(0));
			}else{
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, Constants.SYSTEMMSG_EMPTYFILED);
			}
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	
	@RequestMapping("goodsDscription")
	@ResponseBody
	public String goodsDscription(@RequestParam(value = "goodsId", required = true, defaultValue = "") String goodsId){
		String result = null;
		try {
			TShGoodsBasic tShGoodsBasic = tShGoodsBasicService.getById(goodsId);
			if(tShGoodsBasic!=null){
				TShGoodsDetail tShGoodsDetail = tShGoodsDetailService.getById(goodsId);
				if(tShGoodsDetail!=null)
					tShGoodsDetail.setGoodsName(tShGoodsBasic.getName());
				List<TShGoodsDetail> tShGoodsBasics = new ArrayList<>();
				tShGoodsBasics.add(tShGoodsDetail);
				BeanMapper beanMapper = new BeanMapper();
				List<com.izhbg.typz.im.goods.response.entity.TShGoodsDetail> tShGoodsDetails = beanMapper.copyList(tShGoodsBasics, com.izhbg.typz.im.goods.response.entity.TShGoodsDetail.class);
				
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,tShGoodsDetails==null?null:tShGoodsDetails.get(0));
			}else{
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, Constants.SYSTEMMSG_EMPTYFILED);
			}
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
}

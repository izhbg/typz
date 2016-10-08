package com.izhbg.typz.im.goods.restf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.shop.goods.dto.TShGoodsBasic;
import com.izhbg.typz.shop.goods.dto.TShGoodsDetail;
import com.izhbg.typz.shop.goods.dto.TShGoodsImage;
import com.izhbg.typz.shop.goods.service.TShGoodsBasicService;
import com.izhbg.typz.shop.goods.service.TShGoodsDetailService;
import com.izhbg.typz.shop.goods.service.TShGoodsImageService;
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
	private TShStoreAttacheFileService tShStoreAttacheFileService;
	/**
	 * 产品详情
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("goodsDetail")
	@ResponseBody
	public String goodsDetail(@RequestParam(value = "goodsId", required = true, defaultValue = "") String goodsId){
		String result = null;
		try {
			TShGoodsBasic tShGoodsBasic = tShGoodsBasicService.getById(goodsId);
			if(tShGoodsBasic!=null){
				TShGoodsDetail tShGoodsDetail = tShGoodsDetailService.getById(tShGoodsBasic.getId());
				if(tShGoodsDetail!=null)
					tShGoodsBasic.settShGoodsDetail(tShGoodsDetail);
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
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,tShGoodsBasic);
			}else{
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, Constants.SYSTEMMSG_EMPTYFILED);
			}
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
}

package com.izhbg.typz.im.goods.restf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.shop.store.dto.TShStoreGoodsSale;
import com.izhbg.typz.shop.store.service.TShStoreGoodsBuyerService;
import com.izhbg.typz.shop.store.service.TShStoreGoodsSaleService;
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/im/store/goods/")
public class StoreGoodsSaleController {
	
	@Autowired
	private TShStoreGoodsSaleService tShStoreGoodsSaleService;
	/**
	 * 获取店铺销售列表
	 * @param storeId
	 * @param page
	 * @return
	 */
	@RequestMapping("saleList")
	@ResponseBody
	public String buyerList(@RequestParam(value = "storeId", required = true) String storeId,
							@ModelAttribute Page page){
		String result = null;
		try {
			page = tShStoreGoodsSaleService.pageList(page, storeId);
			List<TShStoreGoodsSale> tShStoreGoodsSales = (List<TShStoreGoodsSale>) page.getResult();
			BeanMapper beanMapper = new BeanMapper();
			List<com.izhbg.typz.im.goods.response.entity.TShStoreGoodsSale> tShStoreGoodsSales_ =  beanMapper.copyList(tShStoreGoodsSales, com.izhbg.typz.im.goods.response.entity.TShStoreGoodsSale.class);
			page.setResult(tShStoreGoodsSales_);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,page);
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	
}

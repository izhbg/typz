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
import com.izhbg.typz.shop.store.dto.TShStoreGoodsBuyer;
import com.izhbg.typz.shop.store.service.TShStoreGoodsBuyerService;
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/im/store/goods/")
public class StoreGoodsBuyerController {
	
	@Autowired
	private TShStoreGoodsBuyerService tShStoreGoodsBuyerService;
	/**
	 * 获取进货列表
	 * @param storeId
	 * @param page
	 * @return
	 */
	@RequestMapping("buyerList")
	@ResponseBody
	public String buyerList(@RequestParam(value = "storeId", required = true) String storeId,
							@ModelAttribute Page page){
		String result = null;
		try {
			page = tShStoreGoodsBuyerService.pageList(page, storeId);
			List<TShStoreGoodsBuyer> list = (List<TShStoreGoodsBuyer>) page.getResult();
			BeanMapper beanMapper = new BeanMapper();
			List<com.izhbg.typz.im.goods.response.entity.TShStoreGoodsBuyer> list_ = beanMapper.copyList(list, com.izhbg.typz.im.goods.response.entity.TShStoreGoodsBuyer.class);
			page.setResult(list_);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,page);
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	
}

package com.izhbg.typz.im.store.restf.controller;

import java.util.ArrayList;
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
import com.izhbg.typz.shop.store.dto.TShStore;
import com.izhbg.typz.shop.store.dto.TShStoreAttachefile;
import com.izhbg.typz.shop.store.dto.TShStoreGoodsSale;
import com.izhbg.typz.shop.store.service.TShStoreAttacheFileService;
import com.izhbg.typz.shop.store.service.TShStoreGoodsSaleService;
import com.izhbg.typz.shop.store.service.TShStoreService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/im/store")
public class StoreController {

	@Autowired
	private TShStoreService tShStoreService;
	@Autowired
	private TShStoreAttacheFileService tShStoreAttacheFileService;
	@Autowired
	private TShStoreGoodsSaleService tShStoreGoodsSaleService;
	/**
	 * 获取店铺信息
	 * @param memberId
	 * @return
	 */
	@RequestMapping("getStore")
	@ResponseBody
	public String getStore(@RequestParam(name="memberId",required=true) String memberId){
		String result = null;
		try {
			
			TShStore store = tShStoreService.getByMemberId(memberId);
			if(store!=null){
				TShStoreAttachefile tShStoreAttachefile = tShStoreAttacheFileService.getIndexAttacheFile(store.getId());
				if(tShStoreAttachefile!=null)
					store.setLogoAttache(tShStoreAttachefile);
				BeanMapper beanMapper = new BeanMapper();
				List<TShStore> stores = new ArrayList<>();
				stores.add(store);
				List<com.izhbg.typz.im.store.response.entity.TShStore> stores_ = beanMapper.copyList(stores, com.izhbg.typz.im.store.response.entity.TShStore.class);
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,stores_.get(0));
			}else{
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	
	/**
	 * 获取店铺产品列表
	 * @param storeId
	 * @return
	 */
	public String getStoreGoods(@RequestParam(name="storeId",required=true) String storeId,
								@ModelAttribute Page page){
		String result = null;
		try {
			page = tShStoreGoodsSaleService.pageList(page, storeId);
			List<TShStoreGoodsSale> tShStoreGoodsSales = (List<TShStoreGoodsSale>) page.getResult();
			if(tShStoreGoodsSales!=null){
				BeanMapper beanMapper = new BeanMapper();
				List<com.izhbg.typz.im.goods.response.entity.TShStoreGoodsSale> tShStoreGoodsSales_=beanMapper.copyList(tShStoreGoodsSales, com.izhbg.typz.im.goods.response.entity.TShStoreGoodsSale.class);
				page.setResult(tShStoreGoodsSales_);
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,page);
			}else
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	
}

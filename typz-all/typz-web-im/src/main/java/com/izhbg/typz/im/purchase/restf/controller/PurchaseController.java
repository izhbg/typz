package com.izhbg.typz.im.purchase.restf.controller;

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
import com.izhbg.typz.shop.purchase.dto.Purchase;
import com.izhbg.typz.shop.purchase.dto.TShPurchase;
import com.izhbg.typz.shop.purchase.service.TShPurchaseService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/im/purchase/")
public class PurchaseController {
	@Autowired
	private TShPurchaseService tShPurchaseService;
	@Autowired
	private TShGoodsBasicService tShGoodsBasicService;
	
	/**
	 * 添加购物车
	 * @param goodsId
	 * @param yhId
	 * @return
	 */
	@RequestMapping("add")
	@ResponseBody
	public String add(@RequestParam(value = "goodsId", required = true, defaultValue = "") String goodsId,
					  @RequestParam(value = "yhId", required = true, defaultValue = "") String yhId){
		String result = null;
		try {
			TShGoodsBasic tsb = tShGoodsBasicService.getById(goodsId);
			TShPurchase tsp = new TShPurchase();
			tsp.setGoodsId(goodsId);
			tsp.setYhId(yhId);
			tsp.setStoreId(tsb.getStoreId());
			tsp.setNum(1);
			tShPurchaseService.add(tsp);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS);
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	/**
	 * 更新购物车
	 * @param goodsId
	 * @param yhId
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public String update(@RequestParam(value = "id", required = true, defaultValue = "") String id,
			  			 @RequestParam(value = "num", required = true, defaultValue = "1") int num){
		String result = null;
		try {
			TShPurchase tsp = new TShPurchase();
			tsp.setId(id);
			tsp.setNum(num);
			tShPurchaseService.update(tsp);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS);
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String delete(@RequestParam(value = "ids", required = true, defaultValue = "") String[] ids){
		String result = null;
		try {
			tShPurchaseService.deleteBatche(ids);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS);
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	/**
	 * 获取个人购物车
	 * @param yhId
	 * @return
	 */
	@RequestMapping("getPurchase")
	@ResponseBody
	public String getPurchase(@RequestParam(value = "yhId", required = true, defaultValue = "") String yhId){
		String result = null;
		try {
			Purchase purchase = tShPurchaseService.getPurchaseByYhId(yhId);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,purchase);
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
}

package com.izhbg.typz.im.index.restf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.shop.goods.dto.TShGoodsTags;
import com.izhbg.typz.shop.goods.index.service.IndexService;
/**
 * 首页controller
* @author xiaolong.cai@mtime.com
* @date 2016年10月5日 下午8:06:36 
* @version V1.0
* $(function(){
* });
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/im/dashboard/")
public class DashboardController {
	
	@Autowired
	private IndexService indexService;
	
	/**
	 * 首页
	 * @param la
	 * @param lo
	 * @return
	 */
	@RequestMapping("dashboard")
	@ResponseBody
	public String smsRequest(@RequestParam(value = "la", required = false, defaultValue = "39.910942") Double la,
							 @RequestParam(value = "lo", required = false, defaultValue = "116.42292") Double lo){
		String result = null;
		try {
			List<TShGoodsTags> tShGoodsTags = indexService.dashboard(la, lo);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,tShGoodsTags);
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	
	/**
	 * 二级页面
	 * @param la
	 * @param lo
	 * @param tagId
	 * @return
	 */
	@RequestMapping("secondPage")
	@ResponseBody
	public String secondPage(@RequestParam(value = "la", required = false, defaultValue = "39.910942") Double la,
							 @RequestParam(value = "lo", required = false, defaultValue = "116.42292") Double lo,
							 @RequestParam(value = "tagId", required = true, defaultValue = "116.42292") String tagId){
		String result = null;
		try {
			TShGoodsTags tShGoodsTags = indexService.secondPage(tagId, la, lo);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,tShGoodsTags);
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	
}

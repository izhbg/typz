package com.izhbg.typz.im.goods.restf.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.shop.goods.dto.GoodsCommentCount;
import com.izhbg.typz.shop.goods.dto.TShGoodsComment;
import com.izhbg.typz.shop.goods.service.TShGoodsCommentService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/im/goodsComment/")
public class GoodsCommentController {
	
	@Autowired
	private TShGoodsCommentService tShGoodsCommentService;
	
	/**
	 * 获取产品评价列表
	 * @param goodsId
	 * @param page
	 * @return
	 */
	@RequestMapping("commentList")
	@ResponseBody
	public String commentList(@RequestParam(value = "goodsId", required = true) String goodsId,
							@ModelAttribute Page page){
		String result = null;
		try {
			page = tShGoodsCommentService.getListByGoodsId(page, goodsId);
			
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,page);
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	
	/**
	 * 评价数量
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("commentCount")
	@ResponseBody
	public String commentCount(@RequestParam(value = "goodsId", required = true) String goodsId){
		String result = null;
		try {
			GoodsCommentCount goodsCommentCount = tShGoodsCommentService.getCountByGoodsId(goodsId);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,goodsCommentCount);
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	
	/**
	 * 是否已评价
	 * @param orderId
	 * @return
	 */
	@RequestMapping("isCommented")
	@ResponseBody
	public String isCommented(@RequestParam(value = "orderId", required = true) String orderId,
							  @RequestParam(value = "goodsId", required = true) String goodsId){
		String result = null;
		try {
			boolean flag = tShGoodsCommentService.isCommonent(orderId,goodsId);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,flag);
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	/**
	 * 添加评价信息
	 * @param tShGoodsComment
	 * @return
	 */
	@RequestMapping("addComment")
	@ResponseBody
	public String addComment(@ModelAttribute TShGoodsComment tShGoodsComment){
		String result = null;
		try {
			tShGoodsComment.setId(IdGenerator.getInstance().getUniqTime()+"");
			tShGoodsComment.setTime(new Date());
			tShGoodsCommentService.add(tShGoodsComment);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	
}

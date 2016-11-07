package com.izhbg.typz.im.store.restf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.shop.store.dto.Withdraw;
import com.izhbg.typz.shop.store.service.WidthdrawService;

/**
 * 1、个人提现记录
 * 
 * @author CAI
 * 2015-7-18
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/im/storeWithdraw")
public class WithdrawController {
	@Autowired
	private WidthdrawService widthdrawService;
	/**
	 * 提现记录
	 * @param memberId
	 * @param withdrawTypeId
	 * @return
	 */
	@RequestMapping(value = "store-withdraw-list", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String list(@RequestParam(value = "memberId", required = true, defaultValue = "") String memberId,
									 @ModelAttribute Page page) {
		String result = null;
		try {
			page = widthdrawService.getHisList(page, memberId);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,page);
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 添加个人提现
	 * @param withdraw
	 * @return
	 */
	@RequestMapping(value = "store-withdraw-add", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String add(@ModelAttribute Withdraw withdraw) {
		String result = null;
		try {
			widthdrawService.approveWidthdraw(withdraw);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	/**
	 * 账户总额
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "getTotal", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getTotal(@RequestParam(value = "memberId", required = true, defaultValue = "") String memberId) {
		String result = null;
		try {
			double total = widthdrawService.getTotal(memberId);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,total);
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 可提现额度
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "getWithdrawer", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getWithdrawer(@RequestParam(value = "memberId", required = true, defaultValue = "") String memberId) {
		String result = null;
		try {
			double total = widthdrawService.getWithdrawer(memberId);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,total);
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
			e.printStackTrace();
		}
		return result;
	}
}

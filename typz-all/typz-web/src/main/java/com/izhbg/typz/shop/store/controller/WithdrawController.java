package com.izhbg.typz.shop.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.shop.store.service.WidthdrawService;

@Controller
@RequestMapping("storeWithdraw")
public class WithdrawController {
	
	@Autowired
	private WidthdrawService widthdrawService;
	
	/**
	 * 待提现
	 * @param page
	 * @param parameterMap
	 * @param model
	 * @return
	 */
	@RequestMapping("store-withdraw-waite-list")
    public String list(@ModelAttribute Page page,
            		   Model model) throws Exception {
		page = widthdrawService.getWaitList(page);
		model.addAttribute("page", page);
		return "shop/store/store-withdraw-waite-list";
	}
	/**
	 * 已提现
	 * @param page
	 * @param parameterMap
	 * @param model
	 * @return
	 */
	@RequestMapping("store-withdraw-history-list")
    public String history(@ModelAttribute Page page,
    					  Model model) throws Exception{
		page = widthdrawService.getHisList(page);
		model.addAttribute("page", page);
		return "shop/store/store-withdraw-history-list";
	}
	/**
	 * 提现 状态设置
	 * @param checkdel
	 * @return
	 */
	@RequestMapping(value = "store-withdraw-setState", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String setState(@RequestParam(value = "checkdel", required = true, defaultValue = "") String[] checkdel) {
		String result;
		try {
			widthdrawService.setState(checkdel);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,Constants.SYSTEMMSG_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR,Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	
	
}

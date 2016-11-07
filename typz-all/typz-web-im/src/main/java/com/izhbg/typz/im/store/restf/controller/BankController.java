package com.izhbg.typz.im.store.restf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.shop.store.dto.TShStoreAccountBank;
import com.izhbg.typz.shop.store.manager.TShStoreAccountBankManager;

/**
 * 获取银行
 * @author CAI
 * 2015-7-17
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/im/storeAccountBank")
public class BankController {
	@Autowired
	private TShStoreAccountBankManager tShStoreAccountBankManager;
	
	@RequestMapping(value = "list", method = {RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String list() {
		String result = null;
		List<TShStoreAccountBank> banks =  tShStoreAccountBankManager.getAll();
		result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,banks);
		return result;
	}
	
	
}

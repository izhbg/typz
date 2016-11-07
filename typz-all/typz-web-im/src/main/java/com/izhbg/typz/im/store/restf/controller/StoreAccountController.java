package com.izhbg.typz.im.store.restf.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.shop.store.dto.TShStoreAccount;
import com.izhbg.typz.shop.store.service.TShStoreAccountService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/im/storeAccount")
public class StoreAccountController {
	@Autowired
	private TShStoreAccountService tShStoreAccountService;
	
	/**
	 * 新增账户
	 * @param tShStoreAccount
	 * @return
	 */
	@RequestMapping("addOrUpdate")
	@ResponseBody
	public String addOrUpdate(@ModelAttribute TShStoreAccount tShStoreAccount){
		String result = null;
		if(tShStoreAccount==null||StringHelper.isEmpty(tShStoreAccount.getMemberId()))
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_EMPTYFILED);
		
		try {
			TShStoreAccount tShStoreAccount_ = tShStoreAccountService.getByMemberId(tShStoreAccount.getMemberId());
			if(tShStoreAccount_==null){
				tShStoreAccount.setId(IdGenerator.getInstance().getUniqTime()+"");
				tShStoreAccount.setChangeTime(new Date());
				tShStoreAccountService.add(tShStoreAccount);
			}else{
				tShStoreAccount.setId(tShStoreAccount_.getId());
				tShStoreAccount.setChangeTime(new Date());
				tShStoreAccountService.update(tShStoreAccount);
			}
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS);
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_SMSCODE_FAILED);
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取个人账户
	 * @param memberId
	 * @return
	 */
	@RequestMapping("getAccount")
	@ResponseBody
	public String getAccount(@RequestParam(name="memberId",required=true) String memberId){
		String result = null;
		try {
			TShStoreAccount tShStoreAccount = tShStoreAccountService.getByMemberId(memberId);
			if(tShStoreAccount==null)
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS);
			else	
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,tShStoreAccount);
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_SMSCODE_FAILED);
			e.printStackTrace();
		}
		return result;
	}
}

package com.izhbg.typz.im.person.address.controller;

import java.util.Date;
import java.util.List;

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
import com.izhbg.typz.shop.person.dto.TShPersonAddress;
import com.izhbg.typz.shop.person.service.TShPersonAddressService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/im/person/address/")
public class PersonAddressServiceController {
	
	@Autowired
	private TShPersonAddressService tShPersonAddressService;
	
	@RequestMapping("addOrUpdate")
	@ResponseBody
	public String addOrUpdate(@ModelAttribute TShPersonAddress tShPersonAddress){
		String result=null;
		if(tShPersonAddress==null)
			result  = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_EMPTYFILED);
		try {
			if(StringHelper.isEmpty(tShPersonAddress.getId())){
				tShPersonAddress.setId(IdGenerator.getInstance().getUniqTime()+"");
				tShPersonAddress.setTime(new Date());
				tShPersonAddressService.add(tShPersonAddress);
			}else{
				tShPersonAddressService.update(tShPersonAddress);
			}
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result  = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String delete(@RequestParam(name="ids" ,required=true) String[] ids){
		String result=null;
		if(ids==null)
			result  = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_EMPTYFILED);
		else{
			try {
				tShPersonAddressService.deleteBatche(ids);
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				result  = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
			}
		}
		return result;
	}
	
	@RequestMapping("getById")
	@ResponseBody
	public String getById(@RequestParam(name="id" ,required=true) String id){
		String result=null;
		if(StringHelper.isEmpty(id))
			result  = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_EMPTYFILED);
		try {
			TShPersonAddress tShPersonAddress = tShPersonAddressService.getById(id);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,tShPersonAddress);
		} catch (Exception e) {
			e.printStackTrace();
			result  = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	@RequestMapping("get")
	@ResponseBody
	public String get(@RequestParam(name="memberId" ,required=true) String memberId){
		String result=null;
		if(StringHelper.isEmpty(memberId))
			result  = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_EMPTYFILED);
		try {
			TShPersonAddress tShPersonAddress = tShPersonAddressService.getDefaultAddress(memberId);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,tShPersonAddress);
		} catch (Exception e) {
			e.printStackTrace();
			result  = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	@RequestMapping("setDefalutAddress")
	@ResponseBody
	public String setDefalutAddress(@RequestParam(name="memberId" ,required=true) String memberId,
					  @RequestParam(name="id" ,required=true) String id){
		String result=null;
		if(StringHelper.isEmpty(memberId))
			result  = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_EMPTYFILED);
		try {
			tShPersonAddressService.setDefalutAddress(id, memberId);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result  = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	@RequestMapping("list")
	@ResponseBody
	public String list(@RequestParam(name="memberId" ,required=true) String memberId){
		String result=null;
		if(StringHelper.isEmpty(memberId))
			result  = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_EMPTYFILED);
		try {
			List<TShPersonAddress> tShPersonAddresses = tShPersonAddressService.getPersonAddressByYhId(memberId);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,tShPersonAddresses);
		} catch (Exception e) {
			e.printStackTrace();
			result  = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_FAILED);
		}
		return result;
	}
	
}

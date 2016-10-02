package com.izhbg.typz.im.person.admin.restf.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.common.redis.RedisService;
import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.base.util.TokenProcessor;
import com.izhbg.typz.im.person.admin.entity.UserInfo;
import com.izhbg.typz.shop.member.dto.TShMember;
import com.izhbg.typz.shop.member.service.TShMemberService;
import com.izhbg.typz.sms.service.SMSService;

@Controller
@RequestMapping("/im/person/admin/")
public class LoginController {
	
	
	@Autowired
	private RedisService redisUti;
	
	@Autowired
	private SMSService smsService;
	
	@Autowired
	private TShMemberService tShMemberService;
	
	/**
	 * 发送短信验证码
	 * @param phone
	 * @return
	 */
	@RequestMapping("smsRequest")
	@ResponseBody
	public String smsRequest(String phone){
		String result = null;
		if(StringHelper.isEmpty(phone))
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_EMPTYFILED);
		else{
			try {
				String codeId = smsService.send(phone);
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,codeId);
			} catch (Exception e) {
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, Constants.SYSTEMMSG_SMSCODE_FAILED);
			}
		}
		
		return result;
	}
	
	/**
	 * 登录注册
	 * @param userInfo
	 * @return
	 */
	@RequestMapping("login")
	@ResponseBody
	public String login(@ModelAttribute UserInfo userInfo){
		String result = null;
		if(userInfo==null||StringHelper.isEmpty(userInfo.getPhone())||StringHelper.isEmpty(userInfo.getCode())||StringHelper.isEmpty(userInfo.getCodeId()))
			result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_EMPTYFILED);
		else{
			
			try {
				
				if(smsService.validateCode(userInfo.getCode(), userInfo.getCodeId())){
					if(!tShMemberService.existPhone(userInfo.getPhone())){
						this.regist(userInfo);
					}
					TShMember member = tShMemberService.getTShMemberByPhone(userInfo.getPhone());
					String token = TokenProcessor.getInstance().generateToken(member.getId(), true);
					//入redis 30天
					redisUti.add(Constants.LOGIN_TOKEN, token, member.getId(),60*60*24*30);
					result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, Constants.SYSTEMMSG_SUCCESS,token);
				}else{
					result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Constants.SYSTEMMSG_SMSCODE_FAILED);
				}
				
			} catch (Exception e) {
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, Constants.SYSTEMMSG_EMPTYFILED);
			}
			
		}
		
		return result;
	}
	
	/**
	 * 自动注册
	 * @param userInfo
	 * @return
	 * @throws Exception
	 */
	public String regist(UserInfo userInfo) throws Exception{
		TShMember member = new TShMember();
		String id = IdGenerator.getInstance().getUniqTime()+"";
		member.setId(id);
		member.setPhone(userInfo.getPhone());
		member.setTime(new Date());
		member.setLa(userInfo.getLa());
		member.setLo(userInfo.getLo());
		tShMemberService.add(member);
		return id;
		
	}
}

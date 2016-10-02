package com.izhbg.typz.sms.service;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.common.redis.RedisService;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.sms.dto.SMS;
import com.izhbg.typz.sms.queue.SMSQueue;

@Service
public class SMSService {
	
	Logger logger = LoggerFactory.getLogger(SMSService.class);
	
	@Autowired
	private SMSQueue smsQueue;
	@Autowired
	private RedisService redisService;
	/**
	 * 发送短信验证码
	 * @param phone
	 * @return
	 */
	public String send(String phone) throws Exception{
		if(StringHelper.isEmpty(phone))
			throw new ServiceException("手机号为空，发送手机验证码失败");
		
		int code = IdGenerator.generateCode(6);
		SMS sms = new SMS();
		sms.setCode(code);
		sms.setPhone(phone);
		
		try {
			//异步发送短信验证
			smsQueue.add(sms);
		} catch (Exception e) {
			logger.debug("短信入队失败  号码{}",sms.getPhone());
			throw new ServiceException("短信入队失败");
		}
		//生成短信验证码ID
		long codeId = IdGenerator.getInstance().getUniqTime();
		
		try {
			//存入redis
			logger.info("短信验证码入redis phone="+phone+" codeId="+codeId+"  code="+code);
			redisService.add(Constants.SMS_CODE_CODEID, codeId+"", code,Constants.SMS_TIMEOUT);
		} catch (Exception e) {
			logger.debug("短信验证码入 reids失败 验证码{}",code);
			throw new ServiceException("短信验证码入 reids失败 验证码");
		}
		return codeId+"";
	}
	
	/**
	 * 验证验证码
	 * @param code
	 * @param codeId
	 * @return
	 */
	public boolean validateCode(String code,String codeId)throws Exception{
		if(StringHelper.isEmpty(code)||StringHelper.isEmpty(codeId))
			throw new ServiceException("参数为空，验证验证码失败");
		String code_ora=null;
		try {
			code_ora = redisService.getStringById(Constants.SMS_CODE_CODEID, codeId);
		} catch (Exception e) {
			logger.debug(" reids中获取验证码失败 验证码ID{}",codeId);
			throw new ServiceException("reids中获取验证码失败");
		}
		if(code.equals(code_ora))
			return true;
		else
			return false;
	}
}

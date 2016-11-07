package com.izhbg.typz.shop.member.service;

import com.izhbg.typz.shop.common.service.TShBaseService;
import com.izhbg.typz.shop.member.dto.TShMember;

public interface TShMemberService extends TShBaseService<TShMember>{
	/**
	 * 是否已存在
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public boolean existPhone(String phone) throws Exception;
	
	/**
	 * 根据电话获取用户
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public TShMember getTShMemberByPhone(String phone) throws Exception;
}

package com.izhbg.typz.sso.auth.service;

import javax.annotation.Resource;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Service;

import com.izhbg.typz.sso.auth.dto.TXtJg;
import com.izhbg.typz.sso.auth.dto.TXtJgYh;
import com.izhbg.typz.sso.auth.manager.TXtJgManager;
import com.izhbg.typz.sso.auth.manager.TXtJgYhManager;

/**
 * 
 * @author dds
 *
 */
@Service
public class TXtYhService {
	
	private TXtJgYhManager tXtJgYhManager;
	private TXtJgManager tXtJgManager;
	/**
	 * 根据用户ID获取用户所属的机构，如果归属多个机构则返回 排序靠前的一位。
	 * @param yhId
	 * @return tXtJg
	 */
	public TXtJg findJgByYhId(String yhId){
		TXtJg tXtJg = null;
		if(StringHelper.isNotEmpty(yhId)){
			TXtJgYh tXtJgYh = tXtJgYhManager.findUniqueBy("yhId", yhId);
			if(tXtJgYh!=null){
				tXtJg = tXtJgManager.findUniqueBy("jgId", tXtJgYh.getJgId());
			}
		}
		return tXtJg;
	}
	
	@Resource
	public void settXtJgYhManager(TXtJgYhManager tXtJgYhManager) {
		this.tXtJgYhManager = tXtJgYhManager;
	}
	
	@Resource
	public void settXtJgManager(TXtJgManager tXtJgManager) {
		this.tXtJgManager = tXtJgManager;
	}
	
	
}

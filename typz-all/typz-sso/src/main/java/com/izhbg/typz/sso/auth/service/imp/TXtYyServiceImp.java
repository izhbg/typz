package com.izhbg.typz.sso.auth.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.sso.auth.dto.TXtYy;
import com.izhbg.typz.sso.auth.manager.TXtYyManager;
import com.izhbg.typz.sso.auth.service.TXtYyService;
@Service("tXtYyService")
@Transactional(rollbackFor=Exception.class)
public class TXtYyServiceImp implements TXtYyService{

	private TXtYyManager tXtYyManager;
	
	@Override
	public TXtYy getSystem(String appId) throws Exception {
		if(StringHelper.isEmpty(appId))
			throw new ServiceException("参数为空,获取应用信息失败");
		return tXtYyManager.findUniqueBy("yyId", appId);
	}

	@Override
	public List<TXtYy> queryAll() throws Exception {
		return tXtYyManager.getAll();
	}
	@Resource
	public void settXtYyManager(TXtYyManager tXtYyManager) {
		this.tXtYyManager = tXtYyManager;
	}
	
}

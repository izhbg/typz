package com.izhbg.typz.sso.auth.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.sso.auth.dto.TXtGnjsZy;
import com.izhbg.typz.sso.auth.manager.TXtGnjsZyManager;

@Service
@Transactional(rollbackFor = Exception.class)
public class TXtGnjsZyService {
	
	private TXtGnjsZyManager tXtGnjsZyManager;
	
	public  List<String> getGnListByJsDmFromDb( String jsDm, String want)
	{
		List<String> sl =tXtGnjsZyManager.find("select a.gnzyDm from TXtGnjsZy a where a.jsDm = ?", jsDm); /*new QueryCache(
				"select a.gnzyDm from TXtGnjsZy a where a.jsDm = :jsDm")
				.setParameter("jsDm", jsDm).list();*/
		
		
		List<String> rl =tXtGnjsZyManager.find("select a.gnDm from TXtGnzy a where a.gnDm in (?) and a.yxBj=1", sl); /*new QueryCache("select a.gnDm from TXtGnzy a where a.gnDm in (:param1) and a.yxBj=1")
			.setParameter("param1", sl).list();*/
		return rl;
	}
	public  List<String> getGnListByJsDm(String jsDm )
	{
		List  gnlist = getGnListByJsDmFromDb(jsDm,"gnzyDm");
		return gnlist;
	}


	@Resource
	public void setTXtGnjsZyManager(TXtGnjsZyManager xtGnjsZyManager) {
		tXtGnjsZyManager = xtGnjsZyManager;
	}
	

	
}

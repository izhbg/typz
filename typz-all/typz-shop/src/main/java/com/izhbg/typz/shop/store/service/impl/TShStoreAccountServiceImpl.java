package com.izhbg.typz.shop.store.service.impl;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.shop.store.dto.TShStoreAccount;
import com.izhbg.typz.shop.store.dto.TShStoreAccountBank;
import com.izhbg.typz.shop.store.manager.TShStoreAccountBankManager;
import com.izhbg.typz.shop.store.manager.TShStoreAccountManager;
import com.izhbg.typz.shop.store.service.TShStoreAccountService;
@Service
public class TShStoreAccountServiceImpl implements TShStoreAccountService{

	@Autowired
	private TShStoreAccountManager tShStoreAccoutManager;
	@Autowired
	private TShStoreAccountBankManager tShStoreAccountBankManager;
	
	private BeanMapper BeanMapper = new BeanMapper();
	
	@Override
	public void add(TShStoreAccount entity) throws Exception {
		if(entity==null)
			throw new ServiceException("参数为空，保存账户信息失败");
		entity.setScBj(Constants.UN_DELETE_STATE);
		tShStoreAccoutManager.save(entity);
	}

	@Override
	public void update(TShStoreAccount entity) throws Exception {
		TShStoreAccount tShStoreAccount = tShStoreAccoutManager.findUniqueBy("id", entity.getId());
		BeanMapper.copy(entity, tShStoreAccount);
		tShStoreAccoutManager.update(entity);
	}

	@Override
	public void delete(String id) throws Exception {
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数为空，删除账户信息失败");
		TShStoreAccount tShStoreAccount = tShStoreAccoutManager.findUniqueBy("id", id);
		tShStoreAccount.setScBj(Constants.DELETE_STATE);
		tShStoreAccoutManager.update(tShStoreAccount);
	}

	@Override
	public void deleteBatche(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空，批量删除账户信息失败");
		for(String id:ids)
			delete(id);
	}

	@Override
	public TShStoreAccount getById(String id) throws Exception {
		
		return null;
	}

	@Override
	public Page pageList(Page page) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TShStoreAccount> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TShStoreAccount> getStoreAccounts(String storeId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TShStoreAccount getByMemberId(String memberId) throws Exception {
		if(StringHelper.isEmpty(memberId))
			throw new ServiceException("参数为空，操作失败");
		TShStoreAccount tShStoreAccount = tShStoreAccoutManager.findUniqueBy("memberId", memberId);
		if(tShStoreAccount!=null&&StringHelper.isNotEmpty(tShStoreAccount.getBankId())){
			TShStoreAccountBank tShStoreAccountBank = tShStoreAccountBankManager.findUniqueBy("id",tShStoreAccount.getBankId());
			if(tShStoreAccountBank!=null)
				tShStoreAccount.setBankName(tShStoreAccountBank.getName());
		}
		return tShStoreAccount;
	}


}

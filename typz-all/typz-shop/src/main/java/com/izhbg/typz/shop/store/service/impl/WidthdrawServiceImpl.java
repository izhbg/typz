package com.izhbg.typz.shop.store.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.shop.member.dto.TShMember;
import com.izhbg.typz.shop.member.manager.TShMemberManager;
import com.izhbg.typz.shop.store.dto.TShStore;
import com.izhbg.typz.shop.store.dto.TShStoreAccount;
import com.izhbg.typz.shop.store.dto.Withdraw;
import com.izhbg.typz.shop.store.manager.TShStoreAccountManager;
import com.izhbg.typz.shop.store.manager.TShStoreManager;
import com.izhbg.typz.shop.store.manager.WithdrawManager;
import com.izhbg.typz.shop.store.service.WidthdrawService;

@Service
public class WidthdrawServiceImpl implements WidthdrawService {

	@Autowired
	private WithdrawManager withdrawManager;
	@Autowired
	private TShStoreAccountManager tShStoreAccountManager;
	@Autowired
	private TShMemberManager tShMemberManager;
	@Autowired
	private TShStoreManager tShStoreManager;
	@Override
	public Page getWaitList(Page page) throws Exception {
		Map<String , Object> map = new HashMap<>();
		map.put("state", Constants.STORE_WIDTHDRAW_WAIT_1);
		return withdrawManager.pagedQuery(" from Withdraw where state=:state order by askTime desc", page.getPageNo(), page.getPageSize(), map);
	}

	@Override
	public Page getHisList(Page page) throws Exception {
		Map<String , Object> map = new HashMap<>();
		map.put("state", Constants.STORE_WIDTHDRAW_ED_2);
		return withdrawManager.pagedQuery(" from Withdraw where state=:state order by askTime desc", page.getPageNo(), page.getPageSize(), map);
	}

	@Override
	public void setState(String[] ids) throws Exception {
		if(ids==null||ids.length<=0)
			throw new ServiceException("参数为空，操作失败");
		Withdraw withdraw = null;
		for(String id:ids) {
			withdraw = withdrawManager.findUniqueBy("id", id);
			if(withdraw!=null) {
				withdraw.setState(Constants.STORE_WIDTHDRAW_ED_2);
				withdrawManager.update(withdraw);
			}
		}
		
	}

	@Override
	public Page getHisList(Page page, String memberId) throws Exception {
		if(StringHelper.isEmpty(memberId))
			throw new ServiceException("参数为空，操作失败");
		Map<String , Object> map = new HashMap<>();
		map.put("memberId", memberId);
		return withdrawManager.pagedQuery(" from Withdraw where  memberId=:memberId order by askTime desc", page.getPageNo(), page.getPageSize(), map);
	}

	@Override
	public void approveWidthdraw(Withdraw withdraw) throws Exception {
		if(withdraw==null||withdraw.getMoney()<=0||StringHelper.isEmpty(withdraw.getMemberId()))
			throw new ServiceException("参数为空，操作失败");
		TShStoreAccount tShStoreAccount = tShStoreAccountManager.findUniqueBy("memberId", withdraw.getMemberId());
		if(tShStoreAccount==null)
			throw new ServiceException("请先设置个人账户");
		withdraw.setId(IdGenerator.getInstance().getUniqTime()+"");
		if(StringHelper.isNotEmpty(tShStoreAccount.getAccountName()))
			withdraw.setAccountName(tShStoreAccount.getAccountName());
		if(StringHelper.isNotEmpty(tShStoreAccount.getAccountNo()))
			withdraw.setAccountNo(tShStoreAccount.getAccountNo());
		if(StringHelper.isNotEmpty(tShStoreAccount.getAccountTypeId()))
			withdraw.setAccountTypeId(tShStoreAccount.getAccountTypeId());
		withdraw.setAskTime(new Date());
		if(StringHelper.isNotEmpty(tShStoreAccount.getBankId()))
			withdraw.setBankId(tShStoreAccount.getBankId());
		withdraw.setState(Constants.STORE_WIDTHDRAW_WAIT_1);
		
		withdrawManager.save(withdraw);
	}

	@Override
	public Double getTotal(String memberId) throws Exception {
		if(StringHelper.isEmpty(memberId))
			throw new ServiceException("参数为空，操作失败");
		TShMember tShMember = tShMemberManager.findUniqueBy("id", memberId);
		if(tShMember==null)
			throw new ServiceException("参数为空，操作失败");
		TShStore tShStore = tShStoreManager.findUniqueBy("yhId", memberId);
		if(tShStore==null)
			throw new ServiceException("参数为空，操作失败");
		String sql = " SELECT sum(tsog.guid_price*num) count FROM t_sh_order tso,t_sh_order_goods tsog WHERE tso.id = tsog.order_id and tso.status=3 and tso.store_id='"+tShStore.getId()+"'";
		
		List<Map<String,Object>> list = tShMemberManager.getJdbcTemplate().queryForList(sql);
		Double count = 0.0;
		if(list!=null&&list.size()>0) {
			Map<String, Object> map = list.get(0);
			if(map!=null&&map.get("count")!=null)
				count = (Double) map.get("count");
		}
		
		
		return count;
	}

	@Override
	public Double getWithdrawer(String memberId) throws Exception {
		if(StringHelper.isEmpty(memberId))
			throw new ServiceException("参数为空，操作失败");
		double count = this.getTotal(memberId);
		String sql = "select sum(money) count from t_sh_store_withdraw where member_id='"+memberId+"' ";
		Double count_ = 0.0;
		List<Map<String,Object>> list = tShMemberManager.getJdbcTemplate().queryForList(sql);
		if(list!=null&&list.size()>0) {
			Map<String,Object> map = list.get(0);
			if(map!=null&&map.get("count")!=null)
				count_ = (Double) map.get("count");
		}
		return count-count_;
	}

}

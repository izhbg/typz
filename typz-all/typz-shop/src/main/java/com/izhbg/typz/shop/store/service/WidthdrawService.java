package com.izhbg.typz.shop.store.service;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.shop.store.dto.Withdraw;
/**
 * 提现服务
* @author xiaolong.cai@mtime.com
* @date 2016年11月1日 下午1:22:24 
* @version V1.0
 */
public interface WidthdrawService {

	/**
	 * 待提现列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page getWaitList(Page page) throws Exception;
	/**
	 * 已提现列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Page getHisList(Page page) throws Exception;
	/**
	 * 状态变更
	 * @param ids
	 * @throws Exception
	 */
	public void setState(String[] ids) throws Exception;
	/**
	 * 个人提现历史
	 * @param page
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public Page getHisList(Page page,String memberId) throws Exception;
	/**
	 * 申请提现
	 * @param withdraw
	 * @throws Exception
	 */
	public void approveWidthdraw(Withdraw withdraw) throws Exception;
	/**
	 * 获取个人账户总额
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public Double getTotal(String memberId) throws Exception;
	/**
	 * 获取可提现额度
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public Double getWithdrawer(String memberId) throws Exception;
}

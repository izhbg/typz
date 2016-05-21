package com.izhbg.typz.sso.auth.service;

import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Service;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.sso.auth.dto.TXtJg;
import com.izhbg.typz.sso.auth.dto.TXtJgYh;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.dto.TXtYhQuery;
import com.izhbg.typz.sso.auth.manager.TXtJgManager;
import com.izhbg.typz.sso.auth.manager.TXtJgYhManager;
import com.sun.org.omg.CORBA.ExcDescriptionSeqHelper;
/**
 * 
 * 用户相关服务
* @author caixl 
* @date 2016-5-20 上午8:40:25 
*
 */
public interface TXtYhService {
	
	/**
	 * 根据用户ID获取用户所属的机构，如果归属多个机构则返回 排序靠前的一位。
	 * @param yhId
	 * @return tXtJg
	 */
	public TXtJg findJgByYhId(String yhId) throws Exception;
	/**
	 * 添加用户
	 * @param tXtYh
	 * @throws Exception
	 */
	public void add(TXtYh tXtYh) throws Exception;
	/**
	 * 添加用户，归属机构
	 * @param tXtYh
	 * @param jgIds
	 * @throws Exception
	 */
	public void add(TXtYh tXtYh,String[] jgIds) throws Exception;
	/**
	 * 删除用户
	 * @param yhId
	 * @throws Exception
	 */
	public void delete(String yhId) throws Exception;
	/**
	 * 批量删除用户
	 * @param yhIds
	 * @throws Exception
	 */
	public void deleteByIds(String[] yhIds) throws Exception;
	/**
	 * 重置密码
	 * @param yhIds
	 * @throws Exception
	 */
	public void resetPassword(String[] yhIds,String defaultPas)throws Exception;
	/**
	 * 重置用户状态
	 * @param yhIds
	 * @throws Exception
	 */
	public void updGUserStatus(String[] yhIds)throws Exception;
	/**
	 * 从机构移除用户
	 * @param yhIds
	 * @param jgId
	 * @throws Exception
	 */
	public void removeYhFromJg(String[] yhIds,String jgId) throws Exception;
	/**
	 * 根据ID查找用户
	 * @param yhId
	 * @throws Exception
	 */
	public TXtYh findByYhId(String yhId) throws Exception;
	/**
	 * 根据用户Dm查找用户
	 * @param yhDm
	 * @throws Exception
	 */
	public TXtYh findByYhDm(String yhDm) throws Exception;
	/**
	 * 更新用户
	 * @param tXtYh
	 * @throws Exception
	 */
	public void update(TXtYh tXtYh) throws Exception;
	/**
	 * 更新用户和归属机构
	 * @param tXtYh
	 * @param jgId
	 * @throws Exception
	 */
	public void update(TXtYh tXtYh,String jgId) throws Exception;
	/**
	 * 用户列表 分页数据
	 * @param page
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Page queryYhPageList(Page page,TXtYhQuery tXtYhQuery) throws Exception;
	
	
}

package com.izhbg.typz.sso.auth.service;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.sso.auth.dto.TXtGnjsAuthorities;

/**
 * 角色 授权接口规则服务
* @author caixl 
* @date 2016-5-23 上午9:16:09 
*
 */
public interface RoleOauthService {
	/**
	 * 获取授权列表
	 * @param page
	 * @param jsDm
	 * @return
	 * @throws Exception
	 */
	public Page queryPageList(Page page,String jsDm) throws Exception;
	/**
	 * 添加授权关系
	 * @param tXtGnjsAuthorities
	 * @throws Exception
	 */
	public void add(String oauthId,String[] checkdel) throws Exception;
	/**
	 * 批量删除授权关系
	 * @param ids
	 * @throws Exception
	 */
	public void deleteByIds(String[] ids) throws Exception;
	/**
	 * 获取规则树结构
	 * @param jsDm
	 * @return
	 * @throws Exception
	 */
	public String getOauthTreeJson(String jsDm)throws Exception;
}

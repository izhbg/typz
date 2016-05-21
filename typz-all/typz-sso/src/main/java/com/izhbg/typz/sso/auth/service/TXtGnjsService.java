package com.izhbg.typz.sso.auth.service;

import java.util.List;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.sso.auth.dto.TXtGnjs;
import com.izhbg.typz.sso.auth.dto.TXtGnjsQuery;

/**
 * 角色相关服务
* @author caixl 
* @date 2016-5-20 下午3:15:14 
*
 */
public interface TXtGnjsService {
	/**
	 * 根据ID获取juese
	 * @param jsId
	 * @return
	 * @throws Exception
	 */
	public TXtGnjs queryById(String jsId) throws Exception;
	/**
	 * 添加角色
	 * @param tXtGnjs
	 * @throws Exception
	 */
	public void add(TXtGnjs tXtGnjs) throws Exception;
	/**
	 * 删除角色根据ID
	 * @param jsId
	 * @throws Exception
	 */
	public void deleteById(String jsId) throws Exception;
	/**
	 * 批量删除角色根据ID
	 * @param jsIds
	 * @throws Exception
	 */
	public void deleteByIds(String[] jsIds) throws Exception;
	/**
	 * 更新角色
	 * @param tXtGnjs
	 * @throws Exception
	 */
	public void update(TXtGnjs tXtGnjs) throws Exception;
	/**
	 * 获取角色列表
	 * @return
	 * @throws Exception
	 */
	public Page qeuryPageList(TXtGnjsQuery tXtGnjsQuery,Page page) throws Exception;
	/**
	 * 根据appId获取角色列表
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public List<TXtGnjs> queryByAppId(String appId) throws Exception;
	/**
	 * 更新角色状态
	 * @param jsIds
	 * @throws Exception
	 */
	public void updateStatus(String[] jsIds) throws Exception;
	
}

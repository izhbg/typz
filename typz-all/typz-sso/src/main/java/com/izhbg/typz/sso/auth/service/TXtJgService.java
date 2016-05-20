package com.izhbg.typz.sso.auth.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import net.sf.json.JSONArray;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.sso.auth.dto.TXtJg;
import com.izhbg.typz.sso.auth.dto.TXtJgQuery;

/**
 * 组织机构service
* @author caixl 
* @date 2016-5-20 上午10:16:07 
*
 */
public interface TXtJgService {
	/**
	 * 根据ID查询机构信息
	 * @param jgId
	 * @return
	 * @throws Exception
	 */
	public TXtJg queryById(String jgId) throws Exception;
	/**
	 * 根据jgDm获取机构信息
	 * @param jgDm
	 * @return
	 * @throws Exception
	 */
	public TXtJg queryByJgDm(String jgDm) throws Exception;
	/**
	 * 添加机构信息
	 * @param tXtJg
	 * @throws Exception
	 */
	public void add(TXtJg tXtJg) throws Exception;
	/**
	 * 根据ID删除机构信息
	 * @param jgId
	 * @throws Exception
	 */
	public void deleteById(String jgId) throws Exception;
	/**
	 * 批量删除机构信息
	 * @param jgIds
	 * @throws Exception
	 */
	public void deleteByIds(String[] jgIds) throws Exception;
	/**
	 * 根据上级机构删除机构信息
	 * @param sjjgId
	 * @throws Exception
	 */
	public void deleteBySjjgId(String sjjgId) throws Exception;
	/**
	 * 更新机构信息
	 * @param tXtJg
	 * @throws Exception
	 */
	public void update(TXtJg tXtJg) throws Exception;
	/**
	 * 批量更新状态
	 * @param jgIds
	 * @throws Exception
	 */
	public void updateStatus(String[] jgIds) throws Exception;
	/**
	 * 根据上级机构ID获取子机构IDS
	 * @param sjjgId
	 * @return
	 * @throws Exception
	 */
	public  List getOrganIds(String sjjgId)throws Exception;
	/**
	 * 获取机构json树结构串
	 * @return
	 * @throws Exception
	 */
	public String getJgsJSON(String currentAppId)throws Exception;
	/**
	 * 获取子机构json
	 * @param sjjgId
	 * @return
	 * @throws Exception
	 */
	public String getSubOrgan(String sjjgId,String appId) throws Exception;
	/**
	 * 获取机构下的用户
	 * @param sjjgId
	 * @param iJgId
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public  JSONArray getSubUserOrgan(String sjjgId, String iJgId,String appId) throws Exception;
	/**
	 * 获取机构下的角色
	 * @param sjjgId
	 * @param jgId
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public  JSONArray getSubRoleOrgan(String sjjgId, String jgId,String appId) throws Exception;
	/**
	 * 分页查询
	 * @param page
	 * @param tXtJgQuery
	 * @return
	 * @throws Exception
	 */
	public Page queryPageList(Page page,TXtJgQuery tXtJgQuery) throws Exception;
}

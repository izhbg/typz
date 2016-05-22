package com.izhbg.typz.sso.auth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.sso.auth.dto.TXtYy;
import com.izhbg.typz.sso.auth.manager.TXtYyManager;
/**
* @author caixl 
* @date 2016-5-20 上午10:50:44 
*
 */
public interface TXtYyService {
    	/**
    	 * 添加应用信息
    	 * @param tXtYy
    	 * @throws Exception
    	 */
    	public void add(TXtYy tXtYy) throws Exception;
    	/**
    	 * 更新应用信息
    	 * @param tXtYy
    	 * @throws Exception
    	 */
    	public void update(TXtYy tXtYy) throws Exception;
    	/**
    	 * 批量删除
    	 * @param ids
    	 * @throws Exception
    	 */
    	public void deleteByIds(String[] ids) throws Exception;
	/**
	 * 获取当前应用信息
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public TXtYy getSystem(String appId) throws Exception;
	/**
	 * 获取所有的应用信息
	 * @return
	 * @throws Exception
	 */
	public List<TXtYy> queryAll() throws Exception;
	/**
	 * 根据code获取
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public TXtYy getByCode(String code) throws Exception;
	/**
	 * 分页查询
	 * @param page
	 * @param code
	 * @param appName
	 * @param classification
	 * @return
	 */
	public  Page page(Page page, String code, String appName, String classification);
	/**
	 * 更新状态
	 * @param ids
	 * @param defaultPas
	 * @throws Exception
	 */
	public void updateStatus(String[] ids,String defaultPas,String type)throws Exception;
	
}

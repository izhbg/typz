package com.izhbg.typz.sso.auth.service;

import net.sf.json.JSONArray;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.sso.auth.dto.UserRoleQuery;
/**
 * 用户角色服务
 * @author CXL
 *
 */
public interface UserRolesService {
    /**
     * 获取角色授权分页列表
     * @param page
     * @param userRoleQuery
     * @return
     * @throws Exception
     */
    public Page queryPageList(Page page,UserRoleQuery userRoleQuery) throws Exception;
    /**
     * 添加用户角色授权
     * @param yhId
     * @param checkdel
     * @throws Exception
     */
    public void add(String yhId,String[] checkdel) throws Exception;
    /**
     * 删除用户角色授权
     * @param checkdel
     * @throws Exception
     */
    public void deleteByIds(String[] checkdel)throws Exception;
    /**
     * 获取角色树结构JSON
     * @param yhId
     * @param appId
     * @return
     * @throws Exception
     */
    public String getJsJson(String yhId,String appId) throws Exception;
}

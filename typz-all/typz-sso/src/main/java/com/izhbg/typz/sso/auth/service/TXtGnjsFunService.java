package com.izhbg.typz.sso.auth.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.sso.auth.dto.RoleFunQuery;

public interface TXtGnjsFunService {
    /**
     * 角色功能分页列表
     * @param page
     * @param roleFunQuery
     * @return
     * @throws Exception
     */
    public Page queryPageList(Page page,RoleFunQuery roleFunQuery)throws Exception;
    /**
     * 添加角色功能
     * @param jsDm
     * @param checkdel
     * @param isRead
     * @param isCreate
     * @param isUpdate
     * @param isDelete
     * @param isAll
     * @throws Exception
     */
    public void add(String jsDm,String[] checkdel,String[] isRead,String[] isCreate,String[] isUpdate,String[] isDelete,String[] isAll) throws Exception;
    /**
     * 批量删除授权功能角色
     * @param checkdel
     * @throws Exception
     */
    public void deleteByIds(String[] checkdel)throws Exception;
    /**
     * 更新状态
     * @param categery
     * @param uuid
     * @throws Exception
     */
    public void changState(String categery,String uuid) throws Exception;
    public JSONObject getRootRoleFunc(String gnDm)throws Exception;
    public JSONArray getSubRoleFunc(String sjgnDm, String jsDm,String appId)throws Exception;
}

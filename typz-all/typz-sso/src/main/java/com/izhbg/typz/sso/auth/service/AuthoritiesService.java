package com.izhbg.typz.sso.auth.service;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.sso.auth.dto.TXtAuthorities;
/**
 * 权限相关服务
 * @author CXL
 *
 */
public interface AuthoritiesService {
    /**
     * 分页查询
     * @param page
     * @param appId
     * @return
     * @throws Exception
     */
    public Page queryPageList(Page page,String appId) throws Exception;
    /**
     * 添加权限
     * @param tXtResources
     * @throws Exception
     */
    public void add(TXtAuthorities tXtAuthorities) throws Exception;
    /**
     * 更新权限
     * @param tXtResources
     * @throws Exception
     */
    public void update(TXtAuthorities tXtAuthorities) throws Exception;
    /**
     * 根据ID批量删除
     * @param ids
     * @throws Exception
     */
    public void deleteByIds(String[] ids) throws Exception;
    /**
     * 根据ID查询
     * @param id
     * @throws Exception
     */
    public TXtAuthorities queryById(String id) throws Exception;
    /**
     * 更新状态
     * @param ids
     * @throws Exception
     */
    public void updateStatus(String[] ids) throws Exception;

}

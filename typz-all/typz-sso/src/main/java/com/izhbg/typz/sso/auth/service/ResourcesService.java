package com.izhbg.typz.sso.auth.service;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.sso.auth.dto.TXtResources;

/**
 * 资源服务
 * @author CXL
 *
 */
public interface ResourcesService {
    /**
     * 分页查询
     * @param page
     * @param appId
     * @return
     * @throws Exception
     */
    public Page queryPageList(Page page,String appId) throws Exception;
    /**
     * 添加资源
     * @param tXtResources
     * @throws Exception
     */
    public void add(TXtResources tXtResources) throws Exception;
    /**
     * 更新资源
     * @param tXtResources
     * @throws Exception
     */
    public void update(TXtResources tXtResources) throws Exception;
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
    public TXtResources queryById(String id) throws Exception;
    /**
     * 更新状态
     * @param ids
     * @throws Exception
     */
    public void updateStatus(String[] ids) throws Exception;
}

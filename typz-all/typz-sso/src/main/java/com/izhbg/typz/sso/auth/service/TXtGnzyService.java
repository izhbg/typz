package com.izhbg.typz.sso.auth.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.sso.auth.dto.TXtGnzy;
import com.izhbg.typz.sso.auth.dto.TXtGnzyQuery;

public interface TXtGnzyService {
    /*
     * 分页查询
     */
    public Page queryPageList(Page page,TXtGnzyQuery tXtGnzyQuery) throws Exception;
    /**
     * 添加功能节点
     * @param tXtGnzy
     * @throws Exception
     */
    public void add(TXtGnzy tXtGnzy) throws Exception;
    /**
     * 根据ID删除功能节点
     * @param gnDm
     * @throws Exception
     */
    public void deleteById(String gnDm) throws Exception;
    /**
     * 批量删除功能节点
     * @param gnDm
     * @throws Exception
     */
    public void deleteByIds(String[] gnDm) throws Exception;
    /**
     * 更新功能节点
     * @param tXtGnzy
     * @throws Exception
     */
    public void update(TXtGnzy tXtGnzy) throws Exception;
    /**
     * 根据ID查询功能节点
     * @param gnDm
     * @throws Exception
     */
    public TXtGnzy queryById(String gnDm) throws Exception;
    /**
     * 获取功能结构树
     * @param appId
     * @return
     * @throws Exception
     */
    public String getFunTreeJson(String appId) throws Exception;
    /**
     * 获取子节点
     * @param pId
     * @param appId
     * @return
     * @throws Exception
     */
    public JSONArray getSubFunc(String pId,String appId)throws Exception;
    
    public JSONArray getSubFuncCheck(String sjgnDm, String gnDm,String appId) throws Exception;
    /**
     * 批量更新状态
     * @param gnDms
     * @throws Exception
     */
    public void updFunStatus(String[] gnDms) throws Exception;
}

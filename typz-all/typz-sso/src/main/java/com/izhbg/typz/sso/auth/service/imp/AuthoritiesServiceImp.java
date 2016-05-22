package com.izhbg.typz.sso.auth.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.sso.auth.dto.TXtAuthorities;
import com.izhbg.typz.sso.auth.manager.TXtAuthoritiesManager;
import com.izhbg.typz.sso.auth.service.AuthoritiesService;

@Service("authoritiesService")
@Transactional(rollbackFor=Exception.class)
public class AuthoritiesServiceImp implements AuthoritiesService {

    private TXtAuthoritiesManager tXtAuthoritiesManager;
    
    private BeanMapper beanMapper = new BeanMapper();
    
    @Override
    public Page queryPageList(Page page, String appId) throws Exception {
	StringBuffer str = new StringBuffer(" from TXtAuthorities where appId=:appId");
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("appId", appId);
	page = tXtAuthoritiesManager.pagedQuery(str.toString(), page.getPageNo(), page.getPageSize(), params);
	return page;
    }

    @Override
    public void add(TXtAuthorities tXtAuthorities) throws Exception {
	if(tXtAuthorities==null)
	    throw new ServiceException("参数为空，添加权限失败");
	tXtAuthoritiesManager.save(tXtAuthorities);
    }

    @Override
    public void update(TXtAuthorities tXtAuthorities) throws Exception {
	if(tXtAuthorities==null)
	    throw new ServiceException("参数为空，更新权限失败");
	TXtAuthorities tXtAuthorities2 = tXtAuthoritiesManager.findUniqueBy("authorityId", tXtAuthorities.getAuthorityId());
	beanMapper.copy(tXtAuthorities, tXtAuthorities2);
	tXtAuthoritiesManager.update(tXtAuthorities2);
    }

    @Override
    public void deleteByIds(String[] ids) throws Exception {
	if(ids==null||ids.length==0)
	    throw new ServiceException("参数为空，删除权限失败");
	List<String> lst = new ArrayList<String>();
	for(String s : ids) 
		lst.add(s);
	List<TXtAuthorities> items = tXtAuthoritiesManager.findByIds(lst);
	
	for(Object o : items)
		tXtAuthoritiesManager.remove(o);
    }

    @Override
    public TXtAuthorities queryById(String id) throws Exception {
	if(StringHelper.isEmpty(id))
	    throw new ServiceException("参数为空，获取权限失败");
	
	return tXtAuthoritiesManager.findUniqueBy("authorityId", id);
    }

    @Override
    public void updateStatus(String[] ids) throws Exception {
	if(ids==null||ids.length==0)
	    throw new ServiceException("参数为空，更新权限状态失败");
	List lst = new ArrayList();
	for(String s : ids) 
		lst.add(s);
	List<TXtAuthorities> itemLst = (List<TXtAuthorities>)tXtAuthoritiesManager.findByIds(lst); 
	
	for(TXtAuthorities item : itemLst ){
		if(item.getEnabled()!=null&&item.getEnabled()==2){
			item.setEnabled(1);
		}else{
			item.setEnabled(2);
		}
		tXtAuthoritiesManager.update(item);
	}
	
    }
    @Resource
    public void settXtAuthoritiesManager(TXtAuthoritiesManager tXtAuthoritiesManager) {
        this.tXtAuthoritiesManager = tXtAuthoritiesManager;
    }
    
}

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
import com.izhbg.typz.sso.auth.dto.TXtResources;
import com.izhbg.typz.sso.auth.manager.TXtResourcesManager;
import com.izhbg.typz.sso.auth.service.ResourcesService;

@Service("resourcesService")
@Transactional(rollbackFor=Exception.class)
public class ResourcesServiceImp  implements ResourcesService{

    private TXtResourcesManager tXtResourcesManager;
    private BeanMapper beanMapper = new BeanMapper();
    
    @Override
    public Page queryPageList(Page page, String appId) throws Exception {
	StringBuffer str = new StringBuffer(" from TXtResources where appId=:appId");
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("appId", appId);
	page = tXtResourcesManager.pagedQuery(str.toString(), page.getPageNo(), page.getPageSize(), params);
	return page;
    }

    @Override
    public void add(TXtResources tXtResources) throws Exception {
	if(tXtResources==null)
	    throw new ServiceException("参数为空，添加资源失败");
	tXtResourcesManager.save(tXtResources);
    }

    @Override
    public void update(TXtResources tXtResources) throws Exception {
	if(tXtResources==null)
	    throw new ServiceException("参数为空，更新资源失败");
	TXtResources tXtResources2 = tXtResourcesManager.findUniqueBy("resourceId", tXtResources.getResourceId());
	beanMapper.copy(tXtResources, tXtResources2);
	tXtResourcesManager.update(tXtResources2);
    }

    @Override
    public void deleteByIds(String[] ids) throws Exception {
	if(ids==null||ids.length==0)
	    throw new ServiceException("参数为空，删除资源失败");
	for(String id:ids)
	    tXtResourcesManager.removeById(id);
    }

    @Override
    public TXtResources queryById(String id) throws Exception {
	if(StringHelper.isEmpty(id))
	    throw new ServiceException("参数为空，获取资源失败");
	return tXtResourcesManager.findUniqueBy("resourceId", id);
    }
    @Resource
    public void settXtResourcesManager(TXtResourcesManager tXtResourcesManager) {
        this.tXtResourcesManager = tXtResourcesManager;
    }

    @Override
    public void updateStatus(String[] ids) throws Exception {
	if(ids==null||ids.length==0)
	    throw new ServiceException("参数为空，更新状态失败");
	List lst = new ArrayList();
	for(String s : ids) 
		lst.add(s);
	List<TXtResources> itemLst = (List<TXtResources>)tXtResourcesManager.findByIds(lst); 
	
	for(TXtResources item : itemLst ){
		if(item.getEnabled()!=null&&item.getEnabled()==2){
			item.setEnabled(1);
		}else{
			item.setEnabled(2);
		}
		tXtResourcesManager.update(item);
	}
	
    }
}

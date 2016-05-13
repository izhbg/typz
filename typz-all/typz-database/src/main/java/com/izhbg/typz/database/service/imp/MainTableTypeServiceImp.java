package com.izhbg.typz.database.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.database.dto.MainTableType;
import com.izhbg.typz.database.manager.MainTableTypeManager;
import com.izhbg.typz.database.service.MainTableTypeService;
/**
 * 
* @ClassName: MainTableTypeServiceImp 
* @Description: 主表类型服务
* @author caixl 
* @date 2016-5-12 下午4:53:39 
*
 */
@Service("mainTableTypeService")
@Transactional
public class MainTableTypeServiceImp implements MainTableTypeService{

	private MainTableTypeManager mainTableTypeManager;
	
	@Override
	public List<MainTableType> findByAppId(String appId) throws Exception{
		if(StringHelper.isEmpty(appId))
			throw new ServiceException("参数不能为空，获取主表类型列表失败");
		List<MainTableType> mainTableTypes = mainTableTypeManager.findBy("appId", appId);
		return mainTableTypes;
	}

	@Override
	public void add(MainTableType mainTableType) throws Exception{
		if(mainTableType==null)
			throw new ServiceException("参数不能为空，添加主表类型失败");
		mainTableTypeManager.save(mainTableType);
	}

	@Override
	public void update(MainTableType mainTableType) throws Exception {
		if(mainTableType==null)
			throw new ServiceException("参数不能为空，更新主表类型失败");
		mainTableTypeManager.update(mainTableType);
	}

	@Override
	public void deleteById(String id) throws Exception{
		if(StringHelper.isEmpty(id))
			throw new ServiceException("参数不能为空，删除主表类型失败");
		mainTableTypeManager.removeById(id);
		
	}
	@Override
	public void deleteByIds(List<String> ids) throws Exception {
		if(ids==null)
			throw new ServiceException("参数为空，删除主表类型失败");
		List<MainTableType> formTemplates = mainTableTypeManager
				.findByIds(ids);
		mainTableTypeManager.removeAll(formTemplates);
	}
	@Resource
	public void setMainTableTypeManager(MainTableTypeManager mainTableTypeManager) {
		this.mainTableTypeManager = mainTableTypeManager;
	}

	
	
	

}

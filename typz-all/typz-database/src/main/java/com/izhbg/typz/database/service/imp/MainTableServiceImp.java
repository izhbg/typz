package com.izhbg.typz.database.service.imp;

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
import com.izhbg.typz.database.dto.MainTable;
import com.izhbg.typz.database.dto.MainTableColumn;
import com.izhbg.typz.database.manager.MainTableColumnManager;
import com.izhbg.typz.database.manager.MainTableManager;
import com.izhbg.typz.database.service.MainTableService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;

@Service("mainTableService")
@Transactional
public class MainTableServiceImp implements MainTableService{

	private MainTableManager mainTableManager;
	private MainTableColumnManager mainTableColumnManager;
	private BeanMapper beanMapper = new BeanMapper();
	
	@Override
	public Page findByAppIdAndTypeId(String appId, String typeId,Page page)
			throws Exception {
		if(StringHelper.isEmpty(appId)||StringHelper.isEmpty(typeId))
			throw new ServiceException("参数为空，获取主列表失败");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appId", appId);
		params.put("type", typeId);
		StringBuffer str = new StringBuffer();
		str.append(" from MainTable where appId=:appId and type=:type ");
		page = mainTableManager.pagedQuery(str.toString(), page.getPageNo(), page.getPageSize(), params);
		return page;
	}

	@Override
	public void add(MainTable mainTable) throws Exception {
		if(mainTable==null)
			throw new ServiceException("参数为空，保存主表失败");
		mainTableManager.save(mainTable);
	}

	@Override
	public void update(MainTable mainTable) throws Exception {
		if(mainTable==null)
			throw new ServiceException("参数为空，更新主表失败");
		mainTableManager.update(mainTable);
	}

	@Override
	@Transactional
	public void delete(String tableId) throws Exception {
		if(StringHelper.isEmpty(tableId))
			throw new ServiceException("参数为空，删除主表信息失败");
		List<MainTableColumn> mainTableColumns = mainTableColumnManager.findBy("maintableid", Long.parseLong(tableId));
		if(mainTableColumns!=null&&mainTableColumns.size()>0)
			mainTableColumnManager.removeAll(mainTableColumns);
		mainTableManager.removeById(Long.parseLong(tableId));
	}
	@Override
	@Transactional
	public MainTable copy(String targetId,String formname) throws Exception {
		if(StringHelper.isEmpty(targetId))
			throw new ServiceException("参数为空,复制主表信息失败");
		 MainTable maintable = mainTableManager.findUniqueBy("tableid",Long.parseLong(targetId));
		 MainTable maintable2 = null;
		if(maintable!=null){
			beanMapper.copy(maintable, maintable2);
			maintable2 = new MainTable();
			maintable2.setTableid(null);
			maintable2.setTableCName(formname);
			maintable2.setClassName(maintable.getClassName());
			maintable2.setAppId(SpringSecurityUtils.getCurrentUserAppId());
			mainTableManager.save(maintable2);
			maintable = maintable2;
			
			List<MainTableColumn> list = mainTableColumnManager.findBy("maintableid", Long.parseLong(targetId));
			MainTableColumn column2 = null;
			for(MainTableColumn column:list){
				column2 = new MainTableColumn();
				beanMapper.copy(column, column2);
				column2.setColumnid(null);
				column2.setMaintableid(maintable.getTableid());
				column2.setAppId(SpringSecurityUtils.getCurrentUserAppId());
				mainTableColumnManager.save(column2);
			}
		}
		return maintable2;
	}
	@Override
	public List<MainTable> findByMainTableName(String mainTableName)
			throws Exception {
		if(StringHelper.isEmpty(mainTableName))
			throw new ServiceException("参数为空,获取主表失败");
		return mainTableManager.findBy("tableName", mainTableName);
	}
	@Resource
	public void setMainTableManager(MainTableManager mainTableManager) {
		this.mainTableManager = mainTableManager;
	}

	@Resource
	public void setMainTableColumnManager(
			MainTableColumnManager mainTableColumnManager) {
		this.mainTableColumnManager = mainTableColumnManager;
	}

	@Override
	public MainTable findByMainTableId(String maintableId) throws Exception {
		if(StringHelper.isEmpty(maintableId))
			throw new ServiceException("参数为空，获取主表失败");
		return mainTableManager.findUniqueBy("tableid", Long.parseLong(maintableId));
	}

}

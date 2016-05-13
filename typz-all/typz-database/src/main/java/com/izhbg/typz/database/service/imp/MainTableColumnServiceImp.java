package com.izhbg.typz.database.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.database.dto.MainTable;
import com.izhbg.typz.database.dto.MainTableColumn;
import com.izhbg.typz.database.manager.MainTableColumnManager;
import com.izhbg.typz.database.manager.MainTableManager;
import com.izhbg.typz.database.service.MainTableColumnService;
import com.izhbg.typz.database.sql.service.BaseService;
import com.izhbg.typz.database.sql.service.SelectDataService;
import com.izhbg.typz.database.uitl.SqlUtil;

@Service("mainTableColumnService")
@Transactional
public class MainTableColumnServiceImp implements MainTableColumnService {

	private MainTableColumnManager mainTableColumnManager;
	private SelectDataService selectDataService;
	private MainTableManager mainTableManager;
	private BaseService baseService;

	@Override
	public List<MainTableColumn> findByMainTableId(String mainTableId)
			throws Exception {
		if (StringHelper.isEmpty(mainTableId))
			throw new ServiceException("参数为空，获取主表列信息失败");
		List<MainTableColumn> mainTableColumns = mainTableColumnManager.findBy(
				"maintableid", Long.parseLong(mainTableId));
		return mainTableColumns;
	}

	@Override
	public void deleteByMainTableId(String mainTableId) throws Exception {
		if (StringHelper.isEmpty(mainTableId))
			throw new ServiceException("参数为空，删除主表列信息失败");
		List<MainTableColumn> mainTableColumns = mainTableColumnManager.findBy(
				"maintableid", Long.parseLong(mainTableId));
		if (mainTableColumns != null)
			mainTableColumnManager.removeAll(mainTableColumns);
	}

	@Override
	public void saveLoadMainTableColumn(String mainTableId) throws Exception {
		if (StringHelper.isEmpty(mainTableId))
			throw new ServiceException("参数为空，加载主表列信息失败");
		MainTable maintable = mainTableManager.findUniqueBy("tableid",
				Long.parseLong(mainTableId));
		List<MainTableColumn> mainTableColumns = this
				.findByMainTableId(mainTableId);
		String[] columnNames = selectDataService.getColumnName(SqlUtil
				.getSqlForLoadColumn(maintable));
		for (String columnName : columnNames) {
			String[] propNames = new String[2];
			propNames[0] = "columnName";
			propNames[1] = "maintableid";
			Object[] propValues = new Object[2];
			propValues[0] = columnName;
			propValues[1] = maintable.getTableid();
			List<MainTableColumn> list = baseService.findObjectByPars(
					MainTableColumn.class, propNames, propValues);
			if (list.size() == 0) {
				MainTableColumn mainTableColumn = new MainTableColumn();
				mainTableColumn.setMaintableid(maintable.getTableid());
				mainTableColumn.setColumnName(columnName);
				mainTableColumn.setColumnCName(columnName);
				mainTableColumn.setPropertyType("1");
				mainTableColumn.setIsList("1");
				mainTableColumn.setIsQuery("0");
				mainTableColumn.setIsExport("1");
				mainTableColumn.setDataType("1");
				mainTableColumn.setIsExportOrder(999);
				mainTableColumn.setIsListOrder(999);
				mainTableColumn.setIsQueryOrder(999);
				this.add(mainTableColumn);
			} else {
				MainTableColumn mainTableColumn = list.get(0);
				mainTableColumns.remove(mainTableColumn);
			}
		}
		for (MainTableColumn mainTableColumn : mainTableColumns) {
			this.deleteByMainTableColumnId(mainTableColumn.getColumnid() + "");
		}
	}

	@Override
	public void add(MainTableColumn mainTableColumn) throws Exception {
		if (mainTableColumn == null)
			throw new ServiceException("参数为空,添加主表列信息失败");
		mainTableColumnManager.save(mainTableColumn);
	}

	@Override
	public void update(MainTableColumn mainTableColumn) throws Exception {
		if (mainTableColumn == null)
			throw new ServiceException("参数为空,添加主表列信息失败");
		mainTableColumnManager.update(mainTableColumn);
	}

	@Override
	public void deleteByMainTableColumnId(String mainTableColumnId)
			throws Exception {
		if (StringHelper.isEmpty(mainTableColumnId))
			throw new ServiceException("参数为空,删除主表信息失败");
		mainTableColumnManager.removeById(Long.parseLong(mainTableColumnId));
	}

	@Override
	public MainTableColumn findByMainTableColumnId(String mainTableColumnId)
			throws Exception {
		if (StringHelper.isEmpty(mainTableColumnId))
			throw new ServiceException("参数为空，获取列信息失败");
		return mainTableColumnManager.findUniqueBy("columnid",
				Long.parseLong(mainTableColumnId));
	}

	@Override
	public void updateMainTableColumns(HttpServletRequest request)
			throws Exception {

		String[] isLists = request.getParameterValues("isList");
		String[] isQuerys = request.getParameterValues("isQuery");
		String[] isExport = request.getParameterValues("isExport");
		String[] isInserts = request.getParameterValues("isInsert");
		request.getParameterValues("isListOrder");
		// 获取需要设定的值，放入map中
		Map<String, Object> maplist = new HashMap<String, Object>();
		Map<String, Object> mapquery = new HashMap<String, Object>();
		Map<String, Object> mapexport = new HashMap<String, Object>();
		Map<String, Object> mapinsert = new HashMap<String, Object>();
		if (isLists != null) {
			for (String islist : isLists) {
				maplist.put(islist, islist);
			}
		}
		if (isQuerys != null) {
			for (String isquery : isQuerys) {
				mapquery.put(isquery, isquery);
			}
		}
		if (isExport != null) {
			for (String isexport : isExport) {
				mapexport.put(isexport, isexport);
			}
		}
		if (isInserts != null) {
			for (String isinsert : isInserts) {
				mapinsert.put(isinsert, isinsert);
			}
		}

		String maintableid = request.getParameter("maintableid");
		List<MainTableColumn> list = mainTableColumnManager.findBy(
				"maintableid", Long.parseLong(maintableid));
		for (int i = 0; i < list.size(); i++) {
			MainTableColumn mainTableColumn = list.get(i);
			String id = mainTableColumn.getColumnid().toString();
			String listorder = request.getParameter("isListOrder_" + id);
			mainTableColumn.setIsListOrder(listorder.length() == 0 ? 999
					: Integer.valueOf(listorder));
			String queryorder = request.getParameter("isQueryOrder_" + id);
			mainTableColumn.setIsQueryOrder(queryorder.length() == 0 ? 999
					: Integer.valueOf(queryorder));
			String exportorder = request.getParameter("isExportOrder_" + id);
			mainTableColumn.setIsExportOrder(exportorder.length() == 0 ? 999
					: Integer.valueOf(exportorder));
			String insertorder = request.getParameter("isInsertOrder_" + id);
			mainTableColumn.setIsInsertOrder(insertorder.length() == 0 ? 999
					: Integer.valueOf(insertorder));

			mainTableColumn.setIsList(maplist.get(id) != null ? "1" : "0");
			mainTableColumn.setIsQuery(mapquery.get(id) != null ? "1" : "0");
			mainTableColumn.setIsExport(mapexport.get(id) != null ? "1" : "0");
			mainTableColumn.setIsInsert(mapinsert.get(id) != null ? "1" : "0");
			mainTableColumnManager.update(mainTableColumn);
		}
	}

	@Resource
	public void setMainTableColumnManager(
			MainTableColumnManager mainTableColumnManager) {
		this.mainTableColumnManager = mainTableColumnManager;
	}

	@Resource
	public void setSelectDataService(SelectDataService selectDataService) {
		this.selectDataService = selectDataService;
	}

	@Resource
	public void setMainTableManager(MainTableManager mainTableManager) {
		this.mainTableManager = mainTableManager;
	}

	@Resource
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

}

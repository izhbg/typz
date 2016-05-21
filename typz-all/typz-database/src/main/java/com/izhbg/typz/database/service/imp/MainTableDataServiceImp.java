package com.izhbg.typz.database.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.DateUtil;
import com.izhbg.typz.base.util.StringUtils;
import com.izhbg.typz.database.dto.MainTable;
import com.izhbg.typz.database.dto.MainTableColumn;
import com.izhbg.typz.database.dto.QueryPanel;
import com.izhbg.typz.database.manager.MainTableColumnManager;
import com.izhbg.typz.database.manager.MainTableManager;
import com.izhbg.typz.database.service.MainTableDataService;
import com.izhbg.typz.database.sql.service.SelectDataService;
import com.izhbg.typz.database.uitl.SqlUtil;
import com.izhbg.typz.sso.auth.UserAuthDTO;
import com.izhbg.typz.sso.util.SpringSecurityUtils;

@Transactional
@Service("mainTableDataService")
public class MainTableDataServiceImp implements MainTableDataService{

	private MainTableManager mainTableManager;
	private MainTableColumnManager mainTableColumnManager;
	private SelectDataService selectDataService;
	@Override
	public Map<String, Object> mainTableData_pageList(
			Map<String, Object> parameterMap, 
			Page page) throws Exception {
		
		String tableName = parameterMap.get("tableName")==null?"":parameterMap.get("tableName")+"";
		if(StringHelper.isEmpty(tableName))
			throw new ServiceException("参数为空，获取表单数据失败");
		List<MainTable> mainTables = mainTableManager.findBy("tableName", tableName);
		if(mainTables==null||mainTables.size()==0)
			throw new ServiceException("没有要显示的表，请查看配置");
		else if(mainTables.size()>1)
			throw new ServiceException("你可能又相同的表在表描述信息中，请查看配置");
		MainTable maintable = mainTables.get(0);
		List<MainTableColumn> mainTableColumns = mainTableColumnManager.findBy("maintableid", maintable.getTableid());
		if(mainTableColumns==null||mainTableColumns.size()==0)
			throw new ServiceException("表的字段信息没有配置");
		//获取查询条件列表
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("isQuery", "1");
		param.put("maintableid", maintable.getTableid());
		List<MainTableColumn> qmtclist = mainTableColumnManager.find(" from MainTableColumn where isQuery=:isQuery and maintableid=:maintableid", param);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<QueryPanel> queryList = this.getQueryList(qmtclist, parameterMap);
		
		//获取表单列表
		param.clear();
		param.put("isList", "1");
		param.put("maintableid", maintable.getTableid());
		List<MainTableColumn> vmtclist = mainTableColumnManager.find(" from MainTableColumn where isList=:isList and maintableid=:maintableid", param);
		// 记录如果是弹出的树形结构的字段
		List<QueryPanel> viewList = new ArrayList<QueryPanel>();
		List<QueryPanel> treelist = new ArrayList<QueryPanel>();
		for (MainTableColumn mainTableColumn : vmtclist) {
			// 在列表信息中要显示的字段
			QueryPanel viewpanel = new QueryPanel();
			viewpanel.setName(mainTableColumn.getColumnName());
			viewpanel.setCname(mainTableColumn.getColumnCName());
			viewpanel.setType(mainTableColumn.getPropertyType());
			viewpanel.setMainTableColumn(mainTableColumn);
			if (mainTableColumn.getPropertyType().equals("2") || mainTableColumn.getPropertyType().equals("4")||mainTableColumn.getPropertyType().equals("3")) {
				List extlist = SqlUtil.getExtList(mainTableColumn, null);
				viewpanel.setLists(extlist);
			} else if (mainTableColumn.getPropertyType().equals("8")) {
				treelist.add(viewpanel);
			}
			viewList.add(viewpanel);
		}
		// 根据条件拼sql语句查询
		String srcsql = SqlUtil.filterAuth(maintable.getSql());
		String selectsql = SqlUtil.getMainSql(viewList, queryList, maintable,srcsql);
		int total = selectDataService.getDataRowNum(selectsql);
		page.setTotalCount(total);
		// 分页
		String selectsqlpage = SqlUtil.getMainSqlForPage(selectsql, page.getPageNo(), page.getPageSize(), maintable);
		List resoultList = selectDataService.getData(selectsqlpage);
		resoultList = selectDataService.getMutiChecked(resoultList, treelist);
		resultMap.put("resoultList", resoultList);
		resultMap.put("viewList", viewList);
		resultMap.put("maintable", maintable);
		resultMap.put("queryList", queryList);
		resultMap.put("page", page);
		return resultMap;
	}
	@Override
	public Map<String, Object> mainTableData_editData(
			Map<String, Object> parameterMap) throws Exception {
		String tableName = StringUtils.getString(parameterMap.get("tableName"));
		if(StringHelper.isEmpty(tableName))
			throw new ServiceException("参数为空，获取编辑页失败");
		List<MainTable> mainTables = mainTableManager.findBy("tableName", tableName);
		if(mainTables==null||mainTables.size()==0)
			throw new ServiceException("没有要显示的表，请查看配置");
		else if(mainTables.size()>1)
			throw new ServiceException("你可能有相同的表在表描述信息中，请查看配置");
		MainTable maintable = mainTables.get(0);
		List<MainTableColumn> mtclist = mainTableColumnManager.findBy("maintableid",maintable.getTableid());
		if(mtclist==null||mtclist.size()==0)
			throw new ServiceException("表的字段信息没有配置");
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//获取可插入的列信息
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("isInsert", "1");
		param.put("maintableid", maintable.getTableid());
		List<MainTableColumn> qmtclist = mainTableColumnManager.find(" from MainTableColumn where isInsert=:isInsert and maintableid=:maintableid ", param);
		List extlist = null;
		QueryPanel querypanel = null;
		List detailList = new ArrayList();
		List queryList = new ArrayList();
		String realtableid = StringUtils.getString(parameterMap.get("realtableid"));
		for (MainTableColumn mainTableColumn : qmtclist) {
			querypanel = new QueryPanel();
			querypanel.setName(mainTableColumn.getColumnName());
			querypanel.setCname(mainTableColumn.getColumnCName());
			querypanel.setType(mainTableColumn.getPropertyType());
			querypanel.setMainTableColumn(mainTableColumn);
			if (mainTableColumn.getPropertyType().equals("2") || mainTableColumn.getPropertyType().equals("4")|| mainTableColumn.getPropertyType().equals("3")) {
				extlist = SqlUtil.getExtList(mainTableColumn, null);
				querypanel.setLists(extlist);
			}
			
			if (mainTableColumn.getColumnName().toLowerCase().equals(maintable.getKeyColumnName().toLowerCase())) {
				querypanel.setValue(realtableid);
				queryList.add(querypanel);
			}
			
			detailList.add(querypanel);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		if (realtableid != null && realtableid.length() > 0) {
			String selectsql = SqlUtil.getMainSql(detailList, queryList, maintable,maintable.getSql());
			List<Map<String,Object>> resoultList = selectDataService.getData(selectsql);
			map = resoultList.get(0);
			for (int i = 0; i < detailList.size(); i++) {
				QueryPanel qp = (QueryPanel) detailList.get(i);
				if (qp.getMainTableColumn().getPropertyType().equals("8")) {
					String value = map.get(qp.getName()) == null ? "" : map.get(qp.getName()).toString();
					qp.setValue(value);
					if (value != null && value.length() > 0) {
						extlist = SqlUtil.getExtList(qp.getMainTableColumn(), value);
						qp.setLists(extlist);
					}
				} else if (qp.getMainTableColumn().getPropertyType().equals("11")) {
					String value = map.get(qp.getName()) == null ? "" : map.get(qp.getName()).toString();
					map.put(qp.getName(), value);
				}
				detailList.set(i, qp);
			}
		}
		resultMap.put("realTableMap", map);
		resultMap.put("maintable", maintable);
		resultMap.put("detailList", detailList);
		return resultMap;
	}
	@Override
	public void mainTableData_save(Map<String, Object> parameterMap)
			throws Exception {
		String tableName = StringUtils.getString(parameterMap.get("tableName"));
		if(StringHelper.isEmpty(tableName))
			throw new ServiceException("参数为空，保存表单数据失败");
		List<MainTable> mainTables = mainTableManager.findBy("tableName", tableName);
		if(mainTables==null||mainTables.size()==0)
			throw new ServiceException("没有要显示的表，请查看配置");
		else if(mainTables.size()>1)
			throw new ServiceException("你可能有相同的表在表描述信息中，请查看配置");
		MainTable maintable = mainTables.get(0);
		List<MainTableColumn> mtclist = mainTableColumnManager.findBy("maintableid",maintable.getTableid());
		if(mtclist==null||mtclist.size()==0)
			throw new ServiceException("表的字段信息没有配置");
		
		//获取可插入的列信息
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("isInsert", "1");
		param.put("maintableid", maintable.getTableid());
		List<MainTableColumn> qmtclist = mainTableColumnManager.find(" from MainTableColumn where isInsert=:isInsert and maintableid=:maintableid ", param);
		List detailList = new ArrayList();
		for (MainTableColumn mainTableColumn : qmtclist) {
			QueryPanel querypanel = new QueryPanel();
			querypanel.setName(mainTableColumn.getColumnName());
			querypanel.setCname(mainTableColumn.getColumnCName());
			querypanel.setType(mainTableColumn.getPropertyType());
			querypanel.setMainTableColumn(mainTableColumn);
			if (mainTableColumn.getPropertyType().equals("2") || mainTableColumn.getPropertyType().equals("4")|| mainTableColumn.getPropertyType().equals("3")) {
				List extlist = SqlUtil.getExtList(mainTableColumn, null);
				querypanel.setLists(extlist);
			}
			String value = StringUtils.getString(parameterMap.get(mainTableColumn.getColumnName()));
			if (value != null && value.length() > 0) {
			  //@TODO 密码处理
			}
			if (mainTableColumn.getTolerant() != null && mainTableColumn.getTolerant().length() > 0&&value!=null && value.length() == 0) {
				String[] tos = mainTableColumn.getTolerant().split(";");
				for (String to : tos) {
					if (to.indexOf("'") == 0 && to.lastIndexOf("'") == to.length() - 1) {
						value += to.substring(1, to.length() - 1);
					} else if (to.equals("Date()")) {
						value += DateUtil.convertDateToString(new Date());

					} else if (to.equals("DateTime()")) {
						value += DateUtil.convertDateTimeToString(new Date());
					} else if (to.equals("LongDateTime()")) {
						value += DateUtil.getDateToStringFull2(new Date()) + System.currentTimeMillis() + DateUtil.getRandom(1000, 9999);
					}  else if (to.equals("CurrentUserName")){
						value +=SpringSecurityUtils.getCurrentUsername();
					}else if (to.equals("CurrentUserDisName")){
						UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
						value +=user.getDisplayName();
					}else if (to.equals("CurrentDepName")){
						List<Map<String,Object>> txgJgYhList =selectDataService.getData("select * from t_xt_jg_yh where yh_id ='"+SpringSecurityUtils.getCurrentUserId()+"'");
						if(txgJgYhList!=null&&txgJgYhList.size()>0)
						{
							Map map2 = txgJgYhList.get(0);
							Object obj = map2.get("jg_id");
							if(obj!=null){
								List<Map<String,Object>> txgJgList = selectDataService.getData("select * from t_xt_jg where jg_id ='"+obj+"'");
								if(txgJgList!=null&&txgJgList.size()>0){
									Map map3 = txgJgList.get(0);
									Object obj2 = map3.get("jg_mc");
									if(obj2!=null){
										value +=obj2;
									}else{
										value +="";
									}
								}else{
									value +="";
								}
							}else{
								value +="";
							}
						}else{
							value +="";
						}
					}else if (to.equals("CurrentUserId")){
						value +=SpringSecurityUtils.getCurrentUserId();
					}else if (to.equals("CurrentAppId")){
						UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
						value +=user.getAppId();
					}else {
						value+=to.toString();
					}
				}
			}
			querypanel.setValue(value);
			detailList.add(querypanel);
		}
		String keyid = StringUtils.getString(parameterMap.get(maintable.getKeyColumnName()));
		boolean haskey = (keyid != null && keyid.length() > 0) ? true : false;
		if (keyid != null && keyid.length() > 0) {
			String isidsql = "select * from " + maintable.getTableRealName() + " where " + maintable.getKeyColumnName() + "='" + keyid + "'";
			List islist = selectDataService.getData(isidsql);
			if (islist.size() == 0) {
				haskey = false;
			}
		}
		String sql = SqlUtil.getInsertSql(detailList, maintable, haskey);
		sql = sql.replaceAll("&apos;", "''").replaceAll("&quot;", "\"");
		try {
			selectDataService.saveRealTable(sql);
		} catch (Exception e) {
			throw new ServiceException("添加表单数据失败.");
		}
	}
	
	@Override
	public void mainTableData_remove(String tableName,String[] realtableids)
			throws Exception {
		if(StringHelper.isEmpty(tableName))
			throw new ServiceException("参数为空，删除表单数据失败");
		List<MainTable> mainTables = mainTableManager.findBy("tableName", tableName);
		if(mainTables==null||mainTables.size()==0)
			throw new ServiceException("没有要显示的表，请查看配置");
		else if(mainTables.size()>1)
			throw new ServiceException("你可能有相同的表在表描述信息中，请查看配置");
		MainTable maintable = mainTables.get(0);
		List<MainTableColumn> mtclist = mainTableColumnManager.findBy("maintableid",maintable.getTableid());
		if(mtclist==null||mtclist.size()==0)
			throw new ServiceException("表的字段信息没有配置");
		for (String realtableid : realtableids) {
			String sql = SqlUtil.getDeleteSql(maintable.getTableRealName(), maintable.getKeyColumnName(), realtableid);
			try {
				selectDataService.remove(sql);
			} catch (RuntimeException e) {
				// 用户日志
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * 组装查询条件
	 * @param qmtclist
	 * @return
	 */
	private List<QueryPanel> getQueryList(List<MainTableColumn> qmtclist,Map<String, Object> parameterMap) throws Exception{
		List<QueryPanel> queryList = new ArrayList<QueryPanel>();
		for (MainTableColumn mainTableColumn : qmtclist) {
			// 查询的表头
			QueryPanel querypanel = new QueryPanel();
			querypanel.setName(mainTableColumn.getColumnName());
			querypanel.setCname(mainTableColumn.getColumnCName());
			querypanel.setType(mainTableColumn.getPropertyType());
			querypanel.setMainTableColumn(mainTableColumn);
			//2--下拉列表，4--单选框
			if (mainTableColumn.getPropertyType().equals("2") || mainTableColumn.getPropertyType().equals("4")|| mainTableColumn.getPropertyType().equals("3")) {
				List extlist = SqlUtil.getExtList(mainTableColumn, null);
				querypanel.setLists(extlist);
			}
			if (mainTableColumn.getPropertyType().equals("5") || mainTableColumn.getPropertyType().equals("10")) {
				if (mainTableColumn.getDataType().equals("3")) {
					String startdate =StringUtils.getString(parameterMap.get(mainTableColumn.getColumnName() + "start"));
					querypanel.setStartdate(startdate);
					String enddate = StringUtils.getString(parameterMap.get(mainTableColumn.getColumnName() + "end"));
					querypanel.setEnddate(enddate);
				} else {
					String value = StringUtils.getString(parameterMap.get(mainTableColumn.getColumnName()));
					querypanel.setValue(value);
				}
			} else if (mainTableColumn.getPropertyType().equals("8")) {
				String value = StringUtils.getString(parameterMap.get(mainTableColumn.getColumnName()));
				querypanel.setValue(value);
				if (value != null && value.length() > 0) {
					List<Map<String,Object>> extlist = SqlUtil.getExtList(mainTableColumn, value);
					querypanel.setLists(extlist);
				}
			} else {
				String value = StringUtils.getString(parameterMap.get(mainTableColumn.getColumnName()));
				querypanel.setValue(value);
			}
			queryList.add(querypanel);
		}
		return queryList;
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
	@Resource
	public void setSelectDataService(SelectDataService selectDataService) {
		this.selectDataService = selectDataService;
	}
	
	
	
}

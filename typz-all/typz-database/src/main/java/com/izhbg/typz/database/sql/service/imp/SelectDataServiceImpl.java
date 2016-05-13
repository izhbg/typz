package com.izhbg.typz.database.sql.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.database.dto.QueryPanel;
import com.izhbg.typz.database.sql.dao.SelectDataDao;
import com.izhbg.typz.database.sql.service.SelectDataService;

@Service("selectDataService")
@Transactional(readOnly = true)
public class SelectDataServiceImpl implements SelectDataService {

	private SelectDataDao selectDataDao;

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getData(String sql) throws Exception {
		List<Map<String, Object>> list = selectDataDao.selectData(sql);
		return list;
	}

	@Transactional
	public boolean remove(String sql) throws Exception {
		return selectDataDao.deletebyid(sql);
	}

	@Transactional(readOnly = true)
	public String[] getColumnName(String sql) throws Exception {
		return selectDataDao.getColumnName(sql);
	}

	@Transactional(readOnly = true)
	public int getDataRowNum(String sql) throws Exception {
		int i = 0;
		i = selectDataDao.selectDataRowNum(sql);
		return i;
	}

	@Transactional
	public void saveRealTable(String sql) throws Exception {
		selectDataDao.insertRealTable(sql);
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getMutiChecked(List<Map<String, Object>> resoultList, List<QueryPanel> treelist)
			throws Exception {
		return selectDataDao.getMutiChecked(resoultList, treelist);
	}

	@Transactional(readOnly = true)
	public Map<String,Object> getListForPage(Page page, String sql)
			throws Exception {
		int total = this.getDataRowNum(sql);
		if (page.getOrderBys()!=null&&page.getOrderBys().size()>0) {
			sql = sql + " order by " + page.getOrderBy() + " "
					+ page.getOrder();
		}
		
		page.setTotalCount(total);
		sql = "select rownum as oid, pagetable.* from (" + sql + ") pagetable ";
		String sqlpage = "select * from (" + sql
				+ ") tables1 where tables1.oid>=" + ((page.getPageNo() - 1) * page.getPageSize() + 1)
				+ " and tables1.oid<=" + page.getPageNo() * page.getPageSize();
		List<Map<String,Object>> resoultList = this.getData(sqlpage);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		page.setResult(resoultList);
		resultMap.put("resoultList", resoultList);
		resultMap.put("p", page);
		return resultMap;
	}

	@Override
	@Transactional
	public boolean executeSql(String sql) throws Exception {
		// TODO Auto-generated method stub
		return selectDataDao.deletebyid(sql);
	}

	@Resource
	public void setSelectDataDao(SelectDataDao selectDataDao) {
		this.selectDataDao = selectDataDao;
	}


}

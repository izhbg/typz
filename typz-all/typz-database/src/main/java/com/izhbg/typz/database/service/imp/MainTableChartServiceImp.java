package com.izhbg.typz.database.service.imp;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.rowset.serial.SerialException;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.database.dto.MainTableChart;
import com.izhbg.typz.database.manager.MainTableChartManager;
import com.izhbg.typz.database.service.MainTableChartService;
@Transactional
@Service("mainTableChartService")
public class MainTableChartServiceImp implements MainTableChartService {

	private MainTableChartManager mainTableChartManager;
	
	@Override
	public List<MainTableChart> findByMainTableId(String maintableid)
			throws Exception {
		if(StringHelper.isEmpty(maintableid))
			throw new ServiceException("参数为空，获取图表信息失败");
		return mainTableChartManager.findBy("tableid", Long.parseLong(maintableid));
	}
	@Override
	public MainTableChart findByMainTableChartId(String maintablechartid)
			throws Exception {
		if(StringHelper.isEmpty(maintablechartid))
			throw new ServiceException("参数为空，获取图表信息失败");
		return mainTableChartManager.findUniqueBy("chartid", Long.parseLong(maintablechartid));
	}
	@Override
	public void add(MainTableChart mainTableChart) throws Exception {
		if(mainTableChart==null)
			throw new ServiceException("参数为空,添加图表信息失败");
		mainTableChartManager.save(mainTableChart);
		
	}
	@Override
	public void update(MainTableChart mainTableChart) throws Exception {
		if(mainTableChart==null)
			throw new ServiceException("参数为空,更新图表信息失败");
		mainTableChartManager.update(mainTableChart);
	}
	@Override
	public void delete(String maintablechartId) throws Exception {
		if(StringHelper.isEmpty(maintablechartId))
			throw new ServiceException("参数为空,删除图表信息失败");
		mainTableChartManager.removeById(Long.parseLong(maintablechartId));
	}
	@Resource
	public void setMainTableChartManager(MainTableChartManager mainTableChartManager) {
		this.mainTableChartManager = mainTableChartManager;
	}
	

	
}

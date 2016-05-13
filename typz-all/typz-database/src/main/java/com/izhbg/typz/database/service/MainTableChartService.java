package com.izhbg.typz.database.service;

import java.util.List;

import com.izhbg.typz.database.dto.MainTableChart;

public interface MainTableChartService {

	/**
	 * 根据maintableid 获取主表相关的图表信息
	 * @param maintableid
	 * @return
	 * @throws Exception
	 */
	public List<MainTableChart> findByMainTableId(String maintableid) throws Exception;
	/**
	 * 根据主键获取图表信息
	 * @param maintablechartid
	 * @return
	 * @throws Exception
	 */
	public MainTableChart findByMainTableChartId(String maintablechartid) throws Exception;
	/**
	 * 添加主表图信息
	 * @param mainTableChart
	 * @throws Exception
	 */
	public void add(MainTableChart mainTableChart) throws Exception;
	/**
	 * 更新图表信息
	 * @param mainTableChart
	 * @throws Exception
	 */
	public void update(MainTableChart mainTableChart) throws Exception;
	/**
	 * 删除图表信息
	 * @param maintablechartId
	 * @throws Exception
	 */
	public void delete(String maintablechartId) throws Exception;
}

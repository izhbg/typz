package com.izhbg.typz.database.controller;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.izhbg.typz.database.dto.MainTable;
import com.izhbg.typz.database.dto.MainTableChart;
import com.izhbg.typz.database.dto.MainTableColumn;
import com.izhbg.typz.database.service.MainTableChartService;
import com.izhbg.typz.database.service.MainTableColumnService;
import com.izhbg.typz.database.service.MainTableService;
import com.izhbg.typz.database.sql.service.BaseService;
/**
 * 图标相关
* @author caixl 
* @date 2016-5-13 下午3:01:36 
*
 */
@Controller
@RequestMapping("/maintablechart")
public class MainTableChartController {
	
	private MainTableService mainTableService;
	private BaseService baseService;
	private MainTableChartService mainTableChartService;
	private MainTableColumnService mainTableColumnService;
	
	@RequestMapping(value="/maintablechart_list", method={RequestMethod.GET, RequestMethod.POST})
	public String mainTableChartList(@RequestParam("maintableid") String maintableid,Model model) throws Exception {
		
		MainTable maintable = mainTableService.findByMainTableId(maintableid);
		model.addAttribute("maintable", maintable);

		List<MainTableChart> listchart = mainTableChartService.findByMainTableId(maintableid);
		model.addAttribute("listchart", listchart);
		return "database/maintablechart/maintablechart_list";
	}
	@RequestMapping(value="/maintablechart_edit", method={RequestMethod.GET, RequestMethod.POST})
	public String maintablechart_edit(@RequestParam("maintableid") String maintableid,
									  @RequestParam(value="chartid" ,required=false) String chartid,
								      Model model) throws Exception {
		MainTable maintable = mainTableService.findByMainTableId(maintableid);
		model.addAttribute("maintable", maintable);
		List<MainTableColumn> list = mainTableColumnService.findByMainTableId(maintableid);
		model.addAttribute("list", list);
		if (StringHelper.isNotEmpty(chartid)) {
			MainTableChart mainTableChart = mainTableChartService.findByMainTableChartId(chartid);
			model.addAttribute("mainTableChart", mainTableChart);
		}
		return "database/maintablechart/maintablechart_detail";
	}
	
	@RequestMapping(value="/maintablechart_add", method={RequestMethod.GET, RequestMethod.POST})
	public String maintablechart_add(MainTableChart mainTableChart) throws Exception {
		if(mainTableChart.getChartid()==null)
			mainTableChartService.add(mainTableChart);
		else
			mainTableChartService.update(mainTableChart);
		return "redirect:/maintablechart/maintablechart_list.izhbg?maintableid="+mainTableChart.getTableid();
	}
	@RequestMapping(value="/maintablechart_delete", method={RequestMethod.GET, RequestMethod.POST})
	public String maintablechart_delete(@RequestParam("chartid") String chartid,
										@RequestParam("maintableid") String maintableid) throws Exception {
		mainTableChartService.delete(chartid);
		return "redirect:/maintablechart/maintablechart_list.izhbg?maintableid="+maintableid;
	}
	@Resource
	public void setMainTableService(MainTableService mainTableService) {
		this.mainTableService = mainTableService;
	}
	@Resource
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}
	@Resource
	public void setMainTableChartService(MainTableChartService mainTableChartService) {
		this.mainTableChartService = mainTableChartService;
	}
	@Resource
	public void setMainTableColumnService(
			MainTableColumnService mainTableColumnService) {
		this.mainTableColumnService = mainTableColumnService;
	}
	
	
	
}

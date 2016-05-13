package com.izhbg.typz.database.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.izhbg.typz.base.common.service.ControllerException;
import com.izhbg.typz.database.dto.MainTable;
import com.izhbg.typz.database.dto.MainTableColumn;
import com.izhbg.typz.database.service.MainTableColumnService;
import com.izhbg.typz.database.service.MainTableService;

/**
 * 
* @ClassName: MainTableColumnController 
* @Description: 主表列信息 
* @author caixl 
* @date 2016-5-12 下午3:17:49 
*
 */
@Controller
@RequestMapping("/maintablecolumn")
public class MainTableColumnController {
	
	private MainTableService mainTableService;
	private MainTableColumnService mainTableColumnService;
	/**
	 * 主表列 列表
	 * @param maintableid
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/maintablecolumn_list", method={RequestMethod.GET, RequestMethod.POST})
	public String maintablecolumn_list(@RequestParam("maintableid") String maintableid,Model model) throws Exception {
		
		MainTable maintable = mainTableService.findByMainTableId(maintableid);
		model.addAttribute("maintable", maintable);
		
		List<MainTableColumn> list = mainTableColumnService.findByMainTableId(maintableid);
		model.addAttribute("list", list);
		return "database/maintablecolumn/maintablecolumn_list";
	}

	/**
	 * 加载主表列信息
	 * @param maintableid
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/load_miantablecolumn", method={RequestMethod.GET, RequestMethod.POST})
	public String load_miantablecolumn(@RequestParam("maintableid") String maintableid,Model model)throws Exception {
		mainTableColumnService.saveLoadMainTableColumn(maintableid);
		return "redirect:/maintablecolumn/maintablecolumn_list.izhbg?maintableid="+maintableid;	
	}
	/**
	 * 添加主表列页面
	 * @param maintableid
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/edit_miantablecolumn", method={RequestMethod.GET, RequestMethod.POST})
	public String edit_miantablecolumn(@RequestParam(value="maintableid",required=true) String maintableid,
									 @RequestParam(value="maintablecolumnid",required=false) String maintableColumnId,
									 Model model) throws Exception {
		if(StringHelper.isEmpty(maintableid))
			throw new ControllerException("参数为空,跳转失败");
		if(StringHelper.isNotEmpty(maintableColumnId)){
			MainTableColumn mainTableColumn = mainTableColumnService.findByMainTableColumnId(maintableColumnId);
			model.addAttribute("maintablecolumn", mainTableColumn);
		}
		
		model.addAttribute("maintableid", maintableid);
		List<MainTableColumn> columnlist = mainTableColumnService.findByMainTableId(maintableid);
		model.addAttribute("columnlist", columnlist);
		return "database/maintablecolumn/maintablecolumn_detial";
	}
	/**
	 * 删除主表列信息
	 * @param maintablecolumnid
	 * @param maintableid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/remove_miantablecolumn", method={RequestMethod.GET, RequestMethod.POST})
	public String remove_miantablecolumn(@RequestParam(value="maintablecolumnid",required=true) String maintablecolumnid,
										@RequestParam(value="maintableid",required=true) String maintableid) throws Exception {
		if(StringHelper.isEmpty(maintableid)||StringHelper.isEmpty(maintablecolumnid))
			throw new ControllerException("参数为空，删除主表列信息失败");
		
		mainTableColumnService.deleteByMainTableColumnId(maintablecolumnid);
		return "redirect:/maintablecolumn/maintablecolumn_list.izhbg?maintableid="+maintableid;	
	}
	/**
	 * 添加主表列信息
	 * @param mainTableColumn
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/add_miantablecolumn", method={RequestMethod.GET, RequestMethod.POST})
	public String add_miantablecolumn(MainTableColumn mainTableColumn) throws Exception {
		if(mainTableColumn==null)
			throw new ControllerException("参数为空,添加列表信息失败");
		if(mainTableColumn.getColumnid()==null)
			mainTableColumnService.add(mainTableColumn);
		else
			mainTableColumnService.update(mainTableColumn);
		return "redirect:/maintablecolumn/maintablecolumn_list.izhbg?maintableid="+mainTableColumn.getMaintableid();
	}
	
	@RequestMapping(value="/maintablecolumn_set", method={RequestMethod.GET, RequestMethod.POST})
	public String mainTableColumnListSet(@RequestParam("maintableid") String maintableid,Model model) throws Exception {
		if(StringHelper.isEmpty(maintableid))
			throw new ControllerException("参数为空,设置列表信息失败");
		MainTable maintable = mainTableService.findByMainTableId(maintableid);
		model.addAttribute("maintable", maintable);
		List<MainTableColumn> list = mainTableColumnService.findByMainTableId(maintableid);
		model.addAttribute("list", list);
		return "database/maintablecolumn/maintablecolumn_set";
	}
	@RequestMapping(value="/add_maintablecolumnlist", method={RequestMethod.GET, RequestMethod.POST})
	public  String add_maintablecolumnlist(HttpServletRequest request) throws Exception {
		String maintableid = request.getParameter("maintableid");
		if(StringHelper.isEmpty(maintableid))
			throw new ControllerException("参数为空,设置主表列信息失败");
		mainTableColumnService.updateMainTableColumns(request);
		return "redirect:/maintablecolumn/maintablecolumn_list.izhbg?maintableid="+maintableid; 
	}
	@Resource
	public void setMainTableService(MainTableService mainTableService) {
		this.mainTableService = mainTableService;
	}
	@Resource
	public void setMainTableColumnService(
			MainTableColumnService mainTableColumnService) {
		this.mainTableColumnService = mainTableColumnService;
	}
	
	
}

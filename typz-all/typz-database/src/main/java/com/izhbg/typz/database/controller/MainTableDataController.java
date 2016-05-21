package com.izhbg.typz.database.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.common.service.ControllerException;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.StringUtils;
import com.izhbg.typz.database.dto.MainTable;
import com.izhbg.typz.database.service.MainTableDataService;

/**
 * maintable 对应的表单操作
* @author caixl 
* @date 2016-5-16 上午9:26:28 
*
 */
@Controller
@RequestMapping("/maintabledata")
public class MainTableDataController {
	
	private MainTableDataService mainTableDataService;
	
	/**
	 * 列表
	 * @param parameterMap
	 * @param model
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/maintabledata_pagelist", method={RequestMethod.GET, RequestMethod.POST})
	public String maintabledata_pagelist(@RequestParam Map<String, Object> parameterMap, Model model,@ModelAttribute Page page) throws Exception {
		
		String forwordpage = parameterMap.get("forwordpage")==null?"":parameterMap.get("forwordpage")+"";
		Map<String,Object> resultMap = mainTableDataService.mainTableData_pageList(parameterMap, page);
		
		List<Map<String, Object>> resoultList = (List<Map<String, Object>>) resultMap.get("resoultList");
		List<Map<String, Object>> viewList = (List<Map<String, Object>>) resultMap.get("viewList");
		List<Map<String, Object>> queryList = (List<Map<String, Object>>) resultMap.get("queryList");
		MainTable maintable = (MainTable) resultMap.get("maintable");
		page = (Page) resultMap.get("page");
		page.setResult(resoultList);
		
		model.addAttribute("page", page);
		model.addAttribute("viewList", viewList);
		model.addAttribute("maintable", maintable);
		model.addAttribute("queryList", queryList);
		if (forwordpage == null || forwordpage.length() == 0) {
			forwordpage = "database/maintabledata/maintabledata_pagelist";
		}
		return forwordpage;
	}
	/**
	 * 编辑页
	 * @param paameterMap
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/maintabledata_edit", method={RequestMethod.GET, RequestMethod.POST})
	public String maintabledata_edit(@RequestParam Map<String,Object> paameterMap,Model model)throws Exception{
		String tableName = StringUtils.getString(paameterMap.get("tableName"));
		if(StringHelper.isEmpty(tableName))
			throw new ControllerException("参数为空，获取编辑页失败");
		Map<String,Object> resultMap = mainTableDataService.mainTableData_editData(paameterMap);
		model.addAttribute("realTableMap", resultMap.get("realTableMap"));
		model.addAttribute("maintable", resultMap.get("maintable"));
		model.addAttribute("detailList", resultMap.get("detailList"));
		return "database/maintabledata/maintabledata_edit";
	}
	/**
	 * 查看页
	 * @param paameterMap
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/maintabledata_view", method={RequestMethod.GET, RequestMethod.POST})
	public String maintabledata_view(@RequestParam Map<String,Object> paameterMap,Model model)throws Exception{
		String tableName = StringUtils.getString(paameterMap.get("tableName"));
		if(StringHelper.isEmpty(tableName))
			throw new ControllerException("参数为空，获取编辑页失败");
		Map<String,Object> resultMap = mainTableDataService.mainTableData_editData(paameterMap);
		model.addAttribute("realTableMap", resultMap.get("realTableMap"));
		model.addAttribute("maintable", resultMap.get("maintable"));
		model.addAttribute("detailList", resultMap.get("detailList"));
		return "database/maintabledata/maintabledata_view";
	}
	/**
	 * 保存
	 * @param paameterMap
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/maintabledata_save", method={RequestMethod.GET, RequestMethod.POST})
	public String maintabledata_save(@RequestParam Map<String,Object> paameterMap,Model model)throws Exception{
		mainTableDataService.mainTableData_save(paameterMap);
		return "redirect:/maintabledata/maintabledata_pagelist.izhbg?tableName="+paameterMap.get("tableName");
	}
	/**
	 * 删除
	 * @param paameterMap
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/maintabledata_remove", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String maintabledata_remove(HttpServletRequest request,Model model)throws Exception{
		String tableName = request.getParameter("tableName");
		String[] realtableids = request.getParameterValues("realtableid");
		if(StringHelper.isEmpty(tableName)||realtableids==null||realtableids.length==0){
			throw new ControllerException("参数为空,删除表单数据失败");
		}
		mainTableDataService.mainTableData_remove(tableName,realtableids);
		JSONObject jsobj = new JSONObject();
		jsobj.put("result", "0");
		jsobj.put("msg", "操作成功");
		return jsobj.toString();
	}
	
	@Resource
	public void setMainTableDataService(MainTableDataService mainTableDataService) {
		this.mainTableDataService = mainTableDataService;
	}
	
	
}

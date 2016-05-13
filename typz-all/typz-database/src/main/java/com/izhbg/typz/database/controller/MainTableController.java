package com.izhbg.typz.database.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.izhbg.typz.base.common.service.ControllerException;
import com.izhbg.typz.base.commondto.TreeObject;
import com.izhbg.typz.base.mapper.BeanMapper;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.CommonUtil;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.database.dto.MainTable;
import com.izhbg.typz.database.dto.MainTableColumn;
import com.izhbg.typz.database.dto.MainTableType;
import com.izhbg.typz.database.manager.MainTableColumnManager;
import com.izhbg.typz.database.manager.MainTableManager;
import com.izhbg.typz.database.manager.MainTableTypeManager;
import com.izhbg.typz.database.service.MainTableColumnService;
import com.izhbg.typz.database.service.MainTableService;
import com.izhbg.typz.database.service.MainTableTypeService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;

/**
 * 
* @ClassName: MainTableController 
* @Description: 数据库表管理
* @author caixl 
* @date 2016-5-11 下午2:09:28 
*
 */
@Controller
@RequestMapping("/maintable")
public class MainTableController {
	
	private BeanMapper beanMapper = new BeanMapper();
	private MainTableService mainTableService;
	private MainTableColumnService mainTableColumnService;
	private MainTableTypeService mainTableTypeService;
	/**
	 * 查询主列表
	 * @param page
	 * @param parameterMap
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/maintable_list", method={RequestMethod.GET, RequestMethod.POST})
	public String maintable_list(@ModelAttribute  Page page,
            @RequestParam Map<String, Object> parameterMap, Model model) throws Exception {
		String type = parameterMap.get("type")==null?"":parameterMap.get("type")+"";
		
		String appId = SpringSecurityUtils.getCurrentUserAppId();
		
		List<MainTableType> mainTableTypes = mainTableTypeService.findByAppId(appId);
		model.addAttribute("result", getMainTableTypeTreeJson(mainTableTypes));
		model.addAttribute("typelist", mainTableTypes);
		
		if(mainTableTypes!=null&&mainTableTypes.size()>0){
			MainTableType mainTableType = mainTableTypes.get(0);
			if(StringHelper.isEmpty(type)){
				type =  mainTableType.getId();
			}
			page = mainTableService.findByAppIdAndTypeId(appId, type, page);
			model.addAttribute("page", page);
		}
		model.addAttribute("parameterMap", parameterMap);
		
		return "database/maintable/maintable_list";
	}
	/**
	 * 拷贝主表信息
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/maintable_copy", method={RequestMethod.GET, RequestMethod.POST})
	public String maintable_add(HttpServletRequest request) throws Exception {
		String formname = request.getParameter("formname");
		String formtype= request.getParameter("formtype");
		String fromid= request.getParameter("fromid");
		String appId = SpringSecurityUtils.getCurrentUserAppId();
		
		MainTable maintable = null;
		if(StringHelper.isEmpty(formname)){
			throw new ControllerException("参数为空,复制表单失败");
		}
		String sql = "";
		//如果 fromid不为空 找出对应的 maintable 并复制 maintble的其他信息
		if(StringHelper.isNotEmpty(fromid)){
			maintable = mainTableService.copy(fromid, formname);
		}else{
			maintable = new MainTable();
			maintable.setTableCName(formname);
			maintable.setType(formtype);
			maintable.setAppId(appId);
			mainTableService.add(maintable);
		}
		return "redirect:/maintable/maintable_detail.izhbg?maintableid="+maintable.getTableid();	
	}
	/**
	 * 主表添加或更新
	 * @param request
	 * @param maintable
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/maintable_addOrupdate", method={RequestMethod.GET, RequestMethod.POST})
	public String maintable_addOrupdate(HttpServletRequest request,MainTable maintable) throws Exception {
		if(StringHelper.isEmpty(maintable.getTableName())||StringHelper.isEmpty(maintable.getSql()))
			throw new ControllerException("参数为空,添加或更新主表失败");
		if(maintable.getTableid()==null){
			maintable.setTableid(IdGenerator.getInstance().getUniqTime());
			mainTableService.add(maintable);
		}else{
			MainTable maintablef = mainTableService.findByMainTableId(maintable.getTableid()+"");
			beanMapper.copy(maintable, maintablef);
			mainTableService.update(maintablef);
		}
		return "redirect:/maintable/maintable_list.izhbg";	
	}
	/**
	 * 详情
	 * @param map
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/maintable_detail", method={RequestMethod.GET, RequestMethod.POST})
	public String maintable_detail(Map<String, Object> map,HttpServletRequest request,Model model) throws Exception {
		String maintableid = request.getParameter("maintableid");
		if(StringHelper.isEmpty(maintableid))
			throw new ControllerException("请求参数为空，获取主表信息失败。");
		
		MainTable maintable = mainTableService.findByMainTableId(maintableid);
		List<MainTableColumn> columnlist = mainTableColumnService.findByMainTableId(maintableid);
		String appId = SpringSecurityUtils.getCurrentUserAppId();
		List<MainTableType> mainTableTypes = mainTableTypeService.findByAppId(appId);
		
		model.addAttribute("columnlist", columnlist);
		model.addAttribute("maintable", maintable);
		model.addAttribute("typelist", mainTableTypes);
		return "database/maintable/maintable_detail";
	}
	/**
	 * 保存
	 * @param MainTableType
	 * @return
	 */
	@RequestMapping("maintabletype_save")
    public String save(@ModelAttribute MainTableType mainTableType)throws Exception {
		if(mainTableType==null)
			throw new ControllerException("参数为空,添加主表类型失败");
        String appId = SpringSecurityUtils.getCurrentUserAppId();
        String id = mainTableType.getId();
        if (StringHelper.isNotEmpty(id)) {
        	mainTableTypeService.update(mainTableType);
        } else {
        	mainTableType.setAppId(appId);
        	mainTableType.setId(IdGenerator.getInstance().getUniqTime()+"");
        	mainTableTypeService.add(mainTableType);
        }
        return "redirect:/maintable/maintable_list.izhbg";
    }
	/**
	 * 删除
	 * @param selectedItem
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("maintable_remove")
    public String remove(@RequestParam("maintableid") String maintableid,HttpServletRequest request) throws Exception {
       if(StringHelper.isEmpty(maintableid))
    	   throw new ControllerException("参数为空，删除主表列信息失败");
        mainTableService.delete(maintableid);
        return "redirect:/maintable/maintable_list.izhbg";
    }
	/**
	 * 删除
	 * @param selectedItem
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("maintabletype_remove")
	public String remove(@RequestParam("selectedItem") List<String> selectedItem)throws Exception {
		if(selectedItem==null)
			throw new ControllerException("参数为空,删除主表类型失败");
		mainTableTypeService.deleteByIds(selectedItem);
		return "redirect:/maintable/maintable_list.izhbg";
	}
	/**
	 * 组装树结构
	 * @param mainTableTypes
	 * @return
	 */
	private String getMainTableTypeTreeJson(List<MainTableType> mainTableTypes){
		List<TreeObject> tlist = new ArrayList<TreeObject>();
		for (MainTableType mainTableType : mainTableTypes) {
			TreeObject to = new TreeObject();
			to.setId(mainTableType.getId());
			to.setName(mainTableType.getName());
			to.setPid(mainTableType.getPid());
			tlist.add(to);
		}
		return CommonUtil.getTreeJson(tlist);
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
	@Resource
	public void setMainTableTypeService(MainTableTypeService mainTableTypeService) {
		this.mainTableTypeService = mainTableTypeService;
	}
	
	
	
}

package com.izhbg.typz.sso.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.swing.Spring;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.common.service.ControllerException;
import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.StringUtils;
import com.izhbg.typz.sso.annotation.SystemControllerLog;
import com.izhbg.typz.sso.auth.UserAuthDTO;
import com.izhbg.typz.sso.auth.dto.TXtGnzy;
import com.izhbg.typz.sso.auth.dto.TXtGnzyQuery;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.dto.TXtYy;
import com.izhbg.typz.sso.auth.manager.TXtGnjsZyManager;
import com.izhbg.typz.sso.auth.manager.TXtGnzyManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.auth.service.TXtGnzyService;
import com.izhbg.typz.sso.auth.service.TXtJgService;
import com.izhbg.typz.sso.auth.service.TXtYhService;
import com.izhbg.typz.sso.auth.service.TXtYyService;
import com.izhbg.typz.sso.common.TreeNode;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
import com.mysql.jdbc.Messages;

/**
 * 
* @ClassName: FuncController 
* @Description: 功能管理
* @author caixl 
* @date 2016-5-9 下午5:25:27 
*
 */
@Controller
@RequestMapping("/fun")
public class FuncController {
    
	private TXtGnzyService tXtGnzyService;
	private TXtJgService tXtJgService;
	private TXtYyService tXtYyService;
	private TXtYhService tXtYhService;
	
	
	/**
	 * 功能列表
	 * @param page
	 * @param parameterMap
	 * @param model
	 * @return
	 */
	@RequestMapping("fun_list")
	 public String list(@ModelAttribute  Page page,
	            @ModelAttribute TXtGnzyQuery tXtGnzyQuery, Model model)throws Exception {
		
		String currentAppId= tXtGnzyQuery.getCurrentAppId();
		if(StringHelper.isEmpty(currentAppId))
		    tXtGnzyQuery.setCurrentAppId(SpringSecurityUtils.getCurrentUserAppId());
		List<TXtYy> tXtYyList = tXtYyService.queryAll();
		model.addAttribute("systems", tXtYyList);
		model.addAttribute("result", tXtGnzyService.getFunTreeJson(tXtGnzyQuery.getCurrentAppId()));
		model.addAttribute("page",tXtGnzyService.queryPageList(page, tXtGnzyQuery));
		model.addAttribute("parameterMap", tXtGnzyQuery);
		return "admin/func/dirfunc";
	 }
	
	/**
	 * 功能子节点
	 * @param parameterMap
	 * @param model
	 * @return
	 */
	@RequestMapping("fun-item")
	 public String item( @RequestParam Map<String, Object> parameterMap, Model model) {
		String gnDm= parameterMap.get("gnDm")==null?"":parameterMap.get("gnDm").toString();
		if(StringHelper.isNotEmpty(gnDm)){
			UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
			TreeNode nods =user.getTreeNode()==null?null:(TreeNode)user.getTreeNode();
			if(nods!=null&&nods.getChildren()!=null){
				for(TreeNode node:nods.getChildren()){
					if(node.getChildren()!=null){
						for(TreeNode nod:node.getChildren()){
							if(gnDm.equals(nod.getCode()))
							{
								model.addAttribute("nodes", nod.getChildren());
							}
						}
					}
					
				}
			}
			model.addAttribute("gnDm", gnDm);
		}
		return "admin/func/fun-item";
	 }
	/**
	 * 功能编辑
	 * @param parameterMap
	 * @param model
	 * @param currentAppId
	 * @return
	 */
	@RequestMapping("fun-edit")
	@SystemControllerLog(description = "编辑功能节点")
	public String funEdit(@RequestParam Map<String, Object> parameterMap, 
			      Model model,
			      String currentAppId) throws Exception {
	    	if(StringHelper.isEmpty(currentAppId))
	    	    currentAppId = SpringSecurityUtils.getCurrentUserAppId();
		String gnDm= parameterMap.get("gnDm")==null?"":parameterMap.get("gnDm").toString();
		TXtGnzy func = null;
		String result = null;
		if(StringHelper.isNotEmpty(gnDm)){
			func = tXtGnzyService.queryById(gnDm);
		}else{
			func = new TXtGnzy();
			func.setSjgnDm("-1");
			func.setAppId(currentAppId);
		}
		TXtYh currentYh = tXtYhService.findByYhId(SpringSecurityUtils.getCurrentUserId());
		TXtGnzy txtGnzy = tXtGnzyService.queryById(func.getSjgnDm());
		model.addAttribute("txtYy", tXtYyService.queryAll());
		model.addAttribute("result", tXtGnzyService.getFunTreeJson(currentAppId));
		model.addAttribute("currentAppId", currentAppId);
		model.addAttribute("func", func);
		model.addAttribute("currentYh", currentYh);
		model.addAttribute("sjgnzyname", txtGnzy.getGnMc());
		return "admin/func/getfunc";
	}
	
	@RequestMapping(value="getSubFunc",method=RequestMethod.POST)
	public @ResponseBody String getSubFunc(@RequestParam Map<String, Object> parameterMap) throws Exception{
		String id = StringUtils.getString(parameterMap.get("id"));
		String appId = StringUtils.getString(parameterMap.get("appId"));
		if(StringHelper.isEmpty(id))
		    throw new ControllerException("参数为空，获取子节点失败");
		if(appId==null){
			appId = SpringSecurityUtils.getCurrentUserAppId();
		}
		return tXtGnzyService.getSubFunc(id.toString(),appId).toString();
	}
	@RequestMapping(value="getFuncCheckTree",method=RequestMethod.POST)
	public @ResponseBody String getFuncCheckTree(@RequestParam Map<String, Object> parameterMap) throws Exception{
		String dm = StringUtils.getString(parameterMap.get("dm"));
		if(StringHelper.isEmpty(dm))
		    throw new ControllerException("参数为空,获取节点失败");
		String gnDm = StringUtils.getString(parameterMap.get("gnDm"));
		return tXtGnzyService.getSubFuncCheck(dm.toString(), gnDm.toString(),SpringSecurityUtils.getCurrentUserAppId()).toString();
	}
	@RequestMapping(value="validateGnDm",method=RequestMethod.POST)
	public @ResponseBody String validateGnDm(@RequestParam String gnDm) throws Exception{
		
		String result = "yes";
		if(StringHelper.isEmpty(gnDm))
		    throw new ControllerException("参数为空，验证失败");
		TXtGnzy tXtGnzy = tXtGnzyService.queryById(gnDm);
		if(tXtGnzy!=null)
		    result = "no";
		return result;
	}
	@SystemControllerLog(description = "添加功能节点")
	@RequestMapping(value="addFun",method=RequestMethod.POST)
	public String addFun(TXtGnzy func,String[] checkdel, Model model) throws Exception{
		if(StringHelper.isEmpty(func.getGnDm())
				||StringHelper.isEmpty(func.getGnMc())
				||StringHelper.isEmpty(func.getAppId())
				||StringHelper.isEmpty(func.getSjgnDm())){
			throw new ControllerException("参数为空,添加功能节点失败");
		}
		tXtGnzyService.add(func);
		return "redirect:/fun/fun_list.izhbg?sjgnDm="+func.getSjgnDm()+"&appId="+func.getAppId();
	}
	@SystemControllerLog(description = "更新功能节点")
	@RequestMapping(value="updateFun",method=RequestMethod.POST)
	public String updateOrg(TXtGnzy func,String[] checkdel, Model model,String currentAppId) throws Exception{
		
		if(StringHelper.isEmpty(func.getGnDm())
				||StringHelper.isEmpty(func.getGnMc())
				||StringHelper.isEmpty(func.getAppId())
				||StringHelper.isEmpty(func.getSjgnDm())){
		    throw new ControllerException("参数为空,更新功能节点失败");
		}
		tXtGnzyService.update(func);
		return "redirect:/fun/fun_list.izhbg?sjgnDm="+func.getSjgnDm()+"&appId="+func.getAppId()+"&currentAppId="+currentAppId;
		
	}
	
	@RequestMapping(value="deleteFun",method=RequestMethod.POST)
	@SystemControllerLog(description = "删除功能节点")
	public @ResponseBody  String deleteFun(String[] checkdel){
		String result="";
		try{
			if(checkdel == null || checkdel.length < 1){
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
			}
			tXtGnzyService.deleteByIds(checkdel);
			result = "sucess";
		} catch (Exception ex) {
		}
		return result;
	}
	@RequestMapping(value="updFunStatus",method=RequestMethod.POST)
	@SystemControllerLog(description = "更新功能节点状态")
	public @ResponseBody  String updFunStatus(String[] checkdel) throws Exception{
		if(checkdel == null || checkdel.length < 1){
			return null;
		}
		tXtGnzyService.updFunStatus(checkdel);
		return "sucess";
	}
	  // ~ ======================================================================
	@Resource
	public void settXtGnzyService(TXtGnzyService tXtGnzyService) {
	    this.tXtGnzyService = tXtGnzyService;
	}
	@Resource
	public void settXtJgService(TXtJgService tXtJgService) {
	    this.tXtJgService = tXtJgService;
	}
	@Resource
	public void settXtYyService(TXtYyService tXtYyService) {
	    this.tXtYyService = tXtYyService;
	}
	@Resource
	public void settXtYhService(TXtYhService tXtYhService) {
	    this.tXtYhService = tXtYhService;
	}
	
	
	
	
	
	
}

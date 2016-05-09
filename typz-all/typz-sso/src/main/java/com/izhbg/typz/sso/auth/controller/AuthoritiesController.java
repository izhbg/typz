package com.izhbg.typz.sso.auth.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.sso.auth.dto.TXtAuthorities;
import com.izhbg.typz.sso.auth.dto.TXtYh;
import com.izhbg.typz.sso.auth.manager.TXtAuthoritiesManager;
import com.izhbg.typz.sso.auth.manager.TXtYhManager;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
import com.mysql.jdbc.Messages;

/**
 * 
* @ClassName: authoritiesController 
* @Description: 权限管理
* @author caixl 
* @date 2016-5-9 上午9:35:25 
*
 */
@Controller
@RequestMapping("/authorities")
public class AuthoritiesController
{
	private TXtYhManager tXtYhManager;
	
	private TXtAuthoritiesManager tXtAuthoritiesManager;
	
	/**
	 * 权限列表
	 * @param page
	 * @param parameterMap
	 * @param model
	 * @return
	 */
	@RequestMapping("authorities_list")
	 public String list(@ModelAttribute  Page page,
	            @RequestParam Map<String, Object> parameterMap, Model model) {
		
		String appId = SpringSecurityUtils.getCurrentUserAppId();
		
		StringBuffer str = new StringBuffer(" from TXtAuthorities where appId=:appId");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appId", appId);
		
		page = tXtAuthoritiesManager.pagedQuery(str.toString(), page.getPageNo(), page.getPageSize(), params);
		
		model.addAttribute("page", page);
		model.addAttribute("parameterMap", parameterMap);
		return "admin/authorities/authorities_list";
	}
	/**
	 * 编辑权限信息
	 * @param authoritiesId
	 * @param model
	 * @param parameterMap
	 * @return
	 */
	@RequestMapping("authorities_edit")
	public String roleEdit(String authorityId, 
						   Model model,
						   @RequestParam Map<String, Object> parameterMap) {
		try {
			
			TXtAuthorities tXtAuthorities = null;
			if(StringHelper.isNotEmpty(authorityId)){
				tXtAuthorities = tXtAuthoritiesManager.findUniqueBy("authorityId", authorityId);
			}else{
				tXtAuthorities = new TXtAuthorities();
			}
			model.addAttribute("tXtAuthorities", tXtAuthorities);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/authorities/authorities_edit";
	}
	/**
	 * 添加或更新权限信息
	 * @param tXtauthorities
	 * @param model
	 * @param appId2
	 * @return
	 */
	@RequestMapping(value="authorities_addORupdate",method=RequestMethod.POST)
	public String addRole(TXtAuthorities tXtauthorities, Model model){
		
		if(StringHelper.isEmpty(tXtauthorities.getAuthorityName())
				||StringHelper.isEmpty(tXtauthorities.getAuthorityDesc())){
			return null;
		}
		
		if(StringHelper.isEmpty(tXtauthorities.getAuthorityId())){
			tXtauthorities.setAuthorityId(IdGenerator.getInstance().getUniqTime()+"");
			tXtauthorities.setAppId(SpringSecurityUtils.getCurrentUserAppId());
			tXtAuthoritiesManager.save(tXtauthorities);
		}else{
			tXtauthorities.setAppId(SpringSecurityUtils.getCurrentUserAppId());
			tXtAuthoritiesManager.update(tXtauthorities);
		}
		
		return "redirect:/authorities/authorities_list.izhbg";
	}
	/**
	 * 删除权限
	 * @param checkdel
	 * @return
	 */
	@RequestMapping(value="authorities_dell",method=RequestMethod.POST)
	public @ResponseBody  String deleteauthorities(String[] checkdel){
		String result="";
		try{
			if(checkdel == null || checkdel.length < 1){
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));	
			}
			List<String> lst = new ArrayList<String>();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtAuthorities> items = tXtAuthoritiesManager.findByIds(lst);
			
			for(Object o : items)
				tXtAuthoritiesManager.remove(o);
			result = "sucess";
		} catch (Exception ex) {
		}
		return result;
	}
	/**
	 * 更新状态
	 * @param checkdel
	 * @return
	 */
	@RequestMapping(value="authorities_updStatus",method=RequestMethod.POST)
	public @ResponseBody  String updRoleStatus(String[] checkdel){
		String result="";
		try {
			
			if (checkdel == null || checkdel.length < 1) {
				result = Ajax.JSONResult(Constants.RESULT_CODE_ERROR, Messages.getString("systemMsg.fieldEmpty"));
			}
			List lst = new ArrayList();
			for(String s : checkdel) 
				lst.add(s);
			List<TXtAuthorities> itemLst = (List<TXtAuthorities>)tXtAuthoritiesManager.findByIds(lst); 
			
			for(TXtAuthorities item : itemLst ){
				if(item.getEnabled()!=null&&item.getEnabled()==2){
					item.setEnabled(1);
				}else{
					item.setEnabled(2);
				}
				tXtAuthoritiesManager.update(item);
			}
			result = "sucess";
		} catch (Exception ex) {
			
		}
		
		return result;
	}
	
	@Resource	
	public void settXtYhManager(TXtYhManager tXtYhManager)
	{
		this.tXtYhManager = tXtYhManager;
	}
	@Resource	
	public void settXtauthoritiesManager(TXtAuthoritiesManager tXtauthoritiesManager)
	{
		this.tXtAuthoritiesManager = tXtauthoritiesManager;
	}
	
	

}

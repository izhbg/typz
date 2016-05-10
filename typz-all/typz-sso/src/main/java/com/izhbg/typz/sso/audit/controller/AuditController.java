package com.izhbg.typz.sso.audit.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.izhbg.typz.base.page.Page;
import com.izhbg.typz.sso.audit.manager.AuditLogManager;
import com.izhbg.typz.sso.util.SpringSecurityUtils;

@Controller
@RequestMapping("/audit")
public class AuditController
{
	
	private AuditLogManager auditLogManager;
	
	/**
	 * 日志列表
	 * @param page
	 * @param parameterMap
	 * @param model
	 * @return
	 */
	@RequestMapping("audit_list")
	 public String list(@ModelAttribute  Page page,
	            @RequestParam Map<String, Object> parameterMap, Model model) {
		
		String appId = SpringSecurityUtils.getCurrentUserAppId();
		
		StringBuffer str = new StringBuffer(" from AuditLog where appId=:appId");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appId", appId);
		
		page = auditLogManager.pagedQuery(str.toString(), page.getPageNo(), page.getPageSize(), params);
		
		model.addAttribute("page", page);
		model.addAttribute("parameterMap", parameterMap);
		return "admin/audit/audit_list";
	}

	@Resource
	public void setAuditLogManager(AuditLogManager auditLogManager)
	{
		this.auditLogManager = auditLogManager;
	}
	

}

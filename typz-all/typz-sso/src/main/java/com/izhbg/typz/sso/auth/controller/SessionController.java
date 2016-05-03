package com.izhbg.typz.sso.auth.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/session")
public class SessionController {

	@RequestMapping(value="setSession",method=RequestMethod.POST)
	public @ResponseBody String getRoleOrganCheckTree(@RequestParam Map<String, Object> parameterMap,HttpSession session){
		Object currentTopMenu = parameterMap.get("currentTopMenu");
		Object currentSubMenu = parameterMap.get("currentSubMenu");
		Object currentTopMenuName = parameterMap.get("currentTopMenuName");
		Object currentSubMenuName = parameterMap.get("currentSubMenuName");
		Object submenutext = parameterMap.get("submenutext");
		Object url = parameterMap.get("url");
		Object gnDm = parameterMap.get("gnDm");
		if(gnDm!=null)
			session.setAttribute("gnDm", gnDm);
		if(currentTopMenu!=null)
			session.setAttribute("currentTopMenu", currentTopMenu);
		if(currentSubMenu!=null)
			session.setAttribute("currentSubMenu", currentSubMenu);
		if(currentTopMenuName!=null)
			session.setAttribute("currentTopMenuName", currentTopMenuName);
		if(currentSubMenuName!=null)
			session.setAttribute("currentSubMenuName", currentSubMenuName);
		if(submenutext!=null)
			session.setAttribute("submenutext", submenutext);
		else
			session.setAttribute("submenutext", null);
		
		if(url!=null)
			session.setAttribute("url", url);
		return "sucess";
	}
}

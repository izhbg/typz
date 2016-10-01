package com.izhbg.typz.im.person.admin.restf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.izhbg.typz.base.common.redis.RedisUtil;

@Controller
@RequestMapping("/person/im/")
public class LoginController {
	
	
	@Autowired
	private RedisUtil redisUti;
	
	
	@RequestMapping("login")
	@ResponseBody
	public String login() throws Exception {
		
		redisUti.add("test", "123", "22222",2);
		
		System.out.println(redisUti.getById("test", "123"));
		
		return "test";
	}
	@RequestMapping("logintest")
	@ResponseBody
	public String logintest() throws Exception {
		
		System.out.println(redisUti.getById("test", "123"));
		
		return "test===="+redisUti.getById("test", "123");
	}
}

package com.izhbg.typz.sso.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/water")
public class WaterController {
	@RequestMapping("water")
	 public String list() {
		return "water/water";
	 }
}

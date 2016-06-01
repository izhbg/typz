package com.izhbg.typz.mobile.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/m/")
public class MobileController {
	
	@RequestMapping("index")
    public String dashboard() {
        return "client/index";
    }
}

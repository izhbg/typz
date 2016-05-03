package com.izhbg.typz.sso.auth.controller;

import org.apache.log4j.Logger;  
import org.springframework.stereotype.Controller;  
import org.springframework.ui.ModelMap;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.RequestParam;  
/**
 *    
* @ClassName: SSOController 
* @Description: 登录验证
* @author caixl 
* @date 2016-4-22 下午4:16:47 
*
 */
@Controller  
@RequestMapping("auth")  
public class SSOController {  
   
    protected static Logger logger = Logger.getLogger("controller");  
   
    /** 
     * 指向登录页面 
     */  
    @RequestMapping(value = "/login", method = RequestMethod.GET)  
    public String getLoginPage(  
            @RequestParam(value = "error", required = false) boolean error,  
            ModelMap model) {  
        logger.debug("Received request to show login page");  
   
        if (error == true) {  
            // Assign an error message  
            model.put("error",  
                    "You have entered an invalid username or password!");  
        } else {  
            model.put("error", "");  
        }  
        return "auth/loginpage";  
   
    }  
   
    /** 
     * 指定无访问额权限页面 
     *  
     * @return 
     */  
    @RequestMapping(value = "/denied", method = RequestMethod.GET)  
    public String getDeniedPage() {  
   
        logger.debug("Received request to show denied page");  
   
        return "auth/deniedpage";  
    }  
}
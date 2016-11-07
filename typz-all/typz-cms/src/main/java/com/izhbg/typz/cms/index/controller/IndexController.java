package com.izhbg.typz.cms.index.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.izhbg.cms.freemarker.util.DirectoryFilter;
import com.izhbg.cms.freemarker.util.ProcessClient;

@Controller
@RequestMapping("/cms")
public class IndexController {
	@RequestMapping("index")
	 public String list(Model model,HttpServletRequest request) {
		  //html生成之后存放的路径  
        String dirPath = request.getSession().getServletContext().getRealPath("/cms/html");  
        File path = new File(dirPath); 
        //生成的文件的名字  
        String indexFileName = "index.html"; 
        /** 
         * 判断是否已经存在该html文件，存在了就直接访问html ，不存在生成html文件 
         */
        String[] indexfileList = path.list(new DirectoryFilter(indexFileName));  
        if(indexfileList.length<=0){  
            Writer out = null;
			try {
				out = new OutputStreamWriter(new FileOutputStream(dirPath+"/"+indexFileName),"UTF-8");
				 //生成html文件  
	            ProcessClient.processBody(out,request,"body.ftl");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}  
           return "/html/index";
        }else{  
           return "/html/"+(indexfileList[0]).split("\\.")[0];
        }  
	}
	@RequestMapping("enter")
	public String enter(Model model,HttpServletRequest request) {
		return "/html/enter";
	}
}

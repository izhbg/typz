package com.izhbg.cms.freemarker.util;


import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * @author cxl
 **/
public class ProcessClient {

	private static Map<String,Object> root = new HashMap<String,Object>();

	/**
	 * 调用FreeMarkertUtil.java
	 * FreeMarkertUtil.processTemplate("body.ftl", root, out);
	 * 来生成html文件
	 * @param out
	 */
	public static void processBody(Writer out,HttpServletRequest request,String templeteName){
		root.put("h", "header 我是头部");
		root.put("f", "footer 我是尾部");
		root.put("users", "body 我是body");
		FreeMarkertUtil.processTemplate(templeteName, root, out,request);
	}
	
}


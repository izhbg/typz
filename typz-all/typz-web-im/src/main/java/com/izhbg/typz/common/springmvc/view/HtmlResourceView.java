package com.izhbg.typz.common.springmvc.view;

import java.io.File;
import java.util.Locale;

import org.springframework.web.servlet.view.InternalResourceView;
/**
 * 
* @ClassName: HtmlResourceView 
* @author caixl 
* @date 2016-6-8 上午11:01:41 
*
 */
public class HtmlResourceView extends InternalResourceView {
	 @Override
	 public boolean checkResource(Locale locale) {
	  File file = new File(this.getServletContext().getRealPath("/") + getUrl());
	  return file.exists();// 判断该页面是否存在
	 }
}

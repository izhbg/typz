package com.izhbg.cms.freemarker.util;

import java.io.File;
import java.io.FilenameFilter;
/**
 **/
public class DirectoryFilter implements FilenameFilter {

	String myString;
	public DirectoryFilter(String myString)
	{
		this.myString = myString;
	}
	
	public boolean accept(File dir,String name)
	{	//FilenameFilter.accept(File dir, String name) 
       // 测试指定文件是否应该包含在某一文件列表中。
		String f= new File(name).getName();
		if(f.contains(myString) || f.equals(myString)){
			return true;
		}
		return false;
	}

}


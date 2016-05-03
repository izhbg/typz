package com.izhbg.typz.sso.auth.service;

import java.util.Comparator;

import com.izhbg.typz.sso.auth.dto.TXtYh;

public class ComparatorTXtYh implements Comparator {
	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		TXtYh jg1 = (TXtYh)o1;
		TXtYh jg2 = (TXtYh)o2;
		int flag=0;
		if(jg1.getXh()==null||jg2.getXh()==null){
			if(jg1.getXh()==null&&jg2.getXh()!=null){
				flag = -1;
			}else if(jg1.getXh()!=null&&jg2.getXh()==null){
				flag = 1;
			}
		}else{
			flag = jg1.getXh().compareTo(jg2.getXh());
		}
		return flag;
	}
}

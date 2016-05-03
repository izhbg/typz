package com.izhbg.typz.sso.auth.service;

import java.util.Comparator;

import com.izhbg.typz.sso.auth.dto.TXtJg;

public class ComparatorTXtJg implements Comparator {
	@Override
	public int compare(Object o1, Object o2) {
		TXtJg jg1 = (TXtJg)o1;
		TXtJg jg2 = (TXtJg)o2;
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

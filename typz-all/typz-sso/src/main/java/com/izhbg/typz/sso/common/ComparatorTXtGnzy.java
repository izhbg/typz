package com.izhbg.typz.sso.common;

import java.util.Comparator;

import com.izhbg.typz.sso.auth.dto.TXtGnzy;

public class ComparatorTXtGnzy implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		int flag = -1;
		TXtGnzy user0=(TXtGnzy)o1;
		TXtGnzy user1=(TXtGnzy)o2;
		if(user0.getGnXh()!=null&&user1.getGnXh()!=null)
		{
			if(user0.getGnXh()>user1.getGnXh()){
				flag = 1;
			}
		}
		if(user0.getGnXh()==null&&user1.getGnXh()!=null)
			flag = 1;
			
		return flag;
	}

}

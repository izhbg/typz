package com.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSON;


public class MainClass {

	public static void main(String[] args) {
		// 构建用户geust  
        User guestUser = new User();  
        guestUser.setName("guest");  
        guestUser.setAge(28);  
        // 构建用户root  
        User rootUser = new User();  
        rootUser.setName("root");  
        guestUser.setAge(35);  
        // 构建用户组对象  
        UserGroup group = new UserGroup();  
        group.setName("admin");  
        group.getUsers().add(guestUser);  
        group.getUsers().add(rootUser);  
        // 用户组对象转JSON串  
        String jsonString = JSON.toJSONString(group);  
        System.out.println("jsonString:" + jsonString);  
        // JSON串转用户组对象  
        UserGroup group2 = JSON.parseObject(jsonString, UserGroup.class);  
        System.out.println("group2:" + group2);  
  
        // 构建用户对象数组  
        User[] users = new User[2];  
        users[0] = guestUser;  
        users[1] = rootUser;  
        // 用户对象数组转JSON串  
        String jsonString2 = JSON.toJSONString(users);  
        System.out.println("jsonString2:" + jsonString2);  
        // JSON串转用户对象列表  
        List<User> users2 = JSON.parseArray(jsonString2, User.class);  
        System.out.println("users2:" + users2);
        
        String str = "";
        
	
	}

	
}


package com.izhbg.typz.database.uitl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class WebUtils {
	
	/**
	 * 生成随机Id号,随机Id号的组成:四位年份两位月份两位日期两位时两位分两位秒+(3位随机号,不够3位前面补零),如:20100803163405346
	 * yyyyMMddHHmmss+3位随机数
	 * @return 随机Id号
	 */
	public static String getRandomId(){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			StringBuffer sb = new StringBuffer(sdf.format(new Date()));
			sb.append(fillZero(3,String.valueOf(new Random().nextInt(1000))));
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException("生成随机Id号失败");
		}
	}
	
	/**
	 * 补零
	 * @param length 补零后的长度
	 * @param source 需要补零的长符串
	 * @return
	 */
	private static String fillZero(int length, String source) {
		StringBuilder result = new StringBuilder(source);
		for(int i=result.length(); i<length ; i++){
			result.insert(0, '0');
		}
		return result.toString();
	}
	
	 /**
     * 添加cookie  
     * @param response
     * @param name cookie的名称
     * @param value cookie的值
     * @param maxAge cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 如果值为0,cookie将随浏览器关闭而清除)
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {  
    	try {
			name = URLEncoder.encode(name, "UTF-8");
			value = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("URL编码异常");
		}
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/netmanage");
        if (maxAge>0) cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
    public static void addCookie(HttpServletResponse response, String name, String value) {  
    	try {
			name = URLEncoder.encode(name, "UTF-8");
			value = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("URL编码异常");
		}
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/netmanage");
        //cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);
    }
    /*public static void addUserToCookie(HttpServletRequest request,HttpServletResponse response, User user) { 
    	WebUtils.addCookie(response, "userID", user.getUserid());
		WebUtils.addCookie(response, "userIsn", user.getUserisn());
		WebUtils.addCookie(response, "userName", user.getUsername());

		WebUtils.addCookie(response, "unitIsn",user.getUnit().getUnitisn());
		WebUtils.addCookie(response, "unitName",user.getUnit().getUnitname());

		WebUtils.addCookie(response, "nodeid",user.getUnit().getNode().getNodeid());
		WebUtils.addCookie(response, "nodeName",user.getUnit().getNode().getNodename());
		WebUtils.addCookie(response, "upnodeid",user.getUnit().getNode().getUpnodeid());
		WebUtils.addCookie(response, "userGroupIsn",user.getUserGroupIsn());
		WebUtils.addCookie(response, "managedNodeID",user.getUnit().getManagednodeid());//用户能管理的节点
		
		String commonurl=PropertiesUtil.getProperties("common.url", "http://localhost:8080/common");
		WebUtils.addCookie(response, "commonurl",commonurl);
		
		String projectname =request.getContextPath();
		projectname = projectname.substring(1, projectname.length());
		WebUtils.addCookie(response, "projectname",projectname);
		String webport =request.getServerPort()+"";
		WebUtils.addCookie(response, "webport",webport);
		String projectroot="http://"+request.getLocalAddr()+":"+webport+"/"+projectname;
		WebUtils.addCookie(response, "projectroot",projectroot);
    }*/
    
    public static void removeUserCookie(HttpServletResponse response){
    	WebUtils.addCookie(response, "userID", "0");
		WebUtils.addCookie(response, "userIsn", "0");
		WebUtils.addCookie(response, "userName", "0");
		WebUtils.addCookie(response, "userGroupIsn", "");

		WebUtils.addCookie(response, "unitIsn", "");
		WebUtils.addCookie(response, "unitName", "");

		WebUtils.addCookie(response, "nodeid", "");
		WebUtils.addCookie(response, "nodeName", "");
		WebUtils.addCookie(response, "upnodeid", "");
		WebUtils.addCookie(response, "managedNodeID", "");
		WebUtils.addCookie(response, "commonurl", "");
		WebUtils.addCookie(response, "projectname", "");
		WebUtils.addCookie(response, "webport", "");
		WebUtils.addCookie(response, "stateReportURL", "");
		WebUtils.addCookie(response, "netmanageURL", "");
    }
    /**
     * 获取cookie的值
     * @param request
     * @param name cookie的名称
     * @return
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
    	Map<String, Cookie> cookieMap = WebUtils.readCookieMap(request);
    	try {
			URLDecoder.decode(name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("URL解码异常");
		}
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            String value = cookie.getValue();
            try {
            	value=URLDecoder.decode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("URL解码异常");
			}
            return value;
        }else{
            return null;
        }
    }
    
    protected static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (int i = 0; i < cookies.length; i++) {
                cookieMap.put(cookies[i].getName(), cookies[i]);
            }
        }
        return cookieMap;
    }
    
    public static boolean copyRealFile(String srcName, String destName) {
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(srcName));
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(destName));
			int i = 0;
			byte[] buffer = new byte[2048];
			while ((i = in.read(buffer)) != -1) {
				out.write(buffer, 0, i);
			}
			out.close();
			in.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}
    /*public static String validateUser(String userName,String passWord){
    	try {
			String endpoint=PropertiesUtil.getProperties("loginservice");
			String operation = "getLogin"; 
			Service service = new Service(); 	           
			Call call = (Call) service.createCall(); 
			call.setTargetEndpointAddress(new java.net.URL(endpoint)); 
			call.setOperationName(operation); 
			return (String) call.invoke(new Object[]{userName,passWord});
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return "false" ;
    }*/
    
    /**
     * 将webservice 传送的json字符串转换为user对象
     * @param xml
     * @return user
     */
	/*public static User xmlToUser(String xml) {
		List<MorphDynaBean> list = JSONArray.toList(JSONArray.fromObject(xml));
		User user = new User();
		if(list!=null&&list.size()>0){
			MorphDynaBean morphDynaBean = list.get(0);
			String userisn = (String) morphDynaBean.get("USERISN");
			String unitisn = (String) morphDynaBean.get("UNITISN");
			String username = (String) morphDynaBean.get("USERNAME");
			String password = (String) morphDynaBean.get("PASSWORD");
			String unitname = (String) morphDynaBean.get("UNITNAME");
			String role = (String) morphDynaBean.get("ROLE");
			String tel = (String) morphDynaBean.get("TEL");
			String email = (String) morphDynaBean.get("EMAIL");
			String ipsection = (String) morphDynaBean.get("IPSECTION");
			long sort = morphDynaBean.get("SORT")==null?0:((Number) morphDynaBean.get("SORT")).longValue();//long
			String userGroupIsn = (String) morphDynaBean.get("USERGROUPISN");
			String userid = (String) morphDynaBean.get("USERID");
			user.setUserisn(userisn);
			user.setUnit(new Unit(unitisn));
			user.setUsername(username);
			user.setPassword(password);
			user.setUnitname(unitname);
			user.setRole(role);
			user.setTel(tel);
			user.setEmail(email);
			user.setIpsection(ipsection);
			user.setSort(sort);
			user.setUserGroupIsn(userGroupIsn);
			user.setUserid(userid);
		}
		return user;
	}*/
}

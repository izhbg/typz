package com.izhbg.typz.base.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 
* @ClassName: CommonUtil 
* @Description: 通用工具类
* @author caixl 
* @date 2016-5-10 下午4:14:36 
*
 */
public class CommonUtil
{
	/**
	 * 根据request 获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request){    
        String ip = request.getHeader("x-forwarded-for");    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("Proxy-Client-IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("WL-Proxy-Client-IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_CLIENT_IP");    
        }    
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");    
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getHeader("X-real-ip");    
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
            ip = request.getRemoteAddr();    
        }    
        return ip;    
    }
}

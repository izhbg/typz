package com.izhbg.typz.base.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.izhbg.typz.base.commondto.TreeObject;


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
	/**
	 * 拼凑树结构json
	 * @param tlist
	 * @param ischecked
	 * @param pid
	 * @return
	 */
	public static String getTreeJson(List<TreeObject> tlist) {
		JSONObject one = new JSONObject();
		one.put("id", "-1");
		one.put("name", "节点数");
		one.put("pId", "");
		one.put("isParent", true);
		one.put("nocheck", true);
		one.put("open", true);
		JSONObject node = null;
		JSONArray jaTree = new JSONArray();
		for(TreeObject resource:tlist){
			node = new JSONObject();
			node.put("id", resource.getId());
			node.put("name", resource.getName());
			node.put("pId", resource.getPid());
			jaTree.add(node);
		}
		jaTree.add(one);
		return jaTree.toString();
	}
}

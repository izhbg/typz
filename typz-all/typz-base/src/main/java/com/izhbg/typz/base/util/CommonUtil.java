package com.izhbg.typz.base.util;

import java.util.ArrayList;
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
		one.put("name", "节点树");
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
	/**
	 * 拼凑树结构
	 * @param tlist
	 * @param ischecked
	 * @param pid
	 * @return
	 */
	public static String getTreeJson(List<TreeObject> tlist, String ischecked, String pid) {
		List<TreeObject> tlist1 = new ArrayList();
		tlist1.addAll(tlist);
		String json = "{'text':'节点树','id':'-1',children:[";
		String[] pids = pid.split(",");
		for (int i = 0; i < pids.length; i++) {
			for (TreeObject to : tlist) {
				if (!pids[i].equals("0") && to.getId().equals(pids[i])) {
					tlist1.remove(to);
					json += "{'text':'" + to.getName() + "','pid':'" + to.getPid() + "',";
					if (ischecked.equals("1")) {
						json += "'checked':'0',";
						json += "'id':'" + to.getId()  + "',";
					} else {
						json += "'id':'" + to.getId() + "',";
					}
					if (to.getIcons() != null && to.getIcons().length() > 0) {
						json += "'icon':'" + to.getIcons() + "',";
					}
					if (to.getCheckType() != null && to.getCheckType().length() > 0) {
						json += "'checktype':'" + to.getCheckType() + "',";
					}
					if (to.getIsCheckType() != null && to.getIsCheckType().length() > 0) {
						json += "'ischecktype':'" + to.getIsCheckType() + "',";
					}
					json += "children:[";
					json += getChildrenData(tlist1, to, ischecked);
					json += "]},";
				} else if (pids[i].equals("0") && (to.getPid().equals(pids[i]) || to.getPid().equals(""))) {
					tlist1.remove(to);
					json += "{'text':'" + to.getName() + "','pid':'" + to.getPid() + "',";
					if (ischecked.equals("1")) {
						json += "'checked':'0',";
						json += "'id':'" + to.getId() +  "',";
					} else {
						json += "'id':'" + to.getId() + "',";
					}
					if (to.getIcons() != null && to.getIcons().length() > 0) {
						json += "'icon':'" + to.getIcons() + "',";
					}
					if (to.getCheckType() != null && to.getCheckType().length() > 0) {
						json += "'checktype':'" + to.getCheckType() + "',";
					}
					if (to.getIsCheckType() != null && to.getIsCheckType().length() > 0) {
						json += "'ischecktype':'" + to.getIsCheckType() + "',";
					}
					json += "children:[";
					json += getChildrenData(tlist1, to, ischecked);
					json += "]},";
				}
			}
		}
		if(!json.equals("{'text':'节点树','id':'-1',children:["))
			json = json.substring(0, json.length() - 1);
		json += "] ";
		json += "}";
		return json;
	}
	
	private static String getChildrenData(List<TreeObject> tlist1, TreeObject to, String ischecked) {
		String json = "";
		List<TreeObject> tlist = new ArrayList();
		tlist.addAll(tlist1);
		for (TreeObject to1 : tlist1) {
			if (to.getId().equals(to1.getPid())) {
				tlist.remove(to1);
				json += "{'text':'" + to1.getName() + "','pid':'" + to1.getPid() + "',";
				if (ischecked.equals("1")) {
					json += "'checked':'0',";
					//json += "'id':'" + to1.getId() + "_" + to1.getName() + "',";
					json += "'id':'" + to1.getId()  + "',";
				} else {
					json += "'id':'" + to1.getId() + "',";
				}
				if (to1.getIcons() != null && to1.getIcons().length() > 0) {
					json += "'icon':'" + to1.getIcons() + "',";
				}
				if (to1.getCheckType() != null && to1.getCheckType().length() > 0) {
					json += "'checktype':'" + to1.getCheckType() + "',";
				}
				if (to1.getIsCheckType() != null && to1.getIsCheckType().length() > 0) {
					json += "'ischecktype':'" + to1.getIsCheckType() + "',";
				}
				json += "children:[";
				json += getChildrenData(tlist, to1, ischecked);
				json += "]},";
			}
		}
		if (json.length() != 0 && json.length() == json.lastIndexOf(",") + 1) {
			json = json.substring(0, json.length() - 1);
		}
		return json;
	}
}

/*
 * Created on 2006-8-19
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.izhbg.typz.base.util;

import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.hibernate.internal.util.StringHelper;

import com.izhbg.typz.base.page.Page;

/**
 * @author Administrator TODO To change the template for this generated type
 *         comment go to Window - Preferences - Java - Code Style - Code
 *         Templates
 */
public class Ajax {
	public static String xmlHead() {
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?><data>";
	}

	public static String wsHead() {
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
	}

	public static String xmlResult(int code, String description) {
		return xmlResult(code, description, "");
	}

	public static String xmlResult(int code, String description, String content) {
		StringBuffer sb = new StringBuffer();
		sb.append(xmlHead());
		sb.append("<code>" + code + "</code>");
		sb.append("<desc><![CDATA[" + description + "]]></desc>");
		sb.append(content);
		sb.append(xmlFoot());
		return sb.toString();
	}

	public static String toJson(ResultObj obj) {
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,
				new JsonValueProcessor() {
					public Object processObjectValue(String key, Object value,
							JsonConfig arg2) {
						if (value == null)
							return "";
						if (value instanceof Date) {
							return ((Date)value).getTime();
						}
						return value.toString();
					}
					public Object processArrayValue(Object value,
							JsonConfig arg1) {
						return null;
					}
				});
		return JSONObject.fromObject(obj,cfg).toString();
	}
	
//	public static void main(String[] args){
//		ResultObj r=new ResultObj();
//		Mblog m=new Mblog();
//		m.setIssueTime(new Date());
//		Mblog ww=new Mblog();
//		ww.setIssueTime(new Date());
//		m.setSource(ww);
//		r.setContent(m);
//		String json=Ajax.toJson(r);
//		Date d=new Date(1300362385013L);
//		System.out.println(d);
//		System.out.println(json);
//		Gson g=new Gson();
//		g.fromJson("{'answerFlag':0,'checkCommentCount':0,'checkReplyCount':0,'commentCount':0,'content':'','fromType':0,'imageUrl':'','issueLoginName':'','issueTime':'2011-12-11 11:11:11','issueUserId':0,'issueUserName':'','mblogType':0,'musicTitle':'','musicUrl':'','path':'','picSerId':0,'prefixName':'','quoteMblogId':'','quoteUserId':0,'quoteUserName':'','replyCount':0,'source':null,'talkId':0,'time':1300362575,'uuid':'','validFlag':0,'videoImgUrl':'','videoSerId':0,'videoTitle':'','videoUrl':''}",Mblog.class);
//	}

	public static String toJson(int code, String description) {
		return toJson(code, description, null);
	}

	/**
	 * 构造json对像
	 * 
	 * @param code
	 *            0成功，1失败
	 * @param description
	 *            描述
	 * @param Object
	 * @return
	 */
	public static String toJson(int code, String description, Object content) {
		ResultObj obj = new ResultObj();
		obj.setCode(code);
		obj.setDescription(description);
		obj.setContent(content);
		return toJson(obj);
	}

	public static String JSONResult(int code, String description) {
		return JSONResult(code, description, null);
	}
	
	public static String JSONResult(int code, String description,String info) {
		StringBuffer sf = new StringBuffer();
		return new JSONObject().element("result",code).element("msg", description).element("info", info).toString();
	}
	/**
	 * 构造json对像
	 * 
	 * @param code
	 *            0成功，1失败
	 * @param description
	 *            描述
	 * @param Object
	 * @return
	 */
	public static String JSONResult(int code, String description, Object obj) {
		StringBuffer sf = new StringBuffer();
		sf.append("{");
		if (obj != null) {
			sf.append("\"info\":{\"talk\":");
			sf.append(JSONArray.fromObject(obj));
			sf.append("},");
		}
		sf.append("\"result\":" + code + ",");
		sf.append("\"msg\":\"" + description + "\"");
		sf.append("}");
		return sf.toString();
	}

	public static String JSONResult(int code,String description,String info,Page page){
		JSONObject jo=new JSONObject();
		jo.element("result", code).element("msg", description)
				.element("currentPage", page.getPageNo())
				.element("totalRows", page.getTotalCount())
				.element("totalPages", page.getPageCount())
				.element("pageSize", page.getPageSize())
				.element("pageStr", page.getOrder())
				.element("info", info);
		return jo.toString();
	}

	public static String xmlFoot() {
		return "</data>";
	}

	public static String getTagWhere(String funName, String tag) {
		tag = tag.trim();
		if (StringHelper.isEmpty(tag.trim()))
			return "";
		tag = tag.replaceAll("，", ",");
		String[] a = tag.split(",");
		StringBuffer sb = new StringBuffer();
		sb.append(" and ( 1=1 ");
		String str = "";
		for (int i = 0; i < a.length; i++) {
			str = a[i].trim();
			if (StringHelper.isNotEmpty(str))
				sb.append(" and " + funName + " like '%" + str + "%'");
		}
		sb.append(") ");
		return sb.toString();
	}

	public static String getTagOrWhere(String funName, String tag) {
		tag = tag.trim();
		if (StringHelper.isEmpty(tag.trim()))
			return "";
		tag = tag.replaceAll("，", ",");
		String[] a = tag.split(",");
		StringBuffer sb = new StringBuffer();
		if (a.length > 0) {
			sb.append(" and ( ");
			String str = "";
			for (int i = 0; i < a.length; i++) {
				str = a[i].trim();
				if (StringHelper.isNotEmpty(str)) {
					if (i > 0)
						sb.append(" or ");
					sb.append(funName + " like '%" + str + "%'");
				}
			}
			sb.append(") ");
		}
		return sb.toString();
	}

	public static String getTagOrString(String funName, String tag) {
		tag = tag.trim();
		if (StringHelper.isEmpty(tag.trim()))
			return "";
		tag = tag.replaceAll("，", ",");
		String[] a = tag.split(",");
		StringBuffer sb = new StringBuffer();
		if (a.length > 0) {
			sb.append(" ( ");
			String str = "";
			for (int i = 0; i < a.length; i++) {
				str = a[i].trim();
				if (StringHelper.isNotEmpty(str)) {
					if (i > 0)
						sb.append(" or ");
					sb.append(funName + " like '%" + str + "%'");
				}
			}
			sb.append(") ");
		}
		return sb.toString();
	}

	public static String parseTag2(String funName, String tag) {
		tag = tag.trim();
		if (StringHelper.isEmpty(tag.trim()))
			return "";
		tag = tag.replaceAll("，", ",");
		String[] a = tag.split(",");
		StringBuffer sb = new StringBuffer();
		String str = "";
		for (int i = 0; i < a.length; i++) {
			str = a[i].trim();
			if (StringHelper.isNotEmpty(str))
				sb.append("<a href=\"javascript:" + funName + "('" + str
						+ "')\">" + str + "</a> <a href=\"javascript:del"
						+ funName + "('" + str
						+ "')\"><img border=0 src='images/del.gif'></a> ");
		}
		return sb.toString();
	}

	public static String parseTag(String funName, String tag) {
		tag = tag.trim();
		if (StringHelper.isEmpty(tag.trim()))
			return "";
		tag = tag.replaceAll("，", ",");
		String[] a = tag.split(",");
		StringBuffer sb = new StringBuffer();
		String str = "";
		for (int i = 0; i < a.length; i++) {
			str = a[i].trim();
			if (StringHelper.isNotEmpty(str))
				sb.append("<a href=\"javascript:" + funName + "('" + str
						+ "')\">" + str + "</a> ");
		}
		return sb.toString();
	}

	public static String parseCmTag(String funName, String tag, int col,
			int typeId) {
		tag = tag.trim();
		if (StringHelper.isEmpty(tag.trim()))
			return "";
		tag = tag.replaceAll("，", ",");
		String[] a = tag.split(",");
		StringBuffer sb = new StringBuffer();
		String str = "";
		for (int i = 0; i < a.length; i++) {
			str = a[i].trim();
			if (StringHelper.isNotEmpty(str))
				sb.append("<a href=\"javascript:" + funName + "(" + typeId
						+ ",'" + col + "','" + str + "')\">" + str + "</a> ");
		}
		return sb.toString();
	}

	public static String parseTag(String funName, String tag, int clearFlag) {
		tag = tag.trim();
		if (StringHelper.isEmpty(tag.trim()))
			return "";
		tag = tag.replaceAll("，", ",");
		String[] a = tag.split(",");
		StringBuffer sb = new StringBuffer();
		String str = "";
		for (int i = 0; i < a.length; i++) {
			str = a[i].trim();
			if (StringHelper.isNotEmpty(str))
				sb.append("<a href=\"javascript:" + funName + "('" + str + "',"
						+ clearFlag + ")\">" + str + "</a> ");
		}
		return sb.toString();
	}

	public static String[] parseTag(String tag) {
		tag = tag.trim();
		if (StringHelper.isEmpty(tag.trim()))
			return null;
		tag = tag.replaceAll("，", ",");
		String[] a = tag.split(",");
		return a;
	}

	public static String parseQuoteTag(String tag) {
		if (StringHelper.isEmpty(tag))
			return "";
		String[] a = tag.split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < a.length; i = i + 2) {
			sb.append("[<a href=\"personal.action?uId=" + a[i]
					+ "\" target=_blank>" + a[i + 1] + "</a>]");
		}
		return sb.toString();
	}
}
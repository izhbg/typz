package com.izhbg.typz.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * JSON转换工具类
 */
public class JsonUtil {

	/**
	 * 对象转换成JSON字符串
	 * 
	 * @param obj
	 *            需要转换的对象
	 * @return 对象的string字符
	 */
	public static String toJson(Object obj) {
		return JSON.toJSONString(obj);
	}
	/**
	 * 异步返回数据结构
	 * @param obj 返回内容
	 * @param result 是否成功 1 成功 0不成功
	 * @param msg 提示信息
	 * @return
	 */
	public static String toJsonResult(Object obj,Integer result,String msg){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", result);
		map.put("msg", msg);
		map.put("content", obj);
		return JSON.toJSONString(map);
	}
	/**
	 * JSON字符串转换成对象
	 * 
	 * @param jsonString
	 *            需要转换的字符串
	 * @param type
	 *            需要转换的对象类型
	 * @return 对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String jsonString, Class<T> type) {
		return (T) JSON.parseObject(jsonString, type);
	}

	/**
	 * 将JSONArray对象转换成list集合
	 * 
	 * @param jsonArr
	 * @return
	 */
	public static List<Object> jsonToList(JSONArray jsonArr) {
		List<Object> list = new ArrayList<Object>();
		for (Object obj : jsonArr) {
			if (obj instanceof JSONArray) {
				list.add(jsonToList((JSONArray) obj));
			} else if (obj instanceof JSONObject) {
				list.add(jsonToMap((JSONObject) obj));
			} else {
				list.add(obj);
			}
		}
		return list;
	}

	/**
	 * 将json字符串转换成map对象
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> jsonToMap(String json) {
		JSONObject obj = JSONObject.parseObject(json);
		return jsonToMap(obj);
	}

	/**
	 * 将JSONObject转换成map对象
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> jsonToMap(JSONObject obj) {
		Set<?> set = obj.keySet();
		Map<String, Object> map = new HashMap<String, Object>(set.size());
		for (Object key : obj.keySet()) {
			Object value = obj.get(key);
			if (value instanceof JSONArray) {
				map.put(key.toString(), jsonToList((JSONArray) value));
			} else if (value instanceof JSONObject) {
				map.put(key.toString(), jsonToMap((JSONObject) value));
			} else {
				map.put(key.toString(), obj.get(key));
			}

		}
		return map;
	}
	
}


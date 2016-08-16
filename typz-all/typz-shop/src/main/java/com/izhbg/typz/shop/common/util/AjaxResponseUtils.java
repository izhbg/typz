/**   
 * @Title: AjaxResponseUtils.java 
 * @Package com.zgxcw.store.web.common.utils 
 * @Description: TODO 
 * @author weiming
 * @date 2015年10月10日 下午2:34:50   
 */ 
package com.izhbg.typz.shop.common.util;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.hibernate.annotations.common.util.StringHelper;


import net.sf.json.JSONObject;

/** 
 * 处理异常讲求帮助类，返回JSON格式数据
 * @ClassName: AjaxResponseUtils 
 * @Description: TODO
 * @date 2015年10月10日 下午2:34:50   
 */
public class AjaxResponseUtils {
  
   /**
    * HttpServletResponse返回给前端JSON格式数据
    * @param mess  返回给前端的消息，字符串格式如    "{\"msg\" : \"1\"}" 
    * @param response    
    * void
    */
   public static void ajax(String mess, HttpServletResponse response) {
     response.setContentType("text/xml;charset=UTF-8");
     response.setHeader("Cache-Control","no-cache"); 
     response.setDateHeader("Expires",0); 
     response.setHeader("Pragma","No-cache");
     response.setCharacterEncoding(FileConstant.encode);
     
     if(StringHelper.isNotEmpty(mess)){
       JSONObject json = JSONObject.fromObject(mess);
       Writer writer = null;
       try{
         writer = response.getWriter();
         writer.write(json.toString());
         writer.flush();
         writer.close();
       } catch (Exception e) {
         e.printStackTrace();
       } finally {
         /** 无条件关闭流 */
         IOUtils.closeQuietly(writer);
       }  
     }  
   }
   
   /**
    * HttpServletResponse返回给前端JSON格式数据 
    * @Description: 
    * @param obj  实体类  
    * @param response    
    */
   public static void ajax(Object obj, HttpServletResponse response) {
     response.setContentType("text/xml;charset=UTF-8");
     response.setHeader("Cache-Control","no-cache"); 
     response.setDateHeader("Expires",0); 
     response.setHeader("Pragma","No-cache");
     response.setCharacterEncoding(FileConstant.encode);
     
     if(obj!=null){
       JSONObject json = JSONObject.fromObject(obj);
       Writer writer = null;
       try{
         writer = response.getWriter();
         writer.write(json.toString());
         writer.flush();
         writer.close();
       } catch (Exception e) {
         e.printStackTrace();
       } finally {
         /** 无条件关闭流 */
         IOUtils.closeQuietly(writer);
       }  
     }  
   }
   
   
   /**
    * HttpServletResponse返回给前端JSON格式数据 
    * @Description: 
    * @param obj  实体类  
    * @param response    
    */
   public static void ajax(Map map, HttpServletResponse response) {
     response.setContentType("charset=utf-8");
     response.setHeader("Cache-Control","no-cache"); 
     response.setDateHeader("Expires",0); 
     response.setHeader("Pragma","No-cache");
     response.setCharacterEncoding(FileConstant.encode);
     
     if(map!=null){
       JSONObject json = JSONObject.fromObject(map);
       Writer writer = null;
       try{
         writer = response.getWriter();
         writer.write(json.toString());
         writer.flush();
         writer.close();
       } catch (Exception e) {
         e.printStackTrace();
       } finally {
         /** 无条件关闭流 */
         IOUtils.closeQuietly(writer);
       }  
     }  
   }
   
   /**
    * 返回给前端JSON格式数据
    * @param response 
    * @param code  返回码
    * @param message   返回消息 
    * void
    */
   public static void ajax(HttpServletResponse response, String code, String message) {
     response.setContentType("text/xml;charset=UTF-8");
     response.setHeader("Cache-Control","no-cache"); 
     response.setDateHeader("Expires",0); 
     response.setHeader("Pragma","No-cache");
     response.setCharacterEncoding(FileConstant.encode);
     
     Map map = new HashMap();
     map.put("code", code);
     map.put("msg", message);
     if(map!=null){
       JSONObject json = JSONObject.fromObject(map);
       Writer writer = null;
       try{
         writer = response.getWriter();
         writer.write(json.toString());
         writer.flush();
         writer.close();
       } catch (Exception e) {
         e.printStackTrace();
       } finally {
         /** 无条件关闭流 */
         IOUtils.closeQuietly(writer);
       }  
     }  
   }
}

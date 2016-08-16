/**   
 * @Title: FileUtils.java 
 * @Package com.zgxcw.store.web.common.constant 
 * @Description: TODO 
 * @author weiming
 * @date 2015年10月4日 下午1:29:33   
 */ 
package com.izhbg.typz.shop.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.izhbg.typz.base.util.ParseUtil;
import com.izhbg.typz.base.util.StringHelper;
import com.izhbg.typz.shop.common.util.FileConstant.FileExtension;;


/** 
 * @ClassName: FileUtils 
 * @Description: 文件帮助类
 * @date 2015年10月4日 下午1:29:33   
 */
public class FileUploadUtils {
  
  private static Logger log = LoggerFactory.getLogger(FileUploadUtils.class);
  
  /**
   * 使用文件通道的方式复制文件
   * 
   * @param s
   *            源文件
   * @param t
   *            复制到的新文件
   */

   public static void fileChannelCopy(File s, File t) {
       FileInputStream fi = null;
       FileOutputStream fo = null;
       FileChannel in = null;
       FileChannel out = null;
       try {
           fi = new FileInputStream(s);
           fo = new FileOutputStream(t);
           in = fi.getChannel();//得到对应的文件通道
           out = fo.getChannel();//得到对应的文件通道
           in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           try {
               fi.close();
               in.close();
               fo.close();
               out.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }

     /**
      * 根据文件路径获取文件头信息，判断是否是合法的格式     
      * @param filePath
      *            文件路径
      * @return 是否合法
      */
     public static boolean isExtension(String filePath) {
       String fileType = getFileHeader(filePath);
       for(FileExtension extension : FileExtension.values()){
         if(extension.getValue().length() <= fileType.length()){
           if(extension.getValue().equals(fileType.substring(0, extension.getValue().length()))){
              return true;
           }
         }else{
           //LogConsole.info("配置的文件头参数[" + extension + "]大于获取的文件头参数[" + fileType + "]长度");
         }
       }
       return false;
     }

     /**
      * 根据文件路径获取文件头信息
      * 
      * @param filePath
      *            文件路径
      * @return 文件头信息
      */
     public static String getFileHeader(String filePath) {
       FileInputStream is = null;
       String value = null;
       try {
         is = new FileInputStream(filePath);
         byte[] b = new byte[4];
         /*
          * int read() 从此输入流中读取一个数据字节。 int read(byte[] b) 从此输入流中将最多 b.length
          * 个字节的数据读入一个 byte 数组中。 int read(byte[] b, int off, int len)
          * 从此输入流中将最多 len 个字节的数据读入一个 byte 数组中。
          */
         is.read(b, 0, b.length);
         value = bytesToHexString(b);
       } catch (Exception e) {
       } finally {
         if (null != is) {
           try {
             is.close();
           } catch (IOException e) {
           }
         }
       }
       return value;
     }
     /**
      * 根据pringMVC上传的文件径获取文件头信息，判断是否是合法的格式     
      * @param file
      *            springMVC上传的文件
      * @return 是否合法
      */
     public static boolean isExtension(CommonsMultipartFile file) {
       String fileType = getFileHeader(file);
       for(FileExtension extension : FileExtension.values()){
         if(extension.getValue().length() <= fileType.length()){
           if(extension.getValue().equals(fileType.substring(0, extension.getValue().length()))){
              return true;
           }
         }else{
          // LogConsole.info("配置的文件头参数[" + extension + "]大于获取的文件头参数[" + fileType + "]长度");
         }
       }
       return false;
     }   
     
     /**
      * 根据springMVC上传的文件获取文件头信息
      * 
      * @param file
      *            springMVC上传的文件
      * @return 文件头信息
      */
     public static String getFileHeader(CommonsMultipartFile file) {
       InputStream is = null;
       String value = null;
       try {
         is =  file.getInputStream();
         byte[] b = new byte[4];
         /*
          * int read() 从此输入流中读取一个数据字节。 int read(byte[] b) 从此输入流中将最多 b.length
          * 个字节的数据读入一个 byte 数组中。 int read(byte[] b, int off, int len)
          * 从此输入流中将最多 len 个字节的数据读入一个 byte 数组中。
          */
         is.read(b, 0, b.length);
         value = bytesToHexString(b);
       } catch (Exception e) {
       } finally {
         if (null != is) {
           try {
             is.close();
           } catch (IOException e) {
           }
         }
       }
       return value;
     }
     /**
      * 根据文件路径获取文件头信息，判断是否是合法的格式     
      * @param filePath
      *            文件路径
      * @return 是否合法
      */
     public static boolean isExtension(File file) {
       String fileType = getFileHeader(file);
       for(FileExtension extension : FileExtension.values()){
         if(extension.getValue().length() <= fileType.length()){
           if(extension.getValue().equals(fileType.substring(0, extension.getValue().length()))){
              return true;
           }
         }else{
          // LogConsole.info("配置的文件头参数[" + extension + "]大于获取的文件头参数[" + fileType + "]长度");
         }
       }
       return false;
     }
     /**
      * 根据文件路径获取文件头信息
      * 
      * @param file 文件
      * @return 文件头信息
      */
     public static String getFileHeader(File file) {
       FileInputStream is = null;
       String value = null;
       try {
         is = new FileInputStream(file);
         byte[] b = new byte[4];
         /*
          * int read() 从此输入流中读取一个数据字节。 int read(byte[] b) 从此输入流中将最多 b.length
          * 个字节的数据读入一个 byte 数组中。 int read(byte[] b, int off, int len)
          * 从此输入流中将最多 len 个字节的数据读入一个 byte 数组中。
          */
         is.read(b, 0, b.length);
         value = bytesToHexString(b);
       } catch (Exception e) {
       } finally {
         if (null != is) {
           try {
             is.close();
           } catch (IOException e) {
           }
         }
       }
       return value;
     }
     
     /**
      * 根据文件路径获取文件头信息，判断是否是合法的格式     
      * @param filePath
      *            文件路径
      * @return 是否合法
      */
     public static boolean isExtension(MultipartFile file) {
       String fileType = getFileHeader(file);
       for(FileExtension extension : FileExtension.values()){
         if(extension.getValue().length() <= fileType.length()){
           if(extension.getValue().equals(fileType.substring(0, extension.getValue().length()))){
              return true;
           }
         }else{
           //LogConsole.info("配置的文件头参数[" + extension + "]大于获取的文件头参数[" + fileType + "]长度");
         }
       }
       return false;
     }
     /**
      * 根据文件路径获取文件头信息
      * 
      * @param file 文件
      * @return 文件头信息
      */
     public static String getFileHeader(MultipartFile file) {
       InputStream is = null;
       String value = null;
       try {
         is = file.getInputStream();
         byte[] b = new byte[4];
         /*
          * int read() 从此输入流中读取一个数据字节。 int read(byte[] b) 从此输入流中将最多 b.length
          * 个字节的数据读入一个 byte 数组中。 int read(byte[] b, int off, int len)
          * 从此输入流中将最多 len 个字节的数据读入一个 byte 数组中。
          */
         is.read(b, 0, b.length);
         value = bytesToHexString(b);
       } catch (Exception e) {
       } finally {
         if (null != is) {
           try {
             is.close();
           } catch (IOException e) {
           }
         }
       }
       return value;
     }
     
     /**
      * 将要读取文件头信息的文件的byte数组转换成string类型表示   , 8位长度
      * 
      * @param src
      *            要读取文件头信息的文件的byte数组
      * @return 文件头信息
      */
     private static String bytesToHexString(byte[] src) {
       StringBuilder builder = new StringBuilder();
       if (src == null || src.length <= 0) {
         return null;
       }
       String hv;
       for (int i = 0; i < src.length; i++) {
         // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
         hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
         if (hv.length() < 2) {
           builder.append(0);
         }
         builder.append(hv);
       }
       System.out.println(builder.toString());
       return builder.toString();
     }
     
     /**
      * 是否是允许尺寸的图片
      * @param file
      * @return    
      * boolean
      */
     public static boolean isAllowSize(File file) {
       try {
         BufferedImage bufreader = ImageIO.read(file);
         int width = bufreader.getWidth();
         int height = bufreader.getHeight();
         if (width < FileConstant.IMAGE_SIZE) {
           return false;
         }else if(height < FileConstant.IMAGE_SIZE){
           return false;
         //等比例  
         }else if(width != height){
           return false;
         }
       } catch (IOException e) {
         e.printStackTrace();
       } catch (Exception e) {
         e.printStackTrace();
       }
       return true;
     }
     
     /**
      * 是否是允许尺寸的图片
      * @param file
      * @return    
      * boolean
      */
     public static boolean isAllowSize(MultipartFile file) {
       try {
         BufferedImage bufreader = ImageIO.read(file.getInputStream());
         int width = bufreader.getWidth();
         int height = bufreader.getHeight();
         if (width < FileConstant.IMAGE_SIZE) {
           return false;
         }else if(height < FileConstant.IMAGE_SIZE){
           return false;
         //等比例  
         }else if(width != height){
           return false;
         }
       } catch (IOException e) {
         e.printStackTrace();
       } catch (Exception e) {
         e.printStackTrace();
       }
       return true;
     }
     /**
      * 删除文件
      * @param filePath   文件全路径
      * void
      */
     public static void deleteFile(String filePath){
       File file = new File(filePath);
       if(file.exists()){
         file.delete();
        // log.info(SessionUtils.logHelper(" Delete TempFile [" + filePath + "] Success"));
       }else{
        // log.error(SessionUtils.logHelper(" Delete TempFile [" + filePath + "] Not Exist"));
       }
     }
     /**
      * 上传文件
      * @param fileUpload
      * @param paramId
      * @param req
      * @return
      * @throws Exception
      */
     public static String docUploadFile(MultipartFile fileUpload,HttpServletRequest req)throws Exception {

 		Calendar cal = Calendar.getInstance();
 		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
 		String mDateTime = formatter.format(cal.getTime());
 		String sPath =req.getSession().getServletContext().getRealPath("/")  + "/upload/goods/";
 		String sFile = mDateTime.substring(0, 4)+"/" +Integer.parseInt(mDateTime.substring(4, 6))+"/" +Integer.parseInt(mDateTime.substring(6, 8));
 		ParseUtil.Mkdir(sPath + sFile); 
 		sFile += "/" + mDateTime + "."
 		+ StringHelper.getFileExt(fileUpload.getOriginalFilename());
 		fileUpload.transferTo(new File(sPath + sFile));
 		return sPath + sFile;
 	}
     /**
      * 转换路径分隔符
      * @param path
      * @return    
      * String
      */
     public static String pathFormat(String path){
       return path.replace("/", File.separator);
     }
     
     public static void main(String[] args) throws Exception {
       System.out.println("是否允许的格式 ：" + isExtension("O:/b111.bmp"));
     }
}

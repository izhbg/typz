package com.izhbg.typz.base.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.izhbg.typz.base.commondto.AttacheFile;


/**
 * 附件相关
 * @author CAI
 *
 */
public class FileUploadUtil {
	
	/**
	 * 上传图片附件
	 * @param request
	 * @param filePath
	 * @param saveFilePathName
	 * @param saveFileName
	 * @param extendes
	 * @return
	 * @throws IOException
	 */
	 public synchronized  AttacheFile saveFileToServer_RestFul(HttpServletRequest request, String filePath, String saveFilePathName, String saveFileName, String[] extendes)
			    throws IOException
	  {
		 MultipartHttpServletRequest multipartRequest=null;
		 try {
			 
			 multipartRequest = (MultipartHttpServletRequest)request;
			 
		 }catch(Exception e) {
			 return null;
		 }
		 
	    CommonsMultipartFile file = (CommonsMultipartFile)multipartRequest
	      .getFile(filePath);
	    AttacheFile attacheFile = null;
	    if ((file != null) && (!file.isEmpty()))
	    {
	      attacheFile = new AttacheFile();
	      String extend = file.getOriginalFilename()
	        .substring(file.getOriginalFilename().lastIndexOf(".") + 1)
	        .toLowerCase();
	      if ((saveFileName == null) || (saveFileName.trim().equals(""))) {
	        saveFileName = UUID.randomUUID().toString() + "." + extend;
	      }
	      if (saveFileName.lastIndexOf(".") < 0) {
	        saveFileName = saveFileName + "." + extend;
	      }
	      float fileSize = Float.valueOf((float)file.getSize()).floatValue();
	      List<String> errors = new ArrayList();
	      boolean flag = true;
	      if (extendes != null) {
	        for (String s : extendes) {
	          if (extend.toLowerCase().equals(s)) {
	            flag = true;
	          }
	        }
	      }
	      if (flag)
	      {
	    	Calendar cal = Calendar.getInstance();
	  		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	  		String mDateTime = formatter.format(cal.getTime());
	    	String sFile = mDateTime.substring(0, 4)+"/" +Integer.parseInt(mDateTime.substring(4, 6))+"/" +Integer.parseInt(mDateTime.substring(6, 8));
	        File path = new File(saveFilePathName+"/"+sFile+"/");
	        if (!path.exists()) {
	          path.mkdirs();
	        }
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	        DataOutputStream out = new DataOutputStream(
	          new FileOutputStream(saveFilePathName+"/"+sFile+"/" + File.separator + 
	          saveFileName));
	        InputStream is = null;
	        try
	        {
	          is = file.getInputStream();
	          int size = (int)fileSize;
	          byte[] buffer = new byte[size];
	          while (is.read(buffer) > 0) {
	            out.write(buffer);
	          }
	        }
	        catch (IOException exception)
	        {
	          exception.printStackTrace();
	        }
	        finally
	        {
	          if (is != null) {
	            is.close();
	          }
	          if (out != null) {
	            out.close();
	          }
	        }
	        attacheFile.setAttachFileName(saveFileName);
	        attacheFile.setAttachShowName(file.getOriginalFilename());
	        attacheFile.setAttachSize(fileSize+"");
	        attacheFile.setPath(saveFilePathName+"/"+sFile+"/" + File.separator + saveFileName);
	      }
	      else
	      {
	        errors.add("不允许的扩展名");
	      }
	    }
	    return attacheFile;
	  }
	 
	 /**
	  * 判断
	  * @param extend
	  * @return
	  */
	 public  boolean isImg(String extend)
	  {
	        boolean ret = false;
	        List list = new ArrayList();
	        list.add("jpg");
	        list.add("jpeg");
	        list.add("bmp");
	        list.add("gif");
	        list.add("png");
	        list.add("tif");
	        for(Iterator iterator = list.iterator(); iterator.hasNext();)
	        {
	            String s = (String)iterator.next();
	            if(s.equals(extend))
	                ret = true;
	        }
	
	        return ret;
	    }
	 /**
	  * 
	  * @param data
	  * @param width
	  * @param height
	  * @return
	  * @throws IOException
	  */
	 public  byte[] scaleImage(byte[] data, int width, int height) throws IOException {  
		    BufferedImage buffered_oldImage = ImageIO.read(new ByteArrayInputStream(data)); 
		    
		    int imageOldWidth = buffered_oldImage.getWidth();  
		    int imageOldHeight = buffered_oldImage.getHeight();  
		    double scale_x = (double) width / imageOldWidth;  
		    double scale_y = (double) height / imageOldHeight;  
		    double scale_xy = Math.min(scale_x, scale_y);  
		    int imageNewWidth = (int) (imageOldWidth * scale_xy);  
		    int imageNewHeight = (int) (imageOldHeight * scale_xy);  
		    BufferedImage buffered_newImage = new BufferedImage(imageNewWidth, imageNewHeight, BufferedImage.TYPE_INT_RGB);  
		    buffered_newImage.getGraphics().drawImage(buffered_oldImage.getScaledInstance(imageNewWidth, imageNewHeight, BufferedImage.SCALE_SMOOTH), 0, 0, null);  
		    buffered_newImage.getGraphics().dispose();  
		    ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();  
		    ImageIO.write(buffered_newImage, "jpeg", outPutStream);  
		    return outPutStream.toByteArray();  
		}
	 public  byte[] scaleImageSize(String sourcePath, int width, int height,float quality) throws IOException {  
		 FileInputStream is = null;  
        ImageInputStream iis = null; 
        ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();  
        is = new FileInputStream(sourcePath);  
        String fileSuffix = sourcePath.substring(sourcePath  
                .lastIndexOf(".") + 1);  
        Iterator<ImageReader> it = ImageIO  
                .getImageReadersByFormatName(fileSuffix);  
        ImageReader reader = it.next();  
        iis = ImageIO.createImageInputStream(is);  
        reader.setInput(iis, true);  
        
        int imageWidth = reader.getWidth(0);
        int imageHeight = reader.getHeight(0);
        
        int newwidth = 0;
        int newheight = 0;
        
        int x= 0;
        int y= 0;
        
        if(imageWidth<=imageHeight*((float)width/height)) {
        	newwidth = imageWidth;
        	newheight = (int) (imageWidth*height/(float)width);
        	y = (imageHeight-newheight)/2;
        }else if(imageHeight<=imageWidth*((float)height/width)) {
        	newwidth = (int) (imageHeight*width/(float)height);
        	newheight = imageHeight;
        	x=(imageWidth-newwidth)/2;
        }
        
        ImageReadParam param = reader.getDefaultReadParam();  
        Rectangle rect = new Rectangle(x, y, newwidth, newheight);  
        param.setSourceRegion(rect);  
        BufferedImage bi = reader.read(0, param); 
        
        
     // 得到指定Format图片的writer  
        Iterator<ImageWriter> iter = ImageIO  
                .getImageWritersByFormatName(fileSuffix);// 得到迭代器  
        ImageWriter writer = (ImageWriter) iter.next(); // 得到writer  
  
        // 得到指定writer的输出参数设置(ImageWriteParam )  
        ImageWriteParam iwp = writer.getDefaultWriteParam();  
        iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // 设置可否压缩  
        iwp.setCompressionQuality(quality); // 设置压缩质量参数  
  
        iwp.setProgressiveMode(ImageWriteParam.MODE_DISABLED);  
  
        ColorModel colorModel = ColorModel.getRGBdefault();  
        // 指定压缩时使用的色彩模式  
        iwp.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel,  
                colorModel.createCompatibleSampleModel(16, 16)));  
  
        // 开始打包图片，写入byte[]  
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 取得内存输出流  
        IIOImage iIamge = new IIOImage(bi, null, null);  
        try {  
            // 此处因为ImageWriter中用来接收write信息的output要求必须是ImageOutput  
            // 通过ImageIo中的静态方法，得到byteArrayOutputStream的ImageOutput  
            writer.setOutput(ImageIO  
                    .createImageOutputStream(byteArrayOutputStream));  
            writer.write(null, iIamge, iwp);  
        } catch (IOException e) {  
            System.out.println("write errro");  
            e.printStackTrace();  
        }  
        
        
        //ImageIO.write(bi, fileSuffix, outPutStream);  
        if (is != null) {  
            try {  
                is.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            is = null;  
        }  
        if (iis != null) {  
            try {  
                iis.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            iis = null;  
        }  
      return byteArrayOutputStream.toByteArray(); 
	 }
	 /** 
	     * 获得指定文件的byte数组 
	     */  
	    public  byte[] getBytes(String filePath){  
	        byte[] buffer = null;  
	        try {  
	            File file = new File(filePath);  
	            FileInputStream fis = new FileInputStream(file);  
	            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
	            byte[] b = new byte[1000];  
	            int n;  
	            while ((n = fis.read(b)) != -1) {  
	                bos.write(b, 0, n);  
	            }  
	            fis.close();  
	            bos.close();  
	            buffer = bos.toByteArray();  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return buffer;  
	    }
	
}

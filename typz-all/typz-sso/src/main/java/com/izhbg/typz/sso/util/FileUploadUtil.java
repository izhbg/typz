package com.izhbg.typz.sso.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.izhbg.typz.sso.auth.dto.TXtAttachFile;


/**
 * 
* @author caixl 
* @date 2016-3-14 上午10:43:05 
*
 */
public class FileUploadUtil {
	/**
	 * 文件上传
	 * @param fileUpload
	 * @param paramId
	 * @param temppath
	 * @param fileconfid
	 * @param userid
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public static TXtAttachFile docUploadFile(MultipartFile fileUpload, 
											  String fileconfid,
											  String userid,
											  HttpServletRequest req)throws Exception {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String mDateTime = formatter.format(cal.getTime());
		 String sPath =req.getSession().getServletContext().getRealPath("/") + "/upload/";
		String sFile = mDateTime.substring(0, 4)+"/" +Integer.parseInt(mDateTime.substring(4, 6))+"/" +Integer.parseInt(mDateTime.substring(6, 8));
		ParseUtil.Mkdir(sPath + sFile);
		sFile += "/" + mDateTime + "."
				+ StringHelper.getFileExt(fileUpload.getOriginalFilename());
		fileUpload.transferTo(new File(sPath + sFile));
		
		TXtAttachFile mainTableAttachFile = new TXtAttachFile();
		mainTableAttachFile.setAttachFileName(mDateTime + "."
				+ StringHelper.getFileExt(fileUpload.getOriginalFilename()));
		mainTableAttachFile.setAttachShowName(fileUpload.getOriginalFilename());
		mainTableAttachFile.setAttachSize(fileUpload.getSize()+"");
		mainTableAttachFile.setAttachTime(new Date());
		mainTableAttachFile.setConfId(fileconfid);
		mainTableAttachFile.setYhId(userid);
		mainTableAttachFile.setAttachType("1");
		mainTableAttachFile.setPath(sPath + sFile);
		//FileUtils.copyFile( , );
		return mainTableAttachFile;
	}
	
	/**
	 * 
	 * @param fileUpload
	 * @param paramId
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public static String docUploadFile(MultipartFile fileUpload, int paramId,HttpServletRequest req)throws Exception {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String mDateTime = formatter.format(cal.getTime());
		String sPath =req.getRealPath("/") + "/upload/";
		String sFile = mDateTime.substring(0, 4)+"/" +Integer.parseInt(mDateTime.substring(4, 6))+"/" +Integer.parseInt(mDateTime.substring(6, 8));
		ParseUtil.Mkdir(sPath + sFile);
		sFile += "/" + mDateTime + "."
		+ StringHelper.getFileExt(fileUpload.getOriginalFilename());
		fileUpload.transferTo(new File(sPath + sFile));
		return sFile;
	}
	/**
	 * 按比例裁切图片
	 * @param sourcePath
	 * @param width
	 * @param height
	 * @param quality
	 * @return
	 * @throws IOException
	 */
	 public  byte[] scaleImageSize(String sourcePath, int width, int height,float quality) throws IOException {  
		 FileInputStream is = null;  
        ImageInputStream iis = null; 
        ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();  
        is = new FileInputStream(sourcePath);  
        String fileSuffix = sourcePath.substring(sourcePath  
                .lastIndexOf(".") + 1);  
        Iterator<ImageReader> it = ImageIO
                .getImageReadersBySuffix(fileSuffix);  
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
}

package com.izhbg.typz.im.goods.restf.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.FileUploadUtil;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.shop.goods.dto.TShGoodsImage;
import com.izhbg.typz.shop.goods.service.TShGoodsImageService;

/**
 * 
* @ClassName: GoodsImgController 
* @Description: 商品图片controoler
* @author caixl 
* @date 2016-7-1 下午1:33:46 
*
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/im/goodsImg")
public class GoodsImgController {
	
	@Autowired
	private TShGoodsImageService tShGoodsImageService;
	/**
	 * 下载图片
	 * @param request
	 * @param response
	 * @param attachId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/download", method={RequestMethod.GET, RequestMethod.POST})
	public String downloadFileImg(HttpServletRequest request,HttpServletResponse response,@RequestParam("attachId") String attachId) throws Exception {
		TShGoodsImage tShGoodsImage = null;
		if(!(attachId==null||"".equals(attachId)||"undefined".equals(attachId))){
			tShGoodsImage = tShGoodsImageService.getById(attachId);
		}
		
		String filePath = tShGoodsImage.getPath();
		
		//解决IE和fireFox乱码中文名
		String agent = (String)request.getHeader("USER-AGENT");   
		
		response.addHeader("Content-Disposition","attachment;filename="+ IdGenerator.getInstance().getUniqTime()+"."+com.izhbg.typz.base.util.StringHelper.getFileExt(filePath) );
		response.setContentType("application/x-attachment");
		// 写出流信息
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		FileInputStream input = null;
		try {
			input = new FileInputStream(filePath);
			bis = new BufferedInputStream(input);
			bos = new BufferedOutputStream(response.getOutputStream());
			
			byte[] buff = new byte[2048];
			int bytesRead;
			
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			
		} catch (final Exception e) {
			System.out.println("IOException." + e);
			
		} finally {
			if (input != null)
				input.close();
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;
	}
	
	/**
	 * 下载图片
	 * @param goodsAttId
	 * @param width
	 * @param height
	 * @param response
	 */
	@RequestMapping(value = "/downloadIndex")  
	public void downloadIndex (@RequestParam(value = "attachId", required = true, defaultValue = "1") String attachId,
							   @RequestParam(value = "width", required = false, defaultValue = "4") int width,
							   @RequestParam(value = "height", required = false, defaultValue = "3") int height,
							   @RequestParam(value = "quality", required = false, defaultValue = "1") float quality,
							   HttpServletRequest request,
							   HttpServletResponse response){
			
		if(StringHelper.isNotEmpty(attachId)){
			TShGoodsImage tShGoodsImage = null;
			try {
				tShGoodsImage = tShGoodsImageService.getById(attachId);
				byte[] data = null;
				try {
					if (width != 0 && height != 0) {  
						FileUploadUtil fileUploadUtil = new FileUploadUtil();
						data = fileUploadUtil.scaleImageSize(tShGoodsImage.getPath(), width, height,quality);  
					}  
					response.setContentType("image/jpeg");  
					response.setCharacterEncoding("UTF-8");  
					OutputStream outputSream = response.getOutputStream();  
					InputStream in = new ByteArrayInputStream(data);  
					int len = 0;  
					byte[] buf = new byte[1024];  
					while ((len = in.read(buf, 0, 1024)) != -1) {  
						outputSream.write(buf, 0, len);  
					}  
					outputSream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}  
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		}else{
			response.setContentType("text/javascript");  
			response.setCharacterEncoding("UTF-8");  
			try {
				OutputStream outputSream = response.getOutputStream();
				
				String result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, Constants.SYSTEMMSG_EMPTYFILED);
				byte[] buf = result.getBytes();
				outputSream.write(buf, 0, buf.length);  
				outputSream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
	}
}

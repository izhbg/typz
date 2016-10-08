package com.izhbg.typz.im.store.restf.controller;

import java.io.ByteArrayInputStream;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.Constants;
import com.izhbg.typz.base.util.FileUploadUtil;
import com.izhbg.typz.shop.goods.dto.TShGoodsImage;
import com.izhbg.typz.shop.store.dto.TShStoreAttachefile;
import com.izhbg.typz.shop.store.service.TShStoreAttacheFileService;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/im/storeImg")
public class StoreImgController {
	@Autowired
	private TShStoreAttacheFileService tShStoreAttacheFileService;
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
			TShStoreAttachefile tShGoodsImage = null;
			try {
				tShGoodsImage = tShStoreAttacheFileService.getById(attachId);
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

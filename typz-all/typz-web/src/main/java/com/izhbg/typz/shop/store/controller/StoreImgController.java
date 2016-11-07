package com.izhbg.typz.shop.store.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.izhbg.typz.base.util.Ajax;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.PropertiesCommonUtil;
import com.izhbg.typz.shop.common.util.AjaxResponseUtils;
import com.izhbg.typz.shop.common.util.CMYKToRGB;
import com.izhbg.typz.shop.common.util.FileUploadUtils;
import com.izhbg.typz.shop.common.util.ImageUtil;
import com.izhbg.typz.shop.goods.dto.ImageCutDto;
import com.izhbg.typz.shop.store.dto.TShStoreAttachefile;
import com.izhbg.typz.shop.store.service.TShStoreAttacheFileService;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 
* @ClassName: GoodsImgController 
* @Description: 店铺图片controoler
* @author caixl 
* @date 2016-7-1 下午1:33:46 
*
 */
@Controller
@RequestMapping("/storeImg")
public class StoreImgController {
	
	@Autowired
	private TShStoreAttacheFileService tShStoreAttacheFileService;
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 */
	@RequestMapping("upload")
	@ResponseBody
	public void upload(HttpServletRequest request, HttpServletResponse response) {
	    try {
	      /** 创建一个通用的多部分解析器 */
	      CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
	      /** 判断 request 是否有文件上传,即多部分请求 */
	      if (multipartResolver.isMultipart(request)) {
	        /** 转换成多部分request */
	        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
	        /** 取得request中的所有文件名 */
	        Iterator<String> iter = multiRequest.getFileNames();
	        StringBuffer imageList = new StringBuffer();
	
	        int succCount = 0;
	        int tooBigCount = 0;
	        int typeErrorCount = 0;
	        int failCount = 0;
	
	        while (iter.hasNext()) {
	          /** 取得上传文件 */
	          List<MultipartFile> files = multiRequest.getFiles(iter.next());
	          for (MultipartFile file : files) {
	            if (file!=null) {
	
	              /** 验证文件格式 */
	              if (!FileUploadUtils.isExtension(file)) {
	                typeErrorCount++;
	                continue;
	              }
	
	              /** 验证图片是否是CMYK色彩模式 */
	              if (!CMYKToRGB.isRgbOrCmyk(file)) {
	                typeErrorCount++;
	                continue;
	              }
	              /** 验证文件大小 */
	              PropertiesCommonUtil propertiesUtil = new PropertiesCommonUtil(
	      				"application.properties");
	      		  String IMAGE_MAXSIZE = propertiesUtil
	      				.getProp("goods.imges.maxsize");
	      		  if(StringHelper.isNotEmpty(IMAGE_MAXSIZE))
	              if (file.getSize() > Long.parseLong(IMAGE_MAXSIZE)) {
	                tooBigCount++;
	                continue;
	              }
	      		  
	              /** 保存到tfs服务器 */
	      		  String path = FileUploadUtils.docUploadFile(file, request);
	      		  TShStoreAttachefile tShStoreAttachefile = new TShStoreAttachefile();
	      		  tShStoreAttachefile.setId(IdGenerator.getInstance().getUniqTime()+"");
	      		  tShStoreAttachefile.setPath(path);
	      		  tShStoreAttachefile.setTime(new Date());
	      		  tShStoreAttachefile.setState(Integer.parseInt(multiRequest.getParameter("state")));
	      		  tShStoreAttachefile.setStoreId(multiRequest.getParameter("storeId"));
	      		  tShStoreAttacheFileService.add(tShStoreAttachefile);
	      		  imageList.append(tShStoreAttachefile.getId()).append(",");
	      		  succCount++;
	            }
	          }
	        }
	
	        if (tooBigCount == 0 && typeErrorCount == 0 && failCount == 0) {
	          responseMessage(response, FileState.OK, imageList.substring(0, imageList.length() - 1)
	              .toString());
	        } else {
	          StringBuffer stringBuffer = new StringBuffer();
	          stringBuffer.append(succCount).append("张上传成功，");
	          if (tooBigCount > 0) {
	            stringBuffer.append(tooBigCount).append("张过大，");
	          }
	          if (typeErrorCount > 0) {
	            stringBuffer.append(typeErrorCount).append("张格式错误，");
	          }
	          if (failCount > 0) {
	            stringBuffer.append(failCount).append("张上传失败，");
	          }
	          stringBuffer.append("请重新选择！");
	          responseErrorMessage(response, stringBuffer.toString(),
	              "");
	        }
	      } else {
	        responseMessage(response, FileState.NULL_FILE, "");
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	      responseMessage(response, FileState.ERROR, "");
	    }

	}
	/**
	   * 裁剪图片
	   * 
	   * @param request
	   * @param response
	   */
	  @RequestMapping("cutImage")
	  public void cutImage(HttpServletRequest request, HttpServletResponse response,
	      ImageCutDto imageCutDto) throws Exception {
		  
	    String fileId = imageCutDto.getFileId();
	    TShStoreAttachefile tShStoreAttachefile = tShStoreAttacheFileService.getById(fileId);
	    /** 文件保存目录 */
	    Calendar cal = Calendar.getInstance();
 		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
 		String mDateTime = formatter.format(cal.getTime());
	    String sPath =request.getSession().getServletContext().getRealPath("/")  + "/upload/goods/";
 		String sFile = mDateTime.substring(0, 4)+"/" +Integer.parseInt(mDateTime.substring(4, 6))+"/" +Integer.parseInt(mDateTime.substring(6, 8));
	    String saveDirectoryPath = FileUploadUtils.pathFormat(sPath+sFile);
	    /** 如果文件目录不存在，创建文件目录 */
	    File tempPath = new File(saveDirectoryPath);
	    if (!tempPath.exists()) {
	      tempPath.mkdirs();
	    }


	    /** 生成新的全文件名 */
	    String newFile = saveDirectoryPath+"/"+ IdGenerator.getInstance().getUniqTime() +"."+com.izhbg.typz.base.util.StringHelper.getFileExt(tShStoreAttachefile.getPath());
	    // 裁剪图片
	    cutImage(tShStoreAttachefile.getPath(), newFile, imageCutDto);
	    
	  
	    FileUploadUtils.deleteFile(tShStoreAttachefile.getPath());
	    tShStoreAttachefile.setPath(newFile);
	    tShStoreAttacheFileService.update(tShStoreAttachefile);
	    responseMessage(response, FileState.OK, tShStoreAttachefile.getId());
	  }
	  /**
	   * 裁剪图片
	   * 
	   * @param sourceFile 源文件路径
	   * @param targetFile 目标文件路径
	   * @param imageCutDto 图片裁剪dto
	   * @throws Exception
	   */
	  private void cutImage(String sourceFile, String targetFile, ImageCutDto imageCutDto)
	      throws Exception {
	    File newFile = new File(targetFile);
	    if (!newFile.exists()) {
	      newFile.createNewFile();
	    }

	    int selectorX = getInt(imageCutDto.getSelectorX());
	    int selectorY = getInt(imageCutDto.getSelectorY());
	    int selectorW = getInt(imageCutDto.getSelectorW());
	    int selectorH = getInt(imageCutDto.getSelectorH());

	    /** 使用imageMagick裁剪图片 */
	    boolean result = ImageUtil
	        .cutImage(sourceFile, targetFile, selectorX, selectorY, selectorW, selectorH, selectorW,
	            selectorH);

	    // 如果imageMagick裁剪图片失败 则使用thumbnail
	    if (!result) {
	      Thumbnails.of(sourceFile).sourceRegion(selectorX, selectorY, selectorW, selectorH)
	          .size(selectorW, selectorH)
	              .toFile(newFile);
	    }
	  }
	  /**
	   * 删除附件
	   * @param imageId
	   */
	  @RequestMapping("delImage")
	  @ResponseBody
	  private String delImage(@RequestParam(value="imageId",required = true)String imageId){
		  String result = null;
		  try {
			  tShStoreAttacheFileService.delete(imageId);
			result = Ajax.JSONResult(1, "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(0, "操作失败");
		}
		  return result;
	  }
	  private int getInt(Double d) {
	    if (d == null) {
	      return 0;
	    } else {
	      return new BigDecimal(d).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	    }
	  }
	/**
	 * 下载图片
	 * @param request
	 * @param response
	 * @param attachId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/downloaImgdFile", method={RequestMethod.GET, RequestMethod.POST})
	public String downloadFileImg(HttpServletRequest request,HttpServletResponse response,@RequestParam("attachId") String attachId) throws Exception {
		TShStoreAttachefile tShStoreAttachefile = null;
		if(!(attachId==null||"".equals(attachId)||"undefined".equals(attachId))){
			tShStoreAttachefile = tShStoreAttacheFileService.getById(attachId);
		}
		
		String filePath = tShStoreAttachefile.getPath();
		
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
	   * @param @param response
	   * @param @param state
	   * @param @param url
	   * @return void
	   * @Description: 异步请求返回结果
	   */
	  private void responseMessage(HttpServletResponse response, FileState state, String url) {
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("code", state.getCode() + "");
	    map.put("message", state.getMessage());
	    map.put("url", url);
	    AjaxResponseUtils.ajax(map, response);
	  }

	  /**
	   * @param @param response
	   * @param @param state
	   * @param @param url
	   * @return void
	   * @Description: 异步请求返回结果
	   */
	  private void responseErrorMessage(HttpServletResponse response, String message, String url) {
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("code", "202");
	    map.put("message", message);
	    map.put("url", url);
	    AjaxResponseUtils.ajax(map, response);
	  }
	/**
	 * 
	* @ClassName: FileState 
	* @Description: 返回信息 
	* @author caixl 
	* @date 2016-7-1 下午2:19:08 
	*
	 */
	public enum FileState {
	
	    OK(200, "上传成功"), ERROR(500, "上传失败"), OVER_FILE_LIMIT(501, "超过上传大小限制"), NO_SUPPORT_EXTENSION(
	        502, "不支持的图片格式"), NO_SUPPORT_COLORTYPE(503, "不支持cmyk色彩模式的图片，请重新上传！"), NULL_FILE(504,
	        "无上传文件"), PIX_RESOLUTION(201, "图片必须为等比例,大于等于800x800像素"), SAVE_ERROR(202, "图片服务器保存图片失败");
	    /**
	     * 状态码
	     */
	    private int code;
	    /**
	     * 状态描述
	     */
	    private String message;
	
	    private FileState(int code, String message) {
	      this.code = code;
	      this.message = message;
	    }
	
	    public int getCode() {
	      return code;
	    }
	
	    public String getMessage() {
	      return message;
	    }
	  }
}

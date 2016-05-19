package com.izhbg.typz.sso.auth.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.izhbg.typz.base.common.service.ControllerException;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.sso.auth.UserAuthDTO;
import com.izhbg.typz.sso.auth.dto.TXtAttachFile;
import com.izhbg.typz.sso.auth.service.TXtAttachFileService;
import com.izhbg.typz.sso.util.FileUploadUtil;
import com.izhbg.typz.sso.util.SpringSecurityUtils;

@Controller
@RequestMapping("/attachefile")
public class AttacheFileController {

	private TXtAttachFileService tXtAttachFileService;
	
	/**
	 * 上传附件
	 * @param request
	 * @param response
	 * @param uploadFile
	 * @param temppath
	 * @param fileconfid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/upload", method={RequestMethod.GET, RequestMethod.POST})
	public String uploadFileAttach(	HttpServletRequest request,
									HttpServletResponse response,
									@RequestParam("uploadFile") MultipartFile uploadFile,
									@RequestParam(value="temppath",required=false) String temppath,
									@RequestParam(value="fileconfid",required=false) String fileconfid) throws Exception {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		if(fileconfid==null||"".equals(fileconfid)||"undefined".equals(fileconfid)){
			fileconfid = formatter.format(cal.getTime());
		}
		if(uploadFile!= null){
			TXtAttachFile mainTableAttachFile = FileUploadUtil.docUploadFile(uploadFile,fileconfid,SpringSecurityUtils.getCurrentUserId()+"",request);
			mainTableAttachFile.setYhMc(SpringSecurityUtils.getCurrentUsername());
			mainTableAttachFile.setId(IdGenerator.getInstance().getUniqTime()+"");
			tXtAttachFileService.attacheFile_save(mainTableAttachFile);
			try {
				response.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
				String file_Name = request.getContextPath()+"/attachefile/downloadFile.izhbg?attachId="+mainTableAttachFile.getId();
				String result = new JSONObject().element("result",0).element("msg", "{\"success\":\"" + true + "\"}")
												.element("mainTableAttachFile", mainTableAttachFile)
												.element("file_path", file_Name)
												.toString();
				out.print(result);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	/**
	 * 刷新附件列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryFilelist", method={RequestMethod.GET, RequestMethod.POST})
	public String queryFilelist(@RequestParam("fileconfid") String fileconfid,Model model) throws Exception {
		List<TXtAttachFile> list = null;
		if(fileconfid!=null||!"".equals(fileconfid)){
			list= tXtAttachFileService.attacheFile_queryByConfigId(fileconfid);
		}
		//获取用户信息
		UserAuthDTO user = (UserAuthDTO)SpringSecurityUtils.getCurrentUser();
		model.addAttribute("user", user);
		model.addAttribute("attachList", list);
		return "admin/attachefile/queryfilelist";
	}
	/**
	 * 更新附件名称
	 * @param attachId
	 * @param realtableid
	 * @param tableName
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getAttachName", method={RequestMethod.GET, RequestMethod.POST})
	public String getAttachName(@RequestParam("attachId") String attachId,
								@RequestParam("realtableid") String realtableid,
								@RequestParam("tableName") String tableName,
								Model model) throws Exception {
		
		
		TXtAttachFile mainTableAttachFile = null;
		if(attachId!=null||!"".equals(attachId)){
			mainTableAttachFile= tXtAttachFileService.attacheFile_queryById(attachId);
		}
		model.addAttribute("attacheId", mainTableAttachFile.getId());
		model.addAttribute("attacheName", mainTableAttachFile.getAttachShowName());
		return "admin/attachefile/getattachname";
	}
	/**
	 * 更新附件名称
	 * @param tXtAttachFile
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updateAttachName", method={RequestMethod.GET, RequestMethod.POST})
	public String updateAttachName(@RequestParam("attacheId") String attacheId,
								   @RequestParam("attacheName") String attacheName,
								   HttpServletResponse response) throws Exception {
		if(StringHelper.isEmpty(attacheName)||StringHelper.isEmpty(attacheId))
			throw new ControllerException("参数为空，修改附件名称失败");
		TXtAttachFile tXtAttachFile = tXtAttachFileService.attacheFile_queryById(attacheId);
		if(tXtAttachFile!=null)
			tXtAttachFile.setAttachShowName(attacheName);
		
		tXtAttachFileService.attacheFile_update(tXtAttachFile);
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			String result = new JSONObject().element("result",0).element("msg", "更新成功").toString();
			out.print(result);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 下载附件
	 * @param request
	 * @param response
	 * @param attachId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/downloadFile", method={RequestMethod.GET, RequestMethod.POST})
	public String downloadFile(HttpServletRequest request,
							   HttpServletResponse response,
							   @RequestParam("attachId") String attachId) throws Exception {
		TXtAttachFile mainTableAttachFile = null;
		if(!(attachId==null||"".equals(attachId)||"undefined".equals(attachId))){
			mainTableAttachFile = tXtAttachFileService.attacheFile_queryById(attachId);
		}
		String fileName=mainTableAttachFile.getAttachShowName();
		String filePath = mainTableAttachFile.getPath();
		
		String fileNameSrc = fileName;
		//解决IE和fireFox乱码中文名
		fileName = URLEncoder.encode(fileName, "UTF-8");
		  //解决文件名字乱码问题
		if (fileName.length() > 150)
		    fileName = new String(fileNameSrc.getBytes("GBK"), "ISO-8859-1");
		//response.reset();
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");
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
	 * 删除
	 * @param request
	 * @param response
	 * @param attachId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteFile", method={RequestMethod.GET, RequestMethod.POST})
	public String deleteFile(HttpServletRequest request,
							 HttpServletResponse response,
							 @RequestParam("attachId") String attachId) throws Exception {
		if(StringHelper.isEmpty(attachId))
			throw new ControllerException("参数为空，删除附件失败");
		tXtAttachFileService.attacheFile_removeById(attachId);
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			String result = new JSONObject().element("result",0).element("msg", "删除成功").toString();
			out.print(result);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	@Resource
	public void settXtAttachFileService(TXtAttachFileService tXtAttachFileService) {
		this.tXtAttachFileService = tXtAttachFileService;
	}
	
	
}

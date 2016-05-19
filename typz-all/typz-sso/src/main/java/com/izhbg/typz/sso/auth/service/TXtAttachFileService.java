package com.izhbg.typz.sso.auth.service;

import java.util.List;

import com.izhbg.typz.sso.auth.dto.TXtAttachFile;

/**
 * 附件处理服务
* @author caixl 
* @date 2016-5-17 下午1:46:17 
*
 */
public interface TXtAttachFileService {
	/**
	 * 保存附件
	 * @param tXtAttachFile
	 * @throws Exception
	 */
	public void attacheFile_save(TXtAttachFile tXtAttachFile) throws Exception;
	/**
	 * 更新附件
	 * @param tXtAttachFile
	 * @throws Exception
	 */
	public void attacheFile_update(TXtAttachFile tXtAttachFile) throws Exception;
	/**
	 * 根据config 获取附件列表
	 * @param fileConfigId
	 * @return
	 * @throws Exception
	 */
	public List<TXtAttachFile> attacheFile_queryByConfigId(String fileConfigId) throws Exception;
	/**
	 * 根据ID获取附件
	 * @param attacheFileId
	 * @return
	 * @throws Exception
	 */
	public TXtAttachFile attacheFile_queryById(String attacheFileId) throws Exception;
	/**
	 * 根据ID删除附件
	 * @param attacheFileId
	 * @throws Exception
	 */
	public void attacheFile_removeById(String attacheFileId) throws Exception;
	/**
	 * 根据configID删除附件
	 * @param fileConfigId
	 * @throws Exception
	 */
	public void attacheFile_removeByConfigId(String fileConfigId) throws Exception;
	
	
}

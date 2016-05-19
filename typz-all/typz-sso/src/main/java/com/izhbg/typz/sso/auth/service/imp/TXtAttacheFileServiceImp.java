package com.izhbg.typz.sso.auth.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.common.service.ServiceException;
import com.izhbg.typz.sso.auth.dto.TXtAttachFile;
import com.izhbg.typz.sso.auth.manager.TXtAttachFileManager;
import com.izhbg.typz.sso.auth.service.TXtAttachFileService;

@Transactional
@Service("tXtAttachFileService")
public class TXtAttacheFileServiceImp implements TXtAttachFileService{

	private TXtAttachFileManager tXtAttachFileManager;
	@Override
	public void attacheFile_save(TXtAttachFile tXtAttachFile) throws Exception {
		if(tXtAttachFile==null)
			throw new ServiceException("参数为空，保存附件失败");
		tXtAttachFileManager.save(tXtAttachFile);
	}

	@Override
	public List<TXtAttachFile> attacheFile_queryByConfigId(String fileConfigId)
			throws Exception {
		if(StringHelper.isEmpty(fileConfigId))
			throw new ServiceException("参数为空，获取附件失败");
		return tXtAttachFileManager.findBy("confId", fileConfigId);
	}

	@Override
	public TXtAttachFile attacheFile_queryById(String attacheFileId)
			throws Exception {
		if(StringHelper.isEmpty(attacheFileId))
			throw new ServiceException("参数为空，获取附件失败");
		return tXtAttachFileManager.findUniqueBy("id", attacheFileId);
	}

	@Override
	public void attacheFile_removeById(String attacheFileId) throws Exception {
		if(StringHelper.isEmpty(attacheFileId))
			throw new ServiceException("参数为空，删除附件失败");
		TXtAttachFile tXtAttachFile = this.attacheFile_queryById(attacheFileId);
		if(tXtAttachFile!=null)
			tXtAttachFileManager.remove(tXtAttachFile);
		
	}

	@Override
	public void attacheFile_removeByConfigId(String fileConfigId)
			throws Exception {
		if(StringHelper.isEmpty(fileConfigId))
			throw new ServiceException("参数为空，删除附件失败");
		List<TXtAttachFile> tXtAttachFiles = this.attacheFile_queryByConfigId(fileConfigId);
		if(tXtAttachFiles!=null&&tXtAttachFiles.size()>0)
			tXtAttachFileManager.removeAll(tXtAttachFiles);
	}
	@Override
	public void attacheFile_update(TXtAttachFile tXtAttachFile)
			throws Exception {
		if(tXtAttachFile==null||StringHelper.isEmpty(tXtAttachFile.getId()))
			throw new ServiceException("参数为空，更新附件失败");
		tXtAttachFileManager.update(tXtAttachFile);
	}
	@Resource
	public void settXtAttachFileManager(TXtAttachFileManager tXtAttachFileManager) {
		this.tXtAttachFileManager = tXtAttachFileManager;
	}

	
	
	

}

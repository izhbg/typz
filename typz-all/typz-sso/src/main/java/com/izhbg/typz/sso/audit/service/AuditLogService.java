package com.izhbg.typz.sso.audit.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.sso.audit.dto.AuditLog;
import com.izhbg.typz.sso.audit.manager.AuditLogManager;
/**
 * 
* @ClassName: AuditLogService 
* @Description: 日志处理 
* @author caixl 
* @date 2016-5-11 上午11:15:27 
*
 */
@Service
@Transactional
public class AuditLogService {
	
	private AuditLogManager auditLogManager;
	/**
	 * 保存
	 * @param auditLog
	 */
	public void log(AuditLog auditLog){
		auditLogManager.save(auditLog);
	} 
	/**
	 * 批量保存
	 * @param auditLogs
	 */
	public void batchLog(List<AuditLog> auditLogs){
		for(AuditLog auditLog:auditLogs){
			auditLogManager.save(auditLog);
		}
	}
	
	@Resource
	public void setAuditLogManager(AuditLogManager auditLogManager) {
		this.auditLogManager = auditLogManager;
	}
	
	
}

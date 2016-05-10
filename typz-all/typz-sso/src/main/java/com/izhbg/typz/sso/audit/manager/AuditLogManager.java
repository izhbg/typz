package com.izhbg.typz.sso.audit.manager;

import org.springframework.stereotype.Service;

import com.izhbg.typz.base.hibernate.HibernateEntityDao;
import com.izhbg.typz.sso.audit.dto.AuditLog;
@Service
public class AuditLogManager extends HibernateEntityDao<AuditLog>
{

}

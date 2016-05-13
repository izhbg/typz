package com.izhbg.typz.sso.audit.manager;

import org.springframework.stereotype.Repository;

import com.izhbg.typz.base.hibernate.HibernateEntityDao;
import com.izhbg.typz.sso.audit.dto.AuditLog;
@Repository
public class AuditLogManager extends HibernateEntityDao<AuditLog>
{

}

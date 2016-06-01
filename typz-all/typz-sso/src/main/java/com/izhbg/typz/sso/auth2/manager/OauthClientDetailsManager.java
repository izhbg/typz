package com.izhbg.typz.sso.auth2.manager;

import org.springframework.stereotype.Repository;

import com.izhbg.typz.base.hibernate.HibernateEntityDao;
import com.izhbg.typz.sso.auth2.dto.OauthClientDetails;

@Repository
public class OauthClientDetailsManager extends HibernateEntityDao<OauthClientDetails> {

}

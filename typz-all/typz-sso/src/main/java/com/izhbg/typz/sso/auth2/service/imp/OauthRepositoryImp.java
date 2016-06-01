/*
 * Copyright (c) 2015 MONKEYK Information Technology Co. Ltd
 * www.monkeyk.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * MONKEYK Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with MONKEYK Information Technology Co. Ltd.
 */
package com.izhbg.typz.sso.auth2.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.izhbg.typz.sso.auth2.dto.OauthClientDetails;
import com.izhbg.typz.sso.auth2.dto.OauthClientDetailsRowMapper;
import com.izhbg.typz.sso.auth2.dto.OauthRepository;

import java.util.List;

@Repository("oauthRepository")
public class OauthRepositoryImp implements OauthRepository {



    private static OauthClientDetailsRowMapper oauthClientDetailsRowMapper = new OauthClientDetailsRowMapper();


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public OauthClientDetails findOauthClientDetails(String clientId) {
        final String sql = " select * from oauth_client_details where  client_id = ? ";
        final List<OauthClientDetails> list = this.jdbcTemplate.query(sql, new Object[]{clientId}, oauthClientDetailsRowMapper);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<OauthClientDetails> findAllOauthClientDetails() {
        final String sql = " select * from oauth_client_details order by create_time desc ";
        return this.jdbcTemplate.query(sql, oauthClientDetailsRowMapper);
    }

    @Override
    public void updateOauthClientDetailsArchive(String clientId, boolean archive) {
        final String sql = " update oauth_client_details set archived = ? where client_id = ? ";
        this.jdbcTemplate.update(sql, archive, clientId);
    }


	@Override
	public void saveOauthClientDetails(OauthClientDetails clientDetails) {
		final String sql = " insert into oauth_client_details(client_id,resource_ids,client_secret,scope,authorized_grant_types,web_server_redirect_uri," +
                " authorities,access_token_validity,refresh_token_validity,additional_information,trusted,autoapprove) values (?,?,?,?,?,?,?,?,?,?,?,?)";
        
        this.jdbcTemplate.update(sql
        		,clientDetails.clientId()
        		,clientDetails.resourceIds()
        		,clientDetails.clientSecret()
        		,clientDetails.scope()
        		,clientDetails.authorizedGrantTypes()
        		,clientDetails.webServerRedirectUri()
        		,clientDetails.authorities()
        		,clientDetails.accessTokenValidity()
        		,clientDetails.refreshTokenValidity()
        		,clientDetails.additionalInformation()
        		,clientDetails.trusted()
        		,clientDetails.autoApprove()
        		);
		
	}
}

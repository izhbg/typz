package com.izhbg.typz.sso.auth2.service;


import java.util.List;

import com.izhbg.typz.sso.auth2.dto.OauthClientDetails;
import com.izhbg.typz.sso.auth2.dto.OauthClientDetailsDto;



public interface OauthService {

    OauthClientDetails loadOauthClientDetails(String clientId);

    List<OauthClientDetailsDto> loadAllOauthClientDetailsDtos();

    void archiveOauthClientDetails(String clientId);

    OauthClientDetailsDto loadOauthClientDetailsDto(String clientId);

    void registerClientDetails(OauthClientDetailsDto formDto);
    
    String getClientId(String clientId);
}
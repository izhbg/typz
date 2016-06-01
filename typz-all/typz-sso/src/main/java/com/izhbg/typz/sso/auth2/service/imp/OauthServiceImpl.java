package com.izhbg.typz.sso.auth2.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.izhbg.typz.sso.auth.dto.TXtGnjsOauth;
import com.izhbg.typz.sso.auth.dto.TXtYhGnjs;
import com.izhbg.typz.sso.auth.manager.TXtGnjsOauthManager;
import com.izhbg.typz.sso.auth.manager.TXtYhGnjsManager;
import com.izhbg.typz.sso.auth2.dto.OauthClientDetails;
import com.izhbg.typz.sso.auth2.dto.OauthClientDetailsDto;
import com.izhbg.typz.sso.auth2.dto.OauthRepository;
import com.izhbg.typz.sso.auth2.service.OauthService;
import com.izhbg.typz.sso.util.SpringSecurityUtils;

@Service("oauthService")
public class OauthServiceImpl implements OauthService {

    private OauthRepository oauthRepository;
    
    private TXtGnjsOauthManager tXtGnjsOauthManager;

    @Override
    public OauthClientDetails loadOauthClientDetails(String clientId) {
        return oauthRepository.findOauthClientDetails(clientId);
    }

    @Override
    public List<OauthClientDetailsDto> loadAllOauthClientDetailsDtos() {
        List<OauthClientDetails> clientDetailses = oauthRepository.findAllOauthClientDetails();
        return OauthClientDetailsDto.toDtos(clientDetailses);
    }

    @Override
    public void archiveOauthClientDetails(String clientId) {
        oauthRepository.updateOauthClientDetailsArchive(clientId, true);
    }

    @Override
    public OauthClientDetailsDto loadOauthClientDetailsDto(String clientId) {
        final OauthClientDetails oauthClientDetails = oauthRepository.findOauthClientDetails(clientId);
        return oauthClientDetails != null ? new OauthClientDetailsDto(oauthClientDetails) : null;
    }

    @Override
    public void registerClientDetails(OauthClientDetailsDto formDto) {
        OauthClientDetails clientDetails = formDto.createDomain();
        oauthRepository.saveOauthClientDetails(clientDetails);
    }

    @Resource
	public void setOauthRepository(OauthRepository oauthRepository) {
		this.oauthRepository = oauthRepository;
	}

	@Override
	public String getClientId(String clientId) {
		String yhId = SpringSecurityUtils.getCurrentUserId();
		List<TXtGnjsOauth> jss = tXtGnjsOauthManager.find("select to from TXtYhGnjs tg ,TXtGnjsOauth to where tg.jsDm=to.roleId and  to.clientId=? and tg.yhId=?", clientId,yhId);
		if(jss==null||jss.size()==0||jss.size()>1)
			return null;
		else{
			TXtGnjsOauth oauthj = jss.get(0);
			return oauthj.getClientId();
		}
	}
	@Resource
	public void settXtGnjsOauthManager(TXtGnjsOauthManager tXtGnjsOauthManager) {
		this.tXtGnjsOauthManager = tXtGnjsOauthManager;
	}
    
    
}
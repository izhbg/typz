package com.izhbg.typz.sso.auth2.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.izhbg.typz.sso.annotation.SystemControllerLog;
import com.izhbg.typz.sso.auth2.dto.OauthClientDetailsDto;
import com.izhbg.typz.sso.auth2.dto.OauthClientDetailsDtoValidator;
import com.izhbg.typz.sso.auth2.service.OauthService;

@Controller
@RequestMapping("/client")
public class ClientDetailsController {


    private OauthService oauthService;

    private OauthClientDetailsDtoValidator clientDetailsDtoValidator;


    @RequestMapping("client_list")
    public String clientDetails(Model model) {
        List<OauthClientDetailsDto> clientDetailsDtoList = oauthService.loadAllOauthClientDetailsDtos();
        model.addAttribute("clientDetailsDtoList", clientDetailsDtoList);
        return "client/client_list";
    }


    /*
    * Logic delete
    * */
    @RequestMapping("archive_client/{clientId}")
    public String archiveClient(@PathVariable("clientId") String clientId) {
        oauthService.archiveOauthClientDetails(clientId);
        return "redirect:client/client_details.izhbg";
    }

    /*
    * Test client
    * */
    @RequestMapping("test_client/{clientId}")
    public String testClient(@PathVariable("clientId") String clientId, Model model) {
        OauthClientDetailsDto clientDetailsDto = oauthService.loadOauthClientDetailsDto(clientId);
        model.addAttribute("clientDetailsDto", clientDetailsDto);
        return "client/test_client";
    }


    /*
    * Register client
    * */
    @RequestMapping(value = "register_client", method = RequestMethod.GET)
    public String registerClient(Model model) {
        model.addAttribute("formDto", new OauthClientDetailsDto());
        return "client/register_client";
    }


    /*
    * Submit register client
    * */
    @RequestMapping(value = "register_client", method = RequestMethod.POST)
    @SystemControllerLog(description = "添加接口策略")
    public String submitRegisterClient(@ModelAttribute("formDto") OauthClientDetailsDto formDto, BindingResult result) {
        clientDetailsDtoValidator.validate(formDto, result);
        if (result.hasErrors()) {
            return "client/register_client";
        }
        oauthService.registerClientDetails(formDto);
        return "redirect:/client/client_list.izhbg";
    }


    @Resource
	public void setOauthService(OauthService oauthService) {
		this.oauthService = oauthService;
	}

    @Resource
	public void setClientDetailsDtoValidator(
			OauthClientDetailsDtoValidator clientDetailsDtoValidator) {
		this.clientDetailsDtoValidator = clientDetailsDtoValidator;
	}


}
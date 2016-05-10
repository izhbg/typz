package com.izhbg.typz.sso.security.handler;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.izhbg.typz.base.util.CommonUtil;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.base.util.StringUtils;
import com.izhbg.typz.sso.annotation.SystemControllerLog;
import com.izhbg.typz.sso.audit.dto.AuditLog;
import com.izhbg.typz.sso.audit.manager.AuditLogManager;
import com.izhbg.typz.sso.util.SpringContextWrapper;
import com.izhbg.typz.sso.util.SpringSecurityUtils;

public class SimpleLoginSuccessHandler implements AuthenticationSuccessHandler,InitializingBean {  
    
    protected Logger logger = Logger.getLogger(SimpleLoginSuccessHandler.class);
      
    private String defaultTargetUrl;  
      
    private boolean forwardToDestination = false;  
      
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();  
      
    private  AuditLogManager auditLogManager = (AuditLogManager) SpringContextWrapper.getBean("auditLogManager");
      
    /* (non-Javadoc) 
     * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication) 
     */  
    @Override  
    @Transactional(readOnly=false,propagation= Propagation.REQUIRED,rollbackFor={Exception.class})  
    public void onAuthenticationSuccess(HttpServletRequest request,  
            HttpServletResponse response, Authentication authentication)  
            throws IOException, ServletException {  
          
        this.saveAuditLog(request);  
          
        if(this.forwardToDestination){  
            logger.info("Login success,Forwarding to "+this.defaultTargetUrl);  
              
            request.getRequestDispatcher(this.defaultTargetUrl).forward(request, response);  
        }else{  
            logger.info("Login success,Redirecting to "+this.defaultTargetUrl);  
              
            this.redirectStrategy.sendRedirect(request, response, this.defaultTargetUrl);  
        }  
    }  
    
    private void saveAuditLog(HttpServletRequest request){
    	 //*========控制台输出=========*//    
        System.out.println("=====前置通知开始=====");    
        System.out.println("方法描述: 登录成功");    
        System.out.println("请求人:" + SpringSecurityUtils.getCurrentUsername());    
        System.out.println("请求IP:" + CommonUtil.getIpAddress(request));    
        //*========数据库日志=========*//    
        AuditLog log = new AuditLog();
        log.setId(IdGenerator.getInstance().getUniqTime()+"");
        log.setDescription("登录成功");    
        log.setMethod("LoginController");    
        log.setType(0);    
        log.setRequestIp(CommonUtil.getIpAddress(request));    
        log.setExceptionCode( null);    
        log.setExceptionDetail( null);    
        log.setParams( null);    
        log.setCreateBy(SpringSecurityUtils.getCurrentUsername()+"("+SpringSecurityUtils.getCurrentUserId()+")");    
        log.setCreateDate(new Date());  
        log.setAppId(SpringSecurityUtils.getCurrentUserAppId());
        //保存数据库    
        auditLogManager.save(log);   
        System.out.println("=====前置通知结束=====");   
    }
      
  
    public void setDefaultTargetUrl(String defaultTargetUrl) {  
        this.defaultTargetUrl = defaultTargetUrl;  
    }  
  
    public void setForwardToDestination(boolean forwardToDestination) {  
        this.forwardToDestination = forwardToDestination;  
    }  
  
    /* (non-Javadoc) 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet() 
     */  
    @Override  
    public void afterPropertiesSet() throws Exception {  
        if(StringUtils.isEmpty(defaultTargetUrl))  //@TODO :Exception通用处理工具
            throw new Exception("You must configure defaultTargetUrl");  
          
    }

    
    
      
}  
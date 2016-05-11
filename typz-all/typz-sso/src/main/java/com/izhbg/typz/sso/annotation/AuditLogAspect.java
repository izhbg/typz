package com.izhbg.typz.sso.annotation;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.util.JSONUtils;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.izhbg.typz.base.util.CommonUtil;
import com.izhbg.typz.base.util.IdGenerator;
import com.izhbg.typz.sso.audit.component.AuditLogQueue;
import com.izhbg.typz.sso.audit.dto.AuditLog;
import com.izhbg.typz.sso.audit.manager.AuditLogManager;
import com.izhbg.typz.sso.util.SpringSecurityUtils;
/**
 * 
* @ClassName: AuditLogAspect 
* @Description: 切点类
* @author caixl 
* @date 2016-5-10 下午2:50:34 
*
 */
@Aspect    
@Component
public class AuditLogAspect
{
	 private  AuditLogQueue auditLogQueue;
	//本地异常日志记录对象    
     private  static  final Logger logger = Logger.getLogger(AuditLogManager.class); 
     
     //Service层切点    
     @Pointcut("@annotation(com.izhbg.typz.sso.annotation.SystemServiceLog)")    
     public  void serviceAspect() {    
     }
     //Controller层切点    
     @Pointcut("@annotation(com.izhbg.typz.sso.annotation.SystemControllerLog)")    
     public  void controllerAspect() {    
     } 
     
     @Before("controllerAspect()")    
     public  void doBefore(JoinPoint joinPoint) { //@TODO 异步线程处理 
    	 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	 HttpSession session = request.getSession();    
    	 //读取session中的用户    
    	 //请求的IP    
    	 String ip = CommonUtil.getIpAddress(request);
    	//获取用户请求方法的参数并序列化为JSON格式字符串    
         String params = "";    
          if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {    
              for ( int i = 0; i < joinPoint.getArgs().length; i++) {    
                 params += JSONUtils.valueToString(joinPoint.getArgs()[i]) + ";";    
             }    
         }
	     try {    
	        //*========控制台输出=========*//    
	        System.out.println("=====前置通知开始=====");    
	        System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));    
	        System.out.println("方法描述:" + getControllerMethodDescription(joinPoint));    
	        System.out.println("请求人:" + SpringSecurityUtils.getCurrentUsername());    
	        System.out.println("请求IP:" + ip);    
	        //*========数据库日志=========*//    
	        AuditLog log = new AuditLog();
	        log.setId(IdGenerator.getInstance().getUniqTime()+"");
	        log.setDescription(getControllerMethodDescription(joinPoint));    
	        log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));    
	        log.setType(0);    
	        log.setRequestIp(ip);    
	        log.setExceptionCode( null);    
	        log.setExceptionDetail( null);    
	        log.setParams( params);    
	        log.setCreateBy(SpringSecurityUtils.getCurrentUsername()+"("+SpringSecurityUtils.getCurrentUserId()+")");    
	        log.setCreateDate(new Date());    
	        log.setAppId(SpringSecurityUtils.getCurrentUserAppId());
	        //保存数据库    
	        auditLogQueue.add(log);   
	        System.out.println("=====前置通知结束=====");    
	    }  catch (Exception e) {    
	        //记录本地异常日志    
	        logger.error("==前置通知异常==");    
	    }    
     }
     
     
	/**
	 * 获取注解中对方法的描述信息 用于service层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getServiceMthodDescription(JoinPoint joinPoint) throws Exception
	{
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods)
		{
			if (method.getName().equals(methodName))
			{
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length)
				{
					description = method.getAnnotation(SystemServiceLog.class).description();
					break;
				}
			}
		}
		return description;
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception
	{
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods)
		{
			if (method.getName().equals(methodName))
			{
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length)
				{
					description = method.getAnnotation(SystemControllerLog.class).description();
					break;
				}
			}
		}
		return description;
	}
	@Resource
	public void setAuditLogQueue(AuditLogQueue auditLogQueue) {
		this.auditLogQueue = auditLogQueue;
	}
	
	
	
	

}

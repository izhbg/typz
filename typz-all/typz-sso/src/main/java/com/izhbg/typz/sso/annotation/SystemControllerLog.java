package com.izhbg.typz.sso.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 *     
* @ClassName: SystemControllerLog 
* @Description: 自定义注解 拦截Controller  
* @author caixl 
* @date 2016-5-10 下午2:16:12 
*
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented    
public  @interface SystemControllerLog {    
    String description()  default "";    
}    

package com.izhbg.typz.base.common.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存注解
 * 
 * 
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.METHOD})  
public @interface RedisEvict {  
    public String fieldKey(); //缓存key  
    public Class type();//类型
}
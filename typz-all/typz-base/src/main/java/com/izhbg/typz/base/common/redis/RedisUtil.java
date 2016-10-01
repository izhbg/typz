package com.izhbg.typz.base.common.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

@Component
public class RedisUtil {
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;
	
	/**
	 * 添加redis
	 * @param key redis key
	 * @param id  value ID
	 * @param value  value
	 */
	public void add(String key,String id,Object value){
		redisTemplate.opsForHash().put(key, id, value);
	}
	
	/**
	 * 设置过期时间
	 * @param key
	 * @param id
	 * @param value
	 * @param seconds
	 */
	public void add(String key,String id,Object value,int seconds){
		redisTemplate.opsForHash().put(key, id, value);
		redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
	}
	
	/**
	 * 续租redis缓存数据
	 * @param key
	 * @param seconds
	 */
	public void setExpireTime(String key,int seconds){
		redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
	}
	
	/**
	 * 删除redis
	 * @param key
	 * @param id
	 */
	public void delete(String key,String id){
		redisTemplate.opsForHash().delete(key, id);
	}
	/**
	 * 查询 id
	 * @param key
	 * @param id
	 * @return
	 */
	public String getStringById(String key ,String id){
		return (String) redisTemplate.boundHashOps(key).get(id);
	}
	/**
	 * 类型转换
	 * @param key
	 * @param id
	 * @return
	 */
	public <T> T getById(String key ,String id){
		String configInfo = (String)redisTemplate.boundHashOps(key).get(id);
        T t_temp = JSON.parseObject(configInfo,new TypeReference<T>() {});
        return t_temp;
	}
}

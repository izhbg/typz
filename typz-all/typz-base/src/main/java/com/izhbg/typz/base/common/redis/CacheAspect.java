package com.izhbg.typz.base.common.redis;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
/**
 * redis切面处理
* @ClassName: CacheAspect 
* @author caixl 
* @date 2016-6-14 上午10:14:08 
*
 */
@Component
@Aspect
public class CacheAspect {

	public static final Logger infoLog = Logger.getLogger(CacheAspect.class);

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 获取或添加缓存
	 * @param jp
	 * @param cache
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	@Around("@annotation(com.izhbg.typz.base.common.redis.RedisCache)")
	public Object RedisCache(final ProceedingJoinPoint jp)
			throws Throwable {
		Method method=getMethod(jp);
		RedisCache cache = method.getAnnotation(RedisCache.class);
		// 得到类名、方法名和参数
		Object[] args = jp.getArgs();
		
		
		// 根据类名，方法名和参数生成key
		final String key = parseKey(cache.fieldKey(),method,jp.getArgs());
		if (infoLog.isDebugEnabled()) {
			infoLog.debug("生成key:" + key);
		}

		// 得到被代理的方法
		//Method me = ((MethodSignature) jp.getSignature()).getMethod();
		// 得到被代理的方法上的注解
		Class modelType = method.getAnnotation(RedisCache.class).type();

		// 检查redis中是否有缓存
		String value = (String) redisTemplate.opsForHash().get(
				modelType.getName(), key);

		// result是方法的最终返回结果
		Object result = null;
		if (null == value) {
			// 缓存未命中
			if (infoLog.isDebugEnabled()) {
				infoLog.debug("缓存未命中");
			}

			// 调用数据库查询方法
			result = jp.proceed(args);

			// 序列化查询结果
			final String  json = serialize(result);
			final String hashName = modelType.getName();
			final int expire = cache.expire();
			// 序列化结果放入缓存
			/*redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection redisConn) throws DataAccessException {
                    // 配置文件中指定了这是一个String类型的连接
                    // 所以这里向下强制转换一定是安全的
                    StringRedisConnection conn = (StringRedisConnection) redisConn;

                    // 判断hash名是否存在
                    // 如果不存在，创建该hash并设置过期时间
                    if (false == conn.exists(hashName) ){
                        conn.hSet(hashName, key, json);
                        conn.expire(hashName, expire);
                    } else {
                        conn.hSet(hashName, key, json);
                    }

                    return null;
                }
            });*/
			// 序列化结果放入缓存
			redisTemplate.opsForHash().put(modelType.getName(), key, json);
		} else {
			// 缓存命中
			if (infoLog.isDebugEnabled()) {
				infoLog.debug("缓存命中, value = " + value);
			}

			// 得到被代理方法的返回值类型
			Class returnType = ((MethodSignature) jp.getSignature())
					.getReturnType();

			// 反序列化从缓存中拿到的json
			result = deserialize(value, returnType, modelType);

			if (infoLog.isDebugEnabled()) {
				infoLog.debug("反序列化结果 = {}" + result);
			}
		}

		return result;
	}
	/**
	 * 删除缓存
	 * @param jp
	 * @param cache
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.izhbg.typz.base.common.redis.RedisEvict)")
	public Object RedisEvict(final ProceedingJoinPoint jp)
			throws Throwable {
		// 得到被代理的方法
		Method me = ((MethodSignature) jp.getSignature()).getMethod();
		// 得到被代理的方法上的注解
		Class modelType = me.getAnnotation(RedisEvict.class).type();

		if (infoLog.isDebugEnabled()) {
			infoLog.debug("清空缓存:" + modelType.getName());
		}
		// 清除对应缓存
	/*	redisTemplate.opsForHash().delete(paramH, paramArrayOfObject);*/
		redisTemplate.delete(modelType.getName());

		return jp.proceed(jp.getArgs());
	}
	 /**
     *  获取被拦截方法对象
     *  
     *  MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象
     *	而缓存的注解在实现类的方法上
     *  所以应该使用反射获取当前对象的方法对象
     */
    public Method getMethod(ProceedingJoinPoint pjp){
      //获取参数的类型
      Object [] args=pjp.getArgs();
      Class [] argTypes=new Class[pjp.getArgs().length];
      for(int i=0;i<args.length;i++){
        argTypes[i]=args[i].getClass();
      }
      Method method=null;
      try {
        method=pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(),argTypes);
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (SecurityException e) {
        e.printStackTrace();
      }
      return method;
      
    }
	 /**
     *	获取缓存的key 
     *	key 定义在注解上，支持SPEL表达式
     * @param pjp
     * @return
     */
    private String parseKey(String key,Method method,Object [] args){
      //获取被拦截方法参数名列表(使用Spring支持类库)
      LocalVariableTableParameterNameDiscoverer u =   
        new LocalVariableTableParameterNameDiscoverer();  
      String [] paraNameArr=u.getParameterNames(method);
      
      //使用SPEL进行key的解析
      ExpressionParser parser = new SpelExpressionParser(); 
      //SPEL上下文
      StandardEvaluationContext context = new StandardEvaluationContext();
      //把方法参数放入SPEL上下文中
      for(int i=0;i<paraNameArr.length;i++){
        context.setVariable(paraNameArr[i], args[i]);
      }
      return parser.parseExpression(key).getValue(context,String.class);
    }
	/**
	 * 序列化
	 * @param target
	 * @return
	 */
	protected String serialize(Object target) {
		return JSON.toJSONString(target);
	}
	/**
	 * 反序列化
	 * @param jsonString
	 * @param clazz
	 * @param modelType
	 * @return
	 */
	protected Object deserialize(String jsonString, Class clazz, Class modelType) {
		// 序列化结果应该是List对象
		if (clazz.isAssignableFrom(List.class)) {
			return JSON.parseArray(jsonString, modelType);
		}

		// 序列化结果是普通对象
		return JSON.parseObject(jsonString, clazz);
	}
}

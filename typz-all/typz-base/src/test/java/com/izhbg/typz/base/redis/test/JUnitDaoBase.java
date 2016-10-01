package com.izhbg.typz.base.redis.test;  
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;  
  
/** 
 * <b>Summary: </b> TODO Junit 基础类,加载环境  
 * <b>Remarks: </b> TODO DAO测试基础类 
 */  
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:application.properties"})  
public  class JUnitDaoBase extends AbstractTransactionalJUnit4SpringContextTests {
} 
package com.izhbg.typz.sms.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.izhbg.typz.sms.aliyun.utils.Client;
import com.izhbg.typz.sms.aliyun.utils.Constants;
import com.izhbg.typz.sms.aliyun.utils.Method;
import com.izhbg.typz.sms.aliyun.utils.Request;
import com.izhbg.typz.sms.dto.SMS;
import com.izhbg.typz.sms.queue.SMSQueue;

@Component
public class SMSConsumer implements Runnable{
	
	private static Logger logger = LoggerFactory.getLogger(SMSConsumer.class);  
	@Value("${sms.app_key}")
	private  String appKey; //AppKey从控制台获取
	@Value("${sms.app_securt}")
    private  String AppSecret ; //AppSecret从控制台获取
	@Value("${sms.sing_name}")
    private  String singName ; // 签名名称从控制台获取，必须是审核通过的
	@Value("${sms.template_code}")
    private  String templateCode; //模板CODE从控制台获取，必须是审核通过的
	@Value("${sms.host}")
    private  String host ; //API域名从控制台获取
	
	@Autowired
	private SMSQueue sendSmsTaskQueue;
	
	public static final int DEFAULT_BATCH_SIZE = 64;  
    private int batchSize = DEFAULT_BATCH_SIZE;  
    private boolean active = true;  
    private Thread thread;  
    
    @PostConstruct
    public void init() {  
        thread = new Thread(this);  
        thread.start();  
    }  
  
    @PreDestroy  
    public void close() {  
        active = false;  
    }  
  
    public void run() {  
        while (active) {  
            execute();  
        }  
    }  
    public void execute() {  
        List<SMS> smses = new ArrayList<SMS>();  
  
        try {  
            int size = 0;  
  
            while (size < batchSize) {  
            	SMS sms = sendSmsTaskQueue.poll();  
  
                if (sms == null) {  
                    break;  
                }  
  
                smses.add(sms);  
                size++;  
            }  
        } catch (Exception ex) {  
            logger.info(ex.getMessage(), ex);  
        }  
  
        if (!smses.isEmpty()) {  
        	StringBuffer stringBuffer = new StringBuffer();
        	for(SMS sms:smses){
        		stringBuffer.setLength(0);
                stringBuffer.append("/singleSendSms?");
                stringBuffer.append("SignName=" +  this.singName);
                stringBuffer.append("&TemplateCode=" +  this.templateCode );
                stringBuffer.append("&RecNum=" + sms.getPhone());
                stringBuffer.append("&ParamString=${'code':'"+sms.getCode()+"','product':'优否尚品'}");
                Request request =  new Request(Method.GET, "http://" + this.host + stringBuffer.toString(), this.appKey, this.AppSecret, Constants.DEFAULT_TIMEOUT);
                try {
                    HttpResponse response = Client.execute(request);
                    HttpEntity entity = response.getEntity();
                    System.out.println(EntityUtils.toString(entity, "UTF-8"));
                }catch (Exception e){
                	e.printStackTrace();
                } 
        	}
        	
        	
        }  
    }
}

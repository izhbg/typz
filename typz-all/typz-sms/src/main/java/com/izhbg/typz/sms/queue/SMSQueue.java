package com.izhbg.typz.sms.queue;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.izhbg.typz.sms.dto.SMS;
/**
 * 发送短信队列
* @author xiaolong.cai@mtime.com
* @date 2016年10月1日 下午9:38:14 
* @version V1.0
 */
@Component
public class SMSQueue {
	
	private  LinkedBlockingQueue<SMS> queues =  new LinkedBlockingQueue<SMS>();  
	/**
	 * 入队    
	 * @param sms
	 */
    public void add(SMS sms) throws Exception{
        queues.add(sms);  
    }
    /**
     * 出队
     * @return
     * @throws InterruptedException
     */
    public SMS poll() throws InterruptedException {  
        return queues.poll(1, TimeUnit.SECONDS);  
    }  
}

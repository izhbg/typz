package com.izhbg.typz.sms.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 发送短信的线程池
* @author xiaolong.cai@mtime.com
* @date 2016年10月1日 下午9:36:42 
* @version V1.0
 */
public class SMSThreadPool {
	private static ExecutorService threadPool = null;  
    public static ExecutorService getThreadPool(){  
        if(threadPool==null){  
            threadPool = Executors.newCachedThreadPool();  
        }  
        return  threadPool;  
    }  
}

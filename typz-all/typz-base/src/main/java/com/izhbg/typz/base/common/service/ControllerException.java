package com.izhbg.typz.base.common.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 
* @ClassName: ControllerService 
* @Description: 异常处理
* @author caixl 
* @date 2016-5-12 下午1:35:47 
*
 */
public class ControllerException extends RuntimeException{
	public ControllerException(String message) {
		super(message);
	}

	public synchronized Throwable fillInStackTrace() {
		return this;
	}

	public String getMessage() {
		try {
			return String.format("{ServiceIp:\"%s\",Message:\"%s\"}",
					new Object[] { InetAddress.getLocalHost().getHostAddress(),
							super.getMessage() });
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			return String.format("{ServiceIp:\"%s\",Message:\"%s\"}",
					new Object[] { "获取服务器IP失败",
					super.getMessage() });
		}
	}

	public String toString() {
		return getMessage();
	}

	
}

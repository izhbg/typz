package com.izhbg.typz.base.util;

import java.security.MessageDigest;

public class Md5Util {
	public static String MD5Encode(String sourceString) {
		String resultString = null;
		try {
			resultString = new String(sourceString);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}
	public static final String byte2hexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString();
	}
	public static void main(String[] args) throws Exception {
		System.out.println(MD5Encode("app_key=4dd7a69ed7de049c2f45c8d6a335e75bba_auth_mobile=13601203291target_user_id=265timestamp=1303898327905621b62abefa7f739647345fdf30fd97e"));
//		System.out.println(MD5Encode("hello"));
//		System.out.println(MD5Encode("123456"));
//		System.out.println(MD5Encode("111111"));
	}
}

package com.izhbg.typz.base.util;

import java.io.InputStream;
import java.util.Properties;

public final class PropertiesCommonUtil {
	private Properties sysProp;

	public PropertiesCommonUtil(String propertiesName) {
		sysProp = new Properties();
		InputStream in = null;
		try {
			in = PropertiesCommonUtil.class.getClassLoader()
					.getResourceAsStream(propertiesName);
			sysProp.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getProp(String key) {
		return sysProp.getProperty(key);
	}
}

package com.sanwisdom.common.utils;

import java.io.InputStream;
import java.util.Properties;


public class PropertiesUtils {
	
	/**
	 * @param name "/net/viralpatel/resources/config.properties"
	 * @return
	 */
	public static Properties loadProps(String name) {
		Properties configProp = new Properties();
		InputStream in = PropertiesUtils.class.getResourceAsStream(name);
		try {
			configProp.load(in);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return configProp;
	}
}

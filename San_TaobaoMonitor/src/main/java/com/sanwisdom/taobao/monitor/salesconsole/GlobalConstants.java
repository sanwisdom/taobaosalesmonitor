package com.sanwisdom.taobao.monitor.salesconsole;

public class GlobalConstants {

	private static final String SRC = "\\src\\";
	
	private static final String TEMP = "\\temp\\";

	private static final String USER_DIR = System.getProperty("user.dir");

	public static String SHOP_CONFIGURATION = "shop_configuration.json";
	
//	public static String SHOP_CONFIGURATION = USER_DIR + SRC + "shop_configuration.json";

	public static String TOTAL_SHOP_INFO = USER_DIR + SRC + "shop_total.json";
	
	public static String TOTAL_SHOP_INFO_TOP = USER_DIR + SRC + "shop_total_top.json";
	
	public static String TEMP_THUMBNAIL_DIR = USER_DIR + TEMP;
	
	public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
}

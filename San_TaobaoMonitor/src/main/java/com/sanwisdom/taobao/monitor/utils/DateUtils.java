package com.sanwisdom.taobao.monitor.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sanwisdom.taobao.monitor.salesconsole.GlobalConstants;

public class DateUtils extends com.sanwisdom.common.utils.DateUtils {
	public static Date parseDate(String dealDate) {
		try {
			return new SimpleDateFormat(GlobalConstants.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS).parse(dealDate);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}

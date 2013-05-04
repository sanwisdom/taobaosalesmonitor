package com.sanwisdom.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

public class DateUtils {

	static Logger log = Logger.getLogger(DateUtils.class);
	
	public static Date trimDateToMonthEnd(Date date) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		print(gregorianCalendar);
		gregorianCalendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
		gregorianCalendar.set(GregorianCalendar.MINUTE, 0);
		gregorianCalendar.set(GregorianCalendar.SECOND, 0);
		gregorianCalendar.set(GregorianCalendar.MILLISECOND, 0);
		print(gregorianCalendar);
		gregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
		gregorianCalendar.set(GregorianCalendar.MONTH, gregorianCalendar.get(GregorianCalendar.MONTH) + 1);
		gregorianCalendar.add(GregorianCalendar.MILLISECOND, -1);
		return gregorianCalendar.getTime();
	}

	private static void print(GregorianCalendar gregorianCalendar) {
		int year = gregorianCalendar.get(GregorianCalendar.YEAR);
		log.debug("YEAR: " + year);
		int month = gregorianCalendar.get(GregorianCalendar.MONTH);
		log.debug("MONTH: " + month);
		int dayOfMonth = gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH);
		log.debug("DAY_OF_MONTH: " + dayOfMonth);
		int hourOfDay = gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY);
		log.debug("HOUR_OF_DAY: " + hourOfDay);
		int minute = gregorianCalendar.get(GregorianCalendar.MINUTE);
		log.debug("MINUTE: " + minute);
		int second = gregorianCalendar.get(GregorianCalendar.SECOND);
		log.debug("SECOND: " + second);
		int millisecond = gregorianCalendar.get(GregorianCalendar.MILLISECOND);
		log.debug("MILLISECOND: " + millisecond);
	}

	public static Date trimDateToMonthStart(Date date) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		print(gregorianCalendar);
		gregorianCalendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
		gregorianCalendar.set(GregorianCalendar.MINUTE, 0);
		gregorianCalendar.set(GregorianCalendar.SECOND, 0);
		gregorianCalendar.set(GregorianCalendar.MILLISECOND, 0);
		print(gregorianCalendar);
		gregorianCalendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
		return gregorianCalendar.getTime();
	}
	
	public static Date createDate(String dateStr, String format) {
		try {
			return new SimpleDateFormat(format).parse(dateStr);
		} catch (ParseException e) {
			throw new IllegalArgumentException(String.format("Invalid dateStr(%s) or format(%s)", dateStr, format));
		}
	}
}

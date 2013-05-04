package com.sanwisdom.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;


public class DateUtilsTest {
	
	static Logger log = Logger.getLogger(DateUtilsTest.class);

	@Test
	public void test_trimDateToMonthEnd() throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2012-11-18");
		Date trimed = DateUtils.trimDateToMonthEnd(date);
		log.debug(trimed);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String formated = format.format(trimed);
		log.debug("Trimed Date: " + formated);
		Assert.assertEquals("2012-11-30", formated);
	}
	
	@Test
	public void test_trimDateToMonthStart() throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2012-11-18");
		Date trimed = DateUtils.trimDateToMonthStart(date);
		log.debug(trimed);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String formated = format.format(trimed);
		log.debug("Trimed Date: " + formated);
		Assert.assertEquals("2012-11-01", formated);
	}
	
	@Test
	public void test_trimDateToMonthEnd_leapYear() throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2000-2-18");
		Date trimed = DateUtils.trimDateToMonthEnd(date);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String formated = format.format(trimed);
		log.debug("Trimed Date: " + formated);
		Assert.assertEquals("2000-02-29", formated);
	}
}

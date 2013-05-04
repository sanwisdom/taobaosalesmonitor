package com.sanwisdom.taobao.monitor.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.util.DefaultPrettyPrinter;
import org.junit.Test;

import com.sanwisdom.common.utils.DateUtils;
import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.salesconsole.GlobalConstants;

public class ReportServiceTest {

	static Logger log = Logger.getLogger(ReportServiceTest.class);
	
	@Test
	public void test() throws IOException, ParseException {
		Date from = new SimpleDateFormat(GlobalConstants.DATE_FORMAT_YYYY_MM_DD).parse("2012-11-01");
		Date to = DateUtils.trimDateToMonthEnd(from);
		Shop shop = ReportService.readMonthlyTransactionsForShop(Long.valueOf(1), from, to);
		String serialized = new ObjectMapper().writer(new DefaultPrettyPrinter()).writeValueAsString(shop);
		log.info(serialized);
		System.out.print("Complete");
	}
	
	@Test
	public void testPersistReportFromJson() throws ParseException {
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(new File(getTestFilePath()), Shop.class);
		} catch (IOException e) {
			log.error(e);
		}
		Date from = new SimpleDateFormat(GlobalConstants.DATE_FORMAT_YYYY_MM_DD).parse("2012-11-01");
		Date to = DateUtils.trimDateToMonthEnd(from);
		ReportService.saveMonthlyReportForShop(shop, to);
	}
	
	@Test
	public void testPersistReport() throws IOException, ParseException {
		Date from = new SimpleDateFormat(GlobalConstants.DATE_FORMAT_YYYY_MM_DD).parse("2012-11-01");
		Date to = DateUtils.trimDateToMonthEnd(from);
		Shop shop = ReportService.readMonthlyTransactionsForShop(Long.valueOf(2), from, to);
		String serialized = new ObjectMapper().writer(new DefaultPrettyPrinter()).writeValueAsString(shop);
		log.info(serialized);
		log.info("Persisting...");
		ReportService.saveMonthlyReportForShop(shop, to);
		log.info("Complete");
	}
	
	private String getTestFilePath() {
		String path = System.getProperty("user.dir") + "\\test\\" + this.getClass().getPackage().getName().replace(".", "\\") + "\\report_one_shop_persist_test.json";
		log.debug("Path: " + path);
		return path;
	}
	
	@Test
	public void testGenerateReportToExcel() throws ParseException {
//		Date from = new SimpleDateFormat(GlobalConstants.DATE_FORMAT_YYYY_MM_DD).parse("2012-12-01");
		Date from = new SimpleDateFormat(GlobalConstants.DATE_FORMAT_YYYY_MM_DD).parse("2012-12-01");
		Date to = new SimpleDateFormat(GlobalConstants.DATE_FORMAT_YYYY_MM_DD).parse("2012-12-23");
//		Date to = DateUtils.trimDateToMonthEnd(from);
		List<Long> shopIds = initWeddings();
		ReportService.generateReportToExcel(shopIds, from, to, 30);
	}

	protected List<Long> initTools() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(Long.valueOf(1));
		ids.add(Long.valueOf(2));
		ids.add(Long.valueOf(3));
		ids.add(Long.valueOf(4));
		ids.add(Long.valueOf(5));
		return ids;
	}
	
	protected List<Long> initWeddings() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(Long.valueOf(6));
		ids.add(Long.valueOf(7));
		ids.add(Long.valueOf(8));
		ids.add(Long.valueOf(9));
		ids.add(Long.valueOf(10));
		ids.add(Long.valueOf(11));
		return ids;
	}
	
	protected List<Long> initJiafang() {
		List<Long> ids = new ArrayList<Long>();
		for(int i = 17; i <= 26; i++) {
			ids.add(Long.valueOf(i));
		}
		return ids;
	}
}

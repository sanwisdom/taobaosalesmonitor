package com.sanwisdom.taobao.monitor.persistence.impl;

import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.util.DefaultPrettyPrinter;
import org.junit.Test;

import com.sanwisdom.common.utils.DateUtils;
import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.salesconsole.GlobalConstants;

public class JdbcReportDaoTest {

	static Logger log = Logger.getLogger(JdbcReportDao.class);
	
	@Test
	public void testReadReport() throws IOException {
		JdbcReportDao dao = new JdbcReportDao();
		Shop shop = dao.readReport(Long.valueOf(1), 50, DateUtils.createDate("2012-11-30", GlobalConstants.DATE_FORMAT_YYYY_MM_DD));
		Assert.assertNotNull(shop);
		Assert.assertEquals(50, shop.getProducts().size());
		log.info(new ObjectMapper().writer(new DefaultPrettyPrinter()).writeValueAsString(shop));
	}
	
	@Test
	public void testReadReports() throws IOException {
		JdbcReportDao dao = new JdbcReportDao();
		List<Shop> shops = dao.readReports(5, DateUtils.createDate("2012-11-30", GlobalConstants.DATE_FORMAT_YYYY_MM_DD));
		Assert.assertNotNull(shops);
		log.info(new ObjectMapper().writer(new DefaultPrettyPrinter()).writeValueAsString(shops));
		for (Shop shop : shops) {
			if (shop.getProducts().size() > 0) {
				Assert.assertEquals(5, shop.getProducts().size());
			}
		}
	}
}

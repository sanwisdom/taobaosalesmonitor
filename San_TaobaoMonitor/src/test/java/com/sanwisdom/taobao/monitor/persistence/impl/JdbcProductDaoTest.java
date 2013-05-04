package com.sanwisdom.taobao.monitor.persistence.impl;

import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import com.sanwisdom.taobao.monitor.businessobject.Deal;
import com.sanwisdom.taobao.monitor.businessobject.Product;

public class JdbcProductDaoTest {
	
	static Logger log = Logger.getLogger(JdbcProductDaoTest.class);
	
	JdbcProductDao jdbcProductDao = new JdbcProductDao();
	
	@After
	public void tearDown() throws SQLException {
		jdbcProductDao.getConn().close();
	}


	@Test
	public void testCreateListOfShop() {
		fail("Not yet implemented");
	}

	
	
	@Test
	public void testQueryProductByShopId_notExist() {
		List<Product> products = jdbcProductDao.queryProducts(Long.valueOf(0));
		Assert.assertEquals(0, products.size());
	}
	
	@Test
	public void testQueryProductByShopId_exist() {
		List<Product> products = jdbcProductDao.queryProducts(Long.valueOf(20));
		Assert.assertTrue(products.size() > 0);
	}
	
	@Test
	public void testQueryDealByProductId_notExist() {
		List<Deal> deals = jdbcProductDao.queryDealsByProductId(Long.valueOf(0));
		Assert.assertEquals(0, deals.size());
	}
	
	@Test
	public void testQueryDealByProductId_exist() {
		List<Deal> deals = jdbcProductDao.queryDealsByProductId(Long.valueOf(2));
		Assert.assertTrue(deals.size() > 0);
	}

	@Test
	public void testFindByPrimaryKeys() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

}

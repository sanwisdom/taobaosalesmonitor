package com.sanwisdom.common.utils.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.sanwisdom.common.utils.jdbc.JdbcUtils.DBMS;
import com.sanwisdom.common.utils.jdbc.JdbcUtils.DbProperties;

public class JdbcUtilsTest {
	
	static Logger log = Logger.getLogger(JdbcUtilsTest.class);
	

	@Test
	public void testConnection() throws SQLException {
		DbProperties dbProps = new DbProperties(DBMS.MYSQL, "localhost", "3306");
		Connection conn = JdbcUtils.getConnectionByDriverManager("root", "123456", dbProps);
		Assert.assertNotNull(conn);
		conn.close();
	}
	
	@Test
	public void testPoolableConnection() throws SQLException {
		long t = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			log.debug("connecting: " + i);
			Connection conn = JdbcUtils.getConnectionByPoolableDataSource("root", "123456", "jdbc:mysql://localhost:3306/taobaomonitor_schema");
			Assert.assertNotNull(conn);
			conn.close();
		}
		log.debug("time: " + (System.currentTimeMillis() - t));
	}
}

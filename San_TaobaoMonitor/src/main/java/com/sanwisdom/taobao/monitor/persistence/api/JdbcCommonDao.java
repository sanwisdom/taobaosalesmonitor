package com.sanwisdom.taobao.monitor.persistence.api;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.sanwisdom.common.utils.jdbc.JdbcUtils;

public class JdbcCommonDao {
	
	static Logger log = Logger.getLogger(JdbcCommonDao.class);
	
	private Connection conn = null;
	
	public Connection getConn() {
		try {
			if (null == conn || conn.isClosed()) {
					conn = JdbcUtils.getConnectionByPoolableDataSource("root", "123456", "jdbc:mysql://localhost:3306/taobaomonitor_schema");
			}
			if (conn.getAutoCommit()) {
				conn.setAutoCommit(false);
			}
		} catch (SQLException e) {
			log.error(e);
			throw new RuntimeException(e);
		} catch (Exception e) {
			log.error(e);
			throw new RuntimeException(e);
		}
		return conn;
	}
}

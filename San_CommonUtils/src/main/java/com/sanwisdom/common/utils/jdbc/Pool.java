package com.sanwisdom.common.utils.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.cpdsadapter.DriverAdapterCPDS;
import org.apache.commons.dbcp.datasources.SharedPoolDataSource;

class Pool {

	private static volatile DataSource ds;

	private Pool() {
		
	}

	public static Connection getConnection(String userName, String password, String url) throws SQLException {
		if (null == ds) {
			synchronized (Pool.class) {
				if (null == ds) {
					DriverAdapterCPDS cpds = new DriverAdapterCPDS();
					cpds.setUrl(url);
					cpds.setUser(userName);
					cpds.setPassword(password);

					SharedPoolDataSource tds = new SharedPoolDataSource();
					tds.setConnectionPoolDataSource(cpds);
					tds.setMaxActive(10);
					tds.setMaxWait(5000);

					ds = tds;
				}
			}
		}
		return ds.getConnection();
	}

}

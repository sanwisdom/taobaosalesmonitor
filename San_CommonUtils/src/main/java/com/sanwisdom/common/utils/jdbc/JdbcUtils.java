package com.sanwisdom.common.utils.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class JdbcUtils {
	
	static Logger log = Logger.getLogger(JdbcUtils.class);
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public enum DBMS {
		MYSQL,
		DERBY
	}
	
	public static class DbProperties {
		
		public DbProperties(DBMS dbms, String serverName, String portNumber) {
			this.dbms = dbms;
			this.serverName = serverName;
			this.portNumber = portNumber;
		}
		
		public DbProperties(DBMS dbms, String dbName) {
			this.dbms = dbms;
			this.dbName = dbName;
		}
		
		public DbProperties() {
			
		}
		
		
		private DBMS dbms;
		
		private String serverName;
		
		private String portNumber;
		
		private String dbName;

		public DBMS getDbms() {
			return dbms;
		}

		public void setDbms(DBMS dbms) {
			this.dbms = dbms;
		}

		public String getServerName() {
			return serverName;
		}

		public void setServerName(String serverName) {
			this.serverName = serverName;
		}

		public String getPortNumber() {
			return portNumber;
		}

		public void setPortNumber(String portNumber) {
			this.portNumber = portNumber;
		}

		public String getDbName() {
			return dbName;
		}

		public void setDbName(String dbName) {
			this.dbName = dbName;
		}
		
		
	}

	public static Connection getConnectionByDriverManager(String userName, String password, DbProperties dbProps) throws SQLException {
	    Connection conn = null;
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", userName);
	    connectionProps.put("password", password);

	    if (DBMS.MYSQL.equals(dbProps.getDbms())) {
	        conn = DriverManager.getConnection(
	                   "jdbc:" + dbProps.getDbms() + "://" +
	                		   dbProps.getServerName() +
	                   ":" + dbProps.getPortNumber() + "/",
	                   connectionProps);
	    } else if (DBMS.DERBY.equals(dbProps.getDbms())) {
	        conn = DriverManager.getConnection(
	                   "jdbc:" + dbProps.getDbms() + ":" +
	                		   dbProps.getDbName() +
	                   ";create=true",
	                   connectionProps);
	    }
	    log.debug("Connected to database");
	    return conn;
	}
	
	public static Connection getConnectionByPoolableDataSource(String userName, String password, String url) throws SQLException {
		return Pool.getConnection(userName, password, url);
	}
}

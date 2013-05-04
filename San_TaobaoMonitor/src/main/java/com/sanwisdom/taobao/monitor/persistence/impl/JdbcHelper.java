package com.sanwisdom.taobao.monitor.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sanwisdom.taobao.monitor.salesconsole.GlobalConstants;

public class JdbcHelper {

	static Logger log = Logger.getLogger(JdbcHelper.class);
	
	public static String toSqlDateString(Date date) {
		return new SimpleDateFormat(GlobalConstants.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS).format(date);
	}
	

	public static void finallyCloseStmtAndConn(PreparedStatement stmt, Connection conn) {
		closeStmt(stmt);
		closeConn(conn);
	}


	public static void closeConn(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			log.error(e);
		}
	}


	public static void closeStmt(PreparedStatement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException ex) {
			log.error(ex);
		}
	}

}

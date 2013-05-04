package com.sanwisdom.taobao.monitor.persistence.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.sanwisdom.taobao.monitor.businessobject.Category;
import com.sanwisdom.taobao.monitor.businessobject.Product;
import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.persistence.api.JdbcCommonDao;

public class JdbcReportDao extends JdbcCommonDao {
	
	static Logger log = Logger.getLogger(JdbcReportDao.class);
	
	
	private JdbcShopDao shopDao = null;
	
	public JdbcShopDao getShopDao() {
		if (null == shopDao) {
			shopDao = new JdbcShopDao();
		}
		return shopDao;
	}
	
	public enum ReportType {
		MONTHLY
	}

	public void generateReport(Shop shop, Date dateTo) throws IOException {
		Statement stmt = null;
		try {
//			this.getConn().setAutoCommit(false); errorlog.log.2013-05-04-02-29
			List<Product> products = shop.getProducts();
			stmt = createReportDetails(this.getConn().createStatement(), shop.getId(), products, dateTo);
			this.getConn().commit();
		} catch (SQLException e) {
			log.error(e);
			if (this.getConn() != null) {
	            try {
	            	log.info(String.format("Transaction is being rolled back while creating shop's monthly report: %s", shop.getShopName()));
	                this.getConn().rollback();
	            } catch(SQLException ex) {
	            	log.error(ex);
	            }
	        }
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				this.getConn().setAutoCommit(true);
				this.getConn().close();
			} catch (SQLException e) {
				throw new IOException(e);
			}
		}
	}
	
	private Statement createReportDetails(Statement stmt, Long shopId,
			List<Product> products, Date dateTo) throws SQLException {
		
		for (int i = 0; i < products.size(); i++) {
			Product p = products.get(i);
			stmt.addBatch(this.getInsertReportSql(shopId, p, ReportType.MONTHLY.name(), dateTo, i + 1));
		}
		int[] updateCounts = stmt.executeBatch();
		log.debug(updateCounts);
		return stmt;
	}

	private static String INSERT_REPORT_TEMPLATE = "INSERT INTO taobaomonitor_schema.t_sales_report" +
			"(SHOP_ID, PRODUCT_ID, REPORT_TYPE, DATE_TO, RANK, TOTAL_SALE_AMOUNT, TOTAL_REVENUE) " +
			"VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');";

	
	private String getInsertReportSql(long shopId, Product p, String category, Date dateTo, int rank) {
		String sql = String.format(INSERT_REPORT_TEMPLATE, 
				String.valueOf(shopId), 
				String.valueOf(p.getId()),
				category, 
				JdbcHelper.toSqlDateString(dateTo),
				String.valueOf(rank),
				String.valueOf(p.getSummary().getMonthlySalesAmount()),
				String.valueOf(p.getSummary().getSalesTotal()));
		log.debug("SQL: " + sql);
		return sql;
	}
	
	public Shop readReport(Shop shop, int rank, Date dateTo) throws IOException {
		if (null == shop.getId()) {
			throw new IllegalArgumentException(String.format("Invalid Shop Id: (%s)", String.valueOf(shop.getId())));
		}
		return this.readReport(shop.getId(), rank, dateTo);
	}
	
	private static String QUERY_REPORT_TEMPLATE = "SELECT p.ID, p.TAOBAO_ID, p.TITLE, p.THUMBNAIL, p.UNIT_PRICE, p.SALES_AMOUNT, p.RATING, p.LINK, r.RANK, r.TOTAL_SALE_AMOUNT, r.TOTAL_REVENUE " +
			"FROM taobaomonitor_schema.t_sales_report r, taobaomonitor_schema.t_sales_product p " +
			"WHERE r.SHOP_ID = %s AND p.ID = r.PRODUCT_ID AND r.RANK <= %s AND r.DATE_TO = '%s' " +
			"ORDER BY r.RANK ASC;";
	
	public Shop readReport(Long shopId, int rank, Date dateTo) throws IOException {
		Shop shop = null;
		JdbcShopDao shopDao = new JdbcShopDao();
		shop = shopDao.queryShop(shopId);
		if (null == shop || null == shop.getId()) {
			throw new IllegalArgumentException(String.format("Invalid Shop Id: (%s)", String.valueOf(shopId)));
		}
		PreparedStatement stmt = null;
		try {
			String query = QUERY_REPORT_TEMPLATE;
			String sql = String.format(query,
					String.valueOf(shopId),
					String.valueOf(rank),
					JdbcHelper.toSqlDateString(dateTo));
			log.debug(sql);
			stmt = this.getConn().prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			List<Product> products = readProductsFromRs(rs);
			rs.close();
			shop.getProducts().addAll(products);
		} catch (SQLException e) {
			log.error(e);
		} finally {
			JdbcHelper.finallyCloseStmtAndConn(stmt, this.getConn());
		}
		return shop;
	}	
	
	protected List<Product> readProductsFromRs(ResultSet rs) throws SQLException {
		List<Product> products = new ArrayList<Product>();
		while (rs.next()) {
			Product p = readProductFromRsRecord(rs);
			if (null != p) {
				products.add(p);
			}
		}
		return products;
	}

	protected Product readProductFromRsRecord(ResultSet rs) throws SQLException {
		Long id = rs.getLong("ID");
		Long taobaoId = rs.getLong("TAOBAO_ID");
		String title = rs.getString("TITLE");
		String thumbnail = rs.getString("THUMBNAIL");
		BigDecimal unitPrice = rs.getBigDecimal("UNIT_PRICE");
		Long salesAmount = rs.getLong("SALES_AMOUNT");
		double rating = rs.getDouble("RATING");
		String link = rs.getString("LINK");
		int rank = rs.getInt("RANK");
		Long totalSaleAmount = rs.getLong("TOTAL_SALE_AMOUNT");
		BigDecimal totalRevenue = rs.getBigDecimal("TOTAL_REVENUE");
		log.debug("RANK: " + String.valueOf(rank));
		log.debug("TOTAL_SALE_AMOUNT: " + String.valueOf(totalSaleAmount));
		log.debug("TOTAL_REVENUE: " + String.valueOf(totalRevenue));
		log.debug("ID: " + id);
		log.debug("TAOBAO_ID: " + String.valueOf(taobaoId));
		log.debug("TITLE: " + title);
		log.debug("THUMBNAIL: " + thumbnail);
		log.debug("UNIT_PRICE: " + String.valueOf(unitPrice));
		log.debug("SALES_AMOUNT: " + String.valueOf(salesAmount));
		log.debug("RATING: " + rating);
		log.debug("LINK: " + link);
		Product p = null;
		if (null != id && null != taobaoId) {
			p = new Product(id, taobaoId, title, thumbnail, unitPrice, salesAmount, rating, link);
			p.getSummary().setMonthlySalesAmount(null == totalSaleAmount ? 0 : totalSaleAmount);
			p.getSummary().setSalesTotal(totalRevenue);
		}
		return p;
	}

	public List<Shop> readReports(int rank, Date dateTo) throws IOException {
		List<Shop> shops = new ArrayList<Shop>();
		List<Long> shopIds = this.getShopDao().queryShopIds();
		for (Long shopId : shopIds) {
			shops.add(this.readReport(shopId, rank, dateTo));
		}
		return shops;
	}
	
	public List<Shop> readReports(int rank, Category category, Date dateTo) throws IOException {
		List<Shop> shops = new ArrayList<Shop>();
		List<Long> shopIds = this.getShopDao().queryShopIdsByCategory(category.getName(), true);
		for (Long shopId : shopIds) {
			shops.add(this.readReport(shopId, rank, dateTo));
		}
		return shops;
	}
}

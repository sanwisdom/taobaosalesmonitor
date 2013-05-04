package com.sanwisdom.taobao.monitor.persistence.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.sanwisdom.taobao.monitor.businessobject.Deal;
import com.sanwisdom.taobao.monitor.businessobject.Product;
import com.sanwisdom.taobao.monitor.businessobject.ProductSummary;
import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.persistence.api.JdbcCommonDao;
import com.sanwisdom.taobao.monitor.salesconsole.GlobalConstants;

public class JdbcProductDao extends JdbcCommonDao {
	
	static Logger log = Logger.getLogger(JdbcProductDao.class);
	
	public Statement createProducts(Statement stmt, Long shopId,
			List<Product> products) throws SQLException {
		for (Product p : products) {
			Long productId = this.insertProductKeepConnected(shopId, p, this.getConn());
			for (Deal d : p.getDeals()) {
				stmt.addBatch(this.getInsertDealSql(productId, d));
			}
			int[] updateCounts = stmt.executeBatch();
			log.debug(updateCounts);
		}
		return stmt;
	}

	
	
	public void updateProducts(Long shopId, List<Product> products) throws IOException {
		 Statement stmt = null;
		 try {
//			 	this.getConn().setAutoCommit(false); BUGFIX errorlog.log.2013-05-04-02-29
				for (Product p : products) {
					Long productId = this.queryProductIdByTaobaoId(p.getSummary().getProductId());
					if (null == productId) {
						try {
//							this.getConn().setAutoCommit(false); BUGFIX errorlog.log.2013-05-03-00-58
							productId = this.insertProductKeepConnected(shopId, p, this.getConn());
							this.getConn().commit(); // FIXME
						} catch (SQLException e) {
							log.error(String.format("Shop ID: %s, Product ID: %s, Link: %s", 
									(null == shopId ? null : String.valueOf(shopId)), 
									(null == productId ? null : String.valueOf(productId)),
									p.getSummary().getLink()), e);
							if (this.getConn() != null) {
					            try {
					            	log.info(String.format("Transaction is being rolled back while saving product: %s", p.getSummary().getTitle()));
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
//								this.getConn().setAutoCommit(true); BUGFIX
								this.getConn().close();
							} catch (SQLException e) {
								throw new IOException(e);
							}
						}
					}
//					this.getConn().setAutoCommit(false);  BUGFIX errorlog.log.2013-05-03-00-58
					stmt = this.getConn().createStatement();
					for (Deal d : p.getDeals()) {
						stmt.addBatch(this.getInsertDealSql(productId, d));
					}
					int[] updateCounts = stmt.executeBatch();
					if (null != stmt) {
						stmt.close();
					}
					log.debug(updateCounts);
				}
				this.getConn().commit(); // FIXME
			} catch (SQLException e) {
				log.error(e);
				if (this.getConn() != null) {
		            try {
		            	log.info(String.format("Transaction is being rolled back while saving shop: %s products", shopId));
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
//					this.getConn().setAutoCommit(true); BUGFIX errorlog.log.2013-05-03-00-58
					this.getConn().close();
				} catch (SQLException e) {
					throw new IOException(e);
				}
			}	
	}

	
	private static String INSERT_PRODUCT_TEMPLATE = "INSERT INTO T_SALES_PRODUCT (TAOBAO_ID, TITLE, THUMBNAIL, UNIT_PRICE, SALES_AMOUNT, RATING, LINK, SHOP_ID) " +
			"values ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')";
	
	protected Long insertProductKeepConnected(Long shopId, Product p, Connection conn) throws SQLException {
		Long id = null;
		PreparedStatement stmt = null;
		String sql = null;
		try {
			ProductSummary s = p.getSummary();
			sql = String.format(INSERT_PRODUCT_TEMPLATE, 
					String.valueOf(s.getProductId()), 
					String.valueOf(s.getTitle()), 
					String.valueOf(s.getThumbnail()), 
					String.valueOf(s.getPrice()), 
					String.valueOf(s.getSalesTotalAmount()), 
					String.valueOf(s.getRating()),
					String.valueOf(s.getLink()),
					String.valueOf(shopId));
			log.debug(sql);
			stmt = this.getConn().prepareStatement(
					sql, 
					Statement.RETURN_GENERATED_KEYS);
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();	
			resultSet.next();
			id = resultSet.getLong(1);
			log.debug("id: " + id);
		} catch (SQLException e) {
			log.error("SQL error statement: " + sql, e);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return id;
	}
	
	protected Long insertProductKeepConnected(Shop shop, Product p, Connection conn) throws SQLException {
		return this.insertProductKeepConnected(shop.getId(), p, conn);
	}
	
	private static String INSERT_DEAL_TEMPLATE = "INSERT INTO T_SALES_DEAL (UNIT_PRICE, SALES_AMOUNT, TOTAL_PRICE, DEAL_DATE, PRODUCT_ID) " +
			"values ('%s', '%s', '%s', '%s', '%s')";
	
	protected Long insertDealKeepConnected(Long productId, Deal d, Connection conn) throws SQLException {
		Long id = null;
		PreparedStatement stmt = null;
		try {
			String sql = getInsertDealSql(productId, d);
			log.debug(sql);
			stmt = this.getConn().prepareStatement(
					sql, 
					Statement.RETURN_GENERATED_KEYS);
			stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();	
			resultSet.next();
			id = resultSet.getLong(1);
			log.debug("id: " + id);
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		return id;
	}


	private String getInsertDealSql(Long productId, Deal d) {
		String sql = String.format(INSERT_DEAL_TEMPLATE, 
				String.valueOf(d.getUnitPrice()), 
				String.valueOf(d.getSalesAmount()), 
				String.valueOf(d.getTotalPrice()), 
				new SimpleDateFormat(GlobalConstants.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS).format(d.getDate()),
				String.valueOf(productId));
		return sql;
	}
	
	
	private static final String QUERY_DEAL_BY_PRODUCT_ID_TEMPLATE = "SELECT ID, UNIT_PRICE, SALES_AMOUNT, TOTAL_PRICE, DEAL_DATE FROM T_SALES_DEAL WHERE PRODUCT_ID = '%s' ORDER BY DEAL_DATE DESC";

	public List<Deal> queryDealsByProductId(Long productId) {
		List<Deal> deals = new ArrayList<Deal>();
		PreparedStatement stmt = null;
		try {
			String query = QUERY_DEAL_BY_PRODUCT_ID_TEMPLATE;
			String sql = String.format(query,
					String.valueOf(productId));
			log.debug(sql);
			stmt = this.getConn().prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			readDealsFromResultSet(deals, rs);
			rs.close();
		} catch (SQLException e) {
			log.error(e);
		} finally {
			JdbcHelper.finallyCloseStmtAndConn(stmt, this.getConn());
		}
		return deals;
	}



	private void readDealsFromResultSet(List<Deal> deals, ResultSet rs)
			throws SQLException {
		while (rs.next()) {
			Long id = rs.getLong("ID");
			BigDecimal unitPrice = rs.getBigDecimal("UNIT_PRICE");
			Long salesAmount = rs.getLong("SALES_AMOUNT");
			BigDecimal totalPrice = rs.getBigDecimal("TOTAL_PRICE");
			Date dealDate = new Date(rs.getTimestamp("DEAL_DATE").getTime());
			log.debug("ID: " + id);
			log.debug("UNIT_PRICE: " + String.valueOf(unitPrice));
			log.debug("SALES_AMOUNT: " + String.valueOf(salesAmount));
			log.debug("TOTAL_PRICE: " + String.valueOf(totalPrice));
			log.debug("DEAL_DATE: " + new SimpleDateFormat(GlobalConstants.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS).format(dealDate));
			if (null != id) {
				Deal d = new Deal(id, unitPrice, salesAmount.intValue(), totalPrice, dealDate);
				deals.add(d);
			}
		}
	}
	
	private static final String QUERY_DEAL_BY_PRODUCT_ID_BETWEEN_DATE_TEMPLATE = "SELECT ID, UNIT_PRICE, SALES_AMOUNT, TOTAL_PRICE, DEAL_DATE FROM T_SALES_DEAL " +
			"WHERE PRODUCT_ID = '%s' AND DEAL_DATE BETWEEN '%s' AND '%s' ORDER BY DEAL_DATE DESC";

	protected List<Deal> queryDealsBetweenDate(Long productId, Date from, Date to) {
		List<Deal> deals = new ArrayList<Deal>();
		PreparedStatement stmt = null;
		try {
			String query = QUERY_DEAL_BY_PRODUCT_ID_BETWEEN_DATE_TEMPLATE;
			String sql = String.format(query,
					String.valueOf(productId),  
					new SimpleDateFormat(GlobalConstants.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS).format(from), 
					new SimpleDateFormat(GlobalConstants.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS).format(to));
			log.debug(sql);
			stmt = this.getConn().prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			readDealsFromResultSet(deals, rs);
			rs.close();
		} catch (SQLException e) {
			log.error(e);
		} finally {
			JdbcHelper.finallyCloseStmtAndConn(stmt, this.getConn());
		}
		return deals;
	}


	private static final String QUERY_PRODUCT_BY_SHOP_ID_TEMPLATE = "SELECT ID, TAOBAO_ID, TITLE, THUMBNAIL, UNIT_PRICE, SALES_AMOUNT, RATING, LINK FROM T_SALES_PRODUCT WHERE SHOP_ID = '%s'";
	
	protected List<Product> queryProducts(Long shopId) {
		List<Product> products = new ArrayList<Product>();
		PreparedStatement stmt = null;
		try {
			String sql = String.format(QUERY_PRODUCT_BY_SHOP_ID_TEMPLATE,
					String.valueOf(shopId));
			log.debug(sql);
			stmt = this.getConn().prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			products = readProductsFromRs(rs);
			rs.close();
		} catch (SQLException e) {
			log.error(e);
		} finally {
			JdbcHelper.finallyCloseStmtAndConn(stmt, this.getConn());
		}
		return products;
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
		}
		return p;
	}
	
	
	private static final String QUERY_PRODUCT_ID_BY_SHOP_ID_TEMPLATE = "SELECT ID FROM T_SALES_PRODUCT WHERE SHOP_ID = '%s'";
	
	protected List<Long> queryProductIds(Long shopId) {
		List<Long> productIds = new ArrayList<Long>();
		PreparedStatement stmt = null;
		try {
			String sql = String.format(QUERY_PRODUCT_ID_BY_SHOP_ID_TEMPLATE,
					String.valueOf(shopId));
			log.debug(sql);
			stmt = this.getConn().prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("ID");
				log.debug("ID: " + id);
				productIds.add(id);
			}
			rs.close();
		} catch (SQLException e) {
			log.error(e);
		} finally {
			JdbcHelper.finallyCloseStmtAndConn(stmt, this.getConn());
		}
		return productIds;
	}
	
	private static final String QUERY_PRODUCT_ID_BY_TAOBAO_ID_TEMPLATE = "SELECT ID FROM T_SALES_PRODUCT WHERE TAOBAO_ID = '%s'";
	
	protected Long queryProductIdByTaobaoId(Long taobaoId) {
		Long productId = null;
		PreparedStatement stmt = null;
		try {
			String sql = String.format(QUERY_PRODUCT_ID_BY_TAOBAO_ID_TEMPLATE,
					String.valueOf(taobaoId));
			log.debug(sql);
			stmt = this.getConn().prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("ID");
				log.debug("ID: " + id);
				return (productId = id);
			}
			rs.close();
		} catch (SQLException e) {
			log.error(e);
		} finally {
			JdbcHelper.finallyCloseStmtAndConn(stmt, this.getConn());
		}
		return productId;
	}
	
	
	
	
	private static final String DELETE_PRODUCT_BY_SHOP_ID_TEMPLATE = "DELETE FROM T_SALES_PRODUCT WHERE SHOP_ID = '%s'";
	
	protected void deleteProductByShopId(Long shopId) {
		try {
			deleteProductByShopIdKeepConn(shopId, this.getConn());
		} catch (SQLException e) {
			log.error(e);
		} finally {
			JdbcHelper.closeConn(this.getConn());
		}
	}


	public PreparedStatement deleteProductByShopIdKeepConn(Long shopId, Connection connection)
			throws SQLException {
		PreparedStatement stmt = null;
		String sql = String.format(DELETE_PRODUCT_BY_SHOP_ID_TEMPLATE,
				String.valueOf(shopId));
		log.debug(sql);
		stmt = connection.prepareStatement(sql);
		stmt.executeUpdate();
	
		JdbcHelper.closeStmt(stmt);
		
		return stmt;
	}
	
	private static final String DELETE_DEAL_BY_PRODUCT_ID_TEMPLATE = "DELETE FROM T_SALES_DEAL WHERE PRODUCT_ID = '%s'";
	
	public void deleteDealByProductId(Long productId) {
		try {
			deleteDealByProductIdKeepConn(productId, this.getConn());
		} catch (SQLException e) {
			log.error(e);
		} finally {
			JdbcHelper.closeConn(this.getConn());
		}
	}


	public PreparedStatement deleteDealByProductIdKeepConn(Long productId, Connection connection)
			throws SQLException {
		PreparedStatement stmt = null;
		String sql = String.format(DELETE_DEAL_BY_PRODUCT_ID_TEMPLATE,
				String.valueOf(productId));
		log.debug(sql);
		stmt = connection.prepareStatement(sql);
		stmt.executeUpdate();
		JdbcHelper.closeStmt(stmt);
		return stmt;
	}
	

	
	

}

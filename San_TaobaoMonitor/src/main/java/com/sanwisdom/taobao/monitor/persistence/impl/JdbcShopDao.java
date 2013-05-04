package com.sanwisdom.taobao.monitor.persistence.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.util.DefaultPrettyPrinter;

import com.sanwisdom.taobao.monitor.businessobject.Deal;
import com.sanwisdom.taobao.monitor.businessobject.Product;
import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.persistence.api.JdbcCommonDao;
import com.sanwisdom.taobao.monitor.persistence.api.ShopDao;
import com.sanwisdom.taobao.monitor.salesconsole.GlobalConstants;

public class JdbcShopDao extends JdbcCommonDao implements ShopDao{

	static Logger log = Logger.getLogger(JdbcProductDao.class);
		
	private JdbcProductDao productDao = null;
	
	public JdbcProductDao getProductDao() {
		if (null == productDao) {
			productDao = new JdbcProductDao();
		}
		return productDao;
	}
	

	public void create(Shop info) throws IOException {
		Statement stmt = null;
		try {
//			this.getConn().setAutoCommit(false); BUGFIX errorlog.log.2013-05-04-02-29
			Long shopId = this.insertShopKeepConnected(info, this.getConn());
			info.setId(shopId);
			List<Product> products = info.getProducts();
			stmt = this.getProductDao().createProducts(stmt, shopId, products);
			this.getConn().commit();
		} catch (SQLException e) {
			log.error(e);
			if (this.getConn() != null) {
	            try {
	            	log.info(String.format("Transaction is being rolled back while saving shop: %s", info.getShopName()));
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
	

	public void create(List<Shop> infos) throws IOException {
		for (Shop shop : infos) {
			this.create(shop);
		}
	}
	
	
	private static String INSERT_SHOP_TEMPLATE = "INSERT INTO T_SALES_SHOP (NAME, URL) values ('%s', '%s')";
	
	protected Long insertShopKeepConnected(Shop shop, Connection conn) throws SQLException {
		Long id = null;
		PreparedStatement stmt = null;
		try {
			String sql = String.format(INSERT_SHOP_TEMPLATE, shop.getShopName(), shop.getUrl());
			log.debug(sql);
			stmt = conn.prepareStatement(
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


	public void createProductsOfShop(Shop info) throws IOException {
		Statement stmt = null;
		try {
			this.getConn().setAutoCommit(false);
			List<Product> products = info.getProducts();
			stmt = this.getProductDao().createProducts(this.getConn().createStatement(), info.getId(), products);
			this.getConn().commit();
		} catch (SQLException e) {
			log.error(e);
			if (this.getConn() != null) {
	            try {
	            	log.info(String.format("Transaction is being rolled back while saving shop's products: %s", info.getShopName()));
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


	public void updateProductsOfShop(Shop info) throws IOException {
		 Shop find = this.queryShopByUrl(info.getUrl());
		 if (null == find) {
			 Statement stmt = null;
				try {
					this.getConn().setAutoCommit(false);
					Long shopId = this.insertShopKeepConnected(info, this.getConn());
					info.setId(shopId);
					this.getConn().commit(); // FIXME
				} catch (SQLException e) {
					log.error(e);
					if (this.getConn() != null) {
			            try {
			            	log.info(String.format("Transaction is being rolled back while saving shop: %s", info.getShopName()));
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
		 List<Product> products = info.getProducts();
		 this.getProductDao().updateProducts(info.getId(), products);
	}
	

	
	public void create(Shop shop, String category) throws IOException {
		Statement stmt = null;
		try {
			this.getConn().setAutoCommit(false);
			stmt = this.getConn().createStatement();
			int update = stmt.executeUpdate(this.getInsertShopSql(shop, category));
			log.debug(update);
		} catch (SQLException e) {
			log.error(e);
			if (this.getConn() != null) {
	            try {
	            	log.info(String.format("Transaction is being rolled back while saving shop: %s", new ObjectMapper().writer(new DefaultPrettyPrinter()).writeValueAsString(shop)));
	                this.getConn().rollback();
	            } catch(SQLException ex) {
	            	log.error(ex);
	            } catch (IOException ex) {
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
	
	public void updateCategory(Shop shop, String category) throws IOException {
		Statement stmt = null;
		try {
			this.getConn().setAutoCommit(false);
			stmt = this.getConn().createStatement();
			int update = stmt.executeUpdate(this.getUpdateShopCategorySql(shop, category));
			log.debug(update);
		} catch (SQLException e) {
			log.error(e);
			if (this.getConn() != null) {
	            try {
	            	log.info(String.format("Transaction is being rolled back while saving shop: %s", new ObjectMapper().writer(new DefaultPrettyPrinter()).writeValueAsString(shop)));
	                this.getConn().rollback();
	            } catch(SQLException ex) {
	            	log.error(ex);
	            } catch (IOException ex) {
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
	
	private static String UPDATE_SHOP_CATEGORY_TEMPLATE = "UPDATE taobaomonitor_schema.t_sales_shop " +
			" SET UPDATED_BY = '%s', CATEGORY = '%s' "+
			" WHERE ID = %s";

	
	private String getUpdateShopCategorySql(Shop shop, String category) {
		String sql = String.format(UPDATE_SHOP_CATEGORY_TEMPLATE, 
				shop.getUpdatedBy(),
				category,
				String.valueOf(shop.getId()));
		log.debug("SQL: " + sql);
		return sql;
	}

	public void create(List<Shop> shops, String category) throws IOException {
		Statement stmt = null;
		try {
			this.getConn().setAutoCommit(false);
			stmt = this.getConn().createStatement();
			for (Shop shop : shops) {
				stmt.addBatch(this.getInsertShopSql(shop, category));
			}
			int[] updateCounts = stmt.executeBatch();
			log.debug(updateCounts);
		} catch (SQLException e) {
			log.error(e);
			if (this.getConn() != null) {
	            try {
	            	log.info(String.format("Transaction is being rolled back while saving shop: %s", new ObjectMapper().writer(new DefaultPrettyPrinter()).writeValueAsString(shops)));
	                this.getConn().rollback();
	            } catch(SQLException ex) {
	            	log.error(ex);
	            } catch (IOException ex) {
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
	
	private static String INSERT_SHOP_CATEGORY_TEMPLATE = "INSERT INTO taobaomonitor_schema.t_sales_shop" +
			"(NAME, URL, CREATED_BY, UPDATED_BY, CREATED_AT, CATEGORY) " +
			"VALUES ('%s', '%s', '%s', '%s', '%s', '%s');";

	private String getInsertShopSql(Shop shop, String category) {
		String sql = String.format(INSERT_SHOP_CATEGORY_TEMPLATE, 
				shop.getShopName(), 
				shop.getUrl(), 
				shop.getCreatedBy(), 
				shop.getUpdatedBy(),
				new SimpleDateFormat(GlobalConstants.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS).format(GregorianCalendar.getInstance().getTime()),
				category);
		log.debug("SQL: " + sql);
		return sql;
	}
	


	private static final String QUERY_SHOP_TEMPLATE = "SELECT ID, NAME, URL FROM T_SALES_SHOP WHERE ID = '%s'";


	public Shop queryShop(Long shopId) {
		Shop shop = null;
		PreparedStatement stmt = null;
		try {
			String sql = String.format(QUERY_SHOP_TEMPLATE,
					String.valueOf(shopId));
			log.debug(sql);
			stmt = this.getConn().prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("ID");
				String name = rs.getString("NAME");
				String url = rs.getString("URL");
				log.debug("ID: " + id);
				log.debug("NAME: " + name);
				log.debug("URL: " + url);
				if (null != id) {
					shop = new Shop(id, name, url);
				}
			}
			rs.close();
		} catch (SQLException e) {
			log.error(e);
		} finally {
			JdbcHelper.finallyCloseStmtAndConn(stmt, this.getConn());
		}
		return shop;
	}
	
	public Shop findByPrimaryKey(Long id) throws IOException {
		Shop shop = null;
		assert id != null;
		shop = queryShop(id);
		if (null == shop) {
			return shop;
		}
		List<Product> products = this.getProductDao().queryProducts(id);
		shop.getProducts().addAll(products);
		for (Product product : products) {
			List<Deal> deals = this.getProductDao().queryDealsByProductId(product.getId());
			product.addAll(deals);
		}
		return shop;
	}
	
	public Shop findShopByIdWithDealsWithIn(Long id, Date from, Date to) throws IOException {
		Shop shop = null;
		assert id != null;
		shop = queryShop(id);
		if (null == shop) {
			return shop;
		}
		List<Product> products = this.getProductDao().queryProducts(id);
		shop.getProducts().addAll(products);
		for (Product product : products) {
			List<Deal> deals = this.getProductDao().queryDealsBetweenDate(product.getId(), from, to);
			product.addAll(deals);
		}
		return shop;
	}
	

	private static final String QUERY_SHOP_BY_URL_TEMPLATE = "SELECT ID, NAME, URL FROM T_SALES_SHOP WHERE URL = '%s'";


	public Shop queryShopByUrl(String url) {
		Shop shop = null;
		PreparedStatement stmt = null;
		try {
			String sql = String.format(QUERY_SHOP_BY_URL_TEMPLATE,
					String.valueOf(url));
			log.debug(sql);
			stmt = this.getConn().prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("ID");
				String name = rs.getString("NAME");
//				String url = rs.getString("URL");
				log.debug("ID: " + id);
				log.debug("NAME: " + name);
				log.debug("URL: " + url);
				if (null != id) {
					shop = new Shop(id, name, url);
				}
			}
			rs.close();
		} catch (SQLException e) {
			log.error(e);
		} finally {
			JdbcHelper.finallyCloseStmtAndConn(stmt, this.getConn());
		}
		return shop;
	}

	
	private static final String QUERY_ALL_SHOP_ID_TEMPLATE = "SELECT ID FROM T_SALES_SHOP ORDER BY ID ASC";


	public List<Long> queryShopIds() {
		List<Long> ids = new ArrayList<Long>();
		PreparedStatement stmt = null;
		try {
			String sql = QUERY_ALL_SHOP_ID_TEMPLATE;
			log.debug(sql);
			stmt = this.getConn().prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("ID");
				log.debug("ID: " + id);
				ids.add(id);
			}
			rs.close();
		} catch (SQLException e) {
			log.error(e);
		} finally {
			JdbcHelper.finallyCloseStmtAndConn(stmt, this.getConn());
		}
		return ids;
	}

	private static final String QUERY_SHOP_ID_BY_CATEGORY_TEMPLATE = "SELECT ID FROM T_SALES_SHOP WHERE CATEGORY = '%s' AND ACTIVE = '%s' ORDER BY ID ASC";
	
	public List<Long> queryShopIdsByCategory(String category, boolean isActive) {
		List<Long> ids = new ArrayList<Long>();
		PreparedStatement stmt = null;
		try {
			String sql = String.format(QUERY_SHOP_ID_BY_CATEGORY_TEMPLATE, category, isActive ? "1" : "0");
			log.debug(sql);
			stmt = this.getConn().prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("ID");
				log.debug("ID: " + id);
				ids.add(id);
			}
			rs.close();
		} catch (SQLException e) {
			log.error(e);
		} finally {
			JdbcHelper.finallyCloseStmtAndConn(stmt, this.getConn());
		}
		return ids;
	}

	public List<Shop> findByPrimaryKeys(List<Long> ids) throws IOException {
		List<Shop> shops = new ArrayList<Shop>();
		for (Long id : ids) {
			shops.add(this.findByPrimaryKey(id));
		}
		return shops;
	}


	public List<Shop> findAll() throws IOException {
		List<Long> ids = this.queryShopIds();
		return this.findByPrimaryKeys(ids);
	}
	
	
	


	public void delete(Long id) throws IOException {
		try {
			this.getConn().setAutoCommit(false);
			List<Long> productIds = this.getProductDao().queryProductIds(id);
			for (Long productId : productIds) {
				this.getProductDao().deleteDealByProductIdKeepConn(productId, this.getConn());
			}
			this.getProductDao().deleteProductByShopIdKeepConn(id, this.getConn());
			this.deleteShopByShopIdKeepConn(id, this.getConn());
		} catch (SQLException e) {
			log.error(e);
			if (this.getConn() != null) {
	            try {
	            	log.info(String.format("Transaction is being rolled back while delete shop: %s", String.valueOf(id)));
	                this.getConn().rollback();
	            } catch(SQLException ex) {
	            	log.error(ex);
	            }
	        }
		} finally {
			try {
				this.getConn().setAutoCommit(true);
				this.getConn().close();
			} catch (SQLException e) {
				throw new IOException(e);
			}
		}
		
	}
	
	public void delete(final Shop info) throws IOException {
		this.delete(info.getId());
	}
	
	private static final String DELETE_SHOP_BY_SHOP_ID_TEMPLATE = "DELETE FROM T_SALES_SHOP WHERE ID = '%s'";
	
	protected void deleteShopByShopId(Long shopId) {
		try {
			deleteShopByShopIdKeepConn(shopId, this.getConn());
		} catch (SQLException e) {
			log.error(e);
		} finally {
			JdbcHelper.closeConn(this.getConn());
		}
	}


	private PreparedStatement deleteShopByShopIdKeepConn(Long shopId, Connection connection)
			throws SQLException {
		PreparedStatement stmt = null;
		String sql = String.format(DELETE_SHOP_BY_SHOP_ID_TEMPLATE,
				String.valueOf(shopId));
		log.debug(sql);
		stmt = connection.prepareStatement(sql);
		stmt.executeUpdate();
		JdbcHelper.closeStmt(stmt);
		return stmt;
	}


	private static final String UPDATE_SHOP_TEMPLATE = "UPDATE T_SALES_SHOP set NAME = '%s', URL = '%s' WHERE ID = '%s'";



	public void update(Shop shop) throws IOException {
		PreparedStatement stmt = null;
		try {
			String sql = String.format(UPDATE_SHOP_TEMPLATE, shop.getShopName(), shop.getUrl(), String.valueOf(shop.getId()));
			log.debug(sql);
			stmt = this.getConn().prepareStatement(sql);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new IOException(e);
		} finally {
			JdbcHelper.closeStmt(stmt);
			JdbcHelper.closeConn(this.getConn());
		}
	}


	public void update(List<Shop> infos) throws IOException {
		for (Shop shop : infos) {
			this.update(shop);
		}
	}

}

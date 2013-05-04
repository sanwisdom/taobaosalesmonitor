package com.sanwisdom.taobao.monitor.persistence.impl;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.util.DefaultPrettyPrinter;
import org.junit.Test;

import com.sanwisdom.taobao.monitor.businessobject.Deal;
import com.sanwisdom.taobao.monitor.businessobject.Product;
import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.constants.GlobalConstants;
import com.sanwisdom.taobao.monitor.datahelper.TestDataHelper;

public class JdbcShopDaoTest {
	
	static Logger log = Logger.getLogger(JdbcShopDaoTest.class);
	
	JdbcShopDao shopDao = new JdbcShopDao();
	
	JdbcProductDao productDao = new JdbcProductDao();

	@Test
	public void testCreateShopWithCategory() throws IOException {
		Shop shop = new Shop();
		shop.setName("史坦利官方旗舰店");
		shop.setUrl("http://stanley.tmall.com/search.htm");
		shopDao.create(shop, "五金工具");
	}
	
	@Test
	public void testUpdateShopCategory() throws IOException {
		JdbcShopDao shopDao = new JdbcShopDao();
		Shop shop = new Shop();
		shop.setId(Long.valueOf(1));
		shop.setName("史坦利官方旗舰店");
		shop.setUrl("http://stanley.tmall.com/search.htm");
		shopDao.updateCategory(shop, "五金类");
	}
	
	@Test
	public void testCreateShops() throws IOException {
		JdbcShopDao shopDao = new JdbcShopDao();
		Shop shop = new Shop();
		shop.setName("史坦利官方旗舰店");
		shop.setUrl("http://stanley.tmall.com/search.htm");
		shopDao.create(shop, "五金工具");
		
		shop = new Shop();
		shop.setName("力成工具官方旗舰店");
		shop.setUrl("http://atomic.tmall.com/search.htm");
		shopDao.create(shop, "五金工具");
		
		shop = new Shop();
		shop.setName("开拓官方旗舰店");
		shop.setUrl("http://exploit.tmall.com/search.htm");
		shopDao.create(shop, "五金工具");
		
		shop = new Shop();
		shop.setName("百锐官方旗舰店");
		shop.setUrl("http://berent.tmall.com/search.htm");
		shopDao.create(shop, "五金工具");
		
		shop = new Shop();
		shop.setName("闪电家居专营店");
		shop.setUrl("http://shandianjj.tmall.com/search.htm");
		shopDao.create(shop, "五金工具");
		
		shop = new Shop();
		shop.setName("吉吉喜品");
		shop.setUrl("http://zhijiazu.tmall.com/search.htm");
		shopDao.create(shop, "喜品");
		
		shop = new Shop();
		shop.setName("喜传说");
		shop.setUrl("http://xichuanshuo.tmall.com/search.htm");
		shopDao.create(shop, "喜品");
		
		shop = new Shop();
		shop.setName("烟雨集");
		shop.setUrl("http://yanyujilife.tmall.com/search.htm");
		shopDao.create(shop, "喜品");
		
		shop = new Shop();
		shop.setName("橙诺婚品家");
		shop.setUrl("http://chengnuohunpinjia.tmall.com/search.htm");
		shopDao.create(shop, "喜品");
		
		shop = new Shop();
		shop.setName("唯思美");
		shop.setUrl("http://wishmade.tmall.com/search.htm");
		shopDao.create(shop, "喜品");
		
		shop = new Shop();
		shop.setName("六人行家居");
		shop.setUrl("http://lrxjj.tmall.com/search.htm");
		shopDao.create(shop, "喜品");

		shop = new Shop();
		shop.setName("衡互邦旗舰商城店");
		shop.setUrl("http://shop36598113.taobao.com/search.htm");
		shopDao.create(shop, "轮椅");
		
		shop = new Shop();
		shop.setName("乐天康复");
		shop.setUrl("http://lunyi.taobao.com/search.htm");
		shopDao.create(shop, "轮椅");
		
		shop = new Shop();
		shop.setName("上海互邦正品专卖店");
		shop.setUrl("http://shop57767242.taobao.com/search.htm");
		shopDao.create(shop, "轮椅");
		
		shop = new Shop();
		shop.setName("大中华医疗社");
		shop.setUrl("http://shop57751906.taobao.com/search.htm");
		shopDao.create(shop, "轮椅");
		
		shop = new Shop();
		shop.setName("康复医疗保健网店");
		shop.setUrl("http://15132835606.taobao.com/search.htm");
		shopDao.create(shop, "轮椅");
	}
	
	@Test
	public void testCreateShops_jiafang() throws IOException {
		JdbcShopDao shopDao = new JdbcShopDao();
		Shop shop = new Shop();
		shop.setName("ctrod家纺旗舰店");
		shop.setUrl("http://ctrod.tmall.com/search.htm");
		shopDao.create(shop, "家纺");
		
		shop = new Shop();
		shop.setName("妍心家居旗舰店");
		shop.setUrl("http://antion.tmall.com/search.htm");
		shopDao.create(shop, "家纺");
		
		shop = new Shop();
		shop.setName("lovinghome旗舰店");
		shop.setUrl("http://lovinghome.tmall.com/search.htm");
		shopDao.create(shop, "家纺");
		
		shop = new Shop();
		shop.setName("丝茉尔家居旗舰店");
		shop.setUrl("http://simoer.tmall.com/search.htm");
		shopDao.create(shop, "家纺");
		
		shop = new Shop();
		shop.setName("多彩汇旗舰店");
		shop.setUrl("http://duocaihui.tmall.com/search.htm");
		shopDao.create(shop, "家纺");
		
		shop = new Shop();
		shop.setName("夜家居旗舰店");
		shop.setUrl("http://nightshome.tmall.com/search.htm");
		shopDao.create(shop, "家纺");
		
		shop = new Shop();
		shop.setName("觉家居旗舰店");
		shop.setUrl("http://jiao.tmall.com/search.htm");
		shopDao.create(shop, "家纺");
		
		shop = new Shop();
		shop.setName("雅西亚旗舰店");
		shop.setUrl("http://yaxiya.tmall.com/search.htm");
		shopDao.create(shop, "家纺");
		
		shop = new Shop();
		shop.setName("dieu家纺旗舰店");
		shop.setUrl("http://dieu.tmall.com/search.htm");
		shopDao.create(shop, "家纺");
		
		shop = new Shop();
		shop.setName("家乐屋美旗舰店");
		shop.setUrl("http://jialewumei.tmall.com/search.htm");
		shopDao.create(shop, "家纺");
		
	}
	

	@Test
	public void testCreateShop() throws IOException {
		Shop shop = TestDataHelper.read(GlobalConstants.SHOP_INFO_STANLEY);
		shopDao.create(shop);
		Assert.assertNotNull(shop.getId());
	}
	
	@Test
	public void testInsertShopKeepConnected() throws IOException, SQLException {
		Shop shop = new Shop();
		shop.setName("史丹利");
		shop.setUrl("tmall.stanley.com");
		Long id = shopDao.insertShopKeepConnected(shop, shopDao.getConn());
		Assert.assertNotNull(id);
	}
	
	@Test
	public void testInsertProductKeepConnected() throws IOException, SQLException {
		Shop shop = new Shop();
		shop.setName("史丹利");
		shop.setUrl("tmall.stanley.com");
		Long id = shopDao.insertShopKeepConnected(shop, shopDao.getConn());
		shop.setId(id);
		Product p = new Product();
		p.getSummary().setLink("http://detail.tmall.com/item.htm?id=15790260601&");
		p.getSummary().setPrice(new BigDecimal(370.00));
		p.getSummary().setProductId(15790260601L);
		p.getSummary().setRating(4.78);
		p.getSummary().setSalesTotalAmount(175407);
		p.getSummary().setThumbnail("http://img02.taobaocdn.com/bao/uploaded/i2/T1fjY0XfBeXXbFUdM__105738.jpg_160x160.jpg");
		p.getSummary().setTitle("【包邮】史丹利百得冲击钻电钻两用550w家用无极变速正反转");
		shop.getProducts().add(p);
		
		productDao.insertProductKeepConnected(shop, p, shopDao.getConn());
		
		Assert.assertNotNull(id);
	}
	
	@Test
	public void testInsertDealKeepConnected() throws IOException, SQLException {
		Shop shop = new Shop();
		shop.setName("史丹利");
		shop.setUrl("tmall.stanley.com");
		Long shopId = shopDao.insertShopKeepConnected(shop, shopDao.getConn());
		shop.setId(shopId);
		Product p = new Product();
		p.getSummary().setLink("http://detail.tmall.com/item.htm?id=15790260601&");
		p.getSummary().setPrice(new BigDecimal(370.00));
		p.getSummary().setProductId(15790260601L);
		p.getSummary().setRating(4.78);
		p.getSummary().setSalesTotalAmount(175407);
		p.getSummary().setThumbnail("http://img02.taobaocdn.com/bao/uploaded/i2/T1fjY0XfBeXXbFUdM__105738.jpg_160x160.jpg");
		p.getSummary().setTitle("【包邮】史丹利百得冲击钻电钻两用550w家用无极变速正反转");
		shop.getProducts().add(p);
		Long productId = productDao.insertProductKeepConnected(shop, p, shopDao.getConn());
		Assert.assertNotNull(productId);
		
		Deal d = new Deal();
		Date dealDate = new Date(1351682232000L); 
		d.setDate(dealDate);
		d.setSalesAmount(1);
		d.setUnitPrice(new BigDecimal(370));
		Long dealId = productDao.insertDealKeepConnected(productId, d, shopDao.getConn());
		Assert.assertNotNull(dealId);
	}

	@Test
	public void testUpdateShop() throws IOException {
		Shop shop = new Shop();
		shop.setId(Long.valueOf(21));
		shop.setName("stanley1");
		shop.setUrl("stanley1.tmall.com");
		shopDao.update(shop);
	}

	@Test
	public void testUpdateListOfShop() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindByPrimaryKey() throws IOException {
		Shop shop = shopDao.findByPrimaryKey(Long.valueOf(21));
		log.debug(new ObjectMapper().writer(new DefaultPrettyPrinter()).writeValueAsString(shop));
		Assert.assertNotNull(shop);
	}
	
	@Test
	public void testQueryShop_notExist() {
		Shop shop = shopDao.queryShop(Long.valueOf(0));
		Assert.assertNull(shop);
	}
	
	@Test
	public void testQueryShop_exist() {
		Shop shop = shopDao.queryShop(Long.valueOf(20));
		Assert.assertNotNull(shop);
	}
	

	
	@Test
	public void testDeleteByPrimaryKey() throws IOException {
		Shop shop = shopDao.findByPrimaryKey(Long.valueOf(19));
		Assert.assertNotNull(shop);
		
		shopDao.delete(Long.valueOf(19));
		
		shop = shopDao.findByPrimaryKey(Long.valueOf(19));
		Assert.assertNull(shop);
	}
}

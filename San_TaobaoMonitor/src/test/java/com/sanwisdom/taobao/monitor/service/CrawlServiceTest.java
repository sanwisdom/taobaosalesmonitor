package com.sanwisdom.taobao.monitor.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.sanwisdom.common.utils.PropertiesUtils;
import com.sanwisdom.taobao.monitor.AbstractUnitTest;
import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.persistence.impl.JdbcShopDao;
import com.sanwisdom.taobao.monitor.utils.DateUtils;

public class CrawlServiceTest extends AbstractUnitTest {
	
	static Logger log = Logger.getLogger(CrawlServiceTest.class);
	
	private Properties props = null;
	
	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	protected Properties getProperties(String name) {
		if (null == props) {
			props = PropertiesUtils.loadProps(name);
		}
		return props;
	}
		
	private Date dealsFrom;
	
	private Date dealsTo;
	
	
	
	public Date getDealFrom() {
		if (null == dealsFrom) {
			dealsFrom = DateUtils.parseDate(this.getProperties(resourcePackage + "crawler.properties").getProperty("crawl_time_frame_from"));	
		}
		return dealsFrom;
	}
	
	public Date getDealTo() {
		if (null == dealsTo) {
			dealsTo = DateUtils.parseDate(this.getProperties(resourcePackage + "crawler.properties").getProperty("crawl_time_frame_to"));	
		}
		return dealsTo;
	}
	
	String resourcePackage = "/" + this.getClass().getPackage().getName().replace(".", "/") + "/";

	@Test
	public void testEnv() throws IOException {
		Assert.assertEquals("2012-11-11 00:00:00", this.getProperties(resourcePackage + "crawler_test.properties").getProperty("crawl_time_frame_from"));
		
		Assert.assertEquals("2012-11-11 23:59:59", this.getProperties(resourcePackage + "crawler_test.properties").getProperty("crawl_time_frame_to"));
	}
	
	@Test
	public void test() throws IOException {
		CrawlService.scanCreateShop(1, "http://stanley.tmall.com/search.htm", this.getDealFrom(), this.getDealTo());
		System.out.print("Complete");
	}
	
	@Test
	public void test_all() throws IOException {
		Map<Long, String> urls = initShops();
		for (int i = 0; i < urls.size(); i++) {
			CrawlService.scanCreateShop(i + 1, urls.get(i), this.getDealFrom(), this.getDealTo());
		}
		
	}
	
	@Test
	public void test_update() throws IOException {
		CrawlService.scanUpdateShop(2, "http://atomic.tmall.com/search.htm", this.getDealFrom(), this.getDealTo());
	}
	
	@Test
	public void test_updateFromCompensationListFile() throws IOException {
		Map<Long, String> urls = initCompensationShops();
		scan(urls);
	}
	
	@Test
	public void test_update_all_fromDb() throws IOException {
		JdbcShopDao dao = new JdbcShopDao();
		String category = "3C数码配件"; // "家纺/床上用品/布艺"; // "3C数码配件" // "五金/工具" // "服装/内衣/配件" // 3C last date 5/1
		boolean isActive = true;
		List<Long> ids = dao.queryShopIdsByCategory(category, isActive);
		Map<Long, String> urls = initShops(ids); 
		scan(urls);
	}

	protected void scan(Map<Long, String> urls) {
		for(Entry<Long, String> url : urls.entrySet()) {
			try {
				Long id = url.getKey();
				String shop = url.getValue();
				log.info(String.format("Key: %s, Value: %s", String.valueOf(id), shop));
				CrawlService.scanUpdateShop(id, shop, this.getDealFrom(), this.getDealTo());
			} catch (Exception e) {
				log.error("Failed: url " + url, e);
				continue;
			}
			log.info("Finish: url " + url);
		}
	}

	private Map<Long, String> initShops(List<Long> ids) throws IOException {
		Map<Long, String> sites = new LinkedHashMap<Long, String>();
		JdbcShopDao dao = new JdbcShopDao();
		List<Shop> shops = dao.findByPrimaryKeys(ids);
		for (Shop shop : shops) {
			sites.put(shop.getId(), shop.getUrl());
		}
		return sites;
	}
	
	protected Map<Long, String> initCompensationShops() throws IOException {
		Map<Long, String> urls = new LinkedHashMap<Long, String>();
		InputStream in = this.getClass().getResourceAsStream("/compensation_site.properties");
		Properties p = new Properties();
		p.load(in);
		Enumeration<?> en = p.propertyNames(); //得到资源文件中的所有key值  
        while (en.hasMoreElements()) {  
        	String key = (String) en.nextElement();  
        	log.info("key=" + key + " value=" + p.getProperty(key));
        	urls.put(Long.valueOf(key), p.getProperty(key));
        }
		return urls;
	}

	protected Map<Long, String> initShops() {
		Map<Long, String> urls = new LinkedHashMap<Long, String>();
//		urls.put(Long.valueOf(1), "http://stanley.tmall.com/search.htm");
//		urls.put(Long.valueOf(2), "http://atomic.tmall.com/search.htm");
//		urls.put(Long.valueOf(3), "http://exploit.tmall.com/search.htm");
//		urls.put(Long.valueOf(4), "http://berent.tmall.com/search.htm");
//		urls.put(Long.valueOf(5), "http://shandianjj.tmall.com/search.htm");
//		urls.put(Long.valueOf(6), "http://zhijiazu.tmall.com/search.htm");
		urls.put(Long.valueOf(7), "http://xichuanshuo.tmall.com/search.htm");
		urls.put(Long.valueOf(8), "http://yanyujilife.tmall.com/search.htm");
		urls.put(Long.valueOf(9), "http://chengnuohunpinjia.tmall.com/search.htm");
		urls.put(Long.valueOf(10), "http://wishmade.tmall.com/search.htm");
		urls.put(Long.valueOf(11), "http://lrxjj.tmall.com/search.htm");
		return urls;
	}
	
	protected Map<Long, String> initShops_jiafang() {
		Map<Long, String> urls = new LinkedHashMap<Long, String>();
		urls.put(Long.valueOf(17), "http://ctrod.tmall.com/search.htm"); // 17
		urls.put(Long.valueOf(18), "http://antion.tmall.com/search.htm");
		urls.put(Long.valueOf(19), "http://lovinghome.tmall.com/search.htm");
		urls.put(Long.valueOf(20), "http://simoer.tmall.com/search.htm");
		urls.put(Long.valueOf(21), "http://duocaihui.tmall.com/search.htm");
		urls.put(Long.valueOf(22), "http://nightshome.tmall.com/search.htm");
		urls.put(Long.valueOf(23), "http://jiao.tmall.com/search.htm");
		urls.put(Long.valueOf(24), "http://yaxiya.tmall.com/search.htm");
		urls.put(Long.valueOf(25), "http://dieu.tmall.com/search.htm");
		urls.put(Long.valueOf(26), "http://jialewumei.tmall.com/search.htm");
		return urls;
	}
	

}

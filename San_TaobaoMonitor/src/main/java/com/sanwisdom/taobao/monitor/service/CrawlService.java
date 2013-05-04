package com.sanwisdom.taobao.monitor.service;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.navigation.ProductVisitor;
import com.sanwisdom.taobao.monitor.persistence.impl.JdbcShopDao;

public class CrawlService {
	
	static Logger log = Logger.getLogger(CrawlService.class);

	public static boolean scanShop(Shop shop) {
		
		return true;
	}
	
	
	public static boolean scanCreateShop(long shopId, String url, Date dealsFrom, Date dealsTo) throws IOException {
		Shop info = new ProductVisitor(dealsFrom, dealsTo).visitProductSales(url);
		info.setId(shopId);
		JdbcShopDao dao = new JdbcShopDao();
		dao.createProductsOfShop(info);
		return true;
	}
	
	public static boolean scanUpdateShop(long shopId, String url, Date dealsFrom, Date dealsTo) throws IOException {
		log.info("Scanning...");
		Shop info = new ProductVisitor(dealsFrom, dealsTo).visitProductSales(url);
		log.info("Scanning...Finished");
		info.setId(shopId);
		info.setUrl(url);
		JdbcShopDao dao = new JdbcShopDao();
		log.info("Persisting...");
		dao.updateProductsOfShop(info);
		log.info("Persisting...Finished");
		return true;
	}
	
}

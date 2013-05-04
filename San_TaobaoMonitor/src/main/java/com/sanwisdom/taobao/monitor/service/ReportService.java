package com.sanwisdom.taobao.monitor.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.sanwisdom.taobao.monitor.businessobject.Product;
import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.persistence.impl.ExcelProductDao;
import com.sanwisdom.taobao.monitor.persistence.impl.JdbcReportDao;
import com.sanwisdom.taobao.monitor.persistence.impl.JdbcShopDao;

public class ReportService {

	static Logger log = Logger.getLogger(ReportService.class);
	
	public static List<Shop> generateMonthlyReport(Date date) {
		List<Shop> tops = new ArrayList<Shop>();
		JdbcShopDao dao = new JdbcShopDao();
		try {
			List<Shop> allShops = dao.findAll();
			for (Shop shop : allShops) {
				Shop topped = topProducts(shop);
				tops.add(topped);
			}
		} catch (IOException e) {
			log.debug(e);
			throw new RuntimeException(e);
		}
		return tops;
	}

	private static Shop topProducts(Shop shop) {
		List<Product> ps = shop.topMonthlySalesAmount(shop.getProducts().size());
		shop.sortSalesTotal(ps);
		Shop topped = new Shop(shop.getId(), shop.getName(), shop.getUrl());
		topped.getProducts().addAll(ps);
		return topped;
	}
	
	private static Shop topProducts(Shop shop, int size) {
		List<Product> ps = shop.topMonthlySalesAmount(size);
		shop.sortSalesTotal(ps);
		Shop topped = new Shop(shop.getId(), shop.getName(), shop.getUrl());
		topped.getProducts().addAll(ps);
		return topped;
	}
	
	public static Shop generateMonthlyReportForShop(Long shopId, Date from, Date to) {
		Shop shop = readMonthlyTransactionsForShop(shopId, from, to);
		saveMonthlyReportForShop(shop, to);
		return shop;
	}
	
	protected static Shop readMonthlyTransactionsForShop(Long shopId, Date from, Date to) {
		JdbcShopDao dao = new JdbcShopDao();
		Shop topped = null;
		try {
			Shop shop = dao.findShopByIdWithDealsWithIn(shopId, from, to);
			topped = topProducts(shop);
		} catch (IOException e) {
			log.debug(e);
			throw new RuntimeException(e);
		}
		return topped;
	}
	
	protected static Shop readTimeFrameTransactionsForShop(Long shopId, Date from, Date to, int size) {
		JdbcShopDao dao = new JdbcShopDao();
		Shop topped = null;
		try {
			Shop shop = dao.findShopByIdWithDealsWithIn(shopId, from, to);
			topped = topProducts(shop, size);
		} catch (IOException e) {
			log.debug(e);
			throw new RuntimeException(e);
		}
		return topped;
	}

	protected static boolean saveMonthlyReportForShop(Shop shop, Date dateTo) {
		JdbcReportDao dao = new JdbcReportDao();
		try {
			dao.generateReport(shop, dateTo);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return true;
	}
	
	public static void generateReportToExcel(List<Long> shopIds, Date from, Date to, int size) {
		List<Shop> shops = new ArrayList<Shop>();
		for (Long shopId : shopIds) {
			shops.add(readTimeFrameTransactionsForShop(shopId, from, to, size));	
		}
		ExcelProductDao dao = new ExcelProductDao();
		try {
			dao.create(shops);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

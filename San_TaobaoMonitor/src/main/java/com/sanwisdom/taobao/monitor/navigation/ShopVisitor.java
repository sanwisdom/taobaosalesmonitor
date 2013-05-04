package com.sanwisdom.taobao.monitor.navigation;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.util.DefaultPrettyPrinter;

import com.sanwisdom.taobao.monitor.businessobject.Product;
import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.salesconsole.GlobalConstants;


public class ShopVisitor {

	private Date dealFrom = null; // parseDate("2012-12-23 00:00:00");
	private Date dealTo = null; // parseDate("2012-12-28 23:59:59");
	
	public Date getDealFrom() {
		return dealFrom;
	}

	public Date getDealTo() {
		return dealTo;
	}

	public ShopVisitor(Date dealFrom, Date dealTo) {
		this.dealFrom = dealFrom;
		this.dealTo = dealTo;
	}
	
	static Logger log = Logger.getLogger(ShopVisitor.class);
	
	private static int TOP_NUMBER = 30;
	
	public List<Shop> visit(List<String> urls) {
		ProductVisitor pv = new ProductVisitor(this.getDealFrom(), this.getDealTo());
		List<Shop> shops = new ArrayList<Shop>();
		for (String url : urls) {
			log.info(String.format("visiting...", url));
			Shop info = pv.visitProductSales(url);
			ObjectMapper mapper = new ObjectMapper();
			String fileName = GlobalConstants.TOTAL_SHOP_INFO; 
			log.debug(fileName);
			File resultFile = new File(fileName);
			try {
				mapper.writer(new DefaultPrettyPrinter()).writeValue(resultFile, info);
			} catch (Exception e) {
				log.error(e);
			}
			List<Product> ps = info.topMonthlySalesAmount(TOP_NUMBER);
			info.sortSalesTotal(ps);
			info.getProducts().clear();
			info.getProducts().addAll(ps);
			shops.add(info);
		}
		return shops;
	}
	
	
}

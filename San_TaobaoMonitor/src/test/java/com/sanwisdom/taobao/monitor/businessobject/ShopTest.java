package com.sanwisdom.taobao.monitor.businessobject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.util.DefaultPrettyPrinter;
import org.junit.Test;

public class ShopTest {
	static Logger log = Logger.getLogger(ShopTest.class);

//	@Test
//	public void tesTopMonthlySalesAmountOfProducts() {
//		Shop info = new ProductVisitor().visitProductSales("http://stanley.tmall.com/search.htm");
//		Assert.assertEquals(237, info.getProducts().size());
//		List<Product> products = info.topMonthlySalesAmount(15);
//		for (Product product : products) {
//			log.info(String.format("%s: 共销售%s件 (单价:%s元)", 
//					product.getSummary().getTitle(), 
//					String.valueOf(product.getSummary().getMonthlySalesAmount()),
//					String.valueOf(product.getSummary().getPrice())));
//		}
//	}
//	
//	@Test
//	public void tesTopMonthlySalesOfProducts_stanley() {
//		String link = "http://stanley.tmall.com/search.htm"; 
//		Shop info = new ProductVisitor().visitProductSales(link);
//		List<Product> products = info.topMonthlySalesAmount(15);
//		info.sortSalesTotal(products);
//		for (Product product : products) {
//			log.info(String.format("%s(id:%s): 销售总金额:%s. 共销售%s件  (单价:%s元)", 
//					product.getSummary().getTitle(), 
//					String.valueOf(product.getSummary().getProductId()),
//					String.valueOf(product.getSummary().getSalesTotal()),
//					String.valueOf(product.getSummary().getMonthlySalesAmount()),
//					String.valueOf(product.getSummary().getPrice())));
//		}
//		output(info, products, GlobalConstants.SHOP_INFO_STANLEY);
//	}


	protected void output(Shop info, List<Product> products, String fileName) {
		info.getProducts().clear();
		info.getProducts().addAll(products);
		ObjectMapper mapper = new ObjectMapper();
		log.debug(fileName);
		File resultFile = new File(fileName);
		try {
			mapper.writer(new DefaultPrettyPrinter()).writeValue(resultFile, info);
		} catch (Exception e) {
			log.error(e);
		}
	}
	
//	@Test
//	public void tesTopMonthlySalesOfProducts_atomic() {
//		String link = "http://atomic.tmall.com/search.htm"; 
//		Shop info = new ProductVisitor().visitProductSales(link);
//		List<Product> products = info.topMonthlySalesAmount(15);
//		info.sortSalesTotal(products);
//		for (Product product : products) {
//			log.info(String.format("%s(id:%s): 销售总金额:%s. 共销售%s件  (单价:%s元)", 
//					product.getSummary().getTitle(), 
//					String.valueOf(product.getSummary().getProductId()),
//					String.valueOf(product.getSummary().getSalesTotal()),
//					String.valueOf(product.getSummary().getMonthlySalesAmount()),
//					String.valueOf(product.getSummary().getPrice())));
//		}
//		output(info, products, GlobalConstants.SHOP_INFO_ATOMIC);
//	}
//	
//	@Test
//	public void tesTopMonthlySalesOfProducts_exploit() {
//		String link = "http://exploit.tmall.com/search.htm";
//		Shop info = new ProductVisitor().visitProductSales(link);
//		List<Product> products = info.topMonthlySalesAmount(15);
//		info.sortSalesTotal(products);
//		for (Product product : products) {
//			log.info(String.format("%s(id:%s): 销售总金额:%s. 共销售%s件  (单价:%s元)", 
//					product.getSummary().getTitle(), 
//					String.valueOf(product.getSummary().getProductId()),
//					String.valueOf(product.getSummary().getSalesTotal()),
//					String.valueOf(product.getSummary().getMonthlySalesAmount()),
//					String.valueOf(product.getSummary().getPrice())));
//		}
//		output(info, products, GlobalConstants.SHOP_INFO_EXPLOIT);
//	}
	
	private static final String USER_DIR = System.getProperty("user.dir");

	@Test
	public void testBug() {
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(new File(getTestFilePath()), Shop.class);
		} catch (IOException e) {
			log.error(e);
		}
		List<Product> products = shop.topMonthlySalesAmount(15);
		Assert.assertEquals(15, products.size());
	}

	private String getTestFilePath() {
		String path = USER_DIR + "\\test\\" + this.getClass().getPackage().getName().replace(".", "\\") + "\\shop_test.json";
		log.debug("Path: " + path);
		return path;
	}
}

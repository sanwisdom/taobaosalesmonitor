package com.sanwisdom.taobao.monitor.navigation;

import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.sanwisdom.taobao.monitor.businessobject.Product;
import com.sanwisdom.taobao.monitor.businessobject.Shop;

public class ProductVisitorTest {


	static Logger log = Logger.getLogger(ProductVisitorTest.class);
	
	@Test
	public void testGetTotalNumberOfProducts_tmall() {
		Shop info = new ProductVisitor(null, null).visitProductSales("http://stanley.tmall.com/search.htm");
		Assert.assertEquals(237, info.getProducts().size());
	}
	
	@Test
	public void testGetTotalNumberOfProducts_taobao() {
		Shop info = new ProductVisitor(null, null).visitProductSales("http://fashion1212.taobao.com/?search=y");
		Assert.assertEquals(100, info.getProducts().size());
	}
	
	@Test
	public void tesTopSalesAmountOfProducts() {
		Shop info = new ProductVisitor(null, null).visitProductSales("http://stanley.tmall.com/search.htm");
		Assert.assertEquals(237, info.getProducts().size());
		List<Product> products = info.topSalesAmount(15);
		for (Product product : products) {
			log.info(String.format("%s: %s", product.getSummary().getTitle(), String.valueOf(product.getSummary().getSalesTotalAmount())));
		}
	}
	
}

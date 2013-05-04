package com.sanwisdom.taobao.monitor.businessobject;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.sanwisdom.taobao.monitor.businessobject.Product;
import com.sanwisdom.taobao.monitor.businessobject.Shop;

public class ProductInfoTest {

	static Logger log = Logger.getLogger(ProductInfoTest.class);
	
	@Test
	public void testTopSalesAmount() {
		Shop info = new Shop();
		int size = 30;
		for (int i = 0; i < size; i++) {
			long salesAmount = new Long(size - i);
			info.getProducts().add(createProduct(salesAmount));
		}
		List<Product> products = info.topSalesAmount(15);
		for (Product product : products) {
			log.info(product.getSummary().getSalesTotalAmount());
		}
	}
	
	private Product createProduct(long salesAmount) {
		Product p = new Product();
		p.getSummary().setSalesTotalAmount(salesAmount);
		return p;
	}

}

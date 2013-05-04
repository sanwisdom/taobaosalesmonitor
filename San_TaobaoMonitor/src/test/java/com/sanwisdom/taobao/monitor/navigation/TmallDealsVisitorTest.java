package com.sanwisdom.taobao.monitor.navigation;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.sanwisdom.taobao.monitor.businessobject.DealInfo;
import com.sanwisdom.taobao.monitor.navigation.impl.TmallDealsVisitor;

public class TmallDealsVisitorTest {

	static Logger log = Logger.getLogger(TmallDealsVisitorTest.class);
	
	@Test
	public void testVisitProductDeals_tmall() {
		String link = "http://detail.tmall.com/item.htm?id=12577540898"; 
		DealInfo info = new TmallDealsVisitor(null, null).visitProductDeals(link);
		log.info(String.format("Amount: %s, Sales: %s", String.valueOf(info.getItemCount()), String.valueOf(info.getTotalSales())));
	}
	
	@Test
	public void testVisitProductDeals_taobao() {
		String link = "http://item.taobao.com/item.htm?id=19990564425"; 
		DealInfo info = new TmallDealsVisitor(null, null).visitProductDeals(link);
		log.info(String.format("Amount: %s, Sales: %s", String.valueOf(info.getItemCount()), String.valueOf(info.getTotalSales())));
	}


	@Test
	public void testVisitItemDetails_withPaging() {
		String link = "http://detail.tmall.com/item.htm?id=8870807483";
		DealInfo deals = new TmallDealsVisitor(null, null).visitProductDeals(link);
		Assert.assertEquals(100, deals.getItemCount());
	}

	@Test
	public void testVisitItemDetails_compatible() {
		String link = "http://detail.tmall.com/item.htm?id=18757476160&";
		DealInfo deals = new TmallDealsVisitor(null, null).visitProductDeals(link);
		Assert.assertEquals(100, deals.getItemCount());
	}

	@Test
	public void testVisitItemDetails_compatible_doublecheck() {
		String link = "http://detail.tmall.com/item.htm?id=16713191279";
		DealInfo deals = new TmallDealsVisitor(null, null).visitProductDeals(link);
		Assert.assertEquals(100, deals.getItemCount());
	}
	
}

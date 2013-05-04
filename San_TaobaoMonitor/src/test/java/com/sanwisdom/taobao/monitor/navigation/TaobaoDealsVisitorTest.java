package com.sanwisdom.taobao.monitor.navigation;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.sanwisdom.taobao.monitor.businessobject.DealInfo;
import com.sanwisdom.taobao.monitor.navigation.impl.TaobaoDealsVisitor;

public class TaobaoDealsVisitorTest {

	static Logger log = Logger.getLogger(TaobaoDealsVisitorTest.class);
		
	@Test
	public void testVisitProductDeals_taobao() {
		String link = "http://item.taobao.com/item.htm?id=21323532063"; 
		DealInfo info = new TaobaoDealsVisitor(null, null).visitProductDeals(link);
		log.info(String.format("Amount: %s, Sales: %s", String.valueOf(info.getItemCount()), String.valueOf(info.getTotalSales())));
	}
	
}

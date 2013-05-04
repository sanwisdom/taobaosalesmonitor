package com.sanwisdom.taobao.monitor.parser;

import java.util.List;

import junit.framework.Assert;

import org.jsoup.nodes.Document;
import org.junit.Test;

import com.sanwisdom.taobao.monitor.businessobject.Product;
import com.sanwisdom.taobao.monitor.htmlparser.HtmlParser;
import com.sanwisdom.taobao.monitor.httpagent.ClientAgent;

public class TmallProductPageParserTest {

	@Test
	public void testGetPageGridItem_tmall() {
		String response = new ClientAgent().visitAndClose("http://atomic.tmall.com/search.htm", null);
		Document doc = new HtmlParser().parse(response);
		List<Product> products = new DefaultProductPageParser().parse(doc);
		Assert.assertEquals(20, products.size());
	}
			
	@Test
	public void testGetProductId() {
		String link = "http://detail.tmall.com/item.htm?id=13049443643";
		long id = new DefaultProductPageParser().getProductId(link);
		Assert.assertEquals(13049443643L, id);
	}

}

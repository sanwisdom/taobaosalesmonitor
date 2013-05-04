package com.sanwisdom.taobao.monitor.parser;

import java.util.List;

import junit.framework.Assert;

import org.jsoup.nodes.Document;
import org.junit.Test;

import com.sanwisdom.taobao.monitor.businessobject.Product;
import com.sanwisdom.taobao.monitor.htmlparser.HtmlParser;
import com.sanwisdom.taobao.monitor.httpagent.ClientAgent;

public class TaobaoProductPageParserTest {

	@Test
	public void testGetPageGridItem_taobao() {
		String response = new ClientAgent().visitAndClose("http://shop65830479.taobao.com/?search=y", null);
		Document doc = new HtmlParser().parse(response);
		List<Product> products = new DefaultProductPageParser().parse(doc);
		Assert.assertEquals(20, products.size());
	}
	

}

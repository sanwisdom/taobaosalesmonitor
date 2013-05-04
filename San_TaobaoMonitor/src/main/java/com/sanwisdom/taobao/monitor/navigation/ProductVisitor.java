package com.sanwisdom.taobao.monitor.navigation;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sanwisdom.taobao.monitor.businessobject.DealInfo;
import com.sanwisdom.taobao.monitor.businessobject.Product;
import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.htmlparser.HtmlParser;
import com.sanwisdom.taobao.monitor.httpagent.ClientAgent;
import com.sanwisdom.taobao.monitor.parser.DefaultProductPageParser;

public class ProductVisitor {

	private Date dealFrom = null; // parseDate("2012-12-23 00:00:00");
	private Date dealTo = null; // parseDate("2012-12-28 23:59:59");
	
	public Date getDealFrom() {
		return dealFrom;
	}

	public Date getDealTo() {
		return dealTo;
	}

	public ProductVisitor(Date dealFrom, Date dealTo) {
		this.dealFrom = dealFrom;
		this.dealTo = dealTo;
	}
	
	

	static Logger log = Logger.getLogger(ProductVisitor.class);
	
	private static int DEFAULT_NUMBER_OF_PRODUCT_PER_PAGE = 20;
	
	public Shop visitProductSales(String link) {
		String responseBodyDetail = new ClientAgent().visitAndClose(link, null);
		Document docSearchResultPage = new HtmlParser().parse(responseBodyDetail);
		URL url = null;
		try {
			url = new URL(link);
		} catch (MalformedURLException e) {
			log.error(e);
		}
		if (null == url) {
			return new Shop();
		}
		Shop products = this.visitProducts(docSearchResultPage, url);
		products.setUrl(url.getAuthority());
		return products;
	}
	
	protected Shop visitProducts(Document searchResultDoc, URL url) {
		String linkTemplate = url.getProtocol() + "://" + url.getAuthority() + "/search.htm?search=y&viewType=grid&pageNum=%s";
		boolean isHotSale = true;
		if (isHotSale) {
			linkTemplate += "&orderType=_coefp";
		}
		Shop productInfo = new Shop();
		if (null == searchResultDoc) {
			return productInfo;
		}
		Elements totalNumberOfProducts = searchResultDoc.select("div.search-result > span");
		String total = totalNumberOfProducts.get(0).text().trim();
		log.info(String.format("total info: %s", total));
		if (StringUtils.isEmpty(total)) {
			return productInfo;
		}
		int numberOfPages = (Integer.parseInt(total) + DEFAULT_NUMBER_OF_PRODUCT_PER_PAGE - 1) / DEFAULT_NUMBER_OF_PRODUCT_PER_PAGE;
		if (isHotSale && numberOfPages > 5) {
			numberOfPages = 5;
		}
		HttpClient httpclient = new DefaultHttpClient(); 
		try {
			ClientAgent clientAgent = new ClientAgent();
			HtmlParser htmlParser = new HtmlParser();
			
//			TaobaoDealsVisitor dealsVisitor = new TaobaoDealsVisitor(this.getDealFrom(), this.getDealTo());
			
			AbstractDealsVisitor dealsVisitor = DealsVisitorFactory.createDealsVisitor(url);
			dealsVisitor.setDealFrom(this.getDealFrom());
			dealsVisitor.setDealTo(this.getDealTo());
			
			DefaultProductPageParser productPageParser = new DefaultProductPageParser();
			for (int i = 1; i <= numberOfPages; i++) {
				String pageingUrl = String.format(linkTemplate, i);
				log.info(String.format("visiting...page %s: %s", i, pageingUrl));
				String repsonse = clientAgent.visitKeepConnection(pageingUrl, url.toString(), httpclient, Integer.valueOf(3));
				Document productsPageDoc = htmlParser.parse(repsonse);
				List<Product> products = productPageParser.parse(productsPageDoc);
				for (Product product : products) {
					if (product.getSummary().getSalesTotalAmount() == 0) {
						continue;
					}
					log.info(String.format("Product Name: %s, Link: %s", product.getSummary().getTitle(), product.getSummary().getLink()));
					DealInfo dealInfo = dealsVisitor.visitProductDeals(product.getSummary().getLink());
					product.add(dealInfo);
				}
				productInfo.getProducts().addAll(products);
			}
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return productInfo;
	}
	
}

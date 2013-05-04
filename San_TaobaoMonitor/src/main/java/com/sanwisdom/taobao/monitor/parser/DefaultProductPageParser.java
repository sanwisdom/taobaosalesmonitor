package com.sanwisdom.taobao.monitor.parser;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sanwisdom.taobao.monitor.businessobject.Product;
import com.sanwisdom.taobao.monitor.businessobject.ProductSummary;

public class DefaultProductPageParser {

	static Logger log = Logger.getLogger(DefaultProductPageParser.class);
	
	public List<Product> parse(Document productsPageDoc) {
		List<Product> list = new LinkedList<Product>();
		if (null == productsPageDoc) {
			return list;
		}
		
		Elements results = productsPageDoc.select("#J_ShopSearchResult");
		Element result = results.get(0);
		log.info("ShopSearchResult: " + results.text());
		Elements shoplist = result.select("ul.shop-list");
		
        Element grid = null;
		try {
			if (shoplist.size() > 0) {
				grid = shoplist.get(0);
			} else {
				return list;
			}
		} catch (Exception e) {
			log.error(e);
			log.error(productsPageDoc);
			log.error(shoplist);
			throw new RuntimeException(e);
//			return list;
		}
		Elements items = grid.select("li > div.item");
		log.info(String.format("executing all (%s) items ", items.size()));
		log.info("----------------------------------------");
		int i = 0;
		for (Element element : items) {
			i++;
			log.info(String.format("-------------------%s---------------------", i));
//			log.info(element);
			String thumbnail = element.select("div.pic > a > img").attr("data-ks-lazyload");
			log.info(String.format("thumbnail: %s", thumbnail));
			String title = element.select("div.desc > a").text();
			log.info(String.format("title: %s", title));
			String price = element.select("div.price > strong").text();
			log.info(String.format("price: %s", price));
			String salesAmount = element.select("div.sales-amount > em").text();
			log.info(String.format("sales amount: %s", salesAmount));
			String rating = element.select("div.rating > span").attr("title");
			log.info(String.format("rating: %s", rating));
			String link = element.select("div.pic > a[href]").attr("href");
			log.info(String.format("link: %s", link));
			Product p = new Product();
			ProductSummary s = new ProductSummary();
			p.setSummary(s);
			s.setLink(link);
			long id = getProductId(link);
 			s.setProductId(id);
 			if (!StringUtils.isEmpty(price)) {
 				price = price.replace("元", "").trim();
 			}
			s.setPrice(StringUtils.isEmpty(price) ? null : new BigDecimal(price));
			log.info(String.format("converted price: %s", s.getPrice()));
			s.setRating(StringUtils.isEmpty(rating) ? 0 : Double.valueOf(rating.substring(0, rating.length() - 1)));
			s.setSalesTotalAmount(StringUtils.isEmpty(salesAmount) ? 0 : Long.valueOf(salesAmount));
			s.setTitle(title);
			s.setThumbnail(thumbnail);
			list.add(p);
		}
		log.info("----------------------------------------");
		return list;
	}

	protected long getProductId(String link) {
		long id = 0;
		if (!StringUtils.isEmpty(link)) {
			int start = link.indexOf("id=");
			if (start >= 0) {
				link = link.substring(start + "id=".length());
				int end = link.indexOf("&");
				if (end > 0 && end < link.length()) {
					link = link.substring(0, end);
				}
				if (!StringUtils.isEmpty(link) && StringUtils.isNumeric(link)) {
					id = Long.parseLong(link);
				}
			}
		}
		return id;
	}
	
}

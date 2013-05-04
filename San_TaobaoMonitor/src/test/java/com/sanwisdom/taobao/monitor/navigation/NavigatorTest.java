package com.sanwisdom.taobao.monitor.navigation;

import java.io.IOException;
import java.util.Iterator;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.sanwisdom.taobao.monitor.htmlparser.HtmlParser;
import com.sanwisdom.taobao.monitor.httpagent.ClientAgent;

public class NavigatorTest {

	static Logger log = Logger.getLogger(NavigatorTest.class);
	
	
	
	@Test
	public void testVisitItemDetails() throws ClientProtocolException,
			IOException {

		String link = "http://detail.tmall.com/item.htm?id=14799092585";
		String responseBodyDetail = new ClientAgent().visitAndClose(link, null);
		Document docDetail = new HtmlParser().parse(responseBodyDetail);
		Elements dealInfos = docDetail.select("button#J_listBuyerOnView");
		log.info(String.format("deals info: %s", dealInfos));
		Element dealInfo = dealInfos.get(0);
		String dealsLink = dealInfo.attr("detail:params");
		log.info(String.format("deals link: %s", dealsLink));
		log.info("----------------------------------------");
		HttpClient httpclientDeals = new DefaultHttpClient();
		try {
			HttpGet httpGetDeals = new HttpGet(dealsLink);
			ResponseHandler<String> responseDealsHandler = new BasicResponseHandler();
			String responseDeals = httpclientDeals.execute(httpGetDeals,
					responseDealsHandler);
			Document deals = Jsoup.parse(responseDeals);
			Elements monthRecordDetails = deals
					.select("table.table-deal-record > tbody > tr");
			log.info(String.format("month record details (count: %s): %s",
					monthRecordDetails.size() - 1, monthRecordDetails));
			Iterator<Element> iterator = monthRecordDetails.iterator();
			iterator.next();
			for (Iterator<Element> iter = iterator; iterator.hasNext();) {
				Element element = iter.next();
				Element price = element.select("tr > td > em").get(0);
				log.info(String.format("month record price: %s", price.text()));
				String dealDate = price.parent().nextElementSibling()
						.nextElementSibling().text();
				log.info(String.format("month record deal date: %s", dealDate));
			}
		} finally {
			httpclientDeals.getConnectionManager().shutdown();
		}

	}
	
	
	
}

package com.sanwisdom.taobao.monitor.navigation.impl;

import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sanwisdom.taobao.monitor.businessobject.DealInfo;
import com.sanwisdom.taobao.monitor.htmlparser.HtmlParser;
import com.sanwisdom.taobao.monitor.httpagent.ClientAgent;
import com.sanwisdom.taobao.monitor.navigation.AbstractDealsVisitor;
import com.sanwisdom.taobao.monitor.parser.TmallProductItemDealsParser;

public class TmallDealsVisitor extends AbstractDealsVisitor {

	static Logger log = Logger.getLogger(TmallDealsVisitor.class);
	
	public TmallDealsVisitor() {
		super();
	}

	public TmallDealsVisitor(Date dealFrom, Date dealTo) {
		super(dealFrom, dealTo);
	}
		
	@Override	
	protected DealInfo visitProductDeals(Document previewDoc, String referer) {
		DealInfo dealsOfProduct = new DealInfo();
		if (null == previewDoc) {
			return dealsOfProduct;
		}
		log.debug(String.format("previewDoc: %s", previewDoc));
		Elements dealInfos = previewDoc.select("button#J_listBuyerOnView");
		log.debug(String.format("deals info: %s", dealInfos));
		if (dealInfos.size() == 0) {
			return dealsOfProduct;
		}
		Element dealInfo = dealInfos.get(0);
		String dealsLink = dealInfo.attr("detail:params");
		log.debug(String.format("deals link: %s", dealsLink));
		dealsLink = dealsLink.substring(0, dealsLink.indexOf(","));		
		log.debug("----------------------------------------");
		log.debug("----------------------------------------");
		log.debug(String.format("deals link: %s", dealsLink));
		dealsLink = dealsLink.concat("&callback=TShop.mods.DealRecord.reload");
		log.debug("----------------------------------------");
		log.debug(String.format("deals link: %s", dealsLink));
		log.debug("----------------------------------------");
		HttpClient httpclientDeals = new DefaultHttpClient();
		try {
			ClientAgent clientAgent = new ClientAgent();
			String responseDeals = clientAgent.visitKeepConnection(dealsLink, referer, httpclientDeals, Integer.valueOf(3));
			log.debug("----------------------------------------");
			responseDeals = responseDeals.substring(responseDeals.indexOf("<table"), responseDeals.indexOf("</table>") + "</table>".length());
			log.debug(String.format("response deals: %s", responseDeals));
			log.debug("----------------------------------------");
			TmallProductItemDealsParser productItemDealsParser = new TmallProductItemDealsParser(this.getDealFrom(), this.getDealTo());
			DealInfo firstPageInfo = productItemDealsParser.parse(new HtmlParser().parse(responseDeals));
			int totalCount = firstPageInfo.getItemCount();
			dealsOfProduct.add(firstPageInfo);
			int i = 2;
			while (i <= 100) {
				String linkage = getNextPageUrl(dealsLink, i);
				log.debug(String.format("deals link: %s", linkage));
				responseDeals = clientAgent.visitKeepConnection(linkage, referer, httpclientDeals, Integer.valueOf(3));
				DealInfo nextPageInfo = productItemDealsParser.parse(new HtmlParser().parse(responseDeals));
				if (nextPageInfo.getDeals().size() == 0
						|| dealsOfProduct.getDeals().size() == 0) {
					break;
				} // FIXME
				if (nextPageInfo.getDeals().size() != 0
						&& dealsOfProduct.getDeals().size() != 0
						&& !nextPageInfo.getDeals().get(0).getDate().before(
						dealsOfProduct.getDeals().get(0).getDate())) {
					break;
				}
				dealsOfProduct.add(nextPageInfo);
				int count = nextPageInfo.getItemCount();
				if (0 == count) {
					break;
				} // FIXME	
				totalCount += count;
				i++;
			}
			log.info(String.format("Monthly Sale: %s", totalCount));
			log.info(String.format("Monthly Sale Total: %s", dealsOfProduct.getTotalSales()));
		} finally {
			httpclientDeals.getConnectionManager().shutdown();
		}
		return dealsOfProduct;
	}

	@Override
	protected String getNextPageUrl(String dealsLink, int i) {
		int indexOfFrom = dealsLink.indexOf("bid_page=");
		int length = "bid_page=".length() + 1;
		String linkage = dealsLink.replace(
				dealsLink.substring(indexOfFrom, indexOfFrom + length),
				"bid_page=" + i);
		return linkage;
	}
}

package com.sanwisdom.taobao.monitor.navigation;

import java.util.Date;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.sanwisdom.taobao.monitor.businessobject.DealInfo;
import com.sanwisdom.taobao.monitor.htmlparser.HtmlParser;
import com.sanwisdom.taobao.monitor.httpagent.ClientAgent;

public abstract class AbstractDealsVisitor {
	private Date dealFrom = null; // parseDate("2012-12-23 00:00:00");

	private Date dealTo = null; // parseDate("2012-12-28 23:59:59");
	
	public Date getDealFrom() {
		return dealFrom;
	}

	public Date getDealTo() {
		return dealTo;
	}
	
	public void setDealFrom(Date dealFrom) {
		this.dealFrom = dealFrom;
	}

	public void setDealTo(Date dealTo) {
		this.dealTo = dealTo;
	}
	
	public AbstractDealsVisitor() {
		
	}

	public AbstractDealsVisitor(Date dealFrom, Date dealTo) {
		this.dealFrom = dealFrom;
		this.dealTo = dealTo;
	}
	
	static Logger log = Logger.getLogger(AbstractDealsVisitor.class);
	
	public DealInfo visitProductDeals(String link) {
		String responseBodyDetail = new ClientAgent().visitAndClose(link, null);
		Document docDetail = new HtmlParser().parse(responseBodyDetail);
		DealInfo deals = this.visitProductDeals(docDetail, link);
		return deals;
	}
	
	protected abstract DealInfo visitProductDeals(Document previewDoc, String referer);
	
	protected abstract String getNextPageUrl(String dealsLink, int i);
}

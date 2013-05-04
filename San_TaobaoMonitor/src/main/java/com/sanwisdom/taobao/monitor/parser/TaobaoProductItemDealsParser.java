package com.sanwisdom.taobao.monitor.parser;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sanwisdom.taobao.monitor.businessobject.Deal;
import com.sanwisdom.taobao.monitor.businessobject.DealInfo;
import com.sanwisdom.taobao.monitor.utils.DateUtils;

public class TaobaoProductItemDealsParser {
	
	private Date dealFrom = null; // parseDate("2012-12-23 00:00:00");
	private Date dealTo = null; // parseDate("2012-12-28 23:59:59");
	
	public Date getDealFrom() {
		return dealFrom;
	}

	public Date getDealTo() {
		return dealTo;
	}
	
	public TaobaoProductItemDealsParser(Date dealFrom, Date dealTo) {
		this.dealFrom = dealFrom;
		this.dealTo = dealTo;
	}
	
	static Logger log = Logger.getLogger(TaobaoProductItemDealsParser.class);
	
	public DealInfo parse(Document dealsDoc) {
		DealInfo page = new DealInfo();
		if (null == dealsDoc) {
			return page;
		}
		Elements monthRecordDetails = dealsDoc.select("table > tbody > tr");
		int size = monthRecordDetails.size();
		if (size == 0) {
			return page;
		}
		log.info(String.format("month record details (count: %s): %s",  size, null));
		Iterator<Element> iterator = monthRecordDetails.iterator();
		Element skiped = iterator.next(); //FIXME support skip first row or not skip
		int count = 0;
		
//		Date dateExcludeFrom = parseDate("2012-11-11 00:00:00");
//		Date dateExcludeTo = parseDate("2012-11-11 23:59:59");
		for (Iterator<Element> iter = iterator; iterator.hasNext();) {
			Element element = iter.next();
			Elements dealRecord = element.select("tr > td > em");
			if (dealRecord.size() < 1) {
				continue;
			}
			Element priceElement = dealRecord.get(1);
			String price = priceElement.text();
			
			String dealDateStr = priceElement.parent().nextElementSibling().nextElementSibling().text();
			Date dealDateTime = DateUtils.parseDate(dealDateStr);
			page.setLastDealDate(dealDateTime);
			//FIXME
			if ((null != this.getDealFrom() && dealDateTime.before(this.getDealFrom())) 
					|| (null != this.getDealTo() && dealDateTime.after(this.getDealTo()))) {
//					 || (dealDateTime.before(dateExcludeTo) && dealDateTime.after(dateExcludeFrom))) {
				continue;
			}
			log.info(String.format("month record deal date: %s", dealDateStr));
			log.debug(String.format("month record price: %s", price));
			String itemCount = priceElement.parent().nextElementSibling().text();
			count += Integer.parseInt(itemCount);
			Deal deal = new Deal();
			deal.setSalesAmount(StringUtils.isEmpty(itemCount) ? 0 : Integer.parseInt(itemCount));
			deal.setUnitPrice(StringUtils.isEmpty(price) ? null : new BigDecimal(price));
			deal.setDate(dealDateTime); // 2012-09-22 19:22:16
			page.add(deal);
		}
		log.info(String.format("month record total: %s", count));
		page.setItemCount(count);
		return page;
	}

	
	
	
}

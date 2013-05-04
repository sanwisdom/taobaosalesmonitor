package com.sanwisdom.taobao.monitor.htmlparser;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlParser {
	
	public Document parse(String htmlContent) {
		if (StringUtils.isEmpty(htmlContent)) {
			return null;
		}
		return Jsoup.parse(htmlContent); 
	}
}

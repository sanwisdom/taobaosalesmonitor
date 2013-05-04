package com.sanwisdom.taobao.monitor.httpagent;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.log4j.Logger;
import org.junit.Test;

public class ClientAgentTest {

	static Logger log = Logger.getLogger(ClientAgentTest.class);

	@Test
	public void testVisitCatalogPage() throws ClientProtocolException, IOException {
		Assert.assertNotNull(new ClientAgent().visitAndClose("http://stanley.tmall.com/search.htm", null));
	}
	
	@Test
	public void testContentType() {
		String url = "http://mdskip.taobao.com/extension/dealRecords.htm?isOffline=&pageSize=15&isStart=false&itemType=b&ends=1366464971000&starts=1365860171000&itemId=12577540898&soldTotalNum=7263&sellerNumId=729570505&isFromDetail=yes&totalSQ=73660&sbn=e351726d417aee1a63e3a0583f2de74a&isSecKill=false&isOriginPrice=false&bidPage=4&callback=TShop.mods.DealRecord.reload";
		HttpClient httpclient = new DefaultHttpClient();
		String responseBody = null;
		HttpGet httpget = new HttpGet(url);
		log.info("executing request " + httpget.getURI());
		// Create a response handler
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
			httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
			httpget.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
			httpget.addHeader("Referer", "http://detail.tmall.com/item.htm?id=12577540898");
			responseBody = httpclient.execute(httpget, responseHandler);
		} catch (Exception e) {
        	log.error(e);
		} 
		log.info("response " + responseBody);
	}
}

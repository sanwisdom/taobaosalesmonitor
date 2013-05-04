package com.sanwisdom.taobao.monitor.httpagent;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
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

public class ClientAgent {

	static Logger log = Logger.getLogger(ClientAgent.class);
	
	public String visitAndClose(String url, String referer) {
		String responseBody = null;
		HttpClient httpclient = new DefaultHttpClient();
        try {
            responseBody = visitKeepConnection(url, referer, httpclient, Integer.valueOf(3));
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
        return responseBody;
	}
	
	// TODO 1. time out retry or compensation logic
	// TODO 2. filter image
	/**
	 * @param url
	 * @param httpclient
	 * @param referer "http://detail.tmall.com/item.htm?id=12577540898"
	 * @param retryCountDown
	 * @return
	 */
	public String visitKeepConnection(String url, String referer, HttpClient httpclient, Integer retryCountDown) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			throw new RuntimeException(e1);
		}
		String responseBody = null;
		HttpGet httpget = new HttpGet(url);
		log.debug("executing request " + httpget.getURI());
		// Create a response handler
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
			httpget.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
			if (StringUtils.isNotEmpty(referer)) {
				httpget.addHeader("Referer", referer);
			}
			responseBody = httpclient.execute(httpget, responseHandler);
		} catch (ClientProtocolException e) {
        	log.error(e);
		} catch (IOException e) {
			log.info(String.format("%s and retrying...(%s count down): %s", e.getMessage(), String.valueOf(retryCountDown), url));
			retryCountDown = retryCountDown - 1;
			if (0 != retryCountDown && null != (responseBody = visitKeepConnection(url, referer, httpclient, retryCountDown))) {
				return responseBody;
			} else {
				log.error(e);
				log.error(String.format("%s: %s", e.getMessage(), url));
			}
		} 
//		log.info("----------------------------------------");
//		log.info(responseBody);
//		log.info("----------------------------------------");
		return responseBody;
	}
}

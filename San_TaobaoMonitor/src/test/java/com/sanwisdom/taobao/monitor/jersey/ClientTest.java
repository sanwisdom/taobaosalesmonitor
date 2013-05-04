package com.sanwisdom.taobao.monitor.jersey;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ClientTest {

	@Test
	public void test() {
		try {
			 
			Client client = Client.create();
	 
			WebResource webResource = client
			   .resource("http://mdskip.taobao.com/extension/dealRecords.htm?isOffline=&pageSize=15&isStart=true&itemType=b&ends=1366464971000&starts=1365860171000&itemId=12577540898&soldTotalNum=7263&sellerNumId=729570505&isFromDetail=yes&totalSQ=73660&sbn=e351726d417aee1a63e3a0583f2de74a&isSecKill=false&isOriginPrice=false&bidPage=3&callback=TShop.mods.DealRecord.reload");
	 
			ClientResponse response = webResource.accept("application/x-javascript") //"application/json"
	                   .get(ClientResponse.class);
	 
			if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}
	 
			String output = response.getEntity(String.class);
	 
			System.out.println("Output from Server .... \n");
			System.out.println(output);
	 
		  } catch (Exception e) {
	 
			e.printStackTrace();
	 
		  }
	}
}

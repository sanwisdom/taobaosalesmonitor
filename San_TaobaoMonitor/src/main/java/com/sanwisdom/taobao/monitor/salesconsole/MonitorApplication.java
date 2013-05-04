package com.sanwisdom.taobao.monitor.salesconsole;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.codehaus.jackson.util.DefaultPrettyPrinter;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.navigation.ShopVisitor;
import com.sanwisdom.taobao.monitor.persistence.impl.ExcelProductDao;
import com.sanwisdom.taobao.monitor.utils.DateUtils;


public class MonitorApplication {
	
	static Logger log = Logger.getLogger(MonitorApplication.class);


	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean isReadDb = false;
		List<Shop> shops = null;
		if (!isReadDb) {
			List<String> shopUrls = new ArrayList<String>();
			ObjectMapper mapper = new ObjectMapper();
			try {
				URL url = Resources.getResource(GlobalConstants.SHOP_CONFIGURATION);
				String text = Resources.toString(url, Charsets.UTF_8);
//				shopUrls = mapper.reader(ArrayList.class).readValue(new File(GlobalConstants.SHOP_CONFIGURATION));
				shopUrls = mapper.reader(ArrayList.class).readValue(text);
			} catch (IOException e) {
				log.error(e);
			}
			Date dealFrom = DateUtils.parseDate("2013-03-19 00:00:00");
			Date dealTo = DateUtils.parseDate("2013-04-19 23:59:59");
			shops = new ShopVisitor(dealFrom, dealTo).visit(shopUrls);
			mapper = new ObjectMapper();
			String fileName = GlobalConstants.TOTAL_SHOP_INFO_TOP; 
			log.debug(fileName);
			File resultFile = new File(fileName);
			try {
				mapper.writer(new DefaultPrettyPrinter()).writeValue(resultFile, shops);
			} catch (Exception e) {
				log.error(e);
			}
		} else {
			ObjectMapper mapper = new ObjectMapper();
			try {
				shops = mapper.readValue(new File(GlobalConstants.TOTAL_SHOP_INFO_TOP), new TypeReference<List<Shop>>(){});
			} catch (IOException e) {
				log.error(e);
			}
		}
		
		try {
			new ExcelProductDao().create(shops);
		} catch (IOException e) {
			log.error(e);
		}
		log.info("Completed");
	}
	
	public static class ShopSettings {
		
	}
	


}

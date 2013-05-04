package com.sanwisdom.taobao.monitor.datahelper;

import java.io.File;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.sanwisdom.taobao.monitor.businessobject.Shop;

public class TestDataHelper {

	static Logger log = Logger.getLogger(TestDataHelper.class);
	
	public static Shop read(String fileName) {
		Shop info = null;
		log.debug(fileName);
		File resultFile = new File(fileName);
		try {
			ObjectMapper mapper = new ObjectMapper();
			info = mapper.reader(Shop.class).readValue(resultFile);
		} catch (Exception e) {
			log.error(e);
		}
		return info;
	}
}

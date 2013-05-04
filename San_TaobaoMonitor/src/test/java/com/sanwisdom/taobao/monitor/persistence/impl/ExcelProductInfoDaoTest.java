package com.sanwisdom.taobao.monitor.persistence.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.constants.GlobalConstants;
import com.sanwisdom.taobao.monitor.datahelper.TestDataHelper;

public class ExcelProductInfoDaoTest {

	static Logger log = Logger.getLogger(ExcelProductInfoDaoTest.class);
	
	@Test
	public void testPersist() throws IOException {
		Shop info = TestDataHelper.read(GlobalConstants.SHOP_INFO_STANLEY);
		
		ExcelProductDao dao = new ExcelProductDao();
		dao.create(info);
	}
	
	@Test
	public void testPersist_many() throws IOException {
		List<Shop> shops = new ArrayList<Shop>();
		Shop infoStanley = TestDataHelper.read(GlobalConstants.SHOP_INFO_STANLEY);
		shops.add(infoStanley);
		Shop infoAtomic = TestDataHelper.read(GlobalConstants.SHOP_INFO_ATOMIC);
		shops.add(infoAtomic);
		Shop infoExploit = TestDataHelper.read(GlobalConstants.SHOP_INFO_EXPLOIT);
		shops.add(infoExploit);
		
		ExcelProductDao dao = new ExcelProductDao();
		dao.create(shops);
	}

}

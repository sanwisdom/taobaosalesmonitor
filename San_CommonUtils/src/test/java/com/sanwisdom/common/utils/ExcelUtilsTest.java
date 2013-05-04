package com.sanwisdom.common.utils;


import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class ExcelUtilsTest {

	static Logger logger = Logger.getLogger(ExcelUtilsTest.class);
	
//	@Test
	public void testReadExcel() throws IOException {
		String fileName = "workbook.xls"; 
		String userDir = System.getProperty("user.dir");
		String path = this.getClass().getCanonicalName();
		logger.debug(userDir);
		logger.debug(path);
		URL fileNamePath = this.getClass().getResource(fileName);
		logger.info(fileNamePath.getPath());
		Assert.assertNotNull(ExcelUtils.read(fileNamePath.getPath()));
	}
	
	@Test
	public void testEmpty() {
		
	}
}

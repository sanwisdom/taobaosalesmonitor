package com.sanwisdom.taobao.seo.reader;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.sanwisdom.common.utils.ExcelUtils;
import com.sanwisdom.taobao.seo.AbstractKeyWordGeneratorTest;

public class ExcelTemplateReaderTest extends AbstractKeyWordGeneratorTest  {

	static Logger logger = Logger.getLogger(ExcelTemplateReaderTest.class);
	
	@Test
	public void testReadExcel() throws IOException {
		String fileName = "workbook.xls"; 
		String userDir = System.getProperty("user.dir");
		String path = this.getClass().getCanonicalName();
		logger.debug(userDir);
		logger.debug(path);
		Assert.assertNotNull(ExcelUtils.read(userDir + "\\bin\\" + fileName));
	}
}

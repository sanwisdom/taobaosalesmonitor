package com.sanwisdom.taobao.seo;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;

import com.sanwisdom.common.utils.ExcelUtils;

public class AbstractKeyWordGeneratorTest {

	protected static final String[] MAIN_KEYWORD_TEST_STRINGS = new String[] {"请帖", "请柬", "喜帖"};

	private String excelName = null;
	
	protected String getExcelName() {
		return this.excelName;
	}
	
	private Workbook wb = null;

	static Logger logger = Logger.getLogger(AbstractKeyWordGeneratorTest.class);

	protected Workbook getWorkbook() {
		return this.wb;
	}

	@Before
	public void setup() throws IOException {
		String fileName = "workbook_bug2.xls";
		String userDir = System.getProperty("user.dir");
		String path = this.getClass().getCanonicalName();
		logger.debug(userDir);
		logger.debug(path);
		excelName = userDir + "\\bin\\" + fileName;
		wb = ExcelUtils.read(excelName);
	}

}

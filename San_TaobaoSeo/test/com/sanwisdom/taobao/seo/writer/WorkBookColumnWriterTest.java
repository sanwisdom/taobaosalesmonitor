package com.sanwisdom.taobao.seo.writer;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import com.sanwisdom.common.utils.ExcelUtils;
import com.sanwisdom.taobao.seo.AbstractKeyWordGeneratorTest;
import com.sanwisdom.taobao.seo.generator.KeywordRobot;

public class WorkBookColumnWriterTest extends AbstractKeyWordGeneratorTest {

	private static final String GENERATED_KEY_WORD = "生成关键字组合";
	static Logger logger = Logger.getLogger(WorkBookColumnWriterTest.class);
	
	
	@Test
	public void testWriteColumn() throws IOException {
		Sheet sheet = this.getWorkbook().getSheetAt(0);
		Map<String, String> keywords = new KeywordRobot().shuffle(sheet);
		new WorkBookColumnWriter().writeColumnByHeaderValue(keywords.keySet(), sheet, GENERATED_KEY_WORD);
		ExcelUtils.write(this.getWorkbook(), this.getExcelName());
	}

}

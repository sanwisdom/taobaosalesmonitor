package com.sanwisdom.taobao.seo.reader;

import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.sanwisdom.taobao.seo.AbstractKeyWordGeneratorTest;
import com.sanwisdom.taobao.seo.reader.WorkBookColumnReader;


public class WorkBookColumnReaderTest extends AbstractKeyWordGeneratorTest {

	
	
	private static final String MAIN_KEY_WORD = "主关键字";
	static Logger logger = Logger.getLogger(WorkBookColumnReaderTest.class);
	
	
	@Test
	public void testReadColumn() throws IOException {
		Set<String> columnData = new WorkBookColumnReader().readColumnByHeaderValue(
				this.getWorkbook().getSheetAt(0), MAIN_KEY_WORD);
		Assert.assertArrayEquals(MAIN_KEYWORD_TEST_STRINGS, columnData.toArray());
	}

}

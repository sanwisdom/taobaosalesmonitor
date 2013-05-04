package com.sanwisdom.taobao.seo.generator;

import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.sanwisdom.taobao.seo.AbstractKeyWordGeneratorTest;
import com.sanwisdom.taobao.seo.generator.KeywordRobot;

public class KeyWordRobotTest extends AbstractKeyWordGeneratorTest {

	static Logger logger = Logger.getLogger(KeyWordRobotTest.class);
	
	private KeywordRobot keywordRobot = new KeywordRobot();
	
	public KeywordRobot getRobot() {
		return this.keywordRobot;
	}
	
	@Test
	public void testGetMainKeywords() {
		Set<String> actuals = this.getRobot().getMainKeywords(this.getWorkbook().getSheetAt(0));
		Assert.assertArrayEquals(MAIN_KEYWORD_TEST_STRINGS, actuals.toArray());
	}

	@Test
	public void testGetSubAdjectives() {
		Set<Set<String>> actuals = this.getRobot().getSubAdjectives(this.getWorkbook().getSheetAt(0));
		for (Set<String> set : actuals) {
			logger.debug(set.toString());
		}
		
	}
	
	@Test
	public void testShuffle() {
		this.getRobot().shuffle(this.getWorkbook().getSheetAt(0));
	}
}

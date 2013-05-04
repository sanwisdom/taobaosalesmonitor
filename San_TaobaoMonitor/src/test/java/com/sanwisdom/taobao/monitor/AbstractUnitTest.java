package com.sanwisdom.taobao.monitor;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

public class AbstractUnitTest {
	
	static Logger log = Logger.getLogger(AbstractUnitTest.class);
	
	private long t1 = 0;

	@Before
	public void setup() {
		log.info("Starting...");
		t1 = System.currentTimeMillis();
	}
	
	@After
	public void teardown() {
		log.info("Complete");
		log.info(String.format("Total time: %s", String.valueOf((System.currentTimeMillis() - t1) / 1000)));
	}
}

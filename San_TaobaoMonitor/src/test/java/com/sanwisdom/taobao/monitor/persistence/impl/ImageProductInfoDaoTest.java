package com.sanwisdom.taobao.monitor.persistence.impl;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.navigation.ProductVisitor;
import com.sanwisdom.taobao.monitor.salesconsole.GlobalConstants;

public class ImageProductInfoDaoTest {
	
	static Logger log = Logger.getLogger(ImageProductInfoDaoTest.class);

	@Test
	public void test_product() throws IOException {
		String thumbnail = "http://img03.taobaocdn.com/bao/uploaded/i3/T1R8q3XkhcXXXq3q72_042913.jpg_160x160.jpg";
		long id = 8499825615L;
		String shopName = "atomic.tmall.com"; 
		new ImageProductDao().saveThumbnail(id, thumbnail, shopName);
		File input = new File(GlobalConstants.TEMP_THUMBNAIL_DIR + "//" + shopName + "//" + id + ".jpg");
		log.info("Absolution Path: " + input.getAbsolutePath());
		log.info("Canonical Path: " + input.getCanonicalPath());
		Assert.assertNotNull(ImageIO.read(input));
	}
	
	@Test
	public void test_shop() throws IOException {
		String link = "http://stanley.tmall.com/search.htm"; 
		Shop info = new ProductVisitor(null, null).visitProductSales(link);
		new ImageProductDao().create(info);
	}
	

}

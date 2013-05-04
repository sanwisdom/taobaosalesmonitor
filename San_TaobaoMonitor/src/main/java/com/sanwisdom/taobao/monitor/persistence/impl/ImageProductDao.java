package com.sanwisdom.taobao.monitor.persistence.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.sanwisdom.common.utils.IoUtils;
import com.sanwisdom.taobao.monitor.businessobject.Product;
import com.sanwisdom.taobao.monitor.businessobject.ProductSummary;
import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.persistence.api.ShopDao;
import com.sanwisdom.taobao.monitor.salesconsole.GlobalConstants;

public class ImageProductDao implements ShopDao {

	static Logger log = Logger.getLogger(ImageProductDao.class);
	
	public void create(final Shop info) throws IOException {
		List<Product> products = info.getProducts();
		for (int i = 0; i < products.size(); i++) {
			ProductSummary s = products.get(i).getSummary();
			String thumbnail = s.getThumbnail();
			long id = s.getProductId();
			saveThumbnail(id, thumbnail, info.getUrl());
		}
	}
	
	public byte[] read(long id, String thumbnail, String shopName) throws IOException {
		File file = new File(GlobalConstants.TEMP_THUMBNAIL_DIR + "//" + shopName + "//" + id + ".jpg");
		if (file.exists()) {
			return IoUtils.readImageFile(file);
		}
		this.saveThumbnail(id, thumbnail, shopName);
		return IoUtils.readImageFile(file);
	}

	public byte[] read(long id, String thumbnail) throws IOException {
		File file = new File(GlobalConstants.TEMP_THUMBNAIL_DIR + "//" + id + ".jpg");
		if (file.exists()) {
			return IoUtils.readImageFile(file, 80);
		}
		this.saveThumbnail(id, thumbnail);
		return IoUtils.readImageFile(file);
	}
	
	
	
	protected void saveThumbnail(long id, String thumbnail, String shopName) throws IOException {
		File dir = new File(GlobalConstants.TEMP_THUMBNAIL_DIR + "//" + shopName);
		saveThumbnail(id, thumbnail, dir);
	}

	private void saveThumbnail(long id, String thumbnail, File dir)
			throws IOException {
		log.info(dir.getCanonicalPath());
		if (!dir.exists()) {
			this.createShopThumbnailDir(dir);
		}
		File file = new File(dir.getCanonicalPath() + "//" + id + ".jpg");
		log.info(file.getCanonicalFile());
		if (!file.exists()) {
			log.info("thumbnail: " + thumbnail);
			BufferedImage image;
			try {
				image = ImageIO.read(new URL(thumbnail));
			} catch (MalformedURLException e) {
				log.warn(String.format("%s: thumbnail: %s", e.getMessage(), thumbnail));
				return;
			}
			file.createNewFile();
			// write image
		    ImageIO.write(image, "jpg", file);
		}
	}
	
	protected void saveThumbnail(long id, String thumbnail) throws IOException {
		File dir = new File(GlobalConstants.TEMP_THUMBNAIL_DIR);
		saveThumbnail(id, thumbnail, dir);
	}

	private File createShopThumbnailDir(File dir) throws IOException {
		if (dir.exists()) {
			if (!dir.isDirectory()) {
				dir.delete();
				dir.mkdirs();
			}
		}
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

//	private void buildDirWithParent(File dir) throws IOException {
//		if (!dir.exists()) {
//			while (!dir.getParentFile().exists()) {
//				dir = dir.getParentFile();
//				buildDirWithParent(dir);
//			}
//			dir.mkdirs();
//		}
//	}

	public void create(final List<Shop> infos) throws IOException {
		for (Shop shopInfo : infos) {
			this.create(shopInfo);
		}
	}

	public void update(final Shop info) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void update(final List<Shop> infos) throws IOException {
		throw new UnsupportedOperationException();
	}

	public Shop findByPrimaryKey(Long id) throws IOException {
		throw new UnsupportedOperationException();
	}

	public List<Shop> findByPrimaryKeys(List<Long> id) throws IOException {
		throw new UnsupportedOperationException();
	}

	public List<Shop> findAll() throws IOException {
		throw new UnsupportedOperationException();
	}

	public void delete(Long id) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void delete(Shop info) throws IOException {
		throw new UnsupportedOperationException();
	}

}

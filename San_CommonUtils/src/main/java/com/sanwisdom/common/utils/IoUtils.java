package com.sanwisdom.common.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

public class IoUtils {

	public static byte[] read(String urlAddress) throws IOException {
		URL url = null;
		byte[] imageBytes = null;
		try {
			url = new URL(urlAddress);
		} catch (MalformedURLException e) {
			throw new IOException(e);
		}
		InputStream is = null;
		try {
			is = url.openStream();
			imageBytes = IOUtils.toByteArray(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return imageBytes;
	}
	
	public static byte[] readImageFile(File file) throws IOException {
		BufferedImage originalImage = ImageIO.read(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, "jpg", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}
	
	public static byte[] readImageFile(File file, float scale) throws IOException {
		BufferedImage originalImage = ImageIO.read(file);
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		Image scaledImage = originalImage.getScaledInstance((int)(width * scale), (int)(height * scale), Image.SCALE_DEFAULT);
		BufferedImage tag = new BufferedImage((int) (width * scale),
				(int) (height * scale), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = tag.createGraphics();
		g.drawImage(scaledImage, 0, 0, null); // 绘制缩小后的图
		g.dispose();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(tag, "jpg", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}
	
	public static byte[] readImageFile(File file, int maxPixelOfEdge) throws IOException {
		BufferedImage originalImage = ImageIO.read(file);
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		float scale = 1.0f; 
		if (width > height) {
			scale = ((float) maxPixelOfEdge) / ((float) width);
		} else {
			scale = ((float) maxPixelOfEdge) / ((float) height);
		}
		Image scaledImage = originalImage.getScaledInstance((int)(width * scale), (int)(height * scale), Image.SCALE_DEFAULT);
		BufferedImage tag = new BufferedImage((int) (width * scale),
				(int) (height * scale), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = tag.createGraphics();
		g.drawImage(scaledImage, 0, 0, null); // 绘制缩小后的图
		g.dispose();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(tag, "jpg", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}
}

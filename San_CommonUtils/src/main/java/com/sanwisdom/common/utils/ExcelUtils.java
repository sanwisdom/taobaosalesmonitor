package com.sanwisdom.common.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtils {

	static Logger log = Logger.getLogger(ExcelUtils.class);
	
	public static Workbook read(String fileName) throws IOException {
		InputStream inp = new FileInputStream(fileName);
		Workbook wb = new HSSFWorkbook(inp);
		return wb;
	}
	
	public static Workbook read(InputStream inp) throws IOException {
		Workbook wb = new HSSFWorkbook(inp);
		return wb;
	}

	public static void write(Workbook wb, String fileName) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(fileName);
		wb.write(fileOut);
		fileOut.close();
	}
	
	public static class Factory {
		public static Workbook createWorkbook() {
			return new HSSFWorkbook();
		}
	}
	
	@Deprecated
	public static String getLocalDefaultTempDir() {
		String fileName = "workbook.xls";
		String userDir = System.getProperty("user.dir");
		log.debug(userDir);
		return userDir + "\\bin\\" + fileName;
	}
	
	
	public static void docorateLinkStyle(Workbook wb, Cell cell) {
		CellStyle hlinkStyle = wb.createCellStyle();
	    Font hlinkFont = wb.createFont();
	    hlinkFont.setUnderline(Font.U_SINGLE);
	    hlinkFont.setColor(IndexedColors.BLUE.getIndex());
	    hlinkStyle.setFont(hlinkFont);
	    cell.setCellStyle(hlinkStyle);
	}
}

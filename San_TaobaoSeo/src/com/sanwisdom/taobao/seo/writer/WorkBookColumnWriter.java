package com.sanwisdom.taobao.seo.writer;

import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class WorkBookColumnWriter {

	static Logger logger = Logger.getLogger(WorkBookColumnWriter.class);
	
	public void writeColumnByHeaderValue(Set<String> keywords, Sheet sheet, String headerTitle) {
		Row header = sheet.getRow(0);
		int colIndex = getColumnIndexByHeaderValue(headerTitle, header);
		if (colIndex != -1) {
			int i = 0;
			assert sheet.getLastRowNum() < 2;
			for (String keyword : keywords) {
				 i++;
				 Row row = sheet.getRow(i);
				 if (null == row) {
					 row = sheet.createRow(i);
				 }
				Cell cell = row.getCell(colIndex);
				if (cell == null) {
					cell = row.createCell(colIndex);
				}
				logger.debug("Writing.." + keyword);
				cell.setCellValue(keyword);
			}
		}
	}

	protected int getColumnIndexByHeaderValue(String headerTitle,
			Row header) {
		int index = -1;
		int i = 0;
		for (Cell cell : header) {
			if (headerTitle.equals(cell.getStringCellValue())) {
				index = i;
				break;
			}
			i++;
		}
		return index;
	}
}

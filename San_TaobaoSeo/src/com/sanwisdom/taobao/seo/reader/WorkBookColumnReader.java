package com.sanwisdom.taobao.seo.reader;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class WorkBookColumnReader {

	public static String EMPTY = "";
	
	static Logger logger = Logger.getLogger(WorkBookColumnReader.class);
	
	public Set<String> readColumnByHeaderValue(Sheet sheet, String headerTitle) {
		Row header = sheet.getRow(0);
		Set<String> columnData = new LinkedHashSet<String>();
		int colIndex = getColumnIndexByHeaderValue(headerTitle, header);
		if (colIndex != -1) {
			int i = 0;
			assert sheet.getLastRowNum() < 2;
			for (Row row : sheet) {
				if (i == 0) {
					i++;
					continue;
				}
			    Cell cell = row.getCell(colIndex);
			    if (null != cell) {
			    	String stringCellValue = cell.getStringCellValue();				    
				    if (null != stringCellValue && !EMPTY.equals(stringCellValue.trim())) {
						columnData.add(stringCellValue);
						logger.debug(stringCellValue);
				    }
			    }
			    i++;
			}
		}
		return columnData;
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

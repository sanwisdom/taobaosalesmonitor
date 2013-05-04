package com.sanwisdom.taobao.monitor.persistence.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.sanwisdom.common.utils.ExcelUtils;
import com.sanwisdom.taobao.monitor.businessobject.Product;
import com.sanwisdom.taobao.monitor.businessobject.ProductSummary;
import com.sanwisdom.taobao.monitor.businessobject.Shop;
import com.sanwisdom.taobao.monitor.persistence.api.ShopDao;

public class ExcelProductDao implements ShopDao {

	static Logger log = Logger.getLogger(ExcelProductDao.class);
	
	private String getProductFileName(String productName) {
		return System.getProperty("user.dir") 
				+ "\\test\\" 
				+ String.format("workbook_%s_%s.xls", productName, new SimpleDateFormat("yyyyMMddHHmmSSsss").format(new Date()));
	}
	
	private String getPivotFileName() {
		Date now = new Date();
		return System.getProperty("user.dir") 
				+ "\\test\\" 
				+ String.format("workbook_%s_%s.xls", new SimpleDateFormat("截止MM月dd日HH时mm分").format(now), new SimpleDateFormat("yyyyMMddHHmmSSsss").format(now));
	}
	
	public void create(Shop info) throws IOException {
		Workbook wb = ExcelUtils.Factory.createWorkbook();
		Sheet sheet = wb.createSheet(info.getUrl());
		createHeaderRow(sheet, 1);
		List<Product> products = info.getProducts();
		for (int i = 0; i < products.size(); i++) {
			Product p = products.get(i);
			List<String> data = getDetailRow(i + 1, p, new LinkedList<String>());
			createDataRow(wb, sheet, data, i + 1);
		}
		ExcelUtils.write(wb, this.getProductFileName(info.getUrl()));
	}

	private List<String> getDetailRow(int i, Product p, List<String> data) {
		if (null == data) {
			data = new LinkedList<String>(); 
		}
		ProductSummary s = p.getSummary();
		data.add(String.valueOf(i));
		data.add(s.getThumbnail());
		data.add(s.getTitle());
		data.add(String.valueOf(s.getMonthlySalesAmount()));
		data.add(String.valueOf(s.getPrice()));
		log.info("单价: " + s.getPrice());
		if (0 == s.getMonthlySalesAmount()) {
			data.add(String.valueOf(s.getPrice()));	
		} else {
			data.add(String.valueOf(s.getSalesTotal().divide(new BigDecimal(s.getMonthlySalesAmount()), 2, RoundingMode.HALF_UP)));
		}
		data.add(String.valueOf(s.getSalesTotal()));
		data.add(String.valueOf(s.getProductId()) + "###" + s.getLink());
		return data;
	}
	

	public void create(List<Shop> infos) throws IOException {
		Workbook wb = ExcelUtils.Factory.createWorkbook();
		Sheet sheet = wb.createSheet();
		int numberOfShops = infos.size();
		createHeaderRow(sheet, numberOfShops);
		List<Product> products = infos.get(0).getProducts();
		int maxNumberOfProducts = getMaxNumberOfProduct(infos);
		for (int i = 0; i < maxNumberOfProducts; i++) {
			List<String> data = null;
			for (int j = 0; j < infos.size(); j++) {
				Shop s = infos.get(j);
				Product p = null;
				if (i < s.getProducts().size()) {
					p = s.getProducts().get(i);
				} else {
					p = new Product();
				}
				data = getDetailRow(i + 1, p, data);
			}
			createDataRow(wb, sheet, data, i + 1);
		}
		
		ExcelUtils.write(wb, this.getPivotFileName());
	}


	private int getMaxNumberOfProduct(List<Shop> infos) {
		int max = 0;
		for (Shop shop : infos) {
			if (shop.getProducts().size() > max) {
				max = shop.getProducts().size();
			}
		}
		return max;
	}

	private Row createDataRow(Workbook wb, Sheet sheet, List<String> data, int rowIndex) {
		Row row = null;
		if (data.isEmpty()) {
			return null;
		}
		row = sheet.createRow(rowIndex);
		row.setHeightInPoints(65f);
		int thumbIdOffset = Header.THUMBNAIL.ordinal() - Header.ID.ordinal();
		for (int i = 0; i < data.size(); i++) {
			Cell cell = row.getCell(i);
			if (cell == null) {
				cell = row.createCell(i);
			}
			int type = i % (getHeaders().size());
			Header header = Header.values()[type];
			log.debug(header.getName());
			if (Header.ID.equals(header)) {
				String idAndAddress = data.get(i);
				header.createCell(wb, cell, idAndAddress);
				String id = parseId(idAndAddress);
				String address = parseAddress(idAndAddress);
				if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(address)) {
					cell.setCellValue(id);
					//URL
					ExcelUtils.docorateLinkStyle(wb, cell);
					CreationHelper createHelper = wb.getCreationHelper();
				    Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);
					link.setAddress(address);
				    cell.setHyperlink(link);
				}
			} else if (Header.THUMBNAIL.equals(header)) {
				header.createCell(wb, cell, data.get(i));
				String idAndAddress = data.get(i - thumbIdOffset);
				String id = parseId(idAndAddress);
				String thumbnail = data.get(i);
				if (StringUtils.isEmpty(id)) {
					id = "0";
					thumbnail = null;
				}
				this.addThumbnail(wb, sheet, Long.parseLong(id), thumbnail, i, rowIndex);
			} else {
				header.createCell(wb, cell, data.get(i));
			}
		}
		return row;
	}
	
	private String parseId(String idAndAddress) {
		String[] idAndUrls = idAndAddress.split("###");
		String id = idAndUrls[0];
		return id;
	}
	
	private String parseAddress(String idAndAddress) {
		String[] idAndUrls = idAndAddress.split("###");
		String address = idAndUrls[1];
		return address;
	}

	private Row createHeaderRow(Sheet sheet, int numberOfShops) {
		Row headerRow = sheet.getRow(0);
		if (null == headerRow) {
			headerRow = sheet.createRow(0);
		}
		createColumnHeaders(sheet, headerRow, numberOfShops);
		return headerRow;
	}

	private void createColumnHeaders(Sheet sheet, Row headerRow, int numberOfShops) {
		for (int i = 0; i < numberOfShops; i++) {
			for (Entry<Integer, String> header : getHeaders().entrySet()) {
				int col = header.getKey() + i * getHeaders().size();
				String text = header.getValue();
				createHeader(headerRow, col, text);
				// adjust column size;
				if (Header.THUMBNAIL.equals(Header.values()[col % Header.values().length])) {
					sheet.setColumnWidth(col, 256*12);
				} 
				if (Header.NAME.equals(Header.values()[col % Header.values().length])) {
					sheet.setColumnWidth(col, 256*64);
				} 
			}
		}
	}

	private void createHeader(Row header, int col, String text) {
		Cell indexH = header.createCell(col);
		indexH.setCellValue(text);
	}
	
	public static volatile Map<Integer, String> headers = null;
	
	public static Map<Integer, String> getHeaders() {
        if (null == headers) {
            synchronized(ExcelProductDao.class) {
                if (headers == null) {
                	headers = new LinkedHashMap<Integer, String>();
                	for (Header header : Header.values()) {
            			headers.put(header.ordinal(), header.getName());
            		}
                }
                    
            }
        }
        return headers;
    }
	
	public enum Header {
		INDEX("销量Top"),
		THUMBNAIL("产品图"),
		NAME("品名"),
		AMOUNT("月销"),
		UNITPRICE("单价"),
		SPEC("平均成交价"),
		TOTAL("销售总额"),
		ID("ID");
		
		Header(String name) {
			this.name = name;
		}
		
		private String name;

		public String getName() {
			return this.name;
		}
		
		public void createCell(Workbook wb, Cell cell, String value) {
			switch (this) {
//			case LINK:
//				cell.setCellValue("link");
//				//URL
//				ExcelUtils.docorateLinkStyle(wb, cell);
//				CreationHelper createHelper = wb.getCreationHelper();
//			    Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_URL);
//			    link.setAddress(value);
//			    cell.setHyperlink(link);
//				break;
			case THUMBNAIL:
				break;
			case AMOUNT:
			case UNITPRICE:
			case TOTAL:
				cell.setCellValue(null == value || "null".equals(value) ? 0 : Double.parseDouble(value));
			    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				break;
			default:
				cell.setCellValue(value);
				break;
			}
		}
	}
	
	protected void addThumbnail(Workbook wb, Sheet sheet, 
			long id, String thumbnail, 
			int col, int row) {
		try {
			log.info(String.format("thumbnail url:%s, id: %s, col: %s, row: %s", thumbnail, String.valueOf(id), String.valueOf(col), String.valueOf(row)));
			if (StringUtils.isEmpty(thumbnail)) {
				return;
			}
			//add picture data to this workbook.
			byte[] bytes = new ImageProductDao().read(id, thumbnail);
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			CreationHelper helper = wb.getCreationHelper();
			// Create the drawing patriarch.  This is the top level container for all shapes. 
			Drawing drawing = sheet.createDrawingPatriarch();
			//add a picture shape
			ClientAnchor anchor = helper.createClientAnchor();
			//set top-left corner of the picture,
			//subsequent call of Picture#resize() will operate relative to it
			anchor.setCol1(col);
			anchor.setRow1(row);
			anchor.setAnchorType(ClientAnchor.MOVE_DONT_RESIZE);
			anchor.setDx1(anchor.getDx1() + 5);
			anchor.setDx2(anchor.getDx2() + 5);
			anchor.setDy1(anchor.getDy1() + 5);
			anchor.setDy2(anchor.getDy2() + 5);
			Picture pict = drawing.createPicture(anchor, pictureIdx);

		    //auto-size picture relative to its top-left corner
		    pict.resize();
		} catch (Exception e) {
			log.warn(String.format("%s: thumbnail url:%s, id: %s", e.getMessage(), thumbnail, String.valueOf(id)));
		}
	    //auto-size picture relative to its top-left corner
//	    pict.resize();
	}


	public void update(Shop info) throws IOException {
		throw new UnsupportedOperationException();
	}


	public void update(List<Shop> infos) throws IOException {
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

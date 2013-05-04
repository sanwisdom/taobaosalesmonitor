package com.sanwisdom.taobao.monitor.businessobject;

import java.math.BigDecimal;

public class ProductSummary {
	
	private static String DEFAULT_PRODUCT_TITLE = "未知产品";

	private long taobaoId = 0L;
	
	private String title = DEFAULT_PRODUCT_TITLE;
	
	private String thumbnail = null;
	
	private BigDecimal unitPrice = BigDecimal.ZERO;
	
	private long salesTotalAmount = 0L;
	
	private long monthlySalesAmount = 0L;
	
	private BigDecimal salesTotal = BigDecimal.ZERO;
	
	private double rating = 0;
	
	private String link;

	public long getProductId() {
		return taobaoId;
	}

	public void setProductId(long productId) {
		this.taobaoId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public BigDecimal getPrice() {
		return unitPrice;
	}

	public void setPrice(BigDecimal price) {
		this.unitPrice = price;
	}

	public long getSalesTotalAmount() {
		return salesTotalAmount;
	}

	public void setSalesTotalAmount(long salesTotalAmount) {
		this.salesTotalAmount = salesTotalAmount;
	}


	public long getMonthlySalesAmount() {
		return monthlySalesAmount;
	}

	public void setMonthlySalesAmount(long monthlySalesAmount) {
		this.monthlySalesAmount = monthlySalesAmount;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public BigDecimal getSalesTotal() {
		return salesTotal;
	}

	public void setSalesTotal(BigDecimal salesTotal) {
		this.salesTotal = salesTotal;
	}
	
	
}

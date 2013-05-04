package com.sanwisdom.taobao.monitor.businessobject;

import java.math.BigDecimal;
import java.util.Date;

public class Deal {
	
	private Long id;

	private int salesAmount;
	
	private BigDecimal unitPrice;
	
	private Date date;
	
	private BigDecimal totalPrice;
	
	public Deal() {
		
	}

	public Deal(Long id, BigDecimal unitPrice, int salesAmount,
			BigDecimal totalPrice, Date dealDate) {
		this.id = id;
		this.unitPrice = unitPrice;
		this.salesAmount = salesAmount;
		this.totalPrice = totalPrice;
		this.date = dealDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTotalPrice() {
		totalPrice = unitPrice.multiply(new BigDecimal(salesAmount));
		return totalPrice;
	}

	public int getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(int salesAmount) {
		this.salesAmount = salesAmount;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

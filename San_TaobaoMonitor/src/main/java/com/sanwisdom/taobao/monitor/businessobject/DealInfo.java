package com.sanwisdom.taobao.monitor.businessobject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DealInfo {
	private List<Deal> deals;
	
	private int itemCount = 0;

	private BigDecimal totalSales = BigDecimal.ZERO;
	
	public List<Deal> getDeals() {
		if (null == deals) {
			deals = new LinkedList<Deal>();
		}
		return deals;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}
	
	public void add(DealInfo dealInfo) {
		this.getDeals().addAll(dealInfo.getDeals());
		this.setTotalSales(this.getTotalSales().add(dealInfo.getTotalSales()));
		this.setItemCount(this.getItemCount() + dealInfo.getItemCount());
	}
	
	public void add(Deal deal) {
		if (null != deal) {
			this.getDeals().add(deal);
			this.setTotalSales(this.getTotalSales().add(deal.getTotalPrice()));
			this.setItemCount(this.getItemCount() + deal.getSalesAmount());
		}
	}
	
	private Date lastDealDate;
	
	public void setLastDealDate(Date lastDealDate) {
		this.lastDealDate = lastDealDate;
	}
	
	public Date getLastDealDate() {
		
		return this.lastDealDate;
	}
}

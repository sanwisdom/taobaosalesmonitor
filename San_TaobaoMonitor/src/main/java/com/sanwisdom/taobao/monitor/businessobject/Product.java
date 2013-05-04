package com.sanwisdom.taobao.monitor.businessobject;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({Entity.class, Deal.class})
public class Product extends Entity {

	private Long id;
	
	private ProductSummary summary;
	
	private List<Deal> deals;
	
	public Product() {
		
	}

	public Product(Long id, Long productId, String title, String thumbnail,
			BigDecimal unitPrice, Long salesAmount, double rating, String link) {
		this.id = id;
		this.getSummary().setProductId(productId);
		this.getSummary().setTitle(title);
		this.getSummary().setThumbnail(thumbnail);
		this.getSummary().setPrice(unitPrice);
		this.getSummary().setSalesTotalAmount(null == salesAmount ? 0 : salesAmount.longValue());
		this.getSummary().setRating(rating);
		this.getSummary().setLink(link);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductSummary getSummary() {
		if (null == summary) {
			summary = new ProductSummary();
		}
		return summary;
	}

	public void setSummary(ProductSummary summary) {
		this.summary = summary;
	}

	public List<Deal> getDeals() {
		if (null == deals) {
			deals = new LinkedList<Deal>();
		}
		return deals;
	}

	public void add(DealInfo dealInfo) {
		if (null != dealInfo) {
			this.getDeals().addAll(dealInfo.getDeals());
			this.getSummary().setMonthlySalesAmount(this.getSummary().getMonthlySalesAmount() + dealInfo.getItemCount());
			this.getSummary().setSalesTotal(this.getSummary().getSalesTotal().add(dealInfo.getTotalSales()));
		}
	}
	

	public void addAll(List<Deal> deals) {
		for (Deal deal : deals) {
			this.getDeals().add(deal);
			this.getSummary().setMonthlySalesAmount(this.getSummary().getMonthlySalesAmount() + deal.getSalesAmount());
			this.getSummary().setSalesTotal(this.getSummary().getSalesTotal().add(deal.getTotalPrice()));
		}
	}
	
	public int compareAmount(Product p) {
		assert null != p;
		return Long.valueOf(this.getSummary().getSalesTotalAmount() - p.getSummary().getSalesTotalAmount()).intValue();
	}
	
	public int compareMonthlyAmount(Product p) {
		assert null != p;
		return Long.valueOf(this.getSummary().getMonthlySalesAmount() - p.getSummary().getMonthlySalesAmount()).intValue();
	}
	
	public int compareSales(Product p) {
		assert null != p;
		return p.getSummary().getSalesTotal().compareTo(this.getSummary().getSalesTotal());
	}


}

package com.sanwisdom.taobao.monitor.businessobject;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

@XmlRootElement
@XmlSeeAlso({Entity.class, Product.class})
public class Shop extends Entity {

	private Long id;
	
	private String name;
	
	private String url;
	
	private List<Product> products;
	
	private static String KEY1 = "差价";
	
	private static String KEY2 = "补差";
	
	public Shop() {
		
	}
	
	public Shop(Long id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public String getShopName() {
		if (StringUtils.isNotEmpty(this.name)) {
			return name;
		} else if (!StringUtils.isEmpty(this.getUrl())) {
			int index = this.getUrl().indexOf('.');
			if (index > -1) {
				name = this.getUrl().substring(0, index);			
			}
		}
		return name;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Product> getProducts() {
		if (null == products) {
			products = new LinkedList<Product>();
		}
		return products;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Product> topSalesAmount(int numberOfTops) {
		LinkedList<Product> sorted = new LinkedList<Product>();
		if (this.getProducts().size() == 0) {
			return sorted;
		} else {
			Product product1 = this.getProducts().get(0);
			if (this.getProducts().size() == 1) {
				sorted.add(product1);
				return sorted;
			} else {
				sorted.add(product1);
				Product product2 = this.getProducts().get(1);
				if (product2.compareAmount(product1) > 0) {
					sorted.offerFirst(product2);
				} else {
					sorted.add(product2);
				}	
				for (int i = 2; i < this.getProducts().size(); i++) {
					Product p = this.getProducts().get(i);
					if (p.getSummary().getTitle().contains(KEY1)
							|| p.getSummary().getTitle().contains(KEY2)) {
						break;
					}
					if (sorted.size() != numberOfTops) {
						if (p.compareAmount(sorted.peekFirst()) > 0) {
							sorted.offerFirst(p);
						} else if (p.compareAmount(sorted.peekLast()) < 0) {
							sorted.offerLast(p);
						} else {
							int j = 0;
							for (;j < sorted.size(); j++) {
								if (p.compareAmount(sorted.get(j)) > 0)
								break;
							}
							sorted.add(j, p);
						}
 					} else {
 						int j = 0;
						for (;j < sorted.size(); j++) {
							if (p.compareAmount(sorted.get(j)) > 0)
							break;
						}
						sorted.add(j, p);
						sorted.removeLast();
 					}
				}
			}
		}
		return sorted;
	}
	
	public List<Product> topMonthlySalesAmount(int numberOfTops) {
		LinkedList<Product> sorted = new LinkedList<Product>();
		Set<Long> urls = new HashSet<Long>();
		if (this.getProducts().size() == 0) {
			return sorted;
		} else {
			Product product1 = this.getProducts().get(0);
			if (this.getProducts().size() == 1) {
				sorted.add(product1);
				return sorted;
			} else {
				sorted.add(product1);
				urls.add(product1.getSummary().getProductId());
//				Product product2 = this.getProducts().get(1);
//				if (product2.compareMonthlyAmount(product1) > 0) {
//					sorted.offerFirst(product2);
//				} else {
//					sorted.add(product2);
//				}	
				for (int i = 1; i < this.getProducts().size(); i++) {
					Product p = this.getProducts().get(i);
					if (p.getSummary().getTitle().contains(KEY1)
							|| p.getSummary().getTitle().contains(KEY2)
							|| urls.contains(p.getSummary().getProductId())) {
						continue;
					}
					if (sorted.size() != numberOfTops) {
						if (p.compareMonthlyAmount(sorted.peekFirst()) > 0) {
							sorted.offerFirst(p);
							urls.add(p.getSummary().getProductId());
						} else if (p.compareMonthlyAmount(sorted.peekLast()) < 0) {
							sorted.offerLast(p);
							urls.add(p.getSummary().getProductId());
						} else {
							int j = 0;
							for (;j < sorted.size(); j++) {
								if (p.compareMonthlyAmount(sorted.get(j)) > 0)
								break;
							}
							sorted.add(j, p);
							urls.add(p.getSummary().getProductId());
						}
 					} else {
 						int j = 0;
						for (;j < sorted.size(); j++) {
							if (p.compareMonthlyAmount(sorted.get(j)) > 0)
							break;
						}
						sorted.add(j, p);
						urls.add(p.getSummary().getProductId());
						Product removed = sorted.removeLast();
						urls.remove(removed.getSummary().getProductId());
 					}
				}
			}
		}
		return sorted;
	}
	
	public <T extends Product> List<T> sortSalesTotal(List<T> sortedSalesAmount) {	
		Comparator<T> comparator = new Comparator<T>() {
			public int compare(final T p1, final T p2) {
				return p1.compareSales(p2);
			}
		};
		Collections.sort(sortedSalesAmount, comparator);
		return sortedSalesAmount;
	}
}

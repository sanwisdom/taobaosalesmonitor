package com.sanwisdom.taobao.monitor.businessobject;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Entity {

	private static final String DEFAULT_USER_SANWISDOM = "sanwisdom";

	private String createdBy;
	
	private String updatedBy;
	
	private Date createdAt;
	
	private Date updatedAt;

	public String getCreatedBy() {
		if (createdBy == null) {
			return DEFAULT_USER_SANWISDOM;
		}
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		if (updatedBy == null) {
			return DEFAULT_USER_SANWISDOM;
		}
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}

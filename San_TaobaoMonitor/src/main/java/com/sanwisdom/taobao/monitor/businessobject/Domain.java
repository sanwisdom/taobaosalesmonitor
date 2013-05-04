package com.sanwisdom.taobao.monitor.businessobject;

public enum	Domain {
	TMALL(".tmall.com"),
	TAOBAO(".taobao.com");
	
	private String name = null; 
	
	private Domain(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}

package com.sanwisdom.taobao.monitor.businessobject;

public enum Category {
	TOOLS("五金工具");
	
	private String name;
	
	
	public String getName() {
		return name;
	}


	Category(String name) {
		this.name = name;
	}
}

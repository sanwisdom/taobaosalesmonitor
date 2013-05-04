
package com.sanwisdom.taobao.monitor.web.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sanwisdom.taobao.monitor.businessobject.Shop;

@XmlRootElement
public class ReportResponse extends BaseResponse {


	private List<Shop> data;

	public List<Shop> getData() {
		return data;
	}

	public void setData(List<Shop> data) {
		this.data = data;
	}

	
}

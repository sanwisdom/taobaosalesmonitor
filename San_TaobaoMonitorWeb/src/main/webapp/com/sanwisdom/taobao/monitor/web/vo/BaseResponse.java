package com.sanwisdom.taobao.monitor.web.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BaseResponse {

	private boolean success;
	
	private String message;
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}

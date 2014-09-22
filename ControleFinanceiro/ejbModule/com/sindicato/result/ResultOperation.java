package com.sindicato.result;

import java.io.Serializable;

public class ResultOperation implements Serializable {

	private static final long serialVersionUID = 1L;


	public ResultOperation() { }
	public ResultOperation(boolean success, String message) { 
		this.success = success;  
		this.message = message;
	}
	
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

package com.sindicato.util;

import java.util.HashMap;
import java.util.Map;

public class FiltroDataTable {

	public FiltroDataTable(){
		this.parameters = new HashMap<String, Object>();
	}
	
	private String where;
	private Map<String, Object> parameters;
	
	public String getWhere() {
		return where;
	}
	public Map<String, Object> getParameters() {
		return parameters;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
}

package com.firefly.model;

import java.io.Serializable;

public interface Mi extends Serializable {
	
	public void setRownum(Integer nownum);
	
	public Integer getRownum();
	
	public void setId(String id);
	
	public String getId();
}

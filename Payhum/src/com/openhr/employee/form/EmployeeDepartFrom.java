package com.openhr.employee.form;

import org.apache.struts.action.ActionForm;

public class EmployeeDepartFrom extends ActionForm{

	
	private String  id;
	private String depname;
	private String total;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDepname() {
		return depname;
	}
	public void setDepname(String depname) {
		this.depname = depname;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
	
	
	
}

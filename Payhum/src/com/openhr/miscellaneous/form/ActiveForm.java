package com.openhr.miscellaneous.form;

import org.apache.struts.action.ActionForm;

public class ActiveForm extends ActionForm
{
	private Integer id;
	
	   private String employeeId;
	   private String firstname;
	   private String middlename;
	   private String active;
	   private String terminated;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getTerminated() {
		return terminated;
	}
	public void setTerminated(String terminated) {
		this.terminated = terminated;
	}
	   
}

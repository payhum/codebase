package com.openhr.tax.form;

import org.apache.struts.action.ActionForm;

import com.openhr.data.Position;

public class TaxMonthForm extends ActionForm{
	 private Integer id;
	 private String employeeId;
	 private String firstname;
	 private String remarks;
	 private String taxdec;
	 private Position positionId;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTaxdec() {
		return taxdec;
	}
	public void setTaxdec(String taxdec) {
		this.taxdec = taxdec;
	}
	public Position getPositionId() {
		return positionId;
	}
	public void setPositionId(Position positionId) {
		this.positionId = positionId;
	}
}

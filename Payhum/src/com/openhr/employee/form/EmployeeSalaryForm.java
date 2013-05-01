package com.openhr.employee.form;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class EmployeeSalaryForm extends ActionForm {


private Integer empId;

private Integer prvsId;

private Double curSalry;

private Double prviesSalry;

private Date toDate;

private Date fromDate;

public Integer getEmpId() {
	return empId;
}

public void setEmpId(Integer empId) {
	this.empId = empId;
}

public Integer getPrvsId() {
	return prvsId;
}

public void setPrvsId(Integer prvsId) {
	this.prvsId = prvsId;
}

public Double getCurSalry() {
	return curSalry;
}

public void setCurSalry(Double curSalry) {
	this.curSalry = curSalry;
}

public Double getPrviesSalry() {
	return prviesSalry;
}

public void setPrviesSalry(Double prviesSalry) {
	this.prviesSalry = prviesSalry;
}

public long getToDate() {
	return toDate.getTime();
}

public void setToDate(long toDate) {
	this.toDate = new Date(toDate);
}

public long getFromDate() {
	return fromDate.getTime();
}

public void setFromDate(long fromDate) {
	this.fromDate = new Date(fromDate);
}


}

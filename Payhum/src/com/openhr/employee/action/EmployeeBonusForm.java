package com.openhr.employee.action;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class EmployeeBonusForm extends ActionForm{

private Integer	empId;
private Double	bonusAmont;
private Date	bonusDate;
public Integer getEmpId() {
	return empId;
}
public void setEmpId(Integer empId) {
	this.empId = empId;
}
public Double getBonusAmont() {
	return bonusAmont;
}
public void setBonusAmont(Double bonusAmont) {
	this.bonusAmont = bonusAmont;
}
public long getBonusDate() {
	return bonusDate.getTime();
}
public void setBonusDate(long bonusDate) {
	this.bonusDate =  new Date(bonusDate);
}
	
}

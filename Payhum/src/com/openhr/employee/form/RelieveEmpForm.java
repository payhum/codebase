package com.openhr.employee.form;

import java.util.Date;

public class RelieveEmpForm {

private Integer	empId;
private Date	relieveDate;
public Integer getEmpId() {
	return empId;
}
public void setEmpId(Integer empId) {
	this.empId = empId;
}
public long getRelieveDate() {
	return relieveDate.getTime();
}
public void setRelieveDate(long lastDate) {
	this.relieveDate=  new Date(lastDate);
}

}

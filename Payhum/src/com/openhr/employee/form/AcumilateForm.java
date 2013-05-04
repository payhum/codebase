package com.openhr.employee.form;

import java.io.Serializable;

import org.apache.struts.action.ActionForm;

import com.openhr.data.EmpPayTax;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;

public class AcumilateForm extends ActionForm implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Long count;

private Employee e;

private EmployeePayroll  epx;

public Long getCount() {
	return count;
}

public void setCount(Long count) {
	this.count = count;
}

public Employee getE() {
	return e;
}

public void setE(Employee e) {
	this.e = e;
}

public EmployeePayroll getEpx() {
	return epx;
}

public void setEpx(EmployeePayroll epx) {
	this.epx = epx;
}





}

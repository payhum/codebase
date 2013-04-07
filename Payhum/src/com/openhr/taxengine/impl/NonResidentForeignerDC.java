package com.openhr.taxengine.impl;

import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;

public class NonResidentForeignerDC extends BaseDC {

	@Override
	public void calculate(Employee emp, EmployeePayroll empPayroll) {
		// There are no deductions for Non-Resident Foreigner
	}

}

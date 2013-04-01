package com.openhr.taxengine.impl;

import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;

public class NonResidentForeignerEC extends BaseEC {

	@Override
	public void calculate(Employee emp, EmployeePayroll empPayroll) {
		// No Exemptions for Non-Resident Foreigners
	}

}

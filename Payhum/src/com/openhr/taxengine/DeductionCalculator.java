package com.openhr.taxengine;

import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;

public interface DeductionCalculator {
	public void calculate(Employee emp, EmployeePayroll empPayroll);
}

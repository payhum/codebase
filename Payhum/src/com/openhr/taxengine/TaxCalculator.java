package com.openhr.taxengine;

import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;

public interface TaxCalculator {
	public Double calculate(Employee emp, EmployeePayroll empPayroll);
}

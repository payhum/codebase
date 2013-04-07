package com.openhr.taxengine;

import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;

public interface IncomeCalculator {
	public EmployeePayroll calculate(Employee emp);

}

package com.openhr.taxengine;

import java.util.Calendar;

import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;

public interface DeductionCalculator {
	public void calculate(Employee emp, EmployeePayroll empPayroll, Calendar currDt, int finStartMonth);
}

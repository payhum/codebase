package com.openhr.taxengine;

import java.util.Calendar;

import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;

public interface IncomeCalculator {
	public EmployeePayroll calculate(Employee emp, Calendar currentDate, boolean active, int finStartMonth);
	
}

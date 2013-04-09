package com.openhr.taxengine.impl;

import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.taxengine.TaxDetails;

public class LocalIC extends BaseIC {

	@Override
	public EmployeePayroll calculate(Employee emp) {
		EmployeePayroll ePayroll = super.calculate(emp);
		
		TaxDetails taxDetails = TaxDetails.getTaxDetailsForCountry();
		
		Double annualGrossPay = ePayroll.getGrossSalary();
		Double totalIncome = ePayroll.getTotalIncome();
		
		// Employer Social Security
		Double employerSS = taxDetails.getEmployerSocialSecurityPercentage() * annualGrossPay / 100;
		totalIncome += employerSS;
		
		ePayroll.setEmployerSS(employerSS);
		
		// Overseas income
		totalIncome += ePayroll.getTaxableOverseasIncome();
		
		ePayroll.setTotalIncome(totalIncome);
		
		return ePayroll;
	}
	

}

package com.openhr.taxengine.impl;

import java.util.Calendar;

import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.taxengine.TaxDetails;
import com.util.payhumpackages.PayhumUtil;

public class LocalIC extends BaseIC {

	@Override
	public EmployeePayroll calculate(Employee emp, Calendar currDt, boolean active, int finStartMonth) {
		EmployeePayroll ePayroll = super.calculate(emp, currDt, active, finStartMonth);
		
		TaxDetails taxDetails = TaxDetails.getTaxDetailsForCountry();
		
		Double annualGrossPay = ePayroll.getGrossSalary();
		Double totalIncome = ePayroll.getTotalIncome();
		
		// Employer Social Security
		Double employerSS = taxDetails.getEmployerSocialSecurityPercentage() * annualGrossPay / 100;
		Double maxLimit = 0D;
		
		if(ePayroll.getEmployerSS() == 0D) {
			int remainingMonths = PayhumUtil.remainingMonths(currDt, finStartMonth);
			maxLimit = (taxDetails.getLimitForEmployerSS() / 12) * remainingMonths; 
		} else {
			maxLimit = ePayroll.getEmployerSS();
		}
				
		if(employerSS > maxLimit
			&& maxLimit != 0D) {
			employerSS = maxLimit;
		}
		
		totalIncome += employerSS;
		
		ePayroll.setEmployerSS(employerSS);
		
		// Overseas income
		totalIncome += ePayroll.getTaxableOverseasIncome();
		
		ePayroll.setTotalIncome(totalIncome);
		
		return ePayroll;
	}
	

}

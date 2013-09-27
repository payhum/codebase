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
		Double employerSS = 0D;
		
		/*if(ePayroll.getPaidNetPay().compareTo(0D) == 0) {
			// This is the first time payroll is processed and he is already inactive. Lets keep it 0 so that max limit is used.
		} else {
			employerSS = taxDetails.getEmployerSocialSecurityPercentage() * annualGrossPay / 100;
		}*/
			
		Double maxLimit = 0D;
		
		if(ePayroll.getEmployerSS() == 0D || !active) {
			int remainingMonths = PayhumUtil.remainingMonths(currDt, finStartMonth);
			
			if(!active) {
				remainingMonths = remainingMonths - 1;
				Double amt = ePayroll.getEmployerSS() - (taxDetails.getLimitForEmployerSS() / 12) * remainingMonths;
				maxLimit = amt;
			} else {
				if(PayhumUtil.isRetroActive(currDt, emp.getHiredate(), finStartMonth)) {
					maxLimit = (taxDetails.getLimitForEmployerSS() / 12) * (remainingMonths + 1);
				} else {
					maxLimit = (taxDetails.getLimitForEmployerSS() / 12) * remainingMonths;
				}
			}
		} else {
			maxLimit = ePayroll.getEmployerSS();
		}
				
		if((employerSS > maxLimit && maxLimit != 0D)  || employerSS == 0D) {
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

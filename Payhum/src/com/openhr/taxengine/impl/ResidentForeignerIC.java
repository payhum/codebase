package com.openhr.taxengine.impl;

import java.util.Calendar;

import com.openhr.common.PayhumConstants;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.TypesData;
import com.openhr.taxengine.TaxDetails;
import com.util.payhumpackages.PayhumUtil;

public class ResidentForeignerIC extends BaseIC {

	@Override
	public EmployeePayroll calculate(Employee emp, Calendar currDt, boolean active, int finStartMonth) {
		EmployeePayroll ePayroll = super.calculate(emp, currDt, active, finStartMonth);
		
		TaxDetails taxDetails = TaxDetails.getTaxDetailsForCountry();
		
		Double annualGrossPay = ePayroll.getGrossSalary();
		Double totalIncome = ePayroll.getTotalIncome();
		
		// Employer Social Security
		TypesData currency = ePayroll.getEmployeeId().getCurrency();
		
		if(currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_USD)) {
			Double emprSSinUSD;
			if(ePayroll.getEmployerSS() == 0D || !active) {
				int remainingMonths = PayhumUtil.remainingMonths(currDt, finStartMonth);
				
				if(!active) {
					remainingMonths = remainingMonths - 1;
					Double amt = ePayroll.getEmployerSS() - (taxDetails.getLimitForEmployerSSInUSD() / 12) * remainingMonths;
					emprSSinUSD = amt;
				} else {
					if(PayhumUtil.isRetroActive(currDt, emp.getHiredate(), finStartMonth)) {
						emprSSinUSD= (taxDetails.getLimitForEmployerSSInUSD() / 12) * (remainingMonths + 1);	
					} else {
						emprSSinUSD= (taxDetails.getLimitForEmployerSSInUSD() / 12) * remainingMonths;
					}
				}
				
				emprSSinUSD = getAmountInMMK(currency, emprSSinUSD);
			} else {
				emprSSinUSD= ePayroll.getEmployerSS();
			}
			
			totalIncome += emprSSinUSD;
			
			ePayroll.setEmployerSS(emprSSinUSD);
		} else {
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
					
			if((employerSS > maxLimit && maxLimit != 0D) || employerSS == 0D) {
				employerSS = maxLimit;
			}
			
			totalIncome += employerSS;
			
			ePayroll.setEmployerSS(employerSS);
		}
		
		ePayroll.setTotalIncome(totalIncome);
		
		return ePayroll;
	}
	

}

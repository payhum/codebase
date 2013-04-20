package com.openhr.taxengine.impl;

import java.util.List;

import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.taxengine.IncomeCalculator;
import com.openhr.taxengine.TaxDetails;

public class BaseIC implements IncomeCalculator {

	@Override
	public EmployeePayroll calculate(Employee emp) {
		/* Total Income involves the following:
		 * - Base Salary
		 * - Bonus
		 * - 12.5% of AGP (free accomodation provided by employer + fully furnished)
		 * 		OR
		 * - 10% of AGP (free accomodation provided by employer + not furnished)
		 * - Taxable income from overseas (for local employees)
		 * - Social Security paid by employer
		 * - Other benefits (allowances)
		 * 
		 * 
		 * Annual Gross Product
		 * - Base Salary
		 * - Bonus
		 * - Allowances (medical, transport)
		 */
		EmployeePayroll empPayroll = EmpPayTaxFactroy.findEmpPayrollbyEmpID(emp);
		TaxDetails taxDetails = TaxDetails.getTaxDetailsForCountry();
		
		Double annualGrossPay = empPayroll.getBaseSalary();
		annualGrossPay += empPayroll.getBonus();
		annualGrossPay += empPayroll.getAllowancesAmount();
		
		empPayroll.setGrossSalary(annualGrossPay);
		
		Double totalIncome = annualGrossPay;
		
		Float accomPercentage = taxDetails.getAccomodationPercentage(empPayroll.getAccomodationType()) ;
		
		if(accomPercentage != null && accomPercentage > 0) {
			Double accomAmt = accomPercentage * annualGrossPay / 100;
			totalIncome += accomAmt;
			
			empPayroll.setAccomodationAmount(accomAmt);
		}
				
		empPayroll.setTotalIncome(totalIncome);
		
		return empPayroll;
	}

}
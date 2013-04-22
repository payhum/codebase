package com.openhr.taxengine.impl;

import java.util.List;

import com.openhr.data.Benefit;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.factories.BenefitFactory;
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
		 * - Other benefits (allowances, etc..)
		 * 
		 * 
		 * Annual Gross Product
		 * - Base Salary
		 * - Bonus
		 * - Allowances (medical, transport)
		 */
		EmployeePayroll empPayroll = EmpPayTaxFactroy.findEmpPayrollbyEmpID(emp);
		TaxDetails taxDetails = TaxDetails.getTaxDetailsForCountry();
		
		//Process the other benefits and add it to the allowances of the EmployeePayroll
		Double allowancesAmt = 0D;
		List<Benefit> empBenefits = BenefitFactory.findByEmpId(empPayroll.getEmployeeId());
		for(Benefit bf : empBenefits){
			allowancesAmt += bf.getAmount();
		}
		
		empPayroll.setAllowances(allowancesAmt);
		
		Double annualGrossPay = empPayroll.getBaseSalary();
		annualGrossPay += empPayroll.getBonus();
		annualGrossPay += allowancesAmt;
		
		empPayroll.setGrossSalary(annualGrossPay);
		
		Double totalIncome = annualGrossPay;
		
		Double accomPercentage = taxDetails.getAccomodationPercentage(empPayroll.getAccomodationType().getName()) ;
		
		if(accomPercentage != null && accomPercentage > 0) {
			Double accomAmt = accomPercentage * annualGrossPay / 100;
			totalIncome += accomAmt;
			
			empPayroll.setAccomodationAmount(accomAmt);
		}
				
		empPayroll.setTotalIncome(totalIncome);
		
		return empPayroll;
	}

}

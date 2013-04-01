package com.openhr.taxengine.impl;

import java.util.Map;

import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.taxengine.DeductionCalculator;
import com.openhr.taxengine.DeductionType;
import com.openhr.taxengine.TaxDetails;

public class BaseDC implements DeductionCalculator{

	@Override
	public void calculate(Employee emp, EmployeePayroll empPayroll) {
		// Life Insurance
		Map<DeductionType, Double> dDeclared = empPayroll.getDeductionsDeclared();
		
		Double insuranceAmnt = dDeclared.get(DeductionType.SELF_LIFE_INSURANCE);
		insuranceAmnt += dDeclared.get(DeductionType.SPOUSE_LIFE_INSURANCE);
		
		empPayroll.addDeduction(DeductionType.LIFE_INSURANCE, insuranceAmnt);
		
		// Donation
		TaxDetails taxDetails = TaxDetails.getTaxDetailsForCountry();
		Double donationPercentage = taxDetails.getDeduction(DeductionType.DONATION);
		
		Double eligibleAmt = donationPercentage * empPayroll.getGrossSalary() / 100;
		Double actualAmt = dDeclared.get(DeductionType.DONATION);
		
		if(actualAmt != null && actualAmt > 0D) {
			empPayroll.addDeduction(DeductionType.DONATION, 
					(eligibleAmt > actualAmt ? actualAmt : eligibleAmt));
		}
		
		// TODO: Social Security required only if 5+ employees
		empPayroll.addDeduction(DeductionType.EMPLOYEE_SOCIAL_SECURITY,
				taxDetails.getDeduction(DeductionType.EMPLOYEE_SOCIAL_SECURITY) * empPayroll.getBaseSalary() / 100);
		
	}

}

package com.openhr.taxengine.impl;

import java.util.List;

import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.taxengine.DeductionCalculator;
import com.openhr.taxengine.DeductionType;
import com.openhr.taxengine.DeductionsDeclared;
import com.openhr.taxengine.TaxDetails;

public class BaseDC implements DeductionCalculator{

	@Override
	public void calculate(Employee emp, EmployeePayroll empPayroll) {
		// Life Insurance
		List<DeductionsDeclared> dDeclared = empPayroll.getDeductionsDeclared();
		TaxDetails taxDetails = TaxDetails.getTaxDetailsForCountry();
		
		for(DeductionsDeclared dd : dDeclared) {
			Integer ddType = dd.getType();
			
			if(ddType != null)
			{
				if(ddType == DeductionType.SELF_LIFE_INSURANCE.getValue()) {
					empPayroll.addDeduction(DeductionType.LIFE_INSURANCE, dd.getAmount());
				} else if(ddType == DeductionType.SPOUSE_LIFE_INSURANCE.getValue()) {
					empPayroll.addDeduction(DeductionType.LIFE_INSURANCE, dd.getAmount());
				} else if (ddType == DeductionType.DONATION.getValue()) {
					// Donation
					Double donationPercentage = taxDetails.getDeduction(DeductionType.DONATION);
					
					Double eligibleAmt = donationPercentage * empPayroll.getGrossSalary() / 100;
					Double actualAmt = dd.getAmount();
					
					if(actualAmt != null && actualAmt > 0D) {
						empPayroll.addDeduction(DeductionType.DONATION, 
								(eligibleAmt > actualAmt ? actualAmt : eligibleAmt));
					}			
				} else if(ddType == DeductionType.EMPLOYEE_SOCIAL_SECURITY.getValue()) {
					// TODO: Social Security required only if 5+ employees
					empPayroll.addDeduction(DeductionType.EMPLOYEE_SOCIAL_SECURITY,
							taxDetails.getDeduction(DeductionType.EMPLOYEE_SOCIAL_SECURITY) * empPayroll.getBaseSalary() / 100);
								
				}
			}
		}
	}
}

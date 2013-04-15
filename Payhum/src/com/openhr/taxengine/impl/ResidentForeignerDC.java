package com.openhr.taxengine.impl;

import java.util.List;

import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.taxengine.DeductionType;
import com.openhr.taxengine.DeductionsDeclared;
import com.openhr.taxengine.TaxDetails;

public class ResidentForeignerDC extends BaseDC {

	@Override
	public void calculate(Employee emp, EmployeePayroll empPayroll) {
		// Life Insurance
		List<DeductionsDeclared> dDeclared = empPayroll.getDeductionsDeclared();
		
		Double selfInsuranceAmt = 0D;
		Double spouseInsuranceAmt = 0D;
		Double donationAmt = 0D;
		for(DeductionsDeclared dd : dDeclared) {
			if(dd.getType().getId() == DeductionType.SELF_LIFE_INSURANCE.getValue()) {
				selfInsuranceAmt = dd.getAmount();
			} else if (dd.getType().getId() == DeductionType.SPOUSE_LIFE_INSURANCE.getValue()) {
				spouseInsuranceAmt = dd.getAmount();
			} else if(dd.getType().getId() == DeductionType.DONATION.getValue()) {
				donationAmt = dd.getAmount();
			}
		}
		
		Double insuranceAmnt = selfInsuranceAmt;
		insuranceAmnt += spouseInsuranceAmt;
		
		empPayroll.addDeduction(DeductionType.LIFE_INSURANCE, insuranceAmnt);
		
		// Donation
		TaxDetails taxDetails = TaxDetails.getTaxDetailsForCountry();
		Double donationPercentage = taxDetails.getDeduction(DeductionType.DONATION);
		
		Double eligibleAmt = donationPercentage * empPayroll.getGrossSalary() / 100;
		Double actualAmt = donationAmt;
		
		if(actualAmt != null && actualAmt > 0D) {
			empPayroll.addDeduction(DeductionType.DONATION, 
					(eligibleAmt > actualAmt ? actualAmt : eligibleAmt));
		}
	}
}

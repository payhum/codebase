package com.openhr.taxengine.impl;

import java.util.List;

import com.openhr.common.PayhumConstants;
import com.openhr.data.DeductionsType;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.factories.DeductionFactory;
import com.openhr.taxengine.DeductionCalculator;
import com.openhr.taxengine.DeductionsDeclared;
import com.openhr.taxengine.TaxDetails;

public class BaseDC implements DeductionCalculator{
	
	@Override
	public void calculate(Employee emp, EmployeePayroll empPayroll) {
		// Life Insurance
		List<DeductionsDeclared> dDeclared = empPayroll.getDeductionsDeclared();
		List<DeductionsType> deductionsTypes = DeductionFactory.findAll();
		
		Double selfInsuranceAmt = 0D;
		Double spouseInsuranceAmt = 0D;
		Double donationAmt = 0D;
		for(DeductionsDeclared dd : dDeclared) {
			if(dd.getType().getId().compareTo(getDeductionsType(deductionsTypes, PayhumConstants.SELF_LIFE_INSURANCE).getId()) == 0) {
				selfInsuranceAmt = dd.getAmount();
			} else if (dd.getType().getId().compareTo(getDeductionsType(deductionsTypes, PayhumConstants.SPOUSE_LIFE_INSURANCE).getId()) == 0) {
				spouseInsuranceAmt = dd.getAmount();
			} else if(dd.getType().getId().compareTo(getDeductionsType(deductionsTypes, PayhumConstants.DONATION).getId()) == 0) {
				donationAmt = dd.getAmount();
			}
		}
		
		Double insuranceAmnt = selfInsuranceAmt;
		insuranceAmnt += spouseInsuranceAmt;
		
		empPayroll.addDeduction(getDeductionsType(deductionsTypes, PayhumConstants.LIFE_INSURANCE), insuranceAmnt);
		
		// Donation
		TaxDetails taxDetails = TaxDetails.getTaxDetailsForCountry();
		Double donationPercentage = taxDetails.getDeduction(PayhumConstants.DONATION);
		
		Double eligibleAmt = donationPercentage * empPayroll.getGrossSalary() / 100;
		Double actualAmt = donationAmt;
		
		if(actualAmt != null && actualAmt > 0D) {
			empPayroll.addDeduction(getDeductionsType(deductionsTypes, PayhumConstants.DONATION), 
					(eligibleAmt > actualAmt ? actualAmt : eligibleAmt));
		}
		
		Double employeeSS = taxDetails.getDeduction(PayhumConstants.EMPLOYEE_SOCIAL_SECURITY) * empPayroll.getBaseSalary() / 100;
		Double maxLimit = taxDetails.getLimitForEmployeeSS();
		
		if(employeeSS > maxLimit) {
			employeeSS = maxLimit;
		}
		
		empPayroll.addDeduction(getDeductionsType(deductionsTypes, PayhumConstants.EMPLOYEE_SOCIAL_SECURITY), employeeSS);
		
	}

	protected DeductionsType getDeductionsType(
			List<DeductionsType> deductionsTypes, String typeStr) {
		for(DeductionsType dType: deductionsTypes) {
			if(dType.getName().equalsIgnoreCase(typeStr))
				return dType;
		}
		
		return null;
	}
}
package com.openhr.taxengine.impl;

import java.util.List;

import com.openhr.common.PayhumConstants;
import com.openhr.data.EmpDependents;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.Exemptionstype;
import com.openhr.factories.DeductionFactory;
import com.openhr.taxengine.ExemptionCalculator;
import com.openhr.taxengine.TaxDetails;

public class BaseEC implements ExemptionCalculator {

	@Override
	public void calculate(Employee emp, EmployeePayroll empPayroll) {
		TaxDetails taxDetails = TaxDetails.getTaxDetailsForCountry();
		List<EmpDependents> dependents = emp.getDependents();
		
		List<Exemptionstype> exemptionsTypes = DeductionFactory.findAllExemptionTypes();
		
		// Handle Married person supporting spouse
		if(emp.isMarried()) {
			for(EmpDependents dependent : dependents) {
				if(dependent.getDepType().getName().equalsIgnoreCase(PayhumConstants.DEP_SPOUSE)
				  && dependent.getOccupationType().getName().equalsIgnoreCase(PayhumConstants.OCCUP_NONE)) {
					empPayroll.addExemption(getExemptionType(exemptionsTypes, PayhumConstants.SUPPORTING_SPOUSE),
							taxDetails.getExemption(PayhumConstants.SUPPORTING_SPOUSE));
				}
			}
		} 
		
		// Handle Children exemptions
		int noOfChildern = 0;
		for(EmpDependents dependent : dependents) {
			if((dependent.getDepType().getName().equalsIgnoreCase(PayhumConstants.DEP_CHILD))
			&& ( dependent.getAge() < 18 || (dependent.getOccupationType().getName().equalsIgnoreCase(PayhumConstants.OCCUP_STUDENT)))) {
				noOfChildern++;
			}
		}
		
		empPayroll.addExemption(getExemptionType(exemptionsTypes, PayhumConstants.CHILDREN),
				taxDetails.getExemption(PayhumConstants.CHILDREN),
				noOfChildern);
		
		
		// Basic allowance
		Double basicAllowanceRate = taxDetails.getExemption(PayhumConstants.BASIC_ALLOWANCE_PERCENTAGE);
		Double basicAllowanceLimit = taxDetails.getExemption(PayhumConstants.BASIC_ALLOWANCE_LIMIT);
		
		Double basicAllowancePerRate = basicAllowanceRate * empPayroll.getTotalIncome() / 100;
		
		if(basicAllowancePerRate > basicAllowanceLimit) {
			empPayroll.addExemption(getExemptionType(exemptionsTypes, PayhumConstants.BASIC_ALLOWANCE), basicAllowanceLimit);
		} else {
			empPayroll.addExemption(getExemptionType(exemptionsTypes, PayhumConstants.BASIC_ALLOWANCE), basicAllowancePerRate);
		}	
	}

	private Exemptionstype getExemptionType(
			List<Exemptionstype> eTypes, String typeStr) {
		for(Exemptionstype eType: eTypes) {
			if(eType.getName().equalsIgnoreCase(typeStr))
				return eType;
		}
		
		return null;
	}
}

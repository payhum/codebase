package com.openhr.taxengine.impl;

import java.util.List;

import com.openhr.data.DependentType;
import com.openhr.data.EmpDependents;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.OccupationType;
import com.openhr.taxengine.ExemptionCalculator;
import com.openhr.taxengine.ExemptionType;
import com.openhr.taxengine.TaxDetails;

public class BaseEC implements ExemptionCalculator {

	@Override
	public void calculate(Employee emp, EmployeePayroll empPayroll) {
		TaxDetails taxDetails = TaxDetails.getTaxDetailsForCountry();
		List<EmpDependents> dependents = emp.getDependents();
		
		// Handle Married person supporting spouse
		if(emp.isMarried()) {
			for(EmpDependents dependent : dependents) {
				if(dependent.getType() == DependentType.SPOUSE) {
					empPayroll.addExemption(ExemptionType.SUPPORTING_SPOUSE,
							taxDetails.getExemption(ExemptionType.SUPPORTING_SPOUSE));
				}
			}
		} 
		
		// Handle Children exemptions
		int noOfChildern = 0;
		for(EmpDependents dependent : dependents) {
			if(dependent.getType() == DependentType.CHILD
			&& ( dependent.getAge() < 18 || dependent.getOccupationType() == OccupationType.STUDENT)) {
				noOfChildern++;
			}
		}
		
		empPayroll.addExemption(ExemptionType.CHILDREN,
				taxDetails.getExemption(ExemptionType.CHILDREN),
				noOfChildern);
		
		
		// Basic allowance
		Double basicAllowanceRate = taxDetails.getExemption(ExemptionType.BASIC_ALLOWANCE_PERCENTAGE);
		Double basicAllowanceLimit = taxDetails.getExemption(ExemptionType.BASIC_ALLOWANCE_LIMIT);
		
		Double basicAllowancePerRate = basicAllowanceRate * empPayroll.getTotalIncome() / 100;
		
		if(basicAllowancePerRate > basicAllowanceLimit) {
			empPayroll.addExemption(ExemptionType.BASIC_ALLOWANCE, basicAllowanceLimit);
		} else {
			empPayroll.addExemption(ExemptionType.BASIC_ALLOWANCE, basicAllowancePerRate);
		}
			
		
		
	}

}

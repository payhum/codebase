package com.openhr.taxengine.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.openhr.common.PayhumConstants;
import com.openhr.data.EmpDependents;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.Exemptionstype;
import com.openhr.factories.DeductionFactory;
import com.openhr.factories.PayrollFactory;
import com.openhr.taxengine.ExemptionCalculator;
import com.openhr.taxengine.TaxDetails;
import com.util.payhumpackages.PayhumUtil;

public class BaseEC implements ExemptionCalculator {

	@Override
	public void calculate(Employee emp, EmployeePayroll empPayroll, int finStartMonth, Calendar toBeProcessedFor, boolean active) {
		TaxDetails taxDetails = TaxDetails.getTaxDetailsForCountry();
		List<EmpDependents> dependents = emp.getDependents();
		
		int monthsInCurrentFY = getApplicableMonthsInCurrentFY(emp, finStartMonth, toBeProcessedFor);
		if(!active)
			monthsInCurrentFY = 1;
		
		List<Exemptionstype> exemptionsTypes = DeductionFactory.findAllExemptionTypes();
		
		// Handle Married person supporting spouse
		if(emp.isMarried()) {
			for(EmpDependents dependent : dependents) {
				if(dependent.getDepType().getName().equalsIgnoreCase(PayhumConstants.DEP_SPOUSE)
				  && dependent.getOccupationType().getName().equalsIgnoreCase(PayhumConstants.OCCUP_NONE)) {
					empPayroll.addExemption(getExemptionType(exemptionsTypes, PayhumConstants.SUPPORTING_SPOUSE),
							(taxDetails.getExemption(PayhumConstants.SUPPORTING_SPOUSE) / 12) * monthsInCurrentFY);
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
				(taxDetails.getExemption(PayhumConstants.CHILDREN) / 12) * monthsInCurrentFY,
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

	private int getApplicableMonthsInCurrentFY(Employee emp, int finStartMonth, Calendar toBeProcessedFor) {
		Date empStartDate = emp.getHiredate();
		
		Calendar empStartCurr = Calendar.getInstance();
		empStartCurr.setTime(empStartDate);
		empStartCurr.set(Calendar.HOUR_OF_DAY, 0);
		empStartCurr.set(Calendar.MINUTE, 0);
		empStartCurr.set(Calendar.SECOND, 0);
		empStartCurr.set(Calendar.MILLISECOND, 0);
		empStartCurr.set(Calendar.DAY_OF_MONTH, 1);
	    
		int empStartYear = empStartCurr.get(Calendar.YEAR);
		int currYear = toBeProcessedFor.get(Calendar.YEAR);
		
		int empStartMonth = empStartCurr.get(Calendar.MONTH);
		int currMonth = toBeProcessedFor.get(Calendar.MONTH);
		
		if(currYear == empStartYear && currMonth == empStartMonth) {
			return PayhumUtil.remainingMonths(toBeProcessedFor, finStartMonth);
		} else if(currYear == empStartYear && currMonth > empStartMonth) {
			int remMonths = PayhumUtil.remainingMonths(empStartCurr, finStartMonth);
			int finMonths = 0;
			try {
				finMonths = PayrollFactory.findPayrollDateByBranch(emp.getDeptId().getBranchId().getId()).size();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(remMonths > finMonths) {
				return finMonths;
			} else {
				return remMonths;
			}
		} else {
			try {
				return PayrollFactory.findPayrollDateByBranch(emp.getDeptId().getBranchId().getId()).size();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
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

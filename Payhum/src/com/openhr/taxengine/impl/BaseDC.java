package com.openhr.taxengine.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.openhr.common.PayhumConstants;
import com.openhr.data.ConfigData;
import com.openhr.data.DeductionsType;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.EmployeeSalary;
import com.openhr.data.TypesData;
import com.openhr.factories.ConfigDataFactory;
import com.openhr.factories.DeductionFactory;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.taxengine.DeductionCalculator;
import com.openhr.taxengine.DeductionsDeclared;
import com.openhr.taxengine.DeductionsDone;
import com.openhr.taxengine.TaxDetails;
import com.util.payhumpackages.PayhumUtil;

public class BaseDC implements DeductionCalculator{
	
	@Override
	public void calculate(Employee emp, EmployeePayroll empPayroll, Calendar currDt, int finStartMonth, boolean active) {
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
		
		List<DeductionsDone> deductionsList = empPayroll.getDeductionsDone();
		Double empeSSAmt = 0D;
		for(DeductionsDone dd: deductionsList) {
			if(dd.getType().getName().equalsIgnoreCase(PayhumConstants.EMPLOYEE_SOCIAL_SECURITY)) {
				empeSSAmt = dd.getAmount();
				break;
			}
		}
		
		// Check if the employee contributes to SS or not
		if(empPayroll.getWithholdSS().compareTo(1) == 0) {
			boolean isRetroActiveEmp = PayhumUtil.isRetroActive(currDt, emp.getHiredate(), finStartMonth);
			int remainingMonths = PayhumUtil.remainingMonths(currDt, finStartMonth);
			
			Double currentMonBaseSal = getMonthlyBaseSal(empPayroll, empeSSAmt);
			if(currentMonBaseSal.compareTo(0D) == 0) {
				// Base Salary didn't change this month, so no updates.
			} else {
				if(empeSSAmt.compareTo(0D) == 0) {
					// First time, so its just base sal * SS percentages * remaining months
					Double tmpSSAmt = 0D;
					
					if(currentMonBaseSal.compareTo(300000D) >= 0) {
						// Its more than limit, lets use max limit
						tmpSSAmt = taxDetails.getLimitForEmployeeSS();
					} else {
						// lets calculate for employee
						if(isRetroActiveEmp) {
							tmpSSAmt = currentMonBaseSal * (taxDetails.getDeduction(PayhumConstants.EMPLOYEE_SOCIAL_SECURITY)/100) * (remainingMonths + 1);
						} else {
							tmpSSAmt = currentMonBaseSal * (taxDetails.getDeduction(PayhumConstants.EMPLOYEE_SOCIAL_SECURITY)/100) * remainingMonths;
						}
					}
					
					empPayroll.addDeduction(getDeductionsType(deductionsTypes, PayhumConstants.EMPLOYEE_SOCIAL_SECURITY), tmpSSAmt);
				} else {
					// Base Salary changed this month, hence it requires an update.
					Double prevMonBaseSal = getPrevMonthlyBaseSal(empPayroll);
					if(prevMonBaseSal.compareTo(300000D) >=0 ) {
						// prev itself was more than limit so no changes.
					} else {
						Double diffBaseSal = currentMonBaseSal - prevMonBaseSal;
						Double ttSS = diffBaseSal * (taxDetails.getDeduction(PayhumConstants.EMPLOYEE_SOCIAL_SECURITY)/100) * remainingMonths;
						
						empeSSAmt = empeSSAmt + ttSS;
						
						empPayroll.addDeduction(getDeductionsType(deductionsTypes, PayhumConstants.EMPLOYEE_SOCIAL_SECURITY), empeSSAmt);
					}
				}
			}
		}
	}

	private Double getPrevMonthlyBaseSal(EmployeePayroll empPayroll) {
		List<EmployeeSalary> empSalList = EmpPayTaxFactroy.findEmpSalary(empPayroll.getEmployeeId());
		EmployeeSalary latestEmpSal = null;
		EmployeeSalary prevEmpSal = null;
		Calendar latestEmpSalStartDate = Calendar.getInstance();
		
		for(EmployeeSalary empSal : empSalList) {
			Date effectiveDate = empSal.getTodate();
			Date fromDate = empSal.getFromdate();
			
			if(effectiveDate.compareTo(fromDate) == 0) {
				latestEmpSalStartDate.setTime(fromDate);
				
				// Zero out the hour, minute, second, and millisecond
				latestEmpSalStartDate.set(Calendar.HOUR_OF_DAY, 0);
			    latestEmpSalStartDate.set(Calendar.MINUTE, 0);
			    latestEmpSalStartDate.set(Calendar.SECOND, 0);
			    latestEmpSalStartDate.set(Calendar.MILLISECOND, 0);
			    latestEmpSalStartDate.set(Calendar.DAY_OF_MONTH, 1);
			    
				latestEmpSal = empSal;
		    	break;
		    }
		}
	
		for(EmployeeSalary empSal : empSalList) {
			Date effectiveDate = empSal.getTodate();
			Date fromDate = empSal.getFromdate();
			
			if(effectiveDate.compareTo(fromDate) == 0)
				continue;
			
		    Calendar effDtCal = Calendar.getInstance();
		    effDtCal.setTime(effectiveDate);
		    // Zero out the hour, minute, second, and millisecond
		    effDtCal.set(Calendar.HOUR_OF_DAY, 0);
		    effDtCal.set(Calendar.MINUTE, 0);
		    effDtCal.set(Calendar.SECOND, 0);
		    effDtCal.set(Calendar.MILLISECOND, 0);
		    effDtCal.set(Calendar.DAY_OF_MONTH, 1);
		    
	    	if(latestEmpSal != null) {
	    		if(latestEmpSalStartDate.equals(effDtCal)) {
	    			// this is what we want.
	    			prevEmpSal = empSal;
	    			break;
	    		}
	    	}
		}
		
		return (prevEmpSal == null) ? null : getAmountInMMK(empPayroll.getEmployeeId().getCurrency(), prevEmpSal.getBasesalary()/12);
	}

	private Double getMonthlyBaseSal(EmployeePayroll empPayroll, Double empeSS) {
		List<EmployeeSalary> empSalList = EmpPayTaxFactroy.findEmpSalary(empPayroll.getEmployeeId());
		
		Calendar currDtCal = Calendar.getInstance();
		// Zero out the hour, minute, second, and millisecond
		currDtCal.set(Calendar.HOUR_OF_DAY, 0);
		currDtCal.set(Calendar.MINUTE, 0);
		currDtCal.set(Calendar.SECOND, 0);
		currDtCal.set(Calendar.MILLISECOND, 0);
		currDtCal.set(Calendar.DAY_OF_MONTH, 1);
		
		for(EmployeeSalary empSal : empSalList) {
			Date effectiveDate = empSal.getTodate();
			Date fromDate = empSal.getFromdate();
			
			Calendar effDtCal = Calendar.getInstance();
		    effDtCal.setTime(fromDate);
		    // Zero out the hour, minute, second, and millisecond
		    effDtCal.set(Calendar.HOUR_OF_DAY, 0);
		    effDtCal.set(Calendar.MINUTE, 0);
		    effDtCal.set(Calendar.SECOND, 0);
		    effDtCal.set(Calendar.MILLISECOND, 0);
		    effDtCal.set(Calendar.DAY_OF_MONTH, 1);
		    
			if(effectiveDate.compareTo(fromDate) == 0) {
				if(currDtCal.equals(effDtCal) || empeSS.compareTo(0D) == 0) {
					// this is latest and from this month, so lets return it.
					return getAmountInMMK(empPayroll.getEmployeeId().getCurrency(), empSal.getBasesalary()/12);
				}
		    }
		}
		
		return 0D;
	}

	protected DeductionsType getDeductionsType(
			List<DeductionsType> deductionsTypes, String typeStr) {
		for(DeductionsType dType: deductionsTypes) {
			if(dType.getName().equalsIgnoreCase(typeStr))
				return dType;
		}
		
		return null;
	}
	
	protected Double getAmountInMMK(TypesData currency, Double amount) {
		Double conversionRate = 1D;
		
		if(currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_USD)) {
			ConfigData currencyConver = ConfigDataFactory.findByName(PayhumConstants.USD_MMK_CONVER);
			conversionRate = Double.valueOf(currencyConver.getConfigValue());
		} else if(currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_EURO)) {
			ConfigData currencyConver = ConfigDataFactory.findByName(PayhumConstants.EURO_MMK_CONVER);
			conversionRate = Double.valueOf(currencyConver.getConfigValue());
		} else if(currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_POUND)) {
			ConfigData currencyConver = ConfigDataFactory.findByName(PayhumConstants.POUND_MMK_CONVER);
			conversionRate = Double.valueOf(currencyConver.getConfigValue());
		} 
		
		return amount * conversionRate;
	}
}
package com.openhr.taxengine.impl;

import java.util.Calendar;
import java.util.List;

import com.openhr.common.PayhumConstants;
import com.openhr.data.ConfigData;
import com.openhr.data.DeductionsType;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.TypesData;
import com.openhr.factories.ConfigDataFactory;
import com.openhr.factories.DeductionFactory;
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
			Double empeSSinUSD;
			boolean isRetroActiveEmp = PayhumUtil.isRetroActive(currDt, emp.getHiredate(), finStartMonth);
			TypesData currency = empPayroll.getEmployeeId().getCurrency();
			
			if(currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_USD)) {			
				if(empeSSAmt == 0D || !active) {
					int remainingMonths = PayhumUtil.remainingMonths(currDt, finStartMonth);
					
					if(!active)
						remainingMonths = 1;
					
					if(isRetroActiveEmp) {
						empeSSinUSD = (taxDetails.getLimitForEmployeeSSInUSD() / 12) * (remainingMonths + 1);
					} else {
						empeSSinUSD = (taxDetails.getLimitForEmployeeSSInUSD() / 12) * remainingMonths;	
					}
					
					empeSSinUSD = getAmountInMMK(currency, empeSSinUSD);
				} else {
					empeSSinUSD = empeSSAmt;
				}
				
				empPayroll.addDeduction(getDeductionsType(deductionsTypes, PayhumConstants.EMPLOYEE_SOCIAL_SECURITY), empeSSinUSD);
			} else {
				Double employeeSS = 0D;
				
				/*if(empPayroll.getPaidNetPay().compareTo(0D) == 0) {
					// This is the first time payroll is processed and he is already inactive. Lets keep it 0 so that max limit is used.
				} else {
					employeeSS = taxDetails.getDeduction(PayhumConstants.EMPLOYEE_SOCIAL_SECURITY) * empPayroll.getBaseSalary() / 100;
				}*/
				
				Double maxLimit = 0D;
				
				if(empeSSAmt == 0D || !active) {
					int remainingMonths = PayhumUtil.remainingMonths(currDt, finStartMonth);
					
					if(!active) {
						remainingMonths = remainingMonths - 1;
						Double amt = empeSSAmt - (taxDetails.getLimitForEmployeeSS() / 12) * remainingMonths;
						maxLimit = amt;
					} else {
						if(isRetroActiveEmp) {
							maxLimit = (taxDetails.getLimitForEmployeeSS() / 12) * (remainingMonths + 1);
						} else {
							maxLimit = (taxDetails.getLimitForEmployeeSS() / 12) * remainingMonths;
						}
					}
				} else {
					maxLimit = empeSSAmt;
				}
				
				if((employeeSS > maxLimit && maxLimit != 0D) || employeeSS == 0D) {
					employeeSS = maxLimit;
				}
				
				empPayroll.addDeduction(getDeductionsType(deductionsTypes, PayhumConstants.EMPLOYEE_SOCIAL_SECURITY), employeeSS);
			}
		}
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
package com.openhr.taxengine.impl;

import java.util.List;

import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.taxengine.TaxCalculator;
import com.openhr.taxengine.TaxRates;

public class BaseTC implements TaxCalculator{

	@Override
	public Double calculate(Employee emp, EmployeePayroll empPayroll) {
		TaxRates taxRates = TaxRates.getTaxRatesForCountry();
		Double lastPercentage = 1D;
		
		if(taxRates.shouldTax(empPayroll)) {
			// Get the taxes and put the math.
			Double taxAmount = new Double(0);
			Double taxableIncome = empPayroll.getTaxableIncome();
			Double amountYetToBeTaxed = new Double(taxableIncome);
			
			List<TaxRates.Slabs> slabs = taxRates.getSlabs();
			
			for(TaxRates.Slabs slab : slabs) {
				Double toValue = slab.getTo();
				Double fromValue = slab.getFrom() - 1;
				Double ratePercentage = slab.getRatePercentage();
				
				if(slab.isLast()) {
					if(amountYetToBeTaxed == 0)
						break;
					
					taxAmount += amountYetToBeTaxed * ratePercentage / 100;
					lastPercentage = ratePercentage;
					break;
				}
				else {
					if(taxableIncome >= toValue) {
						amountYetToBeTaxed = amountYetToBeTaxed - (toValue - fromValue);
						taxAmount += (toValue - fromValue) * ratePercentage / 100;
						lastPercentage = ratePercentage;
					} else {
						if(amountYetToBeTaxed == 0)
							break;
						
						taxAmount += amountYetToBeTaxed * ratePercentage / 100;
						lastPercentage = ratePercentage;
						break;
					}
				}
			}
			
			empPayroll.setTaxAmount(taxAmount);
		} else {
			// The taxable income is lower than the limit, so no tax.
		}
		
		return lastPercentage;
	}

}

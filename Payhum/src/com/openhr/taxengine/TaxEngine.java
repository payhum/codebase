package com.openhr.taxengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openhr.company.Company;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;

public class TaxEngine {
	private Company comp;
	
	private Map<Employee, EmployeePayroll> testMap;
	
	public TaxEngine(Company company) {
		this.comp = company;
		
		if(System.getProperty("DRYRUN") != null 
		&& System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
			testMap = new HashMap<Employee, EmployeePayroll>();
		}
	}
	
	public void execute() {
		// TODO: Get the fiscal year, assuming the current year?
		
		List<Employee> empList = comp.getActiveEmployees();
		
		/*
		 * for each emp
		> Resident type factory.getSourceofIncomeObj(type)
		> obj.calculate(emp)
		> Resident type factory.getExcemptionObj(type)
		> obj.calculate(emp)
			> EmployeeExemptionFactory.get
		> Resident type factory.getTaxRatesCalculator(type)
			> Calculator.execute(emp);
		 */
		for (Employee emp : empList) {
			IncomeCalculator incomeCalc = ResidentTypeFactory.getIncomeCalculator(emp);
			
			// Get the annual income of the person, which involves his AGP + other incomes
			EmployeePayroll empPayroll = incomeCalc.calculate(emp);
			
			// Calculate the Exemptions
			ExemptionCalculator exmpCalc = ResidentTypeFactory.getExemptionCalculator(emp);
			exmpCalc.calculate(emp, empPayroll);
			
			// Calculate the deductions
			DeductionCalculator deducCalc = ResidentTypeFactory.getDeductionCalculator(emp);
			deducCalc.calculate(emp, empPayroll);
			
			// Finally calculate the tax to be paid
			TaxCalculator taxCalc = ResidentTypeFactory.getTaxCalculator(emp);
			taxCalc.calculate(emp, empPayroll);
			
			empPayroll.save();
			
			if(System.getProperty("DRYRUN") != null 
			&& System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
				testMap.put(emp,  empPayroll);
			}
		}
	}
	
	public Map<Employee, EmployeePayroll> testExecute() {
		execute();
		return testMap;
	}
}

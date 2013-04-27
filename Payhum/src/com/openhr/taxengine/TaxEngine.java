package com.openhr.taxengine;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openhr.company.Company;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.factories.EmpPayTaxFactroy;

public class TaxEngine {
	private Company comp;
	private List<Employee> activeEmpList;
	private List<Employee> inActiveEmpList;
	
	private Map<Employee, EmployeePayroll> testMap;
	
	public TaxEngine(Company company, List<Employee> activeList, List<Employee> inactiveList) {
		this.comp = company;
		this.activeEmpList = activeList;
		this.inActiveEmpList = inactiveList;
		
		if(System.getProperty("DRYRUN") != null 
		&& System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
			testMap = new HashMap<Employee, EmployeePayroll>();
		}
	}
	
	public void execute(Calendar toBeProcessedFor) {
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
		for (Employee emp : activeEmpList) {
			System.out.println("Processing for employee - " + emp.getId());
			IncomeCalculator incomeCalc = ResidentTypeFactory.getIncomeCalculator(emp);
			
			// Get the annual income of the person, which involves his AGP + other incomes
			EmployeePayroll empPayroll = incomeCalc.calculate(emp, toBeProcessedFor, true);
			
			// Calculate the Exemptions
			ExemptionCalculator exmpCalc = ResidentTypeFactory.getExemptionCalculator(emp);
			exmpCalc.calculate(emp, empPayroll);
			
			// Calculate the deductions
			DeductionCalculator deducCalc = ResidentTypeFactory.getDeductionCalculator(emp);
			deducCalc.calculate(emp, empPayroll);
			
			// Finally calculate the tax to be paid
			TaxCalculator taxCalc = ResidentTypeFactory.getTaxCalculator(emp);
			taxCalc.calculate(emp, empPayroll);
			
			empPayroll.setNetPay(empPayroll.getTaxableIncome() - empPayroll.getTaxAmount());
			
			// TODO: Update on the split per month/week/biweekly
			
			if(System.getProperty("DRYRUN") != null 
			&& System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
				testMap.put(emp,  empPayroll);
			} else {
				// Update the payroll details into the repos.
				if( ! EmpPayTaxFactroy.update(empPayroll)) {
					// TODO Failed to update, throw error.
				}
			}
		}
		
		// Process the inactive or terminated employees in this month\
		for(Employee emp: inActiveEmpList) {
			System.out.println("Processing for inactive employee - " + emp.getId());
			IncomeCalculator incomeCalc = ResidentTypeFactory.getIncomeCalculator(emp);
			
			// Get the annual income of the person, which involves his AGP + other incomes
			EmployeePayroll empPayroll = incomeCalc.calculate(emp, toBeProcessedFor, false);
			
			// Calculate the Exemptions
			ExemptionCalculator exmpCalc = ResidentTypeFactory.getExemptionCalculator(emp);
			exmpCalc.calculate(emp, empPayroll);
			
			// Calculate the deductions
			DeductionCalculator deducCalc = ResidentTypeFactory.getDeductionCalculator(emp);
			deducCalc.calculate(emp, empPayroll);
			
			// Finally calculate the tax to be paid
			TaxCalculator taxCalc = ResidentTypeFactory.getTaxCalculator(emp);
			taxCalc.calculate(emp, empPayroll);
			
			empPayroll.setNetPay(empPayroll.getTaxableIncome() - empPayroll.getTaxAmount());
			
			if(System.getProperty("DRYRUN") != null 
			&& System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
				testMap.put(emp,  empPayroll);
			} else {
				// Update the payroll details into the repos.
				if( ! EmpPayTaxFactroy.update(empPayroll)) {
					// TODO Failed to update, throw error.
				}
			}
		}
	}
	
	public Map<Employee, EmployeePayroll> testExecute(Calendar currDate) {
		execute(currDate);
		return testMap;
	}
}

package com.openhr.taxengine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openhr.common.PayhumConstants;
import com.openhr.company.Company;
import com.openhr.data.EmpBankAccount;
import com.openhr.data.EmpPayrollMap;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.PayPeriodData;
import com.openhr.data.Payroll;
import com.openhr.factories.EmpBankAccountFactory;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.PayPeriodFactory;
import com.openhr.factories.PayrollFactory;

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
	
	public List<EmployeePayroll> execute(Payroll payroll) throws Exception {
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
		Calendar toBeProcessedFor = Calendar.getInstance();
		toBeProcessedFor.setTime(payroll.getPayDateId().getRunDate());
		
		List<EmployeePayroll> retList = new ArrayList<EmployeePayroll>();
		
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
			
			// Update on the split per month/week/biweekly
			computeDetailsPerPayPeriod(empPayroll, toBeProcessedFor, payroll);
			
			if(System.getProperty("DRYRUN") != null 
			&& System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
				testMap.put(emp,  empPayroll);
			} else {
				// Update the payroll details into the repos.
				boolean success = EmpPayTaxFactroy.update(empPayroll); 
				if( success ) {
					retList.add(empPayroll);
				} else {
					throw new Exception("Failed to process payroll for emp - " + emp.getId());
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
			
			empPayroll.setPaidNetPay(empPayroll.getNetPay());
			empPayroll.setPaidSS(empPayroll.getEmployerSS());
			empPayroll.setPaidTaxAmt(empPayroll.getTaxAmount());
			
			if(System.getProperty("DRYRUN") != null 
			&& System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
				testMap.put(emp,  empPayroll);
			} else {
				// Update the payroll details into the repos.
				boolean success = EmpPayTaxFactroy.update(empPayroll); 
				if( success ) {
					retList.add(empPayroll);
				} else {
					throw new Exception("Failed to process payroll for emp - " + emp.getId());
				}
			}
		}
		
		return retList;
	}
	
	private void computeDetailsPerPayPeriod(EmployeePayroll empPayroll,
			Calendar toBeProcessedFor, Payroll payroll) throws Exception {
		List<PayPeriodData> payPeriods = PayPeriodFactory.findAll();
		
		// default is monthly
		// 0 = monthly, 1 = biweekly, 2 = weekly
		int mode = 0; 
		
		for(PayPeriodData pp : payPeriods) {
			if (pp.getPeriodValue().compareTo(new Integer(1)) == 0) {
				if(pp.getPeriodName().equalsIgnoreCase(PayhumConstants.MONTHLY)) {
					mode = 0;
				} else if(pp.getPeriodName().equalsIgnoreCase(PayhumConstants.BIWEEKLY)) {
					mode = 1;
				} else if(pp.getPeriodName().equalsIgnoreCase(PayhumConstants.WEEKLY)) {
					mode = 2;
				}
			}
		}
		
		if(mode == 0) {
			Double totalTaxAmt = empPayroll.getTaxAmount();
			Double totalNeyPay = empPayroll.getNetPay();
			Double totalEmpSS = empPayroll.getEmployerSS();
			
			// get employee contribution of SS
			List<DeductionsDone> deductionsList = EmpPayTaxFactroy.deductionsDone(empPayroll.getId());
			
			for(DeductionsDone dd: deductionsList) {
				if(dd.getType().getName().equalsIgnoreCase(PayhumConstants.EMPLOYEE_SOCIAL_SECURITY)) {
					totalEmpSS += dd.getAmount();
				}
			}
			
			empPayroll.setPaidNetPay(empPayroll.getPaidNetPay() + totalNeyPay / 12);
			empPayroll.setPaidTaxAmt(empPayroll.getPaidTaxAmt() + totalTaxAmt / 12);
			empPayroll.setPaidSS(empPayroll.getPaidSS() + totalEmpSS / 12);
			
			// Save to emp_payroll_map table.
			EmpBankAccount empBankAcct = EmpBankAccountFactory.findByEmployeeId(empPayroll.getEmployeeId().getId());
			EmpPayrollMap empPayMap = new EmpPayrollMap();
			empPayMap.setEmppayId(empPayroll);
			empPayMap.setNetPay(totalNeyPay / 12);
			empPayMap.setTaxAmt(totalTaxAmt / 12);
			empPayMap.setSocialSec(totalEmpSS / 12);
			empPayMap.setPayrollId(payroll);
			
			if(empBankAcct != null) {
				empPayMap.setMode(1);
			}
			
			if(System.getProperty("DRYRUN") == null 
			|| ! System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
				PayrollFactory.insertEmpPayrollMap(empPayMap);
			}
			
		} else if (mode == 1) {
			//TODO
		} else if (mode == 2) {
			//TODO
		}
	}

	public Map<Employee, EmployeePayroll> testExecute(Payroll payroll) throws Exception {
		execute(payroll);
		return testMap;
	}
}

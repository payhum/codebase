package com.openhr.taxengine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openhr.common.PayhumConstants;
import com.openhr.company.Company;
import com.openhr.data.EmpBankAccount;
import com.openhr.data.EmpPayrollMap;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeBonus;
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
	
	private Double prevNetPay = 0D;
	private Double prevTaxAmt = 0D;
	private Double prevEmpSS = 0D;
	
	public TaxEngine(Company company, List<Employee> activeList, List<Employee> inactiveList) {
		this.comp = company;
		this.activeEmpList = activeList;
		this.inActiveEmpList = inactiveList;
		
		if(System.getProperty("DRYRUN") != null 
		&& System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
			testMap = new HashMap<Employee, EmployeePayroll>();
		}
	}
	
	public List<EmployeePayroll> execute(Payroll payroll, boolean adhoc) throws Exception {
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
			
			prevNetPay = empPayroll.getNetPay();
			prevEmpSS = empPayroll.getEmployerSS();
			prevTaxAmt = empPayroll.getTaxAmount();					
					
			// Calculate the Exemptions
			ExemptionCalculator exmpCalc = ResidentTypeFactory.getExemptionCalculator(emp);
			exmpCalc.calculate(emp, empPayroll);
			
			// Calculate the deductions
			DeductionCalculator deducCalc = ResidentTypeFactory.getDeductionCalculator(emp);
			deducCalc.calculate(emp, empPayroll);
			
			// Finally calculate the tax to be paid
			TaxCalculator taxCalc = ResidentTypeFactory.getTaxCalculator(emp);
			Double lastPercentage = taxCalc.calculate(emp, empPayroll);
			
			// Process if the tax is to be paid by employer.
			processTaxPaidByEmployer(empPayroll, lastPercentage);
			
			empPayroll.setNetPay(empPayroll.getTaxableIncome() - empPayroll.getTaxAmount());
			
			// Update on the split per month/week/biweekly
			computeDetailsPerPayPeriod(empPayroll, toBeProcessedFor, payroll, adhoc);
			
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
			Double lastPercentage = taxCalc.calculate(emp, empPayroll);
			
			// Process if the tax is to be paid by employer.
			processTaxPaidByEmployer(empPayroll, lastPercentage);
						
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
	
	private void processTaxPaidByEmployer(EmployeePayroll empPayroll, 
											Double ratePercentage) {
		Integer taxPaidByEmployer = empPayroll.getTaxPaidByEmployer();
		
		if(taxPaidByEmployer != null && taxPaidByEmployer.compareTo(1) == 0) {
			// Tax is paid by Employer so process it.
			Double taxRate = ratePercentage / 100;
			
			Double taxAmount = empPayroll.getTaxAmount();
			
			Double amt1 = taxAmount * taxRate;
			amt1 += (amt1 * taxRate) / (1 - taxRate);
			amt1 += taxAmount;
			
			empPayroll.setTaxAmount(amt1);
			
			Double totalAmt = empPayroll.getTotalIncome();
			Double taxableAmt = empPayroll.getTaxableIncome();
			
			empPayroll.setTotalIncome(totalAmt + amt1);
			empPayroll.setTaxableIncome(taxableAmt + amt1);
		}
	}

	private void computeDetailsPerPayPeriod(EmployeePayroll empPayroll,
			Calendar toBeProcessedFor, Payroll payroll, boolean adhoc) throws Exception {
		List<PayPeriodData> payPeriods = PayPeriodFactory.findAll();
		
		if(adhoc) {
			EmployeeBonus latestBonus = getLatestBonus(empPayroll, toBeProcessedFor);
			if(latestBonus != null && prevNetPay != 0D) {
				Double diffNetPay = empPayroll.getNetPay() - prevNetPay;
				Double diffTaxAmt = empPayroll.getTaxAmount() - prevTaxAmt;
				Double diffEmpSS = empPayroll.getEmployerSS() - prevEmpSS;
				
				empPayroll.setPaidNetPay(empPayroll.getPaidNetPay() + diffNetPay);
				empPayroll.setPaidTaxAmt(empPayroll.getPaidTaxAmt() + diffTaxAmt);
				empPayroll.setPaidSS(empPayroll.getPaidSS() + diffEmpSS);
				
				// Save to emp_payroll_map table.
				EmpBankAccount empBankAcct = EmpBankAccountFactory.findByEmployeeId(empPayroll.getEmployeeId().getId());
				EmpPayrollMap empPayMap = new EmpPayrollMap();
				empPayMap.setEmppayId(empPayroll);
				empPayMap.setNetPay(diffNetPay);
				empPayMap.setTaxAmount(diffTaxAmt);
				empPayMap.setSocialSec(diffEmpSS);
				empPayMap.setPayrollId(payroll);
				
				if(empBankAcct != null) {
					empPayMap.setMode(1);
				}
				
				if(System.getProperty("DRYRUN") == null 
				|| ! System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
					PayrollFactory.insertEmpPayrollMap(empPayMap);
				}
			} else {
				// this emp has no bonus, so no changes.
			}
		} else {
			// default is monthly
			// 0 = monthly, 1 = biweekly, 2 = weekly
			int mode = 0; 
			
			for(PayPeriodData pp : payPeriods) {
				if (pp.getPeriodValue().compareTo(new Integer(3)) == 0) {
					mode = 0;
				} else if(pp.getPeriodValue().compareTo(new Integer(2)) == 0) {
					mode = 1;
				} else if(pp.getPeriodValue().compareTo(new Integer(1)) == 0) {
					mode = 2;
				}
			}
			
			if(mode == 0) {
				Double totalTaxAmt = empPayroll.getTaxAmount();
				Double totalNeyPay = empPayroll.getNetPay();
				Double totalEmpSS = empPayroll.getEmployerSS();
				
				// get employee contribution of SS
				List<DeductionsDone> deductionsList = empPayroll.getDeductionsDone();
				
				for(DeductionsDone dd: deductionsList) {
					if(dd.getType().getName().equalsIgnoreCase(PayhumConstants.EMPLOYEE_SOCIAL_SECURITY)) {
						totalEmpSS += dd.getAmount();
					}
				}
				
				int remainingMonths = remainingMonths(toBeProcessedFor);
				
				Double pendingTaxAmt = totalTaxAmt - empPayroll.getPaidTaxAmt();
				Double pendingNeyPay = totalNeyPay - empPayroll.getPaidNetPay();
				Double pendingEmpSS = totalEmpSS - empPayroll.getPaidSS();
				
				empPayroll.setPaidNetPay(empPayroll.getPaidNetPay() + pendingNeyPay / remainingMonths);
				empPayroll.setPaidTaxAmt(empPayroll.getPaidTaxAmt() + pendingTaxAmt / remainingMonths);
				empPayroll.setPaidSS(empPayroll.getPaidSS() + pendingEmpSS / remainingMonths);
				
				// Save to emp_payroll_map table.
				EmpBankAccount empBankAcct = EmpBankAccountFactory.findByEmployeeId(empPayroll.getEmployeeId().getId());
				EmpPayrollMap empPayMap = new EmpPayrollMap();
				empPayMap.setEmppayId(empPayroll);
				empPayMap.setNetPay(pendingNeyPay / remainingMonths);
				empPayMap.setTaxAmount(pendingTaxAmt / remainingMonths);
				empPayMap.setSocialSec(pendingEmpSS / remainingMonths);
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
	}

	public Map<Employee, EmployeePayroll> testExecute(Payroll payroll) throws Exception {
		execute(payroll, false);
		return testMap;
	}
	
	public static EmployeeBonus getLatestBonus(EmployeePayroll empPayroll,
			Calendar currDtCal) {
		List<EmployeeBonus> empBonusList = EmpPayTaxFactroy.findEmpBonus(empPayroll.getEmployeeId());
		EmployeeBonus latestEmpBonus = null;
		
		currDtCal.set(Calendar.HOUR_OF_DAY, 0);
	    currDtCal.set(Calendar.MINUTE, 0);
	    currDtCal.set(Calendar.SECOND, 0);
	    currDtCal.set(Calendar.MILLISECOND, 0);
	    currDtCal.set(Calendar.DAY_OF_MONTH, 1);
	    
	    for(EmployeeBonus empBonus : empBonusList) {
			Date effectiveDate = empBonus.getGivendate();
			
		    Calendar effDtCal = Calendar.getInstance();
		    effDtCal.setTime(effectiveDate);
		    // Zero out the hour, minute, second, and millisecond
		    effDtCal.set(Calendar.HOUR_OF_DAY, 0);
		    effDtCal.set(Calendar.MINUTE, 0);
		    effDtCal.set(Calendar.SECOND, 0);
		    effDtCal.set(Calendar.MILLISECOND, 0);
		    effDtCal.set(Calendar.DAY_OF_MONTH, 1);
		    
		    if(currDtCal.equals(effDtCal)) {
		    	// its effective from current month, so lets update the base salary
		    	latestEmpBonus = empBonus;
		    	break;
		    }
		}
	    
	    return latestEmpBonus;
	}
	
	private int remainingMonths(Calendar currDate) {
		int retVal = 0;
		
		// Get the remaining months in the year.
		int currentMonth = currDate.get(Calendar.MONTH) + 1;
		
		if(currentMonth >= 4) {
			retVal = 12 - currentMonth + 1;
			retVal += 3;
		} else {
			retVal = 3 - currentMonth + 1;	
		}
		
		return retVal;
	}
}

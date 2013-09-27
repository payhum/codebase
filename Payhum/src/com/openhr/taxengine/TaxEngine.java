package com.openhr.taxengine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openhr.common.PayhumConstants;
import com.openhr.company.Company;
import com.openhr.data.Branch;
import com.openhr.data.ConfigData;
import com.openhr.data.EmpBankAccount;
import com.openhr.data.EmpPayrollMap;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeBonus;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.Payroll;
import com.openhr.data.PayrollDate;
import com.openhr.data.TypesData;
import com.openhr.factories.ConfigDataFactory;
import com.openhr.factories.EmpBankAccountFactory;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.PayrollFactory;
import com.util.payhumpackages.PayhumUtil;

public class TaxEngine {
	private Company comp;
	private Branch branch;
	private List<Employee> activeEmpList;
	private List<Employee> inActiveEmpList;
	
	private Map<Employee, EmployeePayroll> testMap;
	
	private Double prevNetPay = 0D;
	private Double prevTaxAmt = 0D;
	private Double prevEmprSS = 0D;
	
	public TaxEngine(Company company, Branch branch, List<Employee> activeList, List<Employee> inactiveList) {
		this.comp = company;
		this.branch = branch;
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
		toBeProcessedFor.setTime(payroll.getPayDateId().getRunDateofDateObject());
		
		List<EmployeePayroll> retList = new ArrayList<EmployeePayroll>();
		List<PayrollDate> payrollDates = PayrollFactory.findPayrollDateByBranch(branch.getId());
		List<Payroll> payRuns = PayrollFactory.findAllPayrollRuns();
		
		for (Employee emp : activeEmpList) {
			System.out.println("Processing for employee - " + emp.getId());
			
			IncomeCalculator incomeCalc = ResidentTypeFactory.getIncomeCalculator(emp);
			
			// Get the annual income of the person, which involves his AGP + other incomes
			EmployeePayroll empPayroll = incomeCalc.calculate(emp, toBeProcessedFor, true, comp.getFinStartMonth());
			
			prevNetPay = empPayroll.getNetPay();
			prevEmprSS = empPayroll.getEmployerSS();
			prevTaxAmt = empPayroll.getTaxAmount();			
					
			// Calculate the Exemptions
			ExemptionCalculator exmpCalc = ResidentTypeFactory.getExemptionCalculator(emp);
			exmpCalc.calculate(emp, empPayroll, comp.getFinStartMonth(), toBeProcessedFor, true);
			
			// Calculate the deductions
			DeductionCalculator deducCalc = ResidentTypeFactory.getDeductionCalculator(emp);
			deducCalc.calculate(emp, empPayroll, toBeProcessedFor, comp.getFinStartMonth(), true);
			
			// Finally calculate the tax to be paid
			TaxCalculator taxCalc = ResidentTypeFactory.getTaxCalculator(emp);
			Double lastPercentage = taxCalc.calculate(emp, empPayroll);
			
			// Process if the tax is to be paid by employer.
			processTaxPaidByEmployer(empPayroll, lastPercentage);
			
			// Here if the employees (resident and non-res foreigner) are not subject to withhold tax then
			// consider it.
			Double income = empPayroll.getTotalIncome();
			Double empeSS = 0D;
			
			List<DeductionsDone> deductionsList = empPayroll.getDeductionsDone();
			for(DeductionsDone dd: deductionsList) {
				if(dd.getType().getName().equalsIgnoreCase(PayhumConstants.EMPLOYEE_SOCIAL_SECURITY)) {
					empeSS = dd.getAmount();
					break;
				}
			}
			
			if(empPayroll.getWithholdTax().compareTo(1) == 0) {
				empPayroll.setNetPay(income - empPayroll.getTaxAmount() - empPayroll.getEmployerSS() - empeSS);
			} else {
				// Else keep the Net pay as taxable Income and let Employee pay the tax and we just tell the amount.
				empPayroll.setNetPay(income - empPayroll.getEmployerSS() - empeSS);
			}
			
			// Update on the split per month/week/biweekly
			computeDetailsPerPayPeriod(empPayroll, toBeProcessedFor, payroll, adhoc,
					payrollDates, payRuns, comp.getFinStartMonth());
			
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
			EmployeePayroll empPayroll = incomeCalc.calculate(emp, toBeProcessedFor, false, comp.getFinStartMonth());
			
			// Calculate the Exemptions
			ExemptionCalculator exmpCalc = ResidentTypeFactory.getExemptionCalculator(emp);
			exmpCalc.calculate(emp, empPayroll, comp.getFinStartMonth(), toBeProcessedFor, false);
			
			// Calculate the deductions
			DeductionCalculator deducCalc = ResidentTypeFactory.getDeductionCalculator(emp);
			deducCalc.calculate(emp, empPayroll, toBeProcessedFor, comp.getFinStartMonth(), false);
			
			// Finally calculate the tax to be paid
			TaxCalculator taxCalc = ResidentTypeFactory.getTaxCalculator(emp);
			Double lastPercentage = taxCalc.calculate(emp, empPayroll);
			
			// Process if the tax is to be paid by employer.
			processTaxPaidByEmployer(empPayroll, lastPercentage);
			
			Double income = empPayroll.getTotalIncome();
			Double empeSS = 0D;
			
			List<DeductionsDone> deductionsList = empPayroll.getDeductionsDone();
			for(DeductionsDone dd: deductionsList) {
				if(dd.getType().getName().equalsIgnoreCase(PayhumConstants.EMPLOYEE_SOCIAL_SECURITY)) {
					empeSS = dd.getAmount();
					break;
				}
			}
			
			// Here if the employees (resident and non-res foreigner) are not subject to withhold tax then
			// consider it.
			if(empPayroll.getWithholdTax().compareTo(1) == 0) {
				empPayroll.setNetPay(income - empPayroll.getTaxAmount() - empPayroll.getEmployerSS() - empeSS);
			} else {
				// Else keep the Net pay as taxable Income and let Employee pay the tax and we just tell the amount.
				empPayroll.setNetPay(income - empPayroll.getEmployerSS() - empeSS);
			}
			
			computeDetailsPerPayPeriodForInactive(empPayroll, toBeProcessedFor, payroll, payrollDates, payRuns, comp.getFinStartMonth());
			
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
			Calendar toBeProcessedFor, Payroll payroll, boolean adhoc,
			List<PayrollDate> payrollDates, List<Payroll> payRuns, int finStartMonth) throws Exception {
		boolean isRetroEmp = PayhumUtil.isRetroActive(toBeProcessedFor, empPayroll.getEmployeeId().getHiredate(), finStartMonth);
		
		Integer divNo = payrollDates.size();
		int remainingPaycycles = PayhumUtil.remainingPaycycles(payrollDates, payRuns);
		Double proRate = 1D;
		Double divNoWithProrate = 1D;
		Date hireDate = empPayroll.getEmployeeId().getHiredate();
		Calendar hireDtCal = Calendar.getInstance();
		hireDtCal.setTime(hireDate);
	    // Zero out the hour, minute, second, and millisecond
		hireDtCal.set(Calendar.HOUR_OF_DAY, 0);
		hireDtCal.set(Calendar.MINUTE, 0);
		hireDtCal.set(Calendar.SECOND, 0);
		hireDtCal.set(Calendar.MILLISECOND, 0);
	    
		int currMonth = toBeProcessedFor.get(Calendar.MONTH);
		int hireMonth = hireDtCal.get(Calendar.MONTH);
		int hireYear = hireDtCal.get(Calendar.YEAR);
		int currYear = toBeProcessedFor.get(Calendar.YEAR);
		
		if(currYear == hireYear && currMonth == hireMonth) {
			int hireDay = hireDtCal.get(Calendar.DAY_OF_MONTH);
			
			int diffDays = 1;
			if(hireDay < 31) {
				diffDays = 31 - hireDay;
			}
			
			proRate = diffDays / 30D;
			divNoWithProrate = (remainingPaycycles - 1) + proRate;
		} else {
			// no change in div no
			divNoWithProrate = new Double(divNo);
		}
		
		if(adhoc) {
			EmployeeBonus latestBonus = getLatestBonus(empPayroll, toBeProcessedFor);
			if(latestBonus != null && prevNetPay != 0D) {
				Double diffNetPay = empPayroll.getNetPay() - prevNetPay;
				Double diffTaxAmt = empPayroll.getTaxAmount() - prevTaxAmt;
				Double diffEmpSS = empPayroll.getEmployerSS() - prevEmprSS;
				
				empPayroll.setPaidNetPay(empPayroll.getPaidNetPay() + diffNetPay);
				empPayroll.setPaidTaxAmt(empPayroll.getPaidTaxAmt() + diffTaxAmt);
				empPayroll.setPaidEmprSS(empPayroll.getPaidEmprSS() + diffEmpSS);
				
				// Save to emp_payroll_map table.
				EmpBankAccount empBankAcct = EmpBankAccountFactory.findByEmployeeId(empPayroll.getEmployeeId().getId());
				EmpPayrollMap empPayMap = new EmpPayrollMap();
				empPayMap.setEmppayId(empPayroll);
				empPayMap.setNetPay(diffNetPay);
				empPayMap.setTaxAmount(diffTaxAmt);
				empPayMap.setEmprSocialSec(diffEmpSS);
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
			Double totalTaxAmt = empPayroll.getTaxAmount();
			Double totalNeyPay = empPayroll.getNetPay();
			Double totalEmprSS = empPayroll.getEmployerSS();
			Double totalEmpeSS = 0D;
			Double totalTaxableAmt = empPayroll.getTaxableIncome();
			Double totalAccomAmt = empPayroll.getAccomodationAmount();
			Double totalBasicAllow = 0D;
			
			// get employee contribution of SS
			List<DeductionsDone> deductionsList = empPayroll.getDeductionsDone();
			
			for(DeductionsDone dd: deductionsList) {
				if(dd.getType().getName().equalsIgnoreCase(PayhumConstants.EMPLOYEE_SOCIAL_SECURITY)) {
					totalEmpeSS += dd.getAmount();
					break;
				}
			}
			
			List<ExemptionsDone> exemptionsList = empPayroll.getExemptionsDone();
			
			for(ExemptionsDone ed: exemptionsList) {
				if(ed.getType().getName().equalsIgnoreCase(PayhumConstants.BASIC_ALLOWANCE)) {
					totalBasicAllow += ed.getAmount();
					break;
				}
			}
			
			TypesData currency = empPayroll.getEmployeeId().getCurrency();
			Double currencyCoverVal = 1D;
			
			if(currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_USD)) {
				ConfigData currencyConver = ConfigDataFactory.findByName(PayhumConstants.USD_MMK_CONVER);
				currencyCoverVal = Double.valueOf(currencyConver.getConfigValue());
			} else if(currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_EURO)) {
				ConfigData currencyConver = ConfigDataFactory.findByName(PayhumConstants.EURO_MMK_CONVER);
				currencyCoverVal = Double.valueOf(currencyConver.getConfigValue());
			} else if(currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_POUND)) {
				ConfigData currencyConver = ConfigDataFactory.findByName(PayhumConstants.POUND_MMK_CONVER);
				currencyCoverVal = Double.valueOf(currencyConver.getConfigValue());
			}
			
			Double newOvertimeAmt = empPayroll.getNewOvertimeAmt();
			Double newOtherIncome = empPayroll.getNewOtherIncome();
			Double pendingNeyPay = totalNeyPay - empPayroll.getPaidNetPay() - newOtherIncome - newOvertimeAmt;
			Double pendingTaxAmt = totalTaxAmt - empPayroll.getPaidTaxAmt();
			Double pendingEmprSS = totalEmprSS - empPayroll.getPaidEmprSS();
			Double pendingEmpeSS = totalEmpeSS - empPayroll.getPaidEmpeSS();
			Double pendingTaxableAmt = totalTaxableAmt - empPayroll.getPaidTaxableAmt();
			Double pendingAccomAmt = totalAccomAmt - empPayroll.getPaidAccomAmt();
			Double pendingBasicAllow = totalBasicAllow - empPayroll.getPaidBasicAllow();
			
			Double thisMonthPaidNetPay = 0D;
			Double thisMonthPaidTaxAmt = 0D;
			Double thisMonthPaidEmpeSS = 0D;
			Double thisMonthPaidEmprSS = 0D;
			Double thisMonthPaidTaxableAmt = 0D;
			Double thisMonthPaidAccomAmt = 0D;
			Double thisMonthPaidBasicAllow = 0D;
			if(currYear == hireYear && currMonth == hireMonth) {
				int hireDay = hireDtCal.get(Calendar.DAY_OF_MONTH);
				
				int diffDays = 1;
				if(hireDay < 31) {
					diffDays = 31 - hireDay;
				}
				
				Double divideBy = (remainingPaycycles - 1) + diffDays / 30D;
				thisMonthPaidNetPay = ((pendingNeyPay / divideBy) / 30 ) * diffDays + newOvertimeAmt + newOtherIncome;
				thisMonthPaidTaxAmt = ((pendingTaxAmt / divideBy) / 30 ) * diffDays;
				thisMonthPaidTaxableAmt = ((pendingTaxableAmt / divideBy) / 30 ) * diffDays;
				thisMonthPaidAccomAmt = ((pendingAccomAmt / divideBy) / 30 ) * diffDays;
				thisMonthPaidBasicAllow = ((pendingBasicAllow / divideBy) / 30 ) * diffDays;
				thisMonthPaidEmpeSS = totalEmpeSS / remainingPaycycles;
				thisMonthPaidEmprSS = totalEmprSS / remainingPaycycles;
				
				Double extraEmpeSS = (((thisMonthPaidEmpeSS / divideBy) / 30 ) * (30 - diffDays)) * (remainingPaycycles - 1);
				thisMonthPaidNetPay = thisMonthPaidNetPay - extraEmpeSS;
				
				empPayroll.setPaidNetPay(empPayroll.getPaidNetPay() + thisMonthPaidNetPay );
				empPayroll.setPaidTaxAmt(empPayroll.getPaidTaxAmt() + thisMonthPaidTaxAmt);
				empPayroll.setPaidEmpeSS(thisMonthPaidEmpeSS);
				empPayroll.setPaidEmprSS(thisMonthPaidEmprSS);
				empPayroll.setPaidTaxableAmt(thisMonthPaidTaxableAmt);
				empPayroll.setPaidAccomAmt(thisMonthPaidAccomAmt);
				empPayroll.setPaidBasicAllow(thisMonthPaidBasicAllow);
			} else if(isRetroEmp) {
				thisMonthPaidNetPay = pendingNeyPay / remainingPaycycles + newOvertimeAmt + newOtherIncome;
				thisMonthPaidTaxAmt = pendingTaxAmt / remainingPaycycles;
				thisMonthPaidEmpeSS = pendingEmpeSS / (remainingPaycycles + 1);
				thisMonthPaidEmprSS = pendingEmprSS / (remainingPaycycles + 1);
				thisMonthPaidTaxableAmt = pendingTaxableAmt / remainingPaycycles;
				thisMonthPaidAccomAmt = pendingAccomAmt / remainingPaycycles;
				thisMonthPaidBasicAllow = pendingBasicAllow / remainingPaycycles;
				
				// If retro lets get 2 months SS
				thisMonthPaidEmpeSS = thisMonthPaidEmpeSS * 2;
				thisMonthPaidEmprSS = thisMonthPaidEmprSS * 2;
				
				empPayroll.setPaidNetPay(empPayroll.getPaidNetPay() + thisMonthPaidNetPay);
				empPayroll.setPaidTaxAmt(empPayroll.getPaidTaxAmt() + thisMonthPaidTaxAmt);
				empPayroll.setPaidEmpeSS(empPayroll.getPaidEmpeSS() + thisMonthPaidEmpeSS);
				empPayroll.setPaidEmprSS(empPayroll.getPaidEmprSS() + thisMonthPaidEmprSS);
				empPayroll.setPaidTaxableAmt(empPayroll.getPaidTaxableAmt() + thisMonthPaidTaxableAmt);
				empPayroll.setPaidAccomAmt(empPayroll.getPaidAccomAmt() + thisMonthPaidAccomAmt);
				empPayroll.setPaidBasicAllow(empPayroll.getPaidBasicAllow() + thisMonthPaidBasicAllow);
			} else {
				thisMonthPaidNetPay = pendingNeyPay / remainingPaycycles + newOvertimeAmt + newOtherIncome;
				thisMonthPaidTaxAmt = pendingTaxAmt / remainingPaycycles;
				thisMonthPaidEmpeSS = pendingEmpeSS / remainingPaycycles;
				thisMonthPaidEmprSS = pendingEmprSS / remainingPaycycles;
				thisMonthPaidTaxableAmt = pendingTaxableAmt / remainingPaycycles;
				thisMonthPaidAccomAmt = pendingAccomAmt / remainingPaycycles;
				thisMonthPaidBasicAllow = pendingBasicAllow / remainingPaycycles;
				
				// Now if the employee is USD, then his number for SS should be 5 and 3 only.
				if(currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_USD)) {
					Double valForEmprSS = 5D;
					Double valForEmpeSS = 3D;
					
					Double currValForEmprSS = thisMonthPaidEmprSS / currencyCoverVal;
					Double currValForEmpeSS = thisMonthPaidEmpeSS / currencyCoverVal;
					
					Double diffForEmprSS = currValForEmprSS - valForEmprSS;
					Double diffForEmpeSS = currValForEmpeSS - valForEmpeSS;
					
					thisMonthPaidNetPay += diffForEmprSS * currencyCoverVal;
					thisMonthPaidNetPay += diffForEmpeSS * currencyCoverVal;
					
					thisMonthPaidEmpeSS = valForEmpeSS * currencyCoverVal;
					thisMonthPaidEmprSS = valForEmprSS * currencyCoverVal;
				} 
				
				empPayroll.setPaidNetPay(empPayroll.getPaidNetPay() + thisMonthPaidNetPay);
				empPayroll.setPaidTaxAmt(empPayroll.getPaidTaxAmt() + thisMonthPaidTaxAmt);
				empPayroll.setPaidEmpeSS(empPayroll.getPaidEmpeSS() + thisMonthPaidEmpeSS);
				empPayroll.setPaidEmprSS(empPayroll.getPaidEmprSS() + thisMonthPaidEmprSS);
				empPayroll.setPaidTaxableAmt(empPayroll.getPaidTaxableAmt() + thisMonthPaidTaxableAmt);
				empPayroll.setPaidAccomAmt(empPayroll.getPaidAccomAmt() + thisMonthPaidAccomAmt);
				empPayroll.setPaidBasicAllow(empPayroll.getPaidBasicAllow() + thisMonthPaidBasicAllow);
			}
			
			// Save to emp_payroll_map table.
			EmpBankAccount empBankAcct = EmpBankAccountFactory.findByEmployeeId(empPayroll.getEmployeeId().getId());
			EmpPayrollMap empPayMap = new EmpPayrollMap();
			empPayMap.setEmppayId(empPayroll);
			empPayMap.setNetPay(thisMonthPaidNetPay);
			empPayMap.setTaxAmount(thisMonthPaidTaxAmt);
			empPayMap.setTaxableAmt(thisMonthPaidTaxableAmt);
			empPayMap.setEmpeSocialSec(thisMonthPaidEmpeSS);
			empPayMap.setEmprSocialSec(thisMonthPaidEmprSS);
			empPayMap.setAccomAmt(thisMonthPaidAccomAmt);
			empPayMap.setBasicAllow(thisMonthPaidBasicAllow);
			
			empPayMap.setOvertimeAmt(newOvertimeAmt);
			empPayroll.clearNewOvertimeAmt();
			
			empPayMap.setOtherIncome(newOtherIncome);
			empPayroll.clearNewOtherIncome();
			
			if(isRetroEmp) {
				empPayMap.setRetroBaseSal(empPayroll.getRetroBaseSal());
			} else {
				empPayMap.setRetroBaseSal(0D);
			}
			
			Double baseSalforMonth = empPayroll.getBaseSalary() - empPayroll.getPaidBaseSalary();
			baseSalforMonth = baseSalforMonth / remainingPaycycles;
			if(proRate.compareTo(1D) == 0) {
				empPayMap.setBaseSalary(baseSalforMonth);
				empPayroll.addPaidBaseSalary(baseSalforMonth);
			} else {
				empPayMap.setBaseSalary((empPayroll.getBaseSalary() / divNoWithProrate) * proRate);
				empPayroll.addPaidBaseSalary((empPayroll.getBaseSalary() / divNoWithProrate) * proRate);
			}
			
			empPayMap.setBonus(empPayroll.getNewBonus());
			empPayroll.clearNewBonus();
			
			empPayMap.setCurrencyConverRate(currencyCoverVal);
			
			ConfigData salPayDate = ConfigDataFactory.findByName(PayhumConstants.SAL_PAY_DATE);
			empPayMap.setSalPayDate(Integer.parseInt(salPayDate.getConfigValue()));
			
			empPayMap.setPayrollId(payroll);
			
			if(empBankAcct != null) {
				empPayMap.setMode(1);
			} else {
				empPayMap.setMode(0);
			}
			
			if(System.getProperty("DRYRUN") == null 
			|| ! System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
				PayrollFactory.insertEmpPayrollMap(empPayMap);
			}
		}
	}

	private void computeDetailsPerPayPeriodForInactive(EmployeePayroll empPayroll,
			Calendar toBeProcessedFor, Payroll payroll,
			List<PayrollDate> payrollDates, List<Payroll> payRuns, int finStartMonth) throws Exception {
		boolean isRetroEmp = PayhumUtil.isRetroActive(toBeProcessedFor, empPayroll.getEmployeeId().getHiredate(), finStartMonth);
		
		Integer divNo = 1;
		Double proRate = 1D;
		Double divNoWithProrate = 1D;
		Date hireDate = empPayroll.getEmployeeId().getHiredate();
		Date lastDate = empPayroll.getEmployeeId().getInactiveDate();
		
		Calendar hireDtCal = Calendar.getInstance();
		hireDtCal.setTime(hireDate);
	    // Zero out the hour, minute, second, and millisecond
		hireDtCal.set(Calendar.HOUR_OF_DAY, 0);
		hireDtCal.set(Calendar.MINUTE, 0);
		hireDtCal.set(Calendar.SECOND, 0);
		hireDtCal.set(Calendar.MILLISECOND, 0);
	    
		Calendar lastDtCal = Calendar.getInstance();
		lastDtCal.setTime(lastDate);
	    // Zero out the hour, minute, second, and millisecond
		lastDtCal.set(Calendar.HOUR_OF_DAY, 0);
		lastDtCal.set(Calendar.MINUTE, 0);
		lastDtCal.set(Calendar.SECOND, 0);
		lastDtCal.set(Calendar.MILLISECOND, 0);
	    
		int currMonth = toBeProcessedFor.get(Calendar.MONTH);
		int hireMonth = hireDtCal.get(Calendar.MONTH);
		int hireYear = hireDtCal.get(Calendar.YEAR);
		int currYear = toBeProcessedFor.get(Calendar.YEAR);
		int lastMonth = lastDtCal.get(Calendar.MONTH);
		int lastYear = lastDtCal.get(Calendar.YEAR);
		int lastDay = lastDtCal.get(Calendar.DAY_OF_MONTH);
		
		if(currYear == hireYear && currMonth == hireMonth) {
			int hireDay = hireDtCal.get(Calendar.DAY_OF_MONTH);
			
			int diffDays = 1;
			if(hireDay < 31) {
				diffDays = lastDay + 1 - hireDay;
			}
			
			proRate = diffDays / (double)lastDay;
			divNoWithProrate = divNo + proRate;
		} else {
			// no change in div no
			divNoWithProrate = new Double(divNo);
		}
		
		Double totalTaxAmt = empPayroll.getTaxAmount();
		Double totalNeyPay = empPayroll.getNetPay();
		Double totalEmprSS = empPayroll.getEmployerSS();
		Double totalEmpeSS = 0D;
		Double totalTaxableAmt = empPayroll.getTaxableIncome();
		Double totalAccomAmt = empPayroll.getAccomodationAmount();
		Double totalBasicAllow = 0D;
		
		// get employee contribution of SS
		List<DeductionsDone> deductionsList = empPayroll.getDeductionsDone();
		
		for(DeductionsDone dd: deductionsList) {
			if(dd.getType().getName().equalsIgnoreCase(PayhumConstants.EMPLOYEE_SOCIAL_SECURITY)) {
				totalEmpeSS += dd.getAmount();
				break;
			}
		}
		
		List<ExemptionsDone> exemptionsList = empPayroll.getExemptionsDone();
		
		for(ExemptionsDone ed: exemptionsList) {
			if(ed.getType().getName().equalsIgnoreCase(PayhumConstants.BASIC_ALLOWANCE)) {
				totalBasicAllow += ed.getAmount();
				break;
			}
		}
		
		int remainingPaycycles = 1;
		
		TypesData currency = empPayroll.getEmployeeId().getCurrency();
		Double currencyCoverVal = 1D;
		
		if(currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_USD)) {
			ConfigData currencyConver = ConfigDataFactory.findByName(PayhumConstants.USD_MMK_CONVER);
			currencyCoverVal = Double.valueOf(currencyConver.getConfigValue());
		} else if(currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_EURO)) {
			ConfigData currencyConver = ConfigDataFactory.findByName(PayhumConstants.EURO_MMK_CONVER);
			currencyCoverVal = Double.valueOf(currencyConver.getConfigValue());
		} else if(currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_POUND)) {
			ConfigData currencyConver = ConfigDataFactory.findByName(PayhumConstants.POUND_MMK_CONVER);
			currencyCoverVal = Double.valueOf(currencyConver.getConfigValue());
		}
		
		Double newOvertimeAmt = empPayroll.getNewOvertimeAmt();
		Double newOtherIncome = empPayroll.getNewOtherIncome();
		Double pendingNeyPay = totalNeyPay - empPayroll.getPaidNetPay() - newOtherIncome - newOvertimeAmt;
		Double pendingTaxAmt = totalTaxAmt - empPayroll.getPaidTaxAmt();
		Double pendingEmprSS = totalEmprSS - empPayroll.getPaidEmprSS();
		Double pendingEmpeSS = totalEmpeSS - empPayroll.getPaidEmpeSS();
		Double pendingTaxableAmt = totalTaxableAmt - empPayroll.getPaidTaxableAmt();
		Double pendingAccomAmt = totalAccomAmt - empPayroll.getPaidAccomAmt();
		Double pendingBasicAllow = totalBasicAllow - empPayroll.getPaidBasicAllow();
		
		Double thisMonthPaidNetPay = 0D;
		Double thisMonthPaidTaxAmt = 0D;
		Double thisMonthPaidEmpeSS = 0D;
		Double thisMonthPaidEmprSS = 0D;
		Double thisMonthPaidTaxableAmt = 0D;
		Double thisMonthPaidAccomAmt = 0D;
		Double thisMonthPaidBasicAllow = 0D;
		if(currYear == hireYear && currMonth == hireMonth) {
			int hireDay = hireDtCal.get(Calendar.DAY_OF_MONTH);
			
			int diffDays = 1;
			if(hireDay < 31) {
				diffDays = lastDay + 1 - hireDay;
			}
			
			Double divideBy = (remainingPaycycles - 1) + diffDays / (double) lastDay;
			thisMonthPaidNetPay = ((pendingNeyPay / divideBy) / 30 ) * diffDays + newOvertimeAmt + newOtherIncome;
			thisMonthPaidTaxAmt = ((pendingTaxAmt / divideBy) / 30 ) * diffDays;
			thisMonthPaidTaxableAmt = ((pendingTaxableAmt/ divideBy) / 30 ) * diffDays;
			thisMonthPaidEmpeSS = totalEmpeSS / remainingPaycycles;
			thisMonthPaidEmprSS = totalEmprSS / remainingPaycycles;
			thisMonthPaidAccomAmt = ((pendingAccomAmt / divideBy) / 30 ) * diffDays;
			thisMonthPaidBasicAllow = ((pendingBasicAllow / divideBy) / 30 ) * diffDays;
			
			empPayroll.setPaidNetPay(empPayroll.getPaidNetPay() + thisMonthPaidNetPay );
			empPayroll.setPaidTaxAmt(empPayroll.getPaidTaxAmt() + thisMonthPaidTaxAmt);
			empPayroll.setPaidEmpeSS(thisMonthPaidEmpeSS);
			empPayroll.setPaidEmprSS(thisMonthPaidEmprSS);
			empPayroll.setPaidTaxableAmt(empPayroll.getPaidTaxableAmt() + thisMonthPaidTaxableAmt);
			empPayroll.setPaidAccomAmt(thisMonthPaidAccomAmt);
			empPayroll.setPaidBasicAllow(thisMonthPaidBasicAllow);
		} else if(isRetroEmp) {
			thisMonthPaidNetPay = pendingNeyPay / remainingPaycycles + newOvertimeAmt + newOtherIncome;
			thisMonthPaidTaxAmt = pendingTaxAmt / remainingPaycycles;
			thisMonthPaidTaxableAmt = pendingTaxableAmt / remainingPaycycles;
			thisMonthPaidEmpeSS = pendingEmpeSS / (remainingPaycycles + 1);
			thisMonthPaidEmprSS = pendingEmprSS / (remainingPaycycles + 1);
			thisMonthPaidAccomAmt = pendingAccomAmt / remainingPaycycles;
			thisMonthPaidBasicAllow = pendingBasicAllow / remainingPaycycles;
			
			// If retro lets get 2 months SS
			thisMonthPaidEmpeSS = thisMonthPaidEmpeSS * 2;
			thisMonthPaidEmprSS = thisMonthPaidEmprSS * 2;
			
			empPayroll.setPaidNetPay(empPayroll.getPaidNetPay() + thisMonthPaidNetPay);
			empPayroll.setPaidTaxAmt(empPayroll.getPaidTaxAmt() + thisMonthPaidTaxAmt);
			empPayroll.setPaidEmpeSS(empPayroll.getPaidEmpeSS() + thisMonthPaidEmpeSS);
			empPayroll.setPaidEmprSS(empPayroll.getPaidEmprSS() + thisMonthPaidEmprSS);
			empPayroll.setPaidTaxableAmt(empPayroll.getPaidTaxableAmt() + thisMonthPaidTaxableAmt);
			empPayroll.setPaidAccomAmt(empPayroll.getPaidAccomAmt() + thisMonthPaidAccomAmt);
			empPayroll.setPaidBasicAllow(empPayroll.getPaidBasicAllow() + thisMonthPaidBasicAllow);
		} else {
			thisMonthPaidNetPay = pendingNeyPay / remainingPaycycles + newOvertimeAmt + newOtherIncome;
			thisMonthPaidTaxAmt = pendingTaxAmt / remainingPaycycles;
			thisMonthPaidTaxableAmt = pendingTaxableAmt / remainingPaycycles;
			thisMonthPaidEmpeSS = pendingEmpeSS / remainingPaycycles;
			thisMonthPaidEmprSS = pendingEmprSS / remainingPaycycles;
			thisMonthPaidAccomAmt = pendingAccomAmt / remainingPaycycles;
			thisMonthPaidBasicAllow = pendingBasicAllow / remainingPaycycles;
			
			// Now if the employee is USD, then his number for SS should be 5 and 3 only.
			if(currency.getName().equalsIgnoreCase(PayhumConstants.CURRENCY_USD)) {
				Double valForEmprSS = 5D;
				Double valForEmpeSS = 3D;
				
				Double currValForEmprSS = thisMonthPaidEmprSS / currencyCoverVal;
				Double currValForEmpeSS = thisMonthPaidEmpeSS / currencyCoverVal;
				
				Double diffForEmprSS = currValForEmprSS - valForEmprSS;
				Double diffForEmpeSS = currValForEmpeSS - valForEmpeSS;
				
				thisMonthPaidNetPay += diffForEmprSS * currencyCoverVal;
				thisMonthPaidNetPay += diffForEmpeSS * currencyCoverVal;
				
				thisMonthPaidEmpeSS = valForEmpeSS * currencyCoverVal;
				thisMonthPaidEmprSS = valForEmprSS * currencyCoverVal;
			} 
			
			empPayroll.setPaidNetPay(empPayroll.getPaidNetPay() + thisMonthPaidNetPay);
			empPayroll.setPaidTaxAmt(empPayroll.getPaidTaxAmt() + thisMonthPaidTaxAmt);
			empPayroll.setPaidEmpeSS(empPayroll.getPaidEmpeSS() + thisMonthPaidEmpeSS);
			empPayroll.setPaidEmprSS(empPayroll.getPaidEmprSS() + thisMonthPaidEmprSS);
			empPayroll.setPaidTaxableAmt(empPayroll.getPaidTaxableAmt() + thisMonthPaidTaxableAmt);
			empPayroll.setPaidAccomAmt(empPayroll.getPaidAccomAmt() + thisMonthPaidAccomAmt);
			empPayroll.setPaidBasicAllow(empPayroll.getPaidBasicAllow() + thisMonthPaidBasicAllow);
		}
		
		// Save to emp_payroll_map table.
		EmpBankAccount empBankAcct = EmpBankAccountFactory.findByEmployeeId(empPayroll.getEmployeeId().getId());
		EmpPayrollMap empPayMap = new EmpPayrollMap();
		empPayMap.setEmppayId(empPayroll);
		empPayMap.setNetPay(thisMonthPaidNetPay);
		empPayMap.setTaxAmount(thisMonthPaidTaxAmt);
		empPayMap.setTaxableAmt(thisMonthPaidTaxableAmt);
		empPayMap.setEmpeSocialSec(thisMonthPaidEmpeSS);
		empPayMap.setEmprSocialSec(thisMonthPaidEmprSS);
		empPayMap.setAccomAmt(thisMonthPaidAccomAmt);
		empPayMap.setBasicAllow(thisMonthPaidBasicAllow);
		
		empPayMap.setOvertimeAmt(newOvertimeAmt);
		empPayroll.clearNewOvertimeAmt();
		
		empPayMap.setOtherIncome(newOtherIncome);
		empPayroll.clearNewOtherIncome();
		
		if(isRetroEmp) {
			empPayMap.setRetroBaseSal(empPayroll.getRetroBaseSal());
		} else {
			empPayMap.setRetroBaseSal(0D);
		}
		
		Double baseSalforMonth = empPayroll.getBaseSalary() - empPayroll.getPaidBaseSalary();
		if(proRate.compareTo(1D) == 0) {
			empPayMap.setBaseSalary(baseSalforMonth);
			empPayroll.addPaidBaseSalary(baseSalforMonth);
		} else {
			empPayMap.setBaseSalary((empPayroll.getBaseSalary() / divNoWithProrate) * proRate);
			empPayroll.addPaidBaseSalary((empPayroll.getBaseSalary() / divNoWithProrate) * proRate);
		}
		
		empPayMap.setBonus(empPayroll.getNewBonus());
		empPayroll.clearNewBonus();
		
		empPayMap.setCurrencyConverRate(currencyCoverVal);
		
		ConfigData salPayDate = ConfigDataFactory.findByName(PayhumConstants.SAL_PAY_DATE);
		empPayMap.setSalPayDate(Integer.parseInt(salPayDate.getConfigValue()));
		
		empPayMap.setPayrollId(payroll);
		
		if(empBankAcct != null) {
			empPayMap.setMode(1);
		} else {
			empPayMap.setMode(0);
		}
		
		if(System.getProperty("DRYRUN") == null 
		|| ! System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
			PayrollFactory.insertEmpPayrollMap(empPayMap);
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
}

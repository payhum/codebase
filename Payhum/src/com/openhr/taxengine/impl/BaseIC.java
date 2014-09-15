package com.openhr.taxengine.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openhr.common.PayhumConstants;
import com.openhr.data.Benefit;
import com.openhr.data.ConfigData;
import com.openhr.data.EmpPayrollMap;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeBonus;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.EmployeeSalary;
import com.openhr.data.Holidays;
import com.openhr.data.LeaveRequest;
import com.openhr.data.LeaveType;
import com.openhr.data.OverTime;
import com.openhr.data.OverTimePayRateData;
import com.openhr.data.TypesData;
import com.openhr.factories.BenefitFactory;
import com.openhr.factories.ConfigDataFactory;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.HolidaysFactory;
import com.openhr.factories.LeaveRequestFactory;
import com.openhr.factories.OverTimeFactory;
import com.openhr.taxengine.IncomeCalculator;
import com.openhr.taxengine.TaxDetails;
import com.util.payhumpackages.PayhumUtil;

public class BaseIC implements IncomeCalculator {

	@Override
	public EmployeePayroll calculate(Employee emp, Calendar currDtCal, boolean active, int finStartMonth) {
		/* Total Income involves the following:
		 * - Base Salary
		 * - Bonus
		 * - 12.5% of AGP (free accommodation provided by employer + fully furnished)
		 * 		OR
		 * - 10% of AGP (free accommodation provided by employer + not furnished)
		 * - Taxable income from overseas (for local employees)
		 * - Social Security paid by employer
		 * - Other benefits (allowances, etc..)
		 * 
		 * 
		 * Annual Gross Product
		 * - Base Salary
		 * - Bonus
		 * - Allowances (medical, transport)
		 */
		EmployeePayroll empPayroll = EmpPayTaxFactroy.findEmpPayrollbyEmpID(emp);
		TaxDetails taxDetails = TaxDetails.getTaxDetailsForCountry();
		
		EmployeeSalary latestEmpSal = null;
		
		// First check if there is a BaseSalary change and then update the baseSalary in Payroll table.
		if(active) {
			latestEmpSal = checkAndUpdateBaseSalary(emp, empPayroll, currDtCal, finStartMonth);
		} else {
			latestEmpSal = computeBaseSalaryForInactive(emp, empPayroll, currDtCal, finStartMonth);
		}
		
		// Check if there is a Bonus allocated in the current month and then update the bonus in Payroll table.
		checkAndUpdateBonus(empPayroll, currDtCal);
		
		//Process the other benefits and add it to the allowances of the EmployeePayroll
		Double allowancesAmt = 0D;
		List<Benefit> empBenefits = BenefitFactory.findByEmpId(empPayroll.getEmployeeId());
		for(Benefit bf : empBenefits){
			allowancesAmt += bf.getAmount();
		}
		
		//convert allowance from user's currency to MMK
		allowancesAmt = getAmountInMMK(emp.getCurrency(), allowancesAmt);
		
		empPayroll.setAllowances(allowancesAmt);
		
		Double annualGrossPay = empPayroll.getBaseSalary();
		annualGrossPay += empPayroll.getBonus();
		annualGrossPay += allowancesAmt;
		annualGrossPay += empPayroll.getRetroBaseSal();
		
		empPayroll.setGrossSalary(annualGrossPay);
		
		Double totalIncome = annualGrossPay;
		
		Double accomPercentage = taxDetails.getAccomodationPercentage(empPayroll.getAccomodationType().getName()) ;
		
		if(accomPercentage != null && accomPercentage > 0) {
			Double accomAmt = accomPercentage * annualGrossPay / 100;
			totalIncome += accomAmt;
			
			empPayroll.setAccomodationAmount(accomAmt);
		}
		
		// Process Overtime Income
		calculateOvertimeAmount(emp, empPayroll, currDtCal);
		
		// Process loss of Pay
		Double leaveLoss = calculateLossOfPay(emp, empPayroll, currDtCal, latestEmpSal);

		// process otherincome
		Double newOtherIncome = getAmountInMMK(emp.getCurrency(), empPayroll.getNewOtherIncome());
		empPayroll.setOtherIncome(empPayroll.getOtherIncome() + newOtherIncome);
		
		// empPayroll.setTotalIncome(totalIncome - leaveLoss );
		empPayroll.setTotalIncome(totalIncome - leaveLoss + empPayroll.getOtherIncome() + empPayroll.getOvertimeamt());
		
		return empPayroll;
	}	

	private Double calculateLossOfPay(Employee emp, EmployeePayroll empPayroll,
			Calendar currDtCal, EmployeeSalary latestEmpSal) {
		List<LeaveRequest> leaveRequestList = LeaveRequestFactory.findByEmployeeId(emp.getId());
		Map<LeaveType, Double> leaveTypeTakenMap = new HashMap<LeaveType, Double>();
		
		double noOfLoss = 0;
		
		for(LeaveRequest lr : leaveRequestList){
			if(leaveTypeTakenMap.containsKey(lr.getLeaveTypeId())) {
				Double cnt = leaveTypeTakenMap.get(lr.getLeaveTypeId());
				leaveTypeTakenMap.put(lr.getLeaveTypeId(), cnt + lr.getNoOfDays());
			} else {
				leaveTypeTakenMap.put(lr.getLeaveTypeId(), lr.getNoOfDays());
			}
		}
		
		for(LeaveType lt: leaveTypeTakenMap.keySet()) {
			Double taken = leaveTypeTakenMap.get(lt);
			Integer cap = lt.getDayCap();
			
			if(taken.compareTo(new Double(cap)) > 0) {
				noOfLoss += (taken - cap);
			}
		}
		
		if(noOfLoss > 0) {
			Double daySal = getDaySal(latestEmpSal.getBasesalary(), currDtCal);
			Double amountLoss = daySal * noOfLoss;
			
			empPayroll.setLeaveLoss(amountLoss);
			return amountLoss;
		}
		
		return 0D;
	}

	private Double getDaySal(Double sal, Calendar currDtCal) {
        Double baseSal = sal;
		
		// Per Month
		baseSal = baseSal / 12;
		
		// Per Day
		baseSal = baseSal / 30;
		
		return baseSal;
	}

	private void calculateOvertimeAmount(Employee emp,
			EmployeePayroll empPayroll, Calendar currDtCal) {
		// Get the current month and see how many overtime records exist for the current month
		// and then get the payrate for overtime type and compute the amount.
		List<OverTime> empOvertimeList =  OverTimeFactory.findByEmployeeId(emp.getId());
		List<OverTimePayRateData> overtimePayRateList = EmpPayTaxFactroy.findOverTimeRateAll();
		Double weekdayRate = 0D;
		Double weekendRate = 0D;
		Double holidayRate = 0D;
		
		for(OverTimePayRateData otp: overtimePayRateList) {
			if(otp.getDayGroupName().equalsIgnoreCase(PayhumConstants.WEEKDAY)) {
				weekdayRate = otp.getGrossPercent();
			} else if(otp.getDayGroupName().equalsIgnoreCase(PayhumConstants.WEEKEND)) {
				weekendRate = otp.getGrossPercent();
			} else if(otp.getDayGroupName().equalsIgnoreCase(PayhumConstants.HOLIDAYS)) {
				holidayRate = otp.getGrossPercent();
			}
		}

		Double amount = 0D;
		for(OverTime ot : empOvertimeList) {
			Date otDate = new Date(ot.getOverTimeDate());
			Calendar otCal = Calendar.getInstance();
			otCal.setTime(otDate);
			
			if(otCal.get(Calendar.MONTH) == currDtCal.get(Calendar.MONTH)) {
				// Its the current month.
				if(ot.getStatus() == PayhumConstants.APPROVED) {
					// Its an approved OT.
					Double overtimeHrs = ot.getNoOfHours();
					
					// Check if the requested date is a weekday, weekend or holiday
					int dayOfWeek = otCal.get(Calendar.DAY_OF_WEEK);
					if(dayOfWeek == Calendar.SUNDAY) {
						Double salPerHour = getSalaryPerHour(empPayroll, currDtCal);
						salPerHour = weekendRate * salPerHour / 100;
						amount += overtimeHrs * salPerHour;
					} else if(dayOfWeek != Calendar.SUNDAY) {
						Double salPerHour = getSalaryPerHour(empPayroll, currDtCal);
						salPerHour = weekdayRate * salPerHour / 100;
						amount += overtimeHrs * salPerHour;
					} else if(isDateHoliday(currDtCal)) {
						Double salPerHour = getSalaryPerHour(empPayroll, currDtCal);
						salPerHour = holidayRate * salPerHour / 100;
						amount += overtimeHrs * salPerHour;
					}
				}
			}
		}
		
		empPayroll.setNewOvertimeAmt(empPayroll.getNewOvertimeAmt() + amount);
		empPayroll.addOvertimeamt(getAmountInMMK(emp.getCurrency(), empPayroll.getNewOvertimeAmt()));
	}

	private boolean isDateHoliday(Calendar currDtCal) {
		List<Holidays> holidays = HolidaysFactory.findAll();

		for(Holidays holiday : holidays) {
			Date holDate = new Date(holiday.getDate());
			Calendar holCal = Calendar.getInstance();
			holCal.setTime(holDate);
			
			if(holCal.get(Calendar.YEAR) == currDtCal.get(Calendar.YEAR)
			  && holCal.get(Calendar.MONTH) == currDtCal.get(Calendar.MONTH)
			  && holCal.get(Calendar.DAY_OF_MONTH) == currDtCal.get(Calendar.DAY_OF_MONTH)) {
				return true;
			}
		}
		
		return false;
	}

	private Double getSalaryPerHour(EmployeePayroll empPayroll,
			Calendar currDtCal) {
		Double baseSal = getDaySal(empPayroll.getBaseSalary(), currDtCal);
		
		// Per hour
		baseSal = baseSal / 8;
		
		return baseSal;
	}

	private EmployeeSalary computeBaseSalaryForInactive(Employee emp,
			EmployeePayroll empPayroll, Calendar payRollCal, int finStartMonth) {
		List<EmployeeSalary> empSalList = EmpPayTaxFactroy.findEmpSalary(empPayroll.getEmployeeId());

		EmployeeSalary latestEmpSal = null;
		
		Calendar currDtCal = Calendar.getInstance();
		currDtCal.setTime(payRollCal.getTime());
		// Zero out the hour, minute, second, and millisecond
	    currDtCal.set(Calendar.HOUR_OF_DAY, 0);
	    currDtCal.set(Calendar.MINUTE, 0);
	    currDtCal.set(Calendar.SECOND, 0);
	    currDtCal.set(Calendar.MILLISECOND, 0);
	    currDtCal.set(Calendar.DAY_OF_MONTH, 1);

	    Double totalSal = 0D;
	    
	    for(EmployeeSalary empSal : empSalList) {
			Date effectiveDate = empSal.getFromdate();
			Date toDate = empSal.getTodate();
			
			Calendar effDtCal = Calendar.getInstance();
		    effDtCal.setTime(effectiveDate);
		    // Zero out the hour, minute, second, and millisecond
		    effDtCal.set(Calendar.HOUR_OF_DAY, 0);
		    effDtCal.set(Calendar.MINUTE, 0);
		    effDtCal.set(Calendar.SECOND, 0);
		    effDtCal.set(Calendar.MILLISECOND, 0);
		    effDtCal.set(Calendar.DAY_OF_MONTH, 1);
		    
		    Calendar toDtCal = Calendar.getInstance();
		    toDtCal.setTime(toDate);
		    // Zero out the hour, minute, second, and millisecond
		    toDtCal.set(Calendar.HOUR_OF_DAY, 0);
		    toDtCal.set(Calendar.MINUTE, 0);
		    toDtCal.set(Calendar.SECOND, 0);
		    toDtCal.set(Calendar.MILLISECOND, 0);
		    toDtCal.set(Calendar.DAY_OF_MONTH, 1);
		    
		    Double salAmount = getAmountInMMK(empSal.getEmployeeId().getCurrency(), empSal.getBasesalary());
			if(toDate.compareTo(effectiveDate) != 0) {
				// Not a last record
				int toDtMonth = toDtCal.get(Calendar.MONTH) + 1; 
				int effDtMonth = effDtCal.get(Calendar.MONTH) + 1;
		
				if(toDtMonth > effDtMonth) {
					int count = toDtMonth - effDtMonth;
					Double sal = salAmount/12 * count;
					totalSal += sal;
				} else {
					int count = effDtMonth - 12;
					count += toDtMonth;
					
					Double sal = salAmount/12 * count;
					totalSal += sal;
				}
			} else {
				// Last record
				Date exitDate = emp.getInactiveDate();
				Calendar exitDtCal  = Calendar.getInstance();
				exitDtCal.setTime(exitDate);
				
				int noOfDays = exitDtCal.get(Calendar.DAY_OF_MONTH);
				int exitMonth = exitDtCal.get(Calendar.MONTH);
				
				if(noOfDays > 30)
					noOfDays = 30;
				
				Double monthSal = salAmount/12;
				Double daySal = monthSal / 30;
				
				Double amount = daySal * noOfDays;
				
				if(totalSal.compareTo(0D) == 0) {
					int remainingMonths = PayhumUtil.remainingMonths(currDtCal, finStartMonth);
					totalSal = empPayroll.getBaseSalary() - monthSal * remainingMonths;
					totalSal += amount;
				} else {
					int effDtMonth = effDtCal.get(Calendar.MONTH);
					int diff = exitMonth - effDtMonth;
					
					totalSal += monthSal * diff; 
					totalSal += amount;
				}
				
				latestEmpSal = empSal;
			}
		}
	    
	    empPayroll.setBaseSalary(totalSal);
	    
	    return latestEmpSal;
	}

	// Private Methods
	private void checkAndUpdateBonus(EmployeePayroll empPayroll,
			Calendar payRollCal) {
		List<EmployeeBonus> empBonusList = EmpPayTaxFactroy.findEmpBonus(empPayroll.getEmployeeId());
		EmployeeBonus latestEmpBonus = null;
		Calendar currDtCal = Calendar.getInstance();
		currDtCal.setTime(payRollCal.getTime());
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
	    
	    if(latestEmpBonus != null) {
	    	// There is a bonus, so lets update the emp payroll with it.
	    	Double existingBonusAmt = empPayroll.getBonus();
	    	Double newBouns = getAmountInMMK(latestEmpBonus.getEmployeeId().getCurrency(), latestEmpBonus.getAmount());
	    	
	    	empPayroll.setBonus(existingBonusAmt + newBouns);
	    	empPayroll.setNewBonus(newBouns);
	    }
	}

	private EmployeeSalary checkAndUpdateBaseSalary(Employee emp, EmployeePayroll empPayroll, Calendar payRollCal, int finStartMonth) {
		List<EmployeeSalary> empSalList = EmpPayTaxFactroy.findEmpSalary(empPayroll.getEmployeeId());
		EmployeeSalary latestEmpSal = null;
		EmployeeSalary prevEmpSal = null;
		Calendar currDtCal = Calendar.getInstance();
		currDtCal.setTime(payRollCal.getTime());
		
	    // Zero out the hour, minute, second, and millisecond
	    currDtCal.set(Calendar.HOUR_OF_DAY, 0);
	    currDtCal.set(Calendar.MINUTE, 0);
	    currDtCal.set(Calendar.SECOND, 0);
	    currDtCal.set(Calendar.MILLISECOND, 0);
	    currDtCal.set(Calendar.DAY_OF_MONTH, 1);

		for(EmployeeSalary empSal : empSalList) {
			Date effectiveDate = empSal.getFromdate();
			
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
		    	latestEmpSal = empSal;
		    	break;
		    }
		}
		
		if(latestEmpSal == null) {
			for(EmployeeSalary empSal : empSalList) {
				Date effectiveDate = empSal.getTodate();
				Date fromDate = empSal.getFromdate();
				
				if(effectiveDate.compareTo(fromDate) == 0) {
					latestEmpSal = empSal;
			    	break;
			    }
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
		    
		    //if(currDtCal.after(effDtCal)) {
		    	// Check if this is prev salary just before the current one.
		    	if(latestEmpSal != null) {
		    		if(currDtCal.equals(effDtCal)) {
		    			// this is what we want.
		    			prevEmpSal = empSal;
		    			break;
		    		}
		    	}
		    //}
		}
		
		if(latestEmpSal != null && prevEmpSal != null) {
			Double latestSal = getAmountInMMK(latestEmpSal.getEmployeeId().getCurrency(), latestEmpSal.getBasesalary());
			Double prevSal = getAmountInMMK(prevEmpSal.getEmployeeId().getCurrency(), prevEmpSal.getBasesalary());
			
			if(latestEmpSal.getEmployeeId().getCurrency().getName().equalsIgnoreCase(PayhumConstants.CURRENCY_MMK)) {
				Double diff = latestSal/12 - prevSal/12;
				Double existingSal = empPayroll.getBaseSalary();
				Double newSal = existingSal + diff * PayhumUtil.remainingMonths(currDtCal, finStartMonth);
				empPayroll.setBaseSalary(newSal);
			} else {
				// For other currencies, lets do this:
				// Get the paycycles and get base salary and conversion from it and add them and then
				// add the latest sal in current rate.
				
				List<EmpPayrollMap> empPayMaps = EmpPayTaxFactroy.findAllEmpPayrollMapForGivenEmpPayroll(empPayroll.getId());
				Double newSal = 0D;
				for(EmpPayrollMap empPayMap : empPayMaps) {
					newSal += empPayMap.getBaseSalary();
				}
				
				newSal += latestSal / 12 * PayhumUtil.remainingMonths(currDtCal, finStartMonth);
				empPayroll.setBaseSalary(newSal);
			}
			
		} else if(latestEmpSal != null) {
			Double latestSal = getAmountInMMK(latestEmpSal.getEmployeeId().getCurrency(), latestEmpSal.getBasesalary());
			Double monthlyBase = latestSal/12;
			Double existingSal = empPayroll.getBaseSalary();
			
			if(existingSal == 0D) {
				Double newSal = monthlyBase * PayhumUtil.remainingMonths(currDtCal, finStartMonth);
				
				Date hireDate = empPayroll.getEmployeeId().getHiredate();
				Calendar hireDtCal = Calendar.getInstance();
				hireDtCal.setTime(hireDate);
			    // Zero out the hour, minute, second, and millisecond
				hireDtCal.set(Calendar.HOUR_OF_DAY, 0);
				hireDtCal.set(Calendar.MINUTE, 0);
				hireDtCal.set(Calendar.SECOND, 0);
				hireDtCal.set(Calendar.MILLISECOND, 0);
			    
				int currMonth = currDtCal.get(Calendar.MONTH);
				int hireMonth = hireDtCal.get(Calendar.MONTH);
				int hireYear = hireDtCal.get(Calendar.YEAR);
				int currYear = currDtCal.get(Calendar.YEAR);
				
				if(currYear == hireYear && currMonth == hireMonth) {
					int hireDay = hireDtCal.get(Calendar.DAY_OF_MONTH);
					
					int diffDays = 1;
					if(hireDay < 31) {
						diffDays = 31 - hireDay;
					}
					
					newSal = monthlyBase * (PayhumUtil.remainingMonths(currDtCal, finStartMonth) - 1);
					newSal += monthlyBase / 30 * diffDays;
				} else if (PayhumUtil.isRetroActive(payRollCal, empPayroll.getEmployeeId().getHiredate(), finStartMonth)){
					// Means existing sal is 0 and this is the new month, means he joined sometime in the
					// prev month after payroll is processed.
					
					int hireDay = hireDtCal.get(Calendar.DAY_OF_MONTH);
					
					int diffDays = 1;
					if(hireDay < 31) {
						diffDays = 31 - hireDay;
					}
					
					Double retroBaseSal = monthlyBase / 30 * diffDays;
					empPayroll.setRetroBaseSal(retroBaseSal);
				}
				
				empPayroll.setBaseSalary(newSal);	
			}
		} else {
			// There is no change in salary.
		}
		
		return latestEmpSal;
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

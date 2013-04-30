package com.openhr.taxengine.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.openhr.common.PayhumConstants;
import com.openhr.data.Benefit;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeBonus;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.EmployeeSalary;
import com.openhr.data.Holidays;
import com.openhr.data.LeaveRequest;
import com.openhr.data.LeaveType;
import com.openhr.data.OverTime;
import com.openhr.data.OverTimePayRateData;
import com.openhr.factories.BenefitFactory;
import com.openhr.factories.EmpPayTaxFactroy;
import com.openhr.factories.HolidaysFactory;
import com.openhr.factories.LeaveRequestFactory;
import com.openhr.factories.OverTimeFactory;
import com.openhr.taxengine.IncomeCalculator;
import com.openhr.taxengine.TaxDetails;

public class BaseIC implements IncomeCalculator {

	@Override
	public EmployeePayroll calculate(Employee emp, Calendar currDtCal, boolean active) {
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
		
		// First check if there is a BaseSalary change and then update the baseSalary in Payroll table.
		if(active) {
			checkAndUpdateBaseSalary(emp, empPayroll, currDtCal);
		} else {
			computeBaseSalaryForInactive(emp, empPayroll, currDtCal);
		}
		
		// Check if there is a Bonus allocated in the current month and then update the bonus in Payroll table.
		checkAndUpdateBonus(empPayroll, currDtCal);
		
		//Process the other benefits and add it to the allowances of the EmployeePayroll
		Double allowancesAmt = 0D;
		List<Benefit> empBenefits = BenefitFactory.findByEmpId(empPayroll.getEmployeeId());
		for(Benefit bf : empBenefits){
			allowancesAmt += bf.getAmount();
		}
		
		empPayroll.setAllowances(allowancesAmt);
		
		Double annualGrossPay = empPayroll.getBaseSalary();
		annualGrossPay += empPayroll.getBonus();
		annualGrossPay += allowancesAmt;
		
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
		Double leaveLoss = calculateLossOfPay(emp, empPayroll, currDtCal);

		empPayroll.setTotalIncome(totalIncome - leaveLoss);
		
		return empPayroll;
	}	

	private Double calculateLossOfPay(Employee emp, EmployeePayroll empPayroll,
			Calendar currDtCal) {
		List<LeaveRequest> leaveRequestList = LeaveRequestFactory.findByEmployeeId(emp.getId());
		Map<LeaveType, Integer> leaveTypeTakenMap = new HashMap<LeaveType, Integer>();
		
		int noOfLoss = 0;
		
		for(LeaveRequest lr : leaveRequestList){
			if(leaveTypeTakenMap.containsKey(lr.getLeaveTypeId())) {
				Integer cnt = leaveTypeTakenMap.get(lr.getLeaveTypeId());
				leaveTypeTakenMap.put(lr.getLeaveTypeId(), cnt + lr.getNoOfDays());
			} else {
				leaveTypeTakenMap.put(lr.getLeaveTypeId(), lr.getNoOfDays());
			}
		}
		
		for(LeaveType lt: leaveTypeTakenMap.keySet()) {
			Integer taken = leaveTypeTakenMap.get(lt);
			Integer cap = lt.getDayCap();
			
			if(taken.compareTo(cap) > 0) {
				noOfLoss += (taken - cap);
			}
		}
		
		if(noOfLoss > 0) {
			Double daySal = getDaySal(empPayroll, currDtCal);
			Double amountLoss = daySal * noOfLoss;
			
			empPayroll.setLeaveLoss(amountLoss);
			return amountLoss;
		}
		
		return 0D;
	}

	private Double getDaySal(EmployeePayroll empPayroll, Calendar currDtCal) {
        Double baseSal = empPayroll.getBaseSalary();
		
		// Per Month
		baseSal = baseSal / 12;
		
		// Per Day
		baseSal = baseSal / currDtCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
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
		
		empPayroll.setOvertimeamt(amount);
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
		Double baseSal = getDaySal(empPayroll, currDtCal);
		
		// Per hour
		baseSal = baseSal / 8;
		
		return baseSal;
	}

	private void computeBaseSalaryForInactive(Employee emp,
			EmployeePayroll empPayroll, Calendar currDtCal) {
		List<EmployeeSalary> empSalList = EmpPayTaxFactroy.findEmpSalary(empPayroll.getEmployeeId());

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
		    
		    
			if(toDate != null) {
				// Not a last record
				int toDtMonth = toDtCal.get(Calendar.MONTH) + 1; 
				int effDtMonth = effDtCal.get(Calendar.MONTH) + 1;
				
				if( toDtMonth >= 4) {
					int count = toDtMonth - effDtMonth;
					Double sal = empSal.getBasesalary()/12 * count;
					totalSal += sal;
				} else {
					if(effDtMonth >= 4){
						int count = 12 - effDtMonth;
						count += toDtMonth;
						
						Double sal = empSal.getBasesalary()/12 * count;
						totalSal += sal;
					} else {
						int count = toDtMonth - effDtMonth;
						Double sal = empSal.getBasesalary()/12 * count;
						totalSal += sal;
					}
				}
			} else {
				// Last record
				Date exitDate = emp.getInactiveDate();
				Calendar exitDtCal  = Calendar.getInstance();
				exitDtCal.setTime(exitDate);
				
				int noOfDays = exitDtCal.get(Calendar.DAY_OF_MONTH);
				
				Double monthSal = empSal.getBasesalary()/12;
				Double daySal = monthSal / exitDtCal.getActualMaximum(Calendar.DAY_OF_MONTH);
				
				Double amount = daySal * noOfDays;
				
				totalSal += amount;
			}
		}
	    
	    empPayroll.setBaseSalary(totalSal);
	}

	// Private Methods
	private void checkAndUpdateBonus(EmployeePayroll empPayroll,
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
	    
	    if(latestEmpBonus != null) {
	    	// There is a bonus, so lets update the emp payroll with it.
	    	Double existingBonusAmt = empPayroll.getBonus();
	    	
	    	empPayroll.setBonus(existingBonusAmt + latestEmpBonus.getAmount());
	    }
	}

	private void checkAndUpdateBaseSalary(Employee emp, EmployeePayroll empPayroll, Calendar currDtCal) {
		List<EmployeeSalary> empSalList = EmpPayTaxFactroy.findEmpSalary(empPayroll.getEmployeeId());
		EmployeeSalary latestEmpSal = null;
		EmployeeSalary prevEmpSal = null;
		
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

		for(EmployeeSalary empSal : empSalList) {
			Date effectiveDate = empSal.getTodate();
			
			if(effectiveDate == null)
				continue;
			
		    Calendar effDtCal = Calendar.getInstance();
		    effDtCal.setTime(effectiveDate);
		    // Zero out the hour, minute, second, and millisecond
		    effDtCal.set(Calendar.HOUR_OF_DAY, 0);
		    effDtCal.set(Calendar.MINUTE, 0);
		    effDtCal.set(Calendar.SECOND, 0);
		    effDtCal.set(Calendar.MILLISECOND, 0);
		    effDtCal.set(Calendar.DAY_OF_MONTH, 1);
		    
		    if(currDtCal.after(effDtCal)) {
		    	// Check if this is prev salary just before the current one.
		    	if(latestEmpSal != null) {
		    		if(currDtCal.equals(effDtCal)) {
		    			// this is what we want.
		    			prevEmpSal = empSal;
		    			break;
		    		}
		    	}
		    }
		}
		
		if(latestEmpSal != null && prevEmpSal != null) {
			Double diff = latestEmpSal.getBasesalary()/12 - prevEmpSal.getBasesalary()/12;
			Double existingSal = empPayroll.getBaseSalary();
			
			Double newSal = existingSal + diff * remainingMonths(currDtCal);
			
			empPayroll.setBaseSalary(newSal);
		} else if(latestEmpSal != null) {
			Double monthlyBase = latestEmpSal.getBasesalary()/12;
			Double existingSal = empPayroll.getBaseSalary();
			
			if(existingSal == 0D) {
				Double newSal = monthlyBase * remainingMonths(currDtCal);
				
				empPayroll.setBaseSalary(newSal);	
			}
		} else {
			// There is no change in salary.
		}
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

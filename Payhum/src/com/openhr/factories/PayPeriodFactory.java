package com.openhr.factories;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.common.PayhumConstants;
import com.openhr.data.Branch;
import com.openhr.data.ConfigData;
import com.openhr.data.PayPeriodData;
import com.openhr.data.Payroll;
import com.openhr.data.PayrollDate;
import com.openhr.data.Position;

import com.openhr.factories.common.OpenHRSessionFactory;

public class PayPeriodFactory {
	private static List<PayPeriodData> payprd;
	private static Query query;
	private static Session session;

	public static List<PayPeriodData> findAll() {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("PayPeriodData.findAll");
		payprd = query.list();
		session.getTransaction().commit();

		return payprd;
	}

	
	public static List<Payroll> findCountRunOndate() {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Payroll.findcountRunOnDate");
		List<Payroll>	payprd = query.list();
		session.getTransaction().commit();

		return payprd;
	}
	
	
	
	public static boolean update(PayPeriodData prds) {
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		try {
			PayPeriodData prd = null;

			if (prds.getId() != null) {
				prd = (PayPeriodData) session.get(PayPeriodData.class,
						prds.getId());
				prd.setPeriodName(prds.getPeriodName());
				prd.setPeriodValue(prds.getPeriodValue());
				prd.setDayofmonth(prds.getDayofmonth());

				session.update(prd);
				done = true;
			} else {
				session.save(prds);
				done = true;
			}

			session.getTransaction().commit();
			
			// After updating the repos, check and update the remaining paycycle dates accordingly.
			checkAndUpdatePayCycleDates(prds);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return done;
	}
	
	private static void checkAndUpdatePayCycleDates(PayPeriodData pp) throws Exception {
		ConfigData userComp = ConfigDataFactory.findByName(PayhumConstants.LOGGED_USER_COMP); 
		Integer compId = Integer.parseInt(userComp.getConfigValue());
		
		List<Branch> branches = BranchFactory.findByCompanyId(compId);
		List<PayrollDate> payrollDates = new ArrayList<PayrollDate>();
		
		for(Branch br : branches) {
			payrollDates.addAll(PayrollFactory.findPayrollDateByBranch(br.getId()));	
		}
		
		
		Calendar currCal = Calendar.getInstance();
		int currMonth = currCal.get(Calendar.MONTH);
		int currYear = currCal.get(Calendar.YEAR);
		
		int nextMonth = currMonth + 1;
		
		// First remove all the payroll dates of next month and add new ones. 
		for(PayrollDate pd : payrollDates) {
			Date runDate = pd.getRunDateofDateObject();
			Calendar runCal = Calendar.getInstance();
			runCal.setTime(runDate);
			
			int runMonth = runCal.get(Calendar.MONTH);
			int runYear = runCal.get(Calendar.YEAR);
			
			if(runMonth > currMonth && runYear >= currYear) {
				PayrollFactory.deletePayrollDate(pd);
			}
		}
		
		if (pp.getPeriodValue().compareTo(new Integer(3)) == 0) {
			// Monthly
			// Now add new Payroll Dates
			if(nextMonth >= 3) {
				// Populate from current month to december
				for(int i = nextMonth; i < 12; i++) {
					Date rDate = getPayRunDateForMonth(i, currYear, pp.getDayofmonth());
					PayrollDate payDate = new PayrollDate();
					payDate.setRunDate(rDate);
					PayrollFactory.insertPayrollDate(payDate);
					
					payrollDates.add(payDate);
				}
				
				// Populate from current month to March
				for(int i = 0; i < 3; i++) {
					Date rDate = getPayRunDateForMonth(i, currYear+1, pp.getDayofmonth());
					PayrollDate payDate = new PayrollDate();
					payDate.setRunDate(rDate);
					PayrollFactory.insertPayrollDate(payDate);
					
					payrollDates.add(payDate);
				}
			} else {
				for(int i = 0; i <= nextMonth; i++) {
					Date rDate = getPayRunDateForMonth(i, currYear + 1, pp.getDayofmonth());
					PayrollDate payDate = new PayrollDate();
					payDate.setRunDate(rDate);
					PayrollFactory.insertPayrollDate(payDate);
					
					payrollDates.add(payDate);
				}
			}
		} else if(pp.getPeriodValue().compareTo(new Integer(2)) == 0) {
			// biweekly
			// Now add new Payroll Dates
			if(nextMonth >= 3) {
				// Populate from current month to december
				for(int i = nextMonth; i < 12; i++) {
					Date rDate1 = getSecondFridayOfMonth(i, currYear);
					PayrollDate payDate1 = new PayrollDate();
					payDate1.setRunDate(rDate1);
					PayrollFactory.insertPayrollDate(payDate1);
					
					payrollDates.add(payDate1);
					
					Date rDate2 = getLastFridayOfMonth(i, currYear);
					PayrollDate payDate2 = new PayrollDate();
					payDate2.setRunDate(rDate2);
					PayrollFactory.insertPayrollDate(payDate2);
					
					payrollDates.add(payDate2);
				}
				
				// Populate from current month to March
				for(int i = 0; i < 3; i++) {
					Date rDate1 = getSecondFridayOfMonth(i, currYear+1);
					PayrollDate payDate1 = new PayrollDate();
					payDate1.setRunDate(rDate1);
					PayrollFactory.insertPayrollDate(payDate1);
					
					payrollDates.add(payDate1);
					
					Date rDate2 = getLastFridayOfMonth(i, currYear+1);
					PayrollDate payDate2 = new PayrollDate();
					payDate2.setRunDate(rDate2);
					PayrollFactory.insertPayrollDate(payDate2);
					
					payrollDates.add(payDate2);
				}
			} else {
				for(int i = 0; i <= nextMonth; i++) {
					Date rDate1 = getSecondFridayOfMonth(i, currYear + 1);
					PayrollDate payDate1 = new PayrollDate();
					payDate1.setRunDate(rDate1);
					PayrollFactory.insertPayrollDate(payDate1);
					
					payrollDates.add(payDate1);
					
					Date rDate2 = getLastFridayOfMonth(i, currYear + 1);
					PayrollDate payDate2 = new PayrollDate();
					payDate2.setRunDate(rDate2);
					PayrollFactory.insertPayrollDate(payDate2);
					
					payrollDates.add(payDate2);
				}
			}
		} else if(pp.getPeriodValue().compareTo(new Integer(1)) == 0) {
			// weekly
			// Now add new Payroll Dates
			if(nextMonth >= 3) {
				// Populate from current month to december
				for(int i = nextMonth; i < 12; i++) {
					Calendar calIns = Calendar.getInstance();
					calIns.set(currYear, i, 1);
				    int daysInMonth = calIns.getActualMaximum(Calendar.DAY_OF_MONTH);

				    for (int day = 1; day <= daysInMonth; day++) {
				    	calIns.set(currYear, i, day);
				        int dayOfWeek = calIns.get(Calendar.DAY_OF_WEEK);
				        if (dayOfWeek == Calendar.FRIDAY) {
				        	Date rDate1 = calIns.getTime();
							PayrollDate payDate1 = new PayrollDate();
							payDate1.setRunDate(rDate1);
							PayrollFactory.insertPayrollDate(payDate1);
							
							payrollDates.add(payDate1);
				        }
				    }
				}
				
				// Populate from current month to March
				for(int i = 0; i < 3; i++) {
					Calendar calIns = Calendar.getInstance();
					calIns.set(currYear, i, 1);
				    int daysInMonth = calIns.getActualMaximum(Calendar.DAY_OF_MONTH);

				    for (int day = 1; day <= daysInMonth; day++) {
				    	calIns.set(currYear, i, day);
				        int dayOfWeek = calIns.get(Calendar.DAY_OF_WEEK);
				        if (dayOfWeek == Calendar.FRIDAY) {
				        	Date rDate1 = calIns.getTime();
							PayrollDate payDate1 = new PayrollDate();
							payDate1.setRunDate(rDate1);
							PayrollFactory.insertPayrollDate(payDate1);
							
							payrollDates.add(payDate1);
				        }
				    }
				}
			} else {
				for(int i = 0; i <= nextMonth; i++) {
					Calendar calIns = Calendar.getInstance();
					calIns.set(currYear, i, 1);
				    int daysInMonth = calIns.getActualMaximum(Calendar.DAY_OF_MONTH);

				    for (int day = 1; day <= daysInMonth; day++) {
				    	calIns.set(currYear, i, day);
				        int dayOfWeek = calIns.get(Calendar.DAY_OF_WEEK);
				        if (dayOfWeek == Calendar.FRIDAY) {
				        	Date rDate1 = calIns.getTime();
							PayrollDate payDate1 = new PayrollDate();
							payDate1.setRunDate(rDate1);
							PayrollFactory.insertPayrollDate(payDate1);
							
							payrollDates.add(payDate1);
				        }
				    }
				}
			}
		}
	}
	
	private static Date getLastFridayOfMonth( int month, int year) {
		Calendar retCal = Calendar.getInstance();
		retCal.set(Calendar.YEAR, year);
		retCal.set(Calendar.MONTH, month);
		retCal.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
		retCal.set(Calendar.DAY_OF_WEEK_IN_MONTH, -1);
		
		retCal.set(Calendar.HOUR_OF_DAY, 0);
		retCal.set(Calendar.MINUTE, 0);
		retCal.set(Calendar.SECOND, 0);
		retCal.set(Calendar.MILLISECOND, 0);
	    
		return retCal.getTime();
	}
	
	private static Date getPayRunDateForMonth( int month, int year, int dayofmonth) {
		Calendar retCal = Calendar.getInstance();
		retCal.set(Calendar.YEAR, year);
		retCal.set(Calendar.MONTH, month);
		
		if(dayofmonth != 0) {
			retCal.set(Calendar.DAY_OF_MONTH, dayofmonth);
		} else {
			retCal.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
			retCal.set(Calendar.DAY_OF_WEEK_IN_MONTH, -1);
		}
		
		retCal.set(Calendar.HOUR_OF_DAY, 0);
		retCal.set(Calendar.MINUTE, 0);
		retCal.set(Calendar.SECOND, 0);
		retCal.set(Calendar.MILLISECOND, 0);
	    
		return retCal.getTime();
	}
	private static Date getSecondFridayOfMonth( int month, int year ) {
		Calendar retCal = Calendar.getInstance();
		retCal.set(Calendar.YEAR, year);
		retCal.set(Calendar.MONTH, month);
		retCal.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
		retCal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 2);
		
		retCal.set(Calendar.HOUR_OF_DAY, 0);
		retCal.set(Calendar.MINUTE, 0);
		retCal.set(Calendar.SECOND, 0);
		retCal.set(Calendar.MILLISECOND, 0);
	    
		return retCal.getTime();
	}

}

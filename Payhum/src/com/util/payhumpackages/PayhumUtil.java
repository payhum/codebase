package com.util.payhumpackages;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.openhr.data.Payroll;
import com.openhr.data.PayrollDate;

public class PayhumUtil {

	static SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yy");// 22/12/13

	static SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");// //
																				// 29-June-2002

	static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");// 02-11-2012

	static DecimalFormat decmalfrmt = new DecimalFormat("###.##");

	// String s="E1_JohnSo_29_07_13";

	// public enum
	// Months{1(JANUARY),2(FEBRUARY),3(MARCH),4(APRIL),(5(MAY,6(JUNE),7(JULY),8(AUGUST),9(SEPTEMBER),10(OCTOBER),11(NOVEMBER),12(DECEMBER)};

	public static String getDateFormatFullNum(Date d) {

		String format = DATE_FORMAT.format(d);

		return format;
	}

	public static String decimalFormat(double d) {

		return decmalfrmt.format(d);

	}

	public static Date getDateAfterBefore(Date d) {

		// String format=DATE_FORMAT.format(d);

		Calendar cal = Calendar.getInstance();

		cal.setTime(d);
		cal.add(Calendar.DATE, -7);
		return cal.getTime();
	}

	public static Integer getNumberDays(Date dd) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dd);

		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static String getDateFormatNum(Date d) {

		String format = dateformat.format(d);

		return format;
	}

	public static Integer parseInt(String sint) {
		return Integer.valueOf(sint);
	}

	public static String getDateFormatString(Date d) {

		String format = formatter.format(d);

		return format;
	}

	public static String getNumberSpaces(int a) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < a; i++) {
			sb.append(" ");

		}

		return sb.toString();
	}

	public static String[] splitString(String s, int limit, String regex) {

		String spltit[] = s.split(regex, limit);

		return spltit;

	}

	public static int remainingPaycycles(List<PayrollDate> payDates,
			List<Payroll> payRuns) {
		// default starts with 1 as the current date is being processed, but its
		// already recorded as processed
		int unprocessedDates = 1;

		for (PayrollDate payDate : payDates) {
			boolean processed = false;
			for (Payroll run : payRuns) {
				if (run.getPayDateId().getId().compareTo(payDate.getId()) == 0
						&& run.getBranchId().getId().compareTo(payDate.getBranchId().getId()) == 0) {
					processed = true;
					break;
				}
			}

			if (!processed) {
				unprocessedDates++;
			}

		}

		return unprocessedDates;
	}
	
	public static int remainingMonths(Calendar currDate, int finStartMonth) {
		int retVal = 0;
		
		// Get the remaining months in the year.
		int currentMonth = currDate.get(Calendar.MONTH) + 1;

		// Default is Jan to Dec
		if(finStartMonth < 1)
			finStartMonth = 1;
		
		if(currentMonth > finStartMonth) {
			retVal = 12 - currentMonth + 1;
			retVal += finStartMonth - 1;
		} else if(currentMonth == finStartMonth ) {
			// Start of the financial year, so 12 months is left
			retVal = 12;	
		} else {
			// current month is less than fin start month
			retVal = finStartMonth - currentMonth;
		}
		
		return retVal;
	}


}

package com.openhr.common;

import java.util.Comparator;

import com.openhr.data.EmployeePayroll;

public class EmployeePayrollComparer implements Comparator<EmployeePayroll> {

	@Override
	public int compare(EmployeePayroll arg0, EmployeePayroll arg1) {
		
		int results= compare(arg0.getEmployeeId().getEmployeeId(), arg1.getEmployeeId().getEmployeeId());
	    return results;
	 }
	
	 private static int compare(String a, String b) {
		 try {
			 Long aLong = Long.parseLong(a);
			 Long bLong = Long.parseLong(b);
			 
			 return aLong < bLong ? -1
			         : aLong > bLong ? 1
			         : 0;	 	 
		 }
		 catch(Exception e)
		 {
			 return 1;
		 }
	 }


}

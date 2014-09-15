package com.openhr.common;

import java.util.Comparator;

import com.openhr.data.Employee;

public class EmployeeComparer implements Comparator<Employee> {

	@Override
	public int compare(Employee arg0, Employee arg1) {
		
		int results= compare(arg0.getEmployeeId(), arg1.getEmployeeId());
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

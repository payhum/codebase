package com.openhr.employee;

import com.openhr.factories.BranchFactory;
import com.openhr.factories.DepartmentFactory;
import com.openhr.factories.EmployeeFactory;

public class EmployeeIdUtility {
	public static String nextId() throws Exception {
		Integer lastId = EmployeeFactory.findLastId() + 1;
		int length = String.valueOf(lastId).length();

		if (length == 1) {
			return String.valueOf("000" + lastId);
		}
		if (length == 2) {
			return String.valueOf("00" + lastId);
		}
		if (length == 3) {
			return String.valueOf("0" + lastId);
		}

		return String.valueOf(lastId);

	}
	
	public static String companyNextId() throws Exception {
 		Integer lastId = EmployeeFactory.findCompanyLastId() + 1;
 		int length = String.valueOf(lastId).length();

		if (length == 1) {
			return String.valueOf("000" + lastId);
		}
		if (length == 2) {
			return String.valueOf("00" + lastId);
		}
		if (length == 3) {
			return String.valueOf("0" + lastId);
		}
		
		return lastId+"";

	}
	
	public static String BranchNextId() throws Exception {
 		Integer lastId = BranchFactory.findBranchLastId() + 1;
 		int length = String.valueOf(lastId).length();

		if (length == 1) {
			return String.valueOf("000" + lastId);
		}
		if (length == 2) {
			return String.valueOf("00" + lastId);
		}
		if (length == 3) {
			return String.valueOf("0" + lastId);
		}
		
		return lastId+"";

	}
	
	public static String DepartmentNextId() throws Exception {
 		Integer lastId = DepartmentFactory.findDepartmentLastId() + 1;
 		int length = String.valueOf(lastId).length();

		if (length == 1) {
			return String.valueOf("000" + lastId);
		}
		if (length == 2) {
			return String.valueOf("00" + lastId);
		}
		if (length == 3) {
			return String.valueOf("0" + lastId);
		}
		
		return lastId+"";

	}
	
}

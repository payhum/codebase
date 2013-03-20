package com.openhr.employee;

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
	
}

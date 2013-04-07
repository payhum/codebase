package com.openhr.company;

import java.util.ArrayList;
import java.util.List;

import com.openhr.data.Employee;

public class Company {

	private List<Employee> empList;
	
	public Company () {
		empList = new ArrayList<Employee>();
	}
	
	public List<Employee> getActiveEmployees() {
		return empList;
	}
	
	public void addEmployee(Employee emp) {
		empList.add(emp);
	}

}

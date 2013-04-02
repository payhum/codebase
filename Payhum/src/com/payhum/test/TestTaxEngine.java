package com.payhum.test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.openhr.company.Company;
import com.openhr.data.AccomodationType;
import com.openhr.data.DependentType;
import com.openhr.data.EmpDependents;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.OccupationType;
import com.openhr.data.ResidentType;
import com.openhr.taxengine.DeductionType;
import com.openhr.taxengine.DeductionsDone;
import com.openhr.taxengine.ExemptionType;
import com.openhr.taxengine.ExemptionsDone;
import com.openhr.taxengine.TaxDetails;
import com.openhr.taxengine.TaxEngine;

public class TestTaxEngine {
	public static void main(String[] args) {
		// Setting the system property for DRYRUN.
		
		System.setProperty("DRYRUN", "true");
		
		// First Create the dummy Employee data
		Employee emp = new Employee(0, "E1", "John", "", "Lui", "male", new Date(), new Date());
		emp.setResidentType(ResidentType.LOCAL.getValue());
		emp.setMarried("true");
		
		EmpDependents dep1 = new EmpDependents(1, "eE0", 45, "T", OccupationType.NONE.getValue(), DependentType.SPOUSE.getValue());
		EmpDependents dep2 = new EmpDependents(1, "eE0", 5, "E", OccupationType.STUDENT.getValue(), DependentType.CHILD.getValue());
		EmpDependents dep3 = new EmpDependents(1, "eE0", 15, "F", OccupationType.STUDENT.getValue(), DependentType.CHILD.getValue());
		EmpDependents dep4 = new EmpDependents(1, "eE0", 12, "G", OccupationType.STUDENT.getValue(), DependentType.CHILD.getValue());
		
		List<EmpDependents> deps = new ArrayList<EmpDependents>();
		deps.add(dep1);
		deps.add(dep2);
		deps.add(dep3);
		deps.add(dep4);
		
		emp.setDependents(deps);
		
		Company comp = new Company();
		comp.addEmployee(emp);
		
		// Run through the engine
		System.out.println("Running the Tax Engine in DRYRUN Mode.");
		System.out.println("======================================");
	
		TaxEngine taxEngine = new TaxEngine(comp);
		Map<Employee, EmployeePayroll> retData = taxEngine.testExecute();
		
		for(Employee ei : retData.keySet()) {
			EmployeePayroll empPay = retData.get(ei);
			
			System.out.println("Personal Details :");
			System.out.println("==================");
			System.out.println("Employee Name - " + emp.getFirstname());
			System.out.println("Resident Type - " + emp.getResidentType());
			System.out.println("Married - " + emp.isMarried());
			
			List<EmpDependents> dependents = emp.getDependents();
			int noOfChildren = 0;
			for(EmpDependents dep : dependents ) {
				if(dep.getType() == DependentType.CHILD) {
					noOfChildren++;
				}
			}
			System.out.println("Children - " + noOfChildren);
			
			System.out.println("Income Details :");
			System.out.println("==================");
			
			System.out.println("Base Salary - " + new DecimalFormat("MMK ###,###.###").format(empPay.getBaseSalary()));
			System.out.println("Bonus - " + new DecimalFormat("MMK ###,###.###").format(empPay.getBonus()));
			System.out.println("Allowance - " + new DecimalFormat("MMK ###,###.###").format(empPay.getAllowancesAmount()));
			System.out.println("Accomodation Type - " + empPay.getAccomodationType());
			System.out.println("Accomodation Amount - " + new DecimalFormat("MMK ###,###.###").format(empPay.getAccomodationAmount()));
			System.out.println("Employer Social Security - " + new DecimalFormat("MMK ###,###.###").format(empPay.getEmployerSS()));
			System.out.println("GROSS SALARY - " + new DecimalFormat("MMK ###,###.###").format(empPay.getGrossSalary()));
			System.out.println("TOTAL INCOME - " + new DecimalFormat("MMK ###,###.###").format(empPay.getTotalIncome()));

			List<ExemptionsDone> eDone = empPay.getExemptionsDone();
			List<DeductionsDone> dDone = empPay.getDeductionsDone();
			
			Double spouseAmt = 0D;
			Double childrenAmt = 0D;
			Double empSS = 0D;
			Double bAllowance = 0D;
			
			for(ExemptionsDone ed: eDone) {
				if(ed.getType() == ExemptionType.SUPPORTING_SPOUSE.getValue()) {
					spouseAmt = ed.getAmount();
				} else if(ed.getType() == ExemptionType.CHILDREN.getValue()) {
					childrenAmt = ed.getAmount();
				} else if(ed.getType() == ExemptionType.BASIC_ALLOWANCE.getValue()) {
					bAllowance = ed.getAmount();
				}
			}
			
			for(DeductionsDone ed: dDone) {
				if(ed.getType() == DeductionType.EMPLOYEE_SOCIAL_SECURITY.getValue()) {
					empSS = ed.getAmount();
				}
			}
			
			System.out.println("Exemptions/Deductions :");
			System.out.println("=======================");
			System.out.println("Supporting Spouse - " + new DecimalFormat("MMK ###,###.###").format(spouseAmt));
			System.out.println("Children - " + new DecimalFormat("MMK ###,###.###").format(childrenAmt));
			System.out.println("Employee Social Security - " + new DecimalFormat("MMK ###,###.###").format(empSS));
			System.out.println("Basic Allowance - " + new DecimalFormat("MMK ###,###.###").format(bAllowance));
			System.out.println("TOTAL DEDUCTIONS - " + new DecimalFormat("MMK ###,###.###").format(empPay.getTotalDeductions()));
			System.out.println("TAXABLE INCOME - " + new DecimalFormat("MMK ###,###.###").format(empPay.getTaxableIncome()));
			System.out.println("TAX AMOUNT - " + new DecimalFormat("MMK ###,###.###").format(empPay.getTaxAmount()));
		}
	}
}

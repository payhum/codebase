package com.payhum.test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.openhr.common.PayhumConstants;
import com.openhr.company.Company;
import com.openhr.data.DeductionsType;
import com.openhr.data.EmpDependents;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.Exemptionstype;
import com.openhr.data.TypesData;
import com.openhr.factories.DeductionFactory;
import com.openhr.taxengine.DeductionsDone;
import com.openhr.taxengine.ExemptionsDone;
import com.openhr.taxengine.TaxEngine;

public class TestTaxEngine {
	public static void main(String[] args) {
		// Setting the system property for DRYRUN.

		System.setProperty("DRYRUN", "true");

		TypesData residentType = new TypesData();
		residentType.setDesc(PayhumConstants.LOCAL);
		residentType.setName(PayhumConstants.LOCAL);
		residentType.setType(PayhumConstants.TYPE_RESIDENTTYPE);
		
		// First Create the dummy Employee data
		Employee emp = new Employee(2, "E1", "John", "", "Lui", "male", new Date(), new Date());
		emp.setResidentType(residentType);
		emp.setMarried("true");

		TypesData depSpouse= new TypesData();
		depSpouse.setDesc(PayhumConstants.DEP_SPOUSE);
		depSpouse.setName(PayhumConstants.DEP_SPOUSE);
		depSpouse.setType(PayhumConstants.TYPE_DEPENDENTTYPE);
		
		TypesData depChild = new TypesData();
		depChild.setDesc(PayhumConstants.DEP_CHILD);
		depChild.setName(PayhumConstants.DEP_CHILD);
		depChild.setType(PayhumConstants.TYPE_DEPENDENTTYPE);
		
		TypesData occpNone = new TypesData();
		occpNone.setDesc(PayhumConstants.OCCUP_NONE);
		occpNone.setName(PayhumConstants.OCCUP_NONE);
		occpNone.setType(PayhumConstants.TYPE_OCCUPATIONTYPE);

		TypesData occpStu = new TypesData();
		occpStu.setDesc(PayhumConstants.OCCUP_STUDENT);
		occpStu.setName(PayhumConstants.OCCUP_STUDENT);
		occpStu.setType(PayhumConstants.TYPE_OCCUPATIONTYPE);
		
		EmpDependents dep1 = new EmpDependents(1, emp, 45, "T", occpNone, depSpouse);
		EmpDependents dep2 = new EmpDependents(2, emp, 5, "E", occpStu, depChild);
		EmpDependents dep3 = new EmpDependents(3, emp, 15, "F", occpStu, depChild);
		EmpDependents dep4 = new EmpDependents(4, emp, 12, "G", occpStu, depChild);

		List<EmpDependents> deps = new ArrayList<EmpDependents>();
		deps.add(dep1);
		deps.add(dep2);
		deps.add(dep3);
		deps.add(dep4);

		emp.setDependents(deps);

		Company comp = new Company();
		
		List<Employee> empList1 = new ArrayList<Employee>();
		List<Employee> empList2 = new ArrayList<Employee>();
		empList1.add(emp);
		
		// Run through the engine
		System.out.println("Running the Tax Engine in DRYRUN Mode.");
		System.out.println("======================================");
		Calendar currCal = Calendar.getInstance();
		
		TaxEngine taxEngine = new TaxEngine(comp, empList1, empList2);
		Map<Employee, EmployeePayroll> retData = taxEngine.testExecute(currCal);
		
		for(Employee ei : retData.keySet()) {
			EmployeePayroll empPay = retData.get(ei);

			System.out.println("Personal Details :");
			System.out.println("==================");
			System.out.println("Employee Name - " + emp.getFirstname());
			System.out.println("Resident Type - " + emp.getResidentType().getName());
			System.out.println("Married - " + emp.isMarried());

			List<EmpDependents> dependents = emp.getDependents();
			int noOfChildren = 0;
			for(EmpDependents dep : dependents ) {
				if(dep.getType().getName().equalsIgnoreCase(PayhumConstants.DEP_CHILD)) {
					noOfChildren++;
				}
			}
			System.out.println("Children - " + noOfChildren);

			System.out.println("Income Details :");
			System.out.println("==================");

			System.out.println("Base Salary - " + new DecimalFormat("MMK ###,###.###").format(empPay.getBaseSalary()));
			System.out.println("Bonus - " + new DecimalFormat("MMK ###,###.###").format(empPay.getBonus()));
			System.out.println("Allowance - " + new DecimalFormat("MMK ###,###.###").format(empPay.getAllowancesAmount()));
			System.out.println("Accomodation Type - " + empPay.getAccomodationType().getName());
			System.out.println("Accomodation Amount - " + new DecimalFormat("MMK ###,###.###").format(empPay.getAccomodationAmount()));
			System.out.println("Employer Social Security - " + new DecimalFormat("MMK ###,###.###").format(empPay.getEmployerSS()));
			System.out.println("GROSS SALARY - " + new DecimalFormat("MMK ###,###.###").format(empPay.getGrossSalary()));
			System.out.println("TOTAL INCOME - " + new DecimalFormat("MMK ###,###.###").format(empPay.getTotalIncome()));

			List<DeductionsDone> ddone = empPay.getDeductionsDone();
			List<ExemptionsDone> edone = empPay.getExemptionsDone();
			List<DeductionsType> deductionsTypes = DeductionFactory.findAll();
			List<Exemptionstype> exemptionsTypes = DeductionFactory.findAllExemptionTypes();
			
			Double suppSpouse = 0D;
			Double children = 0D;
			Double empSS = 0D;
			Double basicAllow = 0D;
			
			for(DeductionsDone dd : ddone) {
				if(dd.getType().getId().compareTo(getDeductionsType(deductionsTypes, PayhumConstants.EMPLOYEE_SOCIAL_SECURITY).getId()) == 0) {
					empSS = dd.getAmount();
				}
			}
			
			for(ExemptionsDone ed : edone) {
				if(ed.getType().getId().compareTo(getExemptionsType(exemptionsTypes, PayhumConstants.SUPPORTING_SPOUSE).getId()) == 0) {
					suppSpouse = ed.getAmount();
				} else if(ed.getType().getId().compareTo(getExemptionsType(exemptionsTypes, PayhumConstants.CHILDREN).getId()) == 0) {
					children = ed.getAmount();
				} else if(ed.getType().getId().compareTo(getExemptionsType(exemptionsTypes, PayhumConstants.BASIC_ALLOWANCE).getId()) == 0) {
					basicAllow = ed.getAmount();
				}
			}
			
			System.out.println("Exemptions/Deductions :");
			System.out.println("=======================");
			System.out.println("Supporting Spouse - " + new DecimalFormat("MMK ###,###.###").format(suppSpouse));
			System.out.println("Children - " + new DecimalFormat("MMK ###,###.###").format(children));
			System.out.println("Employee Social Security - " + new DecimalFormat("MMK ###,###.###").format(empSS));
			System.out.println("Basic Allowance - " + new DecimalFormat("MMK ###,###.###").format(basicAllow));
			
			System.out.println("TOTAL DEDUCTIONS - " + new DecimalFormat("MMK ###,###.###").format(empPay.getTotalDeductions()));
			System.out.println("TAXABLE INCOME - " + new DecimalFormat("MMK ###,###.###").format(empPay.getTaxableIncome()));
			System.out.println("TAX AMOUNT - " + new DecimalFormat("MMK ###,###.###").format(empPay.getTaxAmount()));
		}
	}
	
	private static DeductionsType getDeductionsType(
			List<DeductionsType> deductionsTypes, String typeStr) {
		for(DeductionsType dType: deductionsTypes) {
			if(dType.getName().equalsIgnoreCase(typeStr))
				return dType;
		}
		
		return null;
	}
	
	private static Exemptionstype getExemptionsType(
			List<Exemptionstype> eTypes, String typeStr) {
		for(Exemptionstype eType: eTypes) {
			if(eType.getName().equalsIgnoreCase(typeStr))
				return eType;
		}
		
		return null;
	}
}
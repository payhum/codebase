package com.openhr.factories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.data.EmpPayTax;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.factories.common.OpenHRSessionFactory;
import com.openhr.taxengine.DeductionsDone;
import com.openhr.taxengine.ExemptionsDone;

public class EmpPayTaxFactroy implements Serializable {
	private static final long serialVersionUID = 1L;

	private static Session session;
	private static Query query;
	private static List<EmpPayTax> employees;
	private static List<EmployeePayroll> empsum;

	public EmpPayTaxFactroy() {
	}

	// TODO: Need to check this and remove it.
	public static List<EmpPayTax> findAll() throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("EmpPayTax.findAll");
		employees = query.list();

		session.getTransaction().commit();

		return employees;
	}

	public static List<EmployeePayroll> findAllEmpPayroll() 
	{
		session = OpenHRSessionFactory.getInstance().openSession();
		session.beginTransaction();
		query = session.getNamedQuery("EmployeePayroll.findAll");
		empsum = query.list();
		session.getTransaction().commit();

		return empsum;
	}

	public static List<EmployeePayroll> findEmpPayrollbyEmpID(Integer empID) 
	{
		if(System.getProperty("DRYRUN") != null 
		&& System.getProperty("DRYRUN").equalsIgnoreCase("true")) {
			EmployeePayroll empP = EmployeePayroll.loadEmpPayroll(empID);
			List<EmployeePayroll> empPList = new ArrayList<EmployeePayroll>();
			empPList.add(empP);
			return empPList;
		}
		
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("EmployeePayroll.findByEmployeeId");
		query.setInteger(0,empID);
		empsum = query.list();
		session.getTransaction().commit();

		return empsum;
	}

	public synchronized static List<ExemptionsDone> exemptionsDone(Integer employeeId)
			throws Exception {
		Employee e=new Employee();
		e.setId(employeeId);
		session = OpenHRSessionFactory.getInstance().openSession();
		session.beginTransaction();
		query = session.getNamedQuery("ExemptionsDone.findByEmployeeId");
		query.setParameter(0, e);
		List<ExemptionsDone> empsum1 = query.list();

		session.getTransaction().commit();
        session.close();
		return empsum1;
	}
	
	public synchronized static List<DeductionsDone> deductionsDone(Integer employeeId)
			throws Exception {
		Employee e=new Employee();
		e.setId(employeeId);
		session = OpenHRSessionFactory.getInstance().openSession();
		session.beginTransaction();
		query = session.getNamedQuery("DeductionsDone.findByEmployeeId");
		query.setParameter(0, e);
		List<DeductionsDone> empsum2 = query.list();

		session.getTransaction().commit();
        session.close();
		return empsum2;
	}
	
}

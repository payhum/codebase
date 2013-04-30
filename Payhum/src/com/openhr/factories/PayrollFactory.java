package com.openhr.factories;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.data.EmpPayrollMap;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.Payroll;
import com.openhr.data.PayrollDate;
import com.openhr.factories.common.OpenHRSessionFactory;

public class PayrollFactory implements Serializable {
	private static final long serialVersionUID = 1L;

	private static Session session;
	private static Query query;
	private static List<PayrollDate> payrollDateList;
	
	public static List<EmpPayrollMap> findAllEmpPayrollMaps() throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("EmpPayrollMap.findAll");
		List<EmpPayrollMap> empPayrollMapList = query.list();

		return empPayrollMapList;
	}

	public static List<EmpPayrollMap> findEmpPayrollMapByEmpPayroll(EmployeePayroll empPayroll) throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("EmpPayrollMap.findByEmpPayrollId");
		query.setParameter(0, empPayroll);
		
		List<EmpPayrollMap> empPayrollMapList = query.list();

		return empPayrollMapList;
	}

	public static EmpPayrollMap findEmpPayrollMapByEmpPayrollDate(EmployeePayroll empPayroll,
			Payroll payroll) throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("EmpPayrollMap.findByEmpPayrollId");
		query.setParameter(0, empPayroll);
		query.setParameter(1, payroll);
		
		List<EmpPayrollMap> empPayrollMapList = query.list();

		return empPayrollMapList.get(0);
	}

	public static List<Payroll> findAllPayrollRuns() throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Payroll.findAll");
		
		List<Payroll> payrollList = query.list();

		return payrollList;
	}

	public static List<Payroll> findPayrollRunByDate(Date runDate) throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Payroll.findByRunOnDate");
		query.setParameter(0, runDate);
		List<Payroll> payrollList = query.list();

		return payrollList;
	}
	
	public static List<PayrollDate> findAllPayrollDate() throws Exception {
		if(payrollDateList != null && !payrollDateList.isEmpty()) {
			return payrollDateList;
		}
		
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("PayrollDate.findAll");

		payrollDateList = query.list();
		return payrollDateList;
	}

	public static void insertPayrollDate(Date rDate) {
		PayrollDate payDate = new PayrollDate();
		payDate.setRunDate(rDate);
		session = OpenHRSessionFactory.getInstance().openSession();
		session.beginTransaction();
		try {
			session.save(payDate);
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static void insertEmpPayrollMap(EmpPayrollMap empPayMap) {
		session = OpenHRSessionFactory.getInstance().openSession();
		session.beginTransaction();
		try {
			session.save(empPayMap);
			session.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}
}

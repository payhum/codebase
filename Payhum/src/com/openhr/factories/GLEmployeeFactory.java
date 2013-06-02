package com.openhr.factories;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


import com.openhr.data.EmployeePayroll;
import com.openhr.data.GLEmployee;
import com.openhr.factories.common.OpenHRSessionFactory;

public class GLEmployeeFactory {
	private static final long serialVersionUID = 1L;

	private static Session session;
	private static Query query;
	private static List<GLEmployee> glemployees;


	public GLEmployeeFactory() {
	}
	
	
	public static List<GLEmployee> findAll() throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("GLEmployee.findAll");
		glemployees = query.list();
		
		session.getTransaction().commit();
		
		
		
		return glemployees;
	}
	
	
	public static List<Object[]> findCompanyView() throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		String sql="";
		String hql = "SELECT SUM(e.netPay) as net, SUM(e.taxAmount) as tax ,SUM(e.totalDeductions) as dedc, sum(e.baseSalary), sum(e.allowances) from EmployeePayroll e" ;
				Query query = session.createQuery(hql);
				List<Object[]>	glemployees = query.list();
				
				session.getTransaction().commit();
				
				return glemployees;
		
	}
	}
	


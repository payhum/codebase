package com.openhr.factories;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


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
	
	
	public static List<GLEmployee> findCompanyView() throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		String sql="";
		String hql = "SELECT id,accno,accname, date,SUM(gl.debit), SUM(gl.credit)FROM GLEmployee gl GROUP BY gl.accname";
				Query query = session.createQuery(hql);
				glemployees = query.list();
				
				session.getTransaction().commit();
				
				return glemployees;
		
	}
	}
	


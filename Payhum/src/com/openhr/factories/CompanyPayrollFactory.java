/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.factories;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.company.CompanyPayroll;
import com.openhr.factories.common.OpenHRSessionFactory;

/**
 * 
 * @author Vijay
 */
public class CompanyPayrollFactory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Session session;
	private static Query query;
	private static List<CompanyPayroll> compPayrolls;

	public CompanyPayrollFactory() {
	}

	public static List<CompanyPayroll> findAll() throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("CompanyPayroll.findAll");
		compPayrolls = query.list();
		session.getTransaction().commit();

		return compPayrolls;
	}

	public static List<CompanyPayroll> findById(Integer employeeId) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("CompanyPayroll.findById");
		query.setInteger(0, employeeId);
		compPayrolls = query.list();
		session.getTransaction().commit();

		return compPayrolls;
	}

	public static List<CompanyPayroll> findByCompanyId(Integer compId) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("CompanyPayroll.findByCompanyId");
		query.setInteger(0, compId);
		compPayrolls = query.list();
		session.getTransaction().commit();

		return compPayrolls;
	}
	
	public static List<CompanyPayroll> findByProcessedDate(Date processed) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("CompanyPayroll.findByProcessedDate");
		query.setDate(0, processed);
		compPayrolls = query.list();
		session.getTransaction().commit();

		return compPayrolls;
	}

	public static boolean delete(CompanyPayroll e) throws Exception{
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		CompanyPayroll comp = (CompanyPayroll) session.get(CompanyPayroll.class, e.getId());
		session.delete(comp);
		session.getTransaction().commit();
		session.flush();
		done = true;

		return done;
	}

	public static boolean insert(CompanyPayroll e) throws Exception {
		boolean done = false;

		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		session.save(e);
		session.getTransaction().commit();
		done = true;

		return done;
	}
}

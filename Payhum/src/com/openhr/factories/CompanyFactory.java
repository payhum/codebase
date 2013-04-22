/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.factories;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.company.Company;
import com.openhr.factories.common.OpenHRSessionFactory;

/**
 * 
 * @author Vijay
 */
public class CompanyFactory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Session session;
	private static Query query;
	private static List<Company> comps;

	public CompanyFactory() {
	}

	public static List<Company> findAll() throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Company.findAll");
		comps = query.list();
		session.getTransaction().commit();

		return comps;
	}

	public static List<Company> findById(Integer compId) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Company.findById");
		query.setInteger(0, compId);
		comps = query.list();
		session.getTransaction().commit();

		return comps;
	}

	public static List<Company> findByCompanyId(String compId) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Company.findByCompanyId");
		query.setString(0, compId);
		comps = query.list();
		session.getTransaction().commit();

		return comps;
	}
	
	public static List<Company> findByName(String name) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Company.findByName");
		query.setString(0, name);
		comps = query.list();
		session.getTransaction().commit();

		return comps;
	}
		

	public static boolean delete(Company e) throws Exception{
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		Company comp = (Company) session.get(Company.class, e.getId());
		session.delete(comp);
		session.getTransaction().commit();
		session.flush();
		done = true;

		return done;
	}

	public static boolean insert(Company e) throws Exception {
		boolean done = false;

		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		session.save(e);
		session.getTransaction().commit();
		done = true;

		return done;
	}
	

	public static boolean update(Company e) throws Exception {
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		// System.out.println("ALREADY IN SESSION : "+session.contains(session.get(Employee.class,
		// e.getId())));

		Company comp = (Company) session.get(Company.class, e.getId());

		comp.setCompanyId(e.getCompanyId());
		comp.setName(e.getName());
		
		session.update(comp);
		session.getTransaction().commit();
		
		done = true;

		return done;
	}

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.factories;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.company.Licenses;
import com.openhr.factories.common.OpenHRSessionFactory;

/**
 * 
 * @author Vijay
 */
public class LicenseFactory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Session session;
	private static Query query;
	private static List<Licenses> licenses;

	public LicenseFactory () {
	}

	public static List<Licenses> findAll() throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Licenses.findAll");
		licenses = query.list();
		session.getTransaction().commit();

		return licenses;
	}

	public static List<Licenses> findById(Integer id) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Licenses.findById");
		query.setInteger(0, id);
		licenses = query.list();
		session.getTransaction().commit();

		return licenses;
	}

	public static List<Licenses> findByCompanyId(Integer compId) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Licenses.findByCompanyId");
		query.setInteger(0, compId);
		licenses = query.list();
		session.getTransaction().commit();

		return licenses;
	}
	
	public static List<Licenses> findByActive(Integer active) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Licenses.findByActive");
		query.setInteger(0, active);
		licenses = query.list();
		session.getTransaction().commit();

		return licenses;
	}
		
	public static boolean delete(Licenses e) throws Exception{
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		Licenses license = (Licenses) session.get(Licenses.class, e.getId());
		session.delete(license);
		session.getTransaction().commit();
		session.flush();
		done = true;

		return done;
	}

	public static boolean insert(Licenses e) throws Exception {
		boolean done = false;

		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		e.formLicenseKey();
		session.save(e);
		session.getTransaction().commit();
		done = true;

		return done;
	}
	
	public static boolean update(Licenses e) throws Exception {
		boolean done = false;

		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		
		Licenses lis = (Licenses) session.get(Licenses.class, e.getId());

		lis.setCompanyId(e.getCompanyId());
		lis.setActive(e.getActive());
		
		session.update(lis);
		session.getTransaction().commit();
		done = true;

		return done;
	}

}

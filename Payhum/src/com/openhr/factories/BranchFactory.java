/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.factories;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.data.Branch;
import com.openhr.factories.common.OpenHRSessionFactory;

/**
 * 
 * @author Venkat
 */
public class BranchFactory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Session session;
	private static Query query;
	private static List<Branch> comps;

	public BranchFactory() {
	}

	@SuppressWarnings("unchecked")
	public static List<Branch> findAll() throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Branch.findAll");
		comps = query.list();
		session.getTransaction().commit();
 		return comps;
	}

	public static List<Branch> findById(Integer branchId) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Branch.findById");
		query.setInteger(0, branchId);
		comps = query.list();
		session.getTransaction().commit();

		return comps;
	}

	public static List<Branch> findByCompanyId(Integer compId) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Branch.findByCompanyId");
		query.setInteger(0, compId);
		comps = query.list();
		session.getTransaction().commit();

		return comps;
	}
	
	public static Integer findBranchLastId() throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Branch.findLastId");
		List<Branch> lastId = query.list();
 		session.getTransaction().commit();
		if (lastId.size() != 0) {
			return lastId.get(0).getId();
		} else {
			return 0;
		}
	}

	
	 
		

	public static boolean delete(Branch e) throws Exception{
		boolean done = false;
		Session session1 = OpenHRSessionFactory.getInstance().openSession();
		session1.beginTransaction();
		Branch comp = (Branch) session1.get(Branch.class, e.getId());
		session1.delete(comp);
		session1.getTransaction().commit();
		session1.flush();
		done = true;

		return done;
	}

	public static boolean insert(Branch e) throws Exception {
		boolean done = false;

		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		session.save(e);
		session.getTransaction().commit();
		done = true;

		return done;
	}
	

	public static boolean update(Branch e) throws Exception {
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		// System.out.println("ALREADY IN SESSION : "+session.contains(session.get(Employee.class,
		// e.getId())));

		Branch comp = (Branch) session.get(Branch.class, e.getId());

		comp.setCompanyId(e.getCompanyId());
		comp.setName(e.getName());
		comp.setAddress(e.getAddress());
		
		session.update(comp);
		session.getTransaction().commit();
		
		done = true;

		return done;
	}

}

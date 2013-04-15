/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.factories;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.data.EmpBankAccount;
import com.openhr.data.EmployeePayroll;
import com.openhr.factories.common.OpenHRSessionFactory;

/**
 * 
 * @author Vijay
 */
public class EmpBankAccountFactory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Session session;
	private static Query query;
	private static List<EmpBankAccount> empBankAccounts;

	public EmpBankAccountFactory() {
	}

	public static List<EmpBankAccount> findAll() throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("EmpBankAccount.findAll");
		empBankAccounts = query.list();
		session.getTransaction().commit();
		
		return empBankAccounts;
	}

	public static List<EmpBankAccount> findById(Integer id) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("EmpBankAccount.findById");
		query.setInteger(0, id);
		empBankAccounts = query.list();
		session.getTransaction().commit();
		
		return empBankAccounts;
	}

	public static EmpBankAccount findByEmployeeId(Integer employeeId) throws Exception{
		session = OpenHRSessionFactory.getInstance().openSession();
		session.beginTransaction();
		EmpBankAccount eBank = (EmpBankAccount) session.get(EmpBankAccount.class, employeeId);
		
		if(eBank == null ) {
			query = session.getNamedQuery("EmpBankAccount.findByEmployeeId");
			query.setInteger(0, employeeId);
			eBank = (EmpBankAccount) query.list().get(0);
			session.getTransaction().commit();
		}
		session.close();
		return eBank;
	}
	
	public static boolean delete(EmpBankAccount e) throws Exception{
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		EmpBankAccount eBankAccnt = (EmpBankAccount) session.get(EmpBankAccount.class, e.getId());
		session.delete(eBankAccnt);
		session.flush();
		session.getTransaction().commit();
		done = true;
		session.close();
		return done;
	}

	public static boolean insert(EmpBankAccount e) throws Exception {
		boolean done = false;

		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		session.save(e);
		session.getTransaction().commit();
		done = true;
		session.close();
		return done;
	}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.factories;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


import com.openhr.data.BrachData;
import com.openhr.data.DepartBrachData;
import com.openhr.data.Employee;
import com.openhr.data.TypesData;

import com.openhr.factories.common.OpenHRSessionFactory;

/**
 * 
 * @author Mekbib
 */
public class EmployeeFactory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Session session;
	private static Query query;
	private static List<Employee> employees;


	public EmployeeFactory() {
	}

    public static List<Object[]> findAllDepartEmpChart()
	{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		String hql=" select   count(d.id) as num ,  d.name from Employee e , DepartBrachData d where e.companyId=d.id group by e.companyId";
		
		query = session.createQuery(hql);
		List<Object[]> lob=query.list();
		session.getTransaction().commit();
		return lob;
	}
	
	public static Integer findLastId() throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findLastId");
		List<Employee> lastId = query.list();
		session.getTransaction().commit();
		if (lastId.size() != 0) {
			return lastId.get(0).getId();
		} else {
			return 0;
		}
	}

	public static List<Employee> findAll() throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findAll");
		employees = query.list();
		
		session.getTransaction().commit();
		
		
		
		return employees;
	}

	public static List<Employee> findById(Integer employeeId) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findById");
		query.setInteger(0, employeeId);
		employees = query.list();
		session.getTransaction().commit();

		return employees;
	}

	public static List<Employee> findByEmployeeId(String employeeId) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findByEmployeeId");
		query.setString(0, employeeId);
		employees = query.list();
		session.getTransaction().commit();

		return employees;
	}
	
	
	public static List<Employee> findByName(String EmployeeName) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findByName");
		query.setString(0, EmployeeName);
		employees = query.list();
		session.getTransaction().commit();

		return employees;
	}
	
	public static List<Employee> findAllByCompanyID(Integer compID) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findByCompanyID");
		query.setInteger(0, compID);
		employees = query.list();
		session.getTransaction().commit();

		return employees;
	}

	public static List<Employee> findActiveByCompanyID(Integer compID) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findActiveByCompanyID");
		query.setInteger(0, compID);
		List<Employee> activeEmps = query.list();
		session.getTransaction().commit();

		return activeEmps;
	}
	
	public static List<Employee> findInActiveByCompanyIDAndDate(Integer compID, Date currDate) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findInActiveByCompanyIDAndDate");
		query.setInteger(0, compID);
		query.setDate(1, currDate);
		query.setDate(2, currDate);
		List<Employee> inactiveEmps = query.list();
		session.getTransaction().commit();

		return inactiveEmps;
	}
	
	public static boolean delete(Employee e) throws Exception{
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		Employee emp = (Employee) session.get(Employee.class, e.getId());
		session.delete(emp);
		session.getTransaction().commit();
		session.flush();
		done = true;

		return done;
	}

public static boolean deleteGridId(Integer e) throws Exception{
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
try{
		Employee emp = (Employee) session.get(Employee.class, e);
		session.delete(emp);
		session.getTransaction().commit();}
catch(Exception ex)
{}

finally
{
	session.flush();
}
	
		done = true;
		
		

		return done;
	}

	public static boolean insert(Employee e) throws Exception {
		boolean done = false;

		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		session.save(e);
		session.getTransaction().commit();
		done = true;

		return done;
	}

	public static boolean update(Employee e) throws Exception {
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		// System.out.println("ALREADY IN SESSION : "+session.contains(session.get(Employee.class,
		// e.getId())));

		Employee emp = (Employee) session.get(Employee.class, e.getId());

		emp.setEmployeeId(e.getEmployeeId());
		emp.setFirstname(e.getFirstname());
		emp.setMiddlename(e.getMiddlename());
		emp.setLastname(e.getLastname());
		emp.setSex(e.getSex());
		emp.setBirthdate(new Date(e.getBirthdate()));
		emp.setHiredate(new Date(e.getHiredate()));
		emp.setPositionId(e.getPositionId());
		emp.setPhoto(e.getPhoto());
		emp.setStatus(e.getStatus());
		session.getTransaction().commit();
		done = true;

		return done;
	}

	public static List<BrachData> findBrachAll() {
		Session session12 = OpenHRSessionFactory.getInstance().openSession();
		session12.beginTransaction();
		query = session12.getNamedQuery("BrachData.findAll");
		List<BrachData> brd = query.list();
		session12.getTransaction().commit();
		session12.close();
		return brd;
	}
	
	public static List<DepartBrachData> findBrachDepart(Integer compID) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("DepartBrachData.findByBrachId");
		query.setInteger(0, compID);
		List<DepartBrachData>	depList = query.list();
		session.getTransaction().commit();

		return depList;
	}
	
	
	public static List<TypesData> findTypes(String resd) {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("TypesData.findByType");
		query.setString(0, resd);
		List<TypesData> tyd = query.list();
		session.getTransaction().commit();

		return tyd;
	}
	
	public static DepartBrachData findDepartById(Integer compID) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("DepartBrachData.findById");
		query.setInteger(0, compID);
	DepartBrachData	depList = (DepartBrachData) query.list().get(0);
		session.getTransaction().commit();

		return depList;
	}
	
	public static TypesData findTypesById(Integer compID) throws Exception{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("TypesData.findById");
		query.setInteger(0, compID);
		TypesData	tyd = (TypesData) query.list().get(0);
		session.getTransaction().commit();

		return tyd;
	}
}

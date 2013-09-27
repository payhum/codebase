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
import org.hibernate.Transaction;

import com.openhr.data.Benefit;
import com.openhr.data.Branch;
import com.openhr.data.ConfigData;
import com.openhr.data.Department;
import com.openhr.data.EmpBankAccount;
import com.openhr.data.EmpDependents;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeBonus;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.EmployeeSalary;
import com.openhr.data.Position;
import com.openhr.data.TypesData;
import com.openhr.common.PayhumConstants;
import com.openhr.company.Company;
import com.openhr.data.Users;

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
	public static List<Employee> findAllEmpPerDepart(Department depId)
	
	{
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findByDeptID");
		query.setParameter(0, depId);
		employees = query.list();
		session.getTransaction().commit();

		return employees;
	}
	public static boolean saveUpdateBankDet(EmpBankAccount ebn) {
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		try {
			if (ebn.getId() == null) {

				session.save(ebn);
			}

			else {
				// EmpBankAccount ebns= getBankEmpbyId(ebn.getId());

				session.update(ebn);

			}

			session.getTransaction().commit();
			done = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
		return done;
	}

	public static EmployeeSalary getEmpsalry(Employee ees) {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("EmployeeSalary.findSal");
		// query.setInteger(0, id);
		query.setParameter(0, ees);
		EmployeeSalary empAcc = (EmployeeSalary) query.uniqueResult();
		session.getTransaction().commit();
		return empAcc;
	}

	public static EmpBankAccount getBankEmpbyId(Integer id) {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("EmpBankAccount.findById");
		// query.setInteger(0, id);
		query.setInteger(0, id);
		EmpBankAccount empAcc = (EmpBankAccount) query.uniqueResult();
		session.getTransaction().commit();
		return empAcc;
	}

	public static EmpBankAccount getBankDetails(Employee id) {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("EmpBankAccount.findByEmployeeId");
		// query.setInteger(0, id);
		query.setParameter(0, id);
		EmpBankAccount empAcc = (EmpBankAccount) query.uniqueResult();
		session.getTransaction().commit();
		return empAcc;
	}

	public static boolean saveBonus(EmployeeBonus usrs) {
		boolean done = false;

		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		try {
			session.save(usrs);
			session.getTransaction().commit();
			done = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
		return done;

	}

	public static EmployeeSalary getCurrentSalry(Employee id) {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("EmployeeSalary.findSal");
		// query.setInteger(0, id);
		query.setParameter(0, id);
		EmployeeSalary empsal = (EmployeeSalary) query.uniqueResult();
		session.getTransaction().commit();
		return empsal;
	}

	public static boolean saveSal(EmployeeSalary id, Integer ids) {
		boolean done = false;
		Session session2 = OpenHRSessionFactory.getInstance().openSession();
		Transaction t = session2.beginTransaction();

		EmployeeSalary es;
		try {
			if (ids != null) {
				es = findEmpSalryById(ids).get(0);
				es.setTodate(new Date((id.getFromdate().getTime())));
				boolean flag = empsalUpdate(es);
				if (flag)

				{

					session2.save(id);
				}

			} else {

				session2.save(id);
			}

			session2.flush();
			t.commit();

			done = true;
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			t.rollback();
			session2.close();
		}

		// query.setInteger(0, id);
		// query.setParameter(0, id);
		// EmployeeSalary empsal= (EmployeeSalary) query.uniqueResult();

		finally {
			session2.close();
		}
		return done;
	}

	public static boolean empsalUpdate(EmployeeSalary empS) {

		boolean done = false;

		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		session.update(empS);

		session.getTransaction().commit();
		done = true;

		return done;

	}

	public static boolean saveDepdent(EmpDependents empD) {

		boolean done = false;

		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		session.save(empD);
		session.flush();
		session.getTransaction().commit();
		done = true;

		return done;

	}

	public static List<EmpDependents> findEmpDepdentAll(Employee e) {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("EmpDependents.findIndual");
		// query.setInteger(0, id);
		query.setParameter(0, e);
		List<EmpDependents> empDep = query.list();
		session.getTransaction().commit();
		return empDep;
	}

	public static List<Object[]> findAllDepartEmpChart() {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		String hql = " select   count(d.id) as num, d.deptname from Employee e , Department d where e.deptId=d.id AND e.firstname != 'Guest' group by e.deptId";

		query = session.createQuery(hql);
		List<Object[]> lob = query.list();
		session.getTransaction().commit();
		return lob;
	}

	public static List<Object[]> findAllBranchEmpChart() {
		Session session1 = OpenHRSessionFactory.getInstance().openSession();
		session1.beginTransaction();
		String hql = "select count(d.branchId), b.name from Department d,Branch b where d.branchId=b.id group by d.branchId";

		Query query1 = session1.createQuery(hql);
		List<Object[]> lob1 = query1.list();
		session1.getTransaction().commit();
		return lob1;
	}

	public static Integer findLastId() throws Exception {
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

	public static Integer findCompanyLastId() throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Company.findLastId");
		List<Company> lastId = query.list();
		session.getTransaction().commit();
		if (lastId.size() != 0) {
			return lastId.get(0).getId();
		} else {
			return 0;
		}
	}

	public static List<Employee> findAll() throws Exception {
		
		ConfigData userComp = ConfigDataFactory.findByName(PayhumConstants.LOGGED_USER_COMP); 
		Integer compId = Integer.parseInt(userComp.getConfigValue());
		
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		if(compId.compareTo(1) == 0) {
			query = session.getNamedQuery("Employee.findAll");
		} else {
			query = session.getNamedQuery("Employee.findAllByComp");
			query.setInteger(0, compId);
		}
		
		employees = query.list();
		session.getTransaction().commit();
		
		return employees;
	}
	
	public static List<Employee> findAllEmpByComp(Integer compId) throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		
		query = session.getNamedQuery("Employee.findAllByComp");
		query.setInteger(0, compId);
		
		employees = query.list();
		session.getTransaction().commit();
		
		return employees;
	}

	public static List<EmployeeSalary> findEmpSalryById(Integer Id)
			throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("EmployeeSalary.findById");
		query.setInteger(0, Id);
		List<EmployeeSalary> employees = query.list();
		session.getTransaction().commit();

		return employees;
	}

	public static List<Employee> findById(Integer employeeId) throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findById");
		query.setInteger(0, employeeId);
		employees = query.list();
		session.getTransaction().commit();

		return employees;
	}

	public static List<Employee> findByEmployeeId(String employeeId)
			throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findByEmployeeId");
		query.setString(0, employeeId);
		employees = query.list();
		session.getTransaction().commit();

		return employees;
	}

	public static List<Employee> findByName(String EmployeeName)
			throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findByName");
		query.setString(0, EmployeeName);
		employees = query.list();
		session.getTransaction().commit();

		return employees;
	}

	public static List<Employee> findActiveByDeptID(Integer deptId)
			throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findActiveByDeptID");
		query.setInteger(0, deptId);
		List<Employee> activeEmps = query.list();
		session.getTransaction().commit();

		return activeEmps;
	}

	public static List<Employee> findInActiveByDeptIDAndDate(Integer deptId,
			Date currDate) throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findInActiveByDeptIDAndDate");
		query.setInteger(0, deptId);
		query.setDate(1, currDate);
		query.setDate(2, currDate);
		List<Employee> inactiveEmps = query.list();
		session.getTransaction().commit();

		return inactiveEmps;
	}

	public static boolean delete(Employee e) throws Exception {
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

	public static boolean deleteGridId(Integer e) throws Exception {
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		try {
			Employee emp = (Employee) session.get(Employee.class, e);
			session.delete(emp);
			session.getTransaction().commit();
		} finally {
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
		emp.setBirthdate(e.getBirthdate());
		emp.setHiredate(e.getHiredate());
		emp.setPositionId(e.getPositionId());
		emp.setPhoto(e.getPhoto());
		emp.setStatus(e.getStatus());
		emp.setDeptId(e.getDeptId());
		emp.setInactiveDate(e.getInactiveDate());
		session.getTransaction().commit();
		done = true;

		return done;
	}

	public static List<Branch> findBrachAll() {
		Session session12 = OpenHRSessionFactory.getInstance().openSession();
		session12.beginTransaction();
		query = session12.getNamedQuery("Branch.findAll");
		List<Branch> brd = query.list();
		session12.getTransaction().commit();
		session12.close();
		return brd;
	}

	public static List<Branch> findBranchByCompId(Integer compId) {
		Session session12 = OpenHRSessionFactory.getInstance().openSession();
		session12.beginTransaction();
		query = session12.getNamedQuery("Branch.findByCompanyId");
		query.setInteger(0, compId);
		List<Branch> brd = query.list();
		session12.getTransaction().commit();
		session12.close();
		return brd;
	}

	public static List<Department> findBrachDepart(Integer compID)
			throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		if(compID!=0)
		{
			query = session.getNamedQuery("Department.findByBranchId");
		 query.setInteger(0, compID);
		}
		else
		{
			query = session.getNamedQuery("Department.findAll");
		}
		List<Department> depList = query.list();
		session.getTransaction().commit();

		return depList;
	}

	public static List<TypesData> findTypes(String resd) {
	Session session=null;
		boolean flagSession=false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		if (session == null || session.isOpen() == false) 
		{
			flagSession=true;
			 session=OpenHRSessionFactory.getInstance().openSession();
		}
		session.beginTransaction();
		query = session.getNamedQuery("TypesData.findByType");
		query.setString(0, resd);
		List<TypesData> tyd = query.list();
		session.getTransaction().commit();
if(flagSession)
	
{
	
	System.out.println("flagSession --"+flagSession);
	session.close();	
}
		return tyd;
	}
	public static boolean findPostById(Position compID) throws Exception {
		boolean flag=false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findAllPostionID");
		query.setParameter(0, compID);
		List<Position> plist= query.list();
		
		if(plist.size()>0)
		{
			flag=true;
		}
		session.getTransaction().commit();

		return flag;
	}	
	public static Department findDepartById(Integer compID) throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Department.findById");
		query.setInteger(0, compID);
		Department depList = (Department) query.list().get(0);
		session.getTransaction().commit();

		return depList;
	}

	public static TypesData findTypesById(Integer compID) throws Exception {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("TypesData.findById");
		query.setInteger(0, compID);
		TypesData tyd = (TypesData) query.list().get(0);
		session.getTransaction().commit();

		return tyd;
	}

	public static List<Employee> findAllActive(Integer compId) {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findAllActive");
		query.setInteger(0, compId);
		
		List<Employee> activeEmps = query.list();
		session.getTransaction().commit();

		return activeEmps;
	}

	public static List<Employee> findInActiveByDate(Date time, Integer compId) {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findInActiveByDate");
		query.setParameter(0, time);
		query.setParameter(1, time);
		query.setInteger(2, compId);
		List<Employee> inactiveEmps = query.list();
		session.getTransaction().commit();

		return inactiveEmps;
	}

	public static List<Employee> findAllActiveByBranch(Integer branchId) {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findAllActiveByBranch");
		Branch branch = new Branch();
		branch.setId(branchId);
		query.setParameter(0, branch);
		List<Employee> activeEmps = query.list();
		session.getTransaction().commit();

		return activeEmps;
	}

	public static List<Employee> findInActiveByDateAndBranch(Date time,
			Integer branchId) {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Employee.findInActiveByDateAndBranch");
		Branch branch = new Branch();
		branch.setId(branchId);
		query.setParameter(0, time);
		query.setParameter(1, time);
		query.setParameter(2, branch);
		List<Employee> inactiveEmps = query.list();
		session.getTransaction().commit();

		return inactiveEmps;
	}
	
	
	public static boolean insertAll(Employee e, EmployeePayroll ep, EmployeeSalary empsal,
			Benefit ben, EmpBankAccount empBank, List<EmpDependents> dependents) {
		
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
	    Transaction   tx = session.beginTransaction();
	    
	    try {
	    	session.save(e);
	        session.save(ep);
	        session.save(empsal);
	        session.save(ben);
	        
	        if(empBank != null) {
	        	session.save(empBank);
	        }
	        
	        for(EmpDependents dep : dependents) {
	        	session.save(dep);
	        }
	        
	        tx.commit();
	        done = true;
	    } catch (Exception ex) {
	    	tx.rollback();
	        ex.printStackTrace();
	    }finally{
	    }
	    return done;
	}
}

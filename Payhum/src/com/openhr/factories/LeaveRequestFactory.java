package com.openhr.factories;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.data.Department;
import com.openhr.data.LeaveApproval;
import com.openhr.data.LeaveRequest;
import com.openhr.factories.common.OpenHRSessionFactory;

public class LeaveRequestFactory {
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	private static Session session;
    private static Query query;
    private static List<LeaveRequest> leaveRequests;
    private static List<LeaveApproval> appLeaves;

    public LeaveRequestFactory() {
    } 

     
    @SuppressWarnings("unchecked")
	public static List<LeaveRequest> findAll() {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("LeaveRequest.findAll");
        leaveRequests = query.list();
        session.getTransaction().commit();
        
        return leaveRequests;
    }
   
    
    		
    		
    		
    		public static   List<Object[]>  findByStatusNodaysJion(Integer c,Department d)
    	    
    	    
    	    {
    	    	List<Object[]> results=null;
    	    	 session = OpenHRSessionFactory.getInstance().getCurrentSession();
    	         session.beginTransaction();
    	         try
    	         {
    	        	 String hql= "select e.employeeId, sum(lr.status)  from  LeaveRequest lr, Employee e  where  e.id=lr.employeeId and  lr.status="+c+" and e.deptId="+d+"group by lr.employeeId" ;	         
    	        	 
    	         Query query = session.createQuery(hql);
    	        // query.setParameter("c", 1);
    	        
    	         results=query.list();
    	         session.getTransaction().commit();
    	         }
    	         catch(Exception e)
    	         {
    	        	 e.printStackTrace();
    	         }
    	         
    	         return results;
    	    }
    	    		
    		
    		
    		
    		
    		
    		
    		public static   List<Object[]>  findByStatusNodays(Integer c)
    
    
    {
    	List<Object[]> results=null;
    	 session = OpenHRSessionFactory.getInstance().getCurrentSession();
         session.beginTransaction();
         try
         {
         String hql="select lr.employeeId, sum(lr.status) from LeaveRequest lr where  lr.status = "+c+"group by lr.employeeId";
         Query query = session.createQuery(hql);
        // query.setParameter("c", 1);
         query=session.createQuery(hql);
         results=query.list();
         session.getTransaction().commit();
         }
         catch(Exception e)
         {
        	 e.printStackTrace();
         }
         
         return results;
    }
    
    
    @SuppressWarnings("unchecked")
	public static List<LeaveRequest> findByEmployeeId(Integer employeeId){
     	session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("LeaveRequest.findByEmployeeId");
        query.setInteger(0, employeeId);
         leaveRequests = query.list();
        session.getTransaction().commit();
        return leaveRequests;
    	
    }
    
    @SuppressWarnings("unchecked")
	public static List<LeaveRequest> findById(Integer leaveId) {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("LeaveRequest.findById");
        query.setInteger(0, leaveId);
        leaveRequests = query.list();
        session.getTransaction().commit();
        
        return leaveRequests;
    }

    @SuppressWarnings("unchecked")
	public static List<LeaveRequest> findByLeaveDate(Date leaveDate) {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("LeaveRequest.findByLeaveDate");
        query.setDate(0, leaveDate);
        leaveRequests = query.list();
        session.getTransaction().commit();
        
        return leaveRequests;
    }

    @SuppressWarnings("unchecked")
	public static List<LeaveRequest> findByReturnDate(Date returnDate) {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("LeaveRequest.findByReturnDate");
        query.setDate(0, returnDate);
        leaveRequests = query.list();
        session.getTransaction().commit();
        
        return leaveRequests;
    } 
    
    @SuppressWarnings("unchecked")
	public static List<LeaveRequest> findByStatus(Integer status) {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("LeaveRequest.findByStatus");
        query.setInteger(0, status);
        leaveRequests = query.list();
        session.getTransaction().commit();
        
        return leaveRequests;
    }
    
    @SuppressWarnings("unchecked")
	public static List<LeaveRequest> findByNoOfDays(Integer noOfDays) {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("LeaveRequest.findByNoOfDays");
        query.setInteger(0, noOfDays);
        leaveRequests = query.list();
        session.getTransaction().commit();
        
        return leaveRequests;
    }

    public static boolean delete(LeaveRequest l) {
        boolean done = false;
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();  
        try {
            LeaveRequest leave = (LeaveRequest)session.get(LeaveRequest.class, l.getId());
            session.delete(leave);
            session.flush();
            session.getTransaction().commit();
            done = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	
        }
        return done;
    }

    public static boolean insert(LeaveRequest l) {
        boolean done = false;

        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        try {
            session.save(l);
            session.getTransaction().commit();
            done = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	
        }
        return done;
    }

    public static boolean update(LeaveRequest l) {
        boolean done = false;
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
         
        try {
            LeaveRequest leaveRequest = (LeaveRequest)session.get(LeaveRequest.class, l.getId());
            leaveRequest.setLeaveTypeId(l.getLeaveTypeId());
            leaveRequest.setLeaveDate(new Date(l.getLeaveDate()));
            leaveRequest.setReturnDate(new Date(l.getReturnDate()));
            leaveRequest.setStatus(l.getStatus());
            leaveRequest.setNoOfDays(l.getNoOfDays());
            leaveRequest.setDescription(l.getDescription());
            leaveRequest.setEmployeeId(l.getEmployeeId());
            session.getTransaction().commit();
            done = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	
        }
        return done;
    }
    
    public static List<LeaveApproval> findByLeaveId(Integer leaveId) {
 		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("LeaveApproval.findById");
		query.setInteger(0, leaveId);
		appLeaves = query.list();
		session.getTransaction().commit();

		return appLeaves;
	}
    
	public static boolean insert(LeaveApproval l) {
		boolean done = false;

		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		try {
			session.save(l);
			session.getTransaction().commit();
			done = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return done;
	}

}

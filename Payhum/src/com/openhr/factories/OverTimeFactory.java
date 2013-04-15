package com.openhr.factories;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.data.OverTime;
import com.openhr.factories.common.OpenHRSessionFactory;
 
public class OverTimeFactory {
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	private static Session session;
    private static Query query;
    private static List<OverTime> overTimes;

    public OverTimeFactory() {
    }

     
    @SuppressWarnings("unchecked")
	public static List<OverTime> findAll() {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("OverTime.findAll");
        overTimes = query.list();
        session.getTransaction().commit();
        
        return overTimes;
    }

 	 
    
    @SuppressWarnings("unchecked")
	public static List<OverTime> findById(Integer leaveId) {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("OverTime.findById");
        query.setInteger(0, leaveId);
        overTimes = query.list();
        session.getTransaction().commit();
        
        return overTimes;
    }
    
    @SuppressWarnings("unchecked")
	public static List<OverTime> findByStatus(Integer status) {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("OverTime.findByStatus");
        query.setInteger(0, status);
        overTimes = query.list();
        session.getTransaction().commit();
        
        return overTimes;
    }

    
    @SuppressWarnings("unchecked")
   	public static List<OverTime> findByEmployeeId(Integer employeeId){
        	session = OpenHRSessionFactory.getInstance().getCurrentSession();
           session.beginTransaction();
           query = session.getNamedQuery("OverTime.findByEmployeeId");
           query.setInteger(0, employeeId);
           overTimes = query.list();
           session.getTransaction().commit();
           return overTimes;
       	
       }
    

    public static boolean delete(OverTime ot) {
        boolean done = false;
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();  
        try {
        	OverTime overTime = (OverTime)session.get(OverTime.class, ot.getId());
            session.delete(overTime);
            session.flush();
            session.getTransaction().commit();
            done = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	
        }
        return done;
    }

    public static boolean insert(OverTime l) {
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

    public static boolean update(OverTime ot) {
    	boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
 		OverTime overTime = (OverTime) session.get(OverTime.class, ot.getId());
 		overTime.setEmployeeId(ot.getEmployeeId());
 		overTime.setOverTimeDate(new Date(ot.getOverTimeDate()));
  		overTime.setApprovedDate(new Date(ot.getApprovedDate()));
  		overTime.setNoOfHours(ot.getNoOfHours());
  		overTime.setStatus(ot.getStatus());
 		overTime.setApprovedBy(ot.getApprovedBy());
		session.getTransaction().commit();
		done = true;
 		return done;
    }
}

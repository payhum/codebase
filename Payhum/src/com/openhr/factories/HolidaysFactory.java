package com.openhr.factories;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.data.Holidays;
import com.openhr.data.LeaveRequest;
import com.openhr.factories.common.OpenHRSessionFactory;
 
public class HolidaysFactory {
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	private static Session session;
    private static Query query;
    private static List<Holidays> holidays;

    public HolidaysFactory() {
    }

     
    @SuppressWarnings("unchecked")
	public static List<Holidays> findAll() {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("Holidays.findAll");
        holidays = query.list();
        session.getTransaction().commit();
        return holidays;
    }
    
    @SuppressWarnings("unchecked")
   	public static List<Holidays> findByDate(Date date) {
           session = OpenHRSessionFactory.getInstance().getCurrentSession();
           session.beginTransaction();
           query = session.getNamedQuery("Holidays.findByDate");
           query.setDate(0, date);
           holidays = query.list();
           session.getTransaction().commit();
           return holidays;
      }

 	 
    public static boolean delete(Holidays holiday) {
        boolean done = false;
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();  
        try {
        	Holidays h = (Holidays)session.get(Holidays.class, holiday.getId());
            session.delete(h);
            session.flush();
            session.getTransaction().commit();
            done = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	
        }
        return done;
    }

    public static boolean insert(Holidays h) {
        boolean done = false;

        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        try {
            session.save(h);
            session.getTransaction().commit();
            done = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	
        }
        return done;
    }
    
}

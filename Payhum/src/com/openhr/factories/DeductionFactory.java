package com.openhr.factories;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.data.DeductionsType;
import com.openhr.data.Employee;
import com.openhr.data.Position;
import com.openhr.data.Roles;
import com.openhr.data.Users;
import com.openhr.factories.common.OpenHRSessionFactory;


public class DeductionFactory {
	private static final long serialVersionUID = 1L;

	private static Session session;
	private static Query query;
	private static List<DeductionsType> decType;
	public DeductionFactory(){
		
	}
	
	public static List<DeductionsType> findAll() {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("DeductionsType.findAll");
		decType = query.list();
		session.getTransaction().commit();

		return decType;
	}
	
    public static boolean checkAval(DeductionsType dt) {
        boolean done = false;

        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("DeductionsType.findByName");
        query.setString(0, dt.getName());
      if(query.list().size()>0)
    	  
      {
    	  done=true;
    	  
      }
        session.getTransaction().commit();
        return done;
        
    }
    
    
    public static boolean insert(DeductionsType dt) {
        boolean done = false;

        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        try { 
        	session.save(dt);            
            session.getTransaction().commit();
            done = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	
        }
        return done;
        
    }
    
    
	public static boolean delete(DeductionsType dt) throws Exception{
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Query query1 = session.getNamedQuery("DeductionsDone.findByType");
		 query1.setInteger(0, dt.getId());
		if(query1.list().size()>0)
		{
			
			
		}
		else 
			{
			DeductionsType dtp = (DeductionsType) session.get(DeductionsType.class, dt.getId());
			session.delete(dtp);
			done = true;
			}
        
		
		session.getTransaction().commit();
		
	

		return done;
	}
    
    public static List<DeductionsType> findById(Integer usersId) {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("DeductionsType.findById");
        query.setInteger(0, usersId);
        decType = query.list();
        session.getTransaction().commit();
        
        return decType;
    }
    public static List<DeductionsType>  findByName(String usersName) {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("DeductionsType.findByName");
        query.setString(0, usersName);
        decType = query.list();
        session.getTransaction().commit();
        
        return decType;
    }
    public static boolean update(DeductionsType dt) {
        boolean done = false;
        List<DeductionsType> matchingName =DeductionFactory.findByName(dt.getName());
        
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();

        try {
        	DeductionsType dts = null;
        	
        	if(dt.getId() != null) {
        		 dts = (DeductionsType) session.get(DeductionsType.class, dt.getId()); ;
        	}
        	else if (matchingName.size() > 0){
        		dts = (DeductionsType) session.get(DeductionsType.class, matchingName.get(0).getId()); ;
        	}
        	
        	dts.setName(dt.getName());
        	dts.setDescription(dt.getDescription());
            
            if(dt.getId() != null)
            	dts.setId(dt.getId());
            
            
            
            session.update(dts);
            session.getTransaction().commit();
            done = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	
        }
        return done;
    }

	
}

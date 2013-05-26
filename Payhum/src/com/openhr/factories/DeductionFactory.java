package com.openhr.factories;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.common.PayhumConstants;
import com.openhr.data.DeductionsType;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.Exemptionstype;
import com.openhr.data.Position;
import com.openhr.data.Roles;
import com.openhr.data.Users;
import com.openhr.factories.common.OpenHRSessionFactory;
import com.openhr.taxengine.DeductionsDeclared;
import com.openhr.taxengine.DeductionsDone;


public class DeductionFactory {
	private static final long serialVersionUID = 1L;

	private static Session session;
	private static Query query;
	private static List<DeductionsType> decType;
	private static List<Exemptionstype> exmType;
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
	
	public static List<DeductionsType> findAllToDisplay() {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("DeductionsType.findAll");
		decType = query.list();
		
		List<DeductionsType> retList = new ArrayList<DeductionsType>();
		for(DeductionsType dt : decType) {
			if(dt.getName().equalsIgnoreCase(PayhumConstants.SELF_LIFE_INSURANCE)
			|| dt.getName().equalsIgnoreCase(PayhumConstants.SPOUSE_LIFE_INSURANCE)
			|| dt.getName().equalsIgnoreCase(PayhumConstants.DONATION)) {
				retList.add(dt);
			}  
		}
		
		session.getTransaction().commit();

		return retList;
	}
	
	public static List<Exemptionstype> findAllExemptionTypes() {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("Exemptionstype.findAll");
		exmType = query.list();
		session.getTransaction().commit();

		return exmType;
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
    
    public static boolean checkDeductionDeclare(DeductionsType dt) {
        boolean done = false;

        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("DeductionsDeclared.findType");
        query.setParameter(0, dt);
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

    public static boolean insertDeducDec(DeductionsDeclared dt) {
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
	public  static List<DeductionsDeclared> deductionsDecl(Integer payrollId)
			throws Exception {
		EmployeePayroll ePay = new EmployeePayroll();
		ePay.setId(payrollId);
		Session session1 = OpenHRSessionFactory.getInstance().openSession();
		session1.beginTransaction();
		query = session1.getNamedQuery("DeductionsDeclared.find");
		query.setParameter(0, ePay);
		List<DeductionsDeclared> empsum2 = query.list();

		session1.getTransaction().commit();
        session1.close();
		return empsum2;
	}
	
	
	
    public static boolean deleteDeducDec(Integer usrs) {
        boolean done = false;
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        try {
        	DeductionsDeclared dcr = (DeductionsDeclared) session.get(DeductionsDeclared.class, usrs);
            session.delete(dcr);
            session.flush();
            session.getTransaction().commit();
            done = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	
        }
        return done;
    }
	
	
}

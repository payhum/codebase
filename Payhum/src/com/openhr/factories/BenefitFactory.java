/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.factories;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import com.openhr.data.Benefit;
import com.openhr.data.Benefit;
import com.openhr.factories.common.OpenHRSessionFactory;

/**
 *
 * @author Mekbib
 */
public class BenefitFactory {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Session session;
    private static Query query;
    private static List<Benefit> benefits;

    public BenefitFactory() {
    }

    
    public static List<Benefit> search(Benefit b){
    	session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Benefit.class);
        if(b.getId()!=null)
        {
        	criteria.add(Expression.eq("id", b.getId()));
        }
        if(b.getAmount()!=0)
        {        	
        	criteria.add(Expression.gt("amount", b.getAmount()));        	
        }
        
        if(b.getEmployeeId()!=null)
        {
        	criteria.add(Expression.eq("employeeId", b.getEmployeeId()));
        }
        
        if(b.getTypeId()!=null)
        {
        	criteria.add(Expression.eq("typeId", b.getTypeId()));
        }
        
        
        benefits = criteria.list();
        
    	return benefits;
    }
    
    
    
    
    public static List<Benefit> findAll() {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("Benefit.findAll");
        benefits = query.list();
        session.getTransaction().commit();
        
        return benefits;
    }

    public static List<Benefit> findById(Integer BenefitId) {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("Benefit.findById");
        query.setInteger(0, BenefitId);
        benefits = query.list();
        session.getTransaction().commit();
        
        return benefits;
    }

    public static List<Benefit> findByName(Double amount) {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("Benefit.findByAmount");
        query.setDouble(0, amount);
        benefits = query.list();
        session.getTransaction().commit();
        
        return benefits;
    } 


    public static boolean delete(Benefit bt) {
        boolean done = false;
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();  
        try {
            Benefit benefit = (Benefit)session.get(Benefit.class, bt.getId());
            session.delete(benefit);
            session.getTransaction().commit();            
            done = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return done;
    }

    public static boolean insert(Benefit bt) {
        boolean done = false;

        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        try {
            session.save(bt);
            session.getTransaction().commit();
            done = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return done;
    }

    public static boolean update(Benefit bt) {
        boolean done = false;
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();

        try {
        	Benefit Benefit = (Benefit)session.get(Benefit.class, bt.getId());
            Benefit.setAmount(bt.getAmount()); 
            Benefit.setTypeId(bt.getTypeId());
            session.getTransaction().commit();
            done = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return done;
    }
    
} 

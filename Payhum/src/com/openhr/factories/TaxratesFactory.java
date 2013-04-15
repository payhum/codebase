package com.openhr.factories;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.data.TaxRatesData;
import com.openhr.data.Users;
import com.openhr.factories.common.OpenHRSessionFactory;



public class TaxratesFactory implements Serializable{
	private static Session session;
	private static Query query;
	private static List<TaxRatesData> trd;
	
	
public static TaxRatesData findFromIncome()
	
	
	{
	session = OpenHRSessionFactory.getInstance().getCurrentSession();
	session.beginTransaction();
	Query query = session.createQuery("from TaxRatesData  order by id DESC");
	query.setMaxResults(1);
	TaxRatesData taxin = (TaxRatesData) query.uniqueResult();
	session.getTransaction().commit();
		return taxin;
	}
public static List<TaxRatesData> findByIncomeToo(double double1)
{
	session = OpenHRSessionFactory.getInstance().getCurrentSession();
	session.beginTransaction();
	query = session.getNamedQuery("TaxRatesData.findByIncomeTo");
    query.setDouble(0, double1);
    trd = query.list();
    session.getTransaction().commit();
	return trd;
}
public static List<TaxRatesData> findByIncomeTo(double double1)
{
	Session	session = OpenHRSessionFactory.getInstance().openSession();
	session.beginTransaction();
	query = session.getNamedQuery("TaxRatesData.findByIncomeFrom");
    query.setDouble(0, double1);
    List<TaxRatesData> trdto = query.list();
    session.getTransaction().commit();
    session.close();
	return trdto;
}

public static boolean update(TaxRatesData trds) {
    boolean done = false;
    List<TaxRatesData> trdis = TaxratesFactory.findByIncomeTo(trds.getIncomeFrom());
    
    session = OpenHRSessionFactory.getInstance().getCurrentSession();
    session.beginTransaction();

    try {
    	TaxRatesData trd= null;
    	
    	if(trds.getId() != null) {
    		trd = (TaxRatesData) session.get(TaxRatesData.class, trds.getId()); ;
    	}
    	else if (trdis.size() > 0){
    		trd = (TaxRatesData) session.get(TaxRatesData.class, trdis.get(0).getId()); ;
    	}
    	trd.setIncomeTo(trds.getIncomeTo());
        trd.setIncomePersent(trds.getIncomePersent());
        trd.setIncomeFrom(trds.getIncomeFrom());
        
        
        session.update(trd);
        session.getTransaction().commit();
        done = true;
    } catch (Exception ex) {
        ex.printStackTrace();
    }finally{
    	
    }
    return done;
}

public static boolean updatePercent(TaxRatesData trds) {
    boolean done = false;
    //List<TaxRatesData> trdis = TaxratesFactory.findByIncomeTo(trds.getIncomeFrom());
    
    session = OpenHRSessionFactory.getInstance().getCurrentSession();
    session.beginTransaction();

    try {
    	TaxRatesData trd= null;
    	
    	if(trds.getId() != null) {
    		trd = (TaxRatesData) session.get(TaxRatesData.class, trds.getId()); ;
    	}
    	
    	
        trd.setIncomePersent(trds.getIncomePersent());
        
        
        
        session.update(trd);
        session.getTransaction().commit();
        done = true;
    } catch (Exception ex) {
        ex.printStackTrace();
    }finally{
    	
    }
    return done;
}

public static  List<TaxRatesData> findAll()
{
	session = OpenHRSessionFactory.getInstance().getCurrentSession();
	session.beginTransaction();
	query = session.getNamedQuery("TaxRatesData.findAll");
   
    trd = query.list();
    session.getTransaction().commit();
	return trd;
}

public static boolean delete(TaxRatesData txrdata) {
    boolean done = false;
    session = OpenHRSessionFactory.getInstance().getCurrentSession();
    session.beginTransaction();
    try {
    	TaxRatesData txr = (TaxRatesData) session.get(TaxRatesData.class, txrdata.getId());
        session.delete(txr);
        session.flush();
        session.getTransaction().commit();
        done = true;
    } catch (Exception ex) {
        ex.printStackTrace();
    }finally{
    	
    }
    return done;
}

public static boolean upDateDelete(Double to,Double from) {
    boolean done = false;
    session = OpenHRSessionFactory.getInstance().getCurrentSession();
    session.beginTransaction();
    try {
    	List<TaxRatesData> trdis = TaxratesFactory.findByIncomeTo(to);
    	
    	
    	TaxRatesData txr = (TaxRatesData) session.get(TaxRatesData.class, trdis.get(0).getId());
    	txr.setIncomeFrom(from);
    	
        session.update(txr);
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

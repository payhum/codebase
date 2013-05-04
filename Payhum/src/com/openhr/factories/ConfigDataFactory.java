package com.openhr.factories;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.data.ConfigData;
import com.openhr.data.EmployeePayroll;
import com.openhr.factories.common.OpenHRSessionFactory;

public class ConfigDataFactory {
		private static Session session;
	    private static Query query;
	    private static List<ConfigData> configDataList;

	    public ConfigDataFactory() {
	    }

	    public static List<ConfigData> findAll() {
	        session = OpenHRSessionFactory.getInstance().getCurrentSession();
	        session.beginTransaction();
	        query = session.getNamedQuery("ConfigData.findAll");
	        configDataList = query.list();
	        session.getTransaction().commit();        
	        return configDataList;
	    }
	    
	    public static ConfigData findByName(String name) {
	        session = OpenHRSessionFactory.getInstance().getCurrentSession();
	        session.beginTransaction();
	        query = session.getNamedQuery("ConfigData.findByName");
	        query.setString(0, name);
	        configDataList = query.list();
	        session.getTransaction().commit();        
	        return configDataList.get(0);
	    }
	    
		public static boolean update(ConfigData config) {
			boolean done = false;
			Session lsession = OpenHRSessionFactory.getInstance().openSession();
			lsession.beginTransaction();
			lsession.clear();
			
			try {
				lsession.saveOrUpdate(config);
				lsession.getTransaction().commit();
				done = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				lsession.close();
			}
			return done;
		}
}

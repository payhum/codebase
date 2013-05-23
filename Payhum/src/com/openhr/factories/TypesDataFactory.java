package com.openhr.factories;

import java.io.Serializable;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.data.TypesData;
import com.openhr.factories.common.OpenHRSessionFactory;

public class TypesDataFactory implements Serializable {
	private static final long serialVersionUID = 1L;
	
  	private static TypesData td;
 
	public static TypesData findById(int id) {
		Session session2 = OpenHRSessionFactory.getInstance().openSession();
		session2.beginTransaction();
		Query query = session2.getNamedQuery("TypesData.findById");
		query.setDouble(0, id);
		if(query.list().size() != 0){
			td = (TypesData) query.list().get(0);
		}
 		session2.getTransaction().commit();
		session2.close();
		return td;
	}
	
	public static TypesData findByName(String name) {
		Session session2 = OpenHRSessionFactory.getInstance().openSession();
		session2.beginTransaction();
		Query query = session2.getNamedQuery("TypesData.findByName");
		query.setString(0, name);
		if(query.list().size() != 0){
			td = (TypesData) query.list().get(0);
		}
 		session2.getTransaction().commit();
		session2.close();
		return td;
	}
}
